package com.cabletech.linepatrol.appraise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.dao.AppraiseTableDao;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
@Service
public class AppraiseDailyYearEndBO extends AppraiseDailyBO {
	@Autowired
	private AppraiseTableDao appraiseTableDao;
	@Override
	@Transactional
	public Map<String, Object> getAppraiseDailyData(String contractorId, String type, AppraiseDailyBean bean)
			throws ServiceException, Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<AppraiseTable> appraiseTables=new ArrayList<AppraiseTable>();
		if(type.equals("input")){
			appraiseTables=appraiseTableDao.queryAppraiseTableByYear(bean.getYear(), AppraiseConstant.APPRAISE_YEAREND);
			map.put("contractNo", bean.getContractNo());
		}else{
			appraiseTables = getAppraiseTable(bean);
			AppraiseDaily appraiseDaily=null;
			if(type.equals("view")){
				appraiseDaily=appraiseDailyDao.findByUnique("id", bean.getId());
			}
			if(StringUtils.isNotBlank(bean.getId())){
				contractorId = setConId(appraiseDaily,contractorId, bean);
			}
			inputContractNoToMap(map, appraiseDaily);
		}
		inputConNameToMap(bean.getContractorId(), map);
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
	@Transactional
	public void saveDailyYearEnd(AppraiseDailyBean appraiseDailyBean){
		saveAppraiseDailyByBean(appraiseDailyBean);
	}
}
