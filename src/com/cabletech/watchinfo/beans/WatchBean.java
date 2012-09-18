package com.cabletech.watchinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class WatchBean extends BaseBean{

    public WatchBean(){
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


    private String placeID = "";
    private String placeName = "";
    private String principal = "";
    private String terminalID = "";
    private String beginDate = "";
    private String endDate = "";
    private String patrolTime = "";
    private String orderlyBeginTime = "";
    private String orderlyEndTime = "";
    private String lid = "";
    private String regionID = "";
    private String error = "";
    private String orderlyCyc = "";
    private String startpointid = "";
    private String endpointid = "";
    private String watchwidth = "";
    private String gpscoordinate = "";
    private String contractorid = "";
    private String innerregion = "";
    private String watchplace = "";
    private String placetype = "";
    private String dangerlevel = "";
    private String watchreason = "";
    private String endwatchinfo = "";
    private String involvedlinenumber = "";
    private String ifcheckintime = "";
    private String checkresult = "";
    private String dealstatus = "";
    private String involvedsegmentlist = "";
    private String ifcheckdone = "";
    
    // add by guixy 2008-11-13
    private String x = "";
    private String y = "";
    
    
    public String getX() {
		return x;
	}


	public void setX(String x) {
		this.x = x;
	}


	public String getY() {
		return y;
	}


	public void setY(String y) {
		this.y = y;
	}


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


    public String getGpscoordinate(){
        return gpscoordinate;
    }


    public String getContractorid(){
        return contractorid;
    }


    public String getInnerregion(){
        return innerregion;
    }


    public String getWatchplace(){
        return watchplace;
    }


    public String getPlacetype(){
        return placetype;
    }


    public String getDangerlevel(){
        return dangerlevel;
    }


    public String getWatchreason(){
        return watchreason;
    }


    public String getEndwatchinfo(){
        return endwatchinfo;
    }


    public String getInvolvedlinenumber(){
        return involvedlinenumber;
    }


    public String getIfcheckintime(){
        return ifcheckintime;
    }


    public String getCheckresult(){
        return checkresult;
    }


    public String getDealstatus(){
        return dealstatus;
    }


    public String getInvolvedsegmentlist(){
        return involvedsegmentlist;
    }


    public String getIfcheckdone(){
        return ifcheckdone;
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


    public void setGpscoordinate( String gpscoordinate ){
        this.gpscoordinate = gpscoordinate;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public void setInnerregion( String innerregion ){
        this.innerregion = innerregion;
    }


    public void setWatchplace( String watchplace ){
        this.watchplace = watchplace;
    }


    public void setPlacetype( String placetype ){
        this.placetype = placetype;
    }


    public void setDangerlevel( String dangerlevel ){
        this.dangerlevel = dangerlevel;
    }


    public void setWatchreason( String watchreason ){
        this.watchreason = watchreason;
    }


    public void setEndwatchinfo( String endwatchinfo ){
        this.endwatchinfo = endwatchinfo;
    }


    public void setInvolvedlinenumber( String involvedlinenumber ){
        this.involvedlinenumber = involvedlinenumber;
    }


    public void setIfcheckintime( String ifcheckintime ){
        this.ifcheckintime = ifcheckintime;
    }


    public void setCheckresult( String checkresult ){
        this.checkresult = checkresult;
    }


    public void setDealstatus( String dealstatus ){
        this.dealstatus = dealstatus;
    }


    public void setInvolvedsegmentlist( String involvedsegmentlist ){
        this.involvedsegmentlist = involvedsegmentlist;
    }


    public void setIfcheckdone( String ifcheckdone ){
        this.ifcheckdone = ifcheckdone;
    }

}
