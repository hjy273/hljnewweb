package com.cabletech.linepatrol.drill.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;

public class DrillTask extends BaseDomainObject {
	public static final String CANCELED_STATE = "999";
	
	public static final String DRILLTYPE_MOBILE = "1";
	public static final String DRILLTYPE_CON = "2";
	/**
	 * 
	 */
	private static final long serialVersionUID = 8770667862981266995L;

	private String id;
	private String name;// ��������
	private int level;// ��������
	private Date beginTime;// ��ʼʱ��
	private Date endTime;// ����ʱ��
	private String locale;// �����ص�
	private String demand;// ����Ҫ��
	private String remark;// ��ע
	private String drillType;//�������ͣ�1���ƶ��ɷ� 2����ά��Ա�Զ���
	private String creator;// ������
	private Date createTime;// ����ʱ��
	private Date deadline;//�����ύʱ��
	private String saveFlag;//���������Ƿ�Ϊ��ʱ���棺1 ��ʱ���� 0 �ύ

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private Date cancelTime;
	private String cancelTimeDis;
	private String drillState;

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

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getDrillState() {
		return drillState;
	}

	public void setDrillState(String drillState) {
		this.drillState = drillState;
	}

}
