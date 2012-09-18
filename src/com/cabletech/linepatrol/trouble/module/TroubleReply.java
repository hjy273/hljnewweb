package com.cabletech.linepatrol.trouble.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleReply entity. @author MyEclipse Persistence Tools
 */

public class TroubleReply extends BaseDomainObject {

	// Fields

	private String id;
	private String troubleId;// ���ϵ�ϵͳ���
	private String confirmResult;// ����ȷ�Ͻ�� 0 Э�죺1������
	private String terminalInfo;// �����豸����
	private Date troubleEndTime;// ���Ͻ���ʱ��
	private String terminalAddress;// �����豸������
	private String troubleTimeArea;// ���Ϸ���ʱ��
	private String impressStartTime;// Ӱ��ҵ��ʱ�ο�ʼʱ��
	private String impressEndTime;
	private String impressType;;// Ӱ��ҵ������
	private String troubleGpsX;// ����λ��x����
	private String troubleGpsY;
	private String findTroubleGpsX;// �ҵ�����λ��x����
	private String findTroubleGpsY;
	private String impressArea;// Ӱ�����Χ
	private String requestNum;// ����û�Ͷ�߼���
	private String troublePhenomena;// ������������
	private String troubleType;// �������ͱ��
	private Date brokenTime;// �������ʱ��
	private String brokenAddress;
	private String brokenReason;
	private Date judgeTime;// �жϹ��ϵ�ʱ��
	private Date renewTime;// ���ϻָ�ʱ��
	private Date completeTime;// ���½������ʱ��
	private Date returnTime;// �����ֳ�ʱ��
	private String processRemark;// �����ʩ����
	private String processRecord;// ��������ݼ�¼
	private String otherIssue;// �������⴦��
	private String replyRemark;
	private String approveState;// ���������״̬
	private String evaluateState;// ����������״̬
	private int notPassedNum;// ��˲�ͨ������
	private String contractorId;
	private String replyManId;// �����˱��
	private Date replySubmitTime;// ��������дʱ��
	private String isReportCase = "";// �Ƿ񱨰�
	private String reportCaseComment = "";// ����˵��
	private String isReportDanger = "";// �Ƿ���

	private String otherCable;// �������¶�
	private Date arriveTime;// �������ʱ��
	private Date arriveTroubleTime;// ��������ֳ�ʱ��
	private Date findTroubleTime;// �ҵ����ϵ�ʱ��
	private Date troubleAcceptTime;// ��������ʱ��
	private String trunkids;

	// Constructors

	/** default constructor */
	public TroubleReply() {
	}

	/** minimal constructor */
	public TroubleReply(String id) {
		this.id = id;
	}

