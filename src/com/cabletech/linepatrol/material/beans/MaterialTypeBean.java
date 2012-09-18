package com.cabletech.linepatrol.material.beans;

import com.cabletech.commons.base.BaseBean;

/**
 * ≤ƒ¡œ¿‡–Õ 
 * 
 */
public class MaterialTypeBean extends BaseBean {
	private Integer tid ;
	private String typeName;
	private String state;
	private String regionID;
	private String remark;
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRegionID() {
		return regionID;
	}
	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
}
