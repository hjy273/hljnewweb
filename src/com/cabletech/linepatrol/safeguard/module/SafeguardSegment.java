package com.cabletech.linepatrol.safeguard.module;

import com.cabletech.commons.base.BaseDomainObject;

public class SafeguardSegment extends BaseDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8563805897536326758L;

	private String id;
	private String segmentId;//ÖÐ¼Ì¶ÎId
	private String planId;//·½°¸Id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
}
