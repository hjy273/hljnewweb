package com.cabletech.linepatrol.trouble.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;

/**
 * LpTroubleInfo entity. @author MyEclipse Persistence Tools
 */

public class TroubleInfo extends BaseDomainObject implements
		java.io.Serializable {
	public static final String CANCELED_STATE = "999";

	// Fields

	private String id;
	private String troubleId;// ���ϵ����
	private String troubleName;
	private Date troubleStartTime;// ���Ϸ���ʱ��
	private String troubleSendMan;// �����ɷ���
	private String troubleRemark;
	private Date troubleSendTime;
	private String isGreatTrouble;// �Ƿ�Ϊ�ش���� 0���ǣ�1��
	private String connector;// ��ϵ��
	private String connectorTel;
	private String troubleReasonId;// ����ԭ����
	private String sendManId;// �ɷ��˱��
	private String troubleState;// '0'
	// ����������20������ˣ���30����˲�ͨ������31�����ͨ������40�������ˣ���50����ɣ�'00'��ʱ����
	private String troubleAccidentId;// ���϶�Ӧ�������
	private String troubleType;// 0Ѳ�أ�1��֪
	private String regionID;// ������������

	// Constructors
	private String cancelUserId = "";
	private String cancelUserName = "";
	private Date cancelTime;
	private String cancelReason = "";
	private String cancelTimeDis;
	private Date replyDeadline;

	public String getRegionID() {
		return regionID;
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	public String getTroubleType() {
		return troubleType;
	}

	public void setTroubleType(String troubleType) {
		this.troubleType = troubleType;
	}

	/** default constructor */
	public TroubleInfo() {
	}

	/** minimal constructor */
	public TroubleInfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public TroubleInfo(String id, String troubleId, String troubleName,
			Date troubleStartTime, String troubleSendMan, String troubleRemark,
			Date troubleSendTime, String isGreatTrouble, String connector,
			String connectorTel, String troubleReasonId, String sendManId,
			String troubleState, String troubleAccidentId) {
		this.id = id;
		this.troubleId = troubleId;
		this.troubleName = troubleName;
		this.troubleStartTime = troubleStartTime;
		this.troubleSendMan = troubleSendMan;
		this.troubleRemark = troubleRemark;
		this.troubleSendTime = troubleSendTime;
		this.isGreatTrouble = isGreatTrouble;
		this.connector = connector;
		this.connectorTel = connectorTel;
		this.troubleReasonId = troubleReasonId;
		this.sendManId = sendManId;
		this.troubleState = troubleState;
		this.troubleAccidentId = troubleAccidentId;
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

	public String getTroubleName() {
		return this.troubleName;
	}

	public void setTroubleName(String troubleName) {
		this.troubleName = troubleName;
	}

	public Date getTroubleStartTime() {
		return this.troubleStartTime;
	}

	public void setTroubleStartTime(Date troubleStartTime) {
		this.troubleStartTime = troubleStartTime;
	}

	public String getTroubleSendMan() {
		return this.troubleSendMan;
	}

	public void setTroubleSendMan(String troubleSendMan) {
		this.troubleSendMan = troubleSendMan;
	}

	public String getTroubleRemark() {
		return this.troubleRemark;
	}

	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}

	public Date getTroubleSendTime() {
		return this.troubleSendTime;
	}

	public void setTroubleSendTime(Date troubleSendTime) {
		this.troubleSendTime = troubleSendTime;
	}

	public String getIsGreatTrouble() {
		return this.isGreatTrouble;
	}

	public void setIsGreatTrouble(String isGreatTrouble) {
		this.isGreatTrouble = isGreatTrouble;
	}

	public String getConnector() {
		return this.connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

	public String getConnectorTel() {
		return this.connectorTel;
	}

	public void setConnectorTel(String connectorTel) {
		this.connectorTel = connectorTel;
	}

	public String getTroubleReasonId() {
		return this.troubleReasonId;
	}

	public void setTroubleReasonId(String troubleReasonId) {
		this.troubleReasonId = troubleReasonId;
	}

	public String getSendManId() {
		return this.sendManId;
	}

	public void setSendManId(String sendManId) {
		this.sendManId = sendManId;
	}

	public String getTroubleState() {
		return this.troubleState;
	}

	public void setTroubleState(String troubleState) {
		this.troubleState = troubleState;
	}

	public String getTroubleAccidentId() {
		return this.troubleAccidentId;
	}

	public void setTroubleAccidentId(String troubleAccidentId) {
		this.troubleAccidentId = troubleAccidentId;
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
		this.cancelTimeDis = DateUtil.DateToString(cancelTime,
				"yyyy/MM/dd HH:mm:ss");
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

	public Date getReplyDeadline() {
		return replyDeadline;
	}

	public void setReplyDeadline(Date replyDeadline) {
		this.replyDeadline = replyDeadline;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

}