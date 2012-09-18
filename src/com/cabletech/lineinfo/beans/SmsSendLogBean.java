package com.cabletech.lineinfo.beans;

import com.cabletech.commons.base.*;

public class SmsSendLogBean extends BaseBean{
    private String simid;
    private String msg_id;
    private String content;
    private String type;
    private String isresponded;
    private String issent;
    private String sendtime;
    private String senttime;
    public SmsSendLogBean(){
    }


    public String getSimid(){
        return simid;
    }


    public void setSimid( String simid ){
        this.simid = simid;
    }


    public String getMsg_id(){
        return msg_id;
    }


    public void setMsg_id( String msg_id ){
        this.msg_id = msg_id;
    }


    public String getSendtime(){
        return sendtime;
    }


    public void setSendtime( String sendtime ){
        this.sendtime = sendtime;
    }


    public String getContent(){
        return content;
    }


    public void setContent( String content ){
        this.content = content;
    }


    public String getType(){
        return type;
    }


    public void setType( String type ){
        this.type = type;
    }


    public String getIsresponded(){
        return isresponded;
    }


    public void setIsresponded( String isresponded ){
        this.isresponded = isresponded;
    }


    public String getIssent(){
        return issent;
    }


    public void setIssent( String issent ){
        this.issent = issent;
    }


    public String getSenttime(){
        return senttime;
    }


    public void setSenttime( String senttime ){
        this.senttime = senttime;
    }
}
