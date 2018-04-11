/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.order.shopcart.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 购物车Entity
 * @author zyh
 * @version 2018-03-16
 */
public class Shopcart extends DataEntity<Shopcart> {
	
	private static final long serialVersionUID = 1L;
	private String memberId;		//会员id
	private String productsId;		// 商品id
	private String amount;		// 数量
	
	public Shopcart() {
		super();
	}

	public Shopcart(String id){
		super(id);
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getProductsId() {
		return productsId;
	}

	public void setProductsId(String productsId) {
		this.productsId = productsId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}


	
}