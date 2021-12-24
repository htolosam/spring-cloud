package com.tolosa.products.api.models.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tolosa.products.api.models.dao.IProductDao;
import com.tolosa.products.api.models.entity.Product;
import com.tolosa.products.api.models.service.IProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

	private IProductDao productDao;

	public ProductServiceImpl(@Autowired IProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public List<Product> findAll() {
		Date dtFechaActual = new Date();
		log.info("OJO 1: {}", dtFechaActual);
		DateFormat dfLocal = new SimpleDateFormat("kk:mm:ss");
		DateFormat dfLocalHH = new SimpleDateFormat("HH:mm:ss");
		log.info("OJO  format :2 {}", dfLocal.format(dtFechaActual));
		log.info("OJO  format dfLocalHH :2 {}", dfLocalHH.format(dtFechaActual));
		dfLocal.setTimeZone(TimeZone.getTimeZone("Europa/Madrid"));
		dfLocalHH.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
		log.info("OJO: 3 {}", dfLocal.format(dtFechaActual));
		log.info("OJO: dfLocalHH 3 {}", dfLocalHH.format(dtFechaActual));
	    Locale.setDefault(Locale.forLanguageTag("es_CO"));
		return (List<Product>) this.productDao.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return this.productDao.findById(id).get();
	}

	@Override
	public Product save(Product product) {
		return productDao.save(product);
	}
	
	@Override
	public Product update(Product product, Integer id) {
		Product productDb = productDao.findById(id).orElse(null);
		productDb.setName(product.getName());
		productDb.setPrice(product.getPrice());
		return productDao.save(productDb);
	}

	@Override
	public void deleteById(Integer id) {
		productDao.deleteById(id);
	}


}
