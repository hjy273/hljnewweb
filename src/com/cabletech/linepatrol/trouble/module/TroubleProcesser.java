package com.cabletech.linepatrol.trouble.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleProcesser entity. @author MyEclipse Persistence Tools
 */

public class TroubleProcesser extends BaseDomainObject {

	// Fields

	private String id;
	private String troubleReplyId;//���Ϸ�����ϵͳ���
	private String processManType;//������Ա���� '001��ʾ�����ˣ�002��ʾ���ϲ�����Ա��003��ʾ���½�����Ա';
	private String processManId;//������Ա����
	private String processManTel;//������Ա�绰

	// Constructors

	/** default constructor */
	public TroubleProcesser() {
	}

	/** minimal constructor */
	public TroubleProcesser(String id) {
		this.id = id;
	}

	/** full constructor */
	public TroubleProcesser(String id, String troubleReplyId,
			String processManType, String processManId, String processManTel) {
		this.id = id;
		this.troubleReplyId = troubleReplyId;
		this.processManType = processManType;
		this.processManId = processManId;
		this.processManTel = processManTel;
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

	public String getProcessManType() {
		return this.processManType;
	}

	public void setProcessManType(String processManType) {
		this.processManType = processManType;
	}

	public String getProcessManId() {
		return this.processManId;
	}

	public void setProcessManId(String processManId) {
		this.processManId = processManId;
	}

	public String getProcessManTel() {
		return this.processManTel;
	}

	public void setProcessManTel(String processManTel) {
		this.processManTel = processManTel;
	}

}