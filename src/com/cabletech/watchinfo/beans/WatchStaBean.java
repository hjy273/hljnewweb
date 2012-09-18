package com.cabletech.watchinfo.beans;

import com.cabletech.commons.base.*;

public class WatchStaBean extends BaseBean{
    private String statype;
    private String datetype;
    private String executorid;
    private String watchid;
    private String contractorid;
    private String begindate;
    private String enddate;
    private String year;
    private String month;
    private String regionid;
    private String queryname;
    
    public WatchStaBean(){
    }

    

    public String getQueryname() {
		return queryname;
	}



	public void setQueryname(String queryname) {
		this.queryname = queryname;
	}



	public void setStatype( String statype ){
        this.statype = statype;
    }


    public void setDatetype( String datetype ){
        this.datetype = datetype;
    }


    public void setExecutorid( String executorid ){
        this.executorid = executorid;
    }


    public void setWatchid( String watchid ){
        this.watchid = watchid;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public void setBegindate( String begindate ){
        this.begindate = begindate;
    }


    public void setEnddate( String enddate ){
        this.enddate = enddate;
    }


    public void setYear( String year ){
        this.year = year;
    }


    public void setMonth( String month ){
        this.month = month;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public String getStatype(){
        return statype;
    }


    public String getDatetype(){
        return datetype;
    }


    public String getExecutorid(){
        return executorid;
    }


    public String getWatchid(){
        return watchid;
    }


    public String getContractorid(){
        return contractorid;
    }


    public String getBegindate(){
        return begindate;
    }


    public String getEnddate(){
        return enddate;
    }


    public String getYear(){
        return year;
    }


    public String getMonth(){
        return month;
    }


    public String getRegionid(){
        return regionid;
    }
}
