package adamnettles.myretail.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentPriceJson {

  public CurrentPriceJson(CurrentPrice currentPrice) {
    this.currencyCode = currentPrice.getCurrencyCode();
    this.value = currentPrice.getValue();
  }

  private double value;
  @JsonProperty("currency_code")
  private String currencyCode;

}
