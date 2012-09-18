package com.cabletech.baseinfo.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.beans.ContractBean;
import com.cabletech.baseinfo.domainobjects.Contract;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.ctf.dao.HibernateDao;

/**
 * ��ά��λ�б��ͬ������
 * 
 * @author liusq
 * 
 */
@Repository
public class ContractDAOImpl extends HibernateDao<Contract, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * ���Contractʵ����
	 * 
	 * @param bean
	 *            ContractBean��
	 * @return
	 */
	public Contract getContractInfo(ContractBean bean) {
		Contract contract = new Contract();
		BeanUtil.copyProperties(bean, contract);
		return contract;
	}

	/**
	 * ͨ����ά��λ��ź���ݲ�ѯ��άǩԼ��ͬ
	 * 
	 * @param contractorId
	 *            ��λ��λ���
	 * @param year
	 *            ���
	 * @return
	 */
	public Contract getContractByContractorIdAndYear(String contractorId,
			String year) {
		Contract contract = null;
		String hql = "from Contract con where con.contractorId=? and con.year=?";
		List<Contract> contracts = find(hql, contractorId, year);
		if (contracts != null && !contracts.isEmpty()) {
			contract = contracts.get(0);
		}
		return contract;
	}
}
