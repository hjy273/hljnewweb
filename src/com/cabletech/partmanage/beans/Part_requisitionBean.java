/**
 * ���ߣ�Ԭ�ؾ�
 * ���ڣ�2006��3��4��
 * ˵��������������form bean
 * */
package com.cabletech.partmanage.beans;

import org.apache.struts.upload.*;

public class Part_requisitionBean extends Part_baseInfoBean{

    private String reid = "";
    private String contractorid = "";
    private String userid = "";
    private String time = "";
    private String reason = "";
    private String remark = "";
    private String id = "";
    private String renumber = "";
    private String type = "";
    private String regionid = "";

    private String audituserid = ""; //�� ����
    private String auditusername="";
    private String audittime = "";
    private String auditresult = "";
    private String auditremark = "";
    private String deptid = "";
    private String deptname = "";
    private String targetman = "";
    private String targetmanname = "";
    private String audnumber="";

    //Ϊ��ʾ���뵥���
    private String username = ""; //�û�����
    private String contractorname = ""; //��ά��λ����
    private String begintime = ""; //��ʼʱ��
    private String endtime = ""; //����ʱ��
    //Ϊ�²���������
    private String stockid = ""; //��ⵥid
    private String stockuserid = ""; //�����id
    private String stocktime = ""; //���ʱ��
    private String stocknumber = ""; //�������
    private String stockaddress = "";
    private String keeperid = "";

    //Ϊ���ɲ������
    private String oldid = "";
    private String olduserid = "";
    private String oldtime = "";
    private String oldreason = "";
    private String oldremark = "";
    private String oldnumber = "";
    //Ϊ�������
    private String useid = "";
    private String useuserid = "";
    private String usetime = "";
    private String usereason = "";
    private String useremark = "";
    private String usenewnumber = "";
    private String useoldnumber = "";
    //Ϊ������
    private String newesse = ""; //��Ʒʵ������
    private String newshould = ""; //��ƷӦ������,�Ժ�������
    //Ϊ�ϴ��ļ����
    private FormFile file = null;
    private String filename = "";
    private String filesize = "";
    
    //ʹ��ͳ��
    private String totaltype;
    private String level;
    private String subline;
    private String sublineId;
    private String linechangename;
    private String cutchangename;

    public String getCutchangename() {
		return cutchangename;
	}


	public void setCutchangename(String cutchangename) {
		this.cutchangename = cutchangename;
	}


	public String getSubline() {
		return subline;
	}


	public void setSubline(String subline) {
		this.subline = subline;
	}


	public String getSublineId() {
		return sublineId;
	}


	public void setSublineId(String sublineId) {
		this.sublineId = sublineId;
	}


	public String getTotaltype() {
		return totaltype;
	}


	public void setTotaltype(String totaltype) {
		this.totaltype = totaltype;
	}


	public String getLevel() {
		return level;
	}


	public void setLevel(String level) {
		this.level = level;
	}


	public String getLinechangename() {
		return linechangename;
	}


	public void setLinechangename(String linechangename) {
		this.linechangename = linechangename;
	}


	public FormFile getFile(){
        return this.file;
    }


    public void setFile( FormFile file ){
        this.file = file;
    }


    public String getFilename(){
        return this.filename;
    }


    public void setFilename( String filename ){
        this.filename = filename;
    }


    public String getFilesize(){
        return this.filesize;
    }


    public void setFilesize( String filesize ){
        this.filesize = filesize;
    }


    public String getStocknumber(){
        return this.stocknumber;
    }


    public void setStocknumber( String stocknumber ){
        this.stocknumber = stocknumber;
    }


    public String getNewshould(){
        return this.newshould;
    }


    public void setNewshould( String newshould ){
        this.newshould = newshould;
    }


    public String getNewesse(){
        return this.newesse;
    }


    public void setNewesse( String newesse ){
        this.newesse = newesse;
    }


    public String getUseoldnumber(){
        return this.useoldnumber;
    }


    public void setUseoldnumber( String useoldnumber ){
        this.useoldnumber = useoldnumber;
    }


    public String getUsenewnumber(){
        return this.usenewnumber;
    }


    public void setUsenewnumber( String usenewnumber ){
        this.usenewnumber = usenewnumber;
    }


    public String getUseremark(){
        return this.useremark;
    }


    public void setUseremark( String useremark ){
        this.useremark = useremark;
    }


    public String getUsereason(){
        return this.usereason;
    }


    public void setUsereason( String usereason ){
        this.usereason = usereason;
    }


    public String getUsetime(){
        return this.usetime;
    }


    public void setUsetime( String usetime ){
        this.usetime = usetime;
    }


    public String getUseuserid(){
        return this.useuserid;
    }


    public void setUseuserid( String useuserid ){
        this.useuserid = useuserid;
    }


