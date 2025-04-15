package com.pdev.rempms.apigateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerRegistryConfig {

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> pDEVCustomizer() {

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                        .slidingWindowSize(10)//configure calls
                        .failureRateThreshold(60)
                        .waitDurationInOpenState(Duration.ofMillis(1000)) //how long the circuit should stay in the OPEN
                        .permittedNumberOfCallsInHalfOpenState(2) //number of permitted calls in the HALF_OPEN state
                        .minimumNumberOfCalls(2)
                        .maxWaitDurationInHalfOpenState(Duration.ofMillis(0))
                        .slowCallDurationThreshold(Duration.ofSeconds(2))//handle slow responses with threshold percentage
                        .slowCallRateThreshold(60)
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(20000)).build())
                .build());
    }
}
