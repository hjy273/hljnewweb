package com.cabletech.linepatrol.remedy.domain;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class RemedyCheck extends BaseDomainObject {
    private String id;

    private String applyId;

    private String state;

    private String checkMan;

    private Date checkDate;

    private String remark;

    private String nextProcessMan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCheckMan() {
        return checkMan;
    }

    public void setCheckMan(String checkMan) {
        this.checkMan = checkMan;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNextProcessMan() {
        return nextProcessMan;
    }

    public void setNextProcessMan(String nextProcessMan) {
        this.nextProcessMan = nextProcessMan;
    }
}
