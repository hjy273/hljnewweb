package com.cabletech.watchinfo.domainobjects;

import com.cabletech.commons.base.*;

public class Watch extends BaseDomainObject{
    public Watch(){
    }


    private String placeID;
    private String placeName;
    private String principal;
    private String terminalID;
    private String lid;
    private String regionID;
    private java.util.Date beginDate;
    private java.util.Date endDate;
    private String orderlyBeginTime;
    private String orderlyEndTime;
    private java.util.Date patrolTime;
    private String error;
    private String orderlyCyc;
    private String startpointid;
    private String endpointid;
    private String watchwidth;
    private String gpscoordinate;
    private String contractorid;
    private String innerregion;
    private String watchplace;
    private String placetype;
    private String dangerlevel;
    private String watchreason;
    private String endwatchinfo;
    private String involvedlinenumber;
    private String ifcheckintime;
    private String checkresult;
    private String dealstatus;
    public String getPlaceID(){
        return placeID;
    }


    public void setPlaceID( String placeID ){
        this.placeID = placeID;
    }


    public String getPlaceName(){
        return placeName;
    }


    public void setPlaceName( String placeName ){
        this.placeName = placeName;
    }


    public String getPrincipal(){
        return principal;
    }


    public void setPrincipal( String principal ){
        this.principal = principal;
    }


    public String getTerminalID(){
        return terminalID;
    }


    public void setTerminalID( String terminalID ){
        this.terminalID = terminalID;
    }


    public java.util.Date getBeginDate(){
        return beginDate;
    }


    public void setBeginDate( java.util.Date beginDate ){
        this.beginDate = beginDate;
    }


    public java.util.Date getEndDate(){
        return endDate;
    }


    public void setEndDate( java.util.Date endDate ){
        this.endDate = endDate;
    }


    public java.util.Date getPatrolTime(){
        return patrolTime;
    }


    public void setPatrolTime( java.util.Date patrolTime ){
        this.patrolTime = patrolTime;
    }


    public String getOrderlyBeginTime(){
        return orderlyBeginTime;
    }


    public void setOrderlyBeginTime( String orderlyBeginTime ){
        this.orderlyBeginTime = orderlyBeginTime;
    }


    public String getOrderlyEndTime(){
        return orderlyEndTime;
    }


    public void setOrderlyEndTime( String orderlyEndTime ){
        this.orderlyEndTime = orderlyEndTime;
    }


    public String getOrderlyCyc(){
        return orderlyCyc;
    }


    public void setOrderlyCyc( String orderlyCyc ){
        this.orderlyCyc = orderlyCyc;
    }


    public String getError(){
        return error;
    }


    public void setError( String error ){
        this.error = error;
    }


    public String getLid(){
        return lid;
    }


    public void setLid( String lid ){
        this.lid = lid;
    }


    public String getRegionID(){
        return regionID;
    }


    public String getStartpointid(){
        return startpointid;
    }


    public String getEndpointid(){
        return endpointid;
    }


    public String getWatchwidth(){
        return watchwidth;
    }


    public String getGpscoordinate(){
        return gpscoordinate;
    }


    public String getCheckresult(){
        return checkresult;
    }


    public String getContractorid(){
        return contractorid;
    }


    public String getDangerlevel(){
        return dangerlevel;
    }


    public String getEndwatchinfo(){
        return endwatchinfo;
    }


    public String getIfcheckintime(){
        return ifcheckintime;
    }


    public String getInnerregion(){
        return innerregion;
    }


    public String getInvolvedlinenumber(){
        return involvedlinenumber;
    }


    public String getPlacetype(){
        return placetype;
    }


    public String getWatchplace(){
        return watchplace;
    }


    public String getWatchreason(){
        return watchreason;
    }


    public String getDealstatus(){
        return dealstatus;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    public void setStartpointid( String startpointid ){
        this.startpointid = startpointid;
    }


    public void setEndpointid( String endpointid ){
        this.endpointid = endpointid;
    }


    public void setWatchwidth( String watchwidth ){
        this.watchwidth = watchwidth;
    }


    public void setGpscoordinate( String gpscoordinate ){
        this.gpscoordinate = gpscoordinate;
    }


    public void setWatchreason( String watchreason ){
        this.watchreason = watchreason;
    }


    public void setWatchplace( String watchplace ){
        this.watchplace = watchplace;
    }


    public void setPlacetype( String placetype ){
        this.placetype = placetype;
    }


    public void setInvolvedlinenumber( String involvedlinenumber ){
        this.involvedlinenumber = involvedlinenumber;
    }


    public void setInnerregion( String innerregion ){
        this.innerregion = innerregion;
    }


    public void setIfcheckintime( String ifcheckintime ){
        this.ifcheckintime = ifcheckintime;
    }


    public void setEndwatchinfo( String endwatchinfo ){
        this.endwatchinfo = endwatchinfo;
    }


    public void setDangerlevel( String dangerlevel ){
        this.dangerlevel = dangerlevel;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public void setCheckresult( String checkresult ){
        this.checkresult = checkresult;
    }


    public void setDealstatus( String dealstatus ){
        this.dealstatus = dealstatus;
    }

}
