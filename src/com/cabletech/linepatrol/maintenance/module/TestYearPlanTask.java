package com.cabletech.linepatrol.maintenance.module;


import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
  年计划任务
 */
public class TestYearPlanTask extends BaseDomainObject {
	private String id;
	private String yearPlanId;//年计划id
	private String cableLevel;//光缆级别
	private String cableLable;//光缆级别名称
	private int preTestNum;//变更前测试次数
	private int applyNum;//申请测试次数
	private int trunkNum;//变更中继段数量
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