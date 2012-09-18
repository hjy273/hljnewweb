package com.cabletech.linepatrol.cut.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 割接查询条件类
 * @author Administrator
 *
 */
public class QueryCondition extends BaseDomainObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 950344641998036469L;
	private String contractorId;// 割接代维
	private String[] cutLevels;// 割接级别
	private String[] cableLevels;// 光缆级别
	private String isInterrupt;// 是否中断业务
	private String[] states;// 状态
	private String isTimeOut;// 是否超时
	private String timeType;//时间类型 1、申请时间 2、实际开始时间 3、审批时间
	private String beginTime;// 开始时间
	private String endTime;// 结束时间
	private String taskNames;
	private String[] taskStates;
	private String cancelState;//取消状态

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String[] getCutLevels() {
		return cutLevels;
	}

	public void setCutLevels(String[] cutLevels) {
		this.cutLevels = cutLevels;
	}

	public String[] getCableLevels() {
		return cableLevels;
	}

	public void setCableLevels(String[] cableLevels) {
		this.cableLevels = cableLevels;
	}

	public String getIsInterrupt() {
		return isInterrupt;
	}

	public void setIsInterrupt(String isInterrupt) {
		this.isInterrupt = isInterrupt;
	}

	public String[] getStates() {
		return states;
	}

	public void setStates(String[] states) {
		this.states = states;
	}

	public String getIsTimeOut() {
		return isTimeOut;
	}

	public void setIsTimeOut(String isTimeOut) {
		this.isTimeOut = isTimeOut;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
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

	public String getCancelState() {
		return cancelState;
	}

	public void setCancelState(String cancelState) {
		this.cancelState = cancelState;
	}
}
