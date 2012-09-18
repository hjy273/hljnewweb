package com.cabletech.linepatrol.expenses.model;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpExpenseCable entity. @author MyEclipse Persistence Tools
 */

public class ExpenseCable extends BaseDomainObject  {

	// Fields

	private String id;
	private String cableId;
	//private String grademId;
	private ExpenseGradem expenseGradem;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCableId() {
		return cableId;
	}
	public void setCableId(String cableId) {
		this.cableId = cableId;
	}
//	public String getGrademId() {
//		return grademId;
//	}
//	public void setGrademId(String grademId) {
//		this.grademId = grademId;
//	}
	public ExpenseGradem getExpenseGradem() {
		return expenseGradem;
	}
	public void setExpenseGradem(ExpenseGradem expenseGradem) {
		this.expenseGradem = expenseGradem;
	}

}