package com.cabletech.planinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class SubtaskBean extends BaseBean{

    private String subtaskname;
    private String subtaskid;

    public String getSubtaskname(){
        return subtaskname;
    }


    public String getSubtaskid(){
        return subtaskid;
    }


    public void setSubtaskname( String subtaskname ){
        this.subtaskname = subtaskname;
    }


    public void setSubtaskid( String subtaskid ){
        this.subtaskid = subtaskid;
    }


    // Public Methods
    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){
        subtaskname = "";
        subtaskid = "";
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
