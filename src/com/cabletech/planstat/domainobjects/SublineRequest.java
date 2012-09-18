package com.cabletech.planstat.domainobjects;

import java.sql.Timestamp;
import java.util.Date;

import com.cabletech.commons.base.BaseBean;

public class SublineRequest extends BaseBean{
    private String requestid;
    private Timestamp requesttime;
    private String userid;
    private Date responsetime;
    public SublineRequest(){
    	
    }
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public Timestamp getRequesttime() {
		return requesttime;
	}
	public void setRequesttime(Timestamp requesttime) {
		this.requesttime = requesttime;
	}
	public Date getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(Date responsetime) {
		this.responsetime = responsetime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

}
