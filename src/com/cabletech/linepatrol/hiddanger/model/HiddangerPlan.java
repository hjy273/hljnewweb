package com.cabletech.linepatrol.hiddanger.model;

import java.util.Date;

public class HiddangerPlan {
	private String id;
	private String hiddangerId;
	private String planId;
	private Date createTime;
	
	public HiddangerPlan(){
		createTime = new Date();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHiddangerId() {
		return hiddangerId;
	}
	public void setHiddangerId(String hiddangerId) {
		this.hiddangerId = hiddangerId;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
