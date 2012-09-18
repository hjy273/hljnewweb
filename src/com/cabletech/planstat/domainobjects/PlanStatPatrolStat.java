package com.cabletech.planstat.domainobjects;

import com.cabletech.commons.base.*;

public class PlanStatPatrolStat extends BaseBean{
    private String id;
    private String planid;
    private String ppid;
    public PlanStatPatrolStat(){
    }


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
        return id + "-" + planid + "-" + ppid ;

    }

}
