package com.cabletech.linepatrol.cut.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class CutFeedback extends BaseDomainObject {
	public static final String FEEDBACK = "0";
	public static final String CANCELFLOW = "1";
	/**
	 * 割接反馈基类
	 */
	private static final long serialVersionUID = -639301719735947769L;
	private String id;
	private String cutId;// 割接id
	private String isInterrupt;// 是否中断业务
	private float td;// 影响td数量
	private float bs;// 影响基站数量
	private Date beginTime;// 实际开始时间
	private Date endTime;// 实际结束时间
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
	private Date createTime;//创建时间

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

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
