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
	 * 保存问卷信息
	 * @param questIssue：问卷
	 * @return：问卷
	 */
	public QuestIssue saveQuestIssue(QuestIssue questIssue){
		save(questIssue);
		initObject(questIssue);
		return questIssue;
	}
	
	/**
	 * 获得临时保存问卷列表
	 * @return：临时保存问卷列表
	 */
	public List perfectIssueList(){
		String sql = "select issue.id,issue.questionnaire_name name,'后评估-'||type.type type from quest_issue issue,quest_type type"
			+ " where type.id=issue.questionnaire_type and issue.state='1' order by issue.create_date desc";
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 获得某代维公司问卷调查列表
	 * @param conId：代维公司ID
	 * @return：问卷调查列表
	 */
	public List getIssueFeedbackList(String conId){
		String sql = "select issue.id,issue.questionnaire_name name,'后评估-'||type.type type from quest_issue issue,quest_contractor con,quest_type type"
			+ " where issue.id=con.que_id and type.id=issue.questionnaire_type and con.contractor_id='" + conId + "' and con.state in('1','2')";
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 由问卷ID获得问卷类型名称
	 * @param issueId：问卷ID
	 * @return：问卷类型名称
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
	 * 由代维公司ID获得代维公司电话号码
	 * @param conId：代维公司ID
	 * @return：代维公司电话号码
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
	 * 从参评结果表中查找所有的参评厂商ID
	 * issueId：问卷ID
	 * @return：参评厂商ID，用","分割
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
	 * 从参评结果表中查找所有的差评项ID
	 * issueId：问卷ID
	 * @return：参评项ID，用","分割
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
	 * 获得所有的代维公司
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
	 * 获得问卷项的某一评分项被参评对象选择的次数
	 * @param issueId：问卷ID
	 * @param itemId：问卷项ID
	 * @param comId：参评对象ID
	 * @param ruleId：评分项ID
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
	 * 获得代维公司名称
	 * @param conId：代维单位ID，用，号分隔
	 * @return：代维单位名称，用，号分隔
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
	 * 获得待统计列表
	 * @return：待统计列表
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
	 * 问卷汇总
	 * issueId：问卷ID
	 * @return：问卷汇总列表List，其中每一行为一个List，分别记录每一个值
	 */
	public Map questFeedbackStat(String issueId){
		Map mapReturn = new HashMap();//存储结果集
		List list = new ArrayList();//每个问卷的结果集
		List itemList = null;//记录每个问卷参评项列表信息
		Map map = null;//存储每一项问卷：key为问卷项名称,value为问卷项的结果集
		
		//首先获得共多少参评厂商和有多少参评项
		//comIds：为所有的参评厂商  itemIds：为所有的差评项
		String comIds = getAllComIdFromResult(issueId);//所有的参评厂商
		int comNum = 0;
		String itemIds = getAllItemIdFromResult(issueId);//从结果集中获得已提交的问卷的文件项
		String conIds = getAllConIdFromResult(issueId);//从结果集中获得已提交的问卷的代维公司
		String[] comIdArray = null;
		String[] itemIdArray = null;
		String[] conIdArray = null;
		if(comIds != null && !"".equals(comIds) && itemIds != null && !"".equals(itemIds)){
			comIdArray = comIds.split(",");
			itemIdArray = itemIds.split(",");
			conIdArray = conIds.split(",");
			comNum = comIdArray.length;
			//先取非选项的值
			//除第一行外其他的行
			for (int i = 0; i < itemIdArray.length; i++) {
				map = new HashMap();
				String options = getItemOptionsByItemId(itemIdArray[i]);//获得该问卷项的类型：1、数字 2、单选 3、多选 4 文本
				itemList = new ArrayList();
				//row1记录第一行信息，为显示标题
				List row1 = new ArrayList();
				row1.add("");//第一行第一列
				for (int m = 0; m < comIdArray.length; m++) {
					String comName = getComNameByComId(comIdArray[m]);//参评对象名称
					row1.add("bold^"+comName);
				}
				itemList.add(row1);
				//先操作非选项值
				if("1".equals(options)){
					String[][] scoreArray = new String[conIdArray.length][comIdArray.length];
					for (int j = 0; j < conIdArray.length; j++) {
						List row = new ArrayList();
						String conName = getConNameByConId(conIdArray[j]);
						row.add("bold^"+conName);
						for (int k = 0; k < comIdArray.length; k++) {
							String score = getScoreByIssueIdComIdConId(issueId, comIdArray[k], conIdArray[j], itemIdArray[i]);
							scoreArray[j][k] = score;//用一个二维数组存储该分数，用来计算总和
							if(score == null || "".equals(score)){
								score = "-";
							}
							row.add("normal^"+score);
						}
						itemList.add(row);
					}
					//当为数字时，计算总和
					int columnNum = comIdArray.length;
					List sumRow = new ArrayList();
					sumRow.add("bold^合计");
					for(int k = 0; k < columnNum; k++){
						int sum = 0;
						//计算每一列之和
						for (int j = 0; j < conIdArray.length; j++) {
							if(scoreArray[j][k] != null && scoreArray[j][k] != "-" && scoreArray[j][k] != ""){
								sum += Integer.parseInt(scoreArray[j][k]);
							}
						}
						sumRow.add("normal^"+Integer.toString(sum));
					}
					itemList.add(sumRow);
				}else if("4".equals(options)){
					//当为用户输入的文本时
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
					//此种情况为用户单选和多选情况
					List ruleIdList = getRuleIdsByItemId(itemIdArray[i]);
					if(ruleIdList.size() > 0){
						for(int j = 0; j < ruleIdList.size(); j++){
							String ruleId = (String)ruleIdList.get(j);
							String ruleName = ruleNameByRuleId(ruleId);
							List row = new ArrayList();
							row.add("bold^"+ruleName);//第一列数据
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
	 * 获得问卷项名称、评论、类型
	 * @param issueId：问卷ID
	 * @return：BasicDynaBean
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
	 * 由问卷项ID获得该问卷项类型 1、数字 2、单选 3、多选 4、文本
	 * @param itemId：问卷项ID
	 * @return：问卷项类型
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
	 * 获得某问卷项的问卷细则列表
	 * @param itemId：问卷项ID
	 * @return：问卷细则列表
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
	 * 由评分细则ID获得评分细则名称
	 * @param ruleId：评分细则ID
	 * @return：评分细则名称
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
	 * 由参评对象ID获得参评对象名称
	 * @param conId：参评对象ID
	 * @return：参评对象名称
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
	 * 由问卷项ID获得问卷项名称
	 * @param itemId：问卷项ID
	 * @return：问卷项名称
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
	 * 获得在某问卷中，该问题由某代维公司给某参评对象评分
	 * @param issueId：问卷ID
	 * @param comId：参评对象ID
	 * @param conId：代维公司ID
	 * @param itemId：问卷项ID
	 * @return：获得的分数
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
	 * 通过参评厂商的ID查询参评商名称
	 * @param comId：参评商ID
	 * @return：参评厂商名称
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
	 * 通过参评项ID和参评项选项查询参评项名称
	 * @param itemId：参评项ID
	 * @param options：参评项选项
	 * @return：差评项名称
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
	 * 获得查询问卷列表
	 * @param issueName：问卷名称
	 * @param beginTime：查询开始时间
	 * @param endTime：查询结束时间
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
	 * 当前时间增加一天
	 * @param strDate：增加前时间
	 * @return：增加后时间
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
	 * 由问卷ID获得问卷的详细信息
	 * @param issueId：问卷ID
	 * @return：问卷的详细信息
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
	 * 由问卷ID获得所有的代维公司名称
	 * @param issueId：问卷ID
	 * @return：代维公司名称
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
