package com.cabletech.planinfo.domainobjects;

import java.util.*;

import com.cabletech.commons.base.*;

public class Planapprove extends BaseBean{
    private String id;
    private String planid;
    private String approvecontent;
    private String approver;
    private Date approvedate;
    private String remark;
    private String status;
    public Planapprove(){
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setPlanid( String planid ){
        this.planid = planid;
    }


    public void setApprovecontent( String approvecontent ){
        this.approvecontent = approvecontent;
    }


    public void setApprover( String approver ){
        this.approver = approver;
    }


    public void setApprovedate( Date approvedate ){
        this.approvedate = approvedate;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setStatus( String status ){
        this.status = status;
    }


    public String getId(){
        return id;
    }


    public String getPlanid(){
        return planid;
    }


    public String getApprovecontent(){
        return approvecontent;
    }


    public String getApprover(){
        return approver;
    }


    public Date getApprovedate(){
        return approvedate;
    }


    public String getRemark(){
        return remark;
    }


    public String getStatus(){
        return status;
    }
}
