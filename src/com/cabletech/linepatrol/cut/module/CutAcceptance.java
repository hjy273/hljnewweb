package com.cabletech.linepatrol.cut.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class CutAcceptance extends BaseDomainObject {
	/**
	 * ������ջ���
	 */
	private static final long serialVersionUID = -2218886186974340590L;
	private String id;
	private String cutId;// ���Id
	private float actualValue;// ʵ��ʹ�ý��
	private String isUpdate;// �Ƿ����ͼֽ
	private int unapproveNumber;// δͨ������
	private String creator;//������
	private Date createTime;//����ʱ��

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

	public float getActualValue() {
		return actualValue;
	}

	public void setActualValue(float actualValue) {
		this.actualValue = actualValue;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
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
}
