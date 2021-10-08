package com.tolosa.items.api.models.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tolosa.items.api.clients.IProductClientRest;
import com.tolosa.items.api.models.dto.Item;
import com.tolosa.items.api.models.service.IItemService;

@Service("serviceFeign")
public class ItemServiceFeign implements IItemService {
	
	private IProductClientRest productClientRest;

	public ItemServiceFeign(@Autowired IProductClientRest productClientRest) {
		this.productClientRest = productClientRest;
	}
	
	@Override
	public List<Item> findAll() {
		return productClientRest.list().stream().map(p -> Item.builder().product(p).quantity(1).build()).collect(Collectors.toList());
	}

	@Override
	public Item findById(Integer id, Integer quantity) {
		return Item.builder().product(productClientRest.detail(id)).quantity(quantity).build();
	}

}
