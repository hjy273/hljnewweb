package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;

//�ʾ�����
public class QuestType extends BaseDomainObject {
	private String id;
	private String type;//��������
	private String remark;//��ע

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
