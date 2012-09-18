package com.cabletech.planstat.domainobjects;

import java.util.*;

import com.cabletech.commons.base.*;

public class PlanPatrolStat extends BaseBean{
    private String ppid;
    private String executorid;
    private java.util.Date statdate;
    private Integer planpoint;
    private Integer factpoint;
    private Integer trouble;
    private Float overallpatrolp;
    private String regionid;
    private String contractorid;
    private Date factdate;
    public PlanPatrolStat(){
    }


    public void setPpid( String ppid ){
        this.ppid = ppid;
    }


    public void setExecutorid( String executorid ){
        this.executorid = executorid;
    }


    public void setStatdate( java.util.Date statdate ){
        this.statdate = statdate;
    }


    public void setPlanpoint( Integer planpoint ){
        this.planpoint = planpoint;
    }


    public void setFactpoint( Integer factpoint ){
        this.factpoint = factpoint;
    }


    public void setTrouble( Integer trouble ){
        this.trouble = trouble;
    }


    public void setOverallpatrolp( Float overallpatrolp ){
        this.overallpatrolp = overallpatrolp;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public void setFactdate( Date factdate ){
        this.factdate = factdate;
    }


    public String getPpid(){
        return ppid;
    }


    public String getExecutorid(){
        return executorid;
    }


    public java.util.Date getStatdate(){
        return statdate;
    }


    public Integer getPlanpoint(){
        return planpoint;
    }


    public Integer getFactpoint(){
        return factpoint;
    }


    public Integer getTrouble(){
        return trouble;
    }


    public Float getOverallpatrolp(){
        return overallpatrolp;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getContractorid(){
        return contractorid;
    }


    public Date getFactdate(){
        return factdate;
    }


    public String toString(){
        return ppid + "-" + executorid + "-" + statdate + "-" + planpoint + "-" + factpoint + "-" + trouble
            + "-" + overallpatrolp + "-" + regionid + "-" + contractorid + "-" + factdate ;

    }

}
