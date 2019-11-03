package adamnettles.myretail.dao;

import adamnettles.myretail.domain.Pricing;

public interface CassandraDao {

  Pricing getPricing(int id);

  void upsertPricing(Pricing pricing);

}
