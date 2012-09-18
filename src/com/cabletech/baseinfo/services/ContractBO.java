package com.cabletech.baseinfo.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.beans.ContractBean;
import com.cabletech.baseinfo.dao.ContractDAOImpl;
import com.cabletech.baseinfo.dao.ContractorDAOImpl;
import com.cabletech.baseinfo.domainobjects.Contract;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;

/**
 * ��ά��λ�б��ͬҵ����
 * 
 * @author liusq
 * 
 */
@Service
public class ContractBO extends EntityManager<Contract, String> {

	@Resource(name = "contractDAOImpl")
	private ContractDAOImpl contractDAOImpl;

	@Override
	protected HibernateDao<Contract, String> getEntityDao() {
		return contractDAOImpl;
	}

	/**
	 * ת�����ǩԼ��ͬ���棬���ش�ά��λ���ơ���ǰ��ݺ���һ�����
	 * 
	 * @param contractorId
	 *            ����ά��λ���
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> addContractForm(String contractorId)
			throws ServiceException, Exception {
		ContractorDAOImpl contractorDAOImpl = new ContractorDAOImpl();
		String contractorName = contractorDAOImpl
				.getContractorNameById(contractorId);
		String[] years = getYearAndNextYear();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractorName", contractorName);
		map.put("years", years);
		return map;
	}

	/**
	 * �����ά��λǩԼ��ͬ
	 * 
	 * @param bean
	 * @throws ServiceException
	 */
	@Transactional
	public void saveContract(ContractBean bean) throws ServiceException {
		Contract contract = contractDAOImpl.getContractInfo(bean);
		contractDAOImpl.save(contract);
	}

	/**
	 * �ж��Ƿ������ͬ��ǩԼ��ͬ��
	 * 
	 * @param bean
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(readOnly = true)
	public boolean hasSameContractNo(ContractBean bean) throws ServiceException {
		String contractNo = bean.getContractNo();
		String[] array = contractNo.split(",");
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < array.length; i++) {
			if (!set.add(array[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��֤�ô�ά����ȵ�ǩԼ��ͬ�Ƿ������
	 * 
	 * @param bean
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(readOnly = true)
	public boolean hasBeenAddContract(ContractBean bean)
			throws ServiceException {
		String contractorId = bean.getContractorId();
		String year = bean.getYear();
		Contract contract = contractDAOImpl.getContractByContractorIdAndYear(
				contractorId, year);
		if (contract == null) {
			return false;
		}
		return true;
	}

	/**
	 * �޸Ĵ�ά��λǩԼ��ͬǰ�������ݣ���ά��λ���ơ�ǩԼ��ͬ��ϸ��Ϣ��
	 * 
	 * @param contractorId
	 *            ��ά��λ���
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public Map<String, Object> editContractForm(String contractorId)
			throws ServiceException, Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String nextYear = getNextYear();
		Contract contract = contractDAOImpl.getContractByContractorIdAndYear(
				contractorId, nextYear);
		ContractorDAOImpl contractorDAOImpl = new ContractorDAOImpl();
		String contractorName = contractorDAOImpl
				.getContractorNameById(contractorId);
		map.put("contract", contract);
		map.put("contractorName", contractorName);
		return map;
	}

	/**
	 * �鿴��ά��λǩԼ��ͬ��ϸ��Ϣ
	 * 
	 * @param contractorId
	 *            ��ά��λ���
	 * @throws ServiceException
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> viewContract(String contractorId)
			throws ServiceException, Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Contract> contractList = contractDAOImpl.findByProperty(
				"contractorId", contractorId);
		ContractorDAOImpl contractorDAOImpl = new ContractorDAOImpl();
		String contractorName = contractorDAOImpl
				.getContractorNameById(contractorId);
		Contract thisYearContract = null;
		thisYearContract = getThisYearContractInfoByContractList(contractList);
		map.put("contractorName", contractorName);
		map.put("thisYearContract", thisYearContract);
		map.put("contractList", contractList);
		return map;
	}

	/**
	 * �Ӻ�ͬ�б��л�ý���ĺ�ͬ��Ϣ
	 * 
	 * @param contractList
	 * @return
	 */
	private Contract getThisYearContractInfoByContractList(
			List<Contract> contractList) {
		String thisYear = getThisYear();
		if (contractList != null && !contractList.isEmpty()) {
			for (Iterator<Contract> iterator = contractList.iterator(); iterator
					.hasNext();) {
				Contract contract = (Contract) iterator.next();
				if (StringUtils.equals(contract.getYear(), thisYear)) {
					return contract;
				}
			}
		}
		return null;
	}

	/**
	 * ͨ����ͬ��Ż�ú�ͬ������
	 * 
	 * @param contractId
	 *            ��ͬ���
	 * @return
	 */
	public String[] getContractNoArray(String contractId) {
		Contract contract = contractDAOImpl.get(contractId);
		return contract.getContractNoArrayByObject();
	}

	/**
	 * ͨ����ά��˾��ź���ݻ�ú�ͬ������
	 * 
	 * @param contractorId
	 *            ��ά��˾���
	 * @param year
	 *            ���
	 * @return
	 */
	public String[] getContractNoArray(String contractorId, String year) {
		Contract contract = contractDAOImpl.getContractByContractorIdAndYear(
				contractorId, year);
		if(contract != null) {
			return contract.getContractNoArrayByObject();
		}
		return null;
	}
	
	/**
	 * ��õ�ǰ��ݺ���һ�����
	 * 
	 * @return
	 */
	public String[] getYearAndNextYear() {
		String[] years = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String thisYear = sdf.format(new Date());
		String nextYear = Integer.toString((Integer.parseInt(thisYear) + 1));
		years[0] = thisYear;
		years[1] = nextYear;
		return years;
	}
	
	/**
	 * ��õ�ǰ���
	 * @return
	 */
	public String getThisYear() {
		return getYearAndNextYear()[0];
	}
	
	/**
	 * �����һ��
	 * @return
	 */
	public String getNextYear() {
		return getYearAndNextYear()[1];
	}
	@Transactional
	public Contract getContract(String contractorId,String year){
		Contract contract=contractDAOImpl.getContractByContractorIdAndYear(contractorId, year);
		return contract;
	}
}
