package com.cabletech.baseinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class DepartBean extends BaseBean{
    private String deptID;
    private String deptName;
    private String linkmanInfo;
    private String parentID;
    private String remark;
    private String state;
    private String regionid;

    public String getDeptName(){
        return deptName;
    }


    public String getDeptID(){
        return deptID;
    }


    public String getLinkmanInfo(){
        return linkmanInfo;
    }


    public String getParentID(){
        return parentID;
    }


    public String getRemark(){
        return remark;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setParentID( String parentID ){
        this.parentID = parentID;
    }


    public void setLinkmanInfo( String linkmanInfo ){
        this.linkmanInfo = linkmanInfo;
    }


    public void setDeptName( String deptName ){
        this.deptName = deptName;
    }


    public void setDeptID( String deptID ){
        this.deptID = deptID;
    }


    // Public Methods
    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){
        deptID = "";
        deptName = "";
        linkmanInfo = "";
        parentID = "";

        remark = "";
    }


    public String getState(){
        return state;
    }


    public void setState( String state ){
        this.state = state;
    }


    public String getRegionid(){
        return regionid;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
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
