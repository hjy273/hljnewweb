package com.cabletech.linepatrol.trouble.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleCableInfo entity. @author MyEclipse Persistence Tools
 */

public class TroubleCableInfo extends BaseDomainObject {

	// Fields

	private String id;
	private String troubleReplyId;//π ’œ∑¥¿°µ•œµÕ≥±‡∫≈
	private String troubleCableLineId;//π ’œπ‚¿¬±‡∫≈

	// Constructors

	/** default constructor */
	public TroubleCableInfo() {
	}

	/** minimal constructor */
	public TroubleCableInfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public TroubleCableInfo(String id, String troubleReplyId,
			String troubleCableLineId) {
		this.id = id;
		this.troubleReplyId = troubleReplyId;
		this.troubleCableLineId = troubleCableLineId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTroubleReplyId() {
		return this.troubleReplyId;
	}

	public void setTroubleReplyId(String troubleReplyId) {
		this.troubleReplyId = troubleReplyId;
	}

	public String getTroubleCableLineId() {
		return this.troubleCableLineId;
	}

	public void setTroubleCableLineId(String troubleCableLineId) {
		this.troubleCableLineId = troubleCableLineId;
	}

}