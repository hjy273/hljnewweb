package com.cabletech.linepatrol.trouble.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

/**
 * LpTroubleGuideMonth entity. @author MyEclipse Persistence Tools
 */

public class TroubleYearQuotaBean extends BaseCommonBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String guideType;//ͳ������
	private String year;
	private Double maintenanceLength;//ά��������
	private Double interdictionNormTimes;//ǧ������ϴ�����׼ֵ
	private Double interdictionDareTimes;//ǧ������ϴ�����սֵ
	private int troubleTimes;//���ϴ���
	private Double interdictionNormTime;//ǧ�������ʱ����׼ֵ
	private Double interdictionDareTime;//ǧ�������ʱ����սֵ
	private Double interdictionTime;//���ʱ��
	//private Double troubleInterdictionTime;//ǧ���������ʱ
//	private Double troubleInterdictionTimes;//ǧ������ϴ���
	private Double rtrTimeNormValue;//��������ƽ��ʱ��ָ���׼ֵ
	private Double rtrTimeDareValue;//��������ƽ��ʱ��ָ����սֵ
	private Double rtrTimeFinishValue;//��������ƽ��ʱ��ָ�����ֵ
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