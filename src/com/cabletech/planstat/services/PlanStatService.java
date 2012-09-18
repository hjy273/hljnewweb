package com.cabletech.planstat.services;

import com.cabletech.commons.base.BaseService;
import com.cabletech.planstat.domainobjects.SublineRequest;
import com.cabletech.planstat.domainobjects.SublineResponse;

public class PlanStatService extends BaseService{
	SublineRealTimeBO sRBO = null;
    public PlanStatService(){
    	sRBO = new SublineRealTimeBO();
    }
    public void addRealTimeSublineRequest(SublineRequest data) throws Exception{
    	sRBO.addRealTimeSublineRequest(data);
    }
    
    public void addRealTimeSublineResponse(SublineResponse data) throws Exception{
    	sRBO.addRealTimeSublineResponse(data);
    }
}
