package com.cabletech.statistics.beans;

import com.cabletech.commons.base.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.utils.*;

public class ApproveRateBean extends BaseBean{
    private String contractorid;
    private String regionid;
    private String cyctype;
    private String plantype;
    private String beginyear;
    private String beginmonth;
    private String endyear;
    private String endmonth;
    private String approvetimes;
    private String beginYM;
    private String endYM;
    private String approverate;
    private String unapproverate;
    private String approveplantype;
    private String statisticyear;
    private String contractorname;
    private int plannum;
    private int appplannum;
    private String statistictime;
    private String ifhasrecord;
    private int unapproveplannum;
    private String appratenumber;
    private String unappratenumber;

    public ApproveRateBean(){
        statisticyear = DateUtil.getNowYearStr();
        endyear = DateUtil.getNowYearStr();
        beginyear = DateUtil.getNowYearStr();

    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setCyctype( String cyctype ){
        this.cyctype = cyctype;
    }


    public void setPlantype( String plantype ){
        this.plantype = plantype;
    }


    public void setBeginyear( String beginyear ){
        this.beginyear = beginyear;
    }


    public void setBeginmonth( String beginmonth ){
        this.beginmonth = beginmonth;
    }


    public void setEndyear( String endyear ){
        this.endyear = endyear;
    }


    public void setEndmonth( String endmonth ){
        this.endmonth = endmonth;
    }


    public void setApprovetimes( String approvetimes ){
        this.approvetimes = approvetimes;
    }


    public void setBeginYM( String beginYM ){
        this.beginYM = beginYM;
    }


    public void setEndYM( String endYM ){
        this.endYM = endYM;
    }


    public void setApproverate( String approverate ){
        this.approverate = approverate;
    }


    public void setUnapproverate( String unapproverate ){
        this.unapproverate = unapproverate;
    }


    public void setApproveplantype( String approveplantype ){
        this.approveplantype = approveplantype;
    }


    public void setStatisticyear( String statisticyear ){
        this.statisticyear = statisticyear;
    }


    public void setContractorname( String contractorname ){
        this.contractorname = contractorname;
    }


    public void setPlannum( int plannum ){
        this.plannum = plannum;
    }


    public void setAppplannum( int appplannum ){
        this.appplannum = appplannum;
    }


    public void setStatistictime( String statistictime ){
        this.statistictime = statistictime;
    }


    public void setIfhasrecord( String ifhasrecord ){
        this.ifhasrecord = ifhasrecord;
    }


    public void setUnapproveplannum( int unapproveplannum ){
        this.unapproveplannum = unapproveplannum;
    }


    public void setAppratenumber( String appratenumber ){
        this.appratenumber = appratenumber;
    }


    public void setUnappratenumber( String unappratenumber ){
        this.unappratenumber = unappratenumber;
    }


    public String getContractorid(){
        return contractorid;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getCyctype(){
        return cyctype;
    }


    public String getPlantype(){
        return plantype;
    }


    public String getBeginyear(){
        return beginyear;
    }


    public String getBeginmonth(){
        return beginmonth;
    }


    public String getEndyear(){
        return endyear;
    }


    public String getEndmonth(){
        return endmonth;
    }


    public String getApprovetimes(){
        return approvetimes;
    }


    public String getBeginYM(){
        return beginYM;
    }


    public String getEndYM(){
        return endYM;
    }


    public String getApproverate(){
        return approverate;
    }


    public String getUnapproverate(){
        return unapproverate;
    }


    public String getApproveplantype(){
        return approveplantype;
    }


    public String getStatisticyear(){
        return statisticyear;
    }


    public String getContractorname(){
        return contractorname;
    }


    public int getPlannum(){
        return plannum;
    }


    public int getAppplannum(){
        return appplannum;
    }


    public String getStatistictime(){
        return statistictime;
    }


    public String getIfhasrecord(){
        return ifhasrecord;
    }


    public int getUnapproveplannum(){
        return unapproveplannum;
    }


    public String getAppratenumber(){
        return appratenumber;
    }


    public String getUnappratenumber(){
        return unappratenumber;
    }
}
