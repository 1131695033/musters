/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.order.orders.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.order.orders.entity.OrderDetails;
import com.jeeplus.modules.order.orders.entity.Orders;
import com.jeeplus.modules.order.orders.mapper.OrdersMapper;

/**
 * 订单信息Service
 * @author zyh
 * @version 2018-03-16
 */
@Service
@Transactional(readOnly = true)
public class OrdersService extends CrudService<OrdersMapper, Orders> {
	@Autowired
	private OrderDetailsService orderDetailsService;
	
	public Orders get(String id) {
		Orders orders = super.get(id);
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setOrderId(id);
		orders.setOrderDetailsList(orderDetailsService.findList(orderDetails));
		return orders;
	}
	
	public List<Orders> findList(Orders Order) {
		return super.findList(Order);
	}
	
	public Page<Orders> findPage(Page<Orders> page, Orders Order) {
		return super.findPage(page, Order);
	}
	
	@Transactional(readOnly = false)
	public void save(Orders Order) {
		super.save(Order);
	}
	
	@Transactional(readOnly = false)
	public void delete(Orders Order) {
		super.delete(Order);
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setOrderId(Order.getId());
		orderDetailsService.deleteByOrder(orderDetails);
	}
	
}