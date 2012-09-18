package com.cabletech.linepatrol.acceptance.model;

public class Subflow {
	private String id;
	private String applyId;
	private String contractorId;
	private String processInstanceId;
	private String processState;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessState() {
		return processState;
	}
	public void setProcessState(String processState) {
		this.processState = processState;
	}
	@Override
	public String toString() {
		return "Subflow [id=" + id + ", applyId=" + applyId + ", contractorId=" + contractorId + ", processInstanceId="
				+ processInstanceId + ", processState=" + processState + "]";
	}
	
}
