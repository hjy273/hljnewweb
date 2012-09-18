package com.cabletech.linepatrol.drill.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class DrillPlanBean extends BaseCommonBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2932943973035379399L;

	private String id;
	private String taskId;// 任务Id
	private String contractorId;// 代维Id
	private int personNumber;// 计划投入人数
	private int carNumber;// 计划投入车辆
	private String scenario;// 场景
	private String remark;// 备注
	private int unapproveNumber;// 未通过次数
	private String creator;// 创建人
	private String createTime;// 创建时间
	
	private String realBeginTime;//实际演练开始时间
	private String realEndTime;//实际演练结束时间
	private String address;//实际演练地点
	private String equipmentNumber;//计划投入设备数
	
	private String deadline;//总结提交时限
	
	//代维人员自定义演练任务
	private String name;//演练方案名称
	private String beginTime;//演练方案开始时间
	private String endTime;//演练方案结束时间
	private String locale;//演练方案地点
	
	//页面数据
	private String[] delfileid;//删除附件的Ids
	private String operator;//线维人员的操作，是审核还是转审
	private String approveResult;//审核结果，0 不通过 1 通过 2 转审
	private String approveRemark;//审核评论
	private String approveId;//审核人员Id
	private String approvers;//转审线维人员Id
	private String mobile;//审核、转审线维人员电话号码
	private String[] readerPhones;//抄送人电话
	
	private String planState;//演练方案保存时区分标记：当为0时表示保存 1为临时保存

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public int getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(int personNumber) {
		this.personNumber = personNumber;
	}

	public int getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getUnapproveNumber() {
		return unapproveNumber;
	}

	public void setUnapproveNumber(int unapproveNumber) {
		this.unapproveNumber = unapproveNumber;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRealBeginTime() {
		return realBeginTime;
	}

	public void setRealBeginTime(String realBeginTime) {
		this.realBeginTime = realBeginTime;
	}

	public String getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(String realEndTime) {
		this.realEndTime = realEndTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String[] getDelfileid() {
		return delfileid;
	}

	public void setDelfileid(String[] delfileid) {
		this.delfileid = delfileid;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPlanState() {
		return planState;
	}

	public void setPlanState(String planState) {
		this.planState = planState;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}
}
