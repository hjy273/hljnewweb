package com.cabletech.watchinfo.beans;

import java.util.*;

import com.cabletech.commons.base.*;

public class WatchStaResultBean extends BaseBean{
    private Vector watchlist;
    private String contractor;
    private String begindate;
    private String enddate;
    private String datestring;
    private String watchamount;
    private String infoneeded;
    private String infodid;
    private String watchexecuterate;
    private String regionid;
    private String daterange;
    private String alertcount;
    private String undorate;
    private String workratenumber;
    private String undoratenumber;
    private String patrolname;
    public WatchStaResultBean(){
    }


    public void setWatchlist( Vector watchlist ){
        this.watchlist = watchlist;
    }


    public void setContractor( String contractor ){
        this.contractor = contractor;
    }


    public void setBegindate( String begindate ){
        this.begindate = begindate;
    }


    public void setEnddate( String enddate ){
        this.enddate = enddate;
    }


    public void setDatestring( String datestring ){
        this.datestring = datestring;
    }


    public void setWatchamount( String watchamount ){
        this.watchamount = watchamount;
    }


    public void setInfoneeded( String infoneeded ){
        this.infoneeded = infoneeded;
    }


    public void setInfodid( String infodid ){
        this.infodid = infodid;
    }


    public void setWatchexecuterate( String watchexecuterate ){
        this.watchexecuterate = watchexecuterate;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setDaterange( String daterange ){
        this.daterange = daterange;
    }


    public void setAlertcount( String alertcount ){
        this.alertcount = alertcount;
    }


    public void setUndorate( String undorate ){
        this.undorate = undorate;
    }


    public void setWorkratenumber( String workratenumber ){
        this.workratenumber = workratenumber;
    }


    public void setUndoratenumber( String undoratenumber ){
        this.undoratenumber = undoratenumber;
    }


    public void setPatrolname( String patrolname ){

        this.patrolname = patrolname;
    }


    public Vector getWatchlist(){
        return watchlist;
    }


    public String getContractor(){
        return contractor;
    }


    public String getBegindate(){
        return begindate;
    }


    public String getEnddate(){
        return enddate;
    }


    public String getDatestring(){
        return datestring;
    }


    public String getWatchamount(){
        return watchamount;
    }


    public String getInfoneeded(){
        return infoneeded;
    }


    public String getInfodid(){
        return infodid;
    }


    public String getWatchexecuterate(){
        return watchexecuterate;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getDaterange(){
        return daterange;
    }


    public String getAlertcount(){
        return alertcount;
    }


    public String getUndorate(){
        return undorate;
    }


    public String getWorkratenumber(){
        return workratenumber;
    }


    public String getUndoratenumber(){
        return undoratenumber;
    }


    public String getPatrolname(){

        return patrolname;
    }
}
