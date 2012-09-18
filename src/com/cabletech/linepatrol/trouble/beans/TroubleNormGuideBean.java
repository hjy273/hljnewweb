package com.cabletech.linepatrol.trouble.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

/**
 * LpTroubleNormGuide entity. @author MyEclipse Persistence Tools
 */

public class TroubleNormGuideBean extends BaseCommonBean {

	// Fields

	private String id;
	private String guideType;
	private String guideName;
	private Double interdictionTimesDareValue;//千公里阻断次数挑战值
	private Double interdictionTimesNormValue;//千公里阻断次数基准值
	private Double interdictionTimeNormValue;//千公里阻断历时基准值
	private Double interdictionTimeDareValue;//千公里阻断历时挑战值
	private Double rtrTimeNormValue;//平均抢修历时基准值
	private Double rtrTimeDareValue;//平均抢修历时挑战值
	private Double singleRtrTimeNormValue;//单次抢修历时基准值
	
	
	private int reviseTroubleTimes;//修正后故障次数
	private Double reviseInterdictionTime;//修正后阻断时长
	//add by xueyh 20110425 for 记录修正值，为显示修正值准备
	private Double reviseRtrTimeDareValue;//平均抢修历时挑战值
	private Double reviseInterdictionTimesValueCity;//千公里阻断次数  修正值
	private Double reviseInterdictionTimeValueCity;//千公里阻断历时    修正值
	private Double reviseRtrTimeValueCity;//平均抢修历时                      修正值
	
