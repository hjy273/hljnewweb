package com.cabletech.baseinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author not attributable
 * @version 1.0
 */
public class TaskOperationBean extends BaseBean{
    private String ID;
    private String operationName;
    private String operationDes;
    private String regionID;

    // Public Methods
    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){
        ID = "";
        operationName = "";
        operationDes = "";
        regionID = "";
    }


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


    public TaskOperationBean(){
    }
}
