package com.cabletech.linepatrol.trouble.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

public class TroubleInfoBean extends BaseBean {
	private String id;
	private String troubleId;// ���ϵ�ϵͳ���
	private String troubleName;
	private String troubleStartTime;// ���Ϸ���ʱ��
	private String troubleSendMan;// �����ɷ���
	private String troubleRemark;
	private Date troubleSendTime;
	private String isGreatTrouble;// �Ƿ�Ϊ�ش����
	private String connector;// ��ϵ��
	private String connectorTel;
	private String troubleReasonId;// ����ԭ����
	private String sendManId;// �ɷ��˱��
	private String troubleState;
	private String troubleAccidentId;// ���϶�Ӧ�������

	private String tempsave;// ��ʱ����

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelTime;
	private String cancelReason = "";
	private String replyDeadline;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTroubleId() {
		return troubleId;
	}

	public void setTroubleId(String troubleId) {
		this.troubleId = troubleId;
	}

	public String getTroubleName() {
		return troubleName;
	}

	public void setTroubleName(String troubleName) {
		this.troubleName = troubleName;
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

	public Date getTroubleSendTime() {
		return troubleSendTime;
	}

	public void setTroubleSendTime(Date troubleSendTime) {
		this.troubleSendTime = troubleSendTime;
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

	public String getTroubleReasonId() {
		return troubleReasonId;
	}

	public void setTroubleReasonId(String troubleReasonId) {
		this.troubleReasonId = troubleReasonId;
	}

	public String getSendManId() {
		return sendManId;
	}

	public void setSendManId(String sendManId) {
		this.sendManId = sendManId;
	}

	public String getTroubleState() {
		return troubleState;
	}

	public void setTroubleState(String troubleState) {
		this.troubleState = troubleState;
	}

	public String getTroubleAccidentId() {
		return troubleAccidentId;
	}

	public String getTroubleStartTime() {
		return troubleStartTime;
	}

	public void setTroubleStartTime(String troubleStartTime) {
		this.troubleStartTime = troubleStartTime;
	}

	public void setTroubleAccidentId(String troubleAccidentId) {
		this.troubleAccidentId = troubleAccidentId;
	}

	public String getTempsave() {
		return tempsave;
	}

	public void setTempsave(String tempsave) {
		this.tempsave = tempsave;
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

	public String getReplyDeadline() {
		return replyDeadline;
	}

	public void setReplyDeadline(String replyDeadline) {
		this.replyDeadline = replyDeadline;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

}
