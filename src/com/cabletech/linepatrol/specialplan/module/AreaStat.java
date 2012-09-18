package com.cabletech.linepatrol.specialplan.module;

import java.util.Date;

public class AreaStat {
	private String id;
	private String specPlanId;
	private String areaId;
	private String patrolGroupId;
	private Date factDate;
	private Date createDate;
	private String planWatchNumber;
	private String factWatchNumber;
	private String watchRadio;
	
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
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
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
	public String getPlanWatchNumber() {
		return planWatchNumber;
	}
	public void setPlanWatchNumber(String planWatchNumber) {
		this.planWatchNumber = planWatchNumber;
	}
	public String getFactWatchNumber() {
		return factWatchNumber;
	}
	public void setFactWatchNumber(String factWatchNumber) {
		this.factWatchNumber = factWatchNumber;
	}
	public String getWatchRadio() {
		return watchRadio;
	}
	public void setWatchRadio(String watchRadio) {
		this.watchRadio = watchRadio;
	}
}
