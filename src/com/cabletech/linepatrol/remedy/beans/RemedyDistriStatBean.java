package com.cabletech.linepatrol.remedy.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

/**
 * �ֲ�ͳ���ܱ�
 * 
 */
public class RemedyDistriStatBean extends BaseBean {
	private String contractorid ;
	private String contractorname;
	private String town;//��������
	private String totalfee;//���ɷ���
	
	
	
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
