package com.cabletech.linepatrol.safeguard.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class SpecialEndPlan extends BaseDomainObject {
	public static final String ENDPLAN = "1";
	public static final String REDO = "2";
	
	private String id;
	private String planId;// 特巡计划ID
	private String endType;// 结束类型  1：隐患消除（或巡检终止） 2：重新制定
	private Date prevEndDate;// 终止前计划结束时间
	private Date endDate;// 终止计划结束时间
	private String reason;// 终止原因
	private String creater;// 创建人
	private Date createTime;// 创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getEndType() {
		return endType;
	}

	public void setEndType(String endType) {
		this.endType = endType;
	}

	public Date getPrevEndDate() {
		return prevEndDate;
	}

	public void setPrevEndDate(Date prevEndDate) {
		this.prevEndDate = prevEndDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
