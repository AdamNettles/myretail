package adamnettles.myretail.services;

import adamnettles.myretail.domain.Pricing;
import adamnettles.myretail.domain.Product;

public interface ProductService {

  Product getProduct(int productId);

  void putProductPrice(Pricing pricing);

}
