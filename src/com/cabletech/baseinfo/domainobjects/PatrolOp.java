package com.cabletech.baseinfo.domainobjects;

import com.cabletech.commons.base.*;

public class PatrolOp extends BaseDomainObject{

    private String operationcode;
    private String operationdes;
    private String regionid;
    private String remark;
    private String emergencylevel;
    private Integer noticecycle;
    private Integer handlecycle;
    private String optype;
    private String subcode;

    public PatrolOp(){
    }


    public void setOperationcode( String operationcode ){
        this.operationcode = operationcode;
    }


    public void setOperationdes( String operationdes ){
        this.operationdes = operationdes;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setRemark( String remark ){
        this.remark = remark;
    }


    public void setEmergencylevel( String emergencylevel ){
        this.emergencylevel = emergencylevel;
    }


    public void setOptype( String optype ){
        this.optype = optype;
    }


    public void setSubcode( String subcode ){
        this.subcode = subcode;
    }


    public void setNoticecycle( Integer noticecycle ){
        this.noticecycle = noticecycle;
    }


    public void setHandlecycle( Integer handlecycle ){
        this.handlecycle = handlecycle;
    }


    public String getOperationcode(){
        return operationcode;
    }


    public String getOperationdes(){
        return operationdes;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getRemark(){
        return remark;
    }


    public String getEmergencylevel(){
        return emergencylevel;
    }


    public String getOptype(){
        return optype;
    }


    public String getSubcode(){
        return subcode;
    }


    public Integer getNoticecycle(){
        return noticecycle;
    }


    public Integer getHandlecycle(){
        return handlecycle;
    }
}
