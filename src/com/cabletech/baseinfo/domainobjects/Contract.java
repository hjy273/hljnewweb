package com.cabletech.baseinfo.domainobjects;

import org.apache.commons.lang.StringUtils;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * ��ά��λ�б��ͬʵ����
 * 
 * @author liusq
 * 
 */
public class Contract extends BaseDomainObject {
	private static final long serialVersionUID = 3919693147744622532L;

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
	
	/**
	 * ��õ�ǰ����ĺ�ͬ������
	 * @param contract
	 * @return
	 */
	public String[] getContractNoArrayByObject() {
		String contractNo = this.getContractNo();
		String[] contractNoArray = null;
		if(StringUtils.isNotBlank(contractNo)) {
			contractNoArray = contractNo.split(",");
		}
		return contractNoArray;
	}
}
