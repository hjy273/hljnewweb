package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;

//�ʾ�������
public class QuestIssueGradeItem extends BaseDomainObject {
	private String id;
	private String itemId;//������ID
	private String questId;//�ʾ�ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getQuestId() {
		return questId;
	}

	public void setQuestId(String questId) {
		this.questId = questId;
	}
}
