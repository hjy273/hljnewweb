package com.cabletech.planinfo.beans;

import com.cabletech.commons.base.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.utils.*;

public class PlanBean extends BaseBean{
    private String id;
    private String executorid;
    private String creator;
    private String createdate;
    private String status;
    private String begindate;
    private String enddate;
    private String regionid;
    private String approver;
    private String approvedate;
    private String ifinnercheck;
    private String planname;
    private String approvecontent;
    private String applyformdate;
    private String temp = "";
    private String patrolmode;
    public PlanBean(){
        applyformdate = DateUtil.getNowDateString();
    }


    private void jbInit() throws Exception{
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setExecutorid( String executorid ){
        this.executorid = executorid;
    }


    public void setCreator( String creator ){
        this.creator = creator;
    }


    public void setCreatedate( String createdate ){
        this.createdate = createdate;
    }


    public void setStatus( String status ){
        this.status = status;
    }


    public void setBegindate( String begindate ){
        this.begindate = begindate;
    }


    public void setEnddate( String enddate ){
        this.enddate = enddate;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setApprover( String approver ){
        this.approver = approver;
    }


    public void setApprovedate( String approvedate ){
        this.approvedate = approvedate;
    }


    public void setIfinnercheck( String ifinnercheck ){
        this.ifinnercheck = ifinnercheck;
    }


    public void setPlanname( String planname ){
        this.planname = planname;
    }


    public void setApprovecontent( String approvecontent ){
        this.approvecontent = approvecontent;
    }


    public void setApplyformdate( String applyformdate ){
        this.applyformdate = applyformdate;
    }


    public void setTemp( String temp ){
        this.temp = temp;
    }


    public String getId(){
        return id;
    }


    public String getExecutorid(){
        return executorid;
    }


    public String getCreator(){
        return creator;
    }


    public String getCreatedate(){
        return createdate;
    }


    public String getStatus(){
        return status;
    }


    public String getBegindate(){
        return begindate;
    }


    public String getEnddate(){
        return enddate;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getApprover(){
        return approver;
    }


    public String getApprovedate(){
        return approvedate;
    }


    public String getIfinnercheck(){
        return ifinnercheck;
    }


    public String getPlanname(){
        return planname;
    }


    public String getApprovecontent(){
        return approvecontent;
    }


    public String getApplyformdate(){
        return applyformdate;
    }


    public String getTemp(){
        return temp;
    }


	public String getPatrolmode() {
		return patrolmode;
	}


	public void setPatrolmode(String patrolmode) {
		this.patrolmode = patrolmode;
	}

}
