package com.cabletech.linepatrol.remedy.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

/**
 * 修缮项
 * 
 */
public class RemedyItemBean extends BaseBean {
	private int tid ;
	private String itemName;
	private String state;//null 表示正常值，1表示已经删除
	private String remark;
	private String regionID;


	
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
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	

}
