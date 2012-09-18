package com.cabletech.schedule.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class SendSmJobInfo extends BaseDomainObject {
	public static final String NOT_SENT_STATE = "01";
	public static final String SENT_STATE = "02";
	public static final String CANCEL_SENT_STATE = "03";
	public static final String ONLY_ONCE_SEND = "-1";
	public static final String LOOP_SEND = "1";
	public static final String LOOP_SEND_CYCLE = "2";
	private String id;
	private String simId;
	private String sendContent;
	private String sendType;
	private Date firstSendTime;
	private Date lastSendTime;
	private String sendTimeType;
	private int sendTimeSpace;
	private String sendState;
	private String schedularName;
	private String sendObjectId;
	private String createUserId;
	private Date createDate;
	private String sendObjectName;
	private String sendCycleRule;
	private String cronExpressionString;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public Date getFirstSendTime() {
		return firstSendTime;
	}

	public void setFirstSendTime(Date firstSendTime) {
		this.firstSendTime = firstSendTime;
	}

	public Date getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public int getSendTimeSpace() {
		return sendTimeSpace;
	}

	public void setSendTimeSpace(int sendTimeSpace) {
		this.sendTimeSpace = sendTimeSpace;
	}

	public String getSendState() {
		return sendState;
	}

	public void setSendState(String sendState) {
		this.sendState = sendState;
	}

	public String getSchedularName() {
		return schedularName;
	}

	public void setSchedularName(String schedularName) {
		this.schedularName = schedularName;
	}

	public String getSendObjectId() {
		return sendObjectId;
	}

	public void setSendObjectId(String sendObjectId) {
		this.sendObjectId = sendObjectId;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSendTimeType() {
		return sendTimeType;
	}

	public void setSendTimeType(String sendTimeType) {
		this.sendTimeType = sendTimeType;
	}

	public String getSendObjectName() {
		return sendObjectName;
	}

	public void setSendObjectName(String sendObjectName) {
		this.sendObjectName = sendObjectName;
	}

	public String getSendCycleRule() {
		return sendCycleRule;
	}

	public void setSendCycleRule(String sendCycleRule) {
		this.sendCycleRule = sendCycleRule;
	}

	public String getCronExpressionString() {
		return cronExpressionString;
	}

	public void setCronExpressionString(String cronExpressionString) {
		this.cronExpressionString = cronExpressionString;
	}

}
