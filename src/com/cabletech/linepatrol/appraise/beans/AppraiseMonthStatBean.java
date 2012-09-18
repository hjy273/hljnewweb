package com.cabletech.linepatrol.appraise.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

/**
 * ÔÂ¿¼ºËÍ³¼ÆBean
 * 
 * @author liusq
 * 
 */
public class AppraiseMonthStatBean extends BaseCommonBean {

	private static final long serialVersionUID = -3498016592787906797L;

	private String startDate;
	private String endDate;
	private String score;
	private String scoreType;
	private String contractorId;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
}
