package com.cabletech.linepatrol.maintenance.beans;



import com.cabletech.commons.base.BaseBean;

public class TestPlanQueryStatBean extends BaseBean{
	
	private String planBeginTimeStart;
	private String planBeginTimeEnd;
	private String planEndTimeStart;
	private String planEndTimeEnd;
	private String planName;
	private String createMan;
	private String[] planType;
	private String contractorId;
	private String cableName;
	private String cableLevel;
	private String taskNames;
	private String[] taskStates;
	private String testState;
	
	public String getPlanBeginTimeStart() {
		return planBeginTimeStart;
	}
	public void setPlanBeginTimeStart(String planBeginTimeStart) {
		this.planBeginTimeStart = planBeginTimeStart;
	}
	public String getPlanBeginTimeEnd() {
		return planBeginTimeEnd;
	}
	public void setPlanBeginTimeEnd(String planBeginTimeEnd) {
		this.planBeginTimeEnd = planBeginTimeEnd;
	}
	public String getPlanEndTimeStart() {
		return planEndTimeStart;
	}
	public void setPlanEndTimeStart(String planEndTimeStart) {
		this.planEndTimeStart = planEndTimeStart;
	}
	public String getPlanEndinTimeEnd() {
		return planEndTimeEnd;
	}
	public void setPlanEndinTimeEnd(String planEndTimeEnd) {
		this.planEndTimeEnd = planEndTimeEnd;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getCreateMan() {
		return createMan;
	}
	public void setCreateMan(String createMan) {
		this.createMan = createMan;
	}

	public String[] getPlanType() {
		return planType;
	}
	public void setPlanType(String[] planType) {
		this.planType = planType;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getPlanEndTimeEnd() {
		return planEndTimeEnd;
	}
	public void setPlanEndTimeEnd(String planEndTimeEnd) {
		this.planEndTimeEnd = planEndTimeEnd;
	}
	public String getCableName() {
		return cableName;
	}
	public void setCableName(String cableName) {
		this.cableName = cableName;
	}
	public String getCableLevel() {
		return cableLevel;
	}
	public void setCableLevel(String cableLevel) {
		this.cableLevel = cableLevel;
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
	public String getTestState() {
		return testState;
	}
	public void setTestState(String testState) {
		this.testState = testState;
	}
	
}
