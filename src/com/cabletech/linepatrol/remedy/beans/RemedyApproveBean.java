package com.cabletech.linepatrol.remedy.beans;

import com.cabletech.commons.base.BaseBean;

public class RemedyApproveBean extends BaseBean {
    private String state;

    private String approveMan;

    private String applyId;

    private String approveDate;

    private String id;

    private String remark;
    
    private String nextProcessMan;

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

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
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

}
