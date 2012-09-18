package com.cabletech.linepatrol.cut.beans;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class CutFeedbackBean extends BaseCommonBean {
	/**
	 * ��ӷ���
	 */
	private static final long serialVersionUID = -5868620757808220405L;
	private String id;
	private String cutId;// ���id
	private String isInterrupt;// �Ƿ��ж�ҵ��
	private float td;// Ӱ��td����
	private float bs;// Ӱ���վ����
	private String beginTime;// ʵ�ʿ�ʼʱ��
	private String endTime;// ʵ�ʽ���ʱ��
	private float cutTime;// ���ʱ��
	private float afterLength;// ��Ӻ󳤶�
	private String flag;// �Ƿ���»��������
	private float beforeWastage;// ���ǰ���
	private float afterWastage;// ��Ӻ����
	private float fibercoreNumber;// ��о���
	private String liveChargeMan;// �ֳ�������
	private String mobileChargeMan;// �ƶ�������
	private String isTimeOut;// �Ƿ�ʱ
	private String timeOutCase;// ��ʱԭ��
	private String implementation;// ʵʩ���
	private String legacyQuestion;// ��������
	private int unapproveNumber;// δͨ������
	private String cancelCause;//ȡ������ԭ��
	private String feedbackType;//�������ͣ�0 �������� 1 ȡ�����̷���
	private String creator;//������
	private String createTime;//����ʱ��
	
	//ҳ������
	private String operator;//��ά��Ա�Ĳ���������������ת��
	private String approveResult;//���������0 ��ͨ�� 1 ͨ�� 2 ת��
	private String approveRemark;//��������
	private String approveId;//������ԱId
	private String reader;//������
	private String approvers;//ת����ά��ԱId
	private String mobiles;//��ˡ�ת����ά��Ա�绰����
	
	private String proposer;//��������ύ��
	private String[] readerPhones;//�����˵绰����

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCutId() {
		return cutId;
	}

	public void setCutId(String cutId) {
		this.cutId = cutId;
	}

	public String getIsInterrupt() {
		return isInterrupt;
	}

	public void setIsInterrupt(String isInterrupt) {
		this.isInterrupt = isInterrupt;
	}

	public float getTd() {
		return td;
	}

	public void setTd(float td) {
		this.td = td;
	}

	public float getBs() {
		return bs;
	}

	public void setBs(float bs) {
		this.bs = bs;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public float getCutTime() {
		return cutTime;
	}

	public void setCutTime(float cutTime) {
		this.cutTime = cutTime;
	}

	public float getAfterLength() {
		return afterLength;
	}

	public void setAfterLength(float afterLength) {
		this.afterLength = afterLength;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public float getBeforeWastage() {
		return beforeWastage;
	}

	public void setBeforeWastage(float beforeWastage) {
		this.beforeWastage = beforeWastage;
	}

	public float getAfterWastage() {
		return afterWastage;
	}

	public void setAfterWastage(float afterWastage) {
		this.afterWastage = afterWastage;
	}

	public float getFibercoreNumber() {
		return fibercoreNumber;
	}

	public void setFibercoreNumber(float fibercoreNumber) {
		this.fibercoreNumber = fibercoreNumber;
	}

	public String getLiveChargeMan() {
		return liveChargeMan;
	}

	public void setLiveChargeMan(String liveChargeMan) {
		this.liveChargeMan = liveChargeMan;
	}

	public String getMobileChargeMan() {
		return mobileChargeMan;
	}

	public void setMobileChargeMan(String mobileChargeMan) {
		this.mobileChargeMan = mobileChargeMan;
	}

	public String getIsTimeOut() {
		return isTimeOut;
	}

	public void setIsTimeOut(String isTimeOut) {
		this.isTimeOut = isTimeOut;
	}

	public String getTimeOutCase() {
		return timeOutCase;
	}

	public void setTimeOutCase(String timeOutCase) {
		this.timeOutCase = timeOutCase;
	}

	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}

	public String getLegacyQuestion() {
		return legacyQuestion;
	}

	public void setLegacyQuestion(String legacyQuestion) {
		this.legacyQuestion = legacyQuestion;
	}

	public int getUnapproveNumber() {
		return unapproveNumber;
	}

	public void setUnapproveNumber(int unapproveNumber) {
		this.unapproveNumber = unapproveNumber;
	}

	public String getCancelCause() {
		return cancelCause;
	}

	public void setCancelCause(String cancelCause) {
		this.cancelCause = cancelCause;
	}

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getApprovers() {
		return approvers;
	}

	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
