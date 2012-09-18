package com.cabletech.linepatrol.appraise.module;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * ��ά��˾�ճ�����ʵ����
 * 
 * @author liusq
 * 
 */
public class AppraiseDaily extends BaseDomainObject {

	private static final long serialVersionUID = -8500765113977647981L;
	public static final String BUSINESSMODULE_OTHER = "Daily_other";
	private String id; // ϵͳ���
	private String tableId; // �¿��˱�ID
	private String contractorId; // ��ά��λ
	private String contractNo; // �����
	private String businessModule; // ����ҵ��ģ��
	private String businessId; // ����ҵ��ID
	private Date appraiseDate; // ��������
	private String appraiser; // ������
	private Set<AppraiseDailyMark> appraiseDailyMarkSet = new HashSet<AppraiseDailyMark>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getContractNo() {
		if(contractNo != null){
			return contractNo;
		}else{
			return "";
		}
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getBusinessModule() {
		return businessModule;
	}

	public void setBusinessModule(String businessModule) {
		this.businessModule = businessModule;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Date getAppraiseDate() {
		return appraiseDate;
	}

	public void setAppraiseDate(Date appraiseDate) {
		this.appraiseDate = appraiseDate;
	}

	public String getAppraiser() {
		return appraiser;
	}

	public void setAppraiser(String appraiser) {
		this.appraiser = appraiser;
	}

	public Set<AppraiseDailyMark> getAppraiseDailyMarkSet() {
		return appraiseDailyMarkSet;
	}

	public void setAppraiseDailyMarkSet(Set<AppraiseDailyMark> appraiseDailyMarkSet) {
		this.appraiseDailyMarkSet = appraiseDailyMarkSet;
	}
	
	public void addAppraiseDailyMark(AppraiseDailyMark appraiseDailyMark){
		appraiseDailyMarkSet.add(appraiseDailyMark);
	}
	
	public void removeAppraiseDailyMark(AppraiseDailyMark appraiseDailyMark){
		for (AppraiseDailyMark appraiseDailyMarkOrg : appraiseDailyMarkSet) {
			if(StringUtils.equals(appraiseDailyMarkOrg.getId(),appraiseDailyMark.getId())){
				appraiseDailyMarkSet.remove(appraiseDailyMark);
			}
		}
	}

}
