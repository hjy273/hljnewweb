package com.cabletech.linepatrol.safeguard.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class SafeguardPlanBean extends BaseCommonBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2602582630955002004L;
	
	private String id;
	private String safeguardId;
	private String contractorId;
	private Integer planResponder;
	private String specialPlanId;
	private Integer planRespondingUnit;
	private String remark;
	private int auditingNum;
	private String maker;
	private String makeDate;
	private String isAllNet;//�Ƿ�Ϊȫ������
	private int equipmentNumber;//�ƻ�Ͷ���豸��
	private String deadline;//
	
	//ҳ������
	private String[] delfileid;//ɾ��������Ids
	
	private String operator;//��ά��Ա�Ĳ���������˻���ת��
	private String approveResult;//��˽����0 ��ͨ�� 1 ͨ�� 2 ת��
	private String approveRemark;//�������
	private String approveId;//�����ԱId
	private String approvers;//ת����ά��ԱId
	private String mobiles;//��ˡ�ת����ά��Ա�绰����
	
	private String startDate;//��ʼʱ��
	private String endDate;//����ʱ��
	
	private String[] readerPhones;//�����˵绰

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSafeguardId() {
		return safeguardId;
	}

	public void setSafeguardId(String safeguardId) {
		this.safeguardId = safeguardId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public Integer getPlanResponder() {
		return planResponder;
	}

	public void setPlanResponder(Integer planResponder) {
		this.planResponder = planResponder;
	}

	public String getSpecialPlanId() {
		return specialPlanId;
	}

	public void setSpecialPlanId(String specialPlanId) {
		this.specialPlanId = specialPlanId;
	}

	public Integer getPlanRespondingUnit() {
		return planRespondingUnit;
	}

	public void setPlanRespondingUnit(Integer planRespondingUnit) {
		this.planRespondingUnit = planRespondingUnit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getAuditingNum() {
		return auditingNum;
	}

	public void setAuditingNum(int auditingNum) {
		this.auditingNum = auditingNum;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}

	public int getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(int equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String[] getDelfileid() {
		return delfileid;
	}

	public void setDelfileid(String[] delfileid) {
		this.delfileid = delfileid;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}

	public String getIsAllNet() {
		return isAllNet;
	}

	public void setIsAllNet(String isAllNet) {
		this.isAllNet = isAllNet;
	}
	
}
