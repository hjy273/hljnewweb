package com.cabletech.linepatrol.overhaul.beans;

import com.cabletech.commons.base.BaseBean;

public class OverHaulBean extends BaseBean{
	private String projectName;
	private String startTime;
	private String endTime;
	private String projectCreator;
	private Float budgetFee;
	private String projectRemark;
	private String contractors;
	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private String cancelTime;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getProjectCreator() {
		return projectCreator;
	}
	public void setProjectCreator(String projectCreator) {
		this.projectCreator = projectCreator;
	}
	public Float getBudgetFee() {
		return budgetFee;
	}
	public void setBudgetFee(Float budgetFee) {
		this.budgetFee = budgetFee;
	}
	public String getProjectRemark() {
		return projectRemark;
	}
	public void setProjectRemark(String projectRemark) {
		this.projectRemark = projectRemark;
	}
	public String getContractors() {
		return contractors;
	}
	public void setContractors(String contractors) {
		this.contractors = contractors;
	}
	public String getCancelUserId() {
		return cancelUserId;
	}
	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}
	public String getCancelUserName() {
		return cancelUserName;
	}
	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
}