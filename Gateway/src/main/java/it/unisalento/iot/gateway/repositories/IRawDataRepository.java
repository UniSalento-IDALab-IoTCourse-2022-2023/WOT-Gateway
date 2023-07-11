package it.unisalento.iot.gateway.repositories;

import it.unisalento.iot.gateway.domains.RawData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * interfaccia per effettuare le query riguardanti i raw data
 */
public interface IRawDataRepository extends MongoRepository<RawData, String> {
}
