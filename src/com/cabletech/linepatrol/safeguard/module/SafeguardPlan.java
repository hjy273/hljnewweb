package com.cabletech.linepatrol.safeguard.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class SafeguardPlan extends BaseDomainObject {
	
	public static final String IS_ALL_NET="1";//全网保障
	public static final String IS_PORTION_NET="0";//非全网保障

	/**
	 * 
	 */
	private static final long serialVersionUID = 2602582630955002004L;
	
	private String id;
	private String safeguardId;//保障任务Id
	private String contractorId;//代维Id
	private Integer planResponder;//计划出动人员数
	private String specialPlanId;//特巡计划Id
	private Integer planRespondingUnit;//计划出动车辆数
	private String remark;//备注
	private int auditingNum;//审核次数
	private String maker;//制定人
	private Date makeDate;//制定时间
	private Date deadline;//总结提交时限
	
	private int equipmentNumber;//计划投入设备数
	
	private String isAllNet;//是否为全网保障 

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

	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public int getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(int equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getIsAllNet() {
		return isAllNet;
	}

	public void setIsAllNet(String isAllNet) {
		this.isAllNet = isAllNet;
	}
	
}
