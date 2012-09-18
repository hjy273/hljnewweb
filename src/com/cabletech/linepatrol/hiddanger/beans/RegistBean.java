package com.cabletech.linepatrol.hiddanger.beans;

import com.cabletech.commons.base.BaseBean;

public class RegistBean extends BaseBean {
	private String id;
	private String splanId;
	private String hiddangerNumber;
	private String name;
	private String x;
	private String y;
	private String findTime;
	private String findType;
	private String type;
	private String code;
	private String reporter;
	private String treatDepartment;
	private String creater;
	private String createrDept;
	private String hiddangerLevel;
	private String remark;
	private String createTime;
	private String regionId;
	private String hiddangerState;
	private boolean readOnly;
	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private String cancelTime;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getSplanId() {
		return splanId;
	}

	public void setSplanId(String splanId) {
		this.splanId = splanId;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFindTime() {
		return findTime;
	}

	public void setFindTime(String findTime) {
		this.findTime = findTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getHiddangerNumber() {
		return hiddangerNumber;
	}

	public void setHiddangerNumber(String hiddangerNumber) {
		this.hiddangerNumber = hiddangerNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getTreatDepartment() {
		return treatDepartment;
	}

	public void setTreatDepartment(String treatDepartment) {
		this.treatDepartment = treatDepartment;
	}

	public String getHiddangerLevel() {
		return hiddangerLevel;
	}

	public void setHiddangerLevel(String hiddangerLevel) {
		this.hiddangerLevel = hiddangerLevel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreaterDept() {
		return createrDept;
	}

	public void setCreaterDept(String createrDept) {
		this.createrDept = createrDept;
	}

	public String getHiddangerState() {
		return hiddangerState;
	}

	public void setHiddangerState(String hiddangerState) {
		this.hiddangerState = hiddangerState;
	}

	public String getFindType() {
		return findType;
	}

	public void setFindType(String findType) {
		this.findType = findType;
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

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}
}
