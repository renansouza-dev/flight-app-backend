package com.renansouza.flight;

import com.renansouza.flight.models.FlightEntity;
import com.renansouza.flight.models.FlightResponse;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface FlightsRepository extends
        ListCrudRepository<FlightEntity, UUID>,
        PagingAndSortingRepository<FlightEntity, UUID> {

    @Query("""
            select (count(f) > 0)
            from FlightEntity f
            where upper(f.airline) = upper(:airline) and
                upper(f.departureAirport) = upper(:departureAirport) and
                upper(f.arrivalAirport) = upper(:arrivalAirport) and
                to_char(f.departureTime, 'YYYY-MM-DD HH24:MI') = :departureTime and
                to_char(f.arrivalTime, 'YYYY-MM-DD HH24:MI') = :arrivalTime
            """)
    boolean exists(String airline, String departureAirport, String arrivalAirport, String departureTime, String arrivalTime);

    @Query("""
            SELECT new com.renansouza.flight.models.FlightResponse(
                f.id,
                f.airline,
                f.supplier,
                f.fare,
                f.departureAirport,
                f.arrivalAirport,
                f.departureTime,
                f.arrivalTime)
            from FlightEntity f
            where (upper(f.airline) = upper(:airline) or :airline is null)
                and (upper(f.departureAirport) = upper(:departureAirport) or :departureAirport is null)
                and (upper(f.arrivalAirport) = upper(:arrivalAirport) or :arrivalAirport is null)
                and (to_char(f.departureTime ,'YYYY-MM-DD') = :departureTime or :departureTime is null)
                and (to_char(f.arrivalTime ,'YYYY-MM-DD') = :arrivalTime or :arrivalTime is null)
            """)
    Page<FlightResponse> findFlights(
            Pageable pageable,
            @Param("airline") String airline,
            @Param("departureAirport") String departureAirport,
            @Param("arrivalAirport") String arrivalAirport,
            @Param("departureTime") String departureTime,
            @Param("arrivalTime") String arrivalTime);
}