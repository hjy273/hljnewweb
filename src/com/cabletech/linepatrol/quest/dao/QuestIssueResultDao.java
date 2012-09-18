package com.cabletech.linepatrol.quest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.beans.QuestIssueResultBean;
import com.cabletech.linepatrol.quest.module.QuestGuidelineItem;
import com.cabletech.linepatrol.quest.module.QuestIssueResult;

@Repository
public class QuestIssueResultDao extends HibernateDao<QuestIssueResult, String> {
	/**
	 * ���ʾ�ID���ά��λID����ʾ�������
	 * ��������Ϊ��ѡ���ѡʱ����ʾ����ϢӦ��Ϊ����ϸ�����ƶ���������ϸ���ID
	 * @param issueId���ʾ�ID
	 * @param conId����ά��λID
	 * @return���ʾ�������
	 */
	public Map getExistsValueByIssueId(String issueId, String conId){
		Map map = new HashMap();
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct issue.id issue_id,res.score true_value,'' as show_value,res.review_object_id com_id,gitem.item_id item_id,item.options");
		sb.append(" from quest_issue issue, quest_issue_gradeitem gitem,quest_issue_result res,quest_guideline_item item");
		sb.append(" where issue.id=gitem.questionnaire_id and gitem.id=res.item_id and gitem.item_id=item.id");
		sb.append(" and issue.id=? and res.userid=?");
		sb.append(" order by res.review_object_id,gitem.item_id");
		String sql = sb.toString();
		List<BasicDynaBean> list = this.getJdbcTemplate().queryForBeans(sql, issueId, conId);
		List<QuestIssueResultBean> issueResultBeanList = new ArrayList();
		//���optionsΪ��ѡ����ѡʱ��Ҫȡ����Ӧ��item��ֵ
		if(list != null && list.size() > 0){
			for(BasicDynaBean bean:list){
				QuestIssueResultBean issueResultBean = new QuestIssueResultBean();
				String options = (String)bean.get("options");
				String trueValue = (String)bean.get("true_value");//��¼���ݿ�����ʵ�洢������
				String comId = (String)bean.get("com_id");
				String itemId = (String)bean.get("item_id");
				String showValue = "";//����ҳ����ʾ������
				if(QuestGuidelineItem.CHECKBOX.equals(options) || QuestGuidelineItem.RADIO.equals(options)){
					showValue = getRuleValueByItemId(trueValue);
				}else{
					showValue = trueValue;
				}
				String comItemId = comId + itemId;
				issueResultBean.setComItemId(comItemId);
				issueResultBean.setTrueValue(trueValue);
				issueResultBean.setShowValue(showValue);
				map.put(comItemId, issueResultBean);
			}
		}
		return map;
	}
	
	/**
	 * �ɲ�����ID�������ϸ������
	 * @param itemId��������ID
	 * @return
	 */
	public String getRuleValueByItemId(String itemId){
		String ruleValue = "";
		if(itemId != null && !"".equals(itemId)){
			String[] itemIdArray = itemId.split(",");
			String sql = "select rule.id,rule.grade_explain from quest_grade_rule rule where id=?";
			for (int i = 0; i < itemIdArray.length; i++) {
				List list = getJdbcTemplate().queryForBeans(sql, itemIdArray[i]);
				if(list != null && list.size() > 0){
					BasicDynaBean bean = (BasicDynaBean)list.get(0);
					String rule = (String)bean.get("grade_explain");
					ruleValue += rule + ",";
				}
			}
		}
		if(!"".equals(ruleValue)){
			ruleValue = ruleValue.substring(0, ruleValue.length() - 1);
		}
		return ruleValue;
	}
	
	/**
	 * ���ʾ�ID�ʹ�ά��˾IDɾ���ʾ���
	 * @param issueId���ʾ�ID
	 * @param conId����ά��˾ID
	 */
	public void deleteIssueResultByIssueId(String issueId, String conId){
		StringBuffer sb = new StringBuffer();
		sb.append("delete from quest_issue_result res");
		sb.append(" where res.userid='");
		sb.append(conId);
		sb.append("' and exists (select 1 from quest_issue_gradeitem gitem where gitem.id=res.item_id and gitem.questionnaire_id='");
		sb.append(issueId);
		sb.append("')");
		String sql = sb.toString();
		logger.info("���ʾ�IDɾ����������" + sql);
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * ���ʾ�ID�ʹ�ά��˾ID��ò�������ID
	 * @param issueId���ʾ�ID
	 * @param conId����ά��˾ID
	 * @return����������ID
	 */
	public String getComIdByIssueId(String issueId, String conId){
		String comIds = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct res.review_object_id from quest_issue_gradeitem item, quest_issue_result res");
		sb.append(" where item.questionnaire_id='");
		sb.append(issueId);
		sb.append("' and item.id=res.item_id");
		sb.append(" and res.userid='");
		sb.append(conId);
		sb.append("'");
		String sql = sb.toString();
		logger.info("���ʾ�ID�ʹ�άID��ò�����˾ID��" + sql);
		List<BasicDynaBean> list = this.getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			for(BasicDynaBean bean:list){
				String comId = (String)bean.get("review_object_id");
				comIds += comId + ",";
			}
		}
		if(!"".equals(comIds)){
			comIds = comIds.substring(0, comIds.length() - 1);
		}
		return comIds;
	}
}
