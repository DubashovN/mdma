package ru.filit.mdma.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Account not found")
public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException() {
  }

  public AccountNotFoundException(String message) {
    super(message);
  }

  public AccountNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public AccountNotFoundException(Throwable cause) {
    super(cause);
  }
}
