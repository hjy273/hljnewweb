package com.cabletech.linepatrol.remedy.beans;

import com.cabletech.commons.base.BaseBean;

public class StatRemedyBean extends BaseBean {
	private String townId;
	private String contractorId;
	private String month;
	public String getTownId() {
		return townId;
	}
	public void setTownId(String townId) {
		this.townId = townId;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
}	
