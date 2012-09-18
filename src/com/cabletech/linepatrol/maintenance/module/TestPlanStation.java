package com.cabletech.linepatrol.maintenance.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 测试计划中的接地电阻信息表
 */

public class TestPlanStation extends BaseDomainObject  {

	// Fields

	private String id;
	private String testPlanId;
	private String testStationId;//基站编号
	private Date testPlanDate;
	private String testMan;
	private String testRemark;
	private Date createTime;
	private String state;//数据录入状态
	
	private String testManName;
	private String stationName;

	// Constructors

	/** default constructor */
	public TestPlanStation() {
	}

	/** minimal constructor */
	public TestPlanStation(String id) {
		this.id = id;
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


	public String getTestRemark() {
		return this.testRemark;
	}

	public void setTestRemark(String testRemark) {
		this.testRemark = testRemark;
	}

	public String getTestStationId() {
		return testStationId;
	}

	public void setTestStationId(String testStationId) {
		this.testStationId = testStationId;
	}

	public Date getTestPlanDate() {
		return testPlanDate;
	}

	public void setTestPlanDate(Date testPlanDate) {
		this.testPlanDate = testPlanDate;
	}

	public String getTestMan() {
		return testMan;
	}

	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getTestManName() {
		return testManName;
	}

	public void setTestManName(String testManName) {
		this.testManName = testManName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}