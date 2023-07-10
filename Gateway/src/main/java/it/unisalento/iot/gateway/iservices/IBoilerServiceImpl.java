package it.unisalento.iot.gateway.iservices;

import it.unisalento.iot.gateway.exceptions.CannotSendRequestException;

import java.util.Scanner;

public interface IBoilerServiceImpl {

  /**
   * metodo per inviare i dati del boiler associandoci topic e routing key
   * @throws CannotSendRequestException exception per mancato invio della richiesta
   */
  void sendBoilerData() throws CannotSendRequestException;

  /**
   * metodo per inviare un'allarme associandoci topic e routing key
   * @throws CannotSendRequestException exception per mancato invio della richiesta
   */
  void sendAlarm(String alarmType, String trashCanId) throws CannotSendRequestException;
}
