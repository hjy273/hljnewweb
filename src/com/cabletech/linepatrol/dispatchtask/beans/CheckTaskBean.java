package com.cabletech.linepatrol.dispatchtask.beans;

import java.util.List;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class CheckTaskBean extends BaseCommonBean {
	private String id = "";
	private String replyid = "";
	private String replyuserid = "";
	private String approverid = "";
	private String sendtopic = "";
	private String validatetime = "";
	private String validateuserid = "";
	private String validateusername = "";
	private String validateacce = "";
	private String validateresult = "";
	private String validateresultlabel = "";
	private String validateremark = "";
	private String sendacceptdeptid = "";
	
	private List approverUserList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValidatetime() {
		return validatetime;
	}

	public void setValidatetime(String validatetime) {
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

	public String getSendtopic() {
		return sendtopic;
	}

	public void setSendtopic(String sendtopic) {
		this.sendtopic = sendtopic;
	}

	public String getReplyuserid() {
		return replyuserid;
	}

	public void setReplyuserid(String replyuserid) {
		this.replyuserid = replyuserid;
	}

	public String getApproverid() {
		return approverid;
	}

	public void setApproverid(String approverid) {
		this.approverid = approverid;
	}

	public String getSendacceptdeptid() {
		return sendacceptdeptid;
	}

	public void setSendacceptdeptid(String sendacceptdeptid) {
		this.sendacceptdeptid = sendacceptdeptid;
	}

	public String getReplyid() {
		return replyid;
	}

	public void setReplyid(String replyid) {
		this.replyid = replyid;
	}

	public String getValidateresultlabel() {
		return validateresultlabel;
	}

	public void setValidateresultlabel(String validateresultlabel) {
		this.validateresultlabel = validateresultlabel;
	}

	public String getValidateusername() {
		return validateusername;
	}

	public void setValidateusername(String validateusername) {
		this.validateusername = validateusername;
	}

	public List getApproverUserList() {
		return approverUserList;
	}

	public void setApproverUserList(List approverUserList) {
		this.approverUserList = approverUserList;
	}
}
