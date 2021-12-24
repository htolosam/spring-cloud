package com.tolosa.items.api.models.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tolosa.items.api.models.dto.Item;
import com.tolosa.items.api.models.dto.Product;
import com.tolosa.items.api.models.service.IItemService;


@Service("serviceRestTemplate")
public class ItemServiceImpl implements IItemService {

	
	private RestTemplate restTemplateClient;
	
	public ItemServiceImpl(@Autowired RestTemplate restTemplateClient) {
		this.restTemplateClient = restTemplateClient;
	}
	
	@Override
	public List<Item> findAll() {
		List<Product> listProducts = Arrays.asList(restTemplateClient.getForObject("http://localhost:8091", Product[].class));
		return listProducts.stream().map(p -> Item.builder().product(p).quantity(1).build()).collect(Collectors.toList());
	}

	@Override
	public Item findById(Integer id, Integer quantity) {
		Map<String, String> pathvariables = new HashMap<>();
		pathvariables.put("id", id.toString());
		Product product = restTemplateClient.getForObject("http://localhost:8091/{id}", Product.class, pathvariables);
		return Item.builder().product(product).quantity(quantity).build();
	}

	@Override
	public Product save(Product product) {
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product> response = restTemplateClient.exchange("http://localhost:8091", HttpMethod.POST, body, Product.class);
		return response.getBody();
	}

	@Override
	public Product update(Product product, Integer id) {
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		Map<String, String> pathvariables = new HashMap<>();
		pathvariables.put("id", id.toString());
		ResponseEntity<Product> response = restTemplateClient.exchange("http://localhost:8091/{id}", HttpMethod.PUT, body, Product.class, pathvariables);
		return response.getBody();
	}

	@Override
	public void delete(Integer id) {
		Map<String, String> pathvariables = new HashMap<>();
		pathvariables.put("id", id.toString());
		restTemplateClient.delete("http://localhost:8091/{id}", pathvariables);
	}

}
