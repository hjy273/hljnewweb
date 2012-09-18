package com.cabletech.linepatrol.dispatchtask.module;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class CheckTask extends BaseCommonBean {
	private String id = "";
	private String replyid = "";
	private Date validatetime;
	private String validateuserid = "";
	private String validateacce = "";
	private String validateresult = "";
	private String validateremark = "";
	private String sendacceptdeptid = "";
	private String examFlag = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getValidatetime() {
		return validatetime;
	}

	public void setValidatetime(Date validatetime) {
		this.validatetime = validatetime;
	}

	public String getValidateuserid() {
		return validateuserid;
	}

	public void setValidateuserid(String validateuserid) {
		this.validateuserid = validateuserid;
	}

	public String getValidateresult() {
		return validateresult;
	}

	public void setValidateresult(String validateresult) {
		this.validateresult = validateresult;
	}

	public String getValidateremark() {
		return validateremark;
	}

	public void setValidateremark(String validateremark) {
		this.validateremark = validateremark;
	}

	public String getValidateacce() {
		return validateacce;
	}

	public void setValidateacce(String validateacce) {
		this.validateacce = validateacce;
	}

	public String getReplyid() {
		return replyid;
	}

	public void setReplyid(String replyid) {
		this.replyid = replyid;
	}

	public String getSendacceptdeptid() {
		return sendacceptdeptid;
	}

	public void setSendacceptdeptid(String sendacceptdeptid) {
		this.sendacceptdeptid = sendacceptdeptid;
	}

	public String getExamFlag() {
		return examFlag;
	}

	public void setExamFlag(String examFlag) {
		this.examFlag = examFlag;
	}
}
