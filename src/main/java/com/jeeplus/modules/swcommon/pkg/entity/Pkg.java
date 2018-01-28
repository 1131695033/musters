package com.jeeplus.modules.swcommon.pkg.entity;

public class Pkg {
	
	
	private String name;
	private String params;   //参数
	private String outCode; //输出编号
	private String outMsg; //输出信息
	
	public Pkg(String outCode, String outMsg ){
		this.outCode = outCode;
		this.outMsg = outMsg;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getOutCode() {
		return outCode;
	}
	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}
	public String getOutMsg() {
		return outMsg;
	}
	public void setOutMsg(String outMsg) {
		this.outMsg = outMsg;
	}
	
	
	
	

}