	private String idCity;
	private String guideTypeCity;
	private String guideNameCity;
	private Double interdictionTimesDareValueCity;//千公里阻断次数挑战值
	private Double interdictionTimesNormValueCity;//千公里阻断次数基准值
	private Double interdictionTimeNormValueCity;//千公里阻断历时基准值
	private Double interdictionTimeDareValueCity;//千公里阻断历时挑战值
	private Double rtrTimeNormValueCity;//平均抢修历时基准值
	private Double rtrTimeDareValueCity;//平均抢修历时挑战值
	private Double singleRtrTimeNormValueCity;//单次抢修历时基准值
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGuideType() {
		return guideType;
	}
	public void setGuideType(String guideType) {
		this.guideType = guideType;
	}
	public String getGuideName() {
		return guideName;
	}
	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	public Double getInterdictionTimesDareValue() {
		return interdictionTimesDareValue;
	}
	public void setInterdictionTimesDareValue(Double interdictionTimesDareValue) {
		this.interdictionTimesDareValue = interdictionTimesDareValue;
	}
	public Double getInterdictionTimesNormValue() {
		return interdictionTimesNormValue;
	}
	public void setInterdictionTimesNormValue(Double interdictionTimesNormValue) {
		this.interdictionTimesNormValue = interdictionTimesNormValue;
	}
	public Double getInterdictionTimeNormValue() {
		return interdictionTimeNormValue;
	}
	public void setInterdictionTimeNormValue(Double interdictionTimeNormValue) {
		this.interdictionTimeNormValue = interdictionTimeNormValue;
	}
	public Double getInterdictionTimeDareValue() {
		return interdictionTimeDareValue;
	}
	public void setInterdictionTimeDareValue(Double interdictionTimeDareValue) {
		this.interdictionTimeDareValue = interdictionTimeDareValue;
	}
	public Double getRtrTimeNormValue() {
		return rtrTimeNormValue;
	}
	public void setRtrTimeNormValue(Double rtrTimeNormValue) {
		this.rtrTimeNormValue = rtrTimeNormValue;
	}
	public Double getRtrTimeDareValue() {
		return rtrTimeDareValue;
	}
	public void setRtrTimeDareValue(Double rtrTimeDareValue) {
		this.rtrTimeDareValue = rtrTimeDareValue;
	}
	public Double getSingleRtrTimeNormValue() {
		return singleRtrTimeNormValue;
	}
	public int getReviseTroubleTimes() {
		return reviseTroubleTimes;
	}
	public void setReviseTroubleTimes(int reviseTroubleTimes) {
		this.reviseTroubleTimes = reviseTroubleTimes;
	}
	public Double getReviseInterdictionTime() {
		return reviseInterdictionTime;
	}
	public void setReviseInterdictionTime(Double reviseInterdictionTime) {
		this.reviseInterdictionTime = reviseInterdictionTime;
	}
	public void setSingleRtrTimeNormValue(Double singleRtrTimeNormValue) {
		this.singleRtrTimeNormValue = singleRtrTimeNormValue;
	}
	public String getGuideTypeCity() {
		return guideTypeCity;
	}
	public void setGuideTypeCity(String guideTypeCity) {
		this.guideTypeCity = guideTypeCity;
	}
	public String getGuideNameCity() {
		return guideNameCity;
	}
	public void setGuideNameCity(String guideNameCity) {
		this.guideNameCity = guideNameCity;
	}
	public Double getInterdictionTimesDareValueCity() {
		return interdictionTimesDareValueCity;
	}
	public void setInterdictionTimesDareValueCity(
			Double interdictionTimesDareValueCity) {
		this.interdictionTimesDareValueCity = interdictionTimesDareValueCity;
	}
	public Double getInterdictionTimesNormValueCity() {
		return interdictionTimesNormValueCity;
	}
	public void setInterdictionTimesNormValueCity(
			Double interdictionTimesNormValueCity) {
		this.interdictionTimesNormValueCity = interdictionTimesNormValueCity;
	}
	public Double getInterdictionTimeNormValueCity() {
		return interdictionTimeNormValueCity;
	}
	public void setInterdictionTimeNormValueCity(
			Double interdictionTimeNormValueCity) {
		this.interdictionTimeNormValueCity = interdictionTimeNormValueCity;
	}
	public Double getInterdictionTimeDareValueCity() {
		return interdictionTimeDareValueCity;
	}
	public void setInterdictionTimeDareValueCity(
			Double interdictionTimeDareValueCity) {
		this.interdictionTimeDareValueCity = interdictionTimeDareValueCity;
	}
	public Double getRtrTimeNormValueCity() {
		return rtrTimeNormValueCity;
	}
	public void setRtrTimeNormValueCity(Double rtrTimeNormValueCity) {
		this.rtrTimeNormValueCity = rtrTimeNormValueCity;
	}
	public Double getRtrTimeDareValueCity() {
		return rtrTimeDareValueCity;
	}
	public void setRtrTimeDareValueCity(Double rtrTimeDareValueCity) {
		this.rtrTimeDareValueCity = rtrTimeDareValueCity;
	}
	public Double getSingleRtrTimeNormValueCity() {
		return singleRtrTimeNormValueCity;
	}
	public void setSingleRtrTimeNormValueCity(Double singleRtrTimeNormValueCity) {
		this.singleRtrTimeNormValueCity = singleRtrTimeNormValueCity;
	}
	public String getIdCity() {
		return idCity;
	}
	public void setIdCity(String idCity) {
		this.idCity = idCity;
	}
	public void setReviseRtrTimeDareValue(Double reviseRtrTimeDareValue) {
		this.reviseRtrTimeDareValue = reviseRtrTimeDareValue;
	}
	public Double getReviseRtrTimeDareValue() {
		return reviseRtrTimeDareValue;
	}
	public void setReviseInterdictionTimesValueCity(
			Double reviseInterdictionTimesValueCity) {
		this.reviseInterdictionTimesValueCity = reviseInterdictionTimesValueCity;
	}
	public Double getReviseInterdictionTimesValueCity() {
		return reviseInterdictionTimesValueCity;
	}
	public void setReviseInterdictionTimeValueCity(
			Double reviseInterdictionTimeValueCity) {
		this.reviseInterdictionTimeValueCity = reviseInterdictionTimeValueCity;
	}
	public Double getReviseInterdictionTimeValueCity() {
		return reviseInterdictionTimeValueCity;
	}
	public void setReviseRtrTimeValueCity(Double reviseRtrTimeValueCity) {
		this.reviseRtrTimeValueCity = reviseRtrTimeValueCity;
	}
	public Double getReviseRtrTimeValueCity() {
		return reviseRtrTimeValueCity;
	}
	
	

}   