package com.cabletech.analysis.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * ����ǰָ��ʱ�̵�Ѳ������\������\ �Լ�������\��ά��λ\Ѳ�������������
 */
public class CurrentTimeBean {
	private String intersectPoint;// ʱ�佻���
	private int onlineNum;// ��������
	private int noteNum;// ������
	/* ������ �������������б� ��ά��λ���������б� Ѳ�������������б� */
	private List numList;

	public CurrentTimeBean() {
		numList = new ArrayList();
	}

	public String getIntersectPoint() {
		return intersectPoint;
	}

	public void setIntersectPoint(String intersectPoint) {
		this.intersectPoint = intersectPoint;
	}

	public int getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}

	public int getNoteNum() {
		return noteNum;
	}

	public void setNoteNum(int noteNum) {
		this.noteNum = noteNum;
	}

	public List getNumList() {
		return numList;
	}

	public void setNumList(List numList) {
		this.numList = numList;
	}

	public String toSting() {
		return ToStringBuilder.reflectionToString(this);
	}
}
