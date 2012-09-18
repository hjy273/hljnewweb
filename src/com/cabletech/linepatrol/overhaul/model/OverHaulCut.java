package com.cabletech.linepatrol.overhaul.model;

public class OverHaulCut {
	private String id;
	private String cutId;
	private String cutName;
	private String cutRefFee;
	private String cutFee;
	private OverHaulApply overHaulApply;
	
	public OverHaulCut(){
		
	}
	public OverHaulCut(String cutId, String cutFee, String cutRefFee){
		this.cutId = cutId;
		this.cutFee = cutFee;
		this.cutRefFee = cutRefFee;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCutId() {
		return cutId;
	}
	public void setCutId(String cutId) {
		this.cutId = cutId;
	}
	public String getCutName() {
		return cutName;
	}
	public void setCutName(String cutName) {
		this.cutName = cutName;
	}
	public String getCutRefFee() {
		return cutRefFee;
	}
	public void setCutRefFee(String cutRefFee) {
		this.cutRefFee = cutRefFee;
	}
	public String getCutFee() {
		return cutFee;
	}
	public void setCutFee(String cutFee) {
		this.cutFee = cutFee;
	}
	public OverHaulApply getOverHaulApply() {
		return overHaulApply;
	}
	public void setOverHaulApply(OverHaulApply overHaulApply) {
		this.overHaulApply = overHaulApply;
	}
	
}
