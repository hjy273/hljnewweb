package com.cabletech.sysmanage.beans;

import com.cabletech.commons.base.*;

public class SendMessageBean extends BaseBean {
	private String sendManId;
	private String acceptManIds;
	private String mobileIds;
	private String sendContent;

	public SendMessageBean() {
	}

	public String getSendManId() {
		return sendManId;
	}

	public void setSendManId(String sendManId) {
		this.sendManId = sendManId;
	}

	public String getAcceptManIds() {
		return acceptManIds;
	}

	public void setAcceptManIds(String acceptManIds) {
		this.acceptManIds = acceptManIds;
	}

	public String getMobileIds() {
		return mobileIds;
	}

	public void setMobileIds(String mobileIds) {
		this.mobileIds = mobileIds;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}
}
