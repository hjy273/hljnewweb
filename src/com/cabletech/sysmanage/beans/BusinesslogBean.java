package com.cabletech.sysmanage.beans;

import com.cabletech.commons.base.*;

public class BusinesslogBean extends BaseBean{

    private String logdate;
    private String ip;
    private String username;
    private String module;
    private String message;
    private String memo;
    private String fromdate;
    private String enddate;

    public BusinesslogBean(){
    }


    public void setLogdate( String logdate ){
        this.logdate = logdate;
    }


    public void setIp( String ip ){
        this.ip = ip;
    }


    public void setUsername( String username ){
        this.username = username;
    }


    public void setModule( String module ){
        this.module = module;
    }


    public void setMessage( String message ){
        this.message = message;
    }


    public void setMemo( String memo ){
        this.memo = memo;
    }


    public void setFromdate( String fromdate ){
        this.fromdate = fromdate;
    }


    public void setEnddate( String enddate ){
        this.enddate = enddate;
    }


    public String getLogdate(){
        return logdate;
    }


    public String getIp(){
        return ip;
    }


    public String getUsername(){
        return username;
    }


    public String getModule(){
        return module;
    }


    public String getMessage(){
        return message;
    }


    public String getMemo(){
        return memo;
    }


    public String getFromdate(){
        return fromdate;
    }


    public String getEnddate(){
        return enddate;
    }
}
