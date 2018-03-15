/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.products.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 服务产品Entity
 * @author zyh
 * @version 2018-03-13
 */
public class Products extends DataEntity<Products> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商品名称
	private String classifyId;		// 所属分类id
	private String unit;		// 单位
	private String status;		// 状态
	private String imageId;		// 图片
	private Double price;		// 价格
	
	public Products() {
		super();
	}

	public Products(String id){
		super(id);
	}

	@ExcelField(title="商品名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="所属分类id", align=2, sort=2)
	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	
	@ExcelField(title="单位", align=2, sort=3)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="状态", align=2, sort=4)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="图片", align=2, sort=5)
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	@NotNull(message="价格不能为空")
	@ExcelField(title="价格", align=2, sort=6)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}