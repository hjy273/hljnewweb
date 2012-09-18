package com.cabletech.linepatrol.safeguard.module;

import com.cabletech.commons.base.BaseDomainObject;

public class SafeguardCon extends BaseDomainObject {

	public static final String ADDPLAN = "1";
	public static final String APPROVEPLAN = "2";
	public static final String PLANUNAPPROVE = "3";
	public static final String ADDSUMMARY = "4";
	public static final String APPROVESUMMARY = "5";
	public static final String SUMMARYUNAPPROVE = "6";
	public static final String EVALUATE = "7";
	public static final String SAFEGUARDRFINISH = "8";
	/**
	 * 
	 */
	private static final long serialVersionUID = 3797721011779031172L;
	
	private String id;
	private String safeguardId;//���������ɵ�Id
	private String contractorId;//��άid
	private String jbpmFlowId;//����Id
	/**
	 * 1���ƶ����Ϸ��� 2�����Ϸ������ 3�����Ϸ�����˲�ͨ�� 4���ƶ������ܽ� 5�������ܽ���� 6�������ܽ���˲�ͨ�� 7�����
	 */
	private String transactState;// ״̬

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSafeguardId() {
		return safeguardId;
	}

	public void setSafeguardId(String safeguardId) {
		this.safeguardId = safeguardId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getJbpmFlowId() {
		return jbpmFlowId;
	}

	public void setJbpmFlowId(String jbpmFlowId) {
		this.jbpmFlowId = jbpmFlowId;
	}

	public String getTransactState() {
		return transactState;
	}

	public void setTransactState(String transactState) {
		this.transactState = transactState;
	}
}
