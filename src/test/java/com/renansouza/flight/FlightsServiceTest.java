package com.renansouza.flight;

import com.renansouza.flight.client.crazysupplier.CrazySupplierService;
import com.renansouza.flight.exceptions.FlightAlreadyExistsException;
import com.renansouza.flight.exceptions.FlightNotFoundException;
import com.renansouza.flight.models.FlightEntity;
import com.renansouza.flight.models.FlightResponse;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import utils.FlightsUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static utils.FlightsUtils.getFlightResponse;

class FlightsServiceTest {

    @Mock
    FlightsRepository repository;

    @Mock
    CrazySupplierService supplierService;

    @InjectMocks
    FlightsService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("get all flights without filter criteria.")
    void getAllFlightsWithoutCriteria() {
        // given - precondition or setup
        when(repository.findFlights(any(Pageable.class), any(), any(), any(), any(), any())).thenReturn(Page.empty());

        // when - action or the behaviour that we are going test
        var flights = service.getFlights(Pageable.unpaged(), null, null, null, null, null);

        // then - validate assertions from the system under test
        verify(repository, times(1)).findFlights(any(Pageable.class), any(), any(), any(), any(), any());
        assertThat(flights, notNullValue());
        assertThat(flights.getTotalPages(), is(1));
        assertThat(flights.getTotalElements(), is(0L));
        assertThat(flights.getContent().size(), is(0));
        assertThat(flights.getNumberOfElements(), is(0));
    }

    @Test
    @DisplayName("get all flights without filter criteria.")
    void getAllFlightsWithoutCriteriaAndCrazyApiDown() {
        // given - precondition or setup
        when(repository.findFlights(any(Pageable.class), any(), any(), any(), any(), any())).thenReturn(Page.empty());
        when(supplierService.getCrazySupplierFlights(anyString(), anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.emptyList());

        // when - action or the behaviour that we are going test
        var flights = service.getFlights(Pageable.unpaged(), null, null, null, null, null);

        // then - validate assertions from the system under test
        verify(repository, times(1)).findFlights(any(Pageable.class), any(), any(), any(), any(), any());
        verify(supplierService, times(1)).getCrazySupplierFlights(any(), any(), any(), any());
        assertThat(flights, notNullValue());
        assertThat(flights.getTotalPages(), is(1));
        assertThat(flights.getTotalElements(), is(0L));
        assertThat(flights.getContent().size(), is(0));
        assertThat(flights.getNumberOfElements(), is(0));
    }

    @Test
    @DisplayName("get all flights without filter criteria.")
    void getAllFlightsWithoutCriteriaAndResults() {
        // given - precondition or setup
        PageImpl<FlightResponse> page = new PageImpl<>(List.of(getFlightResponse()));
        when(repository.findFlights(any(Pageable.class), any(), any(), any(), any(), any())).thenReturn(page);

        // when - action or the behaviour that we are going test
        var flights = service.getFlights(Pageable.unpaged(), null, null, null, null, null);

        // then - validate assertions from the system under test
        verify(repository, times(1)).findFlights(any(Pageable.class), any(), any(), any(), any(), any());
        assertThat(flights, notNullValue());
        assertThat(flights.getTotalPages(), is(1));
        assertThat(flights.getTotalElements(), is(1L));
        assertThat(flights.getContent().size(), is(1));
        assertThat(flights.getNumberOfElements(), is(1));
    }

    @Test
    @DisplayName("create a new flight.")
    void createFlight() {
        // given - precondition or setup
        when(repository.exists(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(false);

        // when - action or the behaviour that we are going test
        service.createFlight(FlightsUtils.getFlightRequest());

        // then
        verify(repository, times(1)).save(any(FlightEntity.class));
    }

    @Test
    @DisplayName("fail to create a new flight.")
    void failToCreateFlight() {
        // given - precondition or setup
        when(repository.exists(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);

        // when - action or the behaviour that we are going test
        assertThrows(FlightAlreadyExistsException.class, () -> service.createFlight(FlightsUtils.getFlightRequest()));

        // then
        verify(repository, never()).save(any(FlightEntity.class));
    }

    @Test
    @DisplayName("update a flight.")
    void updateFlight() {
        // given - precondition or setup
        FlightEntity entity = FlightsUtils.getflightEntity();
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(FlightsUtils.getflightEntity()));

        // when - action or the behaviour that we are going test
        entity.setAirline("Other Airline");
        service.updateFlight(entity);

        // then
        verify(repository, times(1)).save(any(FlightEntity.class));
    }

    @Test
    @DisplayName("fail to update a flight.")
    void failToUpdateFlight() {
        // given - precondition or setup
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // when - action or the behaviour that we are going test
        assertThrows(FlightNotFoundException.class, () -> service.updateFlight(FlightsUtils.getflightEntity()));

        // then
        verify(repository, never()).delete(any(FlightEntity.class));
    }

    @Test
    @DisplayName("fail to update a flight because its equal.")
    void failToUpdateFlightBecauseItsEqual() {
        // given - precondition or setup
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(FlightsUtils.getflightEntity()));

        // when - action or the behaviour that we are going test
        service.updateFlight(FlightsUtils.getflightEntity());

        // then
        verify(repository, never()).delete(any(FlightEntity.class));
    }

    @Test
    @DisplayName("soft delete a flight.")
    void deleteFlight() {
        // given - precondition or setup
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(FlightsUtils.getflightEntity()));

        // when - action or the behaviour that we are going test
        service.deleteFlight(UUID.randomUUID());

        // then
        verify(repository, times(1)).delete(any(FlightEntity.class));
    }

    @Test
    @DisplayName("fail to soft delete a flight.")
    void failToDeleteFlight() {
        // given - precondition or setup
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // when - action or the behaviour that we are going test
        assertThrows(FlightNotFoundException.class, () -> service.deleteFlight(UUID.randomUUID()));

        // then
        verify(repository, never()).delete(any(FlightEntity.class));
    }

}