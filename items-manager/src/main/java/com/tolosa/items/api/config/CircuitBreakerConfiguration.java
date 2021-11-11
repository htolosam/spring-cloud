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
					//por defecto 100%, porcentaje de umbral de llamadas lentas, si el % de llamadas es mayor que el umbral
					//se va a corto circuito
					.slowCallRateThreshold(50)
					//se configura el tiempo de duracion maximo que puede durar una llmada, se debe tener en cuenta el timeout
					//porque va primero que este y es el que se lanzara si tienen la misma duracion
					.slowCallDurationThreshold(Duration.ofSeconds(2L))
					.build())
					//.timeLimiterConfig(TimeLimiterConfig.ofDefaults()) // time out
					.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(6L)).build())
					.build();
		});
	}
	
}
