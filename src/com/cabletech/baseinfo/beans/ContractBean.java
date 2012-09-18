package com.cabletech.baseinfo.beans;

import org.apache.commons.lang.StringUtils;

import com.cabletech.commons.base.BaseBean;

/**
 * ��ά��λ�б��ͬBean
 * 
 * @author liusq
 * 
 */
public class ContractBean extends BaseBean {
	private static final long serialVersionUID = 1L;

	private String id; // ���
	private String contractorId; // ��ά��˾
	private String year; // ���
	private String contractNo; // ��ͬ��

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
		contractNo = contractNo.replaceAll("��", ",");
		this.contractNo = contractNo;
	}
	
	/**
	 * ���ַ���ת��Ϊ�ַ�����
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
