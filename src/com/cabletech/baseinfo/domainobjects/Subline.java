package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class Subline extends BaseBean{

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
    public Subline(){
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


    public String getState(){
        return state;
    }


    public String getRemark(){
        return remark;
    }


    public String getPatrolid(){
        return patrolid;
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
}
