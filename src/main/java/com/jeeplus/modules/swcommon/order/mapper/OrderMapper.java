package com.jeeplus.modules.swcommon.order.mapper;

import java.util.Map;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.swcommon.order.entity.Order;

/**
 * 序列
 * @author yu
 * @version 2017-08-15
 */
@MyBatisMapper
public interface OrderMapper extends BaseMapper<Order>{

	
	/**
	 * 序列
	 * @return
	 */
	public String getOrderNo(Map<String, Object> map);
}
