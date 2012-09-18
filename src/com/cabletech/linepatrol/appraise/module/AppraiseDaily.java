package com.cabletech.linepatrol.appraise.module;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 代维公司日常考核实体类
 * 
 * @author liusq
 * 
 */
public class AppraiseDaily extends BaseDomainObject {

	private static final long serialVersionUID = -8500765113977647981L;
	public static final String BUSINESSMODULE_OTHER = "Daily_other";
	private String id; // 系统编号
	private String tableId; // 月考核表ID
	private String contractorId; // 代维单位
	private String contractNo; // 标包号
	private String businessModule; // 考核业务模块
	private String businessId; // 考核业务ID
	private Date appraiseDate; // 考核日期
	private String appraiser; // 考核人
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
