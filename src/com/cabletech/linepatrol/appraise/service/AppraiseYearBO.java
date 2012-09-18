package com.cabletech.linepatrol.appraise.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.dao.AppraiseConfirmResultDao;
import com.cabletech.linepatrol.appraise.dao.AppraiseResultDao;
import com.cabletech.linepatrol.appraise.module.AppraiseConfirmResult;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
@Service
public class AppraiseYearBO extends EntityManager<AppraiseResult,String> {
	@Autowired
	private AppraiseResultDao appraiseResultDao;
	@Resource(name="appraiseConfirmResultDao")
	AppraiseConfirmResultDao appraiseConfirmResultDao;
	@Override
	protected HibernateDao<AppraiseResult, String> getEntityDao() {
		// TODO Auto-generated method stub
		return appraiseResultDao;
	}
	/**
	 * 查询年终考核所需要的信息
	 * @param appraiseResultBean
	 * @return
	 */
	@Transactional
	public Map<String,Object> getYearResultByBean(AppraiseResultBean appraiseResultBean){
		List<Map<String,Object>> resultList=appraiseResultDao.getYearResultByBean(appraiseResultBean);
		Map<String,Object> results=new HashMap<String, Object>();
		for(Map<String,Object> result:resultList){
			results.put(result.get("type").toString(), result.get("avg"));
		}
		return results;
	}
	/**
	 * 通过resultId获得考核确认列表
	 * @param resultId
	 * @return
	 */
	@Transactional
	public List<AppraiseConfirmResult> queryConfirmResultByResultId(String resultId) {
		List<AppraiseConfirmResult> appraiseConfirmResults=appraiseConfirmResultDao.getConfirmResultByResultId(resultId);
		return appraiseConfirmResults;
	}

}
