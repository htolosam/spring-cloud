package com.tolosa.items.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tolosa.items.api.models.dto.Item;
import com.tolosa.items.api.models.dto.Product;
import com.tolosa.items.api.models.service.IItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RefreshScope
@RestController
public class ItemController {
	
	@Autowired
	private Environment env;

	private IItemService itemService;
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Value("${configuration.text}")
	private String text;

	public ItemController(@Autowired @Qualifier("serviceFeign") IItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	public List<Item> list(@RequestParam(name = "name") String name,
			@RequestHeader(name = "token-request") String token) {
		log.info("NAME :: {} ::", name);
		log.info("TOKEN :: {} ::", token);
		return itemService.findAll();
	}

	@GetMapping("/{id}/{quantity}")
	public Item detail(@PathVariable Integer id, @PathVariable Integer quantity) {
		return cbFactory.create("items").run(() -> itemService.findById(id, quantity), e -> this.alternateMethod(id, quantity, e));
	}
	
	/**
	 * Si se usa las anotaciones: CircuitBreaker o TimeLimt
	 * Funcionara solo con el archivo application.yml por lo que de forma progrmatica
	 * n funcionara
	 * @param id
	 * @param quantity
	 * @return
	 */
	@CircuitBreaker(name = "items", fallbackMethod = "alternateMethod")
	@GetMapping("/v2/{id}/{quantity}")
	public Item detailV2(@PathVariable Integer id, @PathVariable Integer quantity) {
		return itemService.findById(id, quantity);
	}
	
	
	/**
	 * Bo se debe usar el fallbackMethod en ambos, solo en en el ciruitbreaker se deberia
	 * usar el metodo alternativo
	 * @param id
	 * @param quantity
	 * @return
	 */
	@CircuitBreaker(name = "items", fallbackMethod = "alternateMethod2")
	@TimeLimiter(name = "items", fallbackMethod = "alternateMethod2")
	@GetMapping("/v3/{id}/{quantity}")
	public CompletableFuture<Item> detailV3(@PathVariable Integer id, @PathVariable Integer quantity) {
		return CompletableFuture.supplyAsync(() -> itemService.findById(id, quantity));
	}

	public Item alternateMethod(Integer id, Integer quantity, Throwable e) {
		log.info("Excepcion lanzzada en el micro product :: {} ::", e.getMessage());
		return Item.builder().quantity(quantity)
				.product(Product.builder().id(id).name("Producto predeterminado").price(0.00).build()).build();

	}
	
	public CompletableFuture<Item> alternateMethod2(Integer id, Integer quantity, Throwable e) {
		log.info("Excepcion lanzzada en el micro product :: {} ::", e.getMessage());
		return CompletableFuture.supplyAsync(() -> Item.builder().quantity(quantity)
				.product(Product.builder().id(id).name("Producto predeterminado").price(0.00).build()).build());

	}
	
	@GetMapping("/get-config")
	public ResponseEntity<?> getConfig() {
		Map<String, String> json = new HashMap<>();
		json.put("texto", text);
		if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("name", env.getProperty("configuration.author.name"));
			json.put("email", env.getProperty("configuration.author.email"));
		}
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}

}
