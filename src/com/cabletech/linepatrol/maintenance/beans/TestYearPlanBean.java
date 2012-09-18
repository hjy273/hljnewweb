package com.cabletech.linepatrol.maintenance.beans;

import com.cabletech.commons.base.BaseBean;
/**
 * 年计划
 * @author Administrator
 *
 */
public class TestYearPlanBean extends BaseBean{
	private String id;
	private String planName;
	private String year;
	private String testTimes;//默认测试次数
	private String contractorId;//计划 创建代维
	private String creatorId;//计划创建人
	/*private String cableType[]; //光缆级别
	private int beforTestNum[];//变更前测试次数
	private int applyTestNum[];//申请测试次数
	private int changeCableNum[];//变更中继段数量
*/	
	private String cableLevel;//光缆级别
	private int preTestNum;//变更前测试次数
	private int applyNum;//申请测试次数
	private String trunkIds;
	
	private String approver;//一个审核人
	private String mobile;//审核人的电话号码
	private String reads;//多个抄送人
	private String rmobiles;//抄送人号码
	
	
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
	
	public String getTestTimes() {
		return testTimes;
	}
	public void setTestTimes(String testTimes) {
		this.testTimes = testTimes;
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
	
	public String getCableLevel() {
		return cableLevel;
	}
	public void setCableLevel(String cableLevel) {
		this.cableLevel = cableLevel;
	}
	public int getPreTestNum() {
		return preTestNum;
	}
	public void setPreTestNum(int preTestNum) {
		this.preTestNum = preTestNum;
	}
	public int getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getTrunkIds() {
		return trunkIds;
	}
	public void setTrunkIds(String trunkIds) {
		this.trunkIds = trunkIds;
	}
	
}
