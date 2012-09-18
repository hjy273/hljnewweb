package com.cabletech.baseinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class AlertreceiverListBean extends BaseBean{
    private String id;

    // Public Methods
    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){
        id = "";
        simcode = "";
        emergencylevel = "";
        userid = "";
        contractorid = "";
        regionid="";

    }


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

    public void setRegionid(String regionid){
        this.regionid=regionid;
    }

    public String getRegionid(){
        return regionid;
    }

    private String simcode;
    private String emergencylevel;
    private String userid;
    private String contractorid;
    private String regionid;
}
