package adamnettles.myretail.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

  private int id;
  private String name;
  @JsonProperty("current_price")
  private PricingJson currentPrice;

}
