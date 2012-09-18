package com.cabletech.baseinfo.domainobjects;

/**
 *
 * <p>Title: bean taskoperition</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author alien
 * @version 1.0
 */
public class TaskOperation{
    public void setOperationName( String operationName ){
        this.operationName = operationName;
    }


    public void setOperationDes( String operationDes ){
        this.operationDes = operationDes;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    public String getOperationName(){
        return operationName;
    }


    public String getOperationDes(){
        return operationDes;
    }


    public String getRegionID(){
        return regionID;
    }


    public void setID( String ID ){
        this.ID = ID;
    }


    public String getID(){
        return ID;
    }


    public TaskOperation(){
    }


    private String regionID;

    private String operationDes;

    private String operationName;

    private String ID;
}
