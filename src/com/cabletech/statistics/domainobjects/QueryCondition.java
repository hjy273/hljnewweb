package com.cabletech.statistics.domainobjects;

import java.util.*;

import com.cabletech.commons.base.*;

public class QueryCondition extends BaseBean{
    public QueryCondition(){
    }


    private String deptid;
    private Date begindate;
    private Date enddate;
    private String queryby;
    private String sublineid;
    private String patrolid;
    private String regionid;
    private String lineid;

    public String getDeptid(){

        return deptid;
    }


    public void setDeptid( String deptid ){

        this.deptid = deptid;
    }


    public Date getBegindate(){

        return begindate;
    }


    public void setBegindate( Date begindate ){

        this.begindate = begindate;
    }


    public Date getEnddate(){

        return enddate;
    }


    public void setEnddate( Date enddate ){

        this.enddate = enddate;
    }


    public String getQueryby(){

        return queryby;
    }


    public void setQueryby( String queryby ){

        this.queryby = queryby;
    }


    public String getSublineid(){

        return sublineid;
    }


    public void setSublineid( String sublineid ){

        this.sublineid = sublineid;
    }


    public String getPatrolid(){

        return patrolid;
    }


    public String getRegionid(){
        return regionid;
    }


    public void setPatrolid( String patrolid ){

        this.patrolid = patrolid;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public String getLineid(){
        return lineid;
    }


    public void setLineid( String lineid ){
        this.lineid = lineid;
    }

}
