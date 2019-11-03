package adamnettles.myretail.services;

import adamnettles.myretail.dao.CassandraDao;
import adamnettles.myretail.domain.*;
import adamnettles.myretail.gateways.RedskyGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

import static adamnettles.myretail.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {

  private final CassandraDao cassandraDao;
  private final RedskyGateway redskyGateway;
  private final Collection<String> validCurrencyCodes;

  @Autowired
  public ProductServiceImpl(CassandraDao cassandraDao, RedskyGateway redskyGateway, Collection<String> validCurrencyCodes) {
    this.cassandraDao = cassandraDao;
    this.redskyGateway = redskyGateway;
    this.validCurrencyCodes = validCurrencyCodes;
  }

  @Override
  public Product getProduct(int productId) {
    checkProductId(productId);
    RedskyProduct redskyProduct = redskyGateway.getRedSkyProduct(productId).getProduct();
    Pricing pricing = cassandraDao.getPricing(productId);
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
    cassandraDao.upsertPricing(pricing);
  }

  private void checkProductId(int productId) {
    if (productId < 1) throw new IllegalArgumentException(BAD_ID_MSG);
  }

  private void checkPricing(Pricing pricing) {
    if (pricing.getId() < 1) throw new IllegalArgumentException(BAD_ID_MSG);
    if (pricing.getValue() <= 0) throw new IllegalArgumentException(BAD_PRICE_MSG);
    if (!validCurrencyCodes.contains(pricing.getCurrency_code())) throw new IllegalArgumentException(BAD_CODE_MSG);
  }
}
