package com.tolosa.items.api.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private Product product;
	private Integer quantity;
	
	public Double getTotal() {
		return product.getPrice() * quantity.doubleValue();
	}
}
