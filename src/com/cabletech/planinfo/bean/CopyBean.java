package com.cabletech.planinfo.bean;

import com.cabletech.commons.base.BaseBean;

public class CopyBean extends BaseBean{

	private String prePlanId; //copy �ƻ�id
	private String startDate; //�¼ƻ���ʼʱ��
	private String endDate;  //�¼ƻ�����ʱ��
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
