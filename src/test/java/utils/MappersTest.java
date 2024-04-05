package utils;

import com.renansouza.flight.client.crazysupplier.models.CrazySupplierFlight;
import com.renansouza.flight.models.FlightEntity;
import com.renansouza.flight.models.FlightRequest;
import com.renansouza.flight.models.FlightResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

class MappersTest {

    @Test
    @DisplayName("should map a flight request into a flight entity")
    void mapRequestIntoEntity() {
        // given
        FlightRequest request = FlightsUtils.getFlightRequest();

        // when
        FlightEntity entity = Mappers.mapToEntity(request);

        // then
        assertThat(entity, notNullValue());
        assertThat(entity.getId(), nullValue());
        assertThat(entity.getAirline(), is(request.airline()));
        assertThat(entity.getSupplier(), is(request.supplier()));
        assertThat(entity.getFare(), is(request.fare()));
        assertThat(entity.getDepartureAirport(), is(request.departureAirport()));
        assertThat(entity.getArrivalAirport(), is(request.arrivalAirport()));
        assertThat(entity.getDepartureTime(), is(request.departureTime()));
        assertThat(entity.getArrivalTime(), is(request.arrivalTime()));
    }

    @Test
    @DisplayName("should map a crazy flight into a flight response")
    void mapCrazySupplierIntoResponse() {
        // given
        CrazySupplierFlight crazySupplierFlight = FlightsUtils.getCrazyClientFlight();

        // when
        FlightResponse response = Mappers.mapToResponse(crazySupplierFlight);

        // then
        assertThat(response, notNullValue());
        assertThat(response.id(), notNullValue());
        assertThat(response.airline(), is(crazySupplierFlight.carrier()));
        assertThat(response.supplier(), is("Crazy Flight"));
        assertThat(response.fare(), is(crazySupplierFlight.basePrice().add(crazySupplierFlight.tax())));
        assertThat(response.departureAirport(), is(crazySupplierFlight.departureAirportName()));
        assertThat(response.arrivalAirport(), is(crazySupplierFlight.arrivalAirportName()));
        assertThat(response.departureTime().toLocalDate(), is(crazySupplierFlight.inboundDate()));
        assertThat(response.departureTime().toLocalTime(), is(crazySupplierFlight.inboundTime()));
        assertThat(response.arrivalTime().toLocalDate(), is(crazySupplierFlight.outboundDate()));
        assertThat(response.arrivalTime().toLocalTime(), is(crazySupplierFlight.outboundTime()));
    }

}