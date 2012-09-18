package com.cabletech.linepatrol.drill.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class DrillSummary extends BaseDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2932943973035379399L;

	private String id;
	private String planId;// ����Id
	private int personNumber;// ʵ��Ͷ������
	private int carNumber;// ʵ��Ͷ�복��
	private String summary;// �ܽ�
	private int unapproveNumber;// δͨ������
	private String creator;// ������
	private Date createTime;// ����ʱ��
	
	private int equipmentNumber;//ʵ��Ͷ���豸��

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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public int getEquipmentNumber() {
		return equipmentNumber;
	}

	public void setEquipmentNumber(int equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}
}
