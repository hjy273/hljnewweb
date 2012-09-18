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
	private String troubleId;// 故障单编号
	private String troubleName;
	private Date troubleStartTime;// 故障发生时间
	private String troubleSendMan;// 故障派发人
	private String troubleRemark;
	private Date troubleSendTime;
	private String isGreatTrouble;// 是否为重大故障 0不是，1是
	private String connector;// 联系人
	private String connectorTel;
	private String troubleReasonId;// 故障原因编号
	private String sendManId;// 派发人编号
	private String troubleState;// '0'
	// 待反馈，‘20’待审核，‘30’审核不通过，‘31’审核通过，‘40’待考核，‘50’完成，'00'临时保存
	private String troubleAccidentId;// 故障对应隐患编号
	private String troubleType;// 0巡回，1告知
	private String regionID;// 故障所属区域

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