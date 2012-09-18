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
	private Double interdictionTimesDareValue;//ǧ������ϴ�����սֵ
	private Double interdictionTimesNormValue;//ǧ������ϴ�����׼ֵ
	private Double interdictionTimeNormValue;//ǧ���������ʱ��׼ֵ
	private Double interdictionTimeDareValue;//ǧ���������ʱ��սֵ
	private Double rtrTimeNormValue;//ƽ��������ʱ��׼ֵ
	private Double rtrTimeDareValue;//ƽ��������ʱ��սֵ
	private Double singleRtrTimeNormValue;//����������ʱ��׼ֵ
	
	
	private int reviseTroubleTimes;//��������ϴ���
	private Double reviseInterdictionTime;//���������ʱ��
	//add by xueyh 20110425 for ��¼����ֵ��Ϊ��ʾ����ֵ׼��
	private Double reviseRtrTimeDareValue;//ƽ��������ʱ��սֵ
	private Double reviseInterdictionTimesValueCity;//ǧ������ϴ���  ����ֵ
	private Double reviseInterdictionTimeValueCity;//ǧ���������ʱ    ����ֵ
	private Double reviseRtrTimeValueCity;//ƽ��������ʱ                      ����ֵ
	
	private String idCity;
	private String guideTypeCity;
	private String guideNameCity;
	private Double interdictionTimesDareValueCity;//ǧ������ϴ�����սֵ
	private Double interdictionTimesNormValueCity;//ǧ������ϴ�����׼ֵ
	private Double interdictionTimeNormValueCity;//ǧ���������ʱ��׼ֵ
	private Double interdictionTimeDareValueCity;//ǧ���������ʱ��սֵ
	private Double rtrTimeNormValueCity;//ƽ��������ʱ��׼ֵ
	private Double rtrTimeDareValueCity;//ƽ��������ʱ��սֵ
	private Double singleRtrTimeNormValueCity;//����������ʱ��׼ֵ
	
	
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