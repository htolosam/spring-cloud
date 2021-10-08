package com.tolosa.items.api.models.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Product {
	private Integer id;
	private String name;
	private Double price;
	private Date createdAt;
}
