package com.cabletech.linepatrol.specialplan.beans;

public class TaskRoute implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 433567571769283879L;
	private String id;
	private String sublineName;
	private String sublineId;
	public TaskRoute(){
	}
	public TaskRoute(String sublineId,String sublineName){
		this.sublineId = sublineId;
		this.sublineName = sublineName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSublineName() {
		return sublineName;
	}
	public void setSublineName(String sublineName) {
		this.sublineName = sublineName;
	}
	public String getSublineId() {
		return sublineId;
	}
	public void setSublineId(String sublineId) {
		this.sublineId = sublineId;
	}
	
}
