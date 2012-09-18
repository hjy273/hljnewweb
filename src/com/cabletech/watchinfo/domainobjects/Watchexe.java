package com.cabletech.watchinfo.domainobjects;

import com.cabletech.commons.base.*;

public class Watchexe extends BaseDomainObject{
    public Watchexe(){
    }


    private String id;
    private String executorid;
    private String simid;
    private java.util.Date executetime;
    private String content;
    private String type;
    private String pid;
    private String lid;
    private String gpsCoordinate;
    private String queryby;

    public String getId(){
        return id;
    }


    public void setId( String id ){
        this.id = id;
    }


    public String getExecutorid(){
        return executorid;
    }


    public void setExecutorid( String executorid ){
        this.executorid = executorid;
    }


    public String getSimid(){
        return simid;
    }


    public void setSimid( String simid ){
        this.simid = simid;
    }


    public java.util.Date getExecutetime(){
        return executetime;
    }


    public void setExecutetime( java.util.Date executetime ){
        this.executetime = executetime;
    }


    public String getType(){
        return type;
    }


    public void setType( String type ){
        this.type = type;
    }


    public String getPid(){
        return pid;
    }


    public void setPid( String pid ){
        this.pid = pid;
    }


    public String getLid(){
        return lid;
    }


    public void setLid( String lid ){
        this.lid = lid;
    }


    public String getGpsCoordinate(){
        return gpsCoordinate;
    }


    public void setGpsCoordinate( String gpsCoordinate ){
        this.gpsCoordinate = gpsCoordinate;
    }


    public String getContent(){
        return content;
    }


    public String getQueryby(){
        return queryby;
    }


    public void setContent( String content ){
        this.content = content;
    }


    public void setQueryby( String queryby ){
        this.queryby = queryby;
    }
}
