package com.tolosa.api.gateway.server.app.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Config> {

	public EjemploGatewayFilterFactory() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		//order filter
		return new OrderedGatewayFilter((exchange, chain) -> {
			//pre filter
			
			log.info("ejecutando pre gateway filter factory :: {} ::", config.message);
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				log.info("ejecutando post gateway filter factory :: {} ::", config.message);
				Optional.ofNullable(config.cookieValue).ifPresent(value -> {
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, config.cookieValue).build());
				});
			}));
		}, 2);
	}
	
	
	/**
	 * Orden que se debe seguir en el application.yml
	 */
	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("message","cookieName","cookieValue");
	}



	@Getter
	@Setter
	public static class Config {
		
		private String message;
		private String cookieName;
		private String cookieValue;
		
	}
	
}
