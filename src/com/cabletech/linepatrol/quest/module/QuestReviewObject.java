package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;

//��������
public class QuestReviewObject extends BaseDomainObject {
	private String id;
	private String object;//������������

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}
}
