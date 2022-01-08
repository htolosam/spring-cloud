package com.tolosa.api.gateway.server.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 
 * 
 * Con la anotacion @EnableWebFluxSecurity habilitamos la seguridad en WebFlux
 * 
 * Se debe inyectar jwt authentication filter
 * @author ho.tolosa
 *
 */
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
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
				.and()
				// regitramos el jwt authentication filter
				.addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.csrf().disable().build();
	}

}
