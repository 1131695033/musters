package com.jeeplus.modules.applet.home.mapper;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.applet.home.entity.Home;
import com.jeeplus.modules.classify.entity.Classify;
import com.jeeplus.modules.products.entity.Products;

@MyBatisMapper
public interface HomeMapper  extends BaseMapper<Home> {
	
	List<Classify> selectHomeClassify();
	List<Classify> selectInstall(String parentId);
	List<Products> selectProducts(Products Products);
}
