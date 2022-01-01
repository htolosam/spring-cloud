package com.tolosa.oauth.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * Extendemos de la clase WebSecurityConfigurerAdapter
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//inyectamos el service UserService por medio de la interface
	private UserDetailsService userDetailService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public SecurityConfig (@Autowired UserDetailsService userDetailService) {
		this.userDetailService = userDetailService;
	}

	/**
	 * Sobreescribimos el metodo configure configure(AuthenticationManagerBuilder auth)
	 * Inyectamos el obj AuthenticationManagerBuilder 
	 * Con el obj auth registramos el user detail service this.userDetailService
	 * Encriptamos la contraseña cuando se hace la solicitud, esta contraseña debe estar encriptada también
	 * en la base datos
	 */
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder());
	}
	
	/**
	 * Anotamos como bean para registrarlo en Spring
	 */
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	

}
