package com.cabletech.planinfo.domainobjects;

import java.util.*;

import com.cabletech.commons.base.*;

public class YearMonthPlan extends BaseBean{
    private String id;
    private String year;
    private String month;
    private String plantype;
    private String ifinnercheck;
    private String approver;
    private String remark;
    private String status;
    private java.util.Date approvedate;
    private String planname;
    private String regionid;
    private Date createdate;
    private String creator;
    private String deptid = "";
    private String createuserid = "";

    public YearMonthPlan(){
    }


    public String getId(){
        return id;
    }


    public void setId( String id ){
        this.id = id;
    }


    public String getYear(){
        return year;
    }


    public void setYear( String year ){
        this.year = year;
    }


    public String getMonth(){
        return month;
    }


    public void setMonth( String month ){
        this.month = month;
    }


    public String getPlantype(){
        return plantype;
    }


    public void setPlantype( String plantype ){
        this.plantype = plantype;
    }


    public String getIfinnercheck(){
        return ifinnercheck;
    }


    public void setIfinnercheck( String ifinnercheck ){
        this.ifinnercheck = ifinnercheck;
    }


    public String getApprover(){
        return approver;
    }


    public void setApprover( String approver ){
        this.approver = approver;
    }


    public java.util.Date getApprovedate(){
        return approvedate;
    }


    public void setApprovedate( java.util.Date approvedate ){
        this.approvedate = approvedate;
    }


    public String getRemark(){
        return remark;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public String getStatus(){
        return status;
    }


    public String getPlanname(){
        return planname;
    }


    public String getRegionid(){
        return regionid;
    }


    public Date getCreatedate(){
        return createdate;
    }


    public String getCreator(){
        return creator;
    }


    public String getDeptid(){
        return deptid;
    }


    public String getCreateuserid(){
        return createuserid;
    }


    public void setStatus( String status ){
        this.status = status;
    }


    public void setPlanname( String planname ){
        this.planname = planname;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setCreatedate( Date createdate ){
        this.createdate = createdate;
    }


    public void setCreator( String creator ){
        this.creator = creator;
    }


    public void setDeptid( String deptid ){
        this.deptid = deptid;
    }


    public void setCreateuserid( String createuserid ){
        this.createuserid = createuserid;
    }
}
