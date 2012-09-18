package com.cabletech.linepatrol.remedy.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class RemedyWorkflowInfo extends BaseDomainObject {
    private String id;

    private String remedyId;

    private String stepId;

    private String lastProcessMan;

    private String nextProcessMan;

    private String state;

    private String prevState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemedyId() {
        return remedyId;
    }

    public void setRemedyId(String remedyId) {
        this.remedyId = remedyId;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getLastProcessMan() {
        return lastProcessMan;
    }

    public void setLastProcessMan(String lastProcessMan) {
        this.lastProcessMan = lastProcessMan;
    }

    public String getNextProcessMan() {
        return nextProcessMan;
    }

    public void setNextProcessMan(String nextProcessMan) {
        this.nextProcessMan = nextProcessMan;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrevState() {
        return prevState;
    }

    public void setPrevState(String prevState) {
        this.prevState = prevState;
    }
}
