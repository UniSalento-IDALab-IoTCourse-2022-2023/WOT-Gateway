package it.unisalento.iot.gateway.repositories;

import it.unisalento.iot.gateway.domains.Alarm;
import it.unisalento.iot.gateway.domains.AlarmType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * interfaccia per effettuare le query riguardanti alarm, nel database
 */
public interface IAlarmRepository extends MongoRepository<Alarm, String> {

  Optional<Alarm> findByAlarmType(AlarmType alarmType);
  Optional<Alarm> findByBoilerId(String boilerId);
}
