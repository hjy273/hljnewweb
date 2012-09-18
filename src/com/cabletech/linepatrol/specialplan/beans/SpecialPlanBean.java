package com.cabletech.linepatrol.specialplan.beans;

import com.cabletech.commons.base.BaseBean;



public class SpecialPlanBean extends BaseBean{
	//实体属性
	private String id;
	private String planName;
	private String startDate;
	private String endDate;
	private String patrolGroupId;
	//页面参数
	private String planType;
	private String isApply= "false";
	private String set;
	private String businessId;
	//审核人，抄送人
	private String approver;
	private String reader;
	
	
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		if(startDate != null){
			startDate = startDate.split(" ")[0];
			this.startDate = startDate+ " 00:00:00";
		}else{
			this.startDate ="";
		}
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		if(endDate != null){
			endDate = endDate.split(" ")[0];
			this.endDate = endDate+" 23:59:59";
		}else{
			this.endDate="";
		}
	}
	public String getPatrolGroupId() {
		return patrolGroupId;
	}
	public void setPatrolGroupId(String patrolGroupId) {
		this.patrolGroupId = patrolGroupId;
	}
	
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getIsApply() {
		return isApply;
	}
	public void setIsApply(String isApply) {
		this.isApply = isApply;
	}
	public String getSet() {
		return set;
	}
	public void setSet(String set) {
		this.set = set;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getReader() {
		return reader;
	}
	public void setReader(String reader) {
		this.reader = reader;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
}
