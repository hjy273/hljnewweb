package com.cabletech.linepatrol.trouble.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleProcesser entity. @author MyEclipse Persistence Tools
 */

public class TroubleProcesser extends BaseDomainObject {

	// Fields

	private String id;
	private String troubleReplyId;//故障反馈单系统编号
	private String processManType;//处理人员类型 '001表示负责人，002表示故障测试人员，003表示光缆接续人员';
	private String processManId;//处理人员姓名
	private String processManTel;//处理人员电话

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