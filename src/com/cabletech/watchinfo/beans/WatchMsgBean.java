package com.cabletech.watchinfo.beans;

import com.cabletech.commons.base.BaseBean;

public class WatchMsgBean extends BaseBean {
	
	// 页面标志
	private String pagetype;
	// 盯防id
	private String watchid ;
	// 查询开始时间
	private String begindate;
	// 查询结束时间
	private String enddate;
	// 盯防名称
	private String watchname;
	
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getPagetype() {
		return pagetype;
	}
	public void setPagetype(String pagetype) {
		this.pagetype = pagetype;
	}
	public String getWatchid() {
		return watchid;
	}
	public void setWatchid(String watchid) {
		this.watchid = watchid;
	}
	public String getWatchname() {
		return watchname;
	}
	public void setWatchname(String watchname) {
		this.watchname = watchname;
	}
	
	
	

}
