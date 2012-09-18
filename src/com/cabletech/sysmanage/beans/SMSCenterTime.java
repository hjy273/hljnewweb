package com.cabletech.sysmanage.beans;

import com.cabletech.commons.base.*;

public class SMSCenterTime extends BaseBean{
    private String begintime;
    private String endtime;
    public SMSCenterTime(){
    }


    public void setBegintime( String begintime ){
        this.begintime = begintime;
    }


    public void setEndtime( String endtime ){
        this.endtime = endtime;
    }


    public String getBegintime(){
        return begintime;
    }


    public String getEndtime(){
        return endtime;
    }
}
