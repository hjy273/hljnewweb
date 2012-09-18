package com.cabletech.linepatrol.dispatchtask.module;

import com.cabletech.commons.base.BaseDomainObject;

public class DispatchAcceptDept extends BaseDomainObject {
	private String tid;
	private String sendtaskid = "";
	private String deptid = "";
	private String userid = "";
	private String sendtaskpid = "";
	private String workstate = "";
	private String signInId = "";

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

	public String getSendtaskid() {
		return sendtaskid;
	}

	public void setSendtaskid(String sendtaskid) {
		this.sendtaskid = sendtaskid;
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

	public String getSignInId() {
		return signInId;
	}

	public void setSignInId(String signInId) {
		this.signInId = signInId;
	}

}
