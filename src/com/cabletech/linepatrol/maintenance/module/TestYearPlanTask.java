package com.cabletech.linepatrol.maintenance.module;


import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
  ��ƻ�����
 */
public class TestYearPlanTask extends BaseDomainObject {
	private String id;
	private String yearPlanId;//��ƻ�id
	private String cableLevel;//���¼���
	private String cableLable;//���¼�������
	private int preTestNum;//���ǰ���Դ���
	private int applyNum;//������Դ���
	private int trunkNum;//����м̶�����
	private String trunkIds;
	private String trunkNames;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYearPlanId() {
		return yearPlanId;
	}
	public void setYearPlanId(String yearPlanId) {
		this.yearPlanId = yearPlanId;
	}
	public String getCableLevel() {
		return cableLevel;
	}
	public void setCableLevel(String cableLevel) {
		this.cableLevel = cableLevel;
	}
	public int getPreTestNum() {
		return preTestNum;
	}
	public void setPreTestNum(int preTestNum) {
		this.preTestNum = preTestNum;
	}
	public int getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}
	public String getTrunkIds() {
		return trunkIds;
	}
	public void setTrunkIds(String trunkIds) {
		this.trunkIds = trunkIds;
	}
	public String getTrunkNames() {
		return trunkNames;
	}
	public void setTrunkNames(String trunkNames) {
		this.trunkNames = trunkNames;
	}
	public String getCableLable() {
		return cableLable;
	}
	public void setCableLable(String cableLable) {
		this.cableLable = cableLable;
	}
	public int getTrunkNum() {
		return trunkNum;
	}
	public void setTrunkNum(int trunkNum) {
		this.trunkNum = trunkNum;
	}

}