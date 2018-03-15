/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.products.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.products.entity.Products;
import com.jeeplus.modules.products.mapper.ProductsMapper;

/**
 * 服务产品Service
 * @author zyh
 * @version 2018-03-13
 */
@Service
@Transactional(readOnly = true)
public class ProductsService extends CrudService<ProductsMapper, Products> {

	public Products get(String id) {
		return super.get(id);
	}
	
	public List<Products> findList(Products products) {
		return super.findList(products);
	}
	
	public Page<Products> findPage(Page<Products> page, Products products) {
		return super.findPage(page, products);
	}
	
	@Transactional(readOnly = false)
	public void save(Products products) {
		super.save(products);
	}
	
	@Transactional(readOnly = false)
	public void delete(Products products) {
		super.delete(products);
	}
	
}