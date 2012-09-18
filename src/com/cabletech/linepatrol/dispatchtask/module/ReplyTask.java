package com.cabletech.linepatrol.dispatchtask.module;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class ReplyTask extends BaseCommonBean {
	private String id = "";
	private Date replytime;
	private String replyuserid = "";
	private String replyacce = "";
	private String replyresult = "";
	private String replyremark = "";
	private String sendacceptdeptid = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getReplytime() {
		return replytime;
	}

	public void setReplytime(Date replytime) {
		this.replytime = replytime;
	}

	public String getReplyuserid() {
		return replyuserid;
	}

	public void setReplyuserid(String replyuserid) {
		this.replyuserid = replyuserid;
	}

	public String getReplyacce() {
		return replyacce;
	}

	public void setReplyacce(String replyacce) {
		this.replyacce = replyacce;
	}

	public String getReplyresult() {
		return replyresult;
	}

	public void setReplyresult(String replyresult) {
		this.replyresult = replyresult;
	}

	public String getReplyremark() {
		return replyremark;
	}

	public void setReplyremark(String replyremark) {
		this.replyremark = replyremark;
	}

	public String getSendacceptdeptid() {
		return sendacceptdeptid;
	}

	public void setSendacceptdeptid(String sendacceptdeptid) {
		this.sendacceptdeptid = sendacceptdeptid;
	}
}
