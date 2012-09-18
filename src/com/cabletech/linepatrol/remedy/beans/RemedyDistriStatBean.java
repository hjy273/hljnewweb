package com.cabletech.linepatrol.remedy.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

/**
 * 分布统计总表
 * 
 */
public class RemedyDistriStatBean extends BaseBean {
	private String contractorid ;
	private String contractorname;
	private String town;//区县名称
	private String totalfee;//修缮费用
	
	
	
	public String getContractorid() {
		return contractorid;
	}
	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}
	public String getContractorname() {
		return contractorname;
	}
	public void setContractorname(String contractorname) {
		this.contractorname = contractorname;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(String totalfee) {
		this.totalfee = totalfee;
	}


	
	
	

}
