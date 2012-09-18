package com.cabletech.linepatrol.overhaul.beans;

import com.cabletech.commons.base.BaseBean;

public class OverHaulApplyBean extends BaseBean {
	private String id;
	private String taskId;
	private Float fee = 0.0f;
	private String approver;
	private String reader;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getReader() {
		return reader;
	}
	public void setReader(String reader) {
		this.reader = reader;
	}
	public Float getFee() {
		return fee;
	}
	public void setFee(Float fee) {
		this.fee = fee;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
