package com.cabletech.planinfo.domainobjects;

public class StencilTaskPoint {
	private String ID;

	private String taskid;

	private String pointid;
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getPointid() {
		return pointid;
	}
	public void setPointid(String pointid) {
		this.pointid = pointid;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
}
