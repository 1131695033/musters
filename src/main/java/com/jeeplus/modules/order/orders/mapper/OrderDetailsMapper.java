package com.jeeplus.modules.order.orders.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.order.orders.entity.OrderDetails;

@MyBatisMapper
public interface OrderDetailsMapper  extends BaseMapper<OrderDetails>{

	 void deleteByOrder(OrderDetails orderDetails);
}
