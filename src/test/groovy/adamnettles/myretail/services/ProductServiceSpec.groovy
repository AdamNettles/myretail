package adamnettles.myretail.services

import adamnettles.myretail.MyretailApplication
import adamnettles.myretail.domain.Item
import adamnettles.myretail.domain.CurrentPrice
import adamnettles.myretail.domain.CurrentPriceJson
import adamnettles.myretail.domain.Product
import adamnettles.myretail.domain.ProductDescription
import adamnettles.myretail.domain.RedskyObject
import adamnettles.myretail.domain.RedskyProduct
import adamnettles.myretail.gateways.RedskyGateway
import adamnettles.myretail.repositories.PricingRepository
import spock.lang.Specification
import spock.lang.Unroll

import static adamnettles.myretail.Constants.BAD_CODE_MSG
import static adamnettles.myretail.Constants.BAD_PRICE_MSG
import static adamnettles.myretail.Constants.BAD_ID_MSG

class ProductServiceSpec extends Specification {

  PricingRepository mockRepository = Mock()
  RedskyGateway mockGateway = Mock()
  Collection<String> validCurrencyCodes = MyretailApplication.validCurrencyCodes()
  ProductService productService = new ProductServiceImpl(mockRepository, mockGateway, validCurrencyCodes)

  def "product service get works with good id input"() {

    given: 'expected data'
//    String expectedName = "The Big Lebowski (Blu-ray) (Widescreen)"
    //TODO find out if description including "(Widescreen)" is in error, doesn't appear in redsky api json
    RedskyObject expectedRedsky = new RedskyObject(new RedskyProduct(new Item(new ProductDescription(name))))
    Optional<CurrentPrice> expectedPricing = Optional.of(new CurrentPrice(id, price as double, name))
    Product expectedProduct = new Product(id, name, new CurrentPriceJson(expectedPricing.get()))

    when: 'product service is called'
    Product acutalProduct = productService.getProduct(id)

    then: 'the expected object is produced'
    1 * mockRepository.findById(id) >> expectedPricing
    1 * mockGateway.getRedSkyProduct(id) >> expectedRedsky
    0 * _
    acutalProduct == expectedProduct

    where: 'reasonable data'
    id | name | price
    11 | 'USD' | 11.11
    239872394 | 'USD' | 1_000_000.99
    1 | 'FJD' | 0.0001
  }

  @Unroll
  def "product service get fails on bad id:  #id"() {

    when: 'product service is called'
    productService.getProduct(id)

    then: 'exceptions are thrown'
    0 * _
    def e = thrown(IllegalArgumentException)
    e.message == BAD_ID_MSG

    where: 'unreasonable ids'
    id << [-1, 0]
  }

  def "product service upsert works with good id #id"() {

    given:
    CurrentPrice pricing = new CurrentPrice(id, price, code)

    RedskyObject mockRedskyObject = new RedskyObject(new RedskyProduct(new Item(new ProductDescription("fake"))))

    when: 'post called with good id'
    productService.putProductPrice(pricing)

    then: 'no exceptions are thrown'
    1 * mockGateway.getRedSkyProduct(id) >> mockRedskyObject
    1 * mockRepository.save(pricing)
    0 * _
    noExceptionThrown()

    where: 'valid data'
    id | price | code
    1 | 11.11 | "USD"
    13860428 | 0.01 | "USD"
    13860428 | 13.49 | "USD"
    13860428 | 13.49 | "XCD"
    13860428 | 13.49 | "AUD"
    13860428 | 13.49 | "BTN"
    13860428 | 13.49 | "BAM"
  }

  @Unroll
  def "product service upsert fails with bad input #id .. #price .. #code"() {

    when: 'post called with invalid ids'
    productService.putProductPrice(new CurrentPrice(id, price as double, code))

    then: 'exception thrown with expected message'
    0 * _
    def e = thrown(IllegalArgumentException)
    e.message == expectedMessage

    where: 'invalid ids'
    id | price | code | expectedMessage
    0 | 1.1 | "USD" | BAD_ID_MSG
    -1 | 1.1 | "USD" | BAD_ID_MSG
    1 | 0 | "USD" | BAD_PRICE_MSG
    1 | 1 | "" | BAD_CODE_MSG
    1 | 1 | null | BAD_CODE_MSG

  }

  def "currency code collection is immutable"() {
    when:
    validCurrencyCodes.add("another value")

    then:
    thrown(UnsupportedOperationException)

    when:
    validCurrencyCodes.remove("another value")

    then:
    thrown(UnsupportedOperationException)

    when:
    validCurrencyCodes.clear()

    then:
    thrown(UnsupportedOperationException)
  }

}
