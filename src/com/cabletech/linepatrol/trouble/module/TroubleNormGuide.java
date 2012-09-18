package com.cabletech.linepatrol.trouble.module;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.linepatrol.trouble.beans.TroubleNormGuideBean;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;

/**
 * LpTroubleNormGuide entity. @author MyEclipse Persistence Tools
 */

public class TroubleNormGuide extends BaseDomainObject {

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
	
	//add by xueyh  20110425 for  修正值
	private Double reviseInterdictionTimesValue;//千公里阻断次数  修正值
	private Double reviseInterdictionTimeValue;//千公里阻断历时    修正值
	private Double reviseRtrTimeValue;//平均抢修历时                                 修正值
	
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
	public void setSingleRtrTimeNormValue(Double singleRtrTimeNormValue) {
		this.singleRtrTimeNormValue = singleRtrTimeNormValue;
	}
	public void setReviseInterdictionTimesValue(
			Double reviseInterdictionTimesValue) {
		this.reviseInterdictionTimesValue = reviseInterdictionTimesValue;
	}
	public Double getReviseInterdictionTimesValue() {
		return reviseInterdictionTimesValue;
	}
	public void setReviseInterdictionTimeValue(
			Double reviseInterdictionTimeValue) {
		this.reviseInterdictionTimeValue = reviseInterdictionTimeValue;
	}
	public Double getReviseInterdictionTimeValue() {
		return reviseInterdictionTimeValue;
	}
	public void setReviseRtrTimeValue(Double reviseRtrTimeValue) {
		this.reviseRtrTimeValue = reviseRtrTimeValue;
	}
	public Double getReviseRtrTimeValue() {
		return reviseRtrTimeValue;
	}

	public void CopyBean2Quota(TroubleNormGuideBean bean) {
		
		
		Double interdictionTimeDareValue = bean.getInterdictionTimeDareValueCity();
		Double interdictionTimeNormValue = bean.getInterdictionTimeNormValueCity();
		Double interdictionTimesDareValue = bean.getInterdictionTimesDareValueCity();
		Double interdictionTimesNormValue = bean.getInterdictionTimesNormValueCity();
		Double rtrTimeDareValue = bean.getRtrTimeDareValueCity();
		Double rtrTimeNormValue = bean.getRtrTimeNormValueCity();
		Double singleRtrTimeNormValue = bean.getSingleRtrTimeNormValueCity();
		
		Double reviseInterdictionTimesValueCity=bean.getReviseInterdictionTimesValueCity();//千公里阻断次数  修正值
		Double reviseInterdictionTimeValueCity=bean.getReviseInterdictionTimesValueCity();//千公里阻断历时    修正值
		Double reviseRtrTimeValueCity=bean.getReviseRtrTimeValueCity();//平均抢修历时                      修正值
		setReviseInterdictionTimesValue(reviseInterdictionTimesValueCity);
		setReviseInterdictionTimeValue(reviseInterdictionTimeValueCity);
		setReviseRtrTimeValue(reviseRtrTimeValueCity);
		
		setInterdictionTimeDareValue(interdictionTimeDareValue);
		setInterdictionTimeNormValue(interdictionTimeNormValue);
		setInterdictionTimesDareValue(interdictionTimesDareValue);
		setInterdictionTimesNormValue(interdictionTimesNormValue);
		setRtrTimeDareValue(rtrTimeDareValue);
		setRtrTimeNormValue(rtrTimeNormValue);
		setSingleRtrTimeNormValue(singleRtrTimeNormValue);
	}

}