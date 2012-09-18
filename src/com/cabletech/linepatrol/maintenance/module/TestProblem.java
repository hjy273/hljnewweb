package com.cabletech.linepatrol.maintenance.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * �����м̶��б�
 */

public class TestProblem extends BaseDomainObject  {
	private String id;
	private String testPlanId;
	private String testCablelineId;
	private String problemDescription;//��������
	private String problemState;
	private String tester;//tester
	private String processComment;//�������˵��
	private Date solveTime;//�������ʱ��
	private String cableName;
	
	private String reason;//ԭ�����
	private String testMethod;//�ƻ������ʩ
	private Date testTime;//�ƻ����ʱ��
	
	
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