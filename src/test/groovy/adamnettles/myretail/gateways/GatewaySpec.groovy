package adamnettles.myretail.gateways

import adamnettles.myretail.domain.Item
import adamnettles.myretail.domain.Pricing
import adamnettles.myretail.domain.ProductDescription
import adamnettles.myretail.domain.RedskyObject
import adamnettles.myretail.domain.RedskyProduct
import adamnettles.myretail.gateways.RedskyGateway
import adamnettles.myretail.gateways.RedskyGatewayImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Unroll

import static adamnettles.myretail.Constants.NO_REDSKY_FOUND

class GatewaySpec extends Specification {

    RestTemplate mockTemplate = Mock()
    RedskyGateway redskyGateway = new RedskyGatewayImpl(mockTemplate)

    def "no exception when template call works"() {
        given: 'an acceptable redsky return value'
        ResponseEntity<RedskyObject> mockResponse = Mock()
        RedskyObject redskyObject = new RedskyObject(new RedskyProduct(new Item(new ProductDescription("whatever"))))

        when: 'http call'
        def result = redskyGateway.getRedSkyProduct(111)

        then:
        1 * mockTemplate.getForEntity(_ as String, _ as Class<RedskyObject>) >> mockResponse
        1 * mockResponse.getBody() >> redskyObject
        0 * _
        result == redskyObject
        noExceptionThrown()
    }

    def "throws exception when product id not found in redsky"() {
        given: 'a 404 exception'
        int badId = 111
        HttpClientErrorException clientErrorException = new HttpClientErrorException(HttpStatus.NOT_FOUND, "whatever")

        when: 'http called with non-existant id'
        redskyGateway.getRedSkyProduct(badId)

        then: 'exception'
        1 * mockTemplate.getForEntity(_ as String, _ as Class<RedskyObject>) >> { throw clientErrorException }
        0 * _
        def e = thrown(IllegalArgumentException)
        e.message == NO_REDSKY_FOUND + badId
    }

    @Unroll
    def "non 404 exceptions thrown as themselves"() {
        given: 'a non 404 exceptions'
        int id = 111
        HttpClientErrorException clientErrorException = new HttpClientErrorException(theStatus, "whatever")

        when: 'http called with non-existant id'
        redskyGateway.getRedSkyProduct(id)

        then: 'exception'
        1 * mockTemplate.getForEntity(_ as String, _ as Class<RedskyObject>) >> { throw clientErrorException }
        0 * _
        def e = thrown(HttpClientErrorException)
        e.statusCode == theStatus

        where:
        theStatus << [HttpStatus.BAD_GATEWAY, HttpStatus.BAD_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR]
    }
}
