package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;

//������
public class QuestIssueResult extends BaseDomainObject {
	private String id;
	private String score;//����
	private String reviewId;//�������
	private String itemId;//������ID
	private String userId;//�����û�

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
