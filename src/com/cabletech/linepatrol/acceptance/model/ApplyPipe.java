package com.cabletech.linepatrol.acceptance.model;

import java.util.Date;

public class ApplyPipe {
	private String id;
	private Apply apply;
	private Date planAcceptanceTime;
	private String issueNumber;//ͬ��
	private String projectName;//��������
	private String pipeAddress;//�ܵ���ַ
	private String pipeRoute;//��ϸ·��
	private String pipeLength0;//��������
	private String pipeLength1;//�׹�����
	private String pipeProperty;//��Ȩ����
	private String pipeType;//�ܵ����ԣ����ͣ�
	private String workingDrawing;//ʩ��ͼֽ
	private String moveScale0;//�ƶ�������
	private String moveScale1;//�ƶ��׹���
	private String builder;//���赥λ
	private String builderPhone;//���赥λ�绰
	private String pcpm;//��Ŀ����
	private String maintenance;//ά����λ
	private String remark="";//��ע
	private String remark2="";//��ע2
	private String remark3="";//��ע3
	private String isrecord;
	private String ispassed;
	private String contractorId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssueNumber() {
		return projectName;
	}
	private void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public Apply getApply() {
		return apply;
	}
	public void setApply(Apply apply) {
		this.apply = apply;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.setIssueNumber("");
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
	public String getIsrecord() {
		return isrecord;
	}
	public void setIsrecord(String isrecord) {
		this.isrecord = isrecord;
	}
	public String getIspassed() {
		return ispassed;
	}
	public void setIspassed(String ispassed) {
		this.ispassed = ispassed;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getPipeType() {
		return pipeType;
	}
	public void setPipeType(String pipeType) {
		this.pipeType = pipeType;
	}
	public Date getPlanAcceptanceTime() {
		return planAcceptanceTime;
	}
	public void setPlanAcceptanceTime(Date planAcceptanceTime) {
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
	
}
