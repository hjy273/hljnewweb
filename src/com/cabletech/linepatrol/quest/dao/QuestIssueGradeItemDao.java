package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestIssueGradeItem;

@Repository
public class QuestIssueGradeItemDao extends HibernateDao<QuestIssueGradeItem, String> {
	/**
	 * 添加指标项
	 * @param items：指标项IDS
	 * @param issueId:问卷ID
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
	 * 由问卷ID获得显示的问卷信息
	 * @param issueId：问卷ID
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
	 * 删除问卷与参评项之间关系数据
	 * @param issueId：问卷ID
	 */
	public void deleteGradeItemByIssueId(String issueId){
		String sql = "delete from quest_issue_gradeitem t where t.questionnaire_id='" + issueId + "'";
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 由指标项ID与问卷ID获得指标项与问卷关系表ID
	 * @param issueId：问卷ID
	 * @param itemId：指标项ID
	 * @return：问卷与指标项关系ID
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
