package com.cabletech.linepatrol.appraise.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
@Repository
public class AppraiseRuleDao extends HibernateDao<AppraiseRule, String> {
	public AppraiseRule getAppraiseRuleById(String ruleId){
		String hql="from AppraiseRule rule where rule.id=?";
		List<AppraiseRule> appraiseRules=find(hql,ruleId);
		if(appraiseRules.size()>0){
			return appraiseRules.get(0);
		}
		return null;
	}
}
