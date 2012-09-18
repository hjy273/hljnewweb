package com.cabletech.planinfo.bean;

import com.cabletech.commons.base.BaseBean;

public class CopyBean extends BaseBean{

	private String prePlanId; //copy 计划id
	private String startDate; //新计划开始时间
	private String endDate;  //新计划结束时间
	private String executorid;
	private String userID;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPrePlanId() {
		return prePlanId;
	}
	public void setPrePlanId(String prePlanId) {
		this.prePlanId = prePlanId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String toString(){
		return this.prePlanId +","+this.userID +","+this.startDate +","+this.endDate;
		
	}
	public String getExecutorid() {
		return executorid;
	}
	public void setExecutorid(String executorid) {
		this.executorid = executorid;
	}
}
