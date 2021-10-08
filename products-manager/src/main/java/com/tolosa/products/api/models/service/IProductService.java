package com.tolosa.products.api.models.service;

import java.util.List;

import com.tolosa.products.api.models.entity.Product;

/**
 * Interface para contrato de implementacion del servicio que manejara
 * los productos en el micro
 * @author ho.tolosa
 *
 */
public interface IProductService {

	/**
	 * Listar todos los productos en la base de datos
	 * @return retorna un listado con todos los productos
	 */
	public List<Product> findAll();
	
	/**
	 * Metodo para buscar un producto or medio de su id
	 * @param id id unico de la tabla
	 * @return retorna un producto por medio del id
	 */
	public Product findById(Integer id);
	
}