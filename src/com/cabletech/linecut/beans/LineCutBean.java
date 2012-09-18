package com.cabletech.linecut.beans;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.util.DateUtil;

public class LineCutBean extends BaseBean{
    private String reid = "";
    private String begintime = "";
    private String endtime = "";
    private String contractorid = "";
    private String reuserid = "";
    private String contractorname = "";
    private String sublinename = "";
    private String username = "";
    private String retime = "";
    private String name = "";
    private String sublineid = "";
    private String deptname = "";
    private String reason = "";
    private String address = "";
    private String protime = "";
    private String prousetime = "";
    private String provalue = "";
    private String efsystem = "";
    private String reremark = "";
    private String reacce = "";
    private String audituserid = "";
    private String audittime = "";
    private String deptid = "";
    private String auditremark = "";
    private String auditresult = "";
    private String auditacce = "";
    private String workuserid = "";
    private String endvalue = "";
    private String manpower = "";
    private String usetime = "";
    private String essetime = "";
    private String phone = "";
    private String workremark = "";
    private String workacce = "";
    private String isarchive = "";
    private String archives = "";
    private String id = "";
    private String flag = "";
    private String workid = "";
    private String updoc = "";
    private String acceptacce = "";
    private String acceptresult = "";
    private String acceptremark = "";
    private String time = "";
    private String date = "";
    private String lineid = "";
    private String cutType = "";
    private String numerical = "";
    
    private String reuser="";
    private String audituser="";
    private String auusername="";
    private String paudittime ="";//验收时间
    private String aauditremark = "";
    
    
    public String getAauditremark() {
		return aauditremark;
	}


	public void setAauditremark(String aauditremark) {
		this.aauditremark = aauditremark;
	}


	public String getPaudittime() {
		return paudittime;
	}


	public void setPaudittime(String paudittime) {
		this.paudittime = paudittime;
	}


	public String getAudituser() {
		return audituser;
	}


	public void setAudituser(String audituser) {
		this.audituser = audituser;
	}


	public String getAuusername() {
		return auusername;
	}


	public void setAuusername(String auusername) {
		this.auusername = auusername;
	}


	public String getReuser() {
		return reuser;
	}


	public void setReuser(String reuser) {
		this.reuser = reuser;
	}


	public LineCutBean(){
        super();
        begintime = DateUtil.getNowDateString( "yyyy/MM/dd" );
        endtime = DateUtil.getNowDateString( "yyyy/MM/dd" );
        audittime = DateUtil.getNowDateString( "yyyy/MM/dd" );
    }


    public void setReid( String reid ){
        this.reid = reid;
    }


    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public void setReuserid( String reuserid ){
        this.reuserid = reuserid;
    }


    public void setRetime( String retime ){
        this.retime = retime;
    }


    public void setName( String name ){
        this.name = name;
    }


    public void setSublineid( String sublineid ){
        this.sublineid = sublineid;
    }


    public void setReason( String reason ){
        this.reason = reason;
    }


    public void setAddress( String address ){
        this.address = address;
    }


    public void setProtime( String protime ){
        this.protime = protime;
    }


    public void setProusetime( String prousetime ){
        this.prousetime = prousetime;
    }


    public void setProvalue( String provalue ){
        this.provalue = provalue;
    }


    public void setEfsystem( String efsystem ){
        this.efsystem = efsystem;
    }


    public void setReremark( String reremark ){
        this.reremark = reremark;
    }


    public void setReacce( String reacce ){
        this.reacce = reacce;
    }


    public void setAudituserid( String audituserid ){
        this.audituserid = audituserid;
    }


    public void setAudittime( String audittime ){
        this.audittime = audittime;
    }


    public void setDeptid( String deptid ){
        this.deptid = deptid;
    }


    public void setAuditresult( String auditresult ){
        this.auditresult = auditresult;
    }


    public void setAuditremark( String auditremark ){
        this.auditremark = auditremark;
    }


    public void setAuditacce( String auditacce ){
        this.auditacce = auditacce;
    }


    public void setWorkuserid( String workuserid ){
        this.workuserid = workuserid;
    }


    public void setEndvalue( String endvalue ){
        this.endvalue = endvalue;
    }


    public void setManpower( String manpower ){
        this.manpower = manpower;
    }


    public void setUsetime( String usetime ){
        this.usetime = usetime;
    }


