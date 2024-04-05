package com.renansouza.flight.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FlightsAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FlightAlreadyExistsException.class)
    ResponseEntity<Object> handleAlreadyException(FlightAlreadyExistsException ex, WebRequest request) {
        return buildException(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    ResponseEntity<Object> handleNotFoundException(FlightNotFoundException ex, WebRequest request) {
        return buildException(ex, request, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> buildException(RuntimeException ex, WebRequest request, HttpStatus status) {
        var path = ((ServletWebRequest) request).getRequest().getRequestURI();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("path", path);
        body.put("code", status);

        return new ResponseEntity<>(body, status);
    }
}