package com.cabletech.linepatrol.expenses.model;

import java.util.Set;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * Î¬»¤µ¥¼Û
 */

public class ExpenseUnitPrice extends BaseDomainObject {

	// Fields

	private String id;
	private String explan;
	private String cableLevel;
	private Double unitPrice;
	private String contractorid;
	private String expenseType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExplan() {
		return explan;
	}
	public void setExplan(String explan) {
		this.explan = explan;
	}
	public String getCableLevel() {
		return cableLevel;
	}
	public void setCableLevel(String cableLevel) {
		this.cableLevel = cableLevel;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getContractorid() {
		return contractorid;
	}
	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

}