package com.cabletech.statistics.domainobjects;

import java.util.*;

public class LossPatrol{
    public LossPatrol(){
    }


    private String planid;
    private String planname;
    private Date begindate;
    private Date enddate;
    private String patrolid;
    private String patrolname;
    private String contractorid;
    private String contractorname;
    private int plancount;
    private int realcount;
    private int losscount;
    private String pointid;
    private String pointname;
    private String position;
    private String sublineid;
    private String sublinename;
    private String linetype;

    public void setPlanid( String planid ){
        this.planid = planid;
    }


    public void setPlanname( String planname ){
        this.planname = planname;
    }


    public void setBegindate( Date begindate ){
        this.begindate = begindate;
    }


    public void setEnddate( Date enddate ){
        this.enddate = enddate;
    }


    public void setPatrolid( String patrolid ){
        this.patrolid = patrolid;
    }


    public void setPatrolname( String patrolname ){
        this.patrolname = patrolname;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public void setContractorname( String contractorname ){
        this.contractorname = contractorname;
    }


    public void setPlancount( int plancount ){
        this.plancount = plancount;
    }


    public void setRealcount( int realcount ){
        this.realcount = realcount;
    }


    public void setLosscount( int losscount ){
        this.losscount = losscount;
    }


    public void setPointid( String pointid ){
        this.pointid = pointid;
    }


    public void setPointname( String pointname ){
        this.pointname = pointname;
    }


    public void setPosition( String position ){
        this.position = position;
    }


    public void setSublineid( String sublineid ){
        this.sublineid = sublineid;
    }


    public void setSublinename( String sublinename ){
        this.sublinename = sublinename;
    }


    public void setLinetype( String linetype ){
        this.linetype = linetype;
    }


    public String getPlanid(){
        return planid;
    }


    public String getPlanname(){
        return planname;
    }


    public Date getBegindate(){
        return begindate;
    }


    public Date getEnddate(){
        return enddate;
    }


    public String getPatrolid(){
        return patrolid;
    }


    public String getPatrolname(){
        return patrolname;
    }


    public String getContractorid(){
        return contractorid;
    }


    public String getContractorname(){
        return contractorname;
    }


    public int getPlancount(){
        return plancount;
    }


    public int getRealcount(){
        return realcount;
    }


    public int getLosscount(){
        return losscount;
    }


    public String getPointid(){
        return pointid;
    }


    public String getPointname(){
        return pointname;
    }


    public String getPosition(){
        return position;
    }


    public String getSublineid(){
        return sublineid;
    }


    public String getSublinename(){
        return sublinename;
    }


    public String getLinetype(){
        return linetype;
    }


    private void jbInit() throws Exception{
    }

}
