package com.jeeplus.modules.applet.home.web;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.applet.home.service.HomeService;
import com.jeeplus.modules.classify.entity.Classify;
import com.jeeplus.modules.products.entity.Products;

@Controller
@RequestMapping(value = "${adminPath}/applet/home")
public class HomeContoller {
	@Autowired
	private HomeService homeService;
	
	/**
	 * 首页服务分类列表
	 */
	@ResponseBody
	@RequestMapping(value = "homeclassify")
	public AjaxJson homeclassify( HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		List<Classify> list = homeService.selectHomeClassify();
		if(list != null && list.size() >0){
			body.put("row", list);
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
			j.setMsg("暂未开发服务分类");
		}
		j.setBody(body);
		return j;
	}
	
	/**
	 * 服务分类子列表
	 */
	@ResponseBody
	@RequestMapping(value = "selectInstall")
	public AjaxJson selectInstall(String parentId, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		List<Classify> list = homeService.selectInstall(parentId);
		if(list != null && list.size() >0){
			body.put("row", list);
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
			j.setMsg("未找到服务分类");
		}
		j.setBody(body);
		return j;
	}
	/**
	 * 服务商品列表
	 */
	@ResponseBody
	@RequestMapping(value = "selectProducts")
	public AjaxJson selectProducts(Products products, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		List<Products> list = homeService.selectProducts(products);
		if(list != null && list.size() >0){
			body.put("row", list);
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
			j.setMsg("暂无服务商品");
		}
		j.setBody(body);
		return j;
	}
}
