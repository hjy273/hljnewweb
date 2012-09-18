package com.cabletech.linepatrol.hiddanger.model;

import java.util.Date;

public class HiddangerReport {
	private String id;
	private String hiddangerId;
	private String address;
	private String distanceToCable;
	private String watchPrincipal;
	private String watchPrincipalPhone;
	private String impressLength;
	private String watchActualizeMan;
	private String watchActualizeManPhone;
	private String otherImpressCable;
	private String watchReason;
	private String watchMeasure;
	private String reportRemark;
	private Date planReliefTime;
	private Date watchBeginTime;
	private String signSecurityProtocol;
	private String workStage;
	private String workDepart;
	private String workPrincipalPhone;
	private String workPrincipal;
	private String authorId;
	private Date writingTIme;
	private String approveTimes = "0";
	
	public HiddangerReport(){
		writingTIme = new Date();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHiddangerId() {
		return hiddangerId;
	}
	public void setHiddangerId(String hiddangerId) {
		this.hiddangerId = hiddangerId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistanceToCable() {
		return distanceToCable;
	}
	public void setDistanceToCable(String distanceToCable) {
		this.distanceToCable = distanceToCable;
	}
	public String getWatchPrincipal() {
		return watchPrincipal;
	}
	public void setWatchPrincipal(String watchPrincipal) {
		this.watchPrincipal = watchPrincipal;
	}
	public String getWatchPrincipalPhone() {
		return watchPrincipalPhone;
	}
	public void setWatchPrincipalPhone(String watchPrincipalPhone) {
		this.watchPrincipalPhone = watchPrincipalPhone;
	}
	public String getImpressLength() {
		return impressLength;
	}
	public void setImpressLength(String impressLength) {
		this.impressLength = impressLength;
	}
	public String getWatchActualizeMan() {
		return watchActualizeMan;
	}
	public void setWatchActualizeMan(String watchActualizeMan) {
		this.watchActualizeMan = watchActualizeMan;
	}
	public String getWatchActualizeManPhone() {
		return watchActualizeManPhone;
	}
	public void setWatchActualizeManPhone(String watchActualizeManPhone) {
		this.watchActualizeManPhone = watchActualizeManPhone;
	}
	public String getOtherImpressCable() {
		return otherImpressCable;
	}
	public void setOtherImpressCable(String otherImpressCable) {
		this.otherImpressCable = otherImpressCable;
	}
	public String getWatchReason() {
		return watchReason;
	}
	public void setWatchReason(String watchReason) {
		this.watchReason = watchReason;
	}
	public String getWatchMeasure() {
		return watchMeasure;
	}
	public void setWatchMeasure(String watchMeasure) {
		this.watchMeasure = watchMeasure;
	}
	public String getReportRemark() {
		return reportRemark;
	}
	public void setReportRemark(String reportRemark) {
		this.reportRemark = reportRemark;
	}
	public Date getPlanReliefTime() {
		return planReliefTime;
	}
	public void setPlanReliefTime(Date planReliefTime) {
		this.planReliefTime = planReliefTime;
	}
	public Date getWatchBeginTime() {
		return watchBeginTime;
	}
	public void setWatchBeginTime(Date watchBeginTime) {
		this.watchBeginTime = watchBeginTime;
	}
	public String getSignSecurityProtocol() {
		return signSecurityProtocol;
	}
	public void setSignSecurityProtocol(String signSecurityProtocol) {
		this.signSecurityProtocol = signSecurityProtocol;
	}
	public String getWorkStage() {
		return workStage;
	}
	public void setWorkStage(String workStage) {
		this.workStage = workStage;
	}
	public String getWorkDepart() {
		return workDepart;
	}
	public void setWorkDepart(String workDepart) {
		this.workDepart = workDepart;
	}
	public String getWorkPrincipalPhone() {
		return workPrincipalPhone;
	}
	public void setWorkPrincipalPhone(String workPrincipalPhone) {
		this.workPrincipalPhone = workPrincipalPhone;
	}
	public String getWorkPrincipal() {
		return workPrincipal;
	}
	public void setWorkPrincipal(String workPrincipal) {
		this.workPrincipal = workPrincipal;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public Date getWritingTIme() {
		return writingTIme;
	}
	public void setWritingTIme(Date writingTIme) {
		this.writingTIme = writingTIme;
	}
	public String getApproveTimes() {
		return approveTimes;
	}
	public void setApproveTimes(String approveTimes) {
		this.approveTimes = approveTimes;
	}
	
}