package com.cabletech.linepatrol.acceptance.beans;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.util.StringUtils;

public class RcBean extends BaseBean {
	private String cableId;
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
    private String[] laytypes;
   	private Float grossLength;
	private Float reservedLength;
	private String owner;
	private String builder;
	private String cabletype;
	private String finishtime;
	private String isCheckOut;
	private String refactiveIndex;
	private String havePicture;
	private String remark;
	private String maintenanceId;
	private String isMaintenance;
	private String projectName;
	private String scetion;
	
	private String cableAddOneId;
	private String isTd;
	private String cablelineId;
	private String trunkmentLength0;;
	private String trunkmentLength1;
	private String trunkmentLength2;
	private String trunkmentLength3;
	private String pipeLength0;
	private String pipeLength1;
	private String pipeLength2;
	private String pipeLength3;
	private String pipeLength4;
	private String e1Length;
	private String e1Number;
	private String e2Length;
	private String e2Number;
	private String e3Length;
	private String e3Number;
	private String t1Length;
	private String t1Number;
	private String t2Length;
	private String t2Number;
	private String t3Length;
	private String t3Number;
	private String jNumber;
	private String other1;
	private String other1Number;
	private String other2;
	private String other2Number;
	
	private String payCableId;
	private String buildUnit;
	private String buildAcceptance;
	private String workUnit;
	private String workAcceptance;
	private String surveillanceUnit;
	private String surveillanceAccept;
	private String maintenceUnit;
	private String maintenceAcceptance;
	
	private String cableResultId;
	private Integer times;
	private String result;
	private String planDate;
	private String factDate;
	private String resultRemark;
	private String drawing;
	private String isEligible0;
	private String eligibleReason0;
	private String isEligible1;
	private String eligibleReason1;
	private String isEligible2;
	private String eligibleReason2;
	private String isEligible3;
	private String eligibleReason3;
	private String isEligible4;
	private String eligibleReason4;
	private String isEligible5;
	private String eligibleReason5;
	private String isEligible6;
	private String eligibleReason6;
	
	private String contractorId;
	
	public String getCableId() {
		return cableId;
	}
	public void setCableId(String cableId) {
		this.cableId = cableId;
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
	public String getFinishtime() {
		return finishtime;
	}
	public void setFinishtime(String finishtime) {
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
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getScetion() {
		return scetion;
	}
	public void setScetion(String scetion) {
		this.scetion = scetion;
	}
	public String getCableAddOneId() {
		return cableAddOneId;
	}
	public void setCableAddOneId(String cableAddOneId) {
		this.cableAddOneId = cableAddOneId;
	}
	public String getIsTd() {
		return isTd;
	}
	public void setIsTd(String isTd) {
		this.isTd = isTd;
	}
	public String getCablelineId() {
		return cablelineId;
	}
	public void setCablelineId(String cablelineId) {
		this.cablelineId = cablelineId;
	}
	public String getTrunkmentLength0() {
		return trunkmentLength0;
	}
	public void setTrunkmentLength0(String trunkmentLength0) {
		this.trunkmentLength0 = trunkmentLength0;
	}
	public String getTrunkmentLength1() {
		return trunkmentLength1;
	}
	public void setTrunkmentLength1(String trunkmentLength1) {
		this.trunkmentLength1 = trunkmentLength1;
	}
	public String getTrunkmentLength2() {
		return trunkmentLength2;
	}
	public void setTrunkmentLength2(String trunkmentLength2) {
		this.trunkmentLength2 = trunkmentLength2;
	}
	public String getTrunkmentLength3() {
		return trunkmentLength3;
	}
	public void setTrunkmentLength3(String trunkmentLength3) {
		this.trunkmentLength3 = trunkmentLength3;
	}
	public String getPipeLength0() {
		return pipeLength0;
	}
	public void setPipeLength0(String pipeLength0) {
		this.pipeLength0 = pipeLength0;
	}
	public String getPipeLength1() {
		return pipeLength1;
	}
	public void setPipeLength1(String pipeLength1) {
		this.pipeLength1 = pipeLength1;
	}
	public String getPipeLength2() {
		return pipeLength2;
	}
	public void setPipeLength2(String pipeLength2) {
		this.pipeLength2 = pipeLength2;
	}
	public String getPipeLength3() {
		return pipeLength3;
	}
	public void setPipeLength3(String pipeLength3) {
		this.pipeLength3 = pipeLength3;
	}
	public String getPipeLength4() {
		return pipeLength4;
	}
	public void setPipeLength4(String pipeLength4) {
		this.pipeLength4 = pipeLength4;
	}
	public String getE1Length() {
		return e1Length;
	}
	public void setE1Length(String e1Length) {
		this.e1Length = e1Length;
	}
	public String getE1Number() {
		return e1Number;
	}
	public void setE1Number(String e1Number) {
		this.e1Number = e1Number;
	}
	public String getE2Length() {
		return e2Length;
	}
	public void setE2Length(String e2Length) {
		this.e2Length = e2Length;
	}
	public String getE2Number() {
		return e2Number;
	}
	public void setE2Number(String e2Number) {
		this.e2Number = e2Number;
	}
	public String getE3Length() {
		return e3Length;
	}
	public void setE3Length(String e3Length) {
		this.e3Length = e3Length;
	}
	public String getE3Number() {
		return e3Number;
	}
	public void setE3Number(String e3Number) {
		this.e3Number = e3Number;
	}
	public String getT1Length() {
		return t1Length;
	}
	public void setT1Length(String t1Length) {
		this.t1Length = t1Length;
	}
	public String getT1Number() {
		return t1Number;
	}
	public void setT1Number(String t1Number) {
		this.t1Number = t1Number;
	}
	public String getT2Length() {
		return t2Length;
	}
	public void setT2Length(String t2Length) {
		this.t2Length = t2Length;
	}
	public String getT2Number() {
		return t2Number;
	}
	public void setT2Number(String t2Number) {
		this.t2Number = t2Number;
	}
	public String getT3Length() {
		return t3Length;
	}
	public void setT3Length(String t3Length) {
		this.t3Length = t3Length;
	}
	public String getT3Number() {
		return t3Number;
	}
	public void setT3Number(String t3Number) {
		this.t3Number = t3Number;
	}
	public String getjNumber() {
		return jNumber;
	}
	public void setjNumber(String jNumber) {
		this.jNumber = jNumber;
	}
	public String getOther1() {
		return other1;
	}
	public void setOther1(String other1) {
		this.other1 = other1;
	}
	public String getOther1Number() {
		return other1Number;
	}
	public void setOther1Number(String other1Number) {
		this.other1Number = other1Number;
	}
	public String getOther2() {
		return other2;
	}
	public void setOther2(String other2) {
		this.other2 = other2;
	}
	public String getOther2Number() {
		return other2Number;
	}
	public void setOther2Number(String other2Number) {
		this.other2Number = other2Number;
	}
	public String getPayCableId() {
		return payCableId;
	}
	public void setPayCableId(String payCableId) {
		this.payCableId = payCableId;
	}
	public String getBuildUnit() {
		return buildUnit;
	}
	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}
	public String getBuildAcceptance() {
		return buildAcceptance;
	}
	public void setBuildAcceptance(String buildAcceptance) {
		this.buildAcceptance = buildAcceptance;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getWorkAcceptance() {
		return workAcceptance;
	}
	public void setWorkAcceptance(String workAcceptance) {
		this.workAcceptance = workAcceptance;
	}
	public String getSurveillanceUnit() {
		return surveillanceUnit;
	}
	public void setSurveillanceUnit(String surveillanceUnit) {
		this.surveillanceUnit = surveillanceUnit;
	}
	public String getSurveillanceAccept() {
		return surveillanceAccept;
	}
	public void setSurveillanceAccept(String surveillanceAccept) {
		this.surveillanceAccept = surveillanceAccept;
	}
	public String getMaintenceUnit() {
		return maintenceUnit;
	}
	public void setMaintenceUnit(String maintenceUnit) {
		this.maintenceUnit = maintenceUnit;
	}
	public String getMaintenceAcceptance() {
		return maintenceAcceptance;
	}
	public void setMaintenceAcceptance(String maintenceAcceptance) {
		this.maintenceAcceptance = maintenceAcceptance;
	}
	public String getCableResultId() {
		return cableResultId;
	}
	public void setCableResultId(String cableResultId) {
		this.cableResultId = cableResultId;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public String getFactDate() {
		return factDate;
	}
	public void setFactDate(String factDate) {
		this.factDate = factDate;
	}
	public String getResultRemark() {
		return resultRemark;
	}
	public void setResultRemark(String resultRemark) {
		this.resultRemark = resultRemark;
	}
	public String getDrawing() {
		return drawing;
	}
	public void setDrawing(String drawing) {
		this.drawing = drawing;
	}
	public String getIsEligible0() {
		return isEligible0;
	}
	public void setIsEligible0(String isEligible0) {
		this.isEligible0 = isEligible0;
	}
	public String getEligibleReason0() {
		return eligibleReason0;
	}
	public void setEligibleReason0(String eligibleReason0) {
		this.eligibleReason0 = eligibleReason0;
	}
	public String getIsEligible1() {
		return isEligible1;
	}
	public void setIsEligible1(String isEligible1) {
		this.isEligible1 = isEligible1;
	}
	public String getEligibleReason1() {
		return eligibleReason1;
	}
	public void setEligibleReason1(String eligibleReason1) {
		this.eligibleReason1 = eligibleReason1;
	}
	public String getIsEligible2() {
		return isEligible2;
	}
	public void setIsEligible2(String isEligible2) {
		this.isEligible2 = isEligible2;
	}
	public String getEligibleReason2() {
		return eligibleReason2;
	}
	public void setEligibleReason2(String eligibleReason2) {
		this.eligibleReason2 = eligibleReason2;
	}
	public String getIsEligible3() {
		return isEligible3;
	}
	public void setIsEligible3(String isEligible3) {
		this.isEligible3 = isEligible3;
	}
	public String getEligibleReason3() {
		return eligibleReason3;
	}
	public void setEligibleReason3(String eligibleReason3) {
		this.eligibleReason3 = eligibleReason3;
	}
	public String getIsEligible4() {
		return isEligible4;
	}
	public void setIsEligible4(String isEligible4) {
		this.isEligible4 = isEligible4;
	}
	public String getEligibleReason4() {
		return eligibleReason4;
	}
	public void setEligibleReason4(String eligibleReason4) {
		this.eligibleReason4 = eligibleReason4;
	}
	public String getIsEligible5() {
		return isEligible5;
	}
	public void setIsEligible5(String isEligible5) {
		this.isEligible5 = isEligible5;
	}
	public String getEligibleReason5() {
		return eligibleReason5;
	}
	public void setEligibleReason5(String eligibleReason5) {
		this.eligibleReason5 = eligibleReason5;
	}
	public String getIsEligible6() {
		return isEligible6;
	}
	public void setIsEligible6(String isEligible6) {
		this.isEligible6 = isEligible6;
	}
	public String getEligibleReason6() {
		return eligibleReason6;
	}
	public void setEligibleReason6(String eligibleReason6) {
		this.eligibleReason6 = eligibleReason6;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String[] getLaytypes() {
		return laytypes;
	}
	public void setLaytypes(String[] laytypes) {
		this.laytypes = laytypes;
	}
}
