package com.tolosa.oauth.api.security.event;

import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class authenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	/**
	 * Manejador de exitos
	 * A traves del user obtenemos los datos del usuario
	 * Debemos registrar estos evcentos en Spring Security
	 */
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		if (authentication.getDetails() instanceof WebAuthenticationDetails)
			return;
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String msg = "Success Login: " + user.getUsername();
		System.out.println(msg);
		log.info(msg);
	}

	/**
	 * Manejador de fracasos
	 * 
	 */
	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String msg = "Error en el login: " + exception.getMessage();
		System.out.println(msg);
		log.info(msg);
	}

}
