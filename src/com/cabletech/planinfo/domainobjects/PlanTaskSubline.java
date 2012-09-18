package com.cabletech.planinfo.domainobjects;

public class PlanTaskSubline {
	private String ID;

	private String sublineid;

	private String taskid;
	private String name;

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getSublineid() {
		return sublineid;
	}

	public void setSublineid(String sublineid) {
		this.sublineid = sublineid;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
