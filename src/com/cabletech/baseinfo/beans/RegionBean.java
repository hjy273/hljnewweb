package com.cabletech.baseinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Cable tech</p>
 * @author not attributable
 * @version 1.0
 */

public class RegionBean extends BaseBean{

    private String regionID;
    private String regionName;
    private String regionDes;
    private String state;
    private String parentregionid;

    // Public Methods

    public String getRegionID(){
        return regionID;
    }


    public void setRegionID( String regionID ){
        this.regionID = regionID;
    }


    public String getRegionName(){
        return regionName;
    }


    public void setRegionName( String regionName ){
        this.regionName = regionName;
    }


    public String getRegionDes(){
        return regionDes;
    }


    public void setRegionDes( String regionDes ){
        this.regionDes = regionDes;
    }


    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){

    }


    public String getState(){
        return state;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String getParentregionid(){
        return parentregionid;
    }


    public void setParentregionid( String parentregionid ){
        this.parentregionid = parentregionid;
    }

    /**
     * <p>Validate address.</p>
     *
     * @param errors
     * @param messages
     */
    /*
         public  ActionErrors validate(ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
        // Declare and initalize local variables.
        // Perform validator framework validations
         ActionErrors errors = super.validate(mapping, request);

         // Only need crossfield validations here
         if (true) {
             errors.add("password2",new ActionError("error.password.match"));
         }
         return errors;
         }*/

}
