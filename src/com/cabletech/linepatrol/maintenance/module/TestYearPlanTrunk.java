package com.cabletech.linepatrol.maintenance.module;


import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
  年计划变更中继段
 */

public class TestYearPlanTrunk extends BaseDomainObject {
	private String id;
	private String yearTaskId;//年计划任务id
	private String trunkid;//中继段id
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYearTaskId() {
		return yearTaskId;
	}
	public void setYearTaskId(String yearTaskId) {
		this.yearTaskId = yearTaskId;
	}
	public String getTrunkid() {
		return trunkid;
	}
	public void setTrunkid(String trunkid) {
		this.trunkid = trunkid;
	}
	

}