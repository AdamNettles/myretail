package adamnettles.myretail.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

  private int id;
  private String name;
  private PricingJson current_price;

}
