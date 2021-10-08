package com.tolosa.items.api.models.service;

import java.util.List;

import com.tolosa.items.api.models.dto.Item;

public interface IItemService {

	public List<Item> findAll();
	
	public Item findById(Integer id, Integer quantity);
}
