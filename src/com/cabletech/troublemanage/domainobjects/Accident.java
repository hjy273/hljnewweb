package com.cabletech.troublemanage.domainobjects;

import java.util.*;

import com.cabletech.commons.base.*;

public class Accident extends BaseDomainObject{
    private String keyid;
    private String patrolid;
    private String simid;
    private Date sendtime;
    private String optype;
    private String gpscoordinate;
    private String pid;
    private String lid;
    private String operationcode;
    private Date befortime;
    private Date noticetime;
    private String cooperateman;
    private Date testtime;
    private String testman;
    private String distance;
    private String realdistance;
    private String fixedman;
    private String monitor;
    private String commander;
    private String resonandfix;
    private String breportman;
    private String bconfirmman;
    private String regionid;
    private String isallblock;
    private String status;
    private String year;
    private String month;
    private String datumid;
    public Accident(){
    }


    public void setKeyid( String keyid ){
        this.keyid = keyid;
    }


    public void setPatrolid( String patrolid ){
        this.patrolid = patrolid;
    }


    public void setSimid( String simid ){
        this.simid = simid;
    }


    public void setSendtime( Date sendtime ){
        this.sendtime = sendtime;
    }


    public void setOptype( String optype ){
        this.optype = optype;
    }


    public void setGpscoordinate( String gpscoordinate ){
        this.gpscoordinate = gpscoordinate;
    }


    public void setPid( String pid ){
        this.pid = pid;
    }


    public void setLid( String lid ){
        this.lid = lid;
    }


    public void setOperationcode( String operationcode ){

        this.operationcode = operationcode;
    }


    public void setBefortime( Date befortime ){
        this.befortime = befortime;
    }


    public void setNoticetime( Date noticetime ){
        this.noticetime = noticetime;
    }


    public void setCooperateman( String cooperateman ){
        this.cooperateman = cooperateman;
    }


    public void setTesttime( Date testtime ){
        this.testtime = testtime;
    }


    public void setTestman( String testman ){
        this.testman = testman;
    }


    public void setDistance( String distance ){
        this.distance = distance;
    }


    public void setRealdistance( String realdistance ){
        this.realdistance = realdistance;
    }


    public void setFixedman( String fixedman ){
        this.fixedman = fixedman;
    }


    public void setMonitor( String monitor ){
        this.monitor = monitor;
    }


    public void setCommander( String commander ){
        this.commander = commander;
    }


    public void setResonandfix( String resonandfix ){
        this.resonandfix = resonandfix;
    }


    public void setBreportman( String breportman ){
        this.breportman = breportman;
    }


    public void setBconfirmman( String bconfirmman ){
        this.bconfirmman = bconfirmman;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setIsallblock( String isallblock ){
        this.isallblock = isallblock;
    }


    public void setStatus( String status ){
        this.status = status;
    }


    public void setYear( String year ){
        this.year = year;
    }


    public void setMonth( String month ){
        this.month = month;
    }


    public void setDatumid( String datumid ){
        this.datumid = datumid;
    }


    public String getKeyid(){
        return keyid;
    }


    public String getPatrolid(){
        return patrolid;
    }


    public String getSimid(){
        return simid;
    }


    public Date getSendtime(){
        return sendtime;
    }


    public String getOptype(){
        return optype;
    }


    public String getGpscoordinate(){
        return gpscoordinate;
    }


    public String getPid(){
        return pid;
    }


    public String getLid(){
        return lid;
    }


    public String getOperationcode(){

        return operationcode;
    }


    public Date getBefortime(){
        return befortime;
    }


    public Date getNoticetime(){
        return noticetime;
    }


    public String getCooperateman(){
        return cooperateman;
    }


    public Date getTesttime(){
        return testtime;
    }


    public String getTestman(){
        return testman;
    }


    public String getDistance(){
        return distance;
    }


    public String getRealdistance(){
        return realdistance;
    }


    public String getFixedman(){
        return fixedman;
    }


    public String getMonitor(){
        return monitor;
    }


    public String getCommander(){
        return commander;
    }


    public String getResonandfix(){
        return resonandfix;
    }


    public String getBreportman(){
        return breportman;
    }


    public String getBconfirmman(){
        return bconfirmman;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getIsallblock(){
        return isallblock;
    }


    public String getStatus(){
        return status;
    }


    public String getYear(){
        return year;
    }


    public String getMonth(){
        return month;
    }


    public String getDatumid(){
        return datumid;
    }

}
