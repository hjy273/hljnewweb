package com.cabletech.linepatrol.dispatchtask.beans;

import java.util.List;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class ReplyTaskBean extends BaseCommonBean {
	private String id = "";
	private String approverid = "";
	private String readerid = "";
	private String mobileid = "";
	private String sendtopic = "";
	private String replyid = "";
	private String replytime = "";
	private String replyuserid = "";
	private String replyusername = "";
	private String replyacce = "";
	private String replyresult = "";
	private String replyremark = "";
	private String sendacceptdeptid = "";

	private List approverUserList;
	private String replyTimeSign = "+";
	private String replyTimeLength = "0";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReplyid() {
		return replyid;
	}

	public void setReplyid(String replyid) {
		this.replyid = replyid;
	}

	public String getReplytime() {
		return replytime;
	}

	public void setReplytime(String replytime) {
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

	public String getSendtopic() {
		return sendtopic;
	}

	public void setSendtopic(String sendtopic) {
		this.sendtopic = sendtopic;
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

	public String getMobileid() {
		return mobileid;
	}

	public void setMobileid(String mobileid) {
		this.mobileid = mobileid;
	}

	public String getReaderid() {
		return readerid;
	}

	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}

	public String getReplyusername() {
		return replyusername;
	}

	public void setReplyusername(String replyusername) {
		this.replyusername = replyusername;
	}

	public List getApproverUserList() {
		return approverUserList;
	}

	public void setApproverUserList(List approverUserList) {
		this.approverUserList = approverUserList;
	}

	public String getReplyTimeLength() {
		return replyTimeLength;
	}

	public void setReplyTimeLength(String replyTimeLength) {
		this.replyTimeLength = replyTimeLength;
	}

	public String getReplyTimeSign() {
		return replyTimeSign;
	}

	public void setReplyTimeSign(String replyTimeSign) {
		this.replyTimeSign = replyTimeSign;
	}
}
