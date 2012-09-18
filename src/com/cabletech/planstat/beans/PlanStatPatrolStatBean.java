package com.cabletech.planstat.beans;

import com.cabletech.commons.base.BaseBean;

public class PlanStatPatrolStatBean extends BaseBean{
    public PlanStatPatrolStatBean(){
    }

    private String id;
    private String planid;
    private String ppid;

    public void setId( String id ){
        this.id = id;
    }


    public void setPlanid( String planid ){
        this.planid = planid;
    }


    public void setPpid( String ppid ){
        this.ppid = ppid;
    }


    public String getId(){
        return id;
    }


    public String getPlanid(){
        return planid;
    }


    public String getPpid(){
        return ppid;
    }


    public String toString(){
        return id + "-" + planid + "-" + ppid;

    }

}
