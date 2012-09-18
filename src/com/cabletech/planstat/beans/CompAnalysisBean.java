package com.cabletech.planstat.beans;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.util.DateUtil;

public class CompAnalysisBean extends BaseBean {
    public CompAnalysisBean(){
        endYear = DateUtil.getNowYearStr();
    }
    private String patrolID;
    private String patrolName;
    private String endYear;
    private String endMonth;
    private String startMonth;
    private String theMonth;
    private String contractorID;
    private String contractorName;
    private String regionId;
    
	
	
	public String getEndMonth() {
		return endMonth;
	}
	
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	
	public String getEndYear() {
		return endYear;
	}
	
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
	
	public String getPatrolID() {
		return patrolID;
	}
	
	public void setPatrolID(String patrolID) {
		this.patrolID = patrolID;
	}
	
	public String getPatrolName() {
		return patrolName;
	}
	
	public void setPatrolName(String patrolName) {
		this.patrolName = patrolName;
	}
	
	public String getRegionId() {
		return regionId;
	}
	
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	
	public String getStartMonth() {
		return startMonth;
	}
	
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getTheMonth() {
		return theMonth;
	}

	public void setTheMonth(String theMonth) {
		this.theMonth = theMonth;
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
}
