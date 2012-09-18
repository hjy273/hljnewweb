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
	private String guideType;//统计类型
	private String year;
	private Double maintenanceLength;//维护公里数
	private Double interdictionNormTimes;//千公里阻断次数基准值
	private Double interdictionDareTimes;//千公里阻断次数挑战值
	private int troubleTimes;//故障次数
	private Double interdictionNormTime;//千公里阻断时长基准值
	private Double interdictionDareTime;//千公里阻断时长挑战值
	private Double interdictionTime;//阻断时长
	//private Double troubleInterdictionTime;//千公里阻断历时
//	private Double troubleInterdictionTimes;//千公里阻断次数
	private Double rtrTimeNormValue;//光缆抢修平均时长指标基准值
	private Double rtrTimeDareValue;//光缆抢修平均时长指标挑战值
	private Double rtrTimeFinishValue;//光缆抢修平均时长指标完成值
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