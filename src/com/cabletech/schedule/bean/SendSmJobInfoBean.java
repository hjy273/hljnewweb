package com.cabletech.schedule.bean;

import com.cabletech.commons.base.BaseBean;

;

public class SendSmJobInfoBean extends BaseBean {
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
	private String firstSendTime;
	private String lastSendTime;
	private String sendTimeType;
	private String sendTimeSpace;
	private String sendState;
	private String schedularName;
	private String sendObjectId;
	private String createUserId;
	private String createDate;
	private String prevSentTime;
	private String nextSentTime;
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

	public String getFirstSendTime() {
		return firstSendTime;
	}

	public void setFirstSendTime(String firstSendTime) {
		this.firstSendTime = firstSendTime;
	}

	public String getLastSendTime() {
		return lastSendTime;
	}

	public void setLastSendTime(String lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public String getSendTimeSpace() {
		return sendTimeSpace;
	}

	public void setSendTimeSpace(String sendTimeSpace) {
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getPrevSentTime() {
		return prevSentTime;
	}

	public void setPrevSentTime(String prevSentTime) {
		this.prevSentTime = prevSentTime;
	}

	public String getNextSentTime() {
		return nextSentTime;
	}

	public void setNextSentTime(String nextSentTime) {
		this.nextSentTime = nextSentTime;
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
