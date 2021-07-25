package helpers


import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients

import java.nio.charset.StandardCharsets
import java.util.logging.Logger

import static helpers.GlobalVariable.RestUrl

class reqHelper {
    static Logger LOG = Logger.getLogger("")
    static JsonSlurper json = new JsonSlurper()
    static CloseableHttpClient httpclient = HttpClients.createDefault()

    static SendPostReq(Map message) {
        HttpPost httpPost = new HttpPost(RestUrl);
        httpPost.setEntity(new StringEntity(new String(JsonOutput.toJson(message).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)));
        httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
        LOG.info(httpPost.getMethod() as String)
        LOG.info(httpPost.getEntity().getContent().getText('UTF-8') as String)

        CloseableHttpResponse httpResponse = httpclient.execute(httpPost)
        LOG.info(httpResponse as String)

        Map res = json.parseText(httpResponse.getEntity().getContent().getText()) as Map
        res.httpResCode = httpResponse.statusLine.statusCode
        return res
    }

    static SendPutReq(Map message) {
        HttpPut httpPut = new HttpPut("http://httpbin.org/put");
        httpPut.setEntity(new StringEntity(new String(JsonOutput.toJson(message).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)));
        httpPut.setHeader("Content-type", "application/json;charset=UTF-8");
        LOG.info(httpPut.getMethod() as String)
        LOG.info(httpPut.getEntity().getContent().getText('UTF-8') as String)

        CloseableHttpResponse httpResponse = httpclient.execute(httpPut)
        LOG.info(httpResponse as String)

        Map res = json.parseText(httpResponse.getEntity().getContent().getText()) as Map
        res.httpResCode = httpResponse.statusLine.statusCode
        return res
    }


    static checkSuccess(res) {
        res.httpResCode == 200
        //Тут должны быть проверки ответа на соответствие успеху
        return true
    }

    static checkFailure(res, code) {
        //Тут должны быть проверки ответа на соответствие не успеху с соответствующим кодом
        return true
    }
}
