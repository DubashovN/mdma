package ru.filit.oas.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.filit.oas.web.exception.ClientNotFoundException;

//@RestControllerAdvice
public class ClientResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ClientNotFoundException.class })
    protected ResponseEntity<Object> handleClientException(RuntimeException ex, WebRequest request) {
        String message = "This should be application specific";
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
