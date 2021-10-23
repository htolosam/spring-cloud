package com.tolosa.products.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public Product getById(@PathVariable Integer id) {
		return productService.findById(id);
	}

}
