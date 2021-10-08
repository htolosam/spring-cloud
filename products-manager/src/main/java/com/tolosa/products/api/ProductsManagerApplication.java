package com.tolosa.products.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProductsManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsManagerApplication.class, args);
	}

}
