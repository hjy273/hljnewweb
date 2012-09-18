package com.cabletech.linepatrol.acceptance.model;

import java.util.Date;

public class PayPipe {
	private String id;
	private String pipeId;
	private String taskId;//指pipetask中的id 而非task中的id
	private Integer acceptanceTimes;
	private Date acceptanceDate;
	private String payTime;
	private String buildUnit;
	private String buildAcceptance;
	private String workUnit;
	private String workAcceptance;
	private String surveillanceUnit;
	private String surveillanceAccept;
	private String maintenceUnit;
	private String maintenceAcceptance;
	private String passed;
	private Date passedTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPipeId() {
		return pipeId;
	}
	public void setPipeId(String pipeId) {
		this.pipeId = pipeId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Integer getAcceptanceTimes() {
		return acceptanceTimes;
	}
	public void setAcceptanceTimes(Integer acceptanceTimes) {
		this.acceptanceTimes = acceptanceTimes;
	}
	public Date getAcceptanceDate() {
		return acceptanceDate;
	}
	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getBuildUnit() {
		return buildUnit;
	}
	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}
	public String getBuildAcceptance() {
		return buildAcceptance;
	}
	public void setBuildAcceptance(String buildAcceptance) {
		this.buildAcceptance = buildAcceptance;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getWorkAcceptance() {
		return workAcceptance;
	}
	public void setWorkAcceptance(String workAcceptance) {
		this.workAcceptance = workAcceptance;
	}
	public String getSurveillanceUnit() {
		return surveillanceUnit;
	}
	public void setSurveillanceUnit(String surveillanceUnit) {
		this.surveillanceUnit = surveillanceUnit;
	}
	public String getSurveillanceAccept() {
		return surveillanceAccept;
	}
	public void setSurveillanceAccept(String surveillanceAccept) {
		this.surveillanceAccept = surveillanceAccept;
	}
	public String getMaintenceUnit() {
		return maintenceUnit;
	}
	public void setMaintenceUnit(String maintenceUnit) {
		this.maintenceUnit = maintenceUnit;
	}
	public String getMaintenceAcceptance() {
		return maintenceAcceptance;
	}
	public void setMaintenceAcceptance(String maintenceAcceptance) {
		this.maintenceAcceptance = maintenceAcceptance;
	}
	public String getPassed() {
		return passed;
	}
	public void setPassed(String passed) {
		this.passed = passed;
	}
	public Date getPassedTime() {
		return passedTime;
	}
	public void setPassedTime(Date passedTime) {
		this.passedTime = passedTime;
	}
}
