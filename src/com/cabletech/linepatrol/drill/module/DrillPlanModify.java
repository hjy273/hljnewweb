package com.cabletech.linepatrol.drill.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 记录代维演练方案变更记录
 * @author liusq
 *
 */
public class DrillPlanModify extends BaseDomainObject {

	private String id;
	private String planId;// 演练方案Id
	private Date prevStartTime;// 原演练开始时间
	private Date prevEndTime;// 原演练结束时间
	private Date nextStartTime;// 变更后开始时间
	private Date nextEndTime;// 变更后结束时间
	private String modifyCase;// 变更原因
	private String modifyMan;// 变更申请人
	private Date modifyDate;// 变更申请时间

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
