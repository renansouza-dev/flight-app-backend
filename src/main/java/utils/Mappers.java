package utils;

import com.renansouza.flight.client.crazysupplier.models.CrazySupplierFlight;
import com.renansouza.flight.models.FlightEntity;
import com.renansouza.flight.models.FlightRequest;
import com.renansouza.flight.models.FlightResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class Mappers {

    private Mappers() {}
    private static final ZoneId cetZone = ZoneId.of("CET");

    public static FlightEntity mapToEntity(FlightRequest flightRequest) {
        return FlightEntity.builder()
                .airline(flightRequest.airline())
                .supplier(flightRequest.supplier())
                .fare(flightRequest.fare())
                .departureAirport(flightRequest.departureAirport())
                .arrivalAirport(flightRequest.arrivalAirport())
                .departureTime(flightRequest.departureTime())
                .arrivalTime(flightRequest.arrivalTime())
                .build();
    }

    public static FlightResponse mapToResponse(CrazySupplierFlight flight) {
        LocalDateTime inboundDateTime = LocalDateTime.of(flight.inboundDate(), flight.inboundTime());
        LocalDateTime outboundDateTime = LocalDateTime.of(flight.outboundDate(), flight.outboundTime());

        return new FlightResponse(
                UUID.randomUUID(),
                flight.carrier(),
                "Crazy Flight",
                flight.basePrice().add(flight.tax()),
                flight.departureAirportName(),
                flight.arrivalAirportName(),
                inboundDateTime.atZone(cetZone).toLocalDateTime(),
                outboundDateTime.atZone(cetZone).toLocalDateTime()
        );
    }

}