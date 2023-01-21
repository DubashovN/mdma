package ru.filit.mdma.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Incorrect data")
public class InvalidDataException extends RuntimeException {

  public InvalidDataException() {
    super();
  }

  public InvalidDataException(String message) {
    super(message);
  }

  public InvalidDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidDataException(Throwable cause) {
    super(cause);
  }
}