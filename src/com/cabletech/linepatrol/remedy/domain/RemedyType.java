package com.cabletech.linepatrol.remedy.domain;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 修缮类别实体
 * 
 */
public class RemedyType extends BaseDomainObject {
	private int tid;
	private int itemID;
	private String typeName;
	private float price;
	private String unit;
	private String state;
	private String remark;

	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
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
