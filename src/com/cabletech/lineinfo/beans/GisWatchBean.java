package com.cabletech.lineinfo.beans;

import com.cabletech.commons.base.*;

public class GisWatchBean extends BaseBean{

    private String placeid;
    private String placename;
    private String regionid;
    private String lid;
    private String gpscoordinate;
    private String isgeneralpoint;

    public GisWatchBean(){
    }


    public String getPlaceid(){
        return placeid;
    }


    public void setPlaceid( String placeid ){
        this.placeid = placeid;
    }


    public String getPlacename(){
        return placename;
    }


    public void setPlacename( String placename ){
        this.placename = placename;
    }


    public String getRegionid(){
        return regionid;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public String getLid(){
        return lid;
    }


    public void setLid( String lid ){
        this.lid = lid;
    }


    public String getGpscoordinate(){
        return gpscoordinate;
    }


    public void setGpscoordinate( String gpscoordinate ){
        this.gpscoordinate = gpscoordinate;
    }


    public String getIsgeneralpoint(){
        return isgeneralpoint;
    }


    public void setIsgeneralpoint( String isgeneralpoint ){
        this.isgeneralpoint = isgeneralpoint;
    }
}
