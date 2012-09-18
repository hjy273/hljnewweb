package com.cabletech.lineinfo.beans;

import org.apache.struts.action.*;
import com.cabletech.commons.base.*;

public class ListBean extends BaseBean{

    private String value;
    private String lable;
    public ListBean(){
    }


    public void reset( ActionMapping mapping,
        javax.servlet.http.HttpServletRequest request ){
    }


    public String getValue(){
        return value;
    }


    public void setValue( String value ){
        this.value = value;
    }


    public String getLable(){
        return lable;
    }


    public void setLable( String lable ){
        this.lable = lable;
    }

}
