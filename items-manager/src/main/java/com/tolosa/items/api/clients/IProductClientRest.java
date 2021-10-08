package com.tolosa.items.api.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tolosa.items.api.models.dto.Product;

@FeignClient(name = "products-manager", url = "localhost:8091")
public interface IProductClientRest {
	
	@GetMapping("/products")
	public List<Product> list();
	
	@GetMapping("/products/{id}")
	public Product detail(@PathVariable Integer id);

}