    public void setEssetime( String essetime ){
        this.essetime = essetime;
    }


    public void setWorkremark( String workremark ){
        this.workremark = workremark;
    }


    public void setWorkacce( String workacce ){
        this.workacce = workacce;
    }


    public void setArchives( String archives ){
        this.archives = archives;
    }


    public void setIsarchive( String isarchive ){
        this.isarchive = isarchive;
    }


    public void setContractorname( String contractorname ){
        this.contractorname = contractorname;
    }


    public void setUsername( String username ){
        this.username = username;
    }


    public void setSublinename( String sublinename ){
        this.sublinename = sublinename;
    }


    public void setBegintime( String begintime ){
        this.begintime = begintime;
    }


    public void setEndtime( String endtime ){
        this.endtime = endtime;
    }


    public void setDeptname( String deptname ){
        this.deptname = deptname;
    }


    public void setPhone( String phone ){
        this.phone = phone;
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setFlag( String flag ){
        this.flag = flag;
    }


    public void setWorkid( String workid ){
        this.workid = workid;
    }


    public void setUpdoc( String updoc ){
        this.updoc = updoc;
    }


    public void setAcceptacce( String acceptacce ){
        this.acceptacce = acceptacce;
    }


    public void setAcceptresult( String acceptresult ){
        this.acceptresult = acceptresult;
    }


    public void setAcceptremark( String acceptremark ){
        this.acceptremark = acceptremark;
    }


    public void setTime( String time ){
        this.time = time;
    }


    public void setDate( String date ){
        this.date = date;
    }


    public void setLineid( String lineid ){
        this.lineid = lineid;
    }


    public String getReid(){
        return reid;
    }


    public String getContractorid(){
        return contractorid;
    }


    public String getReuserid(){
        return reuserid;
    }


    public String getRetime(){
        return retime;
    }


    public String getName(){
        return name;
    }


    public String getSublineid(){
        return sublineid;
    }


    public String getReason(){
        return reason;
    }


    public String getAddress(){
        return address;
    }


    public String getProtime(){
        return protime;
    }


    public String getProusetime(){
        return prousetime;
    }


    public String getProvalue(){
        return provalue;
    }


    public String getEfsystem(){
        return efsystem;
    }


    public String getReremark(){
        return reremark;
    }


    public String getReacce(){
        return reacce;
    }


    public String getAudituserid(){
        return audituserid;
    }


    public String getAudittime(){
        return audittime;
    }


    public String getDeptid(){
        return deptid;
    }


    public String getAuditresult(){
        return auditresult;
    }


    public String getAuditremark(){
        return auditremark;
    }


    public String getAuditacce(){
        return auditacce;
    }


    public String getWorkuserid(){
        return workuserid;
    }


    public String getEndvalue(){
        return endvalue;
    }


    public String getManpower(){
        return manpower;
    }


    public String getUsetime(){
        return usetime;
    }


    public String getEssetime(){
        return essetime;
    }


    public String getWorkremark(){
        return workremark;
    }


    public String getWorkacce(){
        return workacce;
    }


    public String getArchives(){
        return archives;
    }


    public String getIsarchive(){
        return isarchive;
    }


    public String getContractorname(){
        return contractorname;
    }


    public String getUsername(){
        return username;
    }


    public String getSublinename(){
        return sublinename;
    }


    public String getBegintime(){
        return begintime;
    }


    public String getEndtime(){
        return endtime;
    }


    public String getDeptname(){
        return deptname;
    }


    public String getPhone(){
        return phone;
    }


    public String getId(){
        return id;
    }


    public String getFlag(){
        return flag;
    }


    public String getWorkid(){
        return workid;
    }


    public String getUpdoc(){
        return updoc;
    }


    public String getAcceptacce(){
        return acceptacce;
    }


    public String getAcceptresult(){
        return acceptresult;
    }


    public String getAcceptremark(){
        return acceptremark;
    }


    public String getTime(){
        return time;
    }


    public String getDate(){
        return date;
    }


    public String getLineid(){
        return lineid;
    }


	public String getCutType() {
		return cutType;
	}


	public void setCutType(String cutType) {
		this.cutType = cutType;
	}


	public String getNumerical() {
		return numerical;
	}


	public void setNumerical(String numerical) {
		this.numerical = numerical;
	}
}
