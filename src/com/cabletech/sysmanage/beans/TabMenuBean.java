package com.cabletech.sysmanage.beans;

import com.cabletech.commons.base.BaseBean;

public class TabMenuBean extends BaseBean {
	private String id;
	private String tabname;
	private String power;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getTabname() {
		return tabname;
	}
	public void setTabname(String tabname) {
		this.tabname = tabname;
	}
	
}
