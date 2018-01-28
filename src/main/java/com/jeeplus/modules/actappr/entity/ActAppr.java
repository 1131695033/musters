/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.actappr.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 审批信息Entity
 * @author jh
 * @version 2017-11-01
 */
public class ActAppr extends DataEntity<ActAppr> {
	
	private static final long serialVersionUID = 1L;
	private String appNo;		// 流程编号
	private String actName;		// 审批环节
	private String apprOper;		// 审批人
	private Date apprTime;		// 审批时间
	private String apprRslt;		// 审批结果
	private String apprOpin;		// 审批意见
	
	public ActAppr() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ActAppr(String id){
		super(id);
	}

	@ExcelField(title="流程编号", align=2, sort=1)
	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	
	@ExcelField(title="审批环节", align=2, sort=2)
	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
	
	@ExcelField(title="审批人", align=2, sort=3)
	public String getApprOper() {
		return apprOper;
	}

	public void setApprOper(String apprOper) {
		this.apprOper = apprOper;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审批时间", align=2, sort=4)
	public Date getApprTime() {
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}
	
	@ExcelField(title="审批结果", align=2, sort=5)
	public String getApprRslt() {
		return apprRslt;
	}

	public void setApprRslt(String apprRslt) {
		this.apprRslt = apprRslt;
	}
	
	@ExcelField(title="审批意见", align=2, sort=6)
	public String getApprOpin() {
		return apprOpin;
	}

	public void setApprOpin(String apprOpin) {
		this.apprOpin = apprOpin;
	}
	
}