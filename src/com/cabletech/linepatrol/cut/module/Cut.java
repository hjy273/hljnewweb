package com.cabletech.linepatrol.cut.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;

public class Cut extends BaseDomainObject {

	public static final String APPLY = "1";//割接申请
	public static final String APPLY_NO_PASS = "2";//申请未通过
	public static final String FEEDBACK = "3";//反馈
	public static final String FEEDBACK_APPROVE = "4";//反馈审核
	public static final String FEEDBACK_NO_PASS = "5";//反馈审核未通过
	public static final String ACCEPTANCE = "6";//验收
	public static final String ACCEPTANCE_APPROVE = "7";//验收审核
	public static final String ACCEPTANCE_NO_PASS = "8";//验收审核未通过
	public static final String EXAM = "9";
	public static final String FINISH = "0";
	public static final String CANCELED_STATE = "999";//取消
	
	//割接级别
	public static final String LEVEL_NORMAL = "1";
	public static final String LEVEL_EXIGENCE = "2";
	public static final String LEVEL_PREPARE = "3";

	public static final String SAVE = "0";
	public static final String TEMPSAVE = "1";
	/**
	 * 割接申请基类
	 */
	private static final long serialVersionUID = 2580693913445789381L;
	private String id;
	private String workOrderId;// 工单号
	private String cutName;// 割接名称
	private String cutLevel;// 割接级别 1、一般割接 2、紧急割接 3、预割接
	private String builder;// 施工单位
	private String chargeMan;// 区域负责人
	private Date beginTime;// 申请开始时间
	private Date endTime;// 申请结束时间
	private float budget;// 预算金额
	private String isCompensation;// 是否有补偿
	private String compCompany;// 补偿单位
	private float beforeLength;// 割接前长度
	private String cutCause;// 割接原因
	/**
	 * 1、申请待审批 2、审批不通过 3、申请待反馈 4、申请反馈待审批 5、反馈审批不通过 6、申请待验收结算 7、验收结算待审批
	 * 8、验收结算审批不通过 9、考核评估 0、完成
	 */
	private String state;// 割接状态:
	private String cutPlace;// 割接地点
	private int unapproveNumber;// 未通过次数
	private Date replyBeginTime;// 批复开始时间
	private Date replyEndTime;// 批复结束时间
	private String linkNamePhone;// 联系人和电话
	private String proposer;// 申请人
	private Date applyDate;// 申请时间
	private String regionId;// 区域Id
	private String otherImpressCable;// 未交维中继段
	private String useMateral;// 使用材料描述
	private String liveChargeman;// 现场负责人
	private String applyState;// 申请状态：0 申请 1 临时保存

	private Date deadline;// 反馈提交时限

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private Date cancelTime;
	private String cancelTimeDis;

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

	public Date getReplyBeginTime() {
		return replyBeginTime;
	}

	public void setReplyBeginTime(Date replyBeginTime) {
		this.replyBeginTime = replyBeginTime;
	}

	public Date getReplyEndTime() {
		return replyEndTime;
	}

	public void setReplyEndTime(Date replyEndTime) {
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

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getCancelUserId() {
		return cancelUserId;
	}

	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
		this.cancelTimeDis=DateUtil.DateToString(cancelTime, "yyyy/MM/dd HH:mm:ss");
	}

	public String getCancelUserName() {
		return cancelUserName;
	}

	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}

	public String getCancelTimeDis() {
		return cancelTimeDis;
	}

	public void setCancelTimeDis(String cancelTimeDis) {
		this.cancelTimeDis = cancelTimeDis;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}
