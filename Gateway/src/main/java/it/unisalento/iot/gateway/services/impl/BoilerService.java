package it.unisalento.iot.gateway.services.impl;

import it.unisalento.iot.gateway.domains.AggregatedData;
import it.unisalento.iot.gateway.domains.AlarmType;
import it.unisalento.iot.gateway.domains.RawData;
import it.unisalento.iot.gateway.exceptions.CannotSendRequestException;
import it.unisalento.iot.gateway.iservices.IAggregatedDataServiceImpl;
import it.unisalento.iot.gateway.iservices.IBoilerServiceImpl;
import it.unisalento.iot.gateway.repositories.IRawDataRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * classe per specificare i servizi del boiler
 */
@Service
public class BoilerService implements IBoilerServiceImpl {

  @Autowired
  RabbitTemplate rabbitTemplate;
  @Autowired
  IAggregatedDataServiceImpl aggregatedDataService;
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  IRawDataRepository rawDataRepository;

  public static int PERFORMANCE_TOP_RANGE = 90;
  public static int EMISSIONS_TOP_RANGE = 1000;
  public static final int LOW_WATER_PRESS_VALUE = 2;
  public static final int GAS_FLAME_FAULT_VALUE = 3;
  public static final int AIR_PRESS_FAULT_VALUE = 4;
  public static final int WATER_OVERTEMP_VALUE = 5;

  static final String routingKey = "gateway.boilerData.#";
  static final String boilerDataTopic = "boilerData-topic";
  static final String alarmTopic = "alarm-topic";
  static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
  
  @Override
  public void sendBoilerData() throws CannotSendRequestException {

    // prendo i dati raw dal db
    List<RawData> rawDataList = rawDataRepository.findAll();

    // creazione dei dati aggregati
    AggregatedData aggregatedData = aggregatedDataService.createAggregatedData(rawDataList);

    // creazione del json
    String aggregatedDataString = "{";

    // "date":"..."
    aggregatedDataString += "\"date\":\"" + sdf.format(new Date()) + "\",";

    // "boilerId":"..."
    aggregatedDataString += "\"boilerId\":\"" + aggregatedData.getBoilerId() + "\",";

    // "temperatureAverageData":"..."
    aggregatedDataString += "\"temperatureAverageData\":\"" + aggregatedData.getTemperatureAverageData() + "\",";

    // "pressureAverageData":"..."
    aggregatedDataString += "\"pressureAverageData\":\"" + aggregatedData.getPressureAverageData() + "\",";

    // "carbonMonoxideAverageData":"..."
    aggregatedDataString += "\"carbonMonoxideAverageData\":\"" + aggregatedData.getCarbonMonoxideAverageData() + "\",";

    // "performanceAverageData":"..."
    aggregatedDataString += "\"performanceAverageData\":\"" + aggregatedData.getPerformanceAverageData() + "\"";

    // end
    aggregatedDataString += "}";

    System.out.println("\nSending aggregated data...");

    // check degli alarms
    try {
      // check del livello del monossido di carbonio
      if (aggregatedData.getCarbonMonoxideAverageData() > EMISSIONS_TOP_RANGE) {
        sendAlarm(String.valueOf(AlarmType.EMISSIONS), aggregatedData.getBoilerId());
      }
      // check del livello delle performance
      if (aggregatedData.getPerformanceAverageData() < PERFORMANCE_TOP_RANGE){
        sendAlarm(String.valueOf(AlarmType.PERFORMANCE), aggregatedData.getBoilerId());
      }

      for (RawData rawData : rawDataList){
        switch (rawData.getStateRawData()) {
          // check della pressione dell'acqua
          case LOW_WATER_PRESS_VALUE -> sendAlarm(String.valueOf(AlarmType.LOW_WATER_PRESS), aggregatedData.getBoilerId());

          // check del guasto del bruciatore
          case GAS_FLAME_FAULT_VALUE -> sendAlarm(String.valueOf(AlarmType.GAS_FLAME_FAULT), aggregatedData.getBoilerId());

          // check del guasto della pressione dell'aria
          case AIR_PRESS_FAULT_VALUE -> sendAlarm(String.valueOf(AlarmType.AIR_PRESS_FAULT), aggregatedData.getBoilerId());

          // check della temperatura dell'acqua
          case WATER_OVERTEMP_VALUE -> sendAlarm(String.valueOf(AlarmType.WATER_OVERTEMP), aggregatedData.getBoilerId());
        }
      }

      // invio della richesta
      rabbitTemplate.convertAndSend(boilerDataTopic, routingKey, aggregatedDataString);

    } catch (Exception e) {
      e.printStackTrace();
      throw new CannotSendRequestException();
    }

    // delete dei dati raw usati
    mongoTemplate.dropCollection("rawData");

    System.out.println("\n+---------------- - -  -  -   -\nAggregated data sent: " + aggregatedDataString + "\n+---------------- - -  -  -   -\n");
  }


  @Override
  public void sendAlarm(String alarmType, String boilerId) throws CannotSendRequestException {

    // creazione del json
    String alarm = "{";

    // "date":"..."
    alarm += "\"date\":\"" + sdf.format(new Date()) + "\",";

    // "alarmType":"..."
    alarm += "\"alarmType\":\"" + alarmType + "\",";

    // "boilerId":"..."
    alarm += "\"boilerId\":\"" + boilerId + "\"";

    // end
    alarm += "}";

    System.out.println("\nSending alarm...");

    try {
      // invio della richesta
      rabbitTemplate.convertAndSend(alarmTopic, routingKey, alarm);
    } catch (Exception e) {
      e.printStackTrace();
      throw new CannotSendRequestException();
    }

    System.out.println("\n+---------------- - -  -  -   -\n🚨 " + alarm + "\n+---------------- - -  -  -   -\n");

  }
}
