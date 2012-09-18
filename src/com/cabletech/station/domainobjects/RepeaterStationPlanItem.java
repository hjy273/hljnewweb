package com.cabletech.station.domainobjects;

import com.cabletech.commons.base.BaseDomainObject;

// default package

/**
 * RepeaterStationPlanItem generated by MyEclipse Persistence Tools
 */

public class RepeaterStationPlanItem extends BaseDomainObject {

    // Fields

    private String tid;

    private String planId;

    private String repeaterStationId;

    // Constructors

    /** default constructor */
    public RepeaterStationPlanItem() {
    }

    /** minimal constructor */
    public RepeaterStationPlanItem(String tid) {
        this.tid = tid;
    }

    /** full constructor */
    public RepeaterStationPlanItem(String tid, String planId, String repeaterStationId) {
        this.tid = tid;
        this.planId = planId;
        this.repeaterStationId = repeaterStationId;
    }

    // Property accessors

    public String getTid() {
        return this.tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPlanId() {
        return this.planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getRepeaterStationId() {
        return this.repeaterStationId;
    }

    public void setRepeaterStationId(String repeaterStationId) {
        this.repeaterStationId = repeaterStationId;
    }

}