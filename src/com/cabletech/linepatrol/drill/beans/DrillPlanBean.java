package com.cabletech.linepatrol.drill.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class DrillPlanBean extends BaseCommonBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2932943973035379399L;

	private String id;
	private String taskId;// ����Id
	private String contractorId;// ��άId
	private int personNumber;// �ƻ�Ͷ������
	private int carNumber;// �ƻ�Ͷ�복��
	private String scenario;// ����
	private String remark;// ��ע
	private int unapproveNumber;// δͨ������
	private String creator;// ������
	private String createTime;// ����ʱ��
	
	private String realBeginTime;//ʵ��������ʼʱ��
	private String realEndTime;//ʵ����������ʱ��
	private String address;//ʵ�������ص�
	private String equipmentNumber;//�ƻ�Ͷ���豸��
	
	private String deadline;//�ܽ��ύʱ��
	
	//��ά��Ա�Զ�����������
	private String name;//������������
	private String beginTime;//����������ʼʱ��
	private String endTime;//������������ʱ��
	private String locale;//���������ص�
	
	//ҳ������
	private String[] delfileid;//ɾ��������Ids
	private String operator;//��ά��Ա�Ĳ���������˻���ת��
	private String approveResult;//��˽����0 ��ͨ�� 1 ͨ�� 2 ת��
	private String approveRemark;//�������
	private String approveId;//�����ԱId
	private String approvers;//ת����ά��ԱId
	private String mobile;//��ˡ�ת����ά��Ա�绰����
	private String[] readerPhones;//�����˵绰
	
	private String planState;//������������ʱ���ֱ�ǣ���Ϊ0ʱ��ʾ���� 1Ϊ��ʱ����

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public int getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(int personNumber) {
		this.personNumber = personNumber;
	}

	public int getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getUnapproveNumber() {
		return unapproveNumber;
	}

	public void setUnapproveNumber(int unapproveNumber) {
		this.unapproveNumber = unapproveNumber;
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

	public String getRealBeginTime() {
		return realBeginTime;
	}

	public void setRealBeginTime(String realBeginTime) {
		this.realBeginTime = realBeginTime;
	}

	public String getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(String realEndTime) {
		this.realEndTime = realEndTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPlanState() {
		return planState;
	}

	public void setPlanState(String planState) {
		this.planState = planState;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}
}
