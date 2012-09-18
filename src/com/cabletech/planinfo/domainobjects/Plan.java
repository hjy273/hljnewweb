package com.cabletech.planinfo.domainobjects;

import com.cabletech.commons.base.*;

public class Plan extends BaseBean{
    private String id;
    private String executorid;
    private String taskid;
    private String creator;
    private java.util.Date createdate;
    private String status;
    private java.util.Date begindate;
    private java.util.Date enddate;
    private String regionid;
    private String approver;
    private java.util.Date approvedate;
    private String planname;
    private String ifinnercheck;
    private String patrolmode;
    public String getPatrolmode() {
		return patrolmode;
	}


	public void setPatrolmode(String patrolmode) {
		this.patrolmode = patrolmode;
	}


	public String getId(){
        return id;
    }


    public void setId( String id ){
        this.id = id;
    }


    public String getExecutorid(){
        return executorid;
    }


    public void setExecutorid( String executorid ){
        this.executorid = executorid;
    }


    public String getTaskid(){
        return taskid;
    }


    public void setTaskid( String taskid ){
        this.taskid = taskid;
    }


    public String getCreator(){
        return creator;
    }


    public void setCreator( String creator ){
        this.creator = creator;
    }


    public java.util.Date getCreatedate(){
        return createdate;
    }


    public void setCreatedate( java.util.Date createdate ){
        this.createdate = createdate;
    }


    public String getStatus(){
        return status;
    }


    public void setStatus( String status ){
        this.status = status;
    }


    public java.util.Date getBegindate(){
        return begindate;
    }


    public void setBegindate( java.util.Date begindate ){
        this.begindate = begindate;
    }


    public java.util.Date getEnddate(){
        return enddate;
    }


    public void setEnddate( java.util.Date enddate ){
        this.enddate = enddate;
    }


    public String getRegionid(){
        return regionid;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
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


    public String getPlanname(){
        return planname;
    }


    public String getIfinnercheck(){
        return ifinnercheck;
    }


    public void setApprovedate( java.util.Date approvedate ){
        this.approvedate = approvedate;
    }


    public void setPlanname( String planname ){
        this.planname = planname;
    }


    public void setIfinnercheck( String ifinnercheck ){
        this.ifinnercheck = ifinnercheck;
    }

}
