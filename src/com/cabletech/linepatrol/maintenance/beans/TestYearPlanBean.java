package com.cabletech.linepatrol.maintenance.beans;

import com.cabletech.commons.base.BaseBean;
/**
 * ��ƻ�
 * @author Administrator
 *
 */
public class TestYearPlanBean extends BaseBean{
	private String id;
	private String planName;
	private String year;
	private String testTimes;//Ĭ�ϲ��Դ���
	private String contractorId;//�ƻ� ������ά
	private String creatorId;//�ƻ�������
	/*private String cableType[]; //���¼���
	private int beforTestNum[];//���ǰ���Դ���
	private int applyTestNum[];//������Դ���
	private int changeCableNum[];//����м̶�����
*/	
	private String cableLevel;//���¼���
	private int preTestNum;//���ǰ���Դ���
	private int applyNum;//������Դ���
	private String trunkIds;
	
	private String approver;//һ�������
	private String mobile;//����˵ĵ绰����
	private String reads;//���������
	private String rmobiles;//�����˺���
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getTestTimes() {
		return testTimes;
	}
	public void setTestTimes(String testTimes) {
		this.testTimes = testTimes;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	public String getCableLevel() {
		return cableLevel;
	}
	public void setCableLevel(String cableLevel) {
		this.cableLevel = cableLevel;
	}
	public int getPreTestNum() {
		return preTestNum;
	}
	public void setPreTestNum(int preTestNum) {
		this.preTestNum = preTestNum;
	}
	public int getApplyNum() {
		return applyNum;
	}
	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getReads() {
		return reads;
	}
	public void setReads(String reads) {
		this.reads = reads;
	}
	public String getRmobiles() {
		return rmobiles;
	}
	public void setRmobiles(String rmobiles) {
		this.rmobiles = rmobiles;
	}
	public String getTrunkIds() {
		return trunkIds;
	}
	public void setTrunkIds(String trunkIds) {
		this.trunkIds = trunkIds;
	}
	
}
