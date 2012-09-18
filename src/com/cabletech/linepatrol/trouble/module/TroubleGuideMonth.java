package com.cabletech.linepatrol.trouble.module;

import java.text.DecimalFormat;
import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleGuideMonth entity. @author MyEclipse Persistence Tools
 */

public class TroubleGuideMonth extends BaseDomainObject {
	public static final String NOT_FINISH_STATE="0";
	public static final String FINISH_STATE="1";
	// Fields
	
	private String id;
	private String guideType;//统计类型
	private String contractorId;
	private Date statMonth;
	private Double maintenanceLength;//本月维护公里数
	//add by xueyh 20110413 for updata 本月维护公里数
	private Double reviseMaintenanceLength;//本月维护公里数：修正值
	private Double interdictionNormTimes;//千公里阻断次数基准值
	private Double interdictionDareTimes;//千公里阻断次数挑战值
	private int troubleTimes;//故障次数
	private int reviseTroubleTimes;//修正后故障次数
	private Double interdictionNormTime;//千公里阻断时长基准值
	private Double interdictionDareTime;//千公里阻断时长挑战值
	private Double interdictionTime;//阻断时长
	private Double reviseInterdictionTime;//修正后阻断时长
	//private Double troubleInterdictionTime;//本月千公里阻断历时
//	private Double troubleInterdictionTimes;//本月千公里阻断次数
	private Double rtrInTime;//故障抢修及时率
	private Double feedbackInTime;//故障反馈及时率
	private Integer cityAreaOutStandardNumber;//城域网单次抢修历时超出指标值的故障次数
	private String finishState;
	
	
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
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public Date getStatMonth() {
		return statMonth;
	}
	public void setStatMonth(Date statMonth) {
		this.statMonth = statMonth;
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
	public int getReviseTroubleTimes() {
		return reviseTroubleTimes;
	}
	public void setReviseTroubleTimes(int reviseTroubleTimes) {
		this.reviseTroubleTimes = reviseTroubleTimes;
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
	public Double getReviseInterdictionTime() {
		return reviseInterdictionTime;
	}
	public void setReviseInterdictionTime(Double reviseInterdictionTime) {
		this.reviseInterdictionTime = reviseInterdictionTime;
	}
	public Double getRtrInTime() {
		return rtrInTime;
	}
	public void setRtrInTime(Double rtrInTime) {
		this.rtrInTime = rtrInTime;
	}
	public Double getFeedbackInTime() {
		return feedbackInTime;
	}
	public void setFeedbackInTime(Double feedbackInTime) {
		this.feedbackInTime = feedbackInTime;
	}
	public String getFinishState() {
		return finishState;
	}
	public void setFinishState(String finishState) {
		this.finishState = finishState;
	}
	public Integer getCityAreaOutStandardNumber() {
		return cityAreaOutStandardNumber;
	}
	public void setCityAreaOutStandardNumber(Integer cityAreaOutStandardNumber) {
		this.cityAreaOutStandardNumber = cityAreaOutStandardNumber;
	}
	public void setReviseMaintenanceLength(Double reviseMaintenanceLength) {
		this.reviseMaintenanceLength = reviseMaintenanceLength;
	}
	public Double getReviseMaintenanceLength() {
		return reviseMaintenanceLength;
	}

	
}