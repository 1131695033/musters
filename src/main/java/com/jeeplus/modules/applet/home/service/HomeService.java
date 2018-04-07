package com.jeeplus.modules.applet.home.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.applet.home.entity.Home;
import com.jeeplus.modules.applet.home.mapper.HomeMapper;
import com.jeeplus.modules.classify.entity.Classify;
import com.jeeplus.modules.products.entity.Products;

@Service
@Transactional(readOnly = true)
public class HomeService extends CrudService<HomeMapper, Home> {
	/**
	 * 首页一级服务分类列表
	 * @return
	 */
	public List<Classify> selectHomeClassify(){
		return mapper.selectHomeClassify();
	}
	/**
	 * 首页服务分类子级列表
	 * @return
	 */
	public List<Classify> selectInstall(String parentId){
		return mapper.selectInstall(parentId);
	}
	/**
	 * 首页服务分类子级列表
	 * @return
	 */
	public List<Products> selectProducts(Products products){
		return mapper.selectProducts(products);
	}
}
