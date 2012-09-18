package com.cabletech.statistics.domainobjects;

import java.util.*;

public class Plan{
    private String planid;
    private String planname;
    private Date begindate;
    private Date enddate;
    private Vector planpatrol;
    private String patrolid;
    private String patrolname;
    private String contractorid;
    private String contractorname;
    private String patrolrate;
    private String lossrate;
    private int plancount;
    private int realcount;
    private int losscount;
    private Vector taskvct;
    private String weeknum;
    private String creator;
    private String createdate;
    private String begindateStr;
    private String enddateStr;
    private String planpointCount = "";
    private String lineid;
    public Plan(){
    }


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


    public void setPlanpatrol( Vector planpatrol ){
        this.planpatrol = planpatrol;
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


    public void setPatrolrate( String patrolrate ){
        this.patrolrate = patrolrate;
    }


    public void setLossrate( String lossrate ){
        this.lossrate = lossrate;
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


    public void setTaskvct( Vector taskvct ){
        this.taskvct = taskvct;
    }


    public void setWeeknum( String weeknum ){
        this.weeknum = weeknum;
    }


    public void setCreator( String creator ){
        this.creator = creator;
    }


    public void setCreatedate( String createdate ){
        this.createdate = createdate;
    }


    public void setBegindateStr( String begindateStr ){
        this.begindateStr = begindateStr;
    }


    public void setEnddateStr( String enddateStr ){
        this.enddateStr = enddateStr;
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


    public Vector getPlanpatrol(){
        return planpatrol;
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


    public String getPatrolrate(){
        return patrolrate;
    }


    public String getLossrate(){
        return lossrate;
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


    public Vector getTaskvct(){
        return taskvct;
    }


    public String getWeeknum(){
        return weeknum;
    }


    public String getCreator(){
        return creator;
    }


    public String getCreatedate(){
        return createdate;
    }


    public String getBegindateStr(){
        return begindateStr;
    }


    public String getEnddateStr(){
        return enddateStr;
    }


    private void jbInit() throws Exception{
    }


    public String getLineid(){
        return lineid;
    }


    public String getPlanpointCount(){
        return planpointCount;
    }


    public void setLineid( String lineid ){
        this.lineid = lineid;
    }


    public void setPlanpointCount( String planpointCount ){
        this.planpointCount = planpointCount;
    }
}
