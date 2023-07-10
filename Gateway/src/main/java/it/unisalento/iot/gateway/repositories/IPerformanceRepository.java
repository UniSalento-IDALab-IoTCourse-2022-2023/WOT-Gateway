package it.unisalento.iot.gateway.repositories;

import it.unisalento.iot.gateway.domains.Alarm;
import it.unisalento.iot.gateway.domains.AlarmType;
import it.unisalento.iot.gateway.domains.PerformanceRawData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * interfaccia per effettuare le query riguardanti le performance
 */
public interface IPerformanceRepository extends MongoRepository<PerformanceRawData, String> {
}
