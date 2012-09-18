package com.cabletech.linepatrol.maintenance.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 测试录入数据信息表
 */

public class TestData extends BaseDomainObject  {
	private String id;
	private String testPlanId;
	private Date recordTime;
	private String recordManId;
	private Date createTime;
	private int approveTimes;//数据录入审核次数
	private String state;

	// Constructors

	/** default constructor */
	public TestData() {
	}

	/** minimal constructor */
	public TestData(String id) {
		this.id = id;
	}

	/** full constructor */
	public TestData(String id, String testPlanId, Date recordTime,
			String recordManId, Date createTime, int approveTimes,
			String state) {
		this.id = id;
		this.testPlanId = testPlanId;
		this.recordTime = recordTime;
		this.recordManId = recordManId;
		this.createTime = createTime;
		this.approveTimes = approveTimes;
		this.state = state;
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

	public Date getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecordManId() {
		return this.recordManId;
	}

	public void setRecordManId(String recordManId) {
		this.recordManId = recordManId;
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}