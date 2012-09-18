package com.cabletech.linepatrol.maintenance.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 测试计划中的备纤信息表
 */

public class TestPlanLine extends BaseDomainObject  {

	// Fields

	private String id;
	private String testPlanId;
	private String cablelineId;
	private String cablelineTestPort;//中继段计划测试端
	private Date testPlanDate;
	private String testMan;
	private String testRemark;
	private String state;//数据录入状态
	
	private String testManName;
	private String cablelineName;
	private String contractorTime;//交维时间
	
	private int cablePlanTestNum=0;//备纤年计划测试次数
	private int cablePlanedTestNum=0;//备纤已经测试次数

	// Constructors

	/** default constructor */
	public TestPlanLine() {
	}

	/** minimal constructor */
	public TestPlanLine(String id) {
		this.id = id;
	}

	/** full constructor */
	public TestPlanLine(String id, String testPlanId, String cablelineId,
			String cablelineTestPort, Date testPlanDate, String testMan,
			String testRemark) {
		this.id = id;
		this.testPlanId = testPlanId;
		this.cablelineId = cablelineId;
		this.cablelineTestPort = cablelineTestPort;
		this.testPlanDate = testPlanDate;
		this.testMan = testMan;
		this.testRemark = testRemark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestPlanId() {
		return this.testPlanId;
	}

	public void setTestPlanId(String testPlanId) {
		this.testPlanId = testPlanId;
	}

	public String getCablelineId() {
		return this.cablelineId;
	}

	public void setCablelineId(String cablelineId) {
		this.cablelineId = cablelineId;
	}

	public String getCablelineTestPort() {
		return this.cablelineTestPort;
	}

	public void setCablelineTestPort(String cablelineTestPort) {
		this.cablelineTestPort = cablelineTestPort;
	}

	public Date getTestPlanDate() {
		return this.testPlanDate;
	}

	public void setTestPlanDate(Date testPlanDate) {
		this.testPlanDate = testPlanDate;
	}

	public String getTestMan() {
		return this.testMan;
	}

	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}

	public String getTestRemark() {
		return this.testRemark;
	}

	public void setTestRemark(String testRemark) {
		this.testRemark = testRemark;
	}


	public String getTestManName() {
		return testManName;
	}

	public void setTestManName(String testManName) {
		this.testManName = testManName;
	}

	public String getCablelineName() {
		return cablelineName;
	}

	public void setCablelineName(String cablelineName) {
		this.cablelineName = cablelineName;
	}

	public String getContractorTime() {
		return contractorTime;
	}

	public void setContractorTime(String contractorTime) {
		this.contractorTime = contractorTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getCablePlanTestNum() {
		return cablePlanTestNum;
	}

	public void setCablePlanTestNum(int cablePlanTestNum) {
		this.cablePlanTestNum = cablePlanTestNum;
	}

	public int getCablePlanedTestNum() {
		return cablePlanedTestNum;
	}

	public void setCablePlanedTestNum(int cablePlanedTestNum) {
		this.cablePlanedTestNum = cablePlanedTestNum;
	}

}