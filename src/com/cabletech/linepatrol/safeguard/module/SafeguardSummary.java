package com.cabletech.linepatrol.safeguard.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class SafeguardSummary extends BaseDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8708824080791905059L;
	
	private String id;
	private String planId;//保障方案Id
	private int factResponder;//实际出动人员
	private int factRespondingUnit;//实际出动车辆
	private String summary;//保障总结
	private int auditingNum;//审核次数
	private String sumManId;//总结人
	private Date sumDate;//总结时间
	
	private int equipmentNumber;//实际投入设备数
	private Date realStartTime;//实际开始时间
	private Date realEndTime;//实际结束时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public int getFactResponder() {
		return factResponder;
	}

	public void setFactResponder(int factResponder) {
		this.factResponder = factResponder;
	}

	public int getFactRespondingUnit() {
		return factRespondingUnit;
	}

	public void setFactRespondingUnit(int factRespondingUnit) {
		this.factRespondingUnit = factRespondingUnit;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getAuditingNum() {
		return auditingNum;
	}

	public void setAuditingNum(int auditingNum) {
		this.auditingNum = auditingNum;
	}

	public String getSumManId() {
		return sumManId;
	}

	public void setSumManId(String sumManId) {
		this.sumManId = sumManId;
	}

	public Date getSumDate() {
		return sumDate;
	}

	public void setSumDate(Date sumDate) {
		this.sumDate = sumDate;
	}

	public int getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(int equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public Date getRealStartTime() {
		return realStartTime;
	}

	public void setRealStartTime(Date realStartTime) {
		this.realStartTime = realStartTime;
	}

	public Date getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}
}
