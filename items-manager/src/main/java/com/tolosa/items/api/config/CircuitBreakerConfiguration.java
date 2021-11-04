package com.tolosa.items.api.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class CircuitBreakerConfiguration {

	/**
	 * Besan para setear valores por defecto a custom
	 * @return
	 */
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id).circuitBreakerConfig(
					CircuitBreakerConfig.custom()
					.slidingWindowSize(10) // tamaño de la ventana deslizante por defecto 100
					.failureRateThreshold(50) // tasa de falllos del umbral
					.waitDurationInOpenState(Duration.ofSeconds(10L)) // tiempo de espera en el estado abierto 60 seg por defecto
					.permittedNumberOfCallsInHalfOpenState(5) // permitir el numero de llamadas en estado semiabierto
					.build())
					.timeLimiterConfig(TimeLimiterConfig.ofDefaults()) // time out
					.build();
		});
	}
	
}
