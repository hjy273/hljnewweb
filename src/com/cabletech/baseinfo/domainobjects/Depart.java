package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class Depart extends BaseDomainObject{
    public Depart(){
    }


    private String deptID;
    private String deptName;
    private String linkmanInfo;
    private String parentID;
    private String remark;
    private String state;
    private String regionid;

    public String getDeptName(){
        return deptName;
    }


    public String getDeptID(){
        return deptID;
    }


    public String getLinkmanInfo(){
        return linkmanInfo;
    }


    public String getParentID(){
        return parentID;
    }


    public String getRemark(){
        return remark;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setParentID( String parentID ){
        this.parentID = parentID;
    }


    public void setLinkmanInfo( String linkmanInfo ){
        this.linkmanInfo = linkmanInfo;
    }


    public void setDeptName( String deptName ){
        this.deptName = deptName;
    }


    public void setDeptID( String deptID ){
        this.deptID = deptID;
    }


    public String getState(){
        return state;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String getRegionid(){
        return regionid;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }
}
