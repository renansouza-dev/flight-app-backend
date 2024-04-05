package com.renansouza.flight;

import com.renansouza.flight.models.FlightEntity;

import java.util.UUID;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import utils.FlightsUtils;
import utils.Mappers;

import static io.restassured.RestAssured.given;

@Testcontainers
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightsControllerIT {

    @Autowired
    FlightsRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port + "/api/v1/flights";
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM flights");
    }

    @Test
    @DisplayName("get all flights without filter criteria.")
    void getAllFlightsWithoutCriteria() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("get one flight with filter criteria.")
    void getAllFlightsWithCriteria() {
        FlightEntity entity = repository.save(Mappers.mapToEntity(FlightsUtils.getFlightRequest()));

        given()
                .queryParam("departureAirport", entity.getDepartureAirport())
                .queryParam("arrivalAirport", entity.getArrivalAirport())
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("create a new flight.")
    void createFlight() {
        given()
                .contentType(ContentType.JSON)
                .body(FlightsUtils.getFlightRequest())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("fail to create a new flight.")
    void failToCreateFlight() {
        FlightEntity entity = repository.save(Mappers.mapToEntity(FlightsUtils.getFlightRequest()));

        given()
                .contentType(ContentType.JSON)
                .body(FlightsUtils.getFlightRequest())
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName("update a flight.")
    void updateFlight() {
        FlightEntity entity = repository.save(Mappers.mapToEntity(FlightsUtils.getFlightRequest()));
        entity.setAirline("Other airline");

        given()
                .contentType(ContentType.JSON)
                .body(entity)
                .when()
                .patch()
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("fail to update a flight.")
    void failToUpdateFlight() {
        FlightEntity entity = repository.save(Mappers.mapToEntity(FlightsUtils.getFlightRequest()));
        repository.delete(entity);

        given()
                .contentType(ContentType.JSON)
                .body(entity)
                .when()
                .patch()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("soft delete a flight.")
    void deleteFlight() {
		var flightId = repository.save(FlightsUtils.getflightEntity()).getId();

		given()
				.contentType(ContentType.JSON)
				.when()
				.delete("/{id}", flightId)
				.then()
				.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("fail to soft delete a flight.")
    void failToDeleteFlight() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/{id}", UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}