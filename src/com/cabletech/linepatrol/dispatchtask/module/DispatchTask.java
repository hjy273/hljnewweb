package com.cabletech.linepatrol.dispatchtask.module;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class DispatchTask extends BaseCommonBean {
	private String id = "";
	private String senduserid = "";
	private String senddeptid = "";
	private Date processterm;// 处理期限
	private String sendtopic = "";
	private String sendtext = "";
	private String sendacce = "";
	private Date sendtime;
	private String sendtype = "";
	private String regionid = "";

	private String replyRequest = "";
	private String cancelUserId = "";
	private Date cancelTime;
	private String cancelReason = "";
	private String workState = DispatchTaskConstant.NOT_END_STATE;
	private String serialnumber = "";
	private String flag = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenduserid() {
		return senduserid;
	}

	public void setSenduserid(String senduserid) {
		this.senduserid = senduserid;
	}

	public String getSenddeptid() {
		return senddeptid;
	}

	public void setSenddeptid(String senddeptid) {
		this.senddeptid = senddeptid;
	}

	public Date getProcessterm() {
		return processterm;
	}

	public void setProcessterm(Date processterm) {
		this.processterm = processterm;
	}

	public String getSendtopic() {
		return sendtopic;
	}

	public void setSendtopic(String sendtopic) {
		this.sendtopic = sendtopic;
	}

	public String getSendtext() {
		return sendtext;
	}

	public void setSendtext(String sendtext) {
		this.sendtext = sendtext;
	}

	public String getSendacce() {
		return sendacce;
	}

	public void setSendacce(String sendacce) {
		this.sendacce = sendacce;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getReplyRequest() {
		return replyRequest;
	}

	public void setReplyRequest(String replyRequest) {
		this.replyRequest = replyRequest;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
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
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}
