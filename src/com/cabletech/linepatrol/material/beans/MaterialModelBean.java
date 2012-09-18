package com.cabletech.linepatrol.material.beans;

import com.cabletech.commons.base.BaseBean;

/**
 * ²ÄÁÏ¹æ¸ñ
 * 
 */
public class MaterialModelBean extends BaseBean {
	private int tid ;
	private int typeID;
	private String modelName;
	private String unit;
	private String state;
	private String remark;
	
	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

	
}
