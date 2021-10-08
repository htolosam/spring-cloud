package com.tolosa.items.api.models.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tolosa.items.api.models.dto.Item;
import com.tolosa.items.api.models.dto.Product;
import com.tolosa.items.api.models.service.IItemService;


@Service("serviceRestTemplate")
public class ItemServiceImpl implements IItemService {

	@Autowired
	private RestTemplate restTemplateClient;
	
	public ItemServiceImpl(@Autowired RestTemplate restTemplateClient) {
		this.restTemplateClient = restTemplateClient;
	}
	
	@Override
	public List<Item> findAll() {
		List<Product> listProducts = Arrays.asList(restTemplateClient.getForObject("http://localhost:8091/products", Product[].class));
		return listProducts.stream().map(p -> Item.builder().product(p).quantity(1).build()).collect(Collectors.toList());
	}

	@Override
	public Item findById(Integer id, Integer quantity) {
		Map<String, String> pathvariables = new HashMap<>();
		pathvariables.put("id", id.toString());
		Product product = restTemplateClient.getForObject("http://localhost:8091/products/{id}", Product.class, pathvariables);
		return Item.builder().product(product).quantity(quantity).build();
	}

}
