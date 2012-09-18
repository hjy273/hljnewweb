package com.cabletech.linepatrol.drill.module;

import java.util.*;

import com.cabletech.commons.base.BaseDomainObject;

public class DrillQueryCondition extends BaseDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8770667862981266995L;

	private String name;// 演练名称
	private String[] levels;// 演练级别
	private String beginTime;// 开始时间
	private String endTime;// 结束时间
	private String[] state;// 演练状态
	private String conId;// 代维
	private String taskNames;
	private String[] taskStates;
	
	private String drillState;//是否取消

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getLevels() {
		return levels;
	}

	public void setLevels(String[] levels) {
		this.levels = levels;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String[] getState() {
		return state;
	}

	public void setState(String[] state) {
		this.state = state;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getTaskNames() {
		return taskNames;
	}

	public void setTaskNames(String taskNames) {
		this.taskNames = taskNames;
	}

	public String[] getTaskStates() {
		return taskStates;
	}

	public void setTaskStates(String[] taskStates) {
		this.taskStates = taskStates;
		this.taskNames = "";
		for (int i = 0; taskStates != null && i < taskStates.length; i++) {
			this.taskNames += taskStates[i];
			if (i < taskStates.length - 1) {
				this.taskNames += ",";
			}
		}
	}

	public String getDrillState() {
		return drillState;
	}

	public void setDrillState(String drillState) {
		this.drillState = drillState;
	}
}
