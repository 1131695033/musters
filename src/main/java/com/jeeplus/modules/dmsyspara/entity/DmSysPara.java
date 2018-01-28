/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmsyspara.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 系统参数维护Entity
 * @author qcz
 * @version 2017-11-06
 */
public class DmSysPara extends DataEntity<DmSysPara> {
	
	private static final long serialVersionUID = 1L;
	private String paraKey;		// 参数代码
	private String paraValue;		// 参数值
	private String comments;		// 说明
	
	public DmSysPara() {
		super();
	}

	public DmSysPara(String id){
		super(id);
	}

	@ExcelField(title="参数代码", align=2, sort=1)
	public String getParaKey() {
		return paraKey;
	}

	public void setParaKey(String paraKey) {
		this.paraKey = paraKey;
	}
	
	@ExcelField(title="参数值", align=2, sort=2)
	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	
	@ExcelField(title="说明", align=2, sort=3)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}