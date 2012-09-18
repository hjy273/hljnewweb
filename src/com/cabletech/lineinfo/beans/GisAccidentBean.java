package com.cabletech.lineinfo.beans;

import com.cabletech.commons.base.*;

public class GisAccidentBean extends BaseBean{
    private String keyid;
    private String patrol;
    private String simid;
    private String sendtime;
    private String optype;
    private String status;
    private String point;
    private String subline;
    private String alerttime;
    public GisAccidentBean(){
    }


    public void setKeyid( String keyid ){
        this.keyid = keyid;
    }


    public void setPatrol( String patrol ){
        this.patrol = patrol;
    }


    public void setSimid( String simid ){
        this.simid = simid;
    }


    public void setSendtime( String sendtime ){
        this.sendtime = sendtime;
    }


    public void setOptype( String optype ){
        this.optype = optype;
    }


    public void setStatus( String status ){
        this.status = status;
    }


    public void setPoint( String point ){
        this.point = point;
    }


    public void setSubline( String subline ){
        this.subline = subline;
    }


    public void setAlerttime( String alerttime ){
        this.alerttime = alerttime;
    }


    public String getKeyid(){
        return keyid;
    }


    public String getPatrol(){
        return patrol;
    }


    public String getSimid(){
        return simid;
    }


    public String getSendtime(){
        return sendtime;
    }


    public String getOptype(){
        return optype;
    }


    public String getStatus(){
        return status;
    }


    public String getPoint(){
        return point;
    }


    public String getSubline(){
        return subline;
    }


    public String getAlerttime(){
        return alerttime;
    }
}
