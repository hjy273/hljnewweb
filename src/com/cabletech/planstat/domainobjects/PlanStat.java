package com.cabletech.planstat.domainobjects;

import com.cabletech.commons.base.BaseBean;

public class PlanStat extends BaseBean{
    private String planid;
    private String planname;
    private String contractorid;
    private String executorid;
    private java.util.Date startdate;
    private java.util.Date enddate;
    private java.lang.Integer planpointn;
    private java.lang.Integer factpointn;
    private java.lang.Integer leakpointn;
    private java.lang.Integer planpoint;
    private java.lang.Integer factpoint;
    private java.lang.Float patrolp;
    private java.lang.Integer keypoint;
    private java.lang.Integer leakkeypoint;
    private java.lang.Float patrolkm;
    private java.util.Date createdate;
    private String examineresult;
    private String patrolmode;
    private String nofinishreason;
    
    public String getNofinishreason() {
		return nofinishreason;
	}


	public void setNofinishreason(String nofinishreason) {
		this.nofinishreason = nofinishreason;
	}


	public PlanStat(){
    }


    public String getPlanid(){
        return planid;
    }


    public void setPlanid( String planid ){
        this.planid = planid;
    }


    public String getPlanname(){
        return planname;
    }


    public void setPlanname( String planname ){
        this.planname = planname;
    }


    public String getContractorid(){
        return contractorid;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public String getExecutorid(){
        return executorid;
    }


    public void setExecutorid( String executorid ){
        this.executorid = executorid;
    }


    public java.util.Date getStartdate(){
        return startdate;
    }


    public void setStartdate( java.util.Date startdate ){
        this.startdate = startdate;
    }


    public java.util.Date getEnddate(){
        return enddate;
    }


    public void setEnddate( java.util.Date enddate ){
        this.enddate = enddate;
    }


    public java.lang.Integer getPlanpointn(){
        return planpointn;
    }


    public void setPlanpointn( java.lang.Integer planpointn ){
        this.planpointn = planpointn;
    }


    public java.lang.Integer getFactpointn(){
        return factpointn;
    }


    public void setFactpointn( java.lang.Integer factpointn ){
        this.factpointn = factpointn;
    }


    public java.lang.Integer getLeakpointn(){
        return leakpointn;
    }


    public void setLeakpointn( java.lang.Integer leakpointn ){
        this.leakpointn = leakpointn;
    }


    public java.lang.Integer getPlanpoint(){
        return planpoint;
    }


    public void setPlanpoint( java.lang.Integer planpoint ){
        this.planpoint = planpoint;
    }


    public java.lang.Integer getFactpoint(){
        return factpoint;
    }


    public void setFactpoint( java.lang.Integer factpoint ){
        this.factpoint = factpoint;
    }


    public java.lang.Float getPatrolp(){
        return patrolp;
    }


    public void setPatrolp( java.lang.Float patrolp ){
        this.patrolp = patrolp;
    }


    public java.lang.Integer getKeypoint(){
        return keypoint;
    }


    public void setKeypoint( java.lang.Integer keypoint ){
        this.keypoint = keypoint;
    }


    public java.lang.Integer getLeakkeypoint(){
        return leakkeypoint;
    }


    public void setLeakkeypoint( java.lang.Integer leakkeypoint ){
        this.leakkeypoint = leakkeypoint;
    }


    public java.lang.Float getPatrolkm(){
        return patrolkm;
    }


    public void setPatrolkm( java.lang.Float patrolkm ){
        this.patrolkm = patrolkm;
    }


    public java.util.Date getCreatedate(){
        return createdate;
    }


    public void setCreatedate( java.util.Date createdate ){
        this.createdate = createdate;
    }


    public String toString(){
        return planname + "-" + contractorid + "-" + executorid + "-" + startdate + "-" + enddate + "-" + planpointn
            + "-" + factpointn + "-" + leakpointn + "-" + planpoint + "-" + factpoint + "-" + patrolp + "-" + keypoint
            + "-" + leakkeypoint + "-" + patrolkm + "-" + createdate;

    }


	public String getExamineresult() {
		return examineresult;
	}


	public void setExamineresult(String examineresult) {
		this.examineresult = examineresult;
	}


	public String getPatrolmode() {
		return patrolmode;
	}


	public void setPatrolmode(String patrolmode) {
		this.patrolmode = patrolmode;
	}

}
