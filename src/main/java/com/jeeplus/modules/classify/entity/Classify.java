/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.classify.entity;


import com.jeeplus.core.persistence.TreeEntity;

/**
 * 服务分类Entity
 * @author zyh
 * @version 2018-03-08
 */
public class Classify extends TreeEntity<Classify> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型编号
	
	private String typeName; //类型名称
	
	
	public Classify() {
		super();
	}

	public Classify(String id){
		super(id);
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public  Classify getParent() {
			return parent;
	}
	
	@Override
	public void setParent(Classify parent) {
		this.parent = parent;
		
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}