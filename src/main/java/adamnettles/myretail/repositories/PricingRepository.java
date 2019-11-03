package adamnettles.myretail.repositories;

import adamnettles.myretail.domain.CurrentPrice;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingRepository extends CassandraRepository<CurrentPrice, Integer> { }
