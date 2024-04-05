package com.renansouza.flight;

import com.renansouza.flight.client.crazysupplier.CrazySupplierService;
import com.renansouza.flight.exceptions.FlightAlreadyExistsException;
import com.renansouza.flight.exceptions.FlightNotFoundException;
import com.renansouza.flight.models.FlightEntity;
import com.renansouza.flight.models.FlightRequest;
import com.renansouza.flight.models.FlightResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utils.Mappers;

@Service
@RequiredArgsConstructor
public class FlightsService {

    private final FlightsRepository repository;
    private final CrazySupplierService crazySupplierService;

    Page<FlightResponse> getFlights(Pageable page, String airline, String departureAirport, String arrivalAirport, LocalDate departureTime, LocalDate arrivalTime) {
        String departureTimeAsString = Objects.nonNull(departureTime) ? departureTime.toString() : null;
        String arrivalTimeAsString = Objects.nonNull(arrivalTime) ? arrivalTime.toString() : null;

        Page<FlightResponse> ownFlights = repository.findFlights(page, airline, departureAirport, arrivalAirport, departureTimeAsString, arrivalTimeAsString);
        List<FlightResponse> crazySupplierFlights = crazySupplierService.getCrazySupplierFlights(departureAirport, arrivalAirport, departureTime, arrivalTime);
        List<FlightResponse> flights = Stream.concat(ownFlights.getContent().stream(), crazySupplierFlights.stream()).toList();

        return new PageImpl<>(flights, page, flights.size());
    }

    @Transactional
    void createFlight(FlightRequest flightRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        boolean exists = repository.exists(
                flightRequest.airline(),
                flightRequest.departureAirport(),
                flightRequest.arrivalAirport(),
                flightRequest.departureTime().format(formatter),
                flightRequest.arrivalTime().format(formatter));

        if (exists) {
            throw new FlightAlreadyExistsException();
        }

        repository.save(Mappers.mapToEntity(flightRequest));
    }

    @Transactional
    void updateFlight(FlightEntity flight) {
        FlightEntity entity = repository
                .findById(flight.getId())
                .orElseThrow(FlightNotFoundException::new);

        if (!flight.equals(entity)) {
            entity.setFare(flight.getFare());
            entity.setAirline(flight.getAirline());
            entity.setSupplier(flight.getSupplier());
            entity.setDepartureAirport(flight.getDepartureAirport());
            entity.setArrivalAirport(flight.getArrivalAirport());
            entity.setDepartureTime(flight.getDepartureTime());
            entity.setArrivalTime(flight.getArrivalTime());

            repository.save(entity);
        }
    }

    @Transactional
    void deleteFlight(UUID id) {
        FlightEntity entity = repository
                .findById(id)
                .orElseThrow(FlightNotFoundException::new);

        repository.delete(entity);
    }

}