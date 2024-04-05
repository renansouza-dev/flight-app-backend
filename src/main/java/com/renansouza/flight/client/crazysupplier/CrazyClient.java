package com.renansouza.flight.client.crazysupplier;

import com.renansouza.flight.client.crazysupplier.models.CrazySupplierFlight;
import com.renansouza.flight.client.crazysupplier.models.CrazySupplierFlightParams;

import java.util.Collections;
import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "crazy-supplier-client", configuration = CrazyClientConfig.class, url = "${crazy.baseUri}")
public interface CrazyClient {

    @GetMapping(value = "/flights")
    @CircuitBreaker(name = "getCrazyFlightsCircuitBreaker", fallbackMethod = "getCrazyFlightsFallback")
    List<CrazySupplierFlight> getCrazyFlights(@SpringQueryMap CrazySupplierFlightParams params);

    default List<CrazySupplierFlight> getCrazyFlightsFallback(Exception exception) {
        return Collections.emptyList();
    }
}