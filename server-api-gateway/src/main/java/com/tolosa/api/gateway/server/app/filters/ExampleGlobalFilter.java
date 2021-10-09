package com.tolosa.api.gateway.server.app.filters;

import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.base.Objects;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class ExampleGlobalFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		/*
		 * Con el Exchange podemos obtener el request y el response
		 */
		log.info("Ejecutamos pre filters");
		exchange.getRequest().mutate().headers(h -> h.add("token", "123456"));
		// con el chain filter continuamos con el flujo de los filtros de la app
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			// dentro de la fncion lambda es el post
			log.info("Ejecutamos filtro post");
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(value -> {
				exchange.getResponse().getHeaders().add("token", value);
			});
			
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
			exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1;
	}

}
