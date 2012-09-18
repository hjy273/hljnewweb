package com.cabletech.linepatrol.safeguard.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;

@Repository
public class SafeguardSpplanDao extends HibernateDao<SafeguardSpplan, String> {
	/**
	 * �ɱ��ϼƻ�ID�����Ѳ�ƻ�ID�б�
	 * @param planId
	 * @return
	 */
	public List getSafeguardSpplanByPlanId(String planId){
		List list = this.findByProperty("planId", planId);
		return list;
	}
	
	/**
	 * ����Ѳ�ƻ�IDɾ�����Ϸ�����Ϣ
	 * @param spplanId����Ѳ�ƻ�ID
	 */
	public void delteSafeguardSpplanBySsplanId(String spplanId){
		String sql = "delete from lp_safeguard_plan where plan_id='" + spplanId + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * �ɱ��Ϸ���ID����Ѳ�ƻ�ID��ñ��Ϸ�������Ѳ�ƻ���ϵID
	 * @param safeguardId�����Ϸ���ID
	 * @param planId����Ѳ�ƻ�ID
	 * @return
	 */
	public List findBySafeguardAndSp(String safeguardId, String planId){
		String sql = "select id from lp_safeguard_plan where scheme_id='" + safeguardId + "' and plan_id='" + planId + "'";
		logger.info("findBySafeguardAndSp:" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		return list;
	}
	
	/**
	 * ����Ѳ�ƻ�ID���Ѳ����ID
	 * @param spId����Ѳ�ƻ�ID
	 * @return
	 */
	public String getPatrolGroupIdBySpId(String spId){
		String patrolGroupId = null;
		String patrolName = null;
		String sql = "select patrol_group_id from lp_special_circuit where plan_id='" + spId + "'";
		List list1 = this.getJdbcTemplate().queryForBeans(sql);
		if(list1 != null && list1.size() > 0){
			BasicDynaBean bdb = (BasicDynaBean)list1.get(0);
			patrolGroupId = (String)bdb.get("patrol_group_id");
		}
		if(patrolGroupId == null || "".equals(patrolGroupId)){
			sql = "select patrol_group_id from lp_special_watch where plan_id='" + spId + "'";
			list1 = this.getJdbcTemplate().queryForBeans(sql);
			if(list1 != null && list1.size() > 0){
				BasicDynaBean bdb = (BasicDynaBean)list1.get(0);
				patrolGroupId = (String)bdb.get("patrol_group_id");
			}
		}
		if(patrolGroupId != null && !"".equals(patrolGroupId)){
			sql = "select patrolname from patrolmaninfo where patrolid='" + patrolGroupId + "'";
			list1 = this.getJdbcTemplate().queryForBeans(sql);
			if(list1 != null && list1.size() > 0){
				BasicDynaBean bdb = (BasicDynaBean)list1.get(0);
				patrolName = (String)bdb.get("patrolname");
			}
		}
		return patrolName;
	}
	
	/**
	 * ����Ѳ�ƻ�ID��ñ��Ϸ���ID
	 * @param spId����Ѳ�ƻ�ID
	 * @return
	 */
	public String getPlanIdBySpId(String spId){
		SafeguardSpplan safeguardSpplan = this.findByUnique("spplanId", spId);
		if(safeguardSpplan != null && !"".equals(safeguardSpplan)){
			return safeguardSpplan.getPlanId();
		}
		return null;
	}
}
