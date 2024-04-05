package com.renansouza.flight.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FlightResponse(
        UUID id,
        String airline,
        String supplier,
        BigDecimal fare,
        String departureAirport,
        String arrivalAirport,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime) {
}
