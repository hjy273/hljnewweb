package com.cabletech.linepatrol.safeguard.module;

import com.cabletech.commons.base.BaseDomainObject;

public class SafeguardSpplan extends BaseDomainObject {

	private String id;
	private String planId;// ���ϼƻ�Id
	private String spplanId;// ��Ѳ�ƻ�Id
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getSpplanId() {
		return spplanId;
	}

	public void setSpplanId(String spplanId) {
		this.spplanId = spplanId;
	}
}
