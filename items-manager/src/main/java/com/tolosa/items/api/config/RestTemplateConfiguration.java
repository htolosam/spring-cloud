package com.tolosa.items.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de configuración donde registremos el rest template
 * @author ho.tolosa
 *
 */
@Configuration
public class RestTemplateConfiguration {

	
	@Bean("restTemplateClient")
	public RestTemplate registerRestTemplate() {
		return new RestTemplate();
	}
}
