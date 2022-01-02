package com.tolosa.oauth.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.tolosa.commons.app.models.entity"})
public class MsOauthManagerApplication implements CommandLineRunner {

	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(MsOauthManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String pass = "12345";
		for (int i = 0; i < 5; i++) {
			String result = this.passwordEncoder.encode(pass);
			System.out.println(result);
		}
	}

}
