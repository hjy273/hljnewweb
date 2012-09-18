package com.cabletech.linepatrol.expenses.model;

import java.util.Date;
import java.util.Set;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpExpenseMonth entity. @author MyEclipse Persistence Tools
 */

public class ExpenseMonth extends BaseDomainObject  {

	// Fields

	private String id;
	private String contractorId;
	private Date yearmonth;
	private int cableNum;
	private Double cableLength;
	private Double monthPrice;
	private String expenseType;//维护类型
	private double rectifyMoney;//矫正费用
	private double deductionMoney;//扣减费用
	private String remark;
	
	private Set<ExpenseGradem> gradems ;
	
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
	public Date getYearmonth() {
		return yearmonth;
	}
	public void setYearmonth(Date yearmonth) {
		this.yearmonth = yearmonth;
	}
	public int getCableNum() {
		return cableNum;
	}
	public void setCableNum(int cableNum) {
		this.cableNum = cableNum;
	}
	public Double getCableLength() {
		return cableLength;
	}
	public void setCableLength(Double cableLength) {
		this.cableLength = cableLength;
	}
	public Double getMonthPrice() {
		return monthPrice;
	}
	public void setMonthPrice(Double monthPrice) {
		this.monthPrice = monthPrice;
	}
	public Set<ExpenseGradem> getGradems() {
		return gradems;
	}
	public void setGradems(Set<ExpenseGradem> gradems) {
		this.gradems = gradems;
	}
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	public double getRectifyMoney() {
		return rectifyMoney;
	}
	public void setRectifyMoney(double rectifyMoney) {
		this.rectifyMoney = rectifyMoney;
	}
	public double getDeductionMoney() {
		return deductionMoney;
	}
	public void setDeductionMoney(double deductionMoney) {
		this.deductionMoney = deductionMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}