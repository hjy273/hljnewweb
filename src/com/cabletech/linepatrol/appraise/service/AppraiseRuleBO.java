package com.cabletech.linepatrol.appraise.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.dao.AppraiseRuleDao;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
@Service
public class AppraiseRuleBO extends EntityManager<AppraiseRule, String> {
	@Resource(name="appraiseRuleDao")
	private AppraiseRuleDao appraiseRuleDao;
	@Override
	protected HibernateDao<AppraiseRule, String> getEntityDao() {
		// TODO Auto-generated method stub
		return appraiseRuleDao;
	}
	@Transactional(readOnly=true)
	public AppraiseRule getRulebyId(String ruleId){
		AppraiseRule appraiseRule=appraiseRuleDao.getAppraiseRuleById(ruleId);
		appraiseRuleDao.initObject(appraiseRule);
		return appraiseRule;
	}

}
