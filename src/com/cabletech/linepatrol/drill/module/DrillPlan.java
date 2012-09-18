package com.cabletech.linepatrol.drill.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class DrillPlan extends BaseDomainObject {

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
	private Date createTime;// ����ʱ��
	
	private Date realBeginTime;//ʵ��������ʼʱ��
	private Date realEndTime;//ʵ����������ʱ��
	private String address;//ʵ�������ص�
	private String equipmentNumber;//�ƻ�Ͷ���豸��
	
	private Date deadline;//�ܽ��ύʱ��

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRealBeginTime() {
		return realBeginTime;
	}

	public void setRealBeginTime(Date realBeginTime) {
		this.realBeginTime = realBeginTime;
	}

	public Date getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(Date realEndTime) {
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}
