package com.renansouza.flight;

import com.renansouza.flight.client.crazysupplier.CrazyClient;
import com.renansouza.flight.client.crazysupplier.models.CrazySupplierFlightParams;
import com.renansouza.flight.exceptions.FlightAlreadyExistsException;
import com.renansouza.flight.exceptions.FlightNotFoundException;
import com.renansouza.flight.models.FlightEntity;
import com.renansouza.flight.models.FlightRequest;

import java.util.Collections;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import utils.FlightsUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightsController.class)
class FlightsControllerTest {

    private static final String PATH = "/flights";

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FlightsService service;

    @MockBean
    CrazyClient supplier;

    @Test
    @DisplayName("get all flights without filter criteria.")
    void getAllFlightsWithoutCriteria() throws Exception {
        // given - precondition or setup
        when(service.getFlights(any(Pageable.class), any(), any(), any(), any(), any())).thenReturn(Page.empty());
        when(supplier.getCrazyFlights(any(CrazySupplierFlightParams.class))).thenReturn(Collections.emptyList());

        // when - action or the behaviour that we are going test
        // then - verify the output
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.last", is(true)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.content", is(Collections.EMPTY_LIST)));
    }

    @Test
    @DisplayName("create a new flight.")
    void createFlight() throws Exception {
        // given - precondition or setup
        doNothing().when(service).createFlight(any(FlightRequest.class));

        // when - action or the behaviour that we are going test
        // then - verify the output
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(FlightsUtils.getFlightRequest())))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("fail to create a new flight.")
    void failToCreateFlight() throws Exception {
        // given - precondition or setup
        doThrow(new FlightAlreadyExistsException()).when(service).createFlight(any(FlightRequest.class));

        // when - action or the behaviour that we are going test
        // then - verify the output
        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(FlightsUtils.getFlightRequest())))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("update a flight.")
    void updateFlight() throws Exception {
        // given - precondition or setup
        doNothing().when(service).updateFlight(any(FlightEntity.class));

        // when - action or the behaviour that we are going test
        // then - verify the output
        mockMvc.perform(patch(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(FlightsUtils.getflightEntity())))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("fail to update a flight.")
    void failToUpdateFlight() throws Exception {
        // given - precondition or setup
        doThrow(new FlightNotFoundException()).when(service).updateFlight(any(FlightEntity.class));

        // when - action or the behaviour that we are going test
        // then - verify the output
        mockMvc.perform(patch(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(FlightEntity.builder().build())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("No flight found with the provided id.")));
    }

    @Test
    @DisplayName("soft delete a flight.")
    void deleteFlight() throws Exception {
        // given - precondition or setup
        willDoNothing().given(service).deleteFlight(any(UUID.class));

        // when - action or the behaviour that we are going test
        // then - verify the output
        mockMvc.perform(delete(PATH + "/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("fail to soft delete a flight.")
    void failToDeleteFlight() throws Exception {
        // given - precondition or setup
        doThrow(new FlightNotFoundException()).when(service).deleteFlight(any(UUID.class));

        // when - action or the behaviour that we are going test
        // then - verify the output
        mockMvc.perform(delete(PATH + "/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("No flight found with the provided id.")));
    }
}