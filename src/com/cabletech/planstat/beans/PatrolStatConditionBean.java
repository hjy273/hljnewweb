package com.cabletech.planstat.beans;

import com.cabletech.commons.base.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.utils.*;

public class PatrolStatConditionBean extends BaseBean {
	public PatrolStatConditionBean() {
		endYear = DateUtil.getNowYearStr();
	}
	private String patrolID;
	private String patrolName;
	private String beginYear; // 年份
	private String beginMonth; // 月份
	private String endYear;
	private String endMonth;
	private String conID;
	private String conName;
	private String mobileID;
	private String mobileName;
	private String lineID;
	private String lineName;
	private String sublineID;
	private String sublineName;
	private String regionId;

	public String getPatrolID() {
		return patrolID;
	}

	public void setPatrolID(String patrolID) {
		this.patrolID = patrolID;
	}

	public String getPatrolName() {
		return patrolName;
	}

	public void setPatrolName(String patrolName) {
		this.patrolName = patrolName;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public String getConID() {
		return conID;
	}

	public String getConName() {
		return conName;
	}

	public String getMobileID() {
		return mobileID;
	}

	public String getMobileName() {
		return mobileName;
	}

	public String getLineID() {
		return lineID;
	}

	public String getLineName() {
		return lineName;
	}

	public String getSublineID() {
		return sublineID;
	}

	public String getSublineName() {
		return sublineName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public void setConID(String conID) {
		this.conID = conID;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public void setMobileID(String mobileID) {
		this.mobileID = mobileID;
	}

	public void setMobileName(String mobileName) {
		this.mobileName = mobileName;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public void setSublineID(String sublineID) {
		this.sublineID = sublineID;
	}

	public void setSublineName(String sublineName) {
		this.sublineName = sublineName;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getBeginYear() {
		return beginYear;
	}

	public void setBeginYear(String beginYear) {
		this.beginYear = beginYear;
	}

	public String getBeginMonth() {
		return beginMonth;
	}

	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}

}
