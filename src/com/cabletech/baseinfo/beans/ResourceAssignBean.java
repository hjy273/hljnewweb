package com.cabletech.baseinfo.beans;

import com.cabletech.commons.base.BaseBean;

public class ResourceAssignBean extends BaseBean{
	private String contractor;
	private String lineid;
	private String terminal;
	private String targetContractor;
	public String getContractor() {
		return contractor;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	public String getLineid() {
		return lineid;
	}
	public void setLineid(String lineid) {
		this.lineid = lineid;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getTargetContractor() {
		return targetContractor;
	}
	public void setTargetContractor(String targetContractor) {
		this.targetContractor = targetContractor;
	}
}
