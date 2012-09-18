package com.cabletech.linepatrol.maintenance.module;


import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;

/**
 *测试计划信息表
 */

public class TestPlan extends BaseDomainObject {
	public static final String CANCELED_STATE = "999";
	
	private String id;
	private String contractorId;
	private String creatorId;
	private String testPlanName;
	private String testPlanType;
	private Date testBeginDate;
	private Date testEndDate;
	private String testPlanRemark;
	private Date createTime;
	private int approveTimes;//审核次数
	private String testState;
	private String testPid;//流程实例编号
	private String regionid;
	
	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private Date cancelTime;
	private String cancelTimeDis;

	// Constructors

	/** default constructor */
	public TestPlan() {
	}

	/** minimal constructor */
	public TestPlan(String id) {
		this.id = id;
	}

	/** full constructor */
	public TestPlan(String id, String contractorId, String creatorId,
			String testPlanName, String testPlanType, Date testBeginDate,
			Date testEndDate, String testPlanRemark, Date createTime,
			int approveTimes, String testState, String testPid,
			String regionid) {
		this.id = id;
		this.contractorId = contractorId;
		this.creatorId = creatorId;
		this.testPlanName = testPlanName;
		this.testPlanType = testPlanType;
		this.testBeginDate = testBeginDate;
		this.testEndDate = testEndDate;
		this.testPlanRemark = testPlanRemark;
		this.createTime = createTime;
		this.approveTimes = approveTimes;
		this.testState = testState;
		this.testPid = testPid;
		this.regionid = regionid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractorId() {
		return this.contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getTestPlanName() {
		return this.testPlanName;
	}

	public void setTestPlanName(String testPlanName) {
		this.testPlanName = testPlanName;
	}

	public String getTestPlanType() {
		return this.testPlanType;
	}

	public void setTestPlanType(String testPlanType) {
		this.testPlanType = testPlanType;
	}

	public Date getTestBeginDate() {
		return this.testBeginDate;
	}

	public void setTestBeginDate(Date testBeginDate) {
		this.testBeginDate = testBeginDate;
	}

	public Date getTestEndDate() {
		return this.testEndDate;
	}

	public void setTestEndDate(Date testEndDate) {
		this.testEndDate = testEndDate;
	}

	public String getTestPlanRemark() {
		return this.testPlanRemark;
	}

	public void setTestPlanRemark(String testPlanRemark) {
		this.testPlanRemark = testPlanRemark;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getApproveTimes() {
		return this.approveTimes;
	}

	public void setApproveTimes(int approveTimes) {
		this.approveTimes = approveTimes;
	}

	public String getTestState() {
		return this.testState;
	}

	public void setTestState(String testState) {
		this.testState = testState;
	}

	public String getTestPid() {
		return this.testPid;
	}

	public void setTestPid(String testPid) {
		this.testPid = testPid;
	}

	public String getRegionid() {
		return this.regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
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