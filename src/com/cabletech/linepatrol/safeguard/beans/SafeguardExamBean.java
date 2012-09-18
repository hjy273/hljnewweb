package com.cabletech.linepatrol.safeguard.beans;

import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;

/**
 * ±£’œ»’≥£øº∫À
 * 
 * @author liusq
 * 
 */
public class SafeguardExamBean extends AppraiseDailyBean {

	private static final long serialVersionUID = 8364117403698619736L;

	private String planId;
	private String taskId;
	private String conId;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}
}
