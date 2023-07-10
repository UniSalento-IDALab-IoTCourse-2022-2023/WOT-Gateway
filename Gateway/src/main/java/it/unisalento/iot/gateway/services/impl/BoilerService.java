package it.unisalento.iot.gateway.services.impl;

import it.unisalento.iot.gateway.domains.AggregatedData;
import it.unisalento.iot.gateway.exceptions.CannotSendRequestException;
import it.unisalento.iot.gateway.iservices.IAggregatedDataServiceImpl;
import it.unisalento.iot.gateway.iservices.IBoilerServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * classe per specificare i servizi del boiler
 */
@Service
public class BoilerService implements IBoilerServiceImpl {

  @Autowired
  RabbitTemplate rabbitTemplate;
  @Autowired
  IAggregatedDataServiceImpl aggregatedDataService;

  public static float PERFORMANCE_TOP_RANGE = 50;
  public static float CO_TOP_RANGE = 10000;

  static final String routingKey = "gateway.boilerData.#";
  static final String boilerDataTopic = "boilerData-topic";
  static final String alarmTopic = "alarm-topic";
  static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

  @Override
  public void sendBoilerData() throws CannotSendRequestException {

    // creazione dei dati aggregati
    AggregatedData aggregatedData = aggregatedDataService.createAggregatedData();

    // creazione del json
    String aggregatedDataString = "{";

    // "date":"..."
    aggregatedDataString += "\"date\":\"" + sdf.format(new Date()) + "\",";

    // "boilerId":"..."
    aggregatedDataString += "\"boilerId\":\"" + aggregatedData.getBoilerId() + "\",";

    // "performanceAverageData":"..."
    aggregatedDataString += "\"performanceAverageData\":\"" + aggregatedData.getPerformanceAverageData() + "\",";

    // "CoAverageData":"..."
    aggregatedDataString += "\"CoAverageData\":\"" + aggregatedData.getCoAverageData() + "\"";

    // end
    aggregatedDataString += "}";

    System.out.println("\nSending aggregated data...");

    try {
      // check del livello delle performance
      if (aggregatedData.getPerformanceAverageData() >= PERFORMANCE_TOP_RANGE){
        sendAlarm("PERFORMANCE", aggregatedData.getBoilerId());
      } else if (aggregatedData.getCoAverageData() >= CO_TOP_RANGE) { // check del livello del monossido di carbonio
        sendAlarm("EMISSIONS", aggregatedData.getBoilerId());
      }
//      else if (...) { // check del ...
//        sendAlarm("OPEN_THERM", aggregatedData.getBoilerId());
//      }

      // invio della richesta
      rabbitTemplate.convertAndSend(boilerDataTopic, routingKey, aggregatedDataString);

    } catch (Exception e) {
      e.printStackTrace();
      throw new CannotSendRequestException();
    }

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
