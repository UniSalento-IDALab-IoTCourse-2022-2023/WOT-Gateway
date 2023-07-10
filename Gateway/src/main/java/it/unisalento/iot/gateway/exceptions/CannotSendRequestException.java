package it.unisalento.iot.gateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * classe per definire un errore custom
 */
@ResponseStatus(code=HttpStatus.BAD_REQUEST)
public class CannotSendRequestException extends Exception{
  public CannotSendRequestException() {
    System.out.print("Error! Cannot send message");
  }
}
