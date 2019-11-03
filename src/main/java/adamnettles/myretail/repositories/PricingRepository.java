package adamnettles.myretail.repositories;

import adamnettles.myretail.domain.Pricing;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingRepository extends CassandraRepository<Pricing, Integer> { }
