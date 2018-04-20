/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.order.orders.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.order.orders.entity.OrderDetails;
import com.jeeplus.modules.order.orders.entity.Orders;
import com.jeeplus.modules.order.orders.mapper.OrderDetailsMapper;

/**
 * 订单明细信息Service
 * @author zyh
 * @version 2018-03-16
 */
@Service
@Transactional(readOnly = true)
public class OrderDetailsService extends CrudService<OrderDetailsMapper, OrderDetails> {

	public OrderDetails get(String id) {
		return super.get(id);
	}
	
	public List<OrderDetails> findList(OrderDetails orderDetails) {
		return super.findList(orderDetails);
	}
	
	public Page<OrderDetails> findPage(Page<OrderDetails> page, OrderDetails orderDetails) {
		return super.findPage(page, orderDetails);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderDetails orderDetails) {
		super.save(orderDetails);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderDetails orderDetails) {
		super.delete(orderDetails);
	}
	@Transactional(readOnly = false)
	public void deleteByOrder(OrderDetails orderDetails) {
		mapper.deleteByOrder(orderDetails);
	}
	
}