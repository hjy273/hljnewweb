package com.cabletech.linepatrol.drill.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class DrillTaskBean extends BaseCommonBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8770667862981266995L;

	private String id;
	private String name;// ��������
	private Integer level;// ��������
	private String beginTime;// ��ʼʱ��
	private String endTime;// ����ʱ��
	private String locale;// �����ص�
	private String demand;// ����Ҫ��
	private String remark;// ��ע
	private String drillType;// ��������
	private String creator;// ������
	private String createTime;// ����ʱ��
	private String deadline;// �����ύʱ��
	private String saveFlag;// ���������Ƿ�Ϊ��ʱ���棺1 ��ʱ���� 0 �ύ

	private String state;

	// ��ҳ������
	private String contractorId;// ��ά��λId
	private String userId;// ��ά��λ��ԱId
	private String mobileId;// ��ά��λ����
	private String[] delfileid;// ɾ��������Ids
	private String conUser;// ��ά��λ���û�

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private String cancelTime;
	
	private String drillState;//�Ƿ�ȡ��

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDrillType() {
		return drillType;
	}

	public void setDrillType(String drillType) {
		this.drillType = drillType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

	public String[] getDelfileid() {
		return delfileid;
	}

	public void setDelfileid(String[] delfileid) {
		this.delfileid = delfileid;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getConUser() {
		return conUser;
	}

	public void setConUser(String conUser) {
		this.conUser = conUser;
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

	public String getDrillState() {
		return drillState;
	}

	public void setDrillState(String drillState) {
		this.drillState = drillState;
	}

}
