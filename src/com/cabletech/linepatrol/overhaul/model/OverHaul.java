package com.cabletech.linepatrol.overhaul.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cabletech.commons.util.DateUtil;

public class OverHaul {
	public static final String CANCELED_STATE = "999";
	
	private String id;
	private String projectName;
	private Date startTime;
	private Date endTime;
	private String projectCreator;
	private Float budgetFee;
	private String projectRemark;
	private String creator;
	private Date createTime;
	private String state;
	private String processInstanceId;
	private String flowTaskName;
	private String subflowId;
	private String subProcessState;
	private Float useFee;
	private Float remainFee;
	private boolean readOnly = false;
	private List<OverHaulApply> applys = new ArrayList<OverHaulApply>();
	
	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private Date cancelTime;
	private String cancelTimeDis;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getProjectCreator() {
		return projectCreator;
	}
	public void setProjectCreator(String projectCreator) {
		this.projectCreator = projectCreator;
	}
	public Float getBudgetFee() {
		return budgetFee;
	}
	public void setBudgetFee(Float budgetFee) {
		this.budgetFee = budgetFee;
	}
	public String getProjectRemark() {
		return projectRemark;
	}
	public void setProjectRemark(String projectRemark) {
		this.projectRemark = projectRemark;
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
	public String getFlowTaskName() {
		return flowTaskName;
	}
	public void setFlowTaskName(String flowTaskName) {
		this.flowTaskName = flowTaskName;
	}
	public String getSubflowId() {
		return subflowId;
	}
	public void setSubflowId(String subflowId) {
		this.subflowId = subflowId;
	}
	public String getSubProcessState() {
		return subProcessState;
	}
	public void setSubProcessState(String subProcessState) {
		this.subProcessState = subProcessState;
	}
	public Float getUseFee() {
		return useFee;
	}
	public void setUseFee(Float useFee) {
		this.useFee = useFee;
	}
	public Float getRemainFee() {
		return remainFee;
	}
	public void setRemainFee(Float remainFee) {
		this.remainFee = remainFee;
	}
	public List<OverHaulApply> getApplys() {
		return applys;
	}
	public void setApplys(List<OverHaulApply> applys) {
		this.applys = applys;
	}
	public void addApply(OverHaulApply overHaulApply){
		this.applys.add(overHaulApply);
	}
	public void clearApply(){
		this.applys.clear();
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public String getCancelUserId() {
		return cancelUserId;
	}
	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}
	public String getCancelUserName() {
		return cancelUserName;
	}
	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
		this.cancelTimeDis=DateUtil.DateToString(cancelTime, "yyyy/MM/dd HH:mm:ss");
	}
	public String getCancelTimeDis() {
		return cancelTimeDis;
	}
	public void setCancelTimeDis(String cancelTimeDis) {
		this.cancelTimeDis = cancelTimeDis;
	}
}