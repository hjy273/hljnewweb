package com.cabletech.linepatrol.maintenance.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 备纤录入数据信息表
 */

public class TestCableData extends BaseDomainObject  {

	// Fields

	private String id;
	private String testDataId;
	private String testPlanId;
	private String testCablelineId;
	private String factTestPort;//实际测试端
	private Date factTestTime;
	private String testPrincipal;//测试负责人
	private String testMan;
	private String testAddress;
	private String testApparatus;//测试仪表
	private String testMethod;//测试方法
	private String testWavelength;//测试波长
	private String testRefractiveIndex;//所设折射率
	private String testAvgTime;//测试平均时间
	private String testState;
	private Date createTime;

	// Constructors

	/** default constructor */
	public TestCableData() {
	}

	/** minimal constructor */
	public TestCableData(String id) {
		this.id = id;
	}

	/** full constructor */
	public TestCableData(String id, String testDataId, String testPlanId,
			String testCablelineId, String factTestPort, Date factTestTime,
			String testPrincipal, String testMan, String testAddress,
			String testApparatus, String testMethod, String testWavelength,
			String testRefractiveIndex, String testAvgTime, String testState,
			Date createTime) {
		this.id = id;
		this.testDataId = testDataId;
		this.testPlanId = testPlanId;
		this.testCablelineId = testCablelineId;
		this.factTestPort = factTestPort;
		this.factTestTime = factTestTime;
		this.testPrincipal = testPrincipal;
		this.testMan = testMan;
		this.testAddress = testAddress;
		this.testApparatus = testApparatus;
		this.testMethod = testMethod;
		this.testWavelength = testWavelength;
		this.testRefractiveIndex = testRefractiveIndex;
		this.testAvgTime = testAvgTime;
		this.testState = testState;
		this.createTime = createTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestDataId() {
		return this.testDataId;
	}

	public void setTestDataId(String testDataId) {
		this.testDataId = testDataId;
	}

	public String getTestPlanId() {
		return this.testPlanId;
	}

	public void setTestPlanId(String testPlanId) {
		this.testPlanId = testPlanId;
	}

	public String getTestCablelineId() {
		return this.testCablelineId;
	}

	public void setTestCablelineId(String testCablelineId) {
		this.testCablelineId = testCablelineId;
	}

	public String getFactTestPort() {
		return this.factTestPort;
	}

	public void setFactTestPort(String factTestPort) {
		this.factTestPort = factTestPort;
	}

	public Date getFactTestTime() {
		return this.factTestTime;
	}

	public void setFactTestTime(Date factTestTime) {
		this.factTestTime = factTestTime;
	}

	public String getTestPrincipal() {
		return this.testPrincipal;
	}

	public void setTestPrincipal(String testPrincipal) {
		this.testPrincipal = testPrincipal;
	}

	public String getTestMan() {
		return this.testMan;
	}

	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}

	public String getTestAddress() {
		return this.testAddress;
	}

	public void setTestAddress(String testAddress) {
		this.testAddress = testAddress;
	}

	public String getTestApparatus() {
		return this.testApparatus;
	}

	public void setTestApparatus(String testApparatus) {
		this.testApparatus = testApparatus;
	}

	public String getTestMethod() {
		return this.testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

	public String getTestWavelength() {
		return this.testWavelength;
	}

	public void setTestWavelength(String testWavelength) {
		this.testWavelength = testWavelength;
	}

	public String getTestRefractiveIndex() {
		return this.testRefractiveIndex;
	}

	public void setTestRefractiveIndex(String testRefractiveIndex) {
		this.testRefractiveIndex = testRefractiveIndex;
	}

	public String getTestAvgTime() {
		return this.testAvgTime;
	}

	public void setTestAvgTime(String testAvgTime) {
		this.testAvgTime = testAvgTime;
	}

	public String getTestState() {
		return this.testState;
	}

	public void setTestState(String testState) {
		this.testState = testState;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}