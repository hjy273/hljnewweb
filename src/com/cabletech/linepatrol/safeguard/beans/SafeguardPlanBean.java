package com.cabletech.linepatrol.safeguard.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class SafeguardPlanBean extends BaseCommonBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2602582630955002004L;
	
	private String id;
	private String safeguardId;
	private String contractorId;
	private Integer planResponder;
	private String specialPlanId;
	private Integer planRespondingUnit;
	private String remark;
	private int auditingNum;
	private String maker;
	private String makeDate;
	private String isAllNet;//是否为全网保障
	private int equipmentNumber;//计划投入设备数
	private String deadline;//
	
	//页面数据
	private String[] delfileid;//删除附件的Ids
	
	private String operator;//线维人员的操作，是审核还是转审
	private String approveResult;//审核结果，0 不通过 1 通过 2 转审
	private String approveRemark;//审核评论
	private String approveId;//审核人员Id
	private String approvers;//转审线维人员Id
	private String mobiles;//审核、转审线维人员电话号码
	
	private String startDate;//开始时间
	private String endDate;//结束时间
	
	private String[] readerPhones;//抄送人电话

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSafeguardId() {
		return safeguardId;
	}

	public void setSafeguardId(String safeguardId) {
		this.safeguardId = safeguardId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public Integer getPlanResponder() {
		return planResponder;
	}

	public void setPlanResponder(Integer planResponder) {
		this.planResponder = planResponder;
	}

	public String getSpecialPlanId() {
		return specialPlanId;
	}

	public void setSpecialPlanId(String specialPlanId) {
		this.specialPlanId = specialPlanId;
	}

	public Integer getPlanRespondingUnit() {
		return planRespondingUnit;
	}

	public void setPlanRespondingUnit(Integer planRespondingUnit) {
		this.planRespondingUnit = planRespondingUnit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getAuditingNum() {
		return auditingNum;
	}

	public void setAuditingNum(int auditingNum) {
		this.auditingNum = auditingNum;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}

	public int getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(int equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String[] getDelfileid() {
		return delfileid;
	}

	public void setDelfileid(String[] delfileid) {
		this.delfileid = delfileid;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}

	public String getIsAllNet() {
		return isAllNet;
	}

	public void setIsAllNet(String isAllNet) {
		this.isAllNet = isAllNet;
	}
	
}
