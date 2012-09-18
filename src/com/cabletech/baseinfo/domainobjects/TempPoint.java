package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author not attributable
 * @version 1.0
 */

public class TempPoint extends BaseBean{

    private String pointID;
    private String gpsCoordinate;
    private String regionID;
    private String simID;
    private String bedited;
    private String receiveTime;
    public TempPoint(){
    }


    public String getPointID(){
        return pointID;
    }


    public void setPointID( String pointID ){
        this.pointID = pointID;
    }


    public String getGpsCoordinate(){
        return gpsCoordinate;
    }


    public void setGpsCoordinate( String gpsCoordinate ){
        this.gpsCoordinate = gpsCoordinate;
    }


    public String getRegionID(){
        return regionID;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    public String getSimID(){
        return simID;
    }


    public void setSimID( String simID ){
        this.simID = simID;
    }


    public String getBedited(){
        return bedited;
    }


    public void setBedited( String bedited ){
        this.bedited = bedited;
    }


    public String getReceiveTime(){
        return receiveTime;
    }


    public void setReceiveTime( String receiveTime ){
        this.receiveTime = receiveTime;
    }
}
