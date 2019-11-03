package adamnettles.myretail.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricingJson {

  public PricingJson(Pricing pricing) {
    this.currency_code = pricing.getCurrency_code();
    this.value = pricing.getValue();
  }

  private double value;
  private String currency_code;

}
