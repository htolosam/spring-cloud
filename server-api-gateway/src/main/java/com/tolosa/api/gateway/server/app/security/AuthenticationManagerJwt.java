package com.tolosa.api.gateway.server.app.security;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

/**
 * Implementa ReactiveAuthenticationManager Este flujo contiene un solo elemento
 * que recibe la autenticacion por argumento que contiene el token jwt Se
 * procesa y se valida con la firma que se encuentra en el servidor de
 * configuraciones Mono.just convierte un objeto normal en uno reactivo
 * 
 * @author ho.tolosa
 *
 */
@Component
public class AuthenticationManagerJwt implements ReactiveAuthenticationManager {

	@Value("${config.security.oauth.jwt.key}")
	private String keyJwt;

	/**
	 * Invoca el metodo subscribe automaticamente
	 */
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		// TODO Auto-generated method stub
		return Mono.just(authentication.getCredentials().toString()).map(token -> { // emitimos el token como un flujo
																					// de stream
			// get llave
			SecretKey key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(keyJwt.getBytes()));
			// devolvemos los claims
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		}).map(claims -> { // convertimos el flujo en authentication
			// get username del token
			String userName = claims.get("user_name", String.class);
			// get roles
			@SuppressWarnings("unchecked")
			List<String> roles = claims.get("authorities", List.class);
			// convertimos los roles a una collections de simple granted authirities
			// Collection<GrantedAuthority> authorities = roles.stream().map(role -> new
			// SimpleGrantedAuthority(role)).collect(Collectors.toList());
			// por referencia a metodo
			Collection<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
			return new UsernamePasswordAuthenticationToken(userName, null, authorities);
		});
	}

}
