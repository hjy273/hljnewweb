package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class Region extends BaseDomainObject{

    private String regionID;
    private String regionName;
    private String regionDes;
    private String state;
    private String parentregionid;
    public Region(){
///        System.out.println( "Regin Object Create" );
    }


    public String getRegionName(){
        return regionName;
    }


    public void setRegionName( String regionName ){
        this.regionName = regionName;
    }


    public String getRegionID(){
        return regionID;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    public String getRegionDes(){
        return regionDes;
    }


    public void setRegionDes( String regionDes ){
        this.regionDes = regionDes;
    }


    public String getState(){
        return state;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String getParentregionid(){
        return parentregionid;
    }


    public void setParentregionid( String parentregionid ){
        this.parentregionid = parentregionid;
    }
}
