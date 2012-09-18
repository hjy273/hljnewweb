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
 * 代维单位中标合同业务类
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
	 * 转向添加签约合同界面，加载代维单位名称、当前年份和下一年年份
	 * 
	 * @param contractorId
	 *            ：代维单位编号
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
	 * 保存代维单位签约合同
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
	 * 判断是否存在相同的签约合同项
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
	 * 验证该代维该年度的签约合同是否已添加
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
	 * 修改代维单位签约合同前加载数据（代维单位名称、签约合同详细信息）
	 * 
	 * @param contractorId
	 *            代维单位编号
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
	 * 查看代维单位签约合同详细信息
	 * 
	 * @param contractorId
	 *            代维单位编号
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
	 * 从合同列表中获得今年的合同信息
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
	 * 通过合同编号获得合同号数组
	 * 
	 * @param contractId
	 *            合同编号
	 * @return
	 */
	public String[] getContractNoArray(String contractId) {
		Contract contract = contractDAOImpl.get(contractId);
		return contract.getContractNoArrayByObject();
	}

	/**
	 * 通过代维公司编号和年份获得合同号数组
	 * 
	 * @param contractorId
	 *            代维公司编号
	 * @param year
	 *            年份
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
	 * 获得当前年份和下一年年份
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
	 * 获得当前年份
	 * @return
	 */
	public String getThisYear() {
		return getYearAndNextYear()[0];
	}
	
	/**
	 * 获得下一年
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
