package com.cabletech.linepatrol.acceptance.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

public class PipesBean extends BaseBean {
	private String id;
	private String issueNumber;
	private String projectName;
	private String pipeAddress;
	private String pipeRoute;
	private String pipeLength0;
	private String pipeLength1;
	private String pipeProperty;
	private String pipeType;
	private String routeRes;
	private String workingDrawing;
	private String moveScale0;
	private String moveScale1;
	private String builder;
	private String builderPhone;
	private String pcpm;
	private String maintenance;
	private String remark;
	private String planAcceptanceTime;
	private String remark2;//备注2
	private String remark3;//备注3
	
	private String ispassed="";//是否通过验收
	private String contractorid;//代维单位
	private String begintime;//开始时间
	private String endtime;//结束时间
	private String isRecord="";//是否核准
	
	public String getIsRecord() {
		return isRecord;
	}
	public void setIsRecord(String isRecord) {
		this.isRecord = isRecord;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssueNumber() {
		return projectName;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.issueNumber = projectName;
		this.projectName = projectName;
	}
	public String getPipeAddress() {
		return pipeAddress;
	}
	public void setPipeAddress(String pipeAddress) {
		this.pipeAddress = pipeAddress;
	}
	public String getPipeRoute() {
		return pipeRoute;
	}
	public void setPipeRoute(String pipeRoute) {
		this.pipeRoute = pipeRoute;
	}
	public String getPipeLength0() {
		return pipeLength0;
	}
	public void setPipeLength0(String pipeLength0) {
		this.pipeLength0 = pipeLength0;
	}
	public String getPipeLength1() {
		return pipeLength1;
	}
	public void setPipeLength1(String pipeLength1) {
		this.pipeLength1 = pipeLength1;
	}
	public String getPipeProperty() {
		return pipeProperty;
	}
	public void setPipeProperty(String pipeProperty) {
		this.pipeProperty = pipeProperty;
	}
	public String getWorkingDrawing() {
		return workingDrawing;
	}
	public void setWorkingDrawing(String workingDrawing) {
		this.workingDrawing = workingDrawing;
	}
	public String getMoveScale0() {
		return moveScale0;
	}
	public void setMoveScale0(String moveScale0) {
		this.moveScale0 = moveScale0;
	}
	public String getMoveScale1() {
		return moveScale1;
	}
	public void setMoveScale1(String moveScale1) {
		this.moveScale1 = moveScale1;
	}
	public String getBuilder() {
		return builder;
	}
	public void setBuilder(String builder) {
		this.builder = builder;
	}
	public String getBuilderPhone() {
		return builderPhone;
	}
	public void setBuilderPhone(String builderPhone) {
		this.builderPhone = builderPhone;
	}
	public String getPcpm() {
		return pcpm;
	}
	public void setPcpm(String pcpm) {
		this.pcpm = pcpm;
	}
	public String getMaintenance() {
		return maintenance;
	}
	public void setMaintenance(String maintenance) {
		this.maintenance = maintenance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPipeType() {
		return pipeType;
	}
	public void setPipeType(String pipeType) {
		this.pipeType = pipeType;
	}
	public String getRouteRes() {
		return routeRes;
	}
	public void setRouteRes(String routeRes) {
		this.routeRes = routeRes;
	}
	public String getPlanAcceptanceTime() {
		return planAcceptanceTime;
	}
	public void setPlanAcceptanceTime(String planAcceptanceTime) {
		this.planAcceptanceTime = planAcceptanceTime;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getIspassed() {
		return ispassed;
	}
	public void setIspassed(String ispassed) {
		this.ispassed = ispassed;
	}
	public String getContractorid() {
		return contractorid;
	}
	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
