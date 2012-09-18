package com.cabletech.linepatrol.hiddanger.beans;

import com.cabletech.commons.base.BaseBean;

public class CloseBean extends BaseBean {
	private String hiddangerId;
	private String reliefTime;
	private String reliefReason;
	private String remark;
	private String approveTimes;
	
	public String getReliefTime() {
		return reliefTime;
	}
	public void setReliefTime(String reliefTime) {
		this.reliefTime = reliefTime;
	}
	public String getReliefReason() {
		return reliefReason;
	}
	public void setReliefReason(String reliefReason) {
		this.reliefReason = reliefReason;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHiddangerId() {
		return hiddangerId;
	}
	public void setHiddangerId(String hiddangerId) {
		this.hiddangerId = hiddangerId;
	}
	public String getApproveTimes() {
		return approveTimes;
	}
	public void setApproveTimes(String approveTimes) {
		this.approveTimes = approveTimes;
	}
}
