package adamnettles.myretail.controllers;

import adamnettles.myretail.domain.Pricing;
import adamnettles.myretail.domain.PricingJson;
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
  public void updateProductPricing(@PathVariable("id") int id, @RequestBody PricingJson pricingJson) {
    productService.putProductPrice(new Pricing(id, pricingJson.getValue(), pricingJson.getCurrency_code()));
  }

}
