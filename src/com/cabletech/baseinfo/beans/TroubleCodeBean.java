package com.cabletech.baseinfo.beans;

import com.cabletech.commons.base.*;


public class TroubleCodeBean extends BaseBean{
    private String troublecode;
    private String troubletype;
    private String troublename;
    private String emergencylevel;//¼¶±ð
    private String mustHandle;
    private Integer noticeCycle;
    private Integer handleCycle;
    private String remark;
    private String id = "";
    private String code = "";
    private String regionid = "";
    private String typename = "";
    private String setnumber = "";
    private String simnumber = "";

    public String getEmergencylevel() {
        return emergencylevel;
    }
    public void setEmergencylevel(String emergencylevel) {
        this.emergencylevel = emergencylevel;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getTroublecode() {
        return troublecode;
    }
    public void setTroublecode(String troublecode) {
        this.troublecode = troublecode;
    }
    public String getTroublename() {
        return troublename;
    }
    public void setTroublename(String troublename) {
        this.troublename = troublename;
    }
    public String getTroubletype() {
        return troubletype;
    }
    public void setMustHandle(String mustHandle){
        this.mustHandle=mustHandle;
    }
    public String getMustHandle(){
        return mustHandle;
    }
    public void setNoticeCycle(Integer noticeCycle){
        this.noticeCycle=noticeCycle;
    }
    public Integer getNoticeCycle(){
        return noticeCycle;
    }
    public void setHandleCycle(Integer handleCycle){
        this.handleCycle=handleCycle;
    }
    public Integer getHandleCycle(){
        return handleCycle;
    }


    public String getId(){
        return id;
    }


    public String getCode(){
        return code;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getTypename(){
        return typename;
    }


    public String getSetnumber(){
        return setnumber;
    }


    public String getSimnumber(){
        return simnumber;
    }


    public void setTroubletype(String troubletype) {
        this.troubletype = troubletype;
    }


    public void setId( String id ){
        this.id = id;
    }


    public void setCode( String code ){
        this.code = code;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setTypename( String typename ){
        this.typename = typename;
    }


    public void setSetnumber( String setnumber ){
        this.setnumber = setnumber;
    }


    public void setSimnumber( String simnumber ){
        this.simnumber = simnumber;
    }

}
