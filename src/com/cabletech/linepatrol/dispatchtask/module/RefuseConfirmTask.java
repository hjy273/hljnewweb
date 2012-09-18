package com.cabletech.linepatrol.dispatchtask.module;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class RefuseConfirmTask extends BaseCommonBean {
	private String id = "";
	private String signInId;
	private Date confirmTime;
	private String confirmResult;
	private String confirmUserId;
	private String confirmRemark;
	private String sendacceptdeptid = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSignInId() {
		return signInId;
	}

	public void setSignInId(String signInId) {
		this.signInId = signInId;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getConfirmUserId() {
		return confirmUserId;
	}

	public void setConfirmUserId(String confirmUserId) {
		this.confirmUserId = confirmUserId;
	}

	public String getConfirmRemark() {
		return confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}

	public String getSendacceptdeptid() {
		return sendacceptdeptid;
	}

	public void setSendacceptdeptid(String sendacceptdeptid) {
		this.sendacceptdeptid = sendacceptdeptid;
	}

	public String getConfirmResult() {
		return confirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}
}
