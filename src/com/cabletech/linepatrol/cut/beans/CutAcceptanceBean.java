package com.cabletech.linepatrol.cut.beans;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class CutAcceptanceBean extends BaseCommonBean {
	/**
	 * ������ս���
	 */
	private static final long serialVersionUID = -1872568362827401493L;
	private String id;
	private String cutId;// ���Id
	private float actualValue;// ʵ��ʹ�ý��
	private String isUpdate;// �Ƿ����ͼֽ
	private int unappoveNumber;// δͨ������
	
	//ҳ������
	private String operator;//��ά��Ա�Ĳ���������������ת��
	private String approveResult;//���������0 ��ͨ�� 1 ͨ�� 2 ת��
	private String approveRemark;//��������
	private String approveId;//������ԱId
	private String reader;//������
	private String approvers;//ת����ά��ԱId
	private String mobiles;//��ˡ�ת����ά��Ա�绰����
	private String creator;//������
	private String createTime;//����ʱ��
	
	private String proposer;//���������
	private String[] readerPhones;//�����˵绰����

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCutId() {
		return cutId;
	}

	public void setCutId(String cutId) {
		this.cutId = cutId;
	}

	public float getActualValue() {
		return actualValue;
	}

	public void setActualValue(float actualValue) {
		this.actualValue = actualValue;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public int getUnappoveNumber() {
		return unappoveNumber;
	}

	public void setUnappoveNumber(int unappoveNumber) {
		this.unappoveNumber = unappoveNumber;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getApprovers() {
		return approvers;
	}

	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
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
}
