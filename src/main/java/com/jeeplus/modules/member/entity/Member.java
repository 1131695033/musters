/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.member.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会员信息Entity
 * @author zyh
 * @version 2018-03-16
 */
public class Member extends DataEntity<Member> {
	
	private static final long serialVersionUID = 1L;
	private String accountName;		// 账户
	private String password;		// 密码
	private String nickname;		// 昵称
	private String phone;		// 电话
	private String type;		// 会员类型
	private String points;		// 积分
	private String workerId;		// worker_id
	private String sellerId;		// seller_id
	private String openId;		// open_id
	
	public Member() {
		super();
	}

	public Member(String id){
		super(id);
	}

	@ExcelField(title="账户", align=2, sort=1)
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@ExcelField(title="密码", align=2, sort=2)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@ExcelField(title="昵称", align=2, sort=3)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@ExcelField(title="电话", align=2, sort=4)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="会员类型", align=2, sort=5)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="积分", align=2, sort=6)
	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
	@ExcelField(title="worker_id", align=2, sort=7)
	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	
	@ExcelField(title="seller_id", align=2, sort=8)
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	@ExcelField(title="open_id", align=2, sort=9)
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}