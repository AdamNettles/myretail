package adamnettles.myretail.gateways;

import adamnettles.myretail.domain.RedskyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static adamnettles.myretail.Constants.NO_REDSKY_FOUND;

@Service
public class RedskyGatewayImpl implements RedskyGateway {

  private static final String REDSKY_HOST = "redsky.target.com";
  private static final String REDSKY_BASE = "/v2/pdp/tcin";
  private static final String EXCLUSIONS = "taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

  private final RestTemplate restTemplate;

  @Autowired
  public RedskyGatewayImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public RedskyObject getRedSkyProduct(int id) {
    String uri = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host(REDSKY_HOST)
        .path(REDSKY_BASE)
        .pathSegment(String.valueOf(id))
        .query("excludes=" + EXCLUSIONS).toUriString();
    try {
      return restTemplate.getForEntity(uri, RedskyObject.class).getBody();
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new IllegalArgumentException(NO_REDSKY_FOUND + id, e);
      } else throw e;
    }
  }
}
