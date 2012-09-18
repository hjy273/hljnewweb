package com.cabletech.linepatrol.expenses.model;

import java.util.List;
import java.util.Set;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 分级维护费用
 */

public class ExpenseGradem extends BaseDomainObject {

	// Fields

	private String id;
	private String gradeFactorId;
	private String unitPriceId;
	private Double unitPrice;//单价为  单价*系数的值
	private Double maintenanceLength;
	private int maintenanceNum;
	//private String expenseId;
	//private List<ExpenseCable> cables;
	private ExpenseMonth expenseMonth;
	private Set<ExpenseCable> expenseCables;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGradeFactorId() {
		return gradeFactorId;
	}
	public void setGradeFactorId(String gradeFactorId) {
		this.gradeFactorId = gradeFactorId;
	}
	public String getUnitPriceId() {
		return unitPriceId;
	}
	public void setUnitPriceId(String unitPriceId) {
		this.unitPriceId = unitPriceId;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getMaintenanceLength() {
		return maintenanceLength;
	}
	public void setMaintenanceLength(Double maintenanceLength) {
		this.maintenanceLength = maintenanceLength;
	}
	public int getMaintenanceNum() {
		return maintenanceNum;
	}
	public void setMaintenanceNum(int maintenanceNum) {
		this.maintenanceNum = maintenanceNum;
	}
//	public String getExpenseId() {
//		return expenseId;
//	}
//	public void setExpenseId(String expenseId) {
//		this.expenseId = expenseId;
//	}
/*	public List<ExpenseCable> getCables() {
		return cables;
	}
	public void setCables(List<ExpenseCable> cables) {
		this.cables = cables;
	}*/
	public ExpenseMonth getExpenseMonth() {
		return expenseMonth;
	}
	public void setExpenseMonth(ExpenseMonth expenseMonth) {
		this.expenseMonth = expenseMonth;
	}
	public Set<ExpenseCable> getExpenseCables() {
		return expenseCables;
	}
	public void setExpenseCables(Set<ExpenseCable> expenseCables) {
		this.expenseCables = expenseCables;
	}
	
}