package com.cabletech.linepatrol.trouble.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleAccidents entity. @author MyEclipse Persistence Tools
 */

public class TroubleAccidents extends BaseDomainObject {

	// Fields

	private String id;
	private String troubleReplyId;//π ’œ∑¥¿°µ•œµÕ≥±‡∫≈
	private String accidentId;//π ’œ∂‘”¶“˛ªº±‡∫≈

	// Constructors

	/** default constructor */
	public TroubleAccidents() {
	}

	/** minimal constructor */
	public TroubleAccidents(String id) {
		this.id = id;
	}

	/** full constructor */
	public TroubleAccidents(String id, String troubleReplyId,
			String accidentId) {
		this.id = id;
		this.troubleReplyId = troubleReplyId;
		this.accidentId = accidentId;
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

	public String getAccidentId() {
		return this.accidentId;
	}

	public void setAccidentId(String accidentId) {
		this.accidentId = accidentId;
	}

}