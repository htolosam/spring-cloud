package com.tolosa.products.api.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tolosa.products.api.models.entity.Product;
import com.tolosa.products.api.models.service.IProductService;

@RestController
public class ProductController {
	
	private IProductService productService;
	
	public ProductController(@Autowired IProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public List<Product> list() {
		return productService.findAll();
	}
	
	@GetMapping("/{id}")
	public Product getById(@PathVariable Integer id) throws InterruptedException{
		if(id.equals(10)) {
			throw new IllegalStateException("Producto no encontrado");
		}
		if(id.equals(7)) {
			TimeUnit.SECONDS.sleep(5L);
		}
		
		return productService.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Product product){
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Product product, @PathVariable Integer id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.update(product, id));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		productService.deleteById(id);
	}

}
