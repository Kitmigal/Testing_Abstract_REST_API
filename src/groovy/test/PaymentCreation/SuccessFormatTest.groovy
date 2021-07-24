package test.PaymentCreation

import spock.lang.Specification
import spock.lang.Unroll

import static helpers.getRequestApi.ReqPaymentCreation
import static helpers.getRequestApi.getRequest
import static helpers.reqHelper.SendPostReq
import static helpers.reqHelper.checkSuccess

class SuccessFormatTest extends Specification {

    static List strFields = []
    static List intFields = []
    static List BD2Fields = []

    def setupSpec() {
        ReqPaymentCreation().forEach((key, mapValue) -> {
            mapValue.findAll { it =~ 'str' } ? strFields.add(key) : null
            mapValue.findAll { it =~ 'int' } ? intFields.add(key) : null
            mapValue.findAll { it =~ 'BD2' } ? BD2Fields.add(key) : null
        })
    }

    @Unroll
    def "Positive test with #name "() {
        when:
        Map req = getRequest(ReqPaymentCreation(), onlyRequired, lenFild)
        def res = SendPostReq(req)

        then:
        checkSuccess(res)

        where:
        name                | onlyRequired | lenFild
        'only required max' | true         | 'max'
        'only required min' | true         | 'min'
        'all fields max'    | false        | 'max'
        'all fields min'    | false        | 'min'
    }

    @Unroll
    def "Positive test with cyrillic symbol in field - #field "() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        req[field] = 'Иванов Иван'

        def res = SendPostReq(req)

        then:
        checkSuccess(res)

        where:
        field << strFields
    }

    @Unroll
    def "Positive test with special characters in field - #field "() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        req[field] = ' ! " # $ % & \' ( ) * + , - . / : ; < = > ? @ [ \\ ] ^ _` { | } ~'

        def res = SendPostReq(req)

        then:
        checkSuccess(res)

        where:
        field << strFields
    }

    @Unroll
    def "Positive test with numeric fields starting with 0 in field - #field "() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        req[field] = '00001'

        def res = SendPostReq(req)

        then:
        checkSuccess(res)

        where:
        field << intFields
    }

    @Unroll
    def "Positive test with BigDecimal fields starting and ending with 0 in field - #field "() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        req[field] = '00001.00'

        def res = SendPostReq(req)

        then:
        checkSuccess(res)

        where:
        field << intFields
    }
}
