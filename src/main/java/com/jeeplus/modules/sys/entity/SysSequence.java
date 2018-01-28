/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 序列号管理Entity
 * @author maldinichen
 * @version 2017-08-16
 */
public class SysSequence extends DataEntity<SysSequence> {
	
	private static final long serialVersionUID = 1L;
	private String snameEn;		// 标识
	private String snameCn;		// 名称
	private String minval;		// 最小值
	private String maxval;		// 最大值
	private String stepby;		// 步长
	private String prefix;		// 前缀
	private String bgnvalue;		// 起始值
	private String suffix;		// 后缀
	private String curvalue;		// 当前值
	private String connvar;		// 连接符
	private String iscircul;		// 是否循环
	private String isleftpad;		// 是否左补足
	private String leftpadnums;		// 左补足位数
	private String formatval;		// 当前格式化值
	
	
	public SysSequence() {
		super();
	}

	public SysSequence(String id){
		super(id);
	}

	@ExcelField(title="标识", align=2, sort=1)
	public String getSnameEn() {
		return snameEn;
	}

	public void setSnameEn(String snameEn) {
		this.snameEn = snameEn;
	}
	
	@ExcelField(title="名称", align=2, sort=2)
	public String getSnameCn() {
		return snameCn;
	}

	public void setSnameCn(String snameCn) {
		this.snameCn = snameCn;
	}
	
	@ExcelField(title="最小值", align=2, sort=3)
	public String getMinval() {
		return minval;
	}

	public void setMinval(String minval) {
		this.minval = minval;
	}
	
	@ExcelField(title="最大值", align=2, sort=4)
	public String getMaxval() {
		return maxval;
	}

	public void setMaxval(String maxval) {
		this.maxval = maxval;
	}
	
	@ExcelField(title="步长", align=2, sort=5)
	public String getStepby() {
		return stepby;
	}

	public void setStepby(String stepby) {
		this.stepby = stepby;
	}
	
	@ExcelField(title="前缀", align=2, sort=6)
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	@ExcelField(title="起始值", align=2, sort=7)
	public String getBgnvalue() {
		return bgnvalue;
	}

	public void setBgnvalue(String bgnvalue) {
		this.bgnvalue = bgnvalue;
	}
	
	@ExcelField(title="后缀", align=2, sort=8)
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	@ExcelField(title="当前值", align=2, sort=9)
	public String getCurvalue() {
		return curvalue;
	}

	public void setCurvalue(String curvalue) {
		this.curvalue = curvalue;
	}
	
	@ExcelField(title="连接符", align=2, sort=10)
	public String getConnvar() {
		return connvar;
	}

	public void setConnvar(String connvar) {
		this.connvar = connvar;
	}
	
	@ExcelField(title="是否循环", dictType="yes_no", align=2, sort=11)
	public String getIscircul() {
		return iscircul;
	}

	public void setIscircul(String iscircul) {
		this.iscircul = iscircul;
	}
	
	@ExcelField(title="是否左补足", dictType="yes_no", align=2, sort=12)
	public String getIsleftpad() {
		return isleftpad;
	}

	public void setIsleftpad(String isleftpad) {
		this.isleftpad = isleftpad;
	}
	
	@ExcelField(title="左补足位数", align=2, sort=13)
	public String getLeftpadnums() {
		return leftpadnums;
	}

	public void setLeftpadnums(String leftpadnums) {
		this.leftpadnums = leftpadnums;
	}
	
	@ExcelField(title="当前格式化值", align=2, sort=14)
	public String getFormatval() {
		return formatval;
	}

	public void setFormatval(String formatval) {
		this.formatval = formatval;
	}
	
}