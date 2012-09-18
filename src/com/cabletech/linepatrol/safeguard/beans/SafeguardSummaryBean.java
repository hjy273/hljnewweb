package com.cabletech.linepatrol.safeguard.beans;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class SafeguardSummaryBean extends BaseCommonBean {

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
	private String sumDate;//总结时间
	
	private int equipmentNumber;//计划投入设备数
	private String realStartTime;//实际开始时间
	private String realEndTime;//实际结束时间
	
	//页面数据
	private String taskId;
	private String operator;//线维人员的操作，是审核还是转审
	private String approveResult;//审核结果，0 不通过 1 通过 2 转审
	private String approveRemark;//审核评论
	private String approveId;//审核人员Id
	private String approvers;//转审线维人员Id
	private String mobiles;//审核、转审线维人员电话号码
	
	private String conId;//保障计划代维id
	
	private String[] delfileid;//删除附件的Ids
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

	public String getSumDate() {
		return sumDate;
	}

	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}

	public int getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(int equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getRealStartTime() {
		return realStartTime;
	}

	public void setRealStartTime(String realStartTime) {
		this.realStartTime = realStartTime;
	}

	public String getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(String realEndTime) {
		this.realEndTime = realEndTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String[] getDelfileid() {
		return delfileid;
	}

	public void setDelfileid(String[] delfileid) {
		this.delfileid = delfileid;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}
}
