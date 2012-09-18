package com.cabletech.watchinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class WatchReportBean extends BaseBean{

    private String lineID; //线路名称
    private String sublineID; //线路段名称
    private String placeID; //外力盯防地点
    private String beginDate;
    private String endDate;
    private String principal; //领导巡检率//负责人巡检率//外力盯防巡检率
    private String state;
    private int orderlyCyc;
    private int errorCyc;
    private java.util.Date orderlyBeginTime;
    private java.util.Date orderlyEndTime;
    private java.util.List timeList;
    private String leaderRate;
    private String principalRate;
    private String watchRate;

    public WatchReportBean(){
    }


    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){

    }


    public String getLineID(){
        return lineID;
    }


    public void setLineID( String lineID ){
        this.lineID = lineID;
    }


    public String getSublineID(){
        return sublineID;
    }


    public void setSublineID( String sublineID ){
        this.sublineID = sublineID;
    }


    public String getPlaceID(){
        return placeID;
    }


    public void setPlaceID( String placeID ){
        this.placeID = placeID;
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


    public String getPrincipal(){
        return principal;
    }


    public void setPrincipal( String principal ){
        this.principal = principal;
    }


    public String getLeaderRate(){
        return leaderRate;
    }


    public void setLeaderRate( String leaderRate ){
        this.leaderRate = leaderRate;
    }


    public String getPrincipalRate(){
        return principalRate;
    }


    public void setPrincipalRate( String principalRate ){
        this.principalRate = principalRate;
    }


    public String getWatchRate(){
        return watchRate;
    }


    public void setWatchRate( String watchRate ){
        this.watchRate = watchRate;
    }


    public String getState(){
        return state;
    }


    public void setState( String state ){
        this.state = state;
    }


    public int getOrderlyCyc(){
        return orderlyCyc;
    }


    public void setOrderlyCyc( int orderlyCyc ){
        this.orderlyCyc = orderlyCyc;
    }


    public int getErrorCyc(){
        return errorCyc;
    }


    public void setErrorCyc( int errorCyc ){
        this.errorCyc = errorCyc;
    }


    public java.util.Date getOrderlyBeginTime(){
        return orderlyBeginTime;
    }


    public void setOrderlyBeginTime( java.util.Date orderlyBeginTime ){
        this.orderlyBeginTime = orderlyBeginTime;
    }


    public java.util.Date getOrderlyEndTime(){
        return orderlyEndTime;
    }


    public void setOrderlyEndTime( java.util.Date orderlyEndTime ){
        this.orderlyEndTime = orderlyEndTime;
    }


    public java.util.List getTimeList(){

        return timeList;
    }


    public void setTimeList( java.util.List timeList ){
        this.timeList = timeList;
    }

}
