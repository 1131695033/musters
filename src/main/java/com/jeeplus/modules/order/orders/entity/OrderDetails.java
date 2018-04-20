/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.order.orders.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 订单管理Entity
 * @author zyh
 * @version 2018-03-16
 */
public class OrderDetails extends DataEntity<OrderDetails> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;  //订单id
	private String  productsId;  //商品id
	private String  workerId;  //服务方id
	private Integer  amount;  //数量
	private Double total; //总价
	
	public OrderDetails() {
		super();
	}

	public OrderDetails(String id){
		super(id);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductsId() {
		return productsId;
	}

	public void setProductsId(String productsId) {
		this.productsId = productsId;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	

	
}