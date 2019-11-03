package adamnettles.myretail.controllers;

import adamnettles.myretail.domain.CurrentPrice;
import adamnettles.myretail.domain.CurrentPriceJson;
import adamnettles.myretail.domain.Product;
import adamnettles.myretail.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyRetailController {

  private final ProductService productService;

  @Autowired
  public MyRetailController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("products/{id}")
  public Product getProduct(@PathVariable("id") int id) {
    return productService.getProduct(id);
  }

  @PutMapping("products/{id}")
  public void updateProductPricing(@PathVariable("id") int id, @RequestBody CurrentPriceJson currentPriceJson) {
    productService.putProductPrice(new CurrentPrice(id, currentPriceJson.getValue(), currentPriceJson.getCurrencyCode()));
  }

}
