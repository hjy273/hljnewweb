package com.cabletech.linepatrol.expenses.model;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpExpenseAffirm entity. @author MyEclipse Persistence Tools
 */

public class ExpenseAffirm extends BaseDomainObject {

	// Fields

	private String id;
	private String contractorId;
	private String budgetId;
	private Date startMonth;
	private Date endMonth;
	private Double deductionPrice;
	private Double balancePrice;
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	}
	public Date getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(Date startMonth) {
		this.startMonth = startMonth;
	}
	public Date getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(Date endMonth) {
		this.endMonth = endMonth;
	}
	public Double getDeductionPrice() {
		return deductionPrice;
	}
	public void setDeductionPrice(Double deductionPrice) {
		this.deductionPrice = deductionPrice;
	}
	public Double getBalancePrice() {
		return balancePrice;
	}
	public void setBalancePrice(Double balancePrice) {
		this.balancePrice = balancePrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}