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
	private Double interdictionTimesDareValue;//ǧ������ϴ�����սֵ
	private Double interdictionTimesNormValue;//ǧ������ϴ�����׼ֵ
	private Double interdictionTimeNormValue;//ǧ���������ʱ��׼ֵ
	private Double interdictionTimeDareValue;//ǧ���������ʱ��սֵ
	private Double rtrTimeNormValue;//ƽ��������ʱ��׼ֵ
	private Double rtrTimeDareValue;//ƽ��������ʱ��սֵ
	private Double singleRtrTimeNormValue;//����������ʱ��׼ֵ
	
	//add by xueyh  20110425 for  ����ֵ
	private Double reviseInterdictionTimesValue;//ǧ������ϴ���  ����ֵ
	private Double reviseInterdictionTimeValue;//ǧ���������ʱ    ����ֵ
	private Double reviseRtrTimeValue;//ƽ��������ʱ                                 ����ֵ
	
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
		
		Double reviseInterdictionTimesValueCity=bean.getReviseInterdictionTimesValueCity();//ǧ������ϴ���  ����ֵ
		Double reviseInterdictionTimeValueCity=bean.getReviseInterdictionTimesValueCity();//ǧ���������ʱ    ����ֵ
		Double reviseRtrTimeValueCity=bean.getReviseRtrTimeValueCity();//ƽ��������ʱ                      ����ֵ
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