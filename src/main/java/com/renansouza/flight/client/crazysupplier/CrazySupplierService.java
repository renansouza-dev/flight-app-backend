package com.renansouza.flight.client.crazysupplier;

import com.renansouza.flight.client.crazysupplier.models.CrazySupplierFlightParams;
import com.renansouza.flight.models.FlightResponse;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utils.Mappers;

@Service
@RequiredArgsConstructor
public class CrazySupplierService {

    private final CrazyClient supplier;

    public List<FlightResponse> getCrazySupplierFlights(String departureAirportName, String arrivalAirportName, LocalDate inboundDate, LocalDate outboundDate) {
        CrazySupplierFlightParams params = new CrazySupplierFlightParams(departureAirportName, arrivalAirportName, inboundDate, outboundDate);
        return supplier.getCrazyFlights(params).stream().map(Mappers::mapToResponse).toList();
    }

}