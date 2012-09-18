package com.cabletech.sendtask.beans;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.uploadfile.formbean.*;

public class SendTaskBean extends BaseBean{
    private String id = "";
    private String senduserid = "";
    private String sendusername = "";
    private String senddeptid = "";
    private String senddeptname = "";
    private String processterm = "";
    private String sendtopic = "";
    private String sendtext = "";
    private String sendacce = "";
    private String acceptdeptid = "";
    private String acceptdeptname = "";
    private String acceptuserid = "";
    private String acceptusername = "";
    private String workstate = "";
    private String endorseid = "";
    private String endorseusername = "";
    private String sendtime = "";
    private String sendtype = "";
    private String regionid = "";
    private String phone = "";

    ////////
    private String time = "";
    private String deptid = "";
    private String deptname = "";
    private String userid = "";
    private String username = "";
    private String result = "";
    private String remark = "";
    private String acce = "";
    private String sendtaskid = "";
    private String replyid = "";
    ////////
    private String replytime = "";
    private String replyuserid = "";
    private String replyusername = "";
    private String replyacce = "";
    private String replyresult = "";
    private String replyremark = "";
    private String validatetime = "";
    private String validateuserid = "";
    private String validateusername = "";
    private String validateresult = "";
    private String validateacce = "";
    private String validateremark = "";
    //////
    private String usernameacce = "";
    private String begintime = "";
    private String endtime = "";
    // add by 2008-12-01 guixy
    private String serialnumber = "";
    private String subtaskid = "";
    
    

    /////////
    private String flag = "";



    public SendTaskBean(){    	
    }


    public String getSubtaskid() {
		return subtaskid;
	}


	public void setSubtaskid(String subtaskid) {
		this.subtaskid = subtaskid;
	}


	public void setId( String id ){
        this.id = id;
    }


    public void setSenduserid( String senduserid ){
        this.senduserid = senduserid;
    }


    public void setSendusername( String sendusername ){
        this.sendusername = sendusername;
    }


    public void setSenddeptid( String senddeptid ){
        this.senddeptid = senddeptid;
    }


    public void setSenddeptname( String senddeptname ){
        this.senddeptname = senddeptname;
    }


    public void setSendtype( String sendtype ){
        this.sendtype = sendtype;
    }


    public void setProcessterm( String processterm ){
        this.processterm = processterm;
    }


    public void setSendtopic( String sendtopic ){
        this.sendtopic = sendtopic;
    }


    public void setSendtext( String sendtext ){
        this.sendtext = sendtext;
    }


    public void setSendacce( String sendacce ){
        this.sendacce = sendacce;
    }


    public void setAcceptdeptid( String acceptdeptid ){
        this.acceptdeptid = acceptdeptid;
    }


    public void setAcceptdeptname( String acceptdeptname ){
        this.acceptdeptname = acceptdeptname;
    }


    public void setAcceptuserid( String acceptuserid ){
        this.acceptuserid = acceptuserid;
    }


    public void setAcceptusername( String acceptusername ){
        this.acceptusername = acceptusername;
    }


    public void setWorkstate( String workstate ){
        this.workstate = workstate;
    }


    public void setEndorseid( String endorseid ){
        this.endorseid = endorseid;
    }


    public void setReplyid( String replyid ){
        this.replyid = replyid;
    }


    public void setSendtime( String sendtime ){
        this.sendtime = sendtime;
    }


    public void setTime( String time ){
        this.time = time;
    }


    public void setDeptid( String deptid ){
        this.deptid = deptid;
    }


    public void setDeptname( String deptname ){
        this.deptname = deptname;
    }


    public void setUserid( String userid ){
        this.userid = userid;
    }


    public void setUsername( String username ){
        this.username = username;
    }


