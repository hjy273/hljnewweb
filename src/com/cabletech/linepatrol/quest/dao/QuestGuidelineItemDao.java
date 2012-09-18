package com.cabletech.linepatrol.quest.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestGuidelineItem;

@Repository
public class QuestGuidelineItemDao extends HibernateDao<QuestGuidelineItem, String> {
	/**
	 * ��ָ����������еĲ�����
	 * @param sortId��ָ�����ID
	 * @param issueId���ʾ���ID
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
	 * �ɲ�����ID��ò�����Bean
	 * @param itemId��������ID
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
	 * ���ʾ�ID��ò�����Bean�б�
	 * @param issueId���ʾ�ID
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
	 * �ɲ�����IDɾ���ò�����
	 * @param issueItemId��������ID
	 */
	public void deleteItemByIssueItemId(String issueItemId){
		String sql = "delete from quest_issue_gradeitem isitem where isitem.id='" + issueItemId + "'";
		getJdbcTemplate().execute(sql);
	}
	
	//��ѯ���еĲ�����
	public List questItemManagerList(){
		//i���ڼ�¼����
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
	 * �жϲ������Ƿ����
	 * @param sortId��ָ�����Id
	 * @param itemName������������
	 * @param itemId��������ID
	 * @return�������ڷ��ء�exists�������򷵻ؿ�
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
	 * ����ָ����
	 * @param item��ָ����ʵ��
	 * @return��ָ����ʵ��
	 */
	public QuestGuidelineItem saveQuestGuidelineItem(QuestGuidelineItem item){
		this.save(item);
		this.initObject(item);
		return item;
	}
	
	/**
	 * ɾ��ָ����
	 * @param itemId��ָ����ID
	 * @return���ɹ�����"success"��ʧ�ܷ���"failure"
	 */
	 public String deleteItemByItemId(String itemId){
		 String flag = "";
		 StringBuffer sb = new StringBuffer();
		 sb.append("select 1 from quest_issue_gradeitem gitem,quest_issue_result res");
		 sb.append(" where gitem.id=res.item_id and gitem.item_id=?");
		 List list = this.getJdbcTemplate().queryForBeans(sb.toString(), itemId);
		 if(list != null && list.size() > 0){
			 //ɾ��ʧ��
			 flag = "failure";
		 }else{
			 String sql = "delete from quest_guideline_item t where t.id='" + itemId + "'";
			 getJdbcTemplate().execute(sql);
			 //ɾ���ɹ�
			 flag = "success";
		 }
		 return flag;
	 }
	 
	 /**
	  * ��ָ�������ָ�����б�
	  * @param sortId��ָ�����ID
	  * @return��ָ�����б�
	  */
	 public List getItemListBySortId(String sortId){
		 List list = findByProperty("sortId", sortId);
		 return list;
	 }
}
