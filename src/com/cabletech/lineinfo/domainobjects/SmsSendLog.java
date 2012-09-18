package com.cabletech.lineinfo.domainobjects;

import com.cabletech.commons.base.*;

public class SmsSendLog extends BaseBean{
    private String simid;
    private String msg_id;
    private java.util.Date sendtime;
    private String content;
    private String type;
    private String isresponded;
    private String issent;
    private java.util.Date senttime;
    public SmsSendLog(){
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


    public java.util.Date getSendtime(){
        return sendtime;
    }


    public void setSendtime( java.util.Date sendtime ){
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


    public java.util.Date getSenttime(){
        return senttime;
    }


    public void setSenttime( java.util.Date senttime ){
        this.senttime = senttime;
    }

}
