package adamnettles.myretail.services;

import adamnettles.myretail.domain.CurrentPrice;
import adamnettles.myretail.domain.Product;

public interface ProductService {

  Product getProduct(int productId);

  void putProductPrice(CurrentPrice currentPrice);

}
