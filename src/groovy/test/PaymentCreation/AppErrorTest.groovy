package test.PaymentCreation

import spock.lang.Specification

import static helpers.getRequestApi.ReqPaymentCreation
import static helpers.getRequestApi.getRequest
import static helpers.reqHelper.SendPostReq
import static helpers.reqHelper.checkFailure
import static helpers.reqHelper.checkSuccess

class AppErrorTest extends Specification{

    def "Negative test with duplicate request"() {
        when:
        Map req = getRequest(ReqPaymentCreation())
        def res1 = SendPostReq(req)

        then:
        checkSuccess(res1)

        when:
        def res2 = SendPostReq(req)

        then:
        checkFailure(res2, 400)
    }
}
