package com.cabletech.linepatrol.quest.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.quest.module.QuestContractor;

@Repository
public class QuestContractorDao extends HibernateDao<QuestContractor, String> {
	/**
	 * ɾ��ĳ�ʾ�Ĳ�������
	 * @param issueId���ʾ�ID
	 */
	public void deleteQuestConByIssueId(String issueId){
		String sql = "delete from quest_contractor con where con.que_id='" + issueId + "'";
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * �ı��ʾ��������״̬
	 * @param issueId���ʾ�ID
	 * @param conId����������ID
	 * @param state��״̬
	 */
	public void changeStateByIssueIdAndConId(String issueId, String conId, String state){
		StringBuffer sb = new StringBuffer();
		sb.append("update quest_contractor t set t.state='");
		sb.append(state);
		sb.append("' where t.que_id='");
		sb.append(issueId);
		sb.append("' and t.contractor_id='");
		sb.append(conId);
		sb.append("'");
		String sql = sb.toString();
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * ����ʾ��������ID
	 * @param issueId���ʾ�ID
	 * @param conId����������ID
	 * @return���ʾ��������ID
	 */
	public String getIdByIssueIdAndConId(String issueId, String conId){
		String id = "";
		String sql = "select qcon.id from quest_contractor qcon where qcon.que_id=? and qcon.contractor_id=?";
		List list = getJdbcTemplate().queryForBeans(sql, issueId, conId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			id = (String)bean.get("id");
		}
		return id;
	}
	
	/**
	 * ���ʾ�ID�Ͳ�������ID����ʾ��������״̬
	 * @param issueId���ʾ�ID
	 * @param conId����������ID
	 * @return���ʾ��������״̬
	 */
	public String getStateByIssueIdAndConId(String issueId, String conId){
		String state = "";
		String sql = "select qcon.state from quest_contractor qcon where qcon.que_id=? and qcon.contractor_id=?";
		List list = getJdbcTemplate().queryForBeans(sql, issueId, conId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			state = (String)bean.get("state");
		}
		return state;
	}
	
	/**
	 * ���ʾ�ID����û�ID��name
	 * @param taskId���ʾ�ID
	 * @return
	 */
	public String[] getConUserIdsByTaskId(String issueId){
		String[] userIds = new String[4];
		String userId = "";
		String userName = "";
		String phone = "";
		String deptId = "";
		String sql = "select u.userid,u.username,u.phone,u.deptid from quest_contractor t,userinfo u where t.contractor_id=u.deptid and t.que_id='"+ issueId + "' order by u.deptid";
		List list = getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				userId += (String)bean.get("userid") + ",";
				phone += (String)bean.get("phone")  + ",";
				userName += (String)bean.get("username") + ",";
				deptId += (String)bean.get("deptid") + ",";
			}
		}
		if(userId != null && !"".equals(userId)){
			userId = userId.substring(0, userId.length() - 1);
		}
		if(phone != null && !"".equals(phone)){
			phone = phone.substring(0, phone.length() - 1);
		}
		if(userName != null && !"".equals(userName)){
			userName = userName.substring(0, userName.length() - 1);
		}
		if(deptId != null && !"".equals(deptId)){
			deptId = deptId.substring(0, deptId.length() - 1);
		}
		userIds[0] = userId;
		userIds[1] = phone;
		userIds[2] = userName;
		userIds[3] = removeDuplicateData(deptId);
		return userIds;
	}
	
	/**
	 * ȥ���ظ�������
	 * @param str
	 * @return
	 */
	private String removeDuplicateData(String str){
		List list = new ArrayList();
		String returnStr = "";
		if(str != null && !"".equals(str)){
			String[] strArray = str.split(",");
			for(int i = 0; i < strArray.length; i++){
				String value = strArray[i];
				if(list.contains(value)){
					continue;
				}else{
					list.add(value);
				}
			}
		}
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				returnStr += list.get(i) + ",";
			}
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
	}
	
	/**
	 * �ɴ�ά��λID��øô�ά��λ�µ������û�
	 * @param conId
	 * @return
	 */
	public String getUserIdByConId(String conId){
		String userId = "";
		String sql = "select t.userid from userinfo t where t.deptid=?";
		List list = getJdbcTemplate().queryForBeans(sql, conId);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				userId += (String)bean.get("userid") + ",";
			}
		}
		if(!"".equals(userId)){
			userId = userId.substring(0, userId.length() - 1);
		}
		return userId;
	}
}
