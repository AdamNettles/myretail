package adamnettles.myretail;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@SpringBootApplication
public class MyretailApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyretailApplication.class, args);
  }

  @Bean
  CassandraTemplate cassandraTemplate(Cluster cluster) {
    Session session = cluster.connect("product");
    return new CassandraTemplate(session);
  }

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public static Collection<String> validCurrencyCodes() {
    String[] array = {"FJD", "MXN", "SCR", "CDF", "BBD", "HNL", "UGX", "ZAR", "MXV", "STN", "CUC", "BSD", "SDG", "IQD",
        "CUP", "GMD", "TWD", "RSD", "UYI", "MYR", "FKP", "XOF", "UYU", "CVE", "OMR", "KES", "SEK", "BTN", "GNF", "MZN",
        "SVC", "ARS", "QAR", "IRR", "XPF", "THB", "UZS", "BDT", "LYD", "KWD", "RUB", "ISK", "MKD", "DZD", "PAB", "SGD",
        "KGS", "XAF", "HRK", "CHF", "DJF", "CHE", "TZS", "VND", "AUD", "CHW", "KHR", "IDR", "KYD", "BWP", "SHP", "TJS",
        "RWF", "AED", "DKK", "BGN", "MMK", "NOK", "SYP", "ZWL", "LKR", "CZK", "XCD", "HTG", "XSU", "BHD", "KZT", "SZL",
        "YER", "AFN", "AWG", "NPR", "MNT", "GBP", "BYN", "HUF", "BIF", "XUA", "XDR", "BZD", "MOP", "NAD", "PEN", "WST",
        "TMT", "CLF", "GTQ", "CLP", "TND", "SLL", "DOP", "KMF", "GEL", "MAD", "TOP", "AZN", "PGK", "UAH", "ERN", "CNY",
        "MRU", "BMD", "PHP", "PYG", "JMD", "COP", "USD", "COU", "USN", "ETB", "SOS", "VUV", "VEF", "LAK", "BND", "LRD",
        "ALL", "ZMW", "ILS", "GHS", "GYD", "KPW", "BOB", "MDL", "AMD", "TRY", "LBP", "JOD", "HKD", "EUR", "LSL", "CAD",
        "BOV", "MUR", "GIP", "RON", "NGN", "CRC", "PKR", "ANG", "SRD", "SAR", "TTD", "MVR", "INR", "KRW", "JPY", "AOA",
        "PLN", "SBD", "MWK", "MGA", "BAM", "EGP", "SSP", "NIO", "NZD", "BRL"};
    HashSet<String> hashSet = new HashSet<>(Arrays.asList(array));
    return Collections.unmodifiableCollection(hashSet);
  }

}
