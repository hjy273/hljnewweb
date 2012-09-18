package com.cabletech.linepatrol.specialplan.beans;

public class WatchTaskBean {
	private String id;
	private String startTime;
	private String endTime;
	private Integer space;
	private String patrolGroupId;
	
	private String areaName;
	private String areaId;
	public WatchTaskBean(){
		
	}
	public WatchTaskBean(String id,String startTime,String endTime,String space,String areaId,String areaName){
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.space = Integer.parseInt(space);
		this.areaId = areaId;
		this.areaName = areaName;
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Integer getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = Integer.parseInt(space);
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
	
}
