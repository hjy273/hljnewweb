package com.cabletech.linepatrol.cut.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class CutBean extends BaseCommonBean {
	/**
	 * 割接申请
	 */
	
	public final static String WATI_REPLY="1";
	public final static String REPLY_WAIT_APPROVE="4";
	public final static String REPLY_WATI_EXAM="7";
	
	private static final long serialVersionUID = -8077567416655860790L;
	
	private String id;
	private String workOrderId;// 工单号
	private String cutName;// 割接名称
	private String cutLevel;//割接级别
	private String builder;// 施工单位
	private String chargeMan;// 区域负责人
	private String beginTime;// 申请开始时间
	private String endTime;// 申请结束时间
	private float budget;// 预算金额
	private String isCompensation;// 是否有补偿
	private String compCompany;// 补偿单位
	private float beforeLength;// 割接前长度
	private String cutCause;// 割接原因
	private String state;// 割接状态:1、申请待审批 2、审批不通过 3、申请待反馈 4、申请反馈待审批 5、反馈审批不通过 6、申请待验收结算 7、验收结算待审批 8、验收结算审批不通过 9、考核评估 0、完成
	private String cutPlace;// 割接地点
	private int unapproveNumber;// 未通过次数
	private String replyBeginTime;// 批复开始时间
	private String replyEndTime;// 批复结束时间
	private String linkNamePhone;//联系人和电话
	private String proposer;//申请人
	private String applyDate;//申请时间
	private String regionId;//区域Id
	private String otherImpressCable;//未交维中继段
	private String useMateral;//使用材料描述
	private String liveChargeman;//现场负责人
	private String applyState;//申请状态：0 申请  1 临时保存
	private String deadline;//反馈提交时限
	
	//页面数据
	private String operator;//线维人员的操作，是审批还是转审
	private String approveResult;//审批结果，0 不通过 1 通过 2 转审
	private String approveRemark;//审批评论
	private String approveId;//审批人员Id
	private String reader;//抄送人
	private String approvers;//转审线维人员Id
	private String mobile;//审核、转审线维人员电话号码
	private String[] readerPhones;//抄送人电话号码

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private String cancelTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getCutName() {
		return cutName;
	}

	public void setCutName(String cutName) {
		this.cutName = cutName;
	}
	
	public String getCutLevel() {
		return cutLevel;
	}

	public void setCutLevel(String cutLevel) {
		this.cutLevel = cutLevel;
	}

	public String getBuilder() {
		return builder;
	}

	public void setBuilder(String builder) {
		this.builder = builder;
	}

	public String getChargeMan() {
		return chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
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

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}

	public String getIsCompensation() {
		return isCompensation;
	}

	public void setIsCompensation(String isCompensation) {
		this.isCompensation = isCompensation;
	}

	public String getCompCompany() {
		return compCompany;
	}

	public void setCompCompany(String compCompany) {
		this.compCompany = compCompany;
	}

	public float getBeforeLength() {
		return beforeLength;
	}

	public void setBeforeLength(float beforeLength) {
		this.beforeLength = beforeLength;
	}

	public String getCutCause() {
		return cutCause;
	}

	public void setCutCause(String cutCause) {
		this.cutCause = cutCause;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCutPlace() {
		return cutPlace;
	}

	public void setCutPlace(String cutPlace) {
		this.cutPlace = cutPlace;
	}

	public int getUnapproveNumber() {
		return unapproveNumber;
	}

	public void setUnapproveNumber(int unapproveNumber) {
		this.unapproveNumber = unapproveNumber;
	}

	public String getReplyBeginTime() {
		return replyBeginTime;
	}

	public void setReplyBeginTime(String replyBeginTime) {
		this.replyBeginTime = replyBeginTime;
	}

	public String getReplyEndTime() {
		return replyEndTime;
	}

	public void setReplyEndTime(String replyEndTime) {
		this.replyEndTime = replyEndTime;
	}

	public String getLinkNamePhone() {
		return linkNamePhone;
	}

	public void setLinkNamePhone(String linkNamePhone) {
		this.linkNamePhone = linkNamePhone;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getOtherImpressCable() {
		return otherImpressCable;
	}

	public void setOtherImpressCable(String otherImpressCable) {
		this.otherImpressCable = otherImpressCable;
	}

	public String getUseMateral() {
		return useMateral;
	}

	public void setUseMateral(String useMateral) {
		this.useMateral = useMateral;
	}

	public String getLiveChargeman() {
		return liveChargeman;
	}

	public void setLiveChargeman(String liveChargeman) {
		this.liveChargeman = liveChargeman;
	}

	public String getApplyState() {
		return applyState;
	}

	public void setApplyState(String applyState) {
		this.applyState = applyState;
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

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
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

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getCancelUserId() {
		return cancelUserId;
	}

	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}

	public String getCancelUserName() {
		return cancelUserName;
	}

	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}
