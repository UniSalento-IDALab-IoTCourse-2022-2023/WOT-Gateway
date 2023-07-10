package it.unisalento.iot.gateway.repositories;

import it.unisalento.iot.gateway.domains.CarbonMonoxideRawData;
import it.unisalento.iot.gateway.domains.PerformanceRawData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * interfaccia per effettuare le query riguardanti il monossido di carbonio
 */
public interface ICarbonMonoxideRepository extends MongoRepository<CarbonMonoxideRawData, String> {
}
