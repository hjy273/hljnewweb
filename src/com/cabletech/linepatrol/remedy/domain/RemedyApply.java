package com.cabletech.linepatrol.remedy.domain;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class RemedyApply extends BaseDomainObject {

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

    private String state;
    
    private String prevState;

    private String currentStepId;

    private String nextProcessMan;
    
    private String firstStepApproveMan;

    private String statusName;

    private String needToUpLevel;

    private String contractorName;

    private String town;
    
    private Float materialFee;

    public Float getMaterialFee() {
		return materialFee;
	}

	public void setMaterialFee(Float materialFee) {
		this.materialFee = materialFee;
	}

	private boolean isPassed = true;

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

    public void setRemedyReason(String remedyReson) {
        this.remedyReason = remedyReson;
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

    public String getNextProcessMan() {
        return nextProcessMan;
    }

    public void setNextProcessMan(String nextProcessMan) {
        this.nextProcessMan = nextProcessMan;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(String currentStepId) {
        this.currentStepId = currentStepId;
    }

    public Float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Float totalFee) {
        this.totalFee = totalFee;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getNeedToUpLevel() {
        return needToUpLevel;
    }

    public void setNeedToUpLevel(String needToUpLevel) {
        this.needToUpLevel = needToUpLevel;
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

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }

    public String getPrevState() {
        return prevState;
    }

    public void setPrevState(String prevState) {
        this.prevState = prevState;
    }

    public String getFirstStepApproveMan() {
        return firstStepApproveMan;
    }

    public void setFirstStepApproveMan(String firstStepApproveMan) {
        this.firstStepApproveMan = firstStepApproveMan;
    }

}
