package com.jeeplus.modules.swcommon.utils;

import java.util.HashMap;
import java.util.Map;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.swcommon.order.mapper.OrderMapper;

public class OrderUtil {

	
	private static com.jeeplus.modules.swcommon.order.mapper.OrderMapper orderMapper = SpringContextHolder.getBean(OrderMapper.class);
	
	
	/**
	 * 获取对应的序列号值
	 * @param typeid
	 * @return
	 */
	public static String getOrderNo(String seq){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inSnameen", seq);
		orderMapper.getOrderNo(map);
		String inSnameen = map.get("outFormatval").toString();
		return inSnameen;
	}

}
