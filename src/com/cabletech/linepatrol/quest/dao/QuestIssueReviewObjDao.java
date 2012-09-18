package com.cabletech.linepatrol.quest.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestIssueReviewObj;

@Repository
public class QuestIssueReviewObjDao extends HibernateDao<QuestIssueReviewObj, String> {
	/**
	 * ���ʾ�ID����ʾ��������ID
	 * @param issueId���ʾ�ID
	 * @return�����ʾ��������ID
	 */
	public String getCompIdsByIssueId(String issueId){
		String compIds = "";
		List list = findByProperty("questId", issueId);
		if(list != null && list.size() > 0){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				QuestIssueReviewObj questIssueReviewObj = (QuestIssueReviewObj) iterator.next();
				compIds += questIssueReviewObj.getReviewId() + ",";
			}
		}
		if(!"".equals(compIds)){
			compIds = compIds.substring(0, compIds.length() - 1);
		}
		return compIds;
	}
	
	/**
	 * ɾ�����ʾ��������
	 * @param issueId���ʾ�ID
	 */
	public void deleteReviewObjByIssueId(String issueId){
		String sql = "delete from quest_issue_reviewobj where quest_id='" + issueId + "'";
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * ���ʾ�ID��ò�����������
	 * @param issueId���ʾ�ID
	 * @return��������������
	 */
	public String getCompNamesByIssueId(String issueId){
		String compNames = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select r.object object_name from quest_reviewobject r, quest_issue_reviewobj ir ");
		sb.append("where r.id=ir.reviewobject_id and ir.quest_id=?");
		String sql = sb.toString();
		List list = this.getJdbcTemplate().queryForBeans(sql, issueId);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				String compName = (String)bean.get("object_name");
				compNames += compName + ",";
			}
		}
		if(!"".equals(compNames)){
			compNames = compNames.substring(0, compNames.length() - 1);
		}
		return compNames;
	}
}
