package com.renansouza.flight.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Entity
@Builder
@SoftDelete
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flights", indexes = {
        @Index(name = "idx_flightentity_id_unq", columnList = "id", unique = true),
        @Index(name = "idx_flightentity_airline_unq", columnList = "airline, departure_airport, arrival_airport, departure_time, arrival_time", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_flightentity_airline", columnNames = {"airline", "departure_airport", "arrival_airport", "departure_time", "arrival_time"})
})
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Setter
    @Size(min = 5, max = 85)
    @Column(name = "airline", nullable = false)
    private String airline;

    @Setter
    @Size(min = 5, max = 85)
    @Column(name = "supplier", nullable = false)
    private String supplier;

    @Setter
    @Positive
    @Column(name = "fare", nullable = false)
    private BigDecimal fare;

    @Setter
    @Size(min = 3, max = 3)
    @Column(name = "departure_airport", nullable = false)
    private String departureAirport;

    @Setter
    @Size(min = 3, max = 3)
    @Column(name = "arrival_airport", nullable = false)
    private String arrivalAirport;

    @Setter
    @FutureOrPresent
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Setter
    @FutureOrPresent
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        FlightEntity that = (FlightEntity) o;
        return this.id != null &&
                Objects.equals(id, that.id) &&
                Objects.equals(airline, that.airline) &&
                Objects.equals(supplier, that.supplier) &&
                Objects.equals(fare, that.fare) &&
                Objects.equals(departureAirport, that.departureAirport) &&
                Objects.equals(arrivalAirport, that.arrivalAirport) &&
                Objects.equals(departureTime, that.departureTime) &&
                Objects.equals(arrivalTime, that.arrivalTime);

    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}