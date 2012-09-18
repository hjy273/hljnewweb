package com.cabletech.linepatrol.quest.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

//�ʾ���
public class QuestIssue extends BaseDomainObject {
	public static final String TEMPSAVE = "1";
	public static final String SAVE = "2";
	
	public static final String QUESTCONTRACTOR = "quest_contractor";
	
	
	private String id;
	private String name;//�ʾ�����
	private String type;//�ʾ�����
	private String remark;//��ע
	private String state;//�ʾ�״̬ 1 ����  2 ����
	private String creator;//�ʾ�����
	private Date createDate;//��������

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
