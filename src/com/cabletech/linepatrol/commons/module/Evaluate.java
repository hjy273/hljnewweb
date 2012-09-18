package com.cabletech.linepatrol.commons.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class Evaluate extends BaseDomainObject{
	private String id;
	private String entityId;
	private String entityType;
	private String entityScore;
	private String entityRemark;
	private String evaluator;
	private Date evaluaterDate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntityScore() {
		return entityScore;
	}
	public void setEntityScore(String entityScore) {
		this.entityScore = entityScore;
	}
	public String getEntityRemark() {
		return entityRemark;
	}
	public void setEntityRemark(String entityRemark) {
		this.entityRemark = entityRemark;
	}
	public String getEvaluator() {
		return evaluator;
	}
	public void setEvaluator(String evaluator) {
		this.evaluator = evaluator;
	}
	public Date getEvaluaterDate() {
		return evaluaterDate;
	}
	public void setEvaluaterDate(Date evaluaterDate) {
		this.evaluaterDate = evaluaterDate;
	}
	
}
