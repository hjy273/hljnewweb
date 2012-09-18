package com.cabletech.planstat.domainobjects;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

public class SublineResponse extends BaseBean{
	private String id;
	private String requestid;
	private String planid;
	private String sublineid;
	private java.lang.Integer nonpatrolnum;
    private java.lang.Integer planpointnum;
    private java.lang.Integer actualpointnum;
    private java.lang.Integer todaypointnum;
    private String deadlinedays;
    private String state;
    private Date finishtime;
    public SublineResponse(){
    	 
    }
	public java.lang.Integer getActualpointnum() {
		return actualpointnum;
	}
	public void setActualpointnum(java.lang.Integer actualpointnum) {
		this.actualpointnum = actualpointnum;
	}
	public String getDeadlinedays() {
		return deadlinedays;
	}
	public void setDeadlinedays(String deadlinedays) {
		this.deadlinedays = deadlinedays;
	}
	public Date getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public java.lang.Integer getNonpatrolnum() {
		return nonpatrolnum;
	}
	public void setNonpatrolnum(java.lang.Integer nonpatrolnum) {
		this.nonpatrolnum = nonpatrolnum;
	}
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public java.lang.Integer getPlanpointnum() {
		return planpointnum;
	}
	public void setPlanpointnum(java.lang.Integer planpointnum) {
		this.planpointnum = planpointnum;
	}
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSublineid() {
		return sublineid;
	}
	public void setSublineid(String sublineid) {
		this.sublineid = sublineid;
	}
	public java.lang.Integer getTodaypointnum() {
		return todaypointnum;
	}
	public void setTodaypointnum(java.lang.Integer todaypointnum) {
		this.todaypointnum = todaypointnum;
	}
}
