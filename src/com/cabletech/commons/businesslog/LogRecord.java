package com.cabletech.commons.businesslog;

import java.text.*;

public class LogRecord
    implements java.io.Serializable{
    private String date;
    private String user;
    private String ip;
    private String message;
    private String memo;
    private String module;

    public LogRecord(){
        setDate( getNowDateString() );
    }


    public String getNowDateString(){
        java.util.Date date = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
        return formatter.format( date );
    }


    public String getDate(){
        return date;
    }


    public void setDate( String date ){
        this.date = date;
    }


    public String getUser(){
        return user;
    }


    public void setUser( String user ){
        this.user = user;
    }


    public String getIp(){
        return ip;
    }


    public void setIp( String ip ){
        this.ip = ip;
    }


    public String getMessage(){
        return message;
    }


    public void setMessage( String message ){
        this.message = message;
    }


    public String getMemo(){
        return memo;
    }


    public void setMemo( String memo ){
        this.memo = memo;
    }


    public String getModule(){
        return module;
    }


    public void setModule( String module ){
        this.module = module;
    }

}
