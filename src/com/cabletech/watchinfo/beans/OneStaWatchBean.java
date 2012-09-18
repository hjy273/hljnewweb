package com.cabletech.watchinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class OneStaWatchBean extends BaseBean{

    public OneStaWatchBean(){
    }


    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){
        this.placeID = "";
        this.placeName = "";
        this.principal = "";
        this.terminalID = "";
        this.beginDate = "";
        this.endDate = "";
        this.patrolTime = "";
        this.orderlyBeginTime = "";
        this.orderlyEndTime = "";
        this.lid = "";
        this.orderlyCyc = "";
        this.error = "";
        this.startpointid = "";
        this.endpointid = "";
        this.watchwidth = "";

    }


    private String placeID;
    private String placeName;
    private String principal;
    private String terminalID;
    private String beginDate;
    private String endDate;
    private String patrolTime;
    private String orderlyBeginTime;
    private String orderlyEndTime;
    private String lid;
    private String regionID;
    private String error;
    private String orderlyCyc;
    private String startpointid;
    private String endpointid;
    private String watchwidth;
    private String infoneed;
    private String infodid;
    private String exerate;
    private String alertamount;

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


    public String getBeginDate(){
        return beginDate;
    }


    public void setBeginDate( String beginDate ){
        this.beginDate = beginDate;
    }


    public String getEndDate(){
        return endDate;
    }


    public void setEndDate( String endDate ){
        this.endDate = endDate;
    }


    public String getPatrolTime(){
        return patrolTime;
    }


    public void setPatrolTime( String patrolTime ){
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


    public String getLid(){
        return lid;
    }


    public void setLid( String lid ){
        this.lid = lid;
    }


    public String getRegionID(){
        return regionID;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    public String getError(){
        return error;
    }


    public void setError( String error ){
        this.error = error;
    }


    public String getOrderlyCyc(){
        return orderlyCyc;
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


    public String getInfoneed(){
        return infoneed;
    }


    public String getInfodid(){
        return infodid;
    }


    public String getExerate(){
        return exerate;
    }


    public String getAlertamount(){
        return alertamount;
    }


    public void setOrderlyCyc( String orderlyCyc ){
        this.orderlyCyc = orderlyCyc;
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


    public void setInfoneed( String infoneed ){
        this.infoneed = infoneed;
    }


    public void setInfodid( String infodid ){
        this.infodid = infodid;
    }


    public void setExerate( String exerate ){
        this.exerate = exerate;
    }


    public void setAlertamount( String alertamount ){
        this.alertamount = alertamount;
    }

}
