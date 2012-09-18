package com.cabletech.linepatrol.overhaul.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OverHaulApply {
	private String id;
	private String taskId;
	private String contractorId;
	private String applicant;
	private Float fee;
	private String creator;
	private Date createTime;
	private String state;
	private String processInstanceId;
	private boolean readOnly = false;
	private Set<OverHaulCut> overHaulCuts = new HashSet<OverHaulCut>();;
	private Set<OverHaulProject> overHaulProjects = new HashSet<OverHaulProject>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public Float getFee() {
		return fee;
	}
	public void setFee(Float fee) {
		this.fee = fee;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Set<OverHaulCut> getOverHaulCuts() {
		return overHaulCuts;
	}
	public void setOverHaulCuts(Set<OverHaulCut> overHaulCuts) {
		this.overHaulCuts = overHaulCuts;
	}
	public void addOverHaulCut(OverHaulCut overHaulCut){
		this.overHaulCuts.add(overHaulCut);
	}
	public void clearOverHaulCut(){
		this.overHaulCuts.clear();
	}
	public Set<OverHaulProject> getOverHaulProjects() {
		return overHaulProjects;
	}
	public void setOverHaulProjects(Set<OverHaulProject> overHaulProjects) {
		this.overHaulProjects = overHaulProjects;
	}
	public void addOverHaulProject(OverHaulProject overHaulProject){
		this.overHaulProjects.add(overHaulProject);
	}
	public void clearOverHaulProject(){
		this.overHaulProjects.clear();
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
}