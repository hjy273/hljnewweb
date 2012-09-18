package com.cabletech.watchinfo.beans;

import com.cabletech.commons.base.*;

public class TempWatchBean extends BaseBean{
    private String bedited;
    private String gpscoordinate;
    private String pointid;
    private String pointname;
    private String receivetime;
    private String regionid;
    private String simid;
    public String getBedited(){
        return bedited;
    }


    public void setBedited( String bedited ){
        this.bedited = bedited;
    }


    public void setSimid( String simid ){
        this.simid = simid;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setReceivetime( String receivetime ){
        this.receivetime = receivetime;
    }


    public void setPointname( String pointname ){
        this.pointname = pointname;
    }


    public void setPointid( String pointid ){
        this.pointid = pointid;
    }


    public void setGpscoordinate( String gpscoordinate ){
        this.gpscoordinate = gpscoordinate;
    }


    public String getGpscoordinate(){
        return gpscoordinate;
    }


    public String getPointid(){
        return pointid;
    }


    public String getPointname(){
        return pointname;
    }


    public String getReceivetime(){
        return receivetime;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getSimid(){
        return simid;
    }

}
