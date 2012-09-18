package com.cabletech.linepatrol.safeguard.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardCon;

@Repository
public class SafeguardConDao extends HibernateDao<SafeguardCon, String> {

	/**
	 * ���±����������ά��ϵ���е�״̬
	 * 
	 * @param taskId
	 *            ����������ID
	 * @param conId
	 *            ����ά��λID
	 * @param state
	 *            ��״̬
	 */
	public void setStateByTaskIdAndConId(String taskId, String conId,
			String state) {
		String hql = "from SafeguardCon con where con.safeguardId=? and con.contractorId=?";
		List list = this.getHibernateTemplate().find(hql,
				new String[] { taskId, conId });
		logger.info("****taskId:" + taskId + "****conId:" + conId);
		if (list != null && list.size() > 0) {
			SafeguardCon safeguardCon = (SafeguardCon) list.get(0);
			safeguardCon.setTransactState(state);
			this.save(safeguardCon);
		} else {
			logger.info("��ѯ����Ϊ�գ�");
		}
	}

	/**
	 * �ɱ�������ID�ʹ�ά��λID��ñ��Ϻʹ�ά��ϵ��ID
	 * 
	 * @param taskId
	 *            ����������ID
	 * @param conId
	 *            ����ά��λID
	 * @return
	 */
	public String getIdByConIdAndTaskId(String taskId, String conId) {
		String hql = "from SafeguardCon safeguardcon where safeguardcon.safeguardId=? and safeguardcon.contractorId=?";
		logger.info("******contractorId:" + conId + "**taskId:" + taskId);
		List list = this.getHibernateTemplate().find(hql,
				new String[] { taskId, conId });
		if (list != null && list.size() > 0) {
			SafeguardCon safeguardCon = (SafeguardCon) list.get(0);
			return safeguardCon.getId();
		} else {
			logger.error("��ѯlistΪ�գ�");
			return "";
		}
	}
	/**
	 * û��ʹ���أ�
	 * @param taskId
	 * @param conId
	 * @return
	 */
	public SafeguardCon getObjectByConIdAndTaskId(String taskId, String conId) {
		String hql = "from SafeguardCon safeguardcon where safeguardcon.safeguardId=? and safeguardcon.contractorId=?";
		logger.info("******contractorId:" + conId + "**taskId:" + taskId);
		List<SafeguardCon> list = find(hql,new Object[] { taskId, conId });
		if (list != null && list.size() > 0) {
			if(list.size()>1){
				logger.info("���ؽ������1,size:"+list.size());	
			}
			return (SafeguardCon) list.get(0);
		} else {
			logger.error("��ѯlistΪ�գ�");
			return null;
		}
	}
	//����������ҵ����еĴ�ά
	public List<SafeguardCon> getObjectByTaskId(String taskId) {
		String hql = "from SafeguardCon safeguardcon where safeguardcon.safeguardId=? ";
		return find(hql,new Object[] { taskId});
	}
	/**
	 * ����û�ID
	 * 
	 * @param conId
	 * @param conUser
	 * @return
	 */
	public String getUserStrByConId(String conId, String conUser) {
		String userIds = "";
		if (conUser != null && !"".equals(conUser)) {
			String[] conUsers = conUser.split(",");
			for (int i = 0; i < conUsers.length; i++) {
				String[] conUserOne = conUsers[i].split(";");
				if (conUserOne[0].equals(conId)) {
					userIds += conUserOne[1] + ",";
				}
			}
		}
		if (!"".equals(userIds)) {
			userIds = userIds.substring(0, userIds.length() - 1);
		}
		return userIds;
	}

