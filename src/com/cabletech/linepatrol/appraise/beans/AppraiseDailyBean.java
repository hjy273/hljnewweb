package com.cabletech.linepatrol.appraise.beans;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.module.AppraiseDailyMark;
import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

/**
 * 代维单位日常考核Bean
 * 
 * @author liusq
 * 
 */
public class AppraiseDailyBean extends BaseCommonBean {
	
	private static final long serialVersionUID = -72172470552266905L;
	
	private String id; // 系统编号
	private String tableId; // 月考核表ID
	private String contractorId; // 代维单位
	private String contractNo; // 标包号
	private String businessModule; // 考核业务模块
	private String businessId; // 考核业务ID
	private String appraiseDate; // 考核日期
	private String appraiser; // 考核人
	private String ruleIds;//|分隔的多个ruleId
	private String remarks;//|分隔的多个remark
	private String markDeductions;//|分隔的多个markDeduction

	private String year;
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
		return contractNo;
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

	public String getAppraiseDate() {
		return appraiseDate;
	}

	public void setAppraiseDate(String appraiseDate) {
		this.appraiseDate = appraiseDate;
	}

	public String getAppraiser() {
		return appraiser;
	}

	public void setAppraiser(String appraiser) {
		this.appraiser = appraiser;
	}

	public String getRuleIds() {
		return ruleIds;
	}

	public void setRuleIds(String ruleIds) {
		this.ruleIds = ruleIds;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	/**
     * 将AppraiseDailyBean转换为AppraiseDaily
     * 
     * @param appraiseDailyBean
     * @return
     */
    public AppraiseDaily getAppriseDailyFromAppraiseDailyBean() {
    	
		AppraiseDaily appraiseDaily = new AppraiseDaily();
		BeanUtil.copyProperties(this, appraiseDaily);
		appraiseDaily.setAppraiseDate(new Date());

		if (StringUtils.isNotBlank(ruleIds)) {
			String[] ruleIdArray = ruleIds.split("\\|");
			String[] remarkArray = remarks.split("\\|");
			String[] markDeductionArray=null;
			if(StringUtils.isNotBlank(markDeductions)){
			markDeductionArray = markDeductions.split("\\|");
			}
			for (int i = 0; i < ruleIdArray.length; i++) {
				AppraiseDailyMark appraiseDailyMark = new AppraiseDailyMark();
				appraiseDailyMark.setRuleId(ruleIdArray[i]);
				appraiseDailyMark.setRemark(remarkArray[i]);
				if(markDeductionArray!=null&&markDeductionArray.length>0){
					appraiseDailyMark.setMarkDeductions(markDeductionArray[i]);
				}
				appraiseDailyMark.setAppraiseDaily(appraiseDaily);
				appraiseDaily.getAppraiseDailyMarkSet().add(appraiseDailyMark);
			}
		}
		return appraiseDaily;
    }

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMarkDeductions() {
		return markDeductions;
	}

	public void setMarkDeductions(String markDeductions) {
		this.markDeductions = markDeductions;
	}
}
