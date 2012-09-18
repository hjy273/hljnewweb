package com.cabletech.baseinfo.beans;

import com.cabletech.commons.base.*;

public class SMSBean extends BaseBean{
    private String deviceid;
    private String password;
    private String seconds;
    private String count;
    private String mode;
    private String spid;
    private String message;
    private String simid;
    private String needreply;
    private String terminalmodel;
    private String sendtime;
    private String resptime;
    private String respstate;
    public SMSBean(){
    }


    public void setDeviceid( String deviceid ){
        this.deviceid = deviceid;
    }


    public void setPassword( String password ){
        this.password = password;
    }


    public void setSeconds( String seconds ){
        this.seconds = seconds;
    }


    public void setCount( String count ){
        this.count = count;
    }


    public void setMode( String mode ){
        this.mode = mode;
    }


    public void setSpid( String spid ){
        this.spid = spid;
    }


    public void setMessage( String message ){
        this.message = message;
    }


    public void setSimid( String simid ){
        this.simid = simid;
    }


    public void setNeedreply( String needreply ){
        this.needreply = needreply;
    }


    public void setTerminalmodel( String terminalmodel ){
        this.terminalmodel = terminalmodel;
    }


    public void setSendtime( String sendtime ){
        this.sendtime = sendtime;
    }


    public void setResptime( String resptime ){
        this.resptime = resptime;
    }


    public void setRespstate( String respstate ){
        this.respstate = respstate;
    }


    public String getDeviceid(){
        return deviceid;
    }


    public String getPassword(){
        return password;
    }


    public String getSeconds(){
        return seconds;
    }


    public String getCount(){
        return count;
    }


    public String getMode(){
        return mode;
    }


    public String getSpid(){
        return spid;
    }


    public String getMessage(){
        return message;
    }


    public String getSimid(){
        return simid;
    }


    public String getNeedreply(){
        return needreply;
    }


    public String getTerminalmodel(){
        return terminalmodel;
    }


    public String getSendtime(){
        return sendtime;
    }


    public String getResptime(){
        return resptime;
    }


    public String getRespstate(){
        return respstate;
    }

}
