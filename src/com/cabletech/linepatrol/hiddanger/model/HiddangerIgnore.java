package com.cabletech.linepatrol.hiddanger.model;

import java.util.Date;

public class HiddangerIgnore {
	private String id;
	private String hiddangerId;
	private String name;
	private String ignoreInfomation;
	private String ignoreManId;
	private Date ignoreWritingTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHiddangerId() {
		return hiddangerId;
	}
	public void setHiddangerId(String hiddangerId) {
		this.hiddangerId = hiddangerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIgnoreInfomation() {
		return ignoreInfomation;
	}
	public void setIgnoreInfomation(String ignoreInfomation) {
		this.ignoreInfomation = ignoreInfomation;
	}
	public String getIgnoreManId() {
		return ignoreManId;
	}
	public void setIgnoreManId(String ignoreManId) {
		this.ignoreManId = ignoreManId;
	}
	public Date getIgnoreWritingTime() {
		return ignoreWritingTime;
	}
	public void setIgnoreWritingTime(Date ignoreWritingTime) {
		this.ignoreWritingTime = ignoreWritingTime;
	}
}
