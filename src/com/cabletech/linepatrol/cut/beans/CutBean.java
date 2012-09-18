package com.cabletech.linepatrol.cut.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class CutBean extends BaseCommonBean {
	/**
	 * �������
	 */
	
	public final static String WATI_REPLY="1";
	public final static String REPLY_WAIT_APPROVE="4";
	public final static String REPLY_WATI_EXAM="7";
	
	private static final long serialVersionUID = -8077567416655860790L;
	
	private String id;
	private String workOrderId;// ������
	private String cutName;// �������
	private String cutLevel;//��Ӽ���
	private String builder;// ʩ����λ
	private String chargeMan;// ��������
	private String beginTime;// ���뿪ʼʱ��
	private String endTime;// �������ʱ��
	private float budget;// Ԥ����
	private String isCompensation;// �Ƿ��в���
	private String compCompany;// ������λ
	private float beforeLength;// ���ǰ����
	private String cutCause;// ���ԭ��
	private String state;// ���״̬:1����������� 2��������ͨ�� 3����������� 4�����뷴�������� 5������������ͨ�� 6����������ս��� 7�����ս�������� 8�����ս���������ͨ�� 9���������� 0�����
	private String cutPlace;// ��ӵص�
	private int unapproveNumber;// δͨ������
	private String replyBeginTime;// ������ʼʱ��
	private String replyEndTime;// ��������ʱ��
	private String linkNamePhone;//��ϵ�˺͵绰
	private String proposer;//������
	private String applyDate;//����ʱ��
	private String regionId;//����Id
	private String otherImpressCable;//δ��ά�м̶�
	private String useMateral;//ʹ�ò�������
	private String liveChargeman;//�ֳ�������
	private String applyState;//����״̬��0 ����  1 ��ʱ����
	private String deadline;//�����ύʱ��
	
	//ҳ������
	private String operator;//��ά��Ա�Ĳ���������������ת��
	private String approveResult;//���������0 ��ͨ�� 1 ͨ�� 2 ת��
	private String approveRemark;//��������
	private String approveId;//������ԱId
	private String reader;//������
	private String approvers;//ת����ά��ԱId
	private String mobile;//��ˡ�ת����ά��Ա�绰����
	private String[] readerPhones;//�����˵绰����

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private String cancelTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getCutName() {
		return cutName;
	}

	public void setCutName(String cutName) {
		this.cutName = cutName;
	}
	
	public String getCutLevel() {
		return cutLevel;
	}

	public void setCutLevel(String cutLevel) {
		this.cutLevel = cutLevel;
	}

	public String getBuilder() {
		return builder;
	}

	public void setBuilder(String builder) {
		this.builder = builder;
	}

	public String getChargeMan() {
		return chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
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

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}

	public String getIsCompensation() {
		return isCompensation;
	}

	public void setIsCompensation(String isCompensation) {
		this.isCompensation = isCompensation;
	}

	public String getCompCompany() {
		return compCompany;
	}

	public void setCompCompany(String compCompany) {
		this.compCompany = compCompany;
	}

	public float getBeforeLength() {
		return beforeLength;
	}

	public void setBeforeLength(float beforeLength) {
		this.beforeLength = beforeLength;
	}

	public String getCutCause() {
		return cutCause;
	}

	public void setCutCause(String cutCause) {
		this.cutCause = cutCause;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCutPlace() {
		return cutPlace;
	}

	public void setCutPlace(String cutPlace) {
		this.cutPlace = cutPlace;
	}

	public int getUnapproveNumber() {
		return unapproveNumber;
	}

	public void setUnapproveNumber(int unapproveNumber) {
		this.unapproveNumber = unapproveNumber;
	}

	public String getReplyBeginTime() {
		return replyBeginTime;
	}

	public void setReplyBeginTime(String replyBeginTime) {
		this.replyBeginTime = replyBeginTime;
	}

	public String getReplyEndTime() {
		return replyEndTime;
	}

	public void setReplyEndTime(String replyEndTime) {
		this.replyEndTime = replyEndTime;
	}

	public String getLinkNamePhone() {
		return linkNamePhone;
	}

	public void setLinkNamePhone(String linkNamePhone) {
		this.linkNamePhone = linkNamePhone;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getOtherImpressCable() {
		return otherImpressCable;
	}

	public void setOtherImpressCable(String otherImpressCable) {
		this.otherImpressCable = otherImpressCable;
	}

	public String getUseMateral() {
		return useMateral;
	}

	public void setUseMateral(String useMateral) {
		this.useMateral = useMateral;
	}

	public String getLiveChargeman() {
		return liveChargeman;
	}

	public void setLiveChargeman(String liveChargeman) {
		this.liveChargeman = liveChargeman;
	}

	public String getApplyState() {
		return applyState;
	}

	public void setApplyState(String applyState) {
		this.applyState = applyState;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
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

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}
