package com.cabletech.linepatrol.appraise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;

@Service
public class AppraiseSpecialBO extends AppraiseBO {
	@Resource(name = "appraiseTableBO")
	private AppraiseTableBO appraiseTableBO;

	/**
	 * 查询统计月考核表
	 * @param appraiseResultBean
	 * @param userInfo
	 * @return
	 */
	@Override
	@Transactional
	public List queryStat(AppraiseResultBean appraiseResultBean, UserInfo userInfo) {
		ContractorBO contractorBO = new ContractorBO();
		Map con = contractorBO.getContractorForMap(userInfo);
		Map<String, String> tableNames = appraiseTableBO.getAllTableName(AppraiseConstant.APPRAISE_SPECIAL);
		List<AppraiseResult> appraiseResults = appraiseResultDao.queryStat(appraiseResultBean);
		if (appraiseResults.size() <= 0) {
			return null;
		}
		
		String contractorId = appraiseResults.get(0).getContractorId();
		String tableId = appraiseResults.get(0).getTableId();
		Float sumResult = 0.0f;
		int i = 0;
		
		List<Map<String,Object>> specialAllStatList=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> specialStatList = new ArrayList<Map<String, Object>>();
		List<AppraiseResultBean> specialList = new ArrayList<AppraiseResultBean>();
		Map<String, Object> specialAllStatMap = new HashMap<String, Object>();
		Map<String, Object> specialStatMap = new HashMap<String, Object>();
		
		for (int index = 0; index < appraiseResults.size(); index++) {
			AppraiseResult appraiseResult = appraiseResults.get(index);
			String conId = appraiseResult.getContractorId();
			String tabId=appraiseResult.getTableId();
			
			if (!conId.equals(contractorId)||!tabId.equals(tableId)) {
				addIntoSpecialStatList(con, contractorId, sumResult, i, specialStatList, specialStatMap, specialList);
				specialStatMap = new HashMap<String, Object>();
				specialList = new ArrayList<AppraiseResultBean>();
				contractorId = conId;
				i = 0;
				sumResult = 0.0f;
			}
			if(!tabId.equals(tableId)){
				addSpecialAllStatList(tableNames, specialAllStatList, specialStatList, specialAllStatMap,tableId);
				tableId=tabId;
				specialAllStatMap = new HashMap<String, Object>();
				specialStatList = new ArrayList<Map<String, Object>>();
			}
			sumResult += appraiseResult.getResult();
			i++;
			AppraiseResultBean appResultBean = new AppraiseResultBean();
			BeanUtil.copyProperties(appraiseResult, appResultBean);
			appResultBean.setAppraiseDateFormat();
			specialList.add(appResultBean);
			if (index == appraiseResults.size() - 1) {
				addIntoSpecialStatList(con, contractorId, sumResult, i, specialStatList, specialStatMap, specialList);
				addSpecialAllStatList(tableNames, specialAllStatList, specialStatList, specialAllStatMap,tableId);
			}
		}
		return specialAllStatList;
	}

	private void addSpecialAllStatList(Map<String, String> tableNames, List<Map<String, Object>> specialAllStatList,
			List<Map<String, Object>> specialStatList, Map<String, Object> specialAllStatMap,String tableId) {
		specialAllStatMap.put("tableName", tableNames.get(tableId));
		specialAllStatMap.put("specialStatList", specialStatList);
		specialAllStatList.add(specialAllStatMap);
	}

	private void addIntoSpecialStatList(Map con, String contractorId, Float sumResult, int i,
			List<Map<String, Object>> specialStatList, Map<String, Object> specialStatMap,
			List<AppraiseResultBean> specialList) {
		specialStatMap.put("contractorName", con.get(contractorId));
		specialStatMap.put("sumResult", sumResult);
		specialStatMap.put("avgResult", sumResult / i);
		specialStatMap.put("specialList", specialList);
		specialStatMap.put("size", i+1);
		specialStatList.add(specialStatMap);
	}
}
