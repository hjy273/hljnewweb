package com.cabletech.linepatrol.resource.model;


import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class RepeaterSection extends BaseDomainObject {
	private String kid;
	private String assetno;
    private String segmentid;
    private String segmentname;
    private String place;
    private String cableLevel;
    private String pointa;
    private String pointaodf;
    private String pointaport;
    private String pointz;
    private String pointzodf;
    private String pointzport;
    private String fiberType;
    private String coreNumber;
    private String producer;
    private String currentState;
    private String laytype;
   	private Float grossLength;
	private Float reservedLength;
	private String owner;
	private String builder;
	private String cabletype;
	private Date finishtime;
	private String isCheckOut;
	private String refactiveIndex;
	private String havePicture;
	private String remark;
	private String maintenanceId;
	private String isMaintenance;
	private String projectName;
	private String scetion;
	private String region;
	private String scrapState="false";
	
	private String MIS;
	private String remark2;
	private String remark3;
	
	public String getScetion() {
		return scetion;
	}
	public void setScetion(String scetion) {
		this.scetion = scetion;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getKid() {
		return kid;
	}
	public void setKid(String kid) {
		this.kid = kid;
	}
	public String getAssetno() {
		return assetno;
	}
	public void setAssetno(String assetno) {
		this.assetno = assetno;
	}
	public String getSegmentid() {
		return segmentid;
	}
	public void setSegmentid(String segmentid) {
		this.segmentid = segmentid;
	}
	public String getSegmentname() {
		return segmentname;
	}
	public void setSegmentname(String segmentname) {
		this.segmentname = segmentname;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getCableLevel() {
		return cableLevel;
	}
	public void setCableLevel(String cableLevel) {
		this.cableLevel = cableLevel;
	}
	public String getPointa() {
		return pointa;
	}
	public void setPointa(String pointa) {
		this.pointa = pointa;
	}
	public String getPointaodf() {
		return pointaodf;
	}
	public void setPointaodf(String pointaodf) {
		this.pointaodf = pointaodf;
	}
	public String getPointaport() {
		return pointaport;
	}
	public void setPointaport(String pointaport) {
		this.pointaport = pointaport;
	}
	public String getPointz() {
		return pointz;
	}
	public void setPointz(String pointz) {
		this.pointz = pointz;
	}
	public String getPointzodf() {
		return pointzodf;
	}
	public void setPointzodf(String pointzodf) {
		this.pointzodf = pointzodf;
	}
	public String getPointzport() {
		return pointzport;
	}
	public void setPointzport(String pointzport) {
		this.pointzport = pointzport;
	}
	public String getFiberType() {
		return fiberType;
	}
	public void setFiberType(String fiberType) {
		this.fiberType = fiberType;
	}
	public String getCoreNumber() {
		return coreNumber;
	}
	public void setCoreNumber(String coreNumber) {
		this.coreNumber = coreNumber;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String getLaytype() {
		return laytype;
	}
	public void setLaytype(String laytype) {
		this.laytype = laytype;
	}
	public Float getGrossLength() {
		return grossLength;
	}
	public void setGrossLength(Float grossLength) {
		this.grossLength = grossLength;
	}
	public Float getReservedLength() {
		return reservedLength;
	}
	public void setReservedLength(Float reservedLength) {
		this.reservedLength = reservedLength;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getBuilder() {
		return builder;
	}
	public void setBuilder(String builder) {
		this.builder = builder;
	}
	public String getCabletype() {
		return cabletype;
	}
	public void setCabletype(String cabletype) {
		this.cabletype = cabletype;
	}
	public Date getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}
	public String getIsCheckOut() {
		return isCheckOut;
	}
	public void setIsCheckOut(String isCheckOut) {
		this.isCheckOut = isCheckOut;
	}
	public String getRefactiveIndex() {
		return refactiveIndex;
	}
	public void setRefactiveIndex(String refactiveIndex) {
		this.refactiveIndex = refactiveIndex;
	}
	public String getHavePicture() {
		return havePicture;
	}
	public void setHavePicture(String havePicture) {
		this.havePicture = havePicture;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMaintenanceId() {
		return maintenanceId;
	}
	public void setMaintenanceId(String maintenanceId) {
		this.maintenanceId = maintenanceId;
	}
	public String getIsMaintenance() {
		return isMaintenance;
	}
	public void setIsMaintenance(String isMaintenance) {
		this.isMaintenance = isMaintenance;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getScrapState() {
		return scrapState;
	}
	public void setScrapState(String scrapState) {
		this.scrapState = scrapState;
	}
	public String getMIS() {
		return MIS;
	}
	public void setMIS(String mIS) {
		MIS = mIS;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
}
