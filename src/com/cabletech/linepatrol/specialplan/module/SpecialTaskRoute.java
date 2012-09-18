package com.cabletech.linepatrol.specialplan.module;

import com.cabletech.commons.base.BaseDomainObject;

public class SpecialTaskRoute extends BaseDomainObject{
	private String id;
//	private String taskId;
	private String sublineId;
	private String patrolGroupId;
	private SpecialCircuit circuitTask;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	public String getTaskId() {
//		return taskId;
//	}
//	public void setTaskId(String taskId) {
//		this.taskId = taskId;
//	}
	public String getSublineId() {
		return sublineId;
	}
	public void setSublineId(String sublineId) {
		this.sublineId = sublineId;
	}
	public String getPatrolGroupId() {
		return patrolGroupId;
	}
	public void setPatrolGroupId(String patrolGroupId) {
		this.patrolGroupId = patrolGroupId;
	}
	public SpecialCircuit getCircuitTask() {
		return circuitTask;
	}
	public void setCircuitTask(SpecialCircuit circuitTask) {
		this.circuitTask = circuitTask;
	}
	
}
