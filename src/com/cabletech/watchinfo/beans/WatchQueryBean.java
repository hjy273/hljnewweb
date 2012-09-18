package com.cabletech.watchinfo.beans;

import com.cabletech.commons.base.*;

public class WatchQueryBean extends BaseBean{
    private String watchid;
    private String sublineid;
    private String executorid;
    private String begindate;
    private String enddate;
    private String queryby;
    private String regionid;
    private String bedited;
    private String statype;
    public WatchQueryBean(){
    }


    public void setWatchid( String watchid ){
        this.watchid = watchid;
    }


    public void setSublineid( String sublineid ){
        this.sublineid = sublineid;
    }


    public void setExecutorid( String executorid ){
        this.executorid = executorid;
    }


    public void setBegindate( String begindate ){
        this.begindate = begindate;
    }


    public void setEnddate( String enddate ){
        this.enddate = enddate;
    }


    public void setQueryby( String queryby ){
        this.queryby = queryby;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setBedited( String bedited ){
        this.bedited = bedited;
    }


    public void setStatype( String statype ){
        this.statype = statype;
    }


    public String getWatchid(){
        return watchid;
    }


    public String getSublineid(){
        return sublineid;
    }


    public String getExecutorid(){
        return executorid;
    }


    public String getBegindate(){
        return begindate;
    }


    public String getEnddate(){
        return enddate;
    }


    public String getQueryby(){
        return queryby;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getBedited(){
        return bedited;
    }


    public String getStatype(){
        return statype;
    }

}
