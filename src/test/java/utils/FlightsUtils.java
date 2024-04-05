package utils;

import com.renansouza.flight.client.crazysupplier.models.CrazySupplierFlight;
import com.renansouza.flight.models.FlightEntity;
import com.renansouza.flight.models.FlightRequest;
import com.renansouza.flight.models.FlightResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FlightsUtils {

    private static final LocalDateTime NOW = LocalDateTime.now();

    public static FlightEntity getflightEntity() {
        return new FlightEntity(
                UUID.randomUUID(),
                "Airline",
                "Supplier",
                BigDecimal.TEN,
                "DEP",
                "ARR",
                NOW,
                NOW.plusHours(2)
        );
    }

    public static FlightRequest getFlightRequest() {
        return new FlightRequest(
                "Airline",
                "Supplier",
                BigDecimal.TEN,
                "DEP",
                "ARR",
                NOW,
                NOW.plusHours(2));
    }

    public static FlightResponse getFlightResponse() {
        return new FlightResponse(
                UUID.randomUUID(),
                "Airline",
                "Supplier",
                BigDecimal.TEN,
                "DEP",
                "ARR",
                NOW,
                NOW.plusHours(2));
    }

    public static CrazySupplierFlight getCrazyClientFlight() {
        LocalDateTime outboundDateTime = NOW.plusHours(2);

        return new CrazySupplierFlight(
                "Airline",
                BigDecimal.TEN,
                BigDecimal.ZERO,
                "DEP",
                "ARR",
                NOW.toLocalDate(),
                NOW.toLocalTime(),
                outboundDateTime.toLocalDate(),
                outboundDateTime.toLocalTime()
        );
    }

}