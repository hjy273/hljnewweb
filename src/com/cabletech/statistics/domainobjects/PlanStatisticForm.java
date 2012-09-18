package com.cabletech.statistics.domainobjects;

import java.util.*;

public class PlanStatisticForm{
    private String formname;
    private String plancount;
    private String realcount;
    private String losscount;
    private String patrolrate;
    private String lossrate;
    private String formtype;
    private Vector dailypatrolvct;
    private Vector planvct;
    private int tasknum;
    private String ifhasrecord;
    private String cyctype;
    private String patrolratenumber;
    private String lossratenumber;
    private String seatchby = "";
    public PlanStatisticForm(){
        try{
            jbInit();
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
    }


    public void setFormname( String formname ){
        this.formname = formname;
    }


    public void setPlancount( String plancount ){
        this.plancount = plancount;
    }


    public void setRealcount( String realcount ){
        this.realcount = realcount;
    }


    public void setLosscount( String losscount ){
        this.losscount = losscount;
    }


    public void setPatrolrate( String patrolrate ){
        this.patrolrate = patrolrate;
    }


    public void setLossrate( String lossrate ){
        this.lossrate = lossrate;
    }


    public void setFormtype( String formtype ){
        this.formtype = formtype;
    }


    public void setDailypatrolvct( Vector dailypatrolvct ){
        this.dailypatrolvct = dailypatrolvct;
    }


    public void setPlanvct( Vector planvct ){
        this.planvct = planvct;
    }


    public void setTasknum( int tasknum ){
        this.tasknum = tasknum;
    }


    public void setIfhasrecord( String ifhasrecord ){
        this.ifhasrecord = ifhasrecord;
    }


    public void setCyctype( String cyctype ){
        this.cyctype = cyctype;
    }


    public void setPatrolratenumber( String patrolratenumber ){

        this.patrolratenumber = patrolratenumber;
    }


    public void setLossratenumber( String lossratenumber ){

        this.lossratenumber = lossratenumber;
    }


    public void setSeatchby( String seatchby ){
        this.seatchby = seatchby;
    }


    public String getFormname(){
        return formname;
    }


    public String getPlancount(){
        return plancount;
    }


    public String getRealcount(){
        return realcount;
    }


    public String getLosscount(){
        return losscount;
    }


    public String getPatrolrate(){
        return patrolrate;
    }


    public String getLossrate(){
        return lossrate;
    }


    public String getFormtype(){
        return formtype;
    }


    public Vector getDailypatrolvct(){
        return dailypatrolvct;
    }


    public Vector getPlanvct(){
        return planvct;
    }


    public int getTasknum(){
        return tasknum;
    }


    public String getIfhasrecord(){
        return ifhasrecord;
    }


    public String getCyctype(){
        return cyctype;
    }


    public String getPatrolratenumber(){

        return patrolratenumber;
    }


    public String getLossratenumber(){

        return lossratenumber;
    }


    public String getSeatchby(){
        return seatchby;
    }


    private void jbInit() throws Exception{
    }
}
