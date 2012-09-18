package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class AlertreceiverList extends BaseDomainObject{
    public void AlertreceiverList(){

    }


    private String id;

    public void setId( String id ){
        this.id = id;
    }


    public String getId(){
        return id;
    }


    public void setSimcode( String simcode ){
        this.simcode = simcode;
    }


    public String getSimcode(){
        return simcode;
    }


    public void setEmergencylevel( String emergencylevel ){
        this.emergencylevel = emergencylevel;
    }


    public String getEmergencylevel(){
        return emergencylevel;
    }


    public void setUserid( String userid ){
        this.userid = userid;
    }


    public String getUserid(){
        return userid;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public String getContractorid(){
        return contractorid;
    }


    private String simcode;
    private String emergencylevel;
    private String userid;
    private String contractorid;

}
