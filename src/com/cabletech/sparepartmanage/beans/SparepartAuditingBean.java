package com.cabletech.sparepartmanage.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

/**
 * SparepartAuditingBean ±∏º˛…Í«Î…Û∫ÀµƒFormBean
 */
public class SparepartAuditingBean extends BaseBean {
    private String tid;

    private String applyId;

    private String auditingResult;

    private String auditingOpnion;

    private Date auditingDate;

    private String auditingPerson;

    private String auditingRemark;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Date getAuditingDate() {
        return auditingDate;
    }

    public void setAuditingDate(Date auditingDate) {
        this.auditingDate = auditingDate;
    }

    public String getAuditingOpnion() {
        return auditingOpnion;
    }

    public void setAuditingOpnion(String auditingOpnion) {
        this.auditingOpnion = auditingOpnion;
    }

    public String getAuditingPerson() {
        return auditingPerson;
    }

    public void setAuditingPerson(String auditingPerson) {
        this.auditingPerson = auditingPerson;
    }

    public String getAuditingRemark() {
        return auditingRemark;
    }

    public void setAuditingRemark(String auditingRemark) {
        this.auditingRemark = auditingRemark;
    }

    public String getAuditingResult() {
        return auditingResult;
    }

    public void setAuditingResult(String auditingResult) {
        this.auditingResult = auditingResult;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

}
