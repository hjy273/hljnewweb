package com.cabletech.linepatrol.overhaul.beans;

import com.cabletech.commons.base.BaseBean;

public class OverHaulQueryBean extends BaseBean {
	private String applyId;
	private String projectName;
	private String minBudgetFee;
	private String maxBudgetFee;
	private String startTime;
	private String endTime;
	private String p;
	private String taskNames;
	private String[] taskStates;
	
	private String state;
	private String contractorId;

	public OverHaulQueryBean() {

	}

	public OverHaulQueryBean(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getMinBudgetFee() {
		return minBudgetFee;
	}

	public void setMinBudgetFee(String minBudgetFee) {
		this.minBudgetFee = minBudgetFee;
	}

	public String getMaxBudgetFee() {
		return maxBudgetFee;
	}

	public void setMaxBudgetFee(String maxBudgetFee) {
		this.maxBudgetFee = maxBudgetFee;
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

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getTaskNames() {
		return taskNames;
	}

	public void setTaskNames(String taskNames) {
		this.taskNames = taskNames;
	}

	public String[] getTaskStates() {
		return taskStates;
	}

	public void setTaskStates(String[] taskStates) {
		this.taskStates = taskStates;
		this.taskNames = "";
		for (int i = 0; taskStates != null && i < taskStates.length; i++) {
			this.taskNames += taskStates[i];
			if (i < taskStates.length - 1) {
				this.taskNames += ",";
			}
		}
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
}
