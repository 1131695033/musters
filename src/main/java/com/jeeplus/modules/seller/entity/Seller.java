/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.seller.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商铺信息Entity
 * @author zyh
 * @version 2018-03-16
 */
public class Seller extends DataEntity<Seller> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商家名称
	private String profession;		// 行业
	private String address;		// 商家地址
	private String register;		// 注册条件
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String bankNo;		// 银行账户
	private String regionId;		// 所属区域
	
	public Seller() {
		super();
	}

	public Seller(String id){
		super(id);
	}

	@ExcelField(title="商家名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="行业", align=2, sort=2)
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	@ExcelField(title="商家地址", align=2, sort=3)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="注册条件", align=2, sort=4)
	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}
	
	@ExcelField(title="经度", align=2, sort=5)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@ExcelField(title="纬度", align=2, sort=6)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@ExcelField(title="银行账户", align=2, sort=7)
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@ExcelField(title="所属区域", align=2, sort=8)
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
}