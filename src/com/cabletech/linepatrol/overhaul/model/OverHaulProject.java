package com.cabletech.linepatrol.overhaul.model;

public class OverHaulProject {
	private String id;
	private String projectId;
	private String projectName;
	private String projectRefFee;
	private String projectFee;
	private OverHaulApply overHaulApply;
	
	public OverHaulProject(){
		
	}
	public OverHaulProject(String projectId, String projectFee, String projectRefFee){
		this.projectId = projectId;
		this.projectFee = projectFee;
		this.projectRefFee = projectRefFee;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectRefFee() {
		return projectRefFee;
	}
	public void setProjectRefFee(String projectRefFee) {
		this.projectRefFee = projectRefFee;
	}
	public String getProjectFee() {
		return projectFee;
	}
	public void setProjectFee(String projectFee) {
		this.projectFee = projectFee;
	}
	public OverHaulApply getOverHaulApply() {
		return overHaulApply;
	}
	public void setOverHaulApply(OverHaulApply overHaulApply) {
		this.overHaulApply = overHaulApply;
	}
}