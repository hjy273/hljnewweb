package com.cabletech.watchinfo.beans;

import com.cabletech.commons.base.BaseBean;

public class WatchMsgBean extends BaseBean {
	
	// ҳ���־
	private String pagetype;
	// ����id
	private String watchid ;
	// ��ѯ��ʼʱ��
	private String begindate;
	// ��ѯ����ʱ��
	private String enddate;
	// ��������
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
