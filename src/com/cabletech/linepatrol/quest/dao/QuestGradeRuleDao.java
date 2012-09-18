package com.cabletech.linepatrol.quest.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestGradeRule;

@Repository
public class QuestGradeRuleDao extends HibernateDao<QuestGradeRule, String> {
	/**
	 * 保存评分细则
	 * @param itemId：参评项ID
	 * @param ruleAddName：评分细则名称
	 */
	public void saveQuestGradeRule(String itemId, String[] ruleAddName){
		if(ruleAddName != null && ruleAddName.length > 0){
			for (int i = 0; i < ruleAddName.length; i++) {
				QuestGradeRule rule = new QuestGradeRule();
				rule.setItemId(itemId);
				rule.setGradeExplain(ruleAddName[i]);
				rule.setMark(0);
				this.save(rule);
			}
		}
	}
}
