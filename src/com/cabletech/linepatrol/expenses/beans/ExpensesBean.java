package com.cabletech.linepatrol.expenses.beans;



import java.util.Date;

import com.cabletech.commons.base.BaseBean;

public class ExpensesBean extends BaseBean{
	private String id;
	private float lengthStart;
	private float lengthEnd;
	private float factor;
	private String contractorId;
	private Date createTime;
	
	private String cableType;
	private float price;
	
	//费用核实
	
	private String finshTime;//交维时间
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getLengthStart() {
		return lengthStart;
	}
	public void setLengthStart(float lengthStart) {
		this.lengthStart = lengthStart;
	}
	public float getLengthEnd() {
		return lengthEnd;
	}
	public void setLengthEnd(float lengthEnd) {
		this.lengthEnd = lengthEnd;
	}
	public float getFactor() {
		return factor;
	}
	public void setFactor(float factor) {
		this.factor = factor;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCableType() {
		return cableType;
	}
	public void setCableType(String cableType) {
		this.cableType = cableType;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getFinshTime() {
		return finshTime;
	}
	public void setFinshTime(String finshTime) {
		this.finshTime = finshTime;
	}
	
}
