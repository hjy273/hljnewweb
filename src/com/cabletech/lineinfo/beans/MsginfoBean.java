package com.cabletech.lineinfo.beans;

import com.cabletech.commons.base.*;

public class MsginfoBean extends BaseBean{

    private String msgID;
    private String sendID;
    private String acceptID;
    private String simID;
    private String msgTime;
    private String msgInfo;
    private String msgType;
    public MsginfoBean(){
    }


    public String getMsgID(){
        return msgID;
    }


    public void setMsgID( String msgID ){
        this.msgID = msgID;
    }


    public String getSendID(){
        return sendID;
    }


    public void setSendID( String sendID ){
        this.sendID = sendID;
    }


    public String getAcceptID(){
        return acceptID;
    }


    public void setAcceptID( String acceptID ){
        this.acceptID = acceptID;
    }


    public String getSimID(){
        return simID;
    }


    public void setSimID( String simID ){
        this.simID = simID;
    }


    public String getMsgTime(){
        return msgTime;
    }


    public void setMsgTime( String msgTime ){
        this.msgTime = msgTime;
    }


    public String getMsgInfo(){
        return msgInfo;
    }


    public void setMsgInfo( String msgInfo ){
        this.msgInfo = msgInfo;
    }


    public String getMsgType(){
        return msgType;
    }


    public void setMsgType( String msgType ){
        this.msgType = msgType;
    }
}
