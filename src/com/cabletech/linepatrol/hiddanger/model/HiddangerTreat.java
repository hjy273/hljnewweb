package com.cabletech.linepatrol.hiddanger.model;

import java.util.Date;

public class HiddangerTreat {
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
	private String treatremark;
	private Date hiddangerRemoveTime;
	private Date watchReliefTime;
	private String watchReliefReason;
	private String authorId;
	private Date writingTIme;
	
	public HiddangerTreat(){
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
	public String getTreatremark() {
		return treatremark;
	}
	public void setTreatremark(String treatremark) {
		this.treatremark = treatremark;
	}
	public Date getHiddangerRemoveTime() {
		return hiddangerRemoveTime;
	}
	public void setHiddangerRemoveTime(Date hiddangerRemoveTime) {
		this.hiddangerRemoveTime = hiddangerRemoveTime;
	}
	public Date getWatchReliefTime() {
		return watchReliefTime;
	}
	public void setWatchReliefTime(Date watchReliefTime) {
		this.watchReliefTime = watchReliefTime;
	}
	public String getWatchReliefReason() {
		return watchReliefReason;
	}
	public void setWatchReliefReason(String watchReliefReason) {
		this.watchReliefReason = watchReliefReason;
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
	
}