	/** full constructor */
	public TroubleReply(String id, String troubleId, String confirmResult,
			String terminalInfo, Date troubleEndTime, String terminalAddress,
			String troubleTimeArea, Date impressStartTime, Date impressEndTime,
			String impressType, String troubleGpsX, String troubleGpsY,
			String findTroubleGpsX, String findTroubleGpsY, String impressArea,
			String requestNum, String troublePhenomena, String troubleType,
			Date brokenTime, String brokenAddress, String brokenReason,
			Date judgeTime, Date renewTime, Date completeTime, Date returnTime,
			String processRemark, String processRecord, String otherIssue,
			String replyRemark, String approveState, String evaluateState,
			int notPassedNum, String contractorId, String replyManId,
			Date replySubmitTime) {
		this.id = id;
		this.troubleId = troubleId;
		this.confirmResult = confirmResult;
		this.terminalInfo = terminalInfo;
		this.troubleEndTime = troubleEndTime;
		this.terminalAddress = terminalAddress;
		this.troubleTimeArea = troubleTimeArea;
		this.impressType = impressType;
		this.troubleGpsX = troubleGpsX;
		this.troubleGpsY = troubleGpsY;
		this.findTroubleGpsX = findTroubleGpsX;
		this.findTroubleGpsY = findTroubleGpsY;
		this.impressArea = impressArea;
		this.requestNum = requestNum;
		this.troublePhenomena = troublePhenomena;
		this.troubleType = troubleType;
		this.brokenTime = brokenTime;
		this.brokenAddress = brokenAddress;
		this.brokenReason = brokenReason;
		this.judgeTime = judgeTime;
		this.renewTime = renewTime;
		this.completeTime = completeTime;
		this.returnTime = returnTime;
		this.processRemark = processRemark;
		this.processRecord = processRecord;
		this.otherIssue = otherIssue;
		this.replyRemark = replyRemark;
		this.approveState = approveState;
		this.evaluateState = evaluateState;
		this.notPassedNum = notPassedNum;
		this.contractorId = contractorId;
		this.replyManId = replyManId;
		this.replySubmitTime = replySubmitTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTroubleId() {
		return this.troubleId;
	}

	public void setTroubleId(String troubleId) {
		this.troubleId = troubleId;
	}

	public String getConfirmResult() {
		return this.confirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}

	public String getTerminalInfo() {
		return this.terminalInfo;
	}

	public void setTerminalInfo(String terminalInfo) {
		this.terminalInfo = terminalInfo;
	}

	public Date getTroubleEndTime() {
		return this.troubleEndTime;
	}

	public void setTroubleEndTime(Date troubleEndTime) {
		this.troubleEndTime = troubleEndTime;
	}

	public String getTerminalAddress() {
		return this.terminalAddress;
	}

	public void setTerminalAddress(String terminalAddress) {
		this.terminalAddress = terminalAddress;
	}

	public String getTroubleTimeArea() {
		return this.troubleTimeArea;
	}

	public void setTroubleTimeArea(String troubleTimeArea) {
		this.troubleTimeArea = troubleTimeArea;
	}

	public String getImpressStartTime() {
		return impressStartTime;
	}

	public void setImpressStartTime(String impressStartTime) {
		this.impressStartTime = impressStartTime;
	}

	public String getImpressEndTime() {
		return impressEndTime;
	}

	public void setImpressEndTime(String impressEndTime) {
		this.impressEndTime = impressEndTime;
	}

	public String getImpressType() {
		return impressType;
	}

	public void setImpressType(String impressType) {
		this.impressType = impressType;
	}

	public String getTroubleGpsX() {
		return this.troubleGpsX;
	}

	public void setTroubleGpsX(String troubleGpsX) {
		this.troubleGpsX = troubleGpsX;
	}

	public String getTroubleGpsY() {
		return this.troubleGpsY;
	}

	public void setTroubleGpsY(String troubleGpsY) {
		this.troubleGpsY = troubleGpsY;
	}

	public String getFindTroubleGpsX() {
		return this.findTroubleGpsX;
	}

	public void setFindTroubleGpsX(String findTroubleGpsX) {
		this.findTroubleGpsX = findTroubleGpsX;
	}

	public String getFindTroubleGpsY() {
		return this.findTroubleGpsY;
	}

	public void setFindTroubleGpsY(String findTroubleGpsY) {
		this.findTroubleGpsY = findTroubleGpsY;
	}

	public String getImpressArea() {
		return this.impressArea;
	}

	public void setImpressArea(String impressArea) {
		this.impressArea = impressArea;
	}

	public String getRequestNum() {
		return this.requestNum;
	}

	public void setRequestNum(String requestNum) {
		this.requestNum = requestNum;
	}

	public String getTroublePhenomena() {
		return this.troublePhenomena;
	}

	public void setTroublePhenomena(String troublePhenomena) {
		this.troublePhenomena = troublePhenomena;
	}

	public String getTroubleType() {
		return this.troubleType;
	}

	public void setTroubleType(String troubleType) {
		this.troubleType = troubleType;
	}

	public Date getBrokenTime() {
		return this.brokenTime;
	}

	public void setBrokenTime(Date brokenTime) {
		this.brokenTime = brokenTime;
	}

	public String getBrokenAddress() {
		return this.brokenAddress;
	}

	public void setBrokenAddress(String brokenAddress) {
		this.brokenAddress = brokenAddress;
	}

	public String getBrokenReason() {
		return this.brokenReason;
	}

	public void setBrokenReason(String brokenReason) {
		this.brokenReason = brokenReason;
	}

	public Date getJudgeTime() {
		return this.judgeTime;
	}

	public void setJudgeTime(Date judgeTime) {
		this.judgeTime = judgeTime;
	}

	public Date getRenewTime() {
		return this.renewTime;
	}

	public void setRenewTime(Date renewTime) {
		this.renewTime = renewTime;
	}

	public Date getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getProcessRemark() {
		return this.processRemark;
	}

	public void setProcessRemark(String processRemark) {
		this.processRemark = processRemark;
	}

	public String getProcessRecord() {
		return this.processRecord;
	}

	public void setProcessRecord(String processRecord) {
		this.processRecord = processRecord;
	}

	public String getOtherIssue() {
		return this.otherIssue;
	}

	public void setOtherIssue(String otherIssue) {
		this.otherIssue = otherIssue;
	}

	public String getReplyRemark() {
		return this.replyRemark;
	}

	public void setReplyRemark(String replyRemark) {
		this.replyRemark = replyRemark;
	}

	public String getApproveState() {
		return this.approveState;
	}

	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}

	public String getEvaluateState() {
		return this.evaluateState;
	}

	public void setEvaluateState(String evaluateState) {
		this.evaluateState = evaluateState;
	}

	public int getNotPassedNum() {
		return this.notPassedNum;
	}

	public void setNotPassedNum(int notPassedNum) {
		this.notPassedNum = notPassedNum;
	}

	public String getContractorId() {
		return this.contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getReplyManId() {
		return this.replyManId;
	}

	public void setReplyManId(String replyManId) {
		this.replyManId = replyManId;
	}

	public Date getReplySubmitTime() {
		return this.replySubmitTime;
	}

	public void setReplySubmitTime(Date replySubmitTime) {
		this.replySubmitTime = replySubmitTime;
	}

	public String getTrunkids() {
		return trunkids;
	}

	public void setTrunkids(String trunkids) {
		this.trunkids = trunkids;
	}

	public String getOtherCable() {
		return otherCable;
	}

	public void setOtherCable(String otherCable) {
		this.otherCable = otherCable;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Date getArriveTroubleTime() {
		return arriveTroubleTime;
	}

	public void setArriveTroubleTime(Date arriveTroubleTime) {
		this.arriveTroubleTime = arriveTroubleTime;
	}

	public Date getFindTroubleTime() {
		return findTroubleTime;
	}

	public void setFindTroubleTime(Date findTroubleTime) {
		this.findTroubleTime = findTroubleTime;
	}

	public Date getTroubleAcceptTime() {
		return troubleAcceptTime;
	}

	public void setTroubleAcceptTime(Date troubleAcceptTime) {
		this.troubleAcceptTime = troubleAcceptTime;
	}

	public String getIsReportCase() {
		return isReportCase;
	}

	public void setIsReportCase(String isReportCase) {
		this.isReportCase = isReportCase;
	}

	public String getReportCaseComment() {
		return reportCaseComment;
	}

	public void setReportCaseComment(String reportCaseComment) {
		this.reportCaseComment = reportCaseComment;
	}

	public String getIsReportDanger() {
		return isReportDanger;
	}

	public void setIsReportDanger(String isReportDanger) {
		this.isReportDanger = isReportDanger;
	}

}