package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;

//�ʾ��������
public class QuestContractor extends BaseDomainObject {
	public static final String ISSUE = "1";
	public static final String TEMPSAVE = "2";
	public static final String SAVE = "3";
	
	private String id;
	private String questId;//�ʾ�ID
	private String conId;//��ά��λID
	private String state;//1���·����ʾ�2���ѱ���� 3�����ύ��

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestId() {
		return questId;
	}

	public void setQuestId(String questId) {
		this.questId = questId;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
