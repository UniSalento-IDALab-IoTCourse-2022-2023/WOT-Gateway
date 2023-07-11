package it.unisalento.iot.gateway.configuration;

import it.unisalento.iot.gateway.exceptions.CannotSendRequestException;
import it.unisalento.iot.gateway.services.impl.BoilerService;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * classe per la configurazione della connessione tra il gateway ed il BE in Cloud
 */
@Configuration
public class ConnectionConfig {

  static final String boilerDataTopic = "boilerData-topic";
  static final String alarmTopic = "alarm-topic";
  public static int SEC_SLEEPING_TIME = 10;

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
}
