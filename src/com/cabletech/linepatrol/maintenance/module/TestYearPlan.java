package com.cabletech.linepatrol.maintenance.module;


import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
  年计划
 */

public class TestYearPlan extends BaseDomainObject {
	private String id;
	private String planName;
	private String year;
	private String contractorId;
	private String creatorId;
	private Date createTime;
	private String state;
	private String testTimes;//默认测试次数
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	public String getTestTimes() {
		return testTimes;
	}
	public void setTestTimes(String testTimes) {
		this.testTimes = testTimes;
	}
	

}