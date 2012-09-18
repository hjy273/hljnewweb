package com.cabletech.linepatrol.specialplan.module;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;

public class SpecialPlan extends BaseDomainObject{
	private static final long serialVersionUID = 1L;
	private String id;
	private String planName;
	private String PlanType;
	private Date startDate;
	private Date endDate;
	private String creator;
	private Date createDate;
	private String regionId;
	private String patrolGroupId;
	private Set<SpecialCircuit> circuitTasks;
	private Set<SpecialWatch> watchTasks;
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
	public String getPlanType() {
		return PlanType;
	}
	public void setPlanType(String planType) {
		PlanType = planType;
	}
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * 计划开始时间-日期为当日的00：00：00
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * 计划开始时间-日期为当日的00：00：00
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = DateUtil.Str2UtilDate(startDate, "yyyy/MM/dd HH:mm:ss");
	}
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * 计划结束时间-日期为当日的23：59：59
	 * @param startDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 计划结束时间-日期为当日的23：59：59
	 * @param startDate
	 */
	public void setEndDate(String endDate) {
		this.endDate = DateUtil.Str2UtilDate(endDate, "yyyy/MM/dd HH:mm:ss");
	}
	public Set<SpecialCircuit> getCircuitTasks() {
		return circuitTasks;
	}
	
	public Set<SpecialWatch> getWatchTasks() {
		return watchTasks;
	}
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public void setCircuitTasks(Set<SpecialCircuit> circuitTasks) {
		this.circuitTasks = circuitTasks;
	}
	public void setWatchTasks(Set<SpecialWatch> watchTasks) {
		this.watchTasks = watchTasks;
	}
	public void addCircuitTask(SpecialCircuit sc){
		if(this.circuitTasks == null){
			this.circuitTasks = new HashSet<SpecialCircuit>();
		}
		this.circuitTasks.add(sc);
	}
	public void addWatchTask(SpecialWatch watchTask){
		if(this.watchTasks == null){
			this.watchTasks = new HashSet<SpecialWatch>();
		}
		this.watchTasks.add(watchTask);
	}
	public String getPatrolGroupId() {
		return patrolGroupId;
	}
	public void setPatrolGroupId(String patrolGroupId) {
		this.patrolGroupId = patrolGroupId;
	}
	
}
