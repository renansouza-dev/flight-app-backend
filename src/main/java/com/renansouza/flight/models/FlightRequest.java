package com.renansouza.flight.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FlightRequest(
        @NotBlank
        @Size(min = 5, max = 85)
        String airline,
        @NotBlank
        @Size(min = 5, max = 85)
        String supplier,
        @Positive
        BigDecimal fare,
        @Size(min = 3, max = 3)
        @NotBlank
        String departureAirport,
        @Size(min = 3, max = 3)
        @NotBlank
        String arrivalAirport,
        @FutureOrPresent
        LocalDateTime departureTime,
        @FutureOrPresent
        LocalDateTime arrivalTime) {
    public FlightRequest {
        airline = airline.toUpperCase().trim();
        supplier = supplier.toUpperCase().trim();
        arrivalAirport = arrivalAirport.toUpperCase().trim();
        departureAirport = departureAirport.toUpperCase().trim();
    }
}