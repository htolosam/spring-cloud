package com.tolosa.products.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.tolosa.commons.app.models.entity"})
public class ProductsManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsManagerApplication.class, args);
	}

}
