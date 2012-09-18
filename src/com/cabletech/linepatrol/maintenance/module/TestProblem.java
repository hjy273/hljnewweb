package com.cabletech.linepatrol.maintenance.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 问题中继段列表
 */

public class TestProblem extends BaseDomainObject  {
	private String id;
	private String testPlanId;
	private String testCablelineId;
	private String problemDescription;//问题描述
	private String problemState;
	private String tester;//tester
	private String processComment;//处理跟踪说明
	private Date solveTime;//解决问题时间
	private String cableName;
	
	private String reason;//原因分析
	private String testMethod;//计划解决措施
	private Date testTime;//计划解决时间
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTestPlanId() {
		return testPlanId;
	}
	public void setTestPlanId(String testPlanId) {
		this.testPlanId = testPlanId;
	}
	public String getTestCablelineId() {
		return testCablelineId;
	}
	public void setTestCablelineId(String testCablelineId) {
		this.testCablelineId = testCablelineId;
	}
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	public String getProblemState() {
		return problemState;
	}
	public void setProblemState(String problemState) {
		this.problemState = problemState;
	}
	public String getTester() {
		return tester;
	}
	public void setTester(String tester) {
		this.tester = tester;
	}
	public String getProcessComment() {
		return processComment;
	}
	public void setProcessComment(String processComment) {
		this.processComment = processComment;
	}
	public Date getSolveTime() {
		return solveTime;
	}
	public void setSolveTime(Date solveTime) {
		this.solveTime = solveTime;
	}
	public String getCableName() {
		return cableName;
	}
	public void setCableName(String cableName) {
		this.cableName = cableName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTestMethod() {
		return testMethod;
	}
	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}
	public Date getTestTime() {
		return testTime;
	}
	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}

}