package com.renansouza.flight;

import com.renansouza.flight.models.FlightEntity;
import com.renansouza.flight.models.FlightRequest;
import com.renansouza.flight.models.FlightResponse;

import java.time.LocalDate;
import java.util.UUID;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flights")
@Tag(name = "flights", description = "an API to search for flights at our own and partners suppliers.")
public class FlightsController {

    private final FlightsService service;

    @GetMapping
    @Operation(summary = "Get flights", description = "Get a list of flights with optional filters and pagination")
    @Timed(value = "bookings.index.time", description = "time to retrieve bookings list", percentiles = {0.5, 0.9})
    Page<FlightResponse> getFlights(
            @Parameter(description = "Airline name") @RequestParam(required = false) String airline,
            @Parameter(description = "Departure airport code") @RequestParam(required = false) String departureAirport,
            @Parameter(description = "Arrival airport code") @RequestParam(required = false) String arrivalAirport,
            @Parameter(description = "Departure time (YYYY-MM-DD)") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureTime,
            @Parameter(description = "Arrival time (YYYY-MM-DD)") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalTime,
            @Parameter(description = "Page size") @RequestParam(required = false, defaultValue = "20") String pageSize,
            @Parameter(description = "Sort direction ('asc' or 'desc')") @RequestParam(required = false, defaultValue = "asc") String direction,
            @Parameter(description = "Sort property") @RequestParam(required = false, defaultValue = "airline") String property) {

        var sort = Sort.by(Sort.Direction.fromString(direction), property);
        var page = PageRequest.of(0, Integer.parseInt(pageSize), sort);

        return service.getFlights(page, airline, departureAirport, arrivalAirport, departureTime, arrivalTime);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new flight")
    @Timed(value = "bookings.index.time", description = "time to retrieve bookings list", percentiles = {0.5, 0.9})
    void createFlight(@Valid @RequestBody FlightRequest flightRequest) {
        service.createFlight(flightRequest);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing flight")
    @Timed(value = "bookings.index.time", description = "time to retrieve bookings list", percentiles = {0.5, 0.9})
    void updateCompanies(@Valid @RequestBody FlightEntity flight) {
        service.updateFlight(flight);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a flight by ID")
    @Timed(value = "bookings.index.time", description = "time to retrieve bookings list", percentiles = {0.5, 0.9})
    void deleteFlight(@PathVariable UUID id) {
        service.deleteFlight(id);
    }

}