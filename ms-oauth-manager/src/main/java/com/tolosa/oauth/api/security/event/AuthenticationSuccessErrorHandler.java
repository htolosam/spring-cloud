package com.tolosa.oauth.api.security.event;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.tolosa.commons.app.models.entity.User;
import com.tolosa.oauth.api.services.IUserService;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private IUserService userService;

	public AuthenticationSuccessErrorHandler(@Autowired IUserService userService) {
		this.userService = userService;
	}

	/**
	 * Manejador de exitos A traves del user obtenemos los datos del usuario Debemos
	 * registrar estos evcentos en Spring Security
	 */
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		if (authentication.getDetails() instanceof WebAuthenticationDetails)
			return;
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String msg = "Success Login: " + user.getUsername();
		System.out.println(msg);
		log.info(msg);
		User userSuccess = userService.findByUserName(authentication.getName());
		if(Objects.nonNull(userSuccess.getAttempts()) && userSuccess.getAttempts() > 0) {
			userSuccess.setAttempts(0);
			this.userService.update(userSuccess, userSuccess.getId());
		}
		
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
		try {
			User user = userService.findByUserName(authentication.getName());
			user.setAttempts(Objects.isNull(user.getAttempts()) ? 0 : user.getAttempts() + 1);
			if (user.getAttempts() >= 3)
				user.setEnabled(false);
			userService.update(user, user.getId());
		} catch (FeignException fe) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
	}

}
