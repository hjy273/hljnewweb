package com.cabletech.planinfo.beans;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.util.DateUtil;

public class BatchBean extends BaseBean{
	private String batchname="";
	private String batchtype="";
	private String startdate="";
	private String enddate="";
	private String taskoperation= "";
	private String taskopname= "";
	private String deptname="";
	private String contractorid = "";
	private String patrolmode;
	public String getPatrolmode() {
		return patrolmode;
	}
	public void setPatrolmode(String patrolmode) {
		this.patrolmode = patrolmode;
	}
	public BatchBean(){
		//startdate = DateUtil.getNowDateString();
		//enddate = DateUtil.getNowDateString();
	}
	public String getBatchname() {
		return batchname;
	}
	public void setBatchname(String batchname) {
		this.batchname = batchname;
	}
	public String getBatchtype() {
		return batchtype;
	}
	public void setBatchtype(String batchtype) {
		this.batchtype = batchtype;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getTaskoperation() {
		return taskoperation;
	}
	public void setTaskoperation(String taskoperation) {
		this.taskoperation = taskoperation;
	}
	public String getTaskopname() {
		return taskopname;
	}
	public void setTaskopname(String taskopname) {
		this.taskopname = taskopname;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getContractorid() {
		return contractorid;
	}
	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}
}
