package com.tolosa.products.api.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.tolosa.commons.app.models.entity.Product;


/**
 * Interface para el manejo de jpa para el producto
 * @author ho.tolosa
 *
 */
public interface IProductDao extends CrudRepository<Product, Integer> {

}
