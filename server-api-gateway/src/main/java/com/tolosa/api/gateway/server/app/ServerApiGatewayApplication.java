package com.tolosa.api.gateway.server.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServerApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApiGatewayApplication.class, args);
	}

}
