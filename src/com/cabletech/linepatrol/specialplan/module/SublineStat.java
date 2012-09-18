package com.cabletech.linepatrol.specialplan.module;

import java.util.Date;

public class SublineStat {
	private String id;
	private String specPlanId;
	private String sublineId;
	private String sublineLevel;
	private String patrolGroupId;
	private Date factDate;
	private Date createDate;
	private String planPointhNumber;
	private String planPointhTimes;
	private String factPointhNumber;
	private String factPointhTimes;
	private String patrolRadio;
	private String planPatrolMileage;
	private String factPatrolMileage;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpecPlanId() {
		return specPlanId;
	}
	public void setSpecPlanId(String specPlanId) {
		this.specPlanId = specPlanId;
	}
	public String getSublineId() {
		return sublineId;
	}
	public void setSublineId(String sublineId) {
		this.sublineId = sublineId;
	}
	public String getSublineLevel() {
		return sublineLevel;
	}
	public void setSublineLevel(String sublineLevel) {
		this.sublineLevel = sublineLevel;
	}
	public String getPatrolGroupId() {
		return patrolGroupId;
	}
	public void setPatrolGroupId(String patrolGroupId) {
		this.patrolGroupId = patrolGroupId;
	}
	public Date getFactDate() {
		return factDate;
	}
	public void setFactDate(Date factDate) {
		this.factDate = factDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPlanPointhNumber() {
		return planPointhNumber;
	}
	public void setPlanPointhNumber(String planPointhNumber) {
		this.planPointhNumber = planPointhNumber;
	}
	public String getPlanPointhTimes() {
		return planPointhTimes;
	}
	public void setPlanPointhTimes(String planPointhTimes) {
		this.planPointhTimes = planPointhTimes;
	}
	public String getFactPointhNumber() {
		return factPointhNumber;
	}
	public void setFactPointhNumber(String factPointhNumber) {
		this.factPointhNumber = factPointhNumber;
	}
	public String getFactPointhTimes() {
		return factPointhTimes;
	}
	public void setFactPointhTimes(String factPointhTimes) {
		this.factPointhTimes = factPointhTimes;
	}
	public String getPatrolRadio() {
		return patrolRadio;
	}
	public void setPatrolRadio(String patrolRadio) {
		this.patrolRadio = patrolRadio;
	}
	public String getPlanPatrolMileage() {
		return planPatrolMileage;
	}
	public void setPlanPatrolMileage(String planPatrolMileage) {
		this.planPatrolMileage = planPatrolMileage;
	}
	public String getFactPatrolMileage() {
		return factPatrolMileage;
	}
	public void setFactPatrolMileage(String factPatrolMileage) {
		this.factPatrolMileage = factPatrolMileage;
	}
}