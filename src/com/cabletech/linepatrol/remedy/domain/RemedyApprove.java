package com.cabletech.linepatrol.remedy.domain;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class RemedyApprove extends BaseDomainObject {
    private String state;

    private String approveMan;

    private String applyId;

    private Date approveDate;

    private String id;

    private String remark;
    
    private String nextProcessMan;
    
    private String stepId;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApproveMan() {
        return approveMan;
    }

    public void setApproveMan(String approveMan) {
        this.approveMan = approveMan;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the nextProcessMan
     */
    public String getNextProcessMan() {
        return nextProcessMan;
    }

    /**
     * @param nextProcessMan the nextProcessMan to set
     */
    public void setNextProcessMan(String nextProcessMan) {
        this.nextProcessMan = nextProcessMan;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }
}
