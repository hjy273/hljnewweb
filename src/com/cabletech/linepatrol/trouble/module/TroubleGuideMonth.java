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
	private String guideType;//ͳ������
	private String contractorId;
	private Date statMonth;
	private Double maintenanceLength;//����ά��������
	//add by xueyh 20110413 for updata ����ά��������
	private Double reviseMaintenanceLength;//����ά��������������ֵ
	private Double interdictionNormTimes;//ǧ������ϴ�����׼ֵ
	private Double interdictionDareTimes;//ǧ������ϴ�����սֵ
	private int troubleTimes;//���ϴ���
	private int reviseTroubleTimes;//��������ϴ���
	private Double interdictionNormTime;//ǧ�������ʱ����׼ֵ
	private Double interdictionDareTime;//ǧ�������ʱ����սֵ
	private Double interdictionTime;//���ʱ��
	private Double reviseInterdictionTime;//���������ʱ��
	//private Double troubleInterdictionTime;//����ǧ���������ʱ
//	private Double troubleInterdictionTimes;//����ǧ������ϴ���
	private Double rtrInTime;//�������޼�ʱ��
	private Double feedbackInTime;//���Ϸ�����ʱ��
	private Integer cityAreaOutStandardNumber;//����������������ʱ����ָ��ֵ�Ĺ��ϴ���
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