    public String getUseid(){
        return this.useid;
    }


    public void setUseid( String useid ){
        this.useid = useid;
    }


    public String getOldnumber(){
        return this.oldnumber;
    }


    public void setOldnumber( String oldnumber ){
        this.oldnumber = oldnumber;
    }


    public String getOldremark(){
        return this.oldremark;
    }


    public void setOldremark( String oldremark ){
        this.oldremark = oldremark;
    }


    public String getOldreason(){
        return this.oldreason;
    }


    public void setOldreason( String oldreason ){
        this.oldreason = oldreason;
    }


    public String getOldtime(){
        return this.oldtime;
    }


    public void setOldtime( String oldtime ){
        this.oldtime = oldtime;
    }


    public String getOlduserid(){
        return this.olduserid;
    }


    public void setOlduserid( String olduserid ){
        this.olduserid = olduserid;
    }


    public String getOldid(){
        return this.oldid;
    }


    public void setOldid( String oldid ){
        this.oldid = oldid;
    }


    public String getStocktime(){
        return this.stocktime;
    }


    public void setStocktime( String stocktime ){
        this.stocktime = stocktime;
    }


    public String getStockuserid(){
        return this.stockuserid;
    }


    public void setStockuserid( String stockuserid ){
        this.stockuserid = stockuserid;
    }


    public String getStockid(){
        return this.stockid;
    }


    public void setStockid( String stockid ){
        this.stockid = stockid;
    }


    public String getDeptname(){
        return this.deptname;
    }


    public void setDeptname( String deptname ){
        this.deptname = deptname;
    }


    public String getDeptid(){
        return this.deptid;
    }


    public String getType(){
        return this.type;
    }


    public void setType( String type ){
        this.type = type;
    }


    public void setDeptid( String deptid ){
        this.deptid = deptid;
    }


    public String getEndtime(){
        return this.endtime;
    }


    public void setEndtime( String endtime ){
        this.endtime = endtime;
    }


    public String getBegintime(){
        return this.begintime;
    }


    public void setBegintime( String begintime ){
        this.begintime = begintime;
    }


    public String getAuditremark(){
        return this.auditremark;
    }


    public void setAuditremark( String auditremark ){
        this.auditremark = auditremark;
    }


    public String getAuditresult(){
        return this.auditresult;
    }


    public void setAuditresult( String auditresult ){
        this.auditresult = auditresult;
    }


    public String getAudittime(){
        return this.audittime;
    }


    public void setAudittime( String audittime ){
        this.audittime = audittime;
    }


    public String getAudituserid(){
        return this.audituserid;
    }


    public void setAudituserid( String audituserid ){
        this.audituserid = audituserid;
    }


    public String getUsername(){
        return this.username;
    }


    public void setUsername( String username ){
        this.username = username;
    }


    public String getContractorname(){
        return this.contractorname;
    }


    public void setContractorname( String contractorname ){
        this.contractorname = contractorname;
    }


    public String getReid(){
        return this.reid;
    }


    public void setReid( String reid ){
        this.reid = reid;
    }


    public String getContractorId(){
        return this.contractorid;
    }

    public String getcontractorid(){
        return this.contractorid;
    }

    public void setContractorid( String contractorid ){
        this.contractorid = contractorid;
    }


    public String getUserid(){
        return this.userid;
    }


    public void setUserid( String userid ){
        this.userid = userid;
    }


    public String getTime(){
        return this.time;
    }


    public void setTime( String time ){
        this.time = time;
    }


    public String getReason(){
        return this.reason;
    }


    public void setReason( String reason ){
        this.reason = reason;
    }


    public String getRemark(){
        return this.remark;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public String getId(){
        return this.id;
    }


    public void setId( String id ){
        this.id = id;
    }


    public String getRenumber(){
        return this.renumber;
    }


    public String getStockaddress(){
        return stockaddress;
    }


    public String getKeeperid(){
        return keeperid;
    }


    public String getTargetman(){
        return targetman;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getAuditusername(){
        return auditusername;
    }


    public String getAudnumber(){
        return audnumber;
    }


    public void setRenumber( String renumber ){
        this.renumber = renumber;
    }


    public void setStockaddress( String stockaddress ){
        this.stockaddress = stockaddress;
    }


    public void setKeeperid( String keeperid ){
        this.keeperid = keeperid;
    }


    public void setTargetman( String targetman ){
        this.targetman = targetman;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setAuditusername( String auditusername ){
        this.auditusername = auditusername;
    }


    public void setAudnumber( String audnumber ){
        this.audnumber = audnumber;
    }


    public Part_requisitionBean(){
    }


	public String getTargetmanname() {
		return targetmanname;
	}


	public void setTargetmanname(String targetmanname) {
		this.targetmanname = targetmanname;
	}

}
