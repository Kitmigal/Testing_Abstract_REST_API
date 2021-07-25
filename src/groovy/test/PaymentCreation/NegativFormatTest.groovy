package test.PaymentCreation

import spock.lang.Specification
import spock.lang.Unroll

import static helpers.getRequestApi.ReqPaymentCreation
import static helpers.getRequestApi.getRequest
import static helpers.reqHelper.SendPutReq
import static helpers.reqHelper.SendPostReq
import static helpers.reqHelper.checkFailure

class NegativFormatTest extends Specification {
    static List strFields = []
    static List intFields = []
    static List BDFields = []
    static List requiredFields = []

    static List AllFields = []
    static List AllFieldsMax = []
    static List AllFieldsMin = []

    def setupSpec() {
        ReqPaymentCreation().forEach((key, mapValue) -> {
            mapValue.findAll { it =~ 'str' } ? strFields.add(key) : null
            mapValue.findAll { it =~ 'int' } ? intFields.add(key) : null
            mapValue.findAll { it =~ 'BD2' } ? BDFields.add(key) : null
            mapValue.required ? requiredFields.add(key) : null
            AllFields.add(key)
            AllFieldsMax.add(mapValue.max)
            AllFieldsMin.add(mapValue.min)
        })
    }

    @Unroll
    def "Negative test over maximum len field - #field"() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        req[field] = (req[field] as String).padLeft(max + 1, '1')

        def res = SendPostReq(req)

        then:
        checkFailure(res, 400)

        where:
        field << AllFields
        max << AllFieldsMax
    }

    @Unroll
    def "Negative test below minimum len field - #field"() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        req[field] = (min == 1) ? null : ((req[field] as String).substring(0, min - 1))

        def res = SendPostReq(req)

        then:
        checkFailure(res, 400)

        where:
        field << AllFields
        min << AllFieldsMin
    }

    @Unroll
    def "Negative test without required field - #field"() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        req.remove(field)
        def res = SendPostReq(req)

        then:
        checkFailure(res, 400)

        where:
        field << requiredFields
    }

    def "Negative test with unspecified field"() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        req.unspecifiedField = 'unspecifiedField'
        def res = SendPostReq(req)

        then:
        checkFailure(res, 400)
    }

    @Unroll
    def "Negative test with invalid field - #field"() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        req[field] = 'qwerty'
        def res = SendPostReq(req)

        then:
        checkFailure(res, 400)

        where:
        field << intFields + BDFields
    }

    def "Negative test with send GET"() {
        when:
        Map req = SendPutReq(ReqPaymentCreation())
        def res = SendPostReq(req)

        then:
        checkFailure(res, 400)
    }
}
