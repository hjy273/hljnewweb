package com.cabletech.linepatrol.maintenance.beans;


import java.util.Date;

import com.cabletech.commons.base.BaseBean;
import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class TestCableDataBean extends BaseFileFormBean{
	private String id;
	private String testDataId;
	private String testPlanId;
	private String testCablelineId;
	private String factTestPort;//ʵ�ʲ��Զ�
	private String factTestTime;
	private String testPrincipal;//���Ը�����
	private String testMan;
	private String testAddress;
	private String testApparatus;//�����Ǳ�
	private String testMethod;//���Է���
	private String testWavelength;//���Բ���
	private String testRefractiveIndex;//����������
	private String testAvgTime;//����ƽ��ʱ��
	private String testState;
	private Date createTime;
	private String lineId;//������Ϣ��ϵͳ���


	// ��о¼��������Ϣ
	private String testCableDataId;//����¼������ϵͳ���
	private String chipSeq;//����
	private String isUsed;//�Ƿ�����
	private String isEligible;//�Ƿ�ϸ�
	private String isSave;//�Ƿ񱣴�
	private String attenuationConstant;//˥������
	private String testRemark;

	//�����м̶�
	private String problemDescription;//��������
	private String processComment;//�������˵��
	private String problemState;
	
	//��о���ݷ�����
	private String coreOrder;//����
	private String abEnd;//���Զ�
	private String baseStation;//���Ի�վ
	private String fileRemark;//�����ļ���¼˵��
	private Date testDate;//��¼����
	//��о���ȷ���
	private float refractiveIndex;//����������
	private float pulseWidth;//��������
	private float coreLength;//о��km
	private String isProblem;
	private String problemAnalyseLen;//�������
	private String lengremark;
	
	//˥����������
	private float decayConstant;//˥������dB/km
	private String isStandardDec;//�Ƿ�ϸ�
	private String problemAnalyseDec;//�������
	private String decayRemark;
	//�ɶ���ķ���
	private float endWaste;//�ɶ����dB
	private String isStandardEnd;//�Ƿ�ϸ�
	private String problemAnalyseEnd;
	private String endRemark;
    //��ͷ��ķ���
	private int[] orderNumber;//���
	private String[] connectorStation;//��ͷλ��
	private float[] waste;//���ֵdB
	private String[] problemAnalyse;//�������
	private String[] remark;
	
	//�쳣�¼�����
	private int[] orderNumberExe;//���
	private String[] eventStation;//�¼�λ��
	private float[] wasteExe;//���ֵdB
	private String[] problemAnalyseExe;//�������
	private String[] remarkExe;
	
	//��������
	private String analyseOther;//��������
	private String analyseResultOther;//�������
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
