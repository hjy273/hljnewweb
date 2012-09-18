package com.cabletech.linepatrol.trouble.beans;

import java.util.Date;
import java.util.List;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class TroubleReplyBean extends BaseCommonBean {
	// Fields

	private String id;
	private String troubleId;// 故障单系统编号
	private String confirmResult;// 故障确认结果
	private String terminalInfo;// 故障设备名称
	private String troubleEndTime;// 故障发生时间
	private String terminalAddress;// 故障设备所属地
	private String troubleTimeArea;// 故障发生时段
	private String impressStartTime;// 影响业务时段开始时间
	private String impressEndTime;
	private String impressTypeArray[];// 影响业务类型
	private String impressType;// 影响业务类型
	private String troubleGpsX;// 故障位置x坐标
	private String troubleGpsY;
	private String findTroubleGpsX;// 找到故障位置x坐标
	private String findTroubleGpsY;
	private String impressArea;// 影响地理范围
	private String requestNum;// 造成用户投诉件数
	private String troublePhenomena;// 故障现象描述
	private String troubleType;// 故障类型编号
	private String brokenTime;// 光缆阻断时间
	private String brokenAddress;
	private String brokenReason;
	private String judgeTime;// 判断故障点时间
	private String renewTime;// 故障恢复时间
	private String completeTime;// 光缆接续完成时间
	private String returnTime;// 撤离现场时间
	private String processRemark;// 处理措施描述
	private String processRecord;// 处理后数据记录
	private String otherIssue;// 遗留问题处理
	private String replyRemark;// 处理后数据记录
	private String approveState;// 反馈单审核状态
	private String evaluateState;// 反馈单评分状态
	private int notPassedNum;// 审核不通过次数
	private String contractorId;
	private String replyManId;// 反馈人编号
	private String replySubmitTime;// 反馈单填写时间
	private String otherCable;// 其他光缆段
	private String arriveTime;// 到达机房时间
	private String arriveTroubleTime;// 到达故障现场时间
	private String findTroubleTime;// 找到故障点时间
	private String troubleAcceptTime;// 故障受理时间
	private String isReportCase = "";// 是否报案
	private String reportCaseComment = "";// 报案说明
	private String isReportDanger = "";// 是否报险

	private String approver;// 一个审核人

	private String approvers;// 多个审核人
	private String reads;// 多个抄送人
	private String mobiles;// 审核人的电话号码
	private String rmobiles;// 抄送人号码

	private String trunkids;

	// 故障基本信息
	private String troubleName;
	private String troubleStartTime;// 故障发生时间
	private String troubleSendMan;// 故障派发人
	private String troubleRemark;
	private String isGreatTrouble;// 是否为重大故障
	private String connector;// 联系人
	private String connectorTel;
	private String sendManId;// 派发人编号

	private String eomsCodes;

	private String tempsave;// 临时保存

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

	private String hiddentrouble;// 隐患信息

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
