package ru.filit.mdma.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Client not found")
public class ClientNotFoundException extends RuntimeException {

  public ClientNotFoundException() {
    super();
  }

  public ClientNotFoundException(String message) {
    super(message);
  }

  public ClientNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClientNotFoundException(Throwable cause) {
    super(cause);
  }
}