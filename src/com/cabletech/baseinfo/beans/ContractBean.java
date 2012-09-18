package com.cabletech.baseinfo.beans;

import org.apache.commons.lang.StringUtils;

import com.cabletech.commons.base.BaseBean;

/**
 * 代维单位中标合同Bean
 * 
 * @author liusq
 * 
 */
public class ContractBean extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String id; // 编号
	private String contractorId; // 代维公司
	private String year; // 年份
	private String contractNo; // 合同号

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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		contractNo = contractNo.replaceAll("，", ",");
		this.contractNo = contractNo;
	}
	
	/**
	 * 将字符串转换为字符数组
	 * @param contractNo
	 * @return
	 */
	public String[] getContractNoArray(String contractNo) {
		String[] contractNoArray = null;
		if(StringUtils.isNotBlank(contractNo)) {
			contractNoArray = contractNo.split(",");
		}
		return contractNoArray;
	}
}
