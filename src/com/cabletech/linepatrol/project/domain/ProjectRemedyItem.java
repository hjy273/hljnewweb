package com.cabletech.linepatrol.project.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class ProjectRemedyItem extends BaseDomainObject {
	private int tid;
	private String itemName;
	private String state = "1";
	private String remark;
	private String regionID;

	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	public String getRegionID() {
		return regionID;
	}
	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}
}