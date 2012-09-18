package com.cabletech.linepatrol.acceptance.beans;

import com.cabletech.commons.base.BaseBean;

public class QueryReinspectBean extends BaseBean {
	private String issueNumber;
	private String begintime;
	private String endtime;
	private String prcpm;
	private String cableLevel;
	private String pcpm;
	
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getPrcpm() {
		return prcpm;
	}
	public void setPrcpm(String prcpm) {
		this.prcpm = prcpm;
	}
	public String getCableLevel() {
		return cableLevel;
	}
	public void setCableLevel(String cableLevel) {
		this.cableLevel = cableLevel;
	}
	public String getPcpm() {
		return pcpm;
	}
	public void setPcpm(String pcpm) {
		this.pcpm = pcpm;
	}
}
