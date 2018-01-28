package com.jeeplus.modules.swcommon.order.entity;

import com.jeeplus.core.persistence.DataEntity;

public class Order extends DataEntity<Order> {
	
	private static final long serialVersionUID = 1L;
	private String sqeName; //序列名

	public String getSqeName() {
		return sqeName;
	}

	public void setSqeName(String sqeName) {
		this.sqeName = sqeName;
	}
	
	

}
