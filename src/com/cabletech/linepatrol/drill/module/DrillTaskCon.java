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
	private String drillId;// 演练任务Id
	private String contractorId;// 代维Id
	/**
	 * 1、制定演练方案 2、演练方案审核 3、演练方案审核不通过 4、制定演练总结 5、演练总结审核 6、演练总结审核不通过 7、演练评分 8、完成
	 */
	private String state;// 状态
	private String jpbmFlowId;// 流程实例id

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
