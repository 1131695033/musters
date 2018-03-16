/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.seller.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.seller.entity.Seller;
import com.jeeplus.modules.seller.mapper.SellerMapper;

/**
 * 商铺信息Service
 * @author zyh
 * @version 2018-03-16
 */
@Service
@Transactional(readOnly = true)
public class SellerService extends CrudService<SellerMapper, Seller> {

	public Seller get(String id) {
		return super.get(id);
	}
	
	public List<Seller> findList(Seller seller) {
		return super.findList(seller);
	}
	
	public Page<Seller> findPage(Page<Seller> page, Seller seller) {
		return super.findPage(page, seller);
	}
	
	@Transactional(readOnly = false)
	public void save(Seller seller) {
		super.save(seller);
	}
	
	@Transactional(readOnly = false)
	public void delete(Seller seller) {
		super.delete(seller);
	}
	
}