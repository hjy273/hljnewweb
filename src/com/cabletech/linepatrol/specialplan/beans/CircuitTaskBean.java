package com.cabletech.linepatrol.specialplan.beans;

import java.util.HashMap;
import java.util.Map;

public class CircuitTaskBean implements java.io.Serializable{
	private String id;
	private String taskName;
	private String startTime;
	private String endTime;
	private String lineLevel;
	private Integer patrolNum;
	
	private Map<String,TaskRoute> taskSubline = new HashMap<String,TaskRoute>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public void setPatrolNum(String patrolNum) {
		this.patrolNum = Integer.parseInt(patrolNum);
	}
	public void setPatrolNum(Integer patrolNum){
		this.patrolNum = patrolNum;
	}

	public Map<String, TaskRoute> getTaskSubline() {
		return taskSubline;
	}

	public void setTaskSubline(Map<String, TaskRoute> taskSubline) {
		this.taskSubline = taskSubline;
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
