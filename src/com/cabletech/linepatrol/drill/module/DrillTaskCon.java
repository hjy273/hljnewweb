package com.cabletech.linepatrol.drill.module;

import com.cabletech.commons.base.BaseDomainObject;

public class DrillTaskCon extends BaseDomainObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3875373556982405154L;
	
	public static final String ADDPLAN = "1";
	public static final String APPROVEPLAN = "2";
	public static final String PLANUNAPPROVE = "3";
	public static final String ADDSUMMARY = "4";
	public static final String APPROVESUMMARY = "5";
	public static final String SUMMARYUNAPPROVE = "6";
	public static final String DRILLREMARK = "7";
	public static final String DRILLRFINISH = "8";

	private String id;
	private String drillId;// ��������Id
	private String contractorId;// ��άId
	/**
	 * 1���ƶ��������� 2������������� 3������������˲�ͨ�� 4���ƶ������ܽ� 5�������ܽ���� 6�������ܽ���˲�ͨ�� 7���������� 8�����
	 */
	private String state;// ״̬
	private String jpbmFlowId;// ����ʵ��id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDrillId() {
		return drillId;
	}

	public void setDrillId(String drillId) {
		this.drillId = drillId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getJpbmFlowId() {
		return jpbmFlowId;
	}

	public void setJpbmFlowId(String jpbmFlowId) {
		this.jpbmFlowId = jpbmFlowId;
	}
}