	/**
	 * ͨ����������IDɾ�������������ά��ϵ����Ϣ
	 * 
	 * @param taskId
	 *            ����������ID
	 */
	public void delteTaskConByTaskId(String taskId) {
		List list = this.findBy("safeguardId", taskId);
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SafeguardCon safeguardTaskCon = (SafeguardCon) iterator.next();
				this.delete(safeguardTaskCon);
			}
		}
	}

	/**
	 * �ɱ��Ϸ���ID��ñ����������ά��ϵID
	 * 
	 * @param planId
	 *            �����Ϸ���ID
	 * @return
	 */
	public String getConIdByPlanId(String planId) {
		String conId = "";
		String sql = "select con.id con_id from lp_safeguard_con con,lp_safeguard_task task, lp_safeguard_scheme plan "
				+ "where con.contractor_id=plan.contractor_id and task.id=con.safeguard_id and task.id=plan.safeguard_id and plan.id=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, planId);
		if (list != null && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			conId = (String) bean.get("con_id");
		}
		return conId;
	}

	/**
	 * �ɱ����ܽ�ID��ñ����������ά��ϵ��ID
	 * 
	 * @param summaryId
	 *            �������ܽ�ID
	 * @return
	 */
	public String getConIdBySummaryId(String summaryId) {
		String conId = "";
		String sql = "select con.id con_id from lp_safeguard_con con,lp_safeguard_task task, lp_safeguard_scheme plan,lp_safeguard_sum summary "
				+ "where con.contractor_id=plan.contractor_id and task.id=con.safeguard_id and task.id=plan.safeguard_id "
				+ "and plan.id=summary.scheme_id and summary.id=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, summaryId);
		if (list != null && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			conId = (String) bean.get("con_id");
		}
		return conId;
	}

	/**
	 * �ɱ�������ID�ʹ�ά��λID��ñ����������ά��ϵID
	 * 
	 * @param taskId
	 *            ����������ID
	 * @param contractorId
	 *            ����ά��λID
	 * @return
	 */
	public String getConIdByTaskIdAndConId(String taskId, String contractorId) {
		String conId = "";
		String sql = "select t.id con_id from lp_safeguard_con t where t.safeguard_id=? and t.contractor_id=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, taskId,
				contractorId);
		if (list != null && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			conId = (String) bean.get("con_id");
		}
		return conId;
	}

	/**
	 * ����Ѳ�ƻ�ID��ñ����������ά��ϵID
	 * 
	 * @param spId
	 *            ����Ѳ�ƻ�ID
	 * @return
	 */
	public String getConIdBySpecialPlanId(String spId) {
		String conId = "";
		StringBuffer sb = new StringBuffer();
		sb
				.append("select con.id con_id from lp_safeguard_task task,lp_safeguard_con con,lp_safeguard_scheme sch,lp_safeguard_plan plan,lp_special_plan sp");
		sb
				.append(" where sp.id=? and sp.id=plan.plan_id and plan.scheme_id=sch.id and sch.contractor_id=con.contractor_id");
		sb.append(" and sch.safeguard_id=task.id");
		String sql = sb.toString();
		List list = getJdbcTemplate().queryForBeans(sql, spId);
		if (list != null && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			conId = (String) bean.get("con_id");
		}
		return conId;
	}

	/**
	 * �ɱ�������ID����û�ID��name
	 * 
	 * @param taskId
	 *            ����������ID
	 * @return
	 */
	public String[] getConUserIdsByTaskId(String taskId) {
		String[] userIds = new String[4];
		String userId = "";
		String userName = "";
		String phone = "";
		String deptId = "";
		String sql = "select u.userid,u.username,u.phone,u.deptid from lp_safeguard_con t,userinfo u where t.contractor_id=u.deptid and t.safeguard_id='"
				+ taskId + "' order by u.deptid";
		List list = getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				userId += (String) bean.get("userid") + ",";
				phone += (String) bean.get("phone") + ",";
				userName += (String) bean.get("username") + ",";
				deptId += (String) bean.get("deptid") + ",";
			}
		}
		if (userId != null && !"".equals(userId)) {
			userId = userId.substring(0, userId.length() - 1);
		}
		if (phone != null && !"".equals(phone)) {
			phone = phone.substring(0, phone.length() - 1);
		}
		if (userName != null && !"".equals(userName)) {
			userName = userName.substring(0, userName.length() - 1);
		}
		if (deptId != null && !"".equals(deptId)) {
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
	 * 
	 * @param str
	 * @return
	 */
	private String removeDuplicateData(String str) {
		List list = new ArrayList();
		String returnStr = "";
		if (str != null && !"".equals(str)) {
			String[] strArray = str.split(",");
			for (int i = 0; i < strArray.length; i++) {
				String value = strArray[i];
				if (list.contains(value)) {
					continue;
				} else {
					list.add(value);
				}
			}
		}
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				returnStr += list.get(i) + ",";
			}
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
	}

	/**
	 * �ɴ�ά��λID��øô�ά��λ�µ������û�
	 * 
	 * @param conId
	 * @return
	 */
	public String getUserIdByConId(String conId) {
		String userId = "";
		String sql = "select t.userid from userinfo t where t.deptid=?";
		List list = getJdbcTemplate().queryForBeans(sql, conId);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				userId += (String) bean.get("userid") + ",";
			}
		}
		if (!"".equals(userId)) {
			userId = userId.substring(0, userId.length() - 1);
		}
		return userId;
	}

	public List getIdsByConIdAndTaskId(String taskId) {
		// TODO Auto-generated method stub
		String hql = "from SafeguardCon safeguardcon where safeguardcon.safeguardId=?";
		logger.info("********taskId:" + taskId);
		List list = this.getHibernateTemplate().find(hql,
				new String[] { taskId });
		return list;
	}
}
