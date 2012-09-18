package com.cabletech.lineinfo.beans;

import com.cabletech.commons.base.*;

public class GISTemppointbean extends BaseBean{
    private String pointid;
    private String pointname;
    private String gps;
    private String sim;
    private String patrolname;
    private String createtime;
    public GISTemppointbean(){
    }


    public void setPointid( String pointid ){
        this.pointid = pointid;
    }


    public void setPointname( String pointname ){
        this.pointname = pointname;
    }


    public void setGps( String gps ){
        this.gps = gps;
    }


    public void setSim( String sim ){
        this.sim = sim;
    }


    public void setPatrolname( String patrolname ){
        this.patrolname = patrolname;
    }


    public void setCreatetime( String createtime ){
        this.createtime = createtime;
    }


    public String getPointid(){
        return pointid;
    }


    public String getPointname(){
        return pointname;
    }


    public String getGps(){
        return gps;
    }


    public String getSim(){
        return sim;
    }


    public String getPatrolname(){
        return patrolname;
    }


    public String getCreatetime(){
        return createtime;
    }
}
