package com.cabletech.linepatrol.material.domain;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;

public class MaterialApply extends BaseDomainObject {
	public static final String CANCELED_STATE = "999";
	
	public static final String LP_MT_NEW = "LP_MT_NEW";// ��˱�

	public static final String LP_MT_USED = "LP_MT_USED";

	public static final String MATERIAL_MODULE = "material";// ����ģ��
	
	public static final String LP_APPLY_SAVE = "01";  //��������
	
	public static final String LP_APPLY_SUBMIT = "02"; //�ύ����
	
	public static final String LP_APPROVE_PASS = "03"; //���ͨ��
	
	public static final String LP_APPROVE_NOT_PASS = "04"; //���δͨ��
	
	public static final String LP_APPROVE_TRANS = "05"; //���ת��
	
	public static final String LP_PUT_STORAGE_SAVE = "06"; //�������
	
	public static final String LP_PUT_STORAGE_SUBMIT = "07"; //�ύ���
	
	public static final String LP_PUT_STORAGE_PASS = "08"; //�������ͨ��
	
	public static final String LP_PUT_STORAGE_NOT_PASS = "09"; //�������δͨ��
	
	public static final String LP_PUT_STORAGE_TRANS = "10"; //�������ת��

	private String id;// �������id

	private String creator;// ������

	private String contractorId;// ������

	private Date createDate;// ����ʱ��

	private String remark;// ��;

	private String type;// ���뵥����

	private String title;// ���뵥����

	private String state;// �������� ������������

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private Date cancelTime;
	private String cancelTimeDis;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

}
