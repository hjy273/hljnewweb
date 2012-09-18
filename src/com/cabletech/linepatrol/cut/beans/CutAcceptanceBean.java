package com.cabletech.linepatrol.cut.beans;

import java.util.Date;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class CutAcceptanceBean extends BaseCommonBean {
	/**
	 * 割接验收结算
	 */
	private static final long serialVersionUID = -1872568362827401493L;
	private String id;
	private String cutId;// 割接Id
	private float actualValue;// 实际使用金额
	private String isUpdate;// 是否更新图纸
	private int unappoveNumber;// 未通过次数
	
	//页面数据
	private String operator;//线维人员的操作，是审批还是转审
	private String approveResult;//审批结果，0 不通过 1 通过 2 转审
	private String approveRemark;//审批评论
	private String approveId;//审批人员Id
	private String reader;//抄送人
	private String approvers;//转审线维人员Id
	private String mobiles;//审核、转审线维人员电话号码
	private String creator;//创建人
	private String createTime;//创建时间
	
	private String proposer;//割接申请人
	private String[] readerPhones;//抄送人电话号码

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
