package com.cabletech.linepatrol.specialplan.module;

import java.util.Date;

public class WatchPlanStat {
	private String id;
	private String specPlanId;
	private String specPlanName;
	private String specPlanType;
	private Date factDate;
	private Date createDate;
	private Integer planPointNumber = 0;
	private Integer planPointTimes = 0;
	private Integer allPlanPointNumbers = 0;
	private Integer planWatchNumber = 0;
	private Integer factPointNumber = 0;
	private Integer factPointTimes = 0;
	private Integer allFactPointNumbers = 0;
	private Integer factWatchNumber = 0;
	private String patrolRatio;
	private String leakRatio;
	private String watchRatio;
	private String planPatrolMileage;
	private String factPatrolMileage;
	private Integer planSublineNumber = 0;
	private Integer noPatrolSublineNumber = 0;
	private Integer noCompleteSublineNumber = 0;
	private Integer completeSublineNumber = 0;
	private Integer planWatchAreaNumber = 0;
	private Integer noWatchAreaNumber = 0;
	private Integer noCompleteAreaNumber = 0;
	private Integer completeAreaNumber = 0;
	private String patrolStatState;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpecPlanId() {
		return specPlanId;
	}
	public void setSpecPlanId(String specPlanId) {
		this.specPlanId = specPlanId;
	}
	public String getSpecPlanName() {
		return specPlanName;
	}
	public void setSpecPlanName(String specPlanName) {
		this.specPlanName = specPlanName;
	}
	public String getSpecPlanType() {
		return specPlanType;
	}
	public void setSpecPlanType(String specPlanType) {
		this.specPlanType = specPlanType;
	}
	public Date getFactDate() {
		return factDate;
	}
	public void setFactDate(Date factDate) {
		this.factDate = factDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getPlanPointNumber() {
		return planPointNumber;
	}
	public void setPlanPointNumber(Integer planPointNumber) {
		this.planPointNumber = planPointNumber;
	}
	public Integer getPlanPointTimes() {
		return planPointTimes;
	}
	public void setPlanPointTimes(Integer planPointTimes) {
		this.planPointTimes = planPointTimes;
	}
	public Integer getPlanWatchNumber() {
		return planWatchNumber;
	}
	public void setPlanWatchNumber(Integer planWatchNumber) {
		this.planWatchNumber = planWatchNumber;
	}
	public Integer getFactPointNumber() {
		return factPointNumber;
	}
	public void setFactPointNumber(Integer factPointNumber) {
		this.factPointNumber = factPointNumber;
	}
	public Integer getFactPointTimes() {
		return factPointTimes;
	}
	public void setFactPointTimes(Integer factPointTimes) {
		this.factPointTimes = factPointTimes;
	}
	public Integer getFactWatchNumber() {
		return factWatchNumber;
	}
	public void setFactWatchNumber(Integer factWatchNumber) {
		this.factWatchNumber = factWatchNumber;
	}
	public String getPatrolRatio() {
		return patrolRatio;
	}
	public void setPatrolRatio(String patrolRatio) {
		this.patrolRatio = patrolRatio;
	}
	public String getWatchRatio() {
		return watchRatio;
	}
	public void setWatchRatio(String watchRatio) {
		this.watchRatio = watchRatio;
	}
	public String getPlanPatrolMileage() {
		return planPatrolMileage;
	}
	public void setPlanPatrolMileage(String planPatrolMileage) {
		this.planPatrolMileage = planPatrolMileage;
	}
	public String getFactPatrolMileage() {
		return factPatrolMileage;
	}
	public void setFactPatrolMileage(String factPatrolMileage) {
		this.factPatrolMileage = factPatrolMileage;
	}
	public Integer getPlanSublineNumber() {
		return planSublineNumber;
	}
	public void setPlanSublineNumber(Integer planSublineNumber) {
		this.planSublineNumber = planSublineNumber;
	}
	public Integer getNoPatrolSublineNumber() {
		return noPatrolSublineNumber;
	}
	public void setNoPatrolSublineNumber(Integer noPatrolSublineNumber) {
		this.noPatrolSublineNumber = noPatrolSublineNumber;
	}
	public Integer getNoCompleteSublineNumber() {
		return noCompleteSublineNumber;
	}
	public void setNoCompleteSublineNumber(Integer noCompleteSublineNumber) {
		this.noCompleteSublineNumber = noCompleteSublineNumber;
	}
	public Integer getCompleteSublineNumber() {
		return completeSublineNumber;
	}
	public void setCompleteSublineNumber(Integer completeSublineNumber) {
		this.completeSublineNumber = completeSublineNumber;
	}
	public Integer getPlanWatchAreaNumber() {
		return planWatchAreaNumber;
	}
	public void setPlanWatchAreaNumber(Integer planWatchAreaNumber) {
		this.planWatchAreaNumber = planWatchAreaNumber;
	}
	public Integer getNoWatchAreaNumber() {
		return noWatchAreaNumber;
	}
	public void setNoWatchAreaNumber(Integer noWatchAreaNumber) {
		this.noWatchAreaNumber = noWatchAreaNumber;
	}
	public Integer getNoCompleteAreaNumber() {
		return noCompleteAreaNumber;
	}
	public void setNoCompleteAreaNumber(Integer noCompleteAreaNumber) {
		this.noCompleteAreaNumber = noCompleteAreaNumber;
	}
	public Integer getCompleteAreaNumber() {
		return completeAreaNumber;
	}
	public void setCompleteAreaNumber(Integer completeAreaNumber) {
		this.completeAreaNumber = completeAreaNumber;
	}
	public String getPatrolStatState() {
		return patrolStatState;
	}
	public void setPatrolStatState(String patrolStatState) {
		this.patrolStatState = patrolStatState;
	}
	public Integer getAllPlanPointNumbers() {
		return allPlanPointNumbers;
	}
	public void setAllPlanPointNumbers(Integer allPlanPointNumbers) {
		this.allPlanPointNumbers = allPlanPointNumbers;
	}
	public Integer getAllFactPointNumbers() {
		return allFactPointNumbers;
	}
	public void setAllFactPointNumbers(Integer allFactPointNumbers) {
		this.allFactPointNumbers = allFactPointNumbers;
	}
	public String getLeakRatio() {
		return leakRatio;
	}
	public void setLeakRatio(String leakRatio) {
		this.leakRatio = leakRatio;
	}
}
