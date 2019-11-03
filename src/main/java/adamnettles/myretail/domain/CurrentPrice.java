package adamnettles.myretail.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "current_price")
public class CurrentPrice {

  @PrimaryKey
  private int id;
  @Column
  private double value;
  @Column(value = "currency_code")
  private String currencyCode;

}
