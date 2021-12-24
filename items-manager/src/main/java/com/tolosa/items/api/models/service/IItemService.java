package com.tolosa.items.api.models.service;

import java.util.List;

import com.tolosa.items.api.models.dto.Item;
import com.tolosa.items.api.models.dto.Product;

/**
 * Interface para servicio items
 * @author ho.tolosa
 *
 */
public interface IItemService {

	/**
	 * Listar todos los items
	 * @return
	 */
	public List<Item> findAll();
	
	/**
	 * Buscar item por id
	 * @param id
	 * @param quantity
	 * @return
	 */
	public Item findById(Integer id, Integer quantity);
	
	/**
	 * Crear producto
	 * @param product
	 * @return
	 */
	public Product save(Product product);
	
	/**
	 * Actualizar producto
	 * @param product
	 * @param id
	 * @return
	 */
	public Product update(Product product, Integer id);
	
	/**
	 * Eliminar producto
	 * @param id
	 */
	public void delete(Integer id);
}
