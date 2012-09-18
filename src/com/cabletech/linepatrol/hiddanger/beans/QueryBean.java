package com.cabletech.linepatrol.hiddanger.beans;

import com.cabletech.commons.base.BaseBean;

public class QueryBean extends BaseBean {
	private String type;
	private String code;
	private String name;
	private String begintime;
	private String endtime;
	private String hiddangerLevel;
	private String hiddangerState;
	private String [] hiddangerLevels;
	private String [] hiddangerStates;
	private String taskNames;
	private String[] taskStates;
	
	private String treatDepartment;//隐患处理单位
	private String hideDangerState;//取消状态  999为取消
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getHiddangerLevel() {
		return hiddangerLevel;
	}
	public void setHiddangerLevel(String hiddangerLevel) {
		this.hiddangerLevel = hiddangerLevel;
	}
	public String getHiddangerState() {
		return hiddangerState;
	}
	public void setHiddangerState(String hiddangerState) {
		this.hiddangerState = hiddangerState;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	public String[] getHiddangerLevels() {
		return hiddangerLevels;
	}
	public void setHiddangerLevels(String[] hiddangerLevels) {
		if (hiddangerLevels != null) {
			String level = "'"+hiddangerLevels[0]+"'";
			for (int i = 1; i < hiddangerLevels.length; i++) {
				level += ",'" + hiddangerLevels[i]+"'";
			}
			this.setHiddangerLevel(level);
		}
		this.hiddangerLevels = hiddangerLevels;
	}
	public String[] getHiddangerStates() {
		return hiddangerStates;
	}
	public void setHiddangerStates(String[] hiddangerStates) {
		if (hiddangerStates != null) {
			String state = hiddangerStates[0];
			for (int i = 1; i < hiddangerStates.length; i++) {
				state += "," + hiddangerStates[i];
			}
			this.setHiddangerState(state);
		}
		this.hiddangerStates = hiddangerStates;
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
	public String getTreatDepartment() {
		return treatDepartment;
	}
	public void setTreatDepartment(String treatDepartment) {
		this.treatDepartment = treatDepartment;
	}
	public String getHideDangerState() {
		return hideDangerState;
	}
	public void setHideDangerState(String hideDangerState) {
		this.hideDangerState = hideDangerState;
	}
}
