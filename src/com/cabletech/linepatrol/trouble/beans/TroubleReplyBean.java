package com.cabletech.linepatrol.trouble.beans;

import java.util.Date;
import java.util.List;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class TroubleReplyBean extends BaseCommonBean {
	// Fields

	private String id;
	private String troubleId;// ���ϵ�ϵͳ���
	private String confirmResult;// ����ȷ�Ͻ��
	private String terminalInfo;// �����豸����
	private String troubleEndTime;// ���Ϸ���ʱ��
	private String terminalAddress;// �����豸������
	private String troubleTimeArea;// ���Ϸ���ʱ��
	private String impressStartTime;// Ӱ��ҵ��ʱ�ο�ʼʱ��
	private String impressEndTime;
	private String impressTypeArray[];// Ӱ��ҵ������
	private String impressType;// Ӱ��ҵ������
	private String troubleGpsX;// ����λ��x����
	private String troubleGpsY;
	private String findTroubleGpsX;// �ҵ�����λ��x����
	private String findTroubleGpsY;
	private String impressArea;// Ӱ�����Χ
	private String requestNum;// ����û�Ͷ�߼���
	private String troublePhenomena;// ������������
	private String troubleType;// �������ͱ��
	private String brokenTime;// �������ʱ��
	private String brokenAddress;
	private String brokenReason;
	private String judgeTime;// �жϹ��ϵ�ʱ��
	private String renewTime;// ���ϻָ�ʱ��
	private String completeTime;// ���½������ʱ��
	private String returnTime;// �����ֳ�ʱ��
	private String processRemark;// �����ʩ����
	private String processRecord;// ��������ݼ�¼
	private String otherIssue;// �������⴦��
	private String replyRemark;// ��������ݼ�¼
	private String approveState;// ���������״̬
	private String evaluateState;// ����������״̬
	private int notPassedNum;// ��˲�ͨ������
	private String contractorId;
	private String replyManId;// �����˱��
	private String replySubmitTime;// ��������дʱ��
	private String otherCable;// �������¶�
	private String arriveTime;// �������ʱ��
	private String arriveTroubleTime;// ��������ֳ�ʱ��
	private String findTroubleTime;// �ҵ����ϵ�ʱ��
	private String troubleAcceptTime;// ��������ʱ��
	private String isReportCase = "";// �Ƿ񱨰�
	private String reportCaseComment = "";// ����˵��
	private String isReportDanger = "";// �Ƿ���

	private String approver;// һ�������

	private String approvers;// ��������
	private String reads;// ���������
	private String mobiles;// ����˵ĵ绰����
	private String rmobiles;// �����˺���

	private String trunkids;

	// ���ϻ�����Ϣ
	private String troubleName;
	private String troubleStartTime;// ���Ϸ���ʱ��
	private String troubleSendMan;// �����ɷ���
	private String troubleRemark;
	private String isGreatTrouble;// �Ƿ�Ϊ�ش����
	private String connector;// ��ϵ��
	private String connectorTel;
	private String sendManId;// �ɷ��˱��

	private String eomsCodes;

	private String tempsave;// ��ʱ����

	public String getTempsave() {
		return tempsave;
	}

	public void setTempsave(String tempsave) {
		this.tempsave = tempsave;
	}

	public String getEomsCodes() {
		return eomsCodes;
	}

	public void setEomsCodes(String eomsCodes) {
		this.eomsCodes = eomsCodes;
	}

	public String getTroubleName() {
		return troubleName;
	}

	public void setTroubleName(String troubleName) {
		this.troubleName = troubleName;
	}

	public String getTroubleStartTime() {
		return troubleStartTime;
	}

	public void setTroubleStartTime(String troubleStartTime) {
		this.troubleStartTime = troubleStartTime;
	}

	public String getTroubleSendMan() {
		return troubleSendMan;
	}

	public void setTroubleSendMan(String troubleSendMan) {
		this.troubleSendMan = troubleSendMan;
	}

	public String getTroubleRemark() {
		return troubleRemark;
	}

	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}

	public String getIsGreatTrouble() {
		return isGreatTrouble;
	}

	public void setIsGreatTrouble(String isGreatTrouble) {
		this.isGreatTrouble = isGreatTrouble;
	}

	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

	public String getConnectorTel() {
		return connectorTel;
	}

	public void setConnectorTel(String connectorTel) {
		this.connectorTel = connectorTel;
	}

	public String getSendManId() {
		return sendManId;
	}

	public void setSendManId(String sendManId) {
		this.sendManId = sendManId;
	}

	public String getTrunkids() {
		return trunkids;
	}

	public void setTrunkids(String trunkids) {
		this.trunkids = trunkids;
	}

	private String hiddentrouble;// ������Ϣ

	public String getApprovers() {
		return approvers;
	}

	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}

	private List<String> trunks;

	public List<String> getTrunks() {
		return trunks;
	}

	public void setTrunks(List<String> trunks) {
		this.trunks = trunks;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

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

	public String[] getImpressTypeArray() {
		return impressTypeArray;
	}

	public void setImpressTypeArray(String[] impressTypeArray) {
		this.impressTypeArray = impressTypeArray;
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

	public String getTroubleEndTime() {
		return troubleEndTime;
	}

	public void setTroubleEndTime(String troubleEndTime) {
		this.troubleEndTime = troubleEndTime;
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

	public String getBrokenTime() {
		return brokenTime;
	}

	public void setBrokenTime(String brokenTime) {
		this.brokenTime = brokenTime;
	}

	public String getJudgeTime() {
		return judgeTime;
	}

	public void setJudgeTime(String judgeTime) {
		this.judgeTime = judgeTime;
	}

	public String getRenewTime() {
		return renewTime;
	}

	public void setRenewTime(String renewTime) {
		this.renewTime = renewTime;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public int getNotPassedNum() {
		return notPassedNum;
	}

	public void setNotPassedNum(int notPassedNum) {
		this.notPassedNum = notPassedNum;
	}

	public String getContractorId() {
		return contractorId;
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

	public String getReplySubmitTime() {
		return replySubmitTime;
	}

	public void setReplySubmitTime(String replySubmitTime) {
		this.replySubmitTime = replySubmitTime;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getHiddentrouble() {
		return hiddentrouble;
	}

	public void setHiddentrouble(String hiddentrouble) {
		this.hiddentrouble = hiddentrouble;
	}

	public String getOtherCable() {
		return otherCable;
	}

	public void setOtherCable(String otherCable) {
		this.otherCable = otherCable;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getArriveTroubleTime() {
		return arriveTroubleTime;
	}

	public void setArriveTroubleTime(String arriveTroubleTime) {
		this.arriveTroubleTime = arriveTroubleTime;
	}

	public String getFindTroubleTime() {
		return findTroubleTime;
	}

	public void setFindTroubleTime(String findTroubleTime) {
		this.findTroubleTime = findTroubleTime;
	}

	public String getReads() {
		return reads;
	}

	public void setReads(String reads) {
		this.reads = reads;
	}

	public String getRmobiles() {
		return rmobiles;
	}

	public void setRmobiles(String rmobiles) {
		this.rmobiles = rmobiles;
	}

	public String getTroubleAcceptTime() {
		return troubleAcceptTime;
	}

	public void setTroubleAcceptTime(String troubleAcceptTime) {
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
