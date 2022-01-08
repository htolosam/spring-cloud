package com.tolosa.api.gateway.server.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 
 * 
 * Con la anotacion @EnableWebFluxSecurity habilitamos la seguridad en WebFlux
 * 
 * @author ho.tolosa
 *
 */
@EnableWebFluxSecurity
public class SecurityConfig {
	
	/**
	 * Bean en el que se habilita la autenticacion para todas las rutas
	 * desabilitamos el csrf porque es una api
	 * @param http
	 * @return
	 */
	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) {
		return http.authorizeExchange()
				.pathMatchers("/api/security/oauth/**")
				.permitAll()
				.pathMatchers(HttpMethod.GET, "/api/users/users", "/api/products", "/api/items")
				.permitAll()
				.pathMatchers(HttpMethod.GET, "/api/users/users/search-user/{id}").hasAnyRole("ADMIN", "USER")
				.pathMatchers("/api/products/**", "/api/items/**", "/api/users/users/**").hasRole("ADMIN")
				.anyExchange()
				.authenticated()
				.and().csrf().disable().build();
	}

}
