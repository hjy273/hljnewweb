package com.cabletech.linepatrol.drill.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class DrillPlanModifyBean extends BaseCommonBean {

	private String id;
	private String planId;// 演练方案Id
	private String prevStartTime;// 原演练开始时间
	private String prevEndTime;// 原演练结束时间
	private String nextStartTime;// 变更后开始时间
	private String nextEndTime;// 变更后结束时间
	private String modifyCase;// 变更原因
	private String modifyMan;// 变更申请人
	private String modifyDate;// 变更申请时间
	
	private String operator;//线维人员的操作，是审核还是转审
	private String approveResult;//审核结果，0 不通过 1 通过 2 转审
	private String approveRemark;//审核评论
	private String approveId;//审核人员Id
	private String approvers;//转审线维人员Id
	private String mobiles;//审核、转审线维人员电话号码
	
	private String conId;//代维公司Id
	private String taskId;//任务Id
	
	private String deadline;//保障总结提交时限
	private String[] readerPhones;//抄送人电话

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPrevStartTime() {
		return prevStartTime;
	}

	public void setPrevStartTime(String prevStartTime) {
		this.prevStartTime = prevStartTime;
	}

	public String getPrevEndTime() {
		return prevEndTime;
	}

	public void setPrevEndTime(String prevEndTime) {
		this.prevEndTime = prevEndTime;
	}

	public String getNextStartTime() {
		return nextStartTime;
	}

	public void setNextStartTime(String nextStartTime) {
		this.nextStartTime = nextStartTime;
	}

	public String getNextEndTime() {
		return nextEndTime;
	}

	public void setNextEndTime(String nextEndTime) {
		this.nextEndTime = nextEndTime;
	}

	public String getModifyCase() {
		return modifyCase;
	}

	public void setModifyCase(String modifyCase) {
		this.modifyCase = modifyCase;
	}

	public String getModifyMan() {
		return modifyMan;
	}

	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}

	public String getApprovers() {
		return approvers;
	}

	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}
}
