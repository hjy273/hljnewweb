package com.cabletech.lineinfo.domainobjects;

import com.cabletech.commons.base.*;

public class TempLine extends BaseBean{
    public TempLine(){
    }


    private String pointid;
    private String startpointgps;
    private String endpointgps;
    private String regionid;

    public void setPointid( String pointid ){
        this.pointid = pointid;
    }


    public void setStartpointgps( String startpointgps ){
        this.startpointgps = startpointgps;
    }


    public void setEndpointgps( String endpointgps ){
        this.endpointgps = endpointgps;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public String getPointid(){
        return pointid;
    }


    public String getStartpointgps(){
        return startpointgps;
    }


    public String getEndpointgps(){
        return endpointgps;
    }


    public String getRegionid(){
        return regionid;
    }

}
