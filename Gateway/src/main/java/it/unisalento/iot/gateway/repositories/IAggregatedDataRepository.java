package it.unisalento.iot.gateway.repositories;

import it.unisalento.iot.gateway.domains.AggregatedData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * interfaccia per effettuare le query riguardanti AggregatedData, nel database
 */
public interface IAggregatedDataRepository extends MongoRepository<AggregatedData, String> {

    List<AggregatedData> findByBoilerId(String boilerId);
    List<AggregatedData> findByPerformanceAverageData(float performanceAverageData);
    List<AggregatedData> findByCoAverageData(float coAverageData);
}
