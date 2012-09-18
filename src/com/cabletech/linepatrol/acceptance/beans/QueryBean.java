package com.cabletech.linepatrol.acceptance.beans;

import com.cabletech.commons.base.BaseBean;

public class QueryBean extends BaseBean {
	private String name;
	private String resourceType;
	private String[] acceptanceState;
	private String begintime;
	private String endtime;
	private String times;
	private String passed;
	private String issueNumber;
	private String currentState;
	private String taskNames;
	private String[] taskStates;
	private String contractorId;
	
	private String processState;

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String[] getAcceptanceState() {
		return acceptanceState;
	}

	public void setAcceptanceState(String[] acceptanceState) {
		this.acceptanceState = acceptanceState;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getPassed() {
		return passed;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
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

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}
}
