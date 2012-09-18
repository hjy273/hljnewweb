package com.cabletech.linepatrol.specialplan.module;

import com.cabletech.commons.base.BaseDomainObject;

public class SpecialTaskPoint extends BaseDomainObject{
	private String id;
//	private String taskId;
	private String pointId;
	private SpecialTaskRoute taskRoute;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SpecialTaskRoute getTaskRoute() {
		return taskRoute;
	}
	public void setTaskRoute(SpecialTaskRoute taskRoute) {
		this.taskRoute = taskRoute;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	
}
