package com.cabletech.linepatrol.quest.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestIssue;

@Repository
public class QuestIssueDao extends HibernateDao<QuestIssue, String> {
	/**
	 * �����ʾ���Ϣ
	 * @param questIssue���ʾ�
	 * @return���ʾ�
	 */
	public QuestIssue saveQuestIssue(QuestIssue questIssue){
		save(questIssue);
		initObject(questIssue);
		return questIssue;
	}
	
	/**
	 * �����ʱ�����ʾ��б�
	 * @return����ʱ�����ʾ��б�
	 */
	public List perfectIssueList(){
		String sql = "select issue.id,issue.questionnaire_name name,'������-'||type.type type from quest_issue issue,quest_type type"
			+ " where type.id=issue.questionnaire_type and issue.state='1' order by issue.create_date desc";
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * ���ĳ��ά��˾�ʾ�����б�
	 * @param conId����ά��˾ID
	 * @return���ʾ�����б�
	 */
	public List getIssueFeedbackList(String conId){
		String sql = "select issue.id,issue.questionnaire_name name,'������-'||type.type type from quest_issue issue,quest_contractor con,quest_type type"
			+ " where issue.id=con.que_id and type.id=issue.questionnaire_type and con.contractor_id='" + conId + "' and con.state in('1','2')";
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * ���ʾ�ID����ʾ���������
	 * @param issueId���ʾ�ID
	 * @return���ʾ���������
	 */
	public String getTypeNameByIsssueId(String issueId){
		String typeName = "";
		String sql = "select type.type from quest_issue issue,quest_type type "
			+ " where type.id=issue.questionnaire_type and issue.id=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, issueId);
		if(list != null && list.size() > 0){
			DynaBean bean = (BasicDynaBean)list.get(0);
			typeName = (String)bean.get("type");
		}
		return typeName;
	}
	
	/**
	 * �ɴ�ά��˾ID��ô�ά��˾�绰����
	 * @param conId����ά��˾ID
	 * @return����ά��˾�绰����
	 */
	public String getPhoneByConId(String conId){
		String phone = "";
		String sql = "select t.linkmaninfo from contractorinfo t where t.contractorid=?";
		List list = getJdbcTemplate().queryForBeans(sql, conId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			phone = (String)bean.get("linkmaninfo");
		}
		return phone;
	}
	
	/**
	 * �Ӳ���������в������еĲ�������ID
	 * issueId���ʾ�ID
	 * @return����������ID����","�ָ�
	 */
	public String getAllComIdFromResult(String issueId){
		String comIds = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct res.review_object_id from quest_issue_result res,quest_issue_gradeitem gitem");
		sb.append(" where res.item_id=gitem.id and gitem.questionnaire_id='");
		sb.append(issueId);
		sb.append("'");
		String sql = sb.toString();
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql);
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
	
	/**
	 * �Ӳ���������в������еĲ�����ID
	 * issueId���ʾ�ID
	 * @return��������ID����","�ָ�
	 */
	public String getAllItemIdFromResult(String issueId){
		String itemIds = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct gitem.item_id from quest_issue_result res,quest_issue_gradeitem gitem,quest_guideline_item item,quest_issue issue,quest_contractor con ");
		sb.append(" where res.item_id=gitem.id and gitem.item_id=item.id and issue.id=gitem.questionnaire_id and issue.state='2' ");
		sb.append(" and con.que_id=issue.id and con.state='3' and con.contractor_id=res.userid ");
		sb.append(" and gitem.questionnaire_id='");
		sb.append(issueId);
		sb.append("'");
		String sql = sb.toString();
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			for(BasicDynaBean bean:list){
				String itemId = (String)bean.get("item_id");
				itemIds += itemId + ",";
			}
		}
		if(!"".equals(itemIds)){
			itemIds = itemIds.substring(0, itemIds.length() - 1);
		}
		return itemIds;
	}
	
	/**
	 * ������еĴ�ά��˾
	 * @param issueId
	 * @return
	 */
	public String getAllConIdFromResult(String issueId){
		String conIds = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct res.userid from quest_issue_result res,quest_issue_gradeitem gitem,quest_issue issue,quest_contractor con ");
		sb.append(" where issue.id=gitem.questionnaire_id and issue.state='2' and con.que_id=issue.id and con.state='3'");
		sb.append(" and con.contractor_id=res.userid and res.item_id=gitem.id and gitem.questionnaire_id='");
		sb.append(issueId);
		sb.append("'");
		String sql = sb.toString();
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			for(BasicDynaBean bean:list){
				String conId = (String)bean.get("userid");
				conIds += conId + ",";
			}
		}
		if(!"".equals(conIds)){
			conIds = conIds.substring(0, conIds.length() - 1);
		}
		return conIds;
	}
	
