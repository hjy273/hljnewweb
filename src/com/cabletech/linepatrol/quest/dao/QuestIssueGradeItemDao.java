package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestIssueGradeItem;

@Repository
public class QuestIssueGradeItemDao extends HibernateDao<QuestIssueGradeItem, String> {
	/**
	 * ���ָ����
	 * @param items��ָ����IDS
	 * @param issueId:�ʾ�ID
	 */
	public void addItem(String items, String issueId){
		if(items != null && items.length() > 0){
			String[] itemIds = items.split(",");
			for (int i = 0; i < itemIds.length; i++) {
				QuestIssueGradeItem questIssueGradeItem = new QuestIssueGradeItem();
				questIssueGradeItem.setItemId(itemIds[i]);
				questIssueGradeItem.setQuestId(issueId);
				this.save(questIssueGradeItem);
			}
		}
	}
	
	/**
	 * ���ʾ�ID�����ʾ���ʾ���Ϣ
	 * @param issueId���ʾ�ID
	 * @return
	 */
	public List getTableItems(String issueId) {
		StringBuffer buf = new StringBuffer();
		buf.append(" select c.id cid,c.class type1,s.id sid,s.sort type2,i.id,i.item,i.weight_value score,i.remark,i.options  ");
		buf.append(" from quest_issue_gradeitem g,quest_guideline_item i,quest_guideline_sort s,quest_guideline_class c ");
		buf.append(" where g.item_id=i.id and i.sort_id=s.id and s.class_id=c.id and g.questionnaire_id=? ");
		buf.append(" order by c.id,s.id,i.id");
		logger.info("*************buf.toString():" + buf.toString());
		List list = this.getJdbcTemplate().queryForBeans(buf.toString(), issueId);
		return list;
	}
	
	/**
	 * ɾ���ʾ��������֮���ϵ����
	 * @param issueId���ʾ�ID
	 */
	public void deleteGradeItemByIssueId(String issueId){
		String sql = "delete from quest_issue_gradeitem t where t.questionnaire_id='" + issueId + "'";
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * ��ָ����ID���ʾ�ID���ָ�������ʾ��ϵ��ID
	 * @param issueId���ʾ�ID
	 * @param itemId��ָ����ID
	 * @return���ʾ���ָ�����ϵID
	 */
	public String getGItemIdByIssueIdAndItemId(String issueId, String itemId){
		String gItemId = "";
		String sql = "select gitem.id gitem_id from quest_issue_gradeitem gitem where gitem.item_id=? and gitem.questionnaire_id=?";
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql, itemId, issueId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = list.get(0);
			gItemId = (String)bean.get("gitem_id");
		}
		return gItemId;
	}
}
