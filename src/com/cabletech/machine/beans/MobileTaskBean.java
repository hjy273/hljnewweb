package com.cabletech.machine.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

/**
 * 移动制定任务
 * 
 * @author haozi
 * 
 */
public class MobileTaskBean extends BaseBean {
	private String tid;

	private String machinetype;// 任务的层次

	private String title;// 任务主题

	private String contractorid;// 代维ID

	private String userid;// 迅检人ID

	private String checkuser;// 检查人ID

	private String makepeopleid;// 制定人ID

	private Date maketime;// 制定的时间

	private String remark;// 备注

	private String executetime;// 该计划执行时间

	private String state;// 状态

	private String conname; // 代维名字

	private String userconname;// 巡检人的名字

	private String checkusername;// 核查人的名字

	private String numerical;
	
	public String getCheckuser() {
		return checkuser;
	}

	public void setCheckuser(String checkuser) {
		this.checkuser = checkuser;
	}

	public String getContractorid() {
		return contractorid;
	}

	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}

	public String getMachinetype() {
		return machinetype;
	}

	public void setMachinetype(String machinetype) {
		this.machinetype = machinetype;
	}

	public String getMakepeopleid() {
		return makepeopleid;
	}

	public void setMakepeopleid(String makepeopleid) {
		this.makepeopleid = makepeopleid;
	}

	public Date getMaketime() {
		return maketime;
	}

	public void setMaketime(Date maketime) {
		this.maketime = maketime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getExecutetime() {
		return executetime;
	}

	public void setExecutetime(String executetime) {
		this.executetime = executetime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCheckusername() {
		return checkusername;
	}

	public void setCheckusername(String checkusername) {
		this.checkusername = checkusername;
	}

	public String getConname() {
		return conname;
	}

	public void setConname(String conname) {
		this.conname = conname;
	}

	public String getUserconname() {
		return userconname;
	}

	public void setUserconname(String userconname) {
		this.userconname = userconname;
	}

	public String getNumerical() {
		return numerical;
	}

	public void setNumerical(String numerical) {
		this.numerical = numerical;
	}
}
