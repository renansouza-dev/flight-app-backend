package com.renansouza.flight.client.crazysupplier.models;

import java.time.LocalDate;
import java.util.Objects;

import lombok.Getter;

@Getter
public class CrazySupplierFlightParams {

    private final String departureAirportName;
    private final String arrivalAirportName;
    private final LocalDate inboundDate;
    private final LocalDate outboundDate;

    public CrazySupplierFlightParams(String departureAirportName, String arrivalAirportName, LocalDate inboundDate, LocalDate outboundDate) {
        this.departureAirportName = Objects.nonNull(departureAirportName) ? departureAirportName.toUpperCase() : null;
        this.arrivalAirportName = Objects.nonNull(arrivalAirportName) ? arrivalAirportName.toUpperCase() : null;
        this.inboundDate = inboundDate;
        this.outboundDate = outboundDate;
    }
}