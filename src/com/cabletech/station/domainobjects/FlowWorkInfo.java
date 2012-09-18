package com.cabletech.station.domainobjects;

// default package

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * FlowWorkInfo generated by MyEclipse Persistence Tools
 */

public class FlowWorkInfo extends BaseDomainObject {

    // Fields

    private String tid;

    private String objectId;

    private String validateUserid;

    private String validateResult;

    private Date validateTime;

    private String validateRemark;

    private String sonmenuId;

    // Constructors

    /** default constructor */
    public FlowWorkInfo() {
    }

    /** minimal constructor */
    public FlowWorkInfo(String tid) {
        this.tid = tid;
    }

    /** full constructor */
    public FlowWorkInfo(String tid, String objectId, String validateUserid, String validateResult,
            Date validateTime, String validateRemark, String sonmenuId) {
        this.tid = tid;
        this.objectId = objectId;
        this.validateUserid = validateUserid;
        this.validateResult = validateResult;
        this.validateTime = validateTime;
        this.validateRemark = validateRemark;
        this.sonmenuId = sonmenuId;
    }

    // Property accessors

    public String getTid() {
        return this.tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getValidateUserid() {
        return this.validateUserid;
    }

    public void setValidateUserid(String validateUserid) {
        this.validateUserid = validateUserid;
    }

    public String getValidateResult() {
        return this.validateResult;
    }

    public void setValidateResult(String validateResult) {
        this.validateResult = validateResult;
    }

    public Date getValidateTime() {
        return this.validateTime;
    }

    public void setValidateTime(Date validateTime) {
        this.validateTime = validateTime;
    }

    public String getValidateRemark() {
        return this.validateRemark;
    }

    public void setValidateRemark(String validateRemark) {
        this.validateRemark = validateRemark;
    }

    public String getSonmenuId() {
        return this.sonmenuId;
    }

    public void setSonmenuId(String sonmenuId) {
        this.sonmenuId = sonmenuId;
    }

}