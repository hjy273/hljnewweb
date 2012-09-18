package com.cabletech.planstat.beans;

import com.cabletech.commons.base.*;
import com.cabletech.commons.util.DateUtil;

public class PlanExeResultBean extends BaseBean{
    private String contractorID;
    private String contractorName;
    private String patrolID;
    private String patrolName;
    private String endYear;
    private String endMonth;
    private String examineresult;
    
    public PlanExeResultBean(){
        endYear = DateUtil.getNowYearStr();
    }


    public String getContractorID(){
        return contractorID;
    }


    public void setContractorID( String contractorID ){
        this.contractorID = contractorID;
    }


    public String getContractorName(){
        return contractorName;
    }


    public void setContractorName( String contractorName ){
        this.contractorName = contractorName;
    }


    public String getPatrolID(){
        return patrolID;
    }


    public void setPatrolID( String patrolID ){
        this.patrolID = patrolID;
    }


    public String getPatrolName(){
        return patrolName;
    }


    public void setPatrolName( String patrolName ){
        this.patrolName = patrolName;
    }


    public String getEndYear(){
        return endYear;
    }


    public void setEndYear( String endYear ){
        this.endYear = endYear;
    }


    public String getEndMonth(){
        return endMonth;
    }


    public void setEndMonth( String endMonth ){
        this.endMonth = endMonth;
    }


	public String getExamineresult() {
		return examineresult;
	}


	public void setExamineresult(String examineresult) {
		this.examineresult = examineresult;
	}

}
