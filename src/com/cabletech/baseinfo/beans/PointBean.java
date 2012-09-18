package com.cabletech.baseinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class PointBean extends BaseBean{

    private String pointID;
    private String pointName;
    private String addressInfo;
    private String gpsCoordinate;
    private String subLineID;
    private String lineType;
    private String isFocus;
    private String regionID;
    private String state;
    private String inumber;
    private String patrolid;
    private String pointtype;
    public PointBean(){
    }


    public String getPointID(){
        return pointID;
    }


    public void setPointID( String pointID ){
        this.pointID = pointID;
    }


    public String getPointName(){
        return pointName;
    }


    public void setPointName( String pointName ){
        this.pointName = pointName;
    }


    public String getAddressInfo(){
        return addressInfo;
    }


    public void setAddressInfo( String addressInfo ){
        this.addressInfo = addressInfo;
    }


    public String getGpsCoordinate(){
        return gpsCoordinate;
    }


    public void setGpsCoordinate( String gpsCoordinate ){
        this.gpsCoordinate = gpsCoordinate;
    }


    public String getSubLineID(){
        return subLineID;
    }


    public void setSubLineID( String subLineID ){
        this.subLineID = subLineID;
    }


    public String getLineType(){
        return lineType;
    }


    public void setLineType( String lineType ){
        this.lineType = lineType;
    }


    public String getIsFocus(){
        return isFocus;
    }


    public void setIsFocus( String isFocus ){
        this.isFocus = isFocus;
    }


    public String getRegionID(){
        return regionID;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    // Public Methods
    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){

    }


    public String getState(){
        return state;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String getInumber(){
        return inumber;
    }


    public String getPatrolid(){
        return patrolid;
    }


    public String getPointtype(){
        return pointtype;
    }


    public void setInumber( String inumber ){
        this.inumber = inumber;
    }


    public void setPatrolid( String patrolid ){
        this.patrolid = patrolid;
    }


    public void setPointtype( String pointtype ){
        this.pointtype = pointtype;
    }

}
