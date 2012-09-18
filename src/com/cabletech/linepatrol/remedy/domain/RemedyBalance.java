package com.cabletech.linepatrol.remedy.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class RemedyBalance extends BaseDomainObject {

    private String id;

    private String remedyId;

    private String creator;

    private Float totalFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemedyId() {
        return remedyId;
    }

    public String getCreator() {
        return creator;
    }

    public Float getTotalFee() {
        return totalFee;
    }

    public void setRemedyId(String remedyId) {
        this.remedyId = remedyId;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setTotalFee(Float totalFee) {
        this.totalFee = totalFee;
    }

}
