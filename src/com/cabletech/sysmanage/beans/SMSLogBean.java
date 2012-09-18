package com.cabletech.sysmanage.beans;

import com.cabletech.commons.base.*;

public class SMSLogBean extends BaseBean{
    private String begindate;
    private String enddate;
    private String regionid;
    private String simid;
    private String contractorid;
    private String patrolmanid;
    public SMSLogBean(){
    }


    public void setBegindate( String begindate ){
        this.begindate = begindate;
    }


    public void setEnddate( String enddate ){
        this.enddate = enddate;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setSimid( String simid ){
        this.simid = simid;
    }

    public void setContractorid(String contractorid){
        this.contractorid=contractorid;
    }

    public void setPatrolmanid(String patrolmanid){
        this.patrolmanid=patrolmanid;
    }

    public String getBegindate(){
        return begindate;
    }


    public String getEnddate(){
        return enddate;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getSimid(){
        return simid;
    }

    public String getContractorid(){
        return contractorid;
    }

    public String getPatrolmanid(){
        return patrolmanid;
    }
}
