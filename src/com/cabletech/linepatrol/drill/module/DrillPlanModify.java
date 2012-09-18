package com.cabletech.linepatrol.drill.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * ��¼��ά�������������¼
 * @author liusq
 *
 */
public class DrillPlanModify extends BaseDomainObject {

	private String id;
	private String planId;// ��������Id
	private Date prevStartTime;// ԭ������ʼʱ��
	private Date prevEndTime;// ԭ��������ʱ��
	private Date nextStartTime;// �����ʼʱ��
	private Date nextEndTime;// ��������ʱ��
	private String modifyCase;// ���ԭ��
	private String modifyMan;// ���������
	private Date modifyDate;// �������ʱ��

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

	public Date getPrevStartTime() {
		return prevStartTime;
	}

	public void setPrevStartTime(Date prevStartTime) {
		this.prevStartTime = prevStartTime;
	}

	public Date getPrevEndTime() {
		return prevEndTime;
	}

	public void setPrevEndTime(Date prevEndTime) {
		this.prevEndTime = prevEndTime;
	}

	public Date getNextStartTime() {
		return nextStartTime;
	}

	public void setNextStartTime(Date nextStartTime) {
		this.nextStartTime = nextStartTime;
	}

	public Date getNextEndTime() {
		return nextEndTime;
	}

	public void setNextEndTime(Date nextEndTime) {
		this.nextEndTime = nextEndTime;
	}

	public String getModifyCase() {
		return modifyCase;
	}

	public void setModifyCase(String modifyCase) {
		this.modifyCase = modifyCase;
	}

	public String getModifyMan() {
		return modifyMan;
	}

	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