    public void setResult( String result ){
        this.result = result;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setAcce( String acce ){
        this.acce = acce;
    }


    public void setSendtaskid( String sendtaskid ){
        this.sendtaskid = sendtaskid;
    }


    public void setReplytime( String replytime ){
        this.replytime = replytime;
    }


    public void setReplyuserid( String replyuserid ){
        this.replyuserid = replyuserid;
    }


    public void setReplyusername( String replyusername ){
        this.replyusername = replyusername;
    }


    public void setReplyacce( String replyacce ){
        this.replyacce = replyacce;
    }


    public void setReplyresult( String replyresult ){
        this.replyresult = replyresult;
    }


    public void setReplyremark( String replyremark ){
        this.replyremark = replyremark;
    }


    public void setValidatetime( String validatetime ){
        this.validatetime = validatetime;
    }


    public void setValidateuserid( String validateuserid ){
        this.validateuserid = validateuserid;
    }


    public void setValidateusername( String validateusername ){
        this.validateusername = validateusername;
    }


    public void setValidateresult( String validateresult ){
        this.validateresult = validateresult;
    }


    public void setValidateacce( String validateacce ){
        this.validateacce = validateacce;
    }


    public void setValidateremark( String validateremark ){
        this.validateremark = validateremark;
    }


    public void setFlag( String flag ){
        this.flag = flag;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setPhone( String phone ){
        this.phone = phone;
    }


    public void setUsernameacce( String usernameacce ){
        this.usernameacce = usernameacce;
    }


    public void setBegintime( String begintime ){
        this.begintime = begintime;
    }


    public void setEndtime( String endtime ){
        this.endtime = endtime;
    }


    public String getId(){
        return id;
    }


    public String getSenduserid(){
        return senduserid;
    }


    public String getSendusername(){
        return sendusername;
    }


    public String getSenddeptid(){
        return senddeptid;
    }


    public String getSenddeptname(){
        return senddeptname;
    }


    public String getSendtype(){
        return sendtype;
    }


    public String getProcessterm(){
        return processterm;
    }


    public String getSendtopic(){
        return sendtopic;
    }


    public String getSendtext(){
        return sendtext;
    }


    public String getSendacce(){
        return sendacce;
    }


    public String getAcceptdeptid(){
        return acceptdeptid;
    }


    public String getAcceptdeptname(){
        return acceptdeptname;
    }


    public String getAcceptuserid(){
        return acceptuserid;
    }


    public String getAcceptusername(){
        return acceptusername;
    }


    public String getWorkstate(){
        return workstate;
    }


    public String getEndorseid(){
        return endorseid;
    }


    public String getReplyid(){
        return replyid;
    }


    public String getSendtime(){
        return sendtime;
    }


    public String getTime(){
        return time;
    }


    public String getDeptid(){
        return deptid;
    }


    public String getDeptname(){
        return deptname;
    }


    public String getUserid(){
        return userid;
    }


    public String getUsername(){
        return username;
    }


    public String getResult(){
        return result;
    }


    public String getRemark(){
        return remark;
    }


    public String getAcce(){
        return acce;
    }


    public String getSendtaskid(){
        return sendtaskid;
    }


    public String getReplytime(){
        return replytime;
    }


    public String getReplyuserid(){
        return replyuserid;
    }


    public String getReplyusername(){
        return replyusername;
    }


    public String getReplyacce(){
        return replyacce;
    }


    public String getReplyresult(){
        return replyresult;
    }


    public String getReplyremark(){
        return replyremark;
    }


    public String getValidatetime(){
        return validatetime;
    }


    public String getValidateuserid(){
        return validateuserid;
    }


    public String getValidateusername(){
        return validateusername;
    }


    public String getValidateresult(){
        return validateresult;
    }


    public String getValidateacce(){
        return validateacce;
    }


    public String getValidateremark(){
        return validateremark;
    }


    public String getFlag(){
        return flag;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getPhone(){
        return phone;
    }


    public String getUsernameacce(){
        return usernameacce;
    }


    public String getBegintime(){
        return begintime;
    }


    public String getEndtime(){
        return endtime;
    }


	public String getSerialnumber() {
		return serialnumber;
	}


	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}


	public String getEndorseusername() {
		return endorseusername;
	}


	public void setEndorseusername(String endorseusername) {
		this.endorseusername = endorseusername;
	}
}
