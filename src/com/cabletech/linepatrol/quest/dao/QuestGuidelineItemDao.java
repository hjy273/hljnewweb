package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestGuidelineItem;

@Repository
public class QuestGuidelineItemDao extends HibernateDao<QuestGuidelineItem, String> {
	/**
	 * 由指标分类或的所有的参评项
	 * @param sortId：指标分类ID
	 * @param issueId：问卷项ID
	 * @return
	 */
	public List getItemListBySortId(String sortId, String issueId){
		StringBuffer sb = new StringBuffer();
		sb.append("select item.id,cl.class qclass,sort.id sort_id,sort.sort,item.item,item.weight_value ");
		sb.append("from quest_guideline_class cl,quest_guideline_sort sort,quest_guideline_item item ");
		sb.append("where cl.id=sort.class_id and sort.id=item.sort_id ");
		sb.append("and item.sort_id=? and not exists ");
		sb.append("(select 1 from quest_issue issue,quest_issue_gradeitem gitem where issue.id=gitem.questionnaire_id ");
		sb.append("and gitem.item_id=item.id and issue.id=?)");
		List list = this.getJdbcTemplate().queryForBeans(sb.toString(), sortId, issueId);
		return list;
	}
	
	/**
	 * 由参评项ID获得参评项Bean
	 * @param itemId：参评项ID
	 * @return
	 */
	public BasicDynaBean getItemBeanByItemId(String itemId){
		BasicDynaBean bean = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select item.id,cl.class class_name,sort.id sort_id,sort.sort sort_name,item.item item_name,item.weight_value ");
		sb.append("from quest_guideline_class cl,quest_guideline_sort sort,quest_guideline_item item ");
		sb.append(" where cl.id=sort.class_id and sort.id=item.sort_id and item.id=?");
		String sql = sb.toString();
		List list = getJdbcTemplate().queryForBeans(sql, itemId);
		if(list != null && list.size() > 0){
			bean = (BasicDynaBean)list.get(0);
		}
		return bean;
	}
	
	/**
	 * 由问卷ID获得参评项Bean列表
	 * @param issueId：问卷ID
	 * @return
	 */
	public List<BasicDynaBean> queryItemBeanListByIssueId(String issueId){
		BasicDynaBean bean = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select item.id,cl.class class_name,sort.id sort_id,sort.sort sort_name,item.item item_name,item.weight_value ");
		sb.append("from quest_guideline_class cl,quest_guideline_sort sort,quest_guideline_item item,quest_issue_gradeitem gitem ");
		sb.append("where cl.id=sort.class_id and sort.id=item.sort_id and item.id=gitem.item_id and gitem.questionnaire_id=?");
		String sql = sb.toString();
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql, issueId);
		return list;
	}
	
	public List queryItemListByIssueId(String issueId){
		String sql = "select isitem.id quest_id,item.id,cl.class qclass,sort.sort,item.item,item.weight_value "
			+ " from quest_guideline_class cl,quest_guideline_sort sort,quest_guideline_item item,quest_issue_gradeitem isitem"
			+ " where cl.id=sort.class_id and sort.id=item.sort_id and isitem.item_id=item.id and isitem.questionnaire_id='" + issueId + "'";
		sql += " order by cl.id,sort.id,item.id";
		List list = this.getJdbcTemplate().queryForBeans(sql);
		return list;
	}
	
	/**
	 * 由参评项ID删除该参评项
	 * @param issueItemId：参评项ID
	 */
	public void deleteItemByIssueItemId(String issueItemId){
		String sql = "delete from quest_issue_gradeitem isitem where isitem.id='" + issueItemId + "'";
		getJdbcTemplate().execute(sql);
	}
	
	//查询所有的参评项
	public List questItemManagerList(){
		//i用于记录数量
		int i = 1;
		String sql ="select sort.sort sort_name,item.id,'' as row_num,item.item item_name " +
				"from quest_guideline_item item,quest_guideline_sort sort where item.sort_id=sort.id and item.weight_value<>999";
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			for(BasicDynaBean bean:list){
				bean.set("row_num", Integer.toString(i));
				i++;
			}
		}
		return list;
	}
	
	/**
	 * 判断参评项是否存在
	 * @param sortId：指标分类Id
	 * @param itemName：参评项名称
	 * @param itemId：参评项ID
	 * @return：若存在返回“exists”，否则返回空
	 */
	public String judgeItemExists(String sortId, String itemName, String itemId){
		String flag = "";
		String sql = "select 1 from quest_guideline_item t where t.sort_id=? and t.item=?";
		if(itemId != null && !"".equals(itemId)){
			sql += " and t.id<>'" + itemId.trim() + "'";
		}
		List list = getJdbcTemplate().queryForBeans(sql, sortId.trim(), itemName.trim());
		if(list != null && list.size() > 0){
			flag = "exists";
		}
		return flag;
	}
	
	/**
	 * 保存指标项
	 * @param item：指标项实体
	 * @return：指标项实体
	 */
	public QuestGuidelineItem saveQuestGuidelineItem(QuestGuidelineItem item){
		this.save(item);
		this.initObject(item);
		return item;
	}
	
	/**
	 * 删除指标项
	 * @param itemId：指标项ID
	 * @return：成功返回"success"，失败返回"failure"
	 */
	 public String deleteItemByItemId(String itemId){
		 String flag = "";
		 StringBuffer sb = new StringBuffer();
		 sb.append("select 1 from quest_issue_gradeitem gitem,quest_issue_result res");
		 sb.append(" where gitem.id=res.item_id and gitem.item_id=?");
		 List list = this.getJdbcTemplate().queryForBeans(sb.toString(), itemId);
		 if(list != null && list.size() > 0){
			 //删除失败
			 flag = "failure";
		 }else{
			 String sql = "delete from quest_guideline_item t where t.id='" + itemId + "'";
			 getJdbcTemplate().execute(sql);
			 //删除成功
			 flag = "success";
		 }
		 return flag;
	 }
	 
	 /**
	  * 由指标分类获得指标项列表
	  * @param sortId：指标分类ID
	  * @return：指标项列表
	  */
	 public List getItemListBySortId(String sortId){
		 List list = findByProperty("sortId", sortId);
		 return list;
	 }
}
