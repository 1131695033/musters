/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.order.shopcart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.order.shopcart.entity.Shopcart;
import com.jeeplus.modules.order.shopcart.mapper.ShopcartMapper;

/**
 * 商铺信息Service
 * @author zyh
 * @version 2018-03-16
 */
@Service
@Transactional(readOnly = true)
public class ShopcartService extends CrudService<ShopcartMapper, Shopcart> {

	public Shopcart get(String id) {
		return super.get(id);
	}
	
	public List<Shopcart> findList(Shopcart Shopcart) {
		return super.findList(Shopcart);
	}
	
	public Page<Shopcart> findPage(Page<Shopcart> page, Shopcart Shopcart) {
		return super.findPage(page, Shopcart);
	}
	
	@Transactional(readOnly = false)
	public void save(Shopcart Shopcart) {
		super.save(Shopcart);
	}
	
	@Transactional(readOnly = false)
	public void delete(Shopcart Shopcart) {
		super.delete(Shopcart);
	}
	
}