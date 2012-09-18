package com.cabletech.linepatrol.maintenance.beans;


import java.util.Date;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class TestCableDataBean extends BaseFileFormBean{
	private String id;
	private String testDataId;
	private String testPlanId;
	private String testCablelineId;
	private String factTestPort;//实际测试端
	private String factTestTime;
	private String testPrincipal;//测试负责人
	private String testMan;
	private String testAddress;
	private String testApparatus;//测试仪表
	private String testMethod;//测试方法
	private String testWavelength;//测试波长
	private String testRefractiveIndex;//所设折射率
	private String testAvgTime;//测试平均时间
	private String testState;
	private Date createTime;
	private String lineId;//备纤信息表系统编号


	// 纤芯录入数据信息
	private String testCableDataId;//备纤录入数据系统编号
	private String chipSeq;//纤序
	private String isUsed;//是否在用
	private String isEligible;//是否合格
	private String isSave;//是否保存
	private String attenuationConstant;//衰减常数
	private String testRemark;

	//问题中继段
	private String problemDescription;//问题描述
	private String processComment;//处理跟踪说明
	private String problemState;
	
	//纤芯数据分析表
	private String coreOrder;//纤序
	private String abEnd;//测试端
	private String baseStation;//测试基站
	private String fileRemark;//分析文件记录说明
	private Date testDate;//记录日期
	//纤芯长度分析
	private float refractiveIndex;//测试折射率
	private float pulseWidth;//测试脉宽
	private float coreLength;//芯长km
	private String isProblem;
	private String problemAnalyseLen;//问题分析
	private String lengremark;
	
	//衰减常数分析
	private float decayConstant;//衰减常数dB/km
	private String isStandardDec;//是否合格
	private String problemAnalyseDec;//问题分析
	private String decayRemark;
	//成端损耗分析
	private float endWaste;//成端损耗dB
	private String isStandardEnd;//是否合格
	private String problemAnalyseEnd;
	private String endRemark;
    //接头损耗分析
	private int[] orderNumber;//序号
	private String[] connectorStation;//接头位置
	private float[] waste;//损耗值dB
	private String[] problemAnalyse;//问题分析
	private String[] remark;
	
	//异常事件分析
	private int[] orderNumberExe;//序号
	private String[] eventStation;//事件位置
	private float[] wasteExe;//损耗值dB
	private String[] problemAnalyseExe;//问题分析
	private String[] remarkExe;
	
	//其他分析
	private String analyseOther;//分析简述
	private String analyseResultOther;//分析结果
	private String remarkOther;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTestDataId() {
		return testDataId;
	}
	public void setTestDataId(String testDataId) {
		this.testDataId = testDataId;
	}
	public String getTestPlanId() {
		return testPlanId;
	}
	public void setTestPlanId(String testPlanId) {
		this.testPlanId = testPlanId;
	}
	public String getTestCablelineId() {
		return testCablelineId;
	}
	public void setTestCablelineId(String testCablelineId) {
		this.testCablelineId = testCablelineId;
	}
	public String getFactTestPort() {
		return factTestPort;
	}
	public void setFactTestPort(String factTestPort) {
		this.factTestPort = factTestPort;
	}
	public String getFactTestTime() {
		return factTestTime;
	}
	public void setFactTestTime(String factTestTime) {
		this.factTestTime = factTestTime;
	}
	public String getTestPrincipal() {
		return testPrincipal;
	}
	public void setTestPrincipal(String testPrincipal) {
		this.testPrincipal = testPrincipal;
	}
	public String getTestMan() {
		return testMan;
	}
	public void setTestMan(String testMan) {
		this.testMan = testMan;
	}
	public String getTestAddress() {
		return testAddress;
	}
	public void setTestAddress(String testAddress) {
		this.testAddress = testAddress;
	}
	public String getTestApparatus() {
		return testApparatus;
	}
	public void setTestApparatus(String testApparatus) {
		this.testApparatus = testApparatus;
	}
	public String getTestMethod() {
		return testMethod;
	}
	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}
	public String getTestWavelength() {
		return testWavelength;
	}
	public void setTestWavelength(String testWavelength) {
		this.testWavelength = testWavelength;
	}
	public String getTestRefractiveIndex() {
		return testRefractiveIndex;
	}
	public void setTestRefractiveIndex(String testRefractiveIndex) {
		this.testRefractiveIndex = testRefractiveIndex;
	}
	public String getTestAvgTime() {
		return testAvgTime;
	}
	public void setTestAvgTime(String testAvgTime) {
		this.testAvgTime = testAvgTime;
	}
	public String getTestState() {
		return testState;
	}
	public void setTestState(String testState) {
		this.testState = testState;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTestCableDataId() {
		return testCableDataId;
	}
	public void setTestCableDataId(String testCableDataId) {
		this.testCableDataId = testCableDataId;
	}
	public String getChipSeq() {
		return chipSeq;
	}
	public void setChipSeq(String chipSeq) {
		this.chipSeq = chipSeq;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public String getIsEligible() {
		return isEligible;
	}
	public void setIsEligible(String isEligible) {
		this.isEligible = isEligible;
	}
	public String getAttenuationConstant() {
		return attenuationConstant;
	}
	public void setAttenuationConstant(String attenuationConstant) {
		this.attenuationConstant = attenuationConstant;
	}
	public String getTestRemark() {
		return testRemark;
	}
	public void setTestRemark(String testRemark) {
		this.testRemark = testRemark;
	}
	public String getProblemDescription() {
		return problemDescription;
	}
	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}
	public String getProcessComment() {
		return processComment;
	}
	public void setProcessComment(String processComment) {
		this.processComment = processComment;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getIsSave() {
		return isSave;
	}
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}
	public String getProblemState() {
		return problemState;
	}
	public void setProblemState(String problemState) {
		this.problemState = problemState;
	}
	public String getCoreOrder() {
		return coreOrder;
	}
	public void setCoreOrder(String coreOrder) {
		this.coreOrder = coreOrder;
	}
	public String getAbEnd() {
		return abEnd;
	}
	public void setAbEnd(String abEnd) {
		this.abEnd = abEnd;
	}
	public String getBaseStation() {
		return baseStation;
	}
	public void setBaseStation(String baseStation) {
		this.baseStation = baseStation;
	}
	public String getFileRemark() {
		return fileRemark;
	}
	public void setFileRemark(String fileRemark) {
		this.fileRemark = fileRemark;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public float getRefractiveIndex() {
		return refractiveIndex;
	}
	public void setRefractiveIndex(float refractiveIndex) {
		this.refractiveIndex = refractiveIndex;
	}
	public float getPulseWidth() {
		return pulseWidth;
	}
	public void setPulseWidth(float pulseWidth) {
		this.pulseWidth = pulseWidth;
	}
	public float getCoreLength() {
		return coreLength;
	}
	public void setCoreLength(float coreLength) {
		this.coreLength = coreLength;
	}
	public String getIsProblem() {
		return isProblem;
	}
	public void setIsProblem(String isProblem) {
		this.isProblem = isProblem;
	}
	public String getProblemAnalyseLen() {
		return problemAnalyseLen;
	}
	public void setProblemAnalyseLen(String problemAnalyseLen) {
		this.problemAnalyseLen = problemAnalyseLen;
	}
	public String getLengremark() {
		return lengremark;
	}
	public void setLengremark(String lengremark) {
		this.lengremark = lengremark;
	}
	public float getDecayConstant() {
		return decayConstant;
	}
	public void setDecayConstant(float decayConstant) {
		this.decayConstant = decayConstant;
	}
	public String getIsStandardDec() {
		return isStandardDec;
	}
	public void setIsStandardDec(String isStandardDec) {
		this.isStandardDec = isStandardDec;
	}
	public String getProblemAnalyseDec() {
		return problemAnalyseDec;
	}
	public void setProblemAnalyseDec(String problemAnalyseDec) {
		this.problemAnalyseDec = problemAnalyseDec;
	}
	public String getDecayRemark() {
		return decayRemark;
	}
	public void setDecayRemark(String decayRemark) {
		this.decayRemark = decayRemark;
	}
	public float getEndWaste() {
		return endWaste;
	}
	public void setEndWaste(float endWaste) {
		this.endWaste = endWaste;
	}
	public String getIsStandardEnd() {
		return isStandardEnd;
	}
	public void setIsStandardEnd(String isStandardEnd) {
		this.isStandardEnd = isStandardEnd;
	}
	public String getProblemAnalyseEnd() {
		return problemAnalyseEnd;
	}
	public void setProblemAnalyseEnd(String problemAnalyseEnd) {
		this.problemAnalyseEnd = problemAnalyseEnd;
	}
	public String getEndRemark() {
		return endRemark;
	}
	public void setEndRemark(String endRemark) {
		this.endRemark = endRemark;
	}
	public int[] getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int[] orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String[] getConnectorStation() {
		return connectorStation;
	}
	public void setConnectorStation(String[] connectorStation) {
		this.connectorStation = connectorStation;
	}
	public float[] getWaste() {
		return waste;
	}
	public void setWaste(float[] waste) {
		this.waste = waste;
	}
	public String[] getProblemAnalyse() {
		return problemAnalyse;
	}
	public void setProblemAnalyse(String[] problemAnalyse) {
		this.problemAnalyse = problemAnalyse;
	}
	public String[] getRemark() {
		return remark;
	}
	public void setRemark(String[] remark) {
		this.remark = remark;
	}
	public int[] getOrderNumberExe() {
		return orderNumberExe;
	}
	public void setOrderNumberExe(int[] orderNumberExe) {
		this.orderNumberExe = orderNumberExe;
	}
	public String[] getEventStation() {
		return eventStation;
	}
	public void setEventStation(String[] eventStation) {
		this.eventStation = eventStation;
	}
	public float[] getWasteExe() {
		return wasteExe;
	}
	public void setWasteExe(float[] wasteExe) {
		this.wasteExe = wasteExe;
	}
	public String[] getProblemAnalyseExe() {
		return problemAnalyseExe;
	}
	public void setProblemAnalyseExe(String[] problemAnalyseExe) {
		this.problemAnalyseExe = problemAnalyseExe;
	}
	public String[] getRemarkExe() {
		return remarkExe;
	}
	public void setRemarkExe(String[] remarkExe) {
		this.remarkExe = remarkExe;
	}
	public String getAnalyseOther() {
		return analyseOther;
	}
	public void setAnalyseOther(String analyseOther) {
		this.analyseOther = analyseOther;
	}
	public String getAnalyseResultOther() {
		return analyseResultOther;
	}
	public void setAnalyseResultOther(String analyseResultOther) {
		this.analyseResultOther = analyseResultOther;
	}
	public String getRemarkOther() {
		return remarkOther;
	}
	public void setRemarkOther(String remarkOther) {
		this.remarkOther = remarkOther;
	}
}
