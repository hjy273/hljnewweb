package com.cabletech.linepatrol.specialplan.module;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.cabletech.commons.base.BaseDomainObject;

public class SpecialCircuit extends BaseDomainObject{
	private String id;
//	private String planId;
	private String taskName;
	private String lineLevel;
	private Integer patrolNum;
	/**
	 * 巡回时段--开始时间
	 */
	private String startTime;
	/**
	 * 巡回时段--结束时间
	 */
	private String endTime;
	private String patrolGroupId;
	private SpecialPlan specialPlan;
	private Set<SpecialTaskRoute> taskRoutes = new HashSet<SpecialTaskRoute>();
	
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
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getLineLevel() {
		return lineLevel;
	}
	public void setLineLevel(String lineLevel) {
		this.lineLevel = lineLevel;
	}
	public Integer getPatrolNum() {
		return patrolNum;
	}
	public void setPatrolNum(Integer patrolNum) {
		this.patrolNum = patrolNum;
	}
	public SpecialPlan getSpecialPlan() {
		return specialPlan;
	}
	public void setSpecialPlan(SpecialPlan specialPlan) {
		this.specialPlan = specialPlan;
	}
	public Set<SpecialTaskRoute> getTaskRoutes() {
		return taskRoutes;
	}
	public void setTaskRoutes(Set<SpecialTaskRoute> taskRoutes) {
		this.taskRoutes = taskRoutes;
	}
	public String getPatrolGroupId() {
		return patrolGroupId;
	}
	public void setPatrolGroupId(String patrolGroupId) {
		this.patrolGroupId = patrolGroupId;
	}
	public void addTaskRoutes(SpecialTaskRoute taskRoute){
		this.taskRoutes.add(taskRoute);
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
	
}
