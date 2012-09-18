package com.cabletech.linepatrol.safeguard.beans;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class SafeguardSummaryBean extends BaseCommonBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8708824080791905059L;
	
	private String id;
	private String planId;//���Ϸ���Id
	private int factResponder;//ʵ�ʳ�����Ա
	private int factRespondingUnit;//ʵ�ʳ�������
	private String summary;//�����ܽ�
	private int auditingNum;//��˴���
	private String sumManId;//�ܽ���
	private String sumDate;//�ܽ�ʱ��
	
	private int equipmentNumber;//�ƻ�Ͷ���豸��
	private String realStartTime;//ʵ�ʿ�ʼʱ��
	private String realEndTime;//ʵ�ʽ���ʱ��
	
	//ҳ������
	private String taskId;
	private String operator;//��ά��Ա�Ĳ���������˻���ת��
	private String approveResult;//��˽����0 ��ͨ�� 1 ͨ�� 2 ת��
	private String approveRemark;//�������
	private String approveId;//�����ԱId
	private String approvers;//ת����ά��ԱId
	private String mobiles;//��ˡ�ת����ά��Ա�绰����
	
	private String conId;//���ϼƻ���άid
	
	private String[] delfileid;//ɾ��������Ids
	private String[] readerPhones;//�����˵绰

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public int getFactResponder() {
		return factResponder;
	}

	public void setFactResponder(int factResponder) {
		this.factResponder = factResponder;
	}

	public int getFactRespondingUnit() {
		return factRespondingUnit;
	}

	public void setFactRespondingUnit(int factRespondingUnit) {
		this.factRespondingUnit = factRespondingUnit;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getAuditingNum() {
		return auditingNum;
	}

	public void setAuditingNum(int auditingNum) {
		this.auditingNum = auditingNum;
	}

	public String getSumManId() {
		return sumManId;
	}

	public void setSumManId(String sumManId) {
		this.sumManId = sumManId;
	}

	public String getSumDate() {
		return sumDate;
	}

	public void setSumDate(String sumDate) {
		this.sumDate = sumDate;
	}

	public int getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(int equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getRealStartTime() {
		return realStartTime;
	}

	public void setRealStartTime(String realStartTime) {
		this.realStartTime = realStartTime;
	}

	public String getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(String realEndTime) {
		this.realEndTime = realEndTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String[] getDelfileid() {
		return delfileid;
	}

	public void setDelfileid(String[] delfileid) {
		this.delfileid = delfileid;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}
}
