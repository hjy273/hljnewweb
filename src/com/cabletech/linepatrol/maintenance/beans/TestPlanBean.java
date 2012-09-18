package com.cabletech.linepatrol.maintenance.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

public class TestPlanBean extends BaseBean {
	private String id;
	private String contractorId;
	private String creatorId;
	private String testPlanName;
	private String testPlanType;
	private String testBeginDate;
	private String testEndDate;
	private String testPlanRemark;
	private Date createTime;
	private int approveTimes;// 审核次数
	private String testState;
	private String testPid;// 流程实例编号
	private String regionid;

	private String approvers;// 多个审核人
	private String mobiles;// 审核人的电话号码
	private String reads;// 多个抄送人
	private String rmobiles;// 抄送人号码

	private String cablelineId;
	private String testStationId;
	private String cablelineTestPort;
	private String testPlanDate;
	private String testMan;
	private String testRemark;
	private String[] cablelineIds;// 中继段编号数组
	private String isTempSaved;// 是否进行暂存

	// 问题光缆
	private String processComment;
	private String problemState;
	private String reason;// 原因分析
	private String testTime;// 计划解决时间
	private String testMethod;// 解决措施

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private String cancelTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getTestPlanName() {
		return testPlanName;
	}

	public void setTestPlanName(String testPlanName) {
		this.testPlanName = testPlanName;
	}

	public String getTestPlanType() {
		return testPlanType;
	}

	public void setTestPlanType(String testPlanType) {
		this.testPlanType = testPlanType;
	}

	public String getTestBeginDate() {
		return testBeginDate;
	}

	public void setTestBeginDate(String testBeginDate) {
		this.testBeginDate = testBeginDate;
	}

	public String getTestEndDate() {
		return testEndDate;
	}

	public void setTestEndDate(String testEndDate) {
		this.testEndDate = testEndDate;
	}

	public String getTestPlanRemark() {
		return testPlanRemark;
	}

	public void setTestPlanRemark(String testPlanRemark) {
		this.testPlanRemark = testPlanRemark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getApproveTimes() {
		return approveTimes;
	}

	public void setApproveTimes(int approveTimes) {
		this.approveTimes = approveTimes;
	}

	public String getTestState() {
		return testState;
	}

	public void setTestState(String testState) {
		this.testState = testState;
	}

	public String getTestPid() {
		return testPid;
	}

	public void setTestPid(String testPid) {
		this.testPid = testPid;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getCablelineId() {
		return cablelineId;
	}

	public void setCablelineId(String cablelineId) {
		this.cablelineId = cablelineId;
	}

	public String getTestStationId() {
		return testStationId;
	}

	public void setTestStationId(String testStationId) {
		this.testStationId = testStationId;
	}

	public String getCablelineTestPort() {
		return cablelineTestPort;
	}

	public void setCablelineTestPort(String cablelineTestPort) {
		this.cablelineTestPort = cablelineTestPort;
	}

	public String getTestPlanDate() {
		return testPlanDate;
	}

	public void setTestPlanDate(String testPlanDate) {
		this.testPlanDate = testPlanDate;
	}

	public String getTestMan() {
		return testMan;
	}

	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}

	public String getTestRemark() {
		return testRemark;
	}

	public void setTestRemark(String testRemark) {
		this.testRemark = testRemark;
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

	public String getProcessComment() {
		return processComment;
	}

	public void setProcessComment(String processComment) {
		this.processComment = processComment;
	}

	public String getProblemState() {
		return problemState;
	}

	public void setProblemState(String problemState) {
		this.problemState = problemState;
	}

	public String getReads() {
		return reads;
	}

	public void setReads(String reads) {
		this.reads = reads;
	}

	public String getRmobiles() {
		return rmobiles;
	}

	public void setRmobiles(String rmobiles) {
		this.rmobiles = rmobiles;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

	public String[] getCablelineIds() {
		return cablelineIds;
	}

	public void setCablelineIds(String[] cablelineIds) {
		this.cablelineIds = cablelineIds;
	}

	public String getIsTempSaved() {
		return isTempSaved;
	}

	public void setIsTempSaved(String isTempSaved) {
		this.isTempSaved = isTempSaved;
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

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
}
