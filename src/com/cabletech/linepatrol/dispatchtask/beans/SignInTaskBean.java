package com.cabletech.linepatrol.dispatchtask.beans;

import java.util.List;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;

public class SignInTaskBean extends BaseCommonBean {
	private String id = "";
	private String tranferuserid = "";
	private String sendtopic = "";
	private String acceptdeptid = "";
	private String acceptuserid = "";
	private List acceptDeptList;
	private List acceptUserList;
	private String endorseid = "";
	private String endorseusername = "";
	private String time = "";
	private String deptid = "";
	private String userid = "";
	private String result = "";
	private String resultlabel = "";
	private String remark = "";
	private String acce = "";
	private String sendtaskid = "";
	private String sendacceptdeptid = "";
	private String refuseUserId = "";
	private String signInId = "";
	private String confirmUserId = "";
	private String confirmTime = "";
	private String confirmUserName = "";
	private String confirmResult = "";
	private String confirmRemark = "";
	private String isTransfer = DispatchTaskConstant.NOT_TRANSFER;
	private String signInState = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEndorseid() {
		return endorseid;
	}

	public void setEndorseid(String endorseid) {
		this.endorseid = endorseid;
	}

	public String getEndorseusername() {
		return endorseusername;
	}

	public void setEndorseusername(String endorseusername) {
		this.endorseusername = endorseusername;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAcce() {
		return acce;
	}

	public void setAcce(String acce) {
		this.acce = acce;
	}

	public String getSendtaskid() {
		return sendtaskid;
	}

	public void setSendtaskid(String sendtaskid) {
		this.sendtaskid = sendtaskid;
	}

	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}

	public String getSendtopic() {
		return sendtopic;
	}

	public void setSendtopic(String sendtopic) {
		this.sendtopic = sendtopic;
	}

	public String getAcceptdeptid() {
		return acceptdeptid;
	}

	public void setAcceptdeptid(String acceptdeptid) {
		this.acceptdeptid = acceptdeptid;
	}

	public String getAcceptuserid() {
		return acceptuserid;
	}

	public void setAcceptuserid(String acceptuserid) {
		this.acceptuserid = acceptuserid;
	}

	public String getTranferuserid() {
		return tranferuserid;
	}

	public void setTranferuserid(String tranferuserid) {
		this.tranferuserid = tranferuserid;
	}

	public String getSendacceptdeptid() {
		return sendacceptdeptid;
	}

	public void setSendacceptdeptid(String sendacceptdeptid) {
		this.sendacceptdeptid = sendacceptdeptid;
	}

	public String getResultlabel() {
		return resultlabel;
	}

	public void setResultlabel(String resultlabel) {
		this.resultlabel = resultlabel;
	}

	public List getAcceptDeptList() {
		return acceptDeptList;
	}

	public void setAcceptDeptList(List acceptDeptList) {
		this.acceptDeptList = acceptDeptList;
	}

	public List getAcceptUserList() {
		return acceptUserList;
	}

	public void setAcceptUserList(List acceptUserList) {
		this.acceptUserList = acceptUserList;
	}

	public String getConfirmUserId() {
		return confirmUserId;
	}

	public void setConfirmUserId(String confirmUserId) {
		this.confirmUserId = confirmUserId;
	}

	public String getConfirmResult() {
		return confirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}

	public String getConfirmRemark() {
		return confirmRemark;
	}

	public void setConfirmRemark(String confirmRemark) {
		this.confirmRemark = confirmRemark;
	}

	public String getRefuseUserId() {
		return refuseUserId;
	}

	public void setRefuseUserId(String refuseUserId) {
		this.refuseUserId = refuseUserId;
	}

	public String getConfirmUserName() {
		return confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}

	public String getSignInId() {
		return signInId;
	}

	public void setSignInId(String signInId) {
		this.signInId = signInId;
	}

	public String getSignInState() {
		return signInState;
	}

	public void setSignInState(String signInState) {
		this.signInState = signInState;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}
}
