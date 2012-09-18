package com.cabletech.linepatrol.safeguard.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class SpecialEndPlanBean extends BaseCommonBean {

	private String id;
	private String planId;// 特巡计划ID
	private String endType;// 结束类型
	private String prevEndDate;// 终止前计划结束时间
	private String endDate;// 终止计划结束时间
	private String reason;// 终止原因
	private String creater;// 创建人
	private String createTime;// 创建时间
	
	private String approveId;//审核人员Id
	private String approvers;//转审线维人员Id
	private String mobiles;//审核、转审线维人员电话号码
	private String operator;//线维人员的操作，是审核还是转审
	private String approveResult;//审核结果，0 不通过 1 通过 2 转审
	private String approveRemark;//审核评论
	private String[] readerPhones;//抄送人电话

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

	public String getEndType() {
		return endType;
	}

	public void setEndType(String endType) {
		this.endType = endType;
	}

	public String getPrevEndDate() {
		return prevEndDate;
	}

	public void setPrevEndDate(String prevEndDate) {
		this.prevEndDate = prevEndDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}
}
