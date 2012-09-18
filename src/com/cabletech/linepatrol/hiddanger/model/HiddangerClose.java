package com.cabletech.linepatrol.hiddanger.model;

import java.util.Date;

public class HiddangerClose {
	private String id;
	private String hiddangerId;
	private Date reliefTime;
	private String reliefReason;
	private String closerId;
	private Date closeTime;
	private String remark;
	private String approveTimes = "0";
	
	public HiddangerClose(){
		closeTime = new Date();
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
	public Date getReliefTime() {
		return reliefTime;
	}
	public void setReliefTime(Date reliefTime) {
		this.reliefTime = reliefTime;
	}
	public String getReliefReason() {
		return reliefReason;
	}
	public void setReliefReason(String reliefReason) {
		this.reliefReason = reliefReason;
	}
	public String getCloserId() {
		return closerId;
	}
	public void setCloserId(String closerId) {
		this.closerId = closerId;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApproveTimes() {
		return approveTimes;
	}
	public void setApproveTimes(String approveTimes) {
		this.approveTimes = approveTimes;
	}
	
}
