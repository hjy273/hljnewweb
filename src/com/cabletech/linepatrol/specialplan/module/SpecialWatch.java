package com.cabletech.linepatrol.specialplan.module;

import com.cabletech.commons.base.BaseDomainObject;

public class SpecialWatch extends BaseDomainObject{
	private String id;
//	private String planId;
	private String startTime;
	private String endTime;
	private Integer space;
	private String areaName;
	private String patrolGroupId;
	private String areaId;
	private SpecialPlan specialPlan;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	public String getPlanId() {
//		return planId;
//	}
//	public void setPlanId(String planId) {
//		this.planId = planId;
//	}
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
	public Integer getSpace() {
		return space;
	}
	public void setSpace(Integer space) {
		this.space = space;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getPatrolGroupId() {
		return patrolGroupId;
	}
	public void setPatrolGroupId(String patrolGroupId) {
		this.patrolGroupId = patrolGroupId;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public SpecialPlan getSpecialPlan() {
		return specialPlan;
	}
	public void setSpecialPlan(SpecialPlan specialPlan) {
		this.specialPlan = specialPlan;
	}
	
}
