package adamnettles.myretail.dao;

import adamnettles.myretail.domain.Pricing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class CassandraDaoImpl implements CassandraDao {

  private final CassandraTemplate cassandraTemplate;

  @Autowired
  public CassandraDaoImpl(CassandraTemplate cassandraTemplate) {
    this.cassandraTemplate = cassandraTemplate;
  }

  @Override
  public Pricing getPricing(int id) {
    return cassandraTemplate.selectOne(Query.query(Criteria.where("id").is(id)), Pricing.class);
  }

  @Override
  public void upsertPricing(Pricing pricing) {
    cassandraTemplate.insert(pricing);
  }
}
