package com.cabletech.linepatrol.remedy.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class RemedyWorkflow extends BaseDomainObject {
    private String id;

    private String remedyId;

    private String prevStepId;

    private String currentStepId;
    
    private String currentStepSeq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrevStepId() {
        return prevStepId;
    }

    public void setPrevStepId(String prevStepId) {
        this.prevStepId = prevStepId;
    }

    public String getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(String currentStepId) {
        this.currentStepId = currentStepId;
    }

    public String getRemedyId() {
        return remedyId;
    }

    public void setRemedyId(String remedyId) {
        this.remedyId = remedyId;
    }

	public String getCurrentStepSeq() {
		return currentStepSeq;
	}

	public void setCurrentStepSeq(String currentStepSeq) {
		this.currentStepSeq = currentStepSeq;
	}
}
