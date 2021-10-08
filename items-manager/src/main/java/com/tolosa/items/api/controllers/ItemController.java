package com.tolosa.items.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tolosa.items.api.models.dto.Item;
import com.tolosa.items.api.models.service.IItemService;

@RestController
@RequestMapping("api/items")
public class ItemController {
	
	public IItemService itemService;
	
	public ItemController(@Autowired @Qualifier("serviceFeign") IItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping
	public List<Item> list(){
		return itemService.findAll();
	}
	
	@GetMapping("/{id}/{quantity}")
	public Item detail(@PathVariable Integer id, @PathVariable Integer quantity) {
		return itemService.findById(id, quantity);
	}

}
