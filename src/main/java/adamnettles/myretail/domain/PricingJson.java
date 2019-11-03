package adamnettles.myretail.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricingJson {

  public PricingJson(Pricing pricing) {
    this.currencyCode = pricing.getCurrencyCode();
    this.value = pricing.getValue();
  }

  private double value;
  @JsonProperty("currency_code")
  private String currencyCode;

}
