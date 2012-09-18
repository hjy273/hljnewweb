package com.cabletech.linepatrol.drill.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class DrillQueryConditionBean extends BaseCommonBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8770667862981266995L;

	private String name;// ��������
	private String[] levels;// ��������
	private String beginTime;// ��ʼʱ��
	private String endTime;// ����ʱ��
	private String[] state;// ����״̬
	private String conId;// ��ά
	
	private String drillState;//�Ƿ�ȡ��

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getLevels() {
		return levels;
	}

	public void setLevels(String[] levels) {
		this.levels = levels;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String[] getState() {
		return state;
	}

	public void setState(String[] state) {
		this.state = state;
	}

	public String getDrillState() {
		return drillState;
	}

	public void setDrillState(String drillState) {
		this.drillState = drillState;
	}
	
}
