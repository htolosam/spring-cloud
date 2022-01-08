package com.tolosa.api.gateway.server.app.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

	private ReactiveAuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(ReactiveAuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * Por medio del exchange podemos obtener el request y el token que estan
	 * enviando desde los micros, o algun cliente mediante las cabaceras
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
				.filter(authHeader -> authHeader.startsWith("Bearer "))
				.switchIfEmpty(chain.filter(exchange).then(Mono.empty())) // hacemos un switch por si no encontramos el
																			// bearer continuando la ejecucion con el
																			// flujo vacio
				// si viene el bearer seguimos con el flujo niormal
				.map(token -> token.replace("Bearer ", ""))
				// una vez con el token formateado seguimos inyectando el authentication manager que 
				//procesa y valida el token
				.flatMap(token -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(exchange, token)))
				// adiferencia del map que devuelve un objeto comun, el flatMap devuelve otro flujo
				//ejemplo Mono o Flux conviertiendo el flujo actual en un obeto reactivo
				//con el chain filter nos autenticamos en el contexto, pasando el exchange seguimos con
				//la ejecucion de los demas filtros y del request
				.flatMap(authentication -> chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
	}

}
