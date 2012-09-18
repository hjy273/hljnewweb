package com.cabletech.planinfo.domainobjects;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cabletech.commons.services.DBService;

public class ExecuteLog {
	/**复制计划*/
	public static final String TYPE_COPY="0";
	/**批量制定计划*/
	public static final String TYPE_BATCH="1";
	private String id;
	private String userId = "";
	private String planBeginDate;

	private String endDate;
	private String type;
	private String result = "";
	private String exception = "";
	public ExecuteLog(){
		DBService dbs = new DBService();
		this.id = dbs.getSeq("EXECUTELOG", 10);
	}
	public ExecuteLog(String userId,String type,String result){
		DBService dbs = new DBService();
		this.id = dbs.getSeq("EXECUTELOG", 10);
		this.userId = userId;
		this.type =type;
		this.result = result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	public String getPlanBeginDate() {
		return planBeginDate;
	}
	public void setPlanBeginDate(String planBeginDate) {
		this.planBeginDate = planBeginDate;
	}
}
