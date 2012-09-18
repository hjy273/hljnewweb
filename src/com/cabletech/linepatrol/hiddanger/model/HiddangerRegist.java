package com.cabletech.linepatrol.hiddanger.model;

import java.util.Date;

import com.cabletech.commons.util.DateUtil;

public class HiddangerRegist {
	public static final String CANCELED_STATE = "999";
	
	private String id;
	private String splanId;
	private String name;
	private String hiddangerNumber;
	private String x;
	private String y;
	private String reporter;
	private Date findTime;
	private String terminal;
	private String findType;
	private String type;
	private String code;
	private String treatDepartment;
	private String creater;
	private String createrDept;
	private Date createTime;
	private String hiddangerState;
	private String hiddangerLevel;
	private String remark;
	private String regionId;
	private String processInstanceId;
	private String terminnalState;
	private String flowTaskName;
	private boolean readOnly;
	private boolean edit = false;
	
	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private Date cancelTime;
	private String cancelTimeDis;
	private String hideDangerState;

	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public String getCreaterDept() {
		return createrDept;
	}
	public void setCreaterDept(String createrDept) {
		this.createrDept = createrDept;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSplanId() {
		return splanId;
	}
	public void setSplanId(String splanId) {
		this.splanId = splanId;
	}
	public String getHiddangerNumber() {
		return hiddangerNumber;
	}
	public void setHiddangerNumber(String hiddangerNumber) {
		this.hiddangerNumber = hiddangerNumber;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public Date getFindTime() {
		return findTime;
	}
	public void setFindTime(Date findTime) {
		this.findTime = findTime;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getFindType() {
		return findType;
	}
	public void setFindType(String findType) {
		this.findType = findType;
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
	public String getTreatDepartment() {
		return treatDepartment;
	}
	public void setTreatDepartment(String treatDepartment) {
		this.treatDepartment = treatDepartment;
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
	public String getHiddangerState() {
		return hiddangerState;
	}
	public void setHiddangerState(String hiddangerState) {
		this.hiddangerState = hiddangerState;
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
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public String getTerminnalState() {
		return terminnalState;
	}
	public void setTerminnalState(String terminnalState) {
		this.terminnalState = terminnalState;
	}
	public String getFlowTaskName() {
		return flowTaskName;
	}
	public void setFlowTaskName(String flowTaskName) {
		this.flowTaskName = flowTaskName;
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
	public String getHideDangerState() {
		return hideDangerState;
	}
	public void setHideDangerState(String hideDangerState) {
		this.hideDangerState = hideDangerState;
	}
}
