package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class Contractor extends BaseDomainObject {
	public Contractor() {
	}

	private String contractorID;

	private String contractorName;

	private String linkmanInfo;

	private String principalInfo;

	private String pactBeginDate;

	private String remark;

	private String state;

	private String parentcontractorid;

	private String pactTerm;

	private String regionid;

	private String depttype;

	private String alias;
	
	//add on 20100128
	private String fax;//传真
	private String workPhone;//办公电话
	private String principalB;//负责人B
	private String email;//Email
	private String address;//地址
	private String maintenanceArea;//维护区域描述

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDepttype() {
		return depttype;
	}

	public void setDepttype(String depttype) {
		this.depttype = depttype;
	}

	public String getContractorID() {
		return contractorID;
	}

	public void setContractorID(String contractorID) {
		this.contractorID = contractorID;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getLinkmanInfo() {
		return linkmanInfo;
	}

	public void setLinkmanInfo(String linkmanInfo) {
		this.linkmanInfo = linkmanInfo;
	}

	public String getPrincipalInfo() {
		return principalInfo;
	}

	public void setPrincipalInfo(String principalInfo) {
		this.principalInfo = principalInfo;
	}

	public String getPactBeginDate() {
		return pactBeginDate;
	}

	public void setPactBeginDate(String pactBeginDate) {
		this.pactBeginDate = pactBeginDate;
	}

	public String getPactTerm() {
		return pactTerm;
	}

	public void setPactTerm(String pactTerm) {
		this.pactTerm = pactTerm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentcontractorid() {
		return parentcontractorid;
	}

	public void setParentcontractorid(String parentcontractorid) {
		this.parentcontractorid = parentcontractorid;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getPrincipalB() {
		return principalB;
	}

	public void setPrincipalB(String principalB) {
		this.principalB = principalB;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMaintenanceArea() {
		return maintenanceArea;
	}

	public void setMaintenanceArea(String maintenanceArea) {
		this.maintenanceArea = maintenanceArea;
	}
}
