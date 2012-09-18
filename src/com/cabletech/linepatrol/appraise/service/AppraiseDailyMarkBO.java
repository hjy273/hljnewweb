package com.cabletech.linepatrol.appraise.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.dao.AppraiseDailyMarkDao;
import com.cabletech.linepatrol.appraise.module.AppraiseDailyMark;
@Service
public class AppraiseDailyMarkBO extends EntityManager<AppraiseDailyMark, String>{
	@Autowired
	private AppraiseDailyMarkDao appraiseDailyMarkDao;
	@Override
	protected HibernateDao<AppraiseDailyMark, String> getEntityDao() {
		// TODO Auto-generated method stub
		return appraiseDailyMarkDao;
	}
	@Transactional
	public Map<String,String> getAppraiseDailyMarkDeductionByBean(String tableId, AppraiseResultBean appraiseResultBean, String typeName){
		Map<String,String> dailyMarkDeductions=new HashMap<String, String>();
		List<Map> dailyMarkDeduction=appraiseDailyMarkDao.queryAppraiseDailyMarkDeductionByBean(tableId, appraiseResultBean, typeName);
		for(Map markDeduction:dailyMarkDeduction){
			dailyMarkDeductions.put(markDeduction.get("ruleId").toString(), markDeduction.get("avgMarkDeduction").toString());
		}
		return dailyMarkDeductions;
	}
	/**
	 * 通过dailyId查询考核结果ruleId和考核分数的Map
	 * @param id
	 * @return
	 */
	@Transactional
	public Map<String,Object> getAllMarkDeductionsByDailyId(String dailyId) {
		Map<String,Object> map=new HashMap<String,Object>();
		List<AppraiseDailyMark> appraiseDailyMarks=appraiseDailyMarkDao.queryDailyMarkByDailyId(dailyId);
		for(AppraiseDailyMark appraiseDailyMark:appraiseDailyMarks){
			map.put(appraiseDailyMark.getRuleId(), appraiseDailyMark.getMarkDeductions());
		}
		return map;
	}
}
