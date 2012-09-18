package com.cabletech.linepatrol.expenses.model;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpExpenseBudget entity. @author MyEclipse Persistence Tools
 */

public class ExpenseBudget extends BaseDomainObject {

	// Fields

	private String id;
	private String contractorId;
	private String expenseType;
	private String year;
	private Double budgetMoney;
	private Double payMoney;
	
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
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Double getBudgetMoney() {
		return budgetMoney;
	}
	public void setBudgetMoney(Double budgetMoney) {
		this.budgetMoney = budgetMoney;
	}
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

}