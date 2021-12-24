package com.tolosa.items.api.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tolosa.items.api.models.dto.Product;

@FeignClient(name = "products-manager", url = "http://localhost:8091")
public interface IProductClientRest {
	
	@GetMapping
	public List<Product> list();
	
	@GetMapping("/{id}")
	public Product detail(@PathVariable Integer id);
	
	@PostMapping
	public Product create(@RequestBody Product product);
	
	@PutMapping("/{id}")
	public Product update(@RequestBody Product product, @PathVariable Integer id);
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Integer id);

}
