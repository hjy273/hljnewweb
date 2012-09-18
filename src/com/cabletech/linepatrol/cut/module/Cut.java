package com.cabletech.linepatrol.cut.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;

public class Cut extends BaseDomainObject {

	public static final String APPLY = "1";//�������
	public static final String APPLY_NO_PASS = "2";//����δͨ��
	public static final String FEEDBACK = "3";//����
	public static final String FEEDBACK_APPROVE = "4";//�������
	public static final String FEEDBACK_NO_PASS = "5";//�������δͨ��
	public static final String ACCEPTANCE = "6";//����
	public static final String ACCEPTANCE_APPROVE = "7";//�������
	public static final String ACCEPTANCE_NO_PASS = "8";//�������δͨ��
	public static final String EXAM = "9";
	public static final String FINISH = "0";
	public static final String CANCELED_STATE = "999";//ȡ��
	
	//��Ӽ���
	public static final String LEVEL_NORMAL = "1";
	public static final String LEVEL_EXIGENCE = "2";
	public static final String LEVEL_PREPARE = "3";

	public static final String SAVE = "0";
	public static final String TEMPSAVE = "1";
	/**
	 * ����������
	 */
	private static final long serialVersionUID = 2580693913445789381L;
	private String id;
	private String workOrderId;// ������
	private String cutName;// �������
	private String cutLevel;// ��Ӽ��� 1��һ���� 2��������� 3��Ԥ���
	private String builder;// ʩ����λ
	private String chargeMan;// ��������
	private Date beginTime;// ���뿪ʼʱ��
	private Date endTime;// �������ʱ��
	private float budget;// Ԥ����
	private String isCompensation;// �Ƿ��в���
	private String compCompany;// ������λ
	private float beforeLength;// ���ǰ����
	private String cutCause;// ���ԭ��
	/**
	 * 1����������� 2��������ͨ�� 3����������� 4�����뷴�������� 5������������ͨ�� 6����������ս��� 7�����ս��������
	 * 8�����ս���������ͨ�� 9���������� 0�����
	 */
	private String state;// ���״̬:
	private String cutPlace;// ��ӵص�
	private int unapproveNumber;// δͨ������
	private Date replyBeginTime;// ������ʼʱ��
	private Date replyEndTime;// ��������ʱ��
	private String linkNamePhone;// ��ϵ�˺͵绰
	private String proposer;// ������
	private Date applyDate;// ����ʱ��
	private String regionId;// ����Id
	private String otherImpressCable;// δ��ά�м̶�
	private String useMateral;// ʹ�ò�������
	private String liveChargeman;// �ֳ�������
	private String applyState;// ����״̬��0 ���� 1 ��ʱ����

	private Date deadline;// �����ύʱ��

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

	public Date getReplyBeginTime() {
		return replyBeginTime;
	}

	public void setReplyBeginTime(Date replyBeginTime) {
		this.replyBeginTime = replyBeginTime;
	}

	public Date getReplyEndTime() {
		return replyEndTime;
	}

	public void setReplyEndTime(Date replyEndTime) {
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

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getCancelUserId() {
		return cancelUserId;
	}

	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
		this.cancelTimeDis=DateUtil.DateToString(cancelTime, "yyyy/MM/dd HH:mm:ss");
	}

	public String getCancelUserName() {
		return cancelUserName;
	}

	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}

	public String getCancelTimeDis() {
		return cancelTimeDis;
	}

	public void setCancelTimeDis(String cancelTimeDis) {
		this.cancelTimeDis = cancelTimeDis;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
}