	/**
	 * ����ʾ����ĳһ�������������ѡ��Ĵ���
	 * @param issueId���ʾ�ID
	 * @param itemId���ʾ���ID
	 * @param comId����������ID
	 * @param ruleId��������ID
	 * @return
	 */
	public Map statNumByRuleIdAndComId(String issueId, String itemId, String comId, String ruleId){
		int num = 0;
		String conIds = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select res.score,res.userid from quest_issue_result res, quest_issue_gradeitem gitem,quest_issue issue,quest_contractor con ");
		sb.append(" where issue.id=gitem.questionnaire_id and issue.id=con.que_id and con.contractor_id=res.userid and con.state='3' and res.item_id=gitem.id and gitem.questionnaire_id=?");
		sb.append(" and gitem.item_id=? and res.review_object_id=?");
		String sql = sb.toString();
		List list = getJdbcTemplate().queryForBeans(sql, issueId, itemId, comId);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				String score = (String)bean.get("score");
				if(score.indexOf(ruleId) != -1){
					conIds += (String)bean.get("userid") + ",";
					num++;
				}
			}
		}
		String conNames = getConNamesByConIds(conIds);
		Map map = new HashMap();
		map.put("num", ""+Integer.toString(num));
		map.put("conNames", conNames);
		return map;
	}
	
	/**
	 * ��ô�ά��˾����
	 * @param conId����ά��λID���ã��ŷָ�
	 * @return����ά��λ���ƣ��ã��ŷָ�
	 */
	public String getConNamesByConIds(String conIds){
		String conNames = "";
		if(conIds != null && !"".equals(conIds)){
			String[] conIdsArray = conIds.split(",");
			for (int i = 0; i < conIdsArray.length; i++) {
				conNames += getConNameByConId(conIdsArray[i]) + ",";
			}
		}
		if(!"".equals(conNames)){
			conNames = conNames.substring(0, conNames.length() - 1);
		}
		return conNames;
	}
	
	/**
	 * ��ô�ͳ���б�
	 * @return����ͳ���б�
	 */
	public List questFeedbackStatList(){
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct issue.id,issue.questionnaire_name name,type.type type,issue.remark remark,to_char(issue.create_date,'yyyy-mm-dd hh24:mi:ss') create_date ");
		sb.append("from quest_issue issue,quest_contractor con,quest_type type ");
		sb.append("where issue.id=con.que_id and con.state='3' and issue.questionnaire_type=type.id");
		String sql = sb.toString();
		List list = this.getJdbcTemplate().queryForBeans(sql);
		return list;
	}
	
	/**
	 * �ʾ����
	 * issueId���ʾ�ID
	 * @return���ʾ�����б�List������ÿһ��Ϊһ��List���ֱ��¼ÿһ��ֵ
	 */
	public Map questFeedbackStat(String issueId){
		Map mapReturn = new HashMap();//�洢�����
		List list = new ArrayList();//ÿ���ʾ�Ľ����
		List itemList = null;//��¼ÿ���ʾ�������б���Ϣ
		Map map = null;//�洢ÿһ���ʾ�keyΪ�ʾ�������,valueΪ�ʾ���Ľ����
		
		//���Ȼ�ù����ٲ������̺��ж��ٲ�����
		//comIds��Ϊ���еĲ�������  itemIds��Ϊ���еĲ�����
		String comIds = getAllComIdFromResult(issueId);//���еĲ�������
		int comNum = 0;
		String itemIds = getAllItemIdFromResult(issueId);//�ӽ�����л�����ύ���ʾ���ļ���
		String conIds = getAllConIdFromResult(issueId);//�ӽ�����л�����ύ���ʾ�Ĵ�ά��˾
		String[] comIdArray = null;
		String[] itemIdArray = null;
		String[] conIdArray = null;
		if(comIds != null && !"".equals(comIds) && itemIds != null && !"".equals(itemIds)){
			comIdArray = comIds.split(",");
			itemIdArray = itemIds.split(",");
			conIdArray = conIds.split(",");
			comNum = comIdArray.length;
			//��ȡ��ѡ���ֵ
			//����һ������������
			for (int i = 0; i < itemIdArray.length; i++) {
				map = new HashMap();
				String options = getItemOptionsByItemId(itemIdArray[i]);//��ø��ʾ�������ͣ�1������ 2����ѡ 3����ѡ 4 �ı�
				itemList = new ArrayList();
				//row1��¼��һ����Ϣ��Ϊ��ʾ����
				List row1 = new ArrayList();
				row1.add("");//��һ�е�һ��
				for (int m = 0; m < comIdArray.length; m++) {
					String comName = getComNameByComId(comIdArray[m]);//������������
					row1.add("bold^"+comName);
				}
				itemList.add(row1);
				//�Ȳ�����ѡ��ֵ
				if("1".equals(options)){
					String[][] scoreArray = new String[conIdArray.length][comIdArray.length];
					for (int j = 0; j < conIdArray.length; j++) {
						List row = new ArrayList();
						String conName = getConNameByConId(conIdArray[j]);
						row.add("bold^"+conName);
						for (int k = 0; k < comIdArray.length; k++) {
							String score = getScoreByIssueIdComIdConId(issueId, comIdArray[k], conIdArray[j], itemIdArray[i]);
							scoreArray[j][k] = score;//��һ����ά����洢�÷��������������ܺ�
							if(score == null || "".equals(score)){
								score = "-";
							}
							row.add("normal^"+score);
						}
						itemList.add(row);
					}
					//��Ϊ����ʱ�������ܺ�
					int columnNum = comIdArray.length;
					List sumRow = new ArrayList();
					sumRow.add("bold^�ϼ�");
					for(int k = 0; k < columnNum; k++){
						int sum = 0;
						//����ÿһ��֮��
						for (int j = 0; j < conIdArray.length; j++) {
							if(scoreArray[j][k] != null && scoreArray[j][k] != "-" && scoreArray[j][k] != ""){
								sum += Integer.parseInt(scoreArray[j][k]);
							}
						}
						sumRow.add("normal^"+Integer.toString(sum));
					}
					itemList.add(sumRow);
				}else if("4".equals(options)){
					//��Ϊ�û�������ı�ʱ
					for (int j = 0; j < conIdArray.length; j++) {
						List row = new ArrayList();
						String conName = getConNameByConId(conIdArray[j]);
						row.add("bold^"+conName);
						for (int k = 0; k < comIdArray.length; k++) {
							String score = getScoreByIssueIdComIdConId(issueId, comIdArray[k], conIdArray[j], itemIdArray[i]);
							if(score == null || "".equals(score)){
								score = "-";
							}
							row.add("normal^"+score);
						}
						itemList.add(row);
					}
				}else{
					//�������Ϊ�û���ѡ�Ͷ�ѡ���
					List ruleIdList = getRuleIdsByItemId(itemIdArray[i]);
					if(ruleIdList.size() > 0){
						for(int j = 0; j < ruleIdList.size(); j++){
							String ruleId = (String)ruleIdList.get(j);
							String ruleName = ruleNameByRuleId(ruleId);
							List row = new ArrayList();
							row.add("bold^"+ruleName);//��һ������
							for (int k = 0; k < comIdArray.length; k++) {
								Map map1 = statNumByRuleIdAndComId(issueId, itemIdArray[i], comIdArray[k], ruleId);
								String num = (String)map1.get("num");
								String conNames = (String)map1.get("conNames");
								if(conNames==null || "".equals(conNames)){
									conNames = " ";
								}
								row.add(conNames + "^" + num + "^titleShow");
							}
							itemList.add(row);
						}
					}
				}
				String itemName = getItemNameByItemId(itemIdArray[i]);
				map.put(itemName, itemList);
				list.add(map);
			}
		}
		BasicDynaBean issueBean = getIssueInfoByIssueId(issueId);
		mapReturn.put("list", list);
		mapReturn.put("comNum", Integer.toString(comNum));
		mapReturn.put("issueBean", issueBean);
		return mapReturn;
	}
	
	/**
	 * ����ʾ������ơ����ۡ�����
	 * @param issueId���ʾ�ID
	 * @return��BasicDynaBean
	 */
	public BasicDynaBean getIssueInfoByIssueId(String issueId){
		BasicDynaBean bean = null;
		String sql = "select issue.questionnaire_name,issue.remark,type.type from quest_issue issue,quest_type type where issue.questionnaire_type=type.id and issue.id=?";
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql, issueId);
		if(list != null && list.size() > 0){
			bean = list.get(0);
		}
		return bean;
	}
	
	/**
	 * ���ʾ���ID��ø��ʾ������� 1������ 2����ѡ 3����ѡ 4���ı�
	 * @param itemId���ʾ���ID
	 * @return���ʾ�������
	 */
	public String getItemOptionsByItemId(String itemId){
		String options = "";
		String sql = "select t.options from quest_guideline_item t where t.id=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, itemId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			options = (String)bean.get("options");
		}
		return options;
	}
	
	/**
	 * ���ĳ�ʾ�����ʾ�ϸ���б�
	 * @param itemId���ʾ���ID
	 * @return���ʾ�ϸ���б�
	 */
	public List getRuleIdsByItemId(String itemId){
		List backList = new ArrayList();
		String sql = "select t.id,t.grade_explain from quest_grade_rule t where t.item_id=?";
		List list = getJdbcTemplate().queryForBeans(sql, itemId);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				String ruleId = (String)bean.get("id");
				backList.add(ruleId);
			}
		}
		return backList;
	}
	
	/**
	 * ������ϸ��ID�������ϸ������
	 * @param ruleId������ϸ��ID
	 * @return������ϸ������
	 */
	public String ruleNameByRuleId(String ruleId){
		String ruleName = "";
		String sql = "select t.grade_explain from quest_grade_rule t where t.id=?";
		List list = getJdbcTemplate().queryForBeans(sql, ruleId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			ruleName = (String)bean.get("grade_explain");
		}
		return ruleName;
	}
	
	/**
	 * �ɲ�������ID��ò�����������
	 * @param conId����������ID
	 * @return��������������
	 */
	public String getConNameByConId(String conId){
		String conName = "";
		String sql = "select con.contractorname from contractorinfo con where con.contractorid=?";
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql, conId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = list.get(0);
			conName = (String)bean.get("contractorname");
		}
		return conName;
	}
	
	/**
	 * ���ʾ���ID����ʾ�������
	 * @param itemId���ʾ���ID
	 * @return���ʾ�������
	 */
	public String getItemNameByItemId(String itemId){
		String itemName = "";
		String sql = "select item.item from quest_guideline_item item where item.id=?";
		List<BasicDynaBean> list = getJdbcTemplate().queryForBeans(sql, itemId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = list.get(0);
			itemName = (String)bean.get("item");
		}
		return itemName;
	}
	
	/**
	 * �����ĳ�ʾ��У���������ĳ��ά��˾��ĳ������������
	 * @param issueId���ʾ�ID
	 * @param comId����������ID
	 * @param conId����ά��˾ID
	 * @param itemId���ʾ���ID
	 * @return����õķ���
	 */
	public String getScoreByIssueIdComIdConId(String issueId, String comId, String conId, String itemId){
		String score = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select res.score from quest_issue_result res,quest_issue_gradeitem gitem ");
		sb.append("where res.item_id=gitem.id and gitem.questionnaire_id=?");
		sb.append("and gitem.item_id=? and res.userid=? and res.review_object_id=?");
		String sql = sb.toString();
		List list = this.getJdbcTemplate().queryForBeans(sql, issueId, itemId, conId, comId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			score = (String)bean.get("score");
		}
		return score;
	}
	
	/**
	 * ͨ���������̵�ID��ѯ����������
	 * @param comId��������ID
	 * @return��������������
	 */
	public String getComNameByComId(String comId){
		String comName = "";
		String sql = "select ro.object com_name from quest_reviewobject ro where ro.id=? ";
		List list = this.getJdbcTemplate().queryForBeans(sql, comId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			comName = (String)bean.get("com_name");
		}
		return comName;
	}
	
	/**
	 * ͨ��������ID�Ͳ�����ѡ���ѯ����������
	 * @param itemId��������ID
	 * @param options��������ѡ��
	 * @return������������
	 */
	public String getItemNameByItemId(String itemId, String options){
		String itemName = "";
		String sql = "select item.item item_name from quest_guideline_item item,quest_issue_gradeitem gitem " +
				" where item.id=gitem.item_id and gitem.item_id=? ";
		if(options.equals("select")){
			sql += " and item.options in ('2','3')";
		}else{
			sql += " and item.options in ('1','4')";
		}
		List list = this.getJdbcTemplate().queryForBeans(sql, itemId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			itemName = (String)bean.get("item_name");
		}
		return itemName;
	}
	
	/**
	 * ��ò�ѯ�ʾ��б�
	 * @param issueName���ʾ�����
	 * @param beginTime����ѯ��ʼʱ��
	 * @param endTime����ѯ����ʱ��
	 * @return
	 */
	public List queryIssueByCondition(String issueName, String beginTime, String endTime){
		StringBuffer sb = new StringBuffer();
		sb.append("select issue.id,issue.questionnaire_name name,type.type type,to_char(issue.create_date,'yyyy-mm-dd hh24:mi:ss') create_date ");
		sb.append("from quest_issue issue,quest_type type ");
		sb.append("where type.id=issue.questionnaire_type and issue.state='2' ");
		if(issueName != null && !"".equals(issueName)){
			sb.append("and issue.questionnaire_name like '%" + issueName + "%' ");
		}
		if(beginTime != null && !"".equals(beginTime)){
			sb.append("and issue.create_date>to_date('" + beginTime + "','yyyy/mm/dd hh24:mi:ss') ");
		}
		if(endTime != null && !"".equals(endTime)){
			sb.append("and issue.create_date<to_date('" + addOneDay(endTime) + "','yyyy/mm/dd hh24:mi:ss') ");
		}
		String sql = sb.toString();
		return getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * ��ǰʱ������һ��
	 * @param strDate������ǰʱ��
	 * @return�����Ӻ�ʱ��
	 */
	private String addOneDay(String strDate){
		String addDate = "";
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if(strDate != null && !"".equals(strDate)){
			try {
				Date date = df.parse(strDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH,1);
				addDate = df.format(calendar.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return addDate;
	}
	
	/**
	 * ���ʾ�ID����ʾ����ϸ��Ϣ
	 * @param issueId���ʾ�ID
	 * @return���ʾ����ϸ��Ϣ
	 */
	public BasicDynaBean getIssueByIssueId(String issueId){
		BasicDynaBean bean = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select issue.id,issue.questionnaire_name name,type.type type,issue.remark,to_char(issue.create_date,'yyyy-mm-dd hh24:mi:ss') create_date ");
		sb.append("from quest_issue issue,quest_type type ");
		sb.append("where type.id=issue.questionnaire_type and issue.id=? ");
		String sql = sb.toString();
		List list = getJdbcTemplate().queryForBeans(sql, issueId);
		if(list != null && list.size() > 0){
			bean = (BasicDynaBean)list.get(0);
		}
		return bean;
	}
	
	/**
	 * ���ʾ�ID������еĴ�ά��˾����
	 * @param issueId���ʾ�ID
	 * @return����ά��˾����
	 */
	public String getConNameByIssueId(String issueId){
		String conNames = "";
		StringBuffer sb = new StringBuffer();
		sb.append("select ci.contractorname from quest_contractor c,contractorinfo ci ");
		sb.append("where c.contractor_id=ci.contractorid and c.que_id=?");
		String sql = sb.toString();
		List list = this.getJdbcTemplate().queryForBeans(sql, issueId);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				String conName = (String)bean.get("contractorname");
				conNames += conName + ",";
			}
		}
		if(!"".equals(conNames)){
			conNames = conNames.substring(0, conNames.length() - 1);
		}
		return conNames;
	}
}
