package com.cabletech.linepatrol.appraise.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 考核评分详细
 * 
 * @author liusq
 * 
 */
public class AppraiseMonthStatContent extends BaseDomainObject {

	private static final long serialVersionUID = -6630109687223445309L;
	private String resultId;
	private String appraiseDate; // 考核日期
	private String contractNo; // 标包号
	private float score; // 分数
	
	public AppraiseMonthStatContent(String resultId,String appraiseDate, String contractNo,
			float score) {
		super();
		this.resultId=resultId;
		this.appraiseDate = appraiseDate;
		this.contractNo = contractNo;
		this.score = score;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getAppraiseDate() {
		return appraiseDate;
	}

	public void setAppraiseDate(String appraiseDate) {
		this.appraiseDate = appraiseDate;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
}
