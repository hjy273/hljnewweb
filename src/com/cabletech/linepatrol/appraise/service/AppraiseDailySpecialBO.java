package com.cabletech.linepatrol.appraise.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
@Service
public class AppraiseDailySpecialBO extends AppraiseDailyBO {
	@Autowired
	private AppraiseTableBO appraiseTableBO;
	@Override
	public Map<String, Object> getAppraiseDailyData(String contractorId,
			String type, AppraiseDailyBean bean) throws ServiceException,
			Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppraiseTable> appraiseTables=new ArrayList<AppraiseTable>();
		if(type.equals(AppraiseConstant.FLAG_INPUT)){
			appraiseTables=appraiseTableBO.getTableByDate(new Date(),new Date(), AppraiseConstant.APPRAISE_SPECIAL);
		}else{
			appraiseTables=getAppraiseTable(bean);
			AppraiseDaily appraiseDaily = null;
			if(StringUtils.isNotBlank(bean.getId())){
				contractorId=setConId(appraiseDaily, contractorId, bean);
			}
		}
		inputConNameToMap(contractorId, map);
		map.put("appraiseTables", appraiseTables);
		return map;
	}
}
