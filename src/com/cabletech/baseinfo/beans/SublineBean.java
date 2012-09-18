package com.cabletech.baseinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class SublineBean extends BaseBean{

    private String subLineID;
    private String lineID;
    private String subLineName;
    private String ruleDeptID;
    private String lineType;
    private String regionID;
    private String state;
    private String checkPoints;
    private String remark;
    private String patrolid;
    private String contractorId;
    private String[] sublinecablelist;
    private String[][] cablelist;
    public SublineBean(){
    }


    public String getContractorId() {
		return contractorId;
	}


	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}


	public String getSubLineID(){
        return subLineID;
    }


    public void setSubLineID( String subLineID ){
        this.subLineID = subLineID;
    }


    public String getLineID(){
        return lineID;
    }


    public void setLineID( String lineID ){
        this.lineID = lineID;
    }


    public String getSubLineName(){
        return subLineName;
    }


    public void setSubLineName( String subLineName ){
        this.subLineName = subLineName;
    }


    public String getRuleDeptID(){
        return ruleDeptID;
    }


    public void setRuleDeptID( String ruleDeptID ){
        this.ruleDeptID = ruleDeptID;
    }


    public String getLineType(){
        return lineType;
    }


    public void setLineType( String lineType ){
        this.lineType = lineType;
    }


    public String getCheckPoints(){
        return checkPoints;
    }


    public void setCheckPoints( String checkPoints ){
        this.checkPoints = checkPoints;
    }


    public String getRegionID(){
        return regionID;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    // Public Methods
    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){

    }


    public String getState(){
        return state;
    }


    public String getRemark(){
        return remark;
    }


    public String getPatrolid(){
        return patrolid;
    }


    public String[] getSublinecablelist(){
        return sublinecablelist;
    }


    public String[][] getCablelist(){
        return cablelist;
    }


    public void setState( String state ){
        this.state = state;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setPatrolid( String patrolid ){
        this.patrolid = patrolid;
    }


    public void setSublinecablelist( String[] sublinecablelist ){
        this.sublinecablelist = sublinecablelist;
    }


    public void setCablelist( String[][] cablelist ){
        this.cablelist = cablelist;
    }

}
