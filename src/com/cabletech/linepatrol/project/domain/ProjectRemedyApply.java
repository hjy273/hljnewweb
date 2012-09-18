package com.cabletech.linepatrol.project.domain;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;

public class ProjectRemedyApply extends BaseDomainObject {
	public static final String CANCELED_STATE = "999";
	
	private String id;
	private String remedyCode;
	private String projectName;
	private String contractorId;
	private int townId;
	private String regionId;
	private String remedyAddress;
	private Date remedyDate;
	private String remedyReason;
	private String remedySolve;
	private String creator;
	private Float totalFee;
	private Float mtotalFee;
	private String state;
	private String contractorName;
	private String town;
	private boolean flag;
	private String flowTaskName;

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private Date cancelTime;
	private String cancelTimeDis;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemedyCode() {
		return remedyCode;
	}

	public void setRemedyCode(String remedyCode) {
		this.remedyCode = remedyCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public int getTownId() {
		return townId;
	}

	public void setTownId(int townId) {
		this.townId = townId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRemedyAddress() {
		return remedyAddress;
	}

	public void setRemedyAddress(String remedyAddress) {
		this.remedyAddress = remedyAddress;
	}

	public Date getRemedyDate() {
		return remedyDate;
	}

	public void setRemedyDate(Date remedyDate) {
		this.remedyDate = remedyDate;
	}

	public String getRemedyReason() {
		return remedyReason;
	}

	public void setRemedyReason(String remedyReason) {
		this.remedyReason = remedyReason;
	}

	public String getRemedySolve() {
		return remedySolve;
	}

	public void setRemedySolve(String remedySolve) {
		this.remedySolve = remedySolve;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Float totalFee) {
		this.totalFee = totalFee;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public Float getMtotalFee() {
		return mtotalFee;
	}

	public void setMtotalFee(Float mtotalFee) {
		this.mtotalFee = mtotalFee;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getFlowTaskName() {
		return flowTaskName;
	}

	public void setFlowTaskName(String flowTaskName) {
		this.flowTaskName = flowTaskName;
	}

	public String getCancelUserId() {
		return cancelUserId;
	}

	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}

	public String getCancelUserName() {
		return cancelUserName;
	}

	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
		this.cancelTimeDis=DateUtil.DateToString(cancelTime, "yyyy/MM/dd HH:mm:ss");
	}

	public String getCancelTimeDis() {
		return cancelTimeDis;
	}

	public void setCancelTimeDis(String cancelTimeDis) {
		this.cancelTimeDis = cancelTimeDis;
	}

}
