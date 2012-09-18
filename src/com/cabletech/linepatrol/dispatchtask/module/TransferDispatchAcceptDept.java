package com.cabletech.linepatrol.dispatchtask.module;

import com.cabletech.commons.base.BaseDomainObject;

public class TransferDispatchAcceptDept extends BaseDomainObject {
	private String tid;
	private String signInId = "";
	private String deptid = "";
	private String userid = "";
	private String sendtaskpid = "";
	private String workstate = "";
	private String isTransfer = "";

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getWorkstate() {
		return workstate;
	}

	public void setWorkstate(String workstate) {
		this.workstate = workstate;
	}

	public String getSendtaskpid() {
		return sendtaskpid;
	}

	public void setSendtaskpid(String sendtaskpid) {
		this.sendtaskpid = sendtaskpid;
	}

	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}

	public String getSignInId() {
		return signInId;
	}

	public void setSignInId(String signInId) {
		this.signInId = signInId;
	}

}
