package com.cabletech.watchinfo.beans;

import com.cabletech.commons.base.*;

public class SubWatchBean extends BaseBean{

    public SubWatchBean(){
    }


    private String kid;
    private String watchid;
    private String segment_kid;
    private String segmentname;
    private String fiber_kid;
    private String fibername;
    private String laytype;
    private String corenum;
    private String netlayer;
    private String monitortype;
    private String ifneedcut;

    public void setKid( String kid ){
        this.kid = kid;
    }


    public void setWatchid( String watchid ){
        this.watchid = watchid;
    }


    public void setSegment_kid( String segment_kid ){
        this.segment_kid = segment_kid;
    }


    public void setSegmentname( String segmentname ){
        this.segmentname = segmentname;
    }


    public void setFiber_kid( String fiber_kid ){
        this.fiber_kid = fiber_kid;
    }


    public void setFibername( String fibername ){
        this.fibername = fibername;
    }


    public void setLaytype( String laytype ){
        this.laytype = laytype;
    }


    public void setCorenum( String corenum ){
        this.corenum = corenum;
    }


    public void setNetlayer( String netlayer ){
        this.netlayer = netlayer;
    }


    public void setMonitortype( String monitortype ){
        this.monitortype = monitortype;
    }


    public void setIfneedcut( String ifneedcut ){
        this.ifneedcut = ifneedcut;
    }


    public String getKid(){
        return kid;
    }


    public String getWatchid(){
        return watchid;
    }


    public String getSegment_kid(){
        return segment_kid;
    }


    public String getSegmentname(){
        return segmentname;
    }


    public String getFiber_kid(){
        return fiber_kid;
    }


    public String getFibername(){
        return fibername;
    }


    public String getLaytype(){
        return laytype;
    }


    public String getCorenum(){
        return corenum;
    }


    public String getNetlayer(){
        return netlayer;
    }


    public String getMonitortype(){
        return monitortype;
    }


    public String getIfneedcut(){
        return ifneedcut;
    }

}
