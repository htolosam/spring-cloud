package com.tolosa.users.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.tolosa.commons.app.models.entity.Role;
import com.tolosa.commons.app.models.entity.User;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.exposeIdsFor(User.class, Role.class);
		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
	}

	
	
}