package com.cabletech.lineinfo.beans;

import com.cabletech.commons.base.*;

public class Planexecutebean extends BaseBean{
    private String id;
    private String pointname;
    private String gps;
    private String simid;
    private String patrolname;
    private String executetime;
    private String sublinename;
    public Planexecutebean(){
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setPointname( String pointname ){
        this.pointname = pointname;
    }


    public void setGps( String gps ){
        this.gps = gps;
    }


    public void setSimid( String simid ){
        this.simid = simid;
    }


    public void setPatrolname( String patrolname ){
        this.patrolname = patrolname;
    }


    public void setExecutetime( String executetime ){
        this.executetime = executetime;
    }


    public void setSublinename( String sublinename ){
        this.sublinename = sublinename;
    }


    public String getId(){
        return id;
    }


    public String getPointname(){
        return pointname;
    }


    public String getGps(){
        return gps;
    }


    public String getSimid(){
        return simid;
    }


    public String getPatrolname(){
        return patrolname;
    }


    public String getExecutetime(){
        return executetime;
    }


    public String getSublinename(){
        return sublinename;
    }
}
