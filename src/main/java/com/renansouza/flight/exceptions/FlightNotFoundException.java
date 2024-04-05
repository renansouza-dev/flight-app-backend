package com.renansouza.flight.exceptions;

public class FlightNotFoundException extends RuntimeException {

    public FlightNotFoundException() {
        super("No flight found with the provided id.");
    }
}