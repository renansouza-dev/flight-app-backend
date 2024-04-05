package com.renansouza.flight.exceptions;

public class FlightAlreadyExistsException extends RuntimeException {
    public FlightAlreadyExistsException() {
        super("A flight with the provided details already exists.");
    }
}