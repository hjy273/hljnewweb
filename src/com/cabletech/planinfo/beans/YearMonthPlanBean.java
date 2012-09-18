package com.cabletech.planinfo.beans;

import java.util.*;

import com.cabletech.commons.base.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.utils.*;

public class YearMonthPlanBean extends BaseBean{
    private String id;
    private String year;
    private String month;
    private String plantype;
    private String ifinnercheck;
    private String approver;
    private String approvedate;
    private String remark;
    private String status;
    private String planname;
    private String regionid;
    private String creator;
    private String createdate;
    private String approvecontent;
    private String applyformdate;
    private String deptid = "";
    private String createuserid = "";
    private String phone = "";
    private List tasklist;

    public YearMonthPlanBean(){
        year = DateUtil.getNowYearStr();
        //month = DateUtil.getNowMonthStr();
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


    public String getApprovedate(){
        return approvedate;
    }


    public void setApprovedate( String approvedate ){
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


    public String getCreator(){
        return creator;
    }


    public String getCreatedate(){
        return createdate;
    }


    public String getApprovecontent(){
        return approvecontent;
    }


    public String getApplyformdate(){
        return applyformdate;
    }


    public String getDeptid(){
        return deptid;
    }


    public String getPhone(){
        return phone;
    }


    public String getCreateuserid(){
        return createuserid;
    }


    public List getTasklist(){
        return tasklist;
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


    public void setCreator( String creator ){
        this.creator = creator;
    }


    public void setCreatedate( String createdate ){
        this.createdate = createdate;
    }


    public void setApprovecontent( String approvecontent ){
        this.approvecontent = approvecontent;
    }


    public void setApplyformdate( String applyformdate ){
        this.applyformdate = applyformdate;
    }


    public void setDeptid( String deptid ){
        this.deptid = deptid;
    }


    public void setPhone( String phone ){
        this.phone = phone;
    }


    public void setCreateuserid( String createuserid ){
        this.createuserid = createuserid;
    }


    public void setTasklist( List tasklist ){
        this.tasklist = tasklist;
    }
}
