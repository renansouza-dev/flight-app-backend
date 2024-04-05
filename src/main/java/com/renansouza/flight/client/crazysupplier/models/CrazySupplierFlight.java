package com.renansouza.flight.client.crazysupplier.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record CrazySupplierFlight(String carrier,
                                  BigDecimal basePrice,
                                  BigDecimal tax,
                                  String departureAirportName,
                                  String arrivalAirportName,
                                  LocalDate inboundDate,
                                  LocalTime inboundTime,
                                  LocalDate outboundDate,
                                  LocalTime outboundTime) {
}
