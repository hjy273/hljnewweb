package com.cabletech.linepatrol.safeguard.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;

public class SafeguardTask extends BaseDomainObject {
	public static final String CANCELED_STATE = "999";

	private static final long serialVersionUID = -8563805897536326758L;

	private String id;
	private String safeguardCode;// 保障编号
	private String safeguardName;// 保障名称
	private Date startDate;// 开始时间
	private Date endDate;// 结束时间
	private String level;// 保障级别
	private String region;// 保障地点
	private String requirement;// 保障要求
	private String remark;// 备注
	private Date sendDate;// 派单时间
	private String sender;// 派单人
	private String regionId;// 区域

	private Date deadline;// 方案提交时限
	private String saveFlag;// 用于区分是否为临时保存：1 临时保存 0 提交

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private Date cancelTime;
	private String cancelTimeDis;
	private String safeguardState;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSafeguardCode() {
		return safeguardCode;
	}

	public void setSafeguardCode(String safeguardCode) {
		this.safeguardCode = safeguardCode;
	}

	public String getSafeguardName() {
		return safeguardName;
	}

	public void setSafeguardName(String safeguardName) {
		this.safeguardName = safeguardName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public String getCancelUserId() {
		return cancelUserId;
	}

	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}

	public String getCancelUserName() {
		return cancelUserName;
	}

	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
		this.cancelTimeDis=DateUtil.DateToString(cancelTime, "yyyy/MM/dd HH:mm:ss");
	}

	public String getCancelTimeDis() {
		return cancelTimeDis;
	}

	public void setCancelTimeDis(String cancelTimeDis) {
		this.cancelTimeDis = cancelTimeDis;
	}

	public String getSafeguardState() {
		return safeguardState;
	}

	public void setSafeguardState(String safeguardState) {
		this.safeguardState = safeguardState;
	}
}
