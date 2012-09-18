package com.cabletech.linepatrol.dispatchtask.module;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class SignInTask extends BaseCommonBean {
	private String id = "";
	private Date time;
	private String deptid = "";
	private String userid = "";
	private String result = "";
	private String remark = "";
	private String acce = "";
	private String transferuserid = "";
	private String sendacceptdeptid = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
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

	public String getTransferuserid() {
		return transferuserid;
	}

	public void setTransferuserid(String transferuserid) {
		this.transferuserid = transferuserid;
	}

	public String getSendacceptdeptid() {
		return sendacceptdeptid;
	}

	public void setSendacceptdeptid(String sendacceptdeptid) {
		this.sendacceptdeptid = sendacceptdeptid;
	}
}
