package com.cabletech.linepatrol.maintenance.module;


import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
  ��ƻ�����м̶�
 */

public class TestYearPlanTrunk extends BaseDomainObject {
	private String id;
	private String yearTaskId;//��ƻ�����id
	private String trunkid;//�м̶�id
	
	
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