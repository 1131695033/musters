/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.worker.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工人信息Entity
 * @author zyh
 * @version 2018-03-16
 */
public class Worker extends DataEntity<Worker> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 工人姓名
	private String profession;		// 工种
	private String regionId;		// 所属区域
	private String register;		// 注册条件
	private String bankNo;		// 银行账户
	private String address;		// 真实地址
	
	public Worker() {
		super();
	}

	public Worker(String id){
		super(id);
	}

	@ExcelField(title="工人姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="工种", align=2, sort=2)
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	@ExcelField(title="所属区域", align=2, sort=3)
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	@ExcelField(title="注册条件", align=2, sort=4)
	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}
	
	@ExcelField(title="银行账户", align=2, sort=5)
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@ExcelField(title="真实地址", align=2, sort=6)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}