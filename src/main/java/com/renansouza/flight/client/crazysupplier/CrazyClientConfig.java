package com.renansouza.flight.client.crazysupplier;

import java.util.concurrent.TimeUnit;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrazyClientConfig {

    @Value("${crazy.client.connectTimeout:5000}")
    private Integer connectTimeoutMillis;

    @Value("${crazy.client.readTimeout:2000}")
    private Integer readTimeoutMillis;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeoutMillis, TimeUnit.MILLISECONDS, readTimeoutMillis, TimeUnit.MILLISECONDS, true);
    }

}