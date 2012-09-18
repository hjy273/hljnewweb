package com.cabletech.linepatrol.safeguard.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class SafeguardSummary extends BaseDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8708824080791905059L;
	
	private String id;
	private String planId;//���Ϸ���Id
	private int factResponder;//ʵ�ʳ�����Ա
	private int factRespondingUnit;//ʵ�ʳ�������
	private String summary;//�����ܽ�
	private int auditingNum;//��˴���
	private String sumManId;//�ܽ���
	private Date sumDate;//�ܽ�ʱ��
	
	private int equipmentNumber;//ʵ��Ͷ���豸��
	private Date realStartTime;//ʵ�ʿ�ʼʱ��
	private Date realEndTime;//ʵ�ʽ���ʱ��

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
