package com.cabletech.linepatrol.appraise.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.services.ContractBO;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.dao.AppraiseDailyDao;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
@Service
public class AppraiseDailyDailyBO extends AppraiseDailyBO {
	@Resource(name = "appraiseDailyDao")
	private AppraiseDailyDao appraiseDailyDao;
	
	@Autowired
	private AppraiseTableBO appraiseTableBO;

	@Autowired
	private ContractBO contractBO;
	/**
	 * 获得日常考核信息表中的数据，包括月考核表、代维单位、签约合同
	 * 
	 * @param contractorId
	 *            代维单位编号
	 * @param type
	 *            类型：是查看还是输入
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> getAppraiseDailyData(String contractorId,
			String type, AppraiseDailyBean bean) throws ServiceException,
			Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String year = DateUtil.DateToString(new Date(), "yyyy");
		List<AppraiseTable> appraiseTables = null;
		// 判断是输入还是查看，根据不同的类型appraiseTable取值不同
		if (StringUtils.equals(type, "input")) {
			appraiseTables = appraiseTableBO.getTableByYear(
					year, AppraiseConstant.APPRAISE_MONTH);
		} else {
			appraiseTables = getAppraiseTable(bean);
			AppraiseDaily appraiseDaily = null;
			if(StringUtils.isNotBlank(bean.getId())){
				contractorId = setConId(appraiseDaily,contractorId, bean);
			}else{
				appraiseDaily = appraiseDailyDao.getAppraiseDailyByCondition(bean);
			}
			inputContractNoToMap(map, appraiseDaily);
		}
		inputConNoArrayToMap(contractorId, map, year);
		inputConNameToMap(contractorId, map);
		map.put("appraiseTables", appraiseTables);
		return map;
	}
	/**
	 * 将contractNo放入map中
	 * @param map
	 * @param appraiseDaily
	 */
	private void inputContractNoToMap(Map<String, Object> map, AppraiseDaily appraiseDaily) {
		String contractNo = null;
		if(appraiseDaily != null) {
			contractNo = appraiseDaily.getContractNo();
		}
		map.put("contractNo", contractNo);
	}
	/**
	 * 将contractNoArray放入到map中
	 * @param contractorId
	 * @param map
	 * @param year
	 */
	private void inputConNoArrayToMap(String contractorId, Map<String, Object> map, String year) {
		String[] contractNoArray = contractBO.getContractNoArray(contractorId,
				year);
		map.put("contractNoArray", contractNoArray);
	}
	@Transactional
	public AppraiseDaily saveBusinessEqId(AppraiseDaily appraiseDaily){
		appraiseDailyDao.save(appraiseDaily);
		appraiseDaily.setBusinessId(appraiseDaily.getId());
		appraiseDailyDao.save(appraiseDaily);
		return appraiseDaily;
	}
}
