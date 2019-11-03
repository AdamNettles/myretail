package adamnettles.myretail.services;

import adamnettles.myretail.domain.*;
import adamnettles.myretail.gateways.RedskyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Service;
import java.util.Collection;

import static adamnettles.myretail.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {

  private final CassandraRepository<Pricing, Integer> pricingRepository;
  private final RedskyGateway redskyGateway;
  private final Collection<String> validCurrencyCodes;

  @Autowired
  public ProductServiceImpl(CassandraRepository<Pricing, Integer> pricingRepository,
                            RedskyGateway redskyGateway,
                            Collection<String> validCurrencyCodes) {
    this.pricingRepository = pricingRepository;
    this.redskyGateway = redskyGateway;
    this.validCurrencyCodes = validCurrencyCodes;
  }

  @Override
  public Product getProduct(int productId) {
    checkProductId(productId);
    RedskyProduct redskyProduct = redskyGateway.getRedSkyProduct(productId).getProduct();
    Pricing pricing = pricingRepository.findById(productId).get();
    checkPricing(pricing);
    return new Product(
        productId,
        redskyProduct.getItem().getProductDescription().getTitle(),
        new PricingJson(pricing));
  }

  @Override
  public void putProductPrice(Pricing pricing) {
    checkPricing(pricing);
    //make sure it's valid
    redskyGateway.getRedSkyProduct(pricing.getId());
    pricingRepository.save(pricing);
  }

  private void checkProductId(int productId) {
    if (productId < 1) throw new IllegalArgumentException(BAD_ID_MSG);
  }

  private void checkPricing(Pricing pricing) {
    if (pricing.getId() < 1) throw new IllegalArgumentException(BAD_ID_MSG);
    if (pricing.getValue() <= 0) throw new IllegalArgumentException(BAD_PRICE_MSG);
    if (!validCurrencyCodes.contains(pricing.getCurrencyCode())) throw new IllegalArgumentException(BAD_CODE_MSG);
  }
}
