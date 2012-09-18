package com.cabletech.linepatrol.appraise.module;

import java.util.ArrayList;
import java.util.List;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * ¿¼ºËÆÀ·Ö
 * 
 * @author liusq
 * 
 */
public class AppraiseMonthStat extends BaseDomainObject {

	private static final long serialVersionUID = -6630109687223445309L;

	private String contractorName; // ´úÎ¬µ¥Î»Ãû³Æ
	private List<AppraiseMonthStatDetail> resultList = new ArrayList<AppraiseMonthStatDetail>(); // ¿¼ºËÏêÏ¸

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public List<AppraiseMonthStatDetail> getResultList() {
		return resultList;
	}

	public void setResultList(List<AppraiseMonthStatDetail> resultList) {
		this.resultList = resultList;
	}

	public void addResultList(AppraiseMonthStatDetail detail) {
		resultList.add(detail);
	}
}
