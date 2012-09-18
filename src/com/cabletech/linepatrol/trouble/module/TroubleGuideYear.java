package com.cabletech.linepatrol.trouble.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleGuideMonth entity. @author MyEclipse Persistence Tools
 */

public class TroubleGuideYear extends BaseDomainObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String guideType;//ͳ������
	private String year;
	private Double maintenanceLength=0.0;//ά��������
	private Double interdictionNormTimes=0.0;//ǧ������ϴ�����׼ֵ
	private Double interdictionDareTimes=0.0;//ǧ������ϴ�����սֵ
	private int troubleTimes;//���ϴ���
	private Double interdictionNormTime=0.0;//ǧ�������ʱ����׼ֵ
	private Double interdictionDareTime=0.0;//ǧ�������ʱ����սֵ
	private Double interdictionTime=0.0;//���ʱ��
	//private Double troubleInterdictionTime;//ǧ���������ʱ
//	private Double troubleInterdictionTimes;//ǧ������ϴ���
	private Double rtrTimeNormValue=0.0;//��������ƽ��ʱ��ָ���׼ֵ
	private Double rtrTimeDareValue=0.0;//��������ƽ��ʱ��ָ����սֵ
	private Double rtrTimeFinishValue=0.0;//��������ƽ��ʱ��ָ�����ֵ
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
	public Double getRtrTimeFinishValue() {
		return rtrTimeFinishValue;
	}
	public void setRtrTimeFinishValue(Double rtrTimeFinishValue) {
		this.rtrTimeFinishValue = rtrTimeFinishValue;
	}
	
	
	
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
	
	public Double getMaintenanceLength() {
		return maintenanceLength;
	}
	public void setMaintenanceLength(Double maintenanceLength) {
		this.maintenanceLength = maintenanceLength;
	}
	public Double getInterdictionNormTimes() {
		return interdictionNormTimes;
	}
	public void setInterdictionNormTimes(Double interdictionNormTimes) {
		this.interdictionNormTimes = interdictionNormTimes;
	}
	public Double getInterdictionDareTimes() {
		return interdictionDareTimes;
	}
	public void setInterdictionDareTimes(Double interdictionDareTimes) {
		this.interdictionDareTimes = interdictionDareTimes;
	}
	public int getTroubleTimes() {
		return troubleTimes;
	}
	public void setTroubleTimes(int troubleTimes) {
		this.troubleTimes = troubleTimes;
	}
	public Double getInterdictionNormTime() {
		return interdictionNormTime;
	}
	public void setInterdictionNormTime(Double interdictionNormTime) {
		this.interdictionNormTime = interdictionNormTime;
	}
	public Double getInterdictionDareTime() {
		return interdictionDareTime;
	}
	public void setInterdictionDareTime(Double interdictionDareTime) {
		this.interdictionDareTime = interdictionDareTime;
	}
	public Double getInterdictionTime() {
		return interdictionTime;
	}
	public void setInterdictionTime(Double interdictionTime) {
		this.interdictionTime = interdictionTime;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
}