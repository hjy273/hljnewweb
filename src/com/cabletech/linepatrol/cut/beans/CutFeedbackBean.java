package com.cabletech.linepatrol.cut.beans;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class CutFeedbackBean extends BaseCommonBean {
	/**
	 * 割接反馈
	 */
	private static final long serialVersionUID = -5868620757808220405L;
	private String id;
	private String cutId;// 割接id
	private String isInterrupt;// 是否中断业务
	private float td;// 影响td数量
	private float bs;// 影响基站数量
	private String beginTime;// 实际开始时间
	private String endTime;// 实际结束时间
	private float cutTime;// 割接时长
	private float afterLength;// 割接后长度
	private String flag;// 是否更新或增补标记
	private float beforeWastage;// 割接前损耗
	private float afterWastage;// 割接后损耗
	private float fibercoreNumber;// 纤芯编号
	private String liveChargeMan;// 现场负责人
	private String mobileChargeMan;// 移动负责人
	private String isTimeOut;// 是否超时
	private String timeOutCase;// 超时原因
	private String implementation;// 实施情况
	private String legacyQuestion;// 遗留问题
	private int unapproveNumber;// 未通过次数
	private String cancelCause;//取消流程原因
	private String feedbackType;//反馈类型：0 正常反馈 1 取消流程反馈
	private String creator;//创建人
	private String createTime;//创建时间
	
	//页面数据
	private String operator;//线维人员的操作，是审批还是转审
	private String approveResult;//审批结果，0 不通过 1 通过 2 转审
	private String approveRemark;//审批评论
	private String approveId;//审批人员Id
	private String reader;//抄送人
	private String approvers;//转审线维人员Id
	private String mobiles;//审核、转审线维人员电话号码
	
	private String proposer;//割接申请提交人
	private String[] readerPhones;//抄送人电话号码

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCutId() {
		return cutId;
	}

	public void setCutId(String cutId) {
		this.cutId = cutId;
	}

	public String getIsInterrupt() {
		return isInterrupt;
	}

	public void setIsInterrupt(String isInterrupt) {
		this.isInterrupt = isInterrupt;
	}

	public float getTd() {
		return td;
	}

	public void setTd(float td) {
		this.td = td;
	}

	public float getBs() {
		return bs;
	}

	public void setBs(float bs) {
		this.bs = bs;
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

	public float getCutTime() {
		return cutTime;
	}

	public void setCutTime(float cutTime) {
		this.cutTime = cutTime;
	}

	public float getAfterLength() {
		return afterLength;
	}

	public void setAfterLength(float afterLength) {
		this.afterLength = afterLength;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public float getBeforeWastage() {
		return beforeWastage;
	}

	public void setBeforeWastage(float beforeWastage) {
		this.beforeWastage = beforeWastage;
	}

	public float getAfterWastage() {
		return afterWastage;
	}

	public void setAfterWastage(float afterWastage) {
		this.afterWastage = afterWastage;
	}

	public float getFibercoreNumber() {
		return fibercoreNumber;
	}

	public void setFibercoreNumber(float fibercoreNumber) {
		this.fibercoreNumber = fibercoreNumber;
	}

	public String getLiveChargeMan() {
		return liveChargeMan;
	}

	public void setLiveChargeMan(String liveChargeMan) {
		this.liveChargeMan = liveChargeMan;
	}

	public String getMobileChargeMan() {
		return mobileChargeMan;
	}

	public void setMobileChargeMan(String mobileChargeMan) {
		this.mobileChargeMan = mobileChargeMan;
	}

	public String getIsTimeOut() {
		return isTimeOut;
	}

	public void setIsTimeOut(String isTimeOut) {
		this.isTimeOut = isTimeOut;
	}

	public String getTimeOutCase() {
		return timeOutCase;
	}

	public void setTimeOutCase(String timeOutCase) {
		this.timeOutCase = timeOutCase;
	}

	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}

	public String getLegacyQuestion() {
		return legacyQuestion;
	}

	public void setLegacyQuestion(String legacyQuestion) {
		this.legacyQuestion = legacyQuestion;
	}

	public int getUnapproveNumber() {
		return unapproveNumber;
	}

	public void setUnapproveNumber(int unapproveNumber) {
		this.unapproveNumber = unapproveNumber;
	}

	public String getCancelCause() {
		return cancelCause;
	}

	public void setCancelCause(String cancelCause) {
		this.cancelCause = cancelCause;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
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

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
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
}
