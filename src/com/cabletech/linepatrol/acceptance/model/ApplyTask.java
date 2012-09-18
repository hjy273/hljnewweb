package com.cabletech.linepatrol.acceptance.model;

import java.util.Date;

import com.cabletech.linepatrol.acceptance.service.AcceptanceConstant;

public class ApplyTask {
	private String id;
	private String applyId;
	private String contractorId;
	private String taketaskMan;
	private Date taketaskTime;
	private String assigner;
	private Date assignTime;
	private String creater;
	private Date createTime;
	private String isComplete;
	
	
	public ApplyTask(){
		
	}
	public ApplyTask(String applyId,String contractorId,String userId){
		this.applyId= applyId;
		this.contractorId = contractorId;
		this.assigner = userId;
		this.assignTime = new Date();
		this.isComplete = AcceptanceConstant.UNCOMPLETED;
	}
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
	public String getTaketaskMan() {
		return taketaskMan;
	}
	public void setTaketaskMan(String taketaskMan) {
		this.taketaskMan = taketaskMan;
	}
	public Date getTaketaskTime() {
		return taketaskTime;
	}
	public void setTaketaskTime(Date taketaskTime) {
		this.taketaskTime = taketaskTime;
	}
	public String getAssigner() {
		return assigner;
	}
	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
}
