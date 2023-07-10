package it.unisalento.iot.gateway.configuration;

import it.unisalento.iot.gateway.exceptions.CannotSendRequestException;
import it.unisalento.iot.gateway.repositories.ICarbonMonoxideRepository;
import it.unisalento.iot.gateway.repositories.IPerformanceRepository;
import it.unisalento.iot.gateway.services.impl.BoilerService;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

/**
 * classe per la configurazione della connessione tra il gateway ed il BE in Cloud
 */
@Configuration
public class ConnectionConfig {

  @Autowired
  BoilerService boilerService;
  @Autowired
  IPerformanceRepository performanceRepository;
  @Autowired
  ICarbonMonoxideRepository carbonMonoxideRepository;

  static final String boilerDataTopic = "boilerData-topic";
  static final String alarmTopic = "alarm-topic";

  /**
   * metodo che inizializza il topic boilerData
   * @return del topic waste
   */
  @Bean
  public TopicExchange boilerDataTopicExchange() {
    return new TopicExchange(boilerDataTopic);
  }

  /**
   * metodo che inizializza il topic alarm
   * @return del topic alarm
   */
  @Bean
  public TopicExchange alarmTopicExchange() {
    return new TopicExchange(alarmTopic);
  }

  @Bean
  public void inputCli() throws CannotSendRequestException, InterruptedException {
//    PerformanceRawData performanceRawData = new PerformanceRawData();
//    performanceRawData.setDate(new Date());
//    performanceRawData.setPerformanceRawData(12.3f);
//    performanceRepository.save(performanceRawData);
//
//    CarbonMonoxideRawData carbonMonoxideRawData = new CarbonMonoxideRawData();
//    carbonMonoxideRawData.setDate(new Date());
//    carbonMonoxideRawData.setCarbonMonoxideRawData(45.6f);
//    carbonMonoxideRepository.save(carbonMonoxideRawData);

    boilerService.sendBoilerData();
  }
}
