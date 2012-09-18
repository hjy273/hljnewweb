package com.cabletech.bj.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.ctf.dao.HibernateDao;


public class CommonDao {
	private static Logger logger = Logger.getLogger(CommonDao.class.getName());

	/**
	 * 查询通讯录
	 * @param user
	 * @param userName
	 * @param mobile
	 * @return
	 */
	public List getAddressList(UserInfo user,String userName,String mobile){
		String regionid = user.getRegionid();
		StringBuffer sb = new StringBuffer();
		sb.append(" select u.username name,u.phone,d.deptname,u.position, d.deptid contractorid");
		sb.append(" from userinfo u,deptinfo d ");
		sb.append(" where u.deptid=d.deptid");
		if(userName!=null && !userName.equals("")){
			sb.append(" and u.username like '%"+userName+"%'");
		}
		if(mobile!=null && !mobile.equals("")){
			sb.append(" and u.phone like '%"+mobile+"%'");
		}
		sb.append(" and u.regionid='"+regionid+"'");
		sb.append(" and u.state is null");
		sb.append(" union ");
		sb.append(" select u.username name,u.phone,c.contractorname deptname,u.position,c.contractorid ");
		sb.append(" from userinfo u,contractorinfo c ");
		sb.append(" where u.deptid=c.contractorid");
		if(userName!=null && !userName.equals("")){
			sb.append(" and u.username like '%"+userName+"%'");
		}
		if(mobile!=null && !mobile.equals("")){
			sb.append(" and u.phone like '%"+mobile+"%'");
		}
		sb.append(" and u.regionid='"+regionid+"'");
		sb.append(" and u.state is null");
		sb.append(" union ");
		sb.append(" select c.name,c.mobile phone,con.contractorname deptname,c.jobinfo position,con.contractorid");
		sb.append("  from contractorperson c,contractorinfo con");
		sb.append("  where c.contractorid=con.contractorid");
		if(userName!=null && !userName.equals("")){
			sb.append(" and c.name like '%"+userName+"%'");
		}
		if(mobile!=null && !mobile.equals("")){
			sb.append(" and c.mobile like '%"+mobile+"%'");
		}
		sb.append(" and c.regionid='"+regionid+"'");
		sb.append(" order by contractorid");
		QueryUtil query;
		try {
			query = new QueryUtil();
			return query.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取代维单位
	 * @param user
	 * @return
	 *//*
	public List getCons(UserInfo user){
		String deptype = user.getDeptype();
		String sb= " select * from contractorinfo c ";
		if(deptype.equals("2")){
			sb = " where c.contractorid='"+user.getDeptID()+"'";
		}
		if(deptype.equals("1")){
			sb = " where c.regionid='"+user.getRegionID()+"' and c.state is null";
		}
		QueryUtil query;
		try {
			query = new QueryUtil();
			return query.queryBeans(sb);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	*//**
	 * 获取巡检组
	 * @param conid 代维单位id
	 * @return
	 *//*
	public List getPatrolGroup(String conid){
		//String sb = " select * from patrolmaninfo p where p.parent='"+conid+"' and p.state is null";
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from patrolmaninfo p where p.state is null and p.parent='"+conid+"'");
		sb.append("	and p.patrolid in (");
		sb.append(" select t.ownerid from terminalinfo t,onlineman m where t.simnumber=m.simid");
		sb.append(" and ROUND(TO_NUMBER(sysdate- m.activetime) * 24) <"+OnLineBO.ONLINE_PERIOD+")");
		QueryUtil query;
		try {
			query = new QueryUtil();
			return query.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	*//**
	 * 获取sim
	 * @param patrolId 巡检组id
	 * @return
	 *//*
	public List getPatrolSim(String patrolId){
		// sb = " select * from patrolman_son_info patrol where patrol.parent_patrol='"+patrolId+"' and p.state is null";
		StringBuffer sb = new StringBuffer();
		sb.append(" select t.simnumber simid,to_char(m.activetime,'HH24:MI:SS') activetime, operate ");
		sb.append("	from onlineman m,terminalinfo t where m.simid=t.simnumber ");
		sb.append(" and ROUND(TO_NUMBER(sysdate- m.activetime) * 24) <"+OnLineBO.ONLINE_PERIOD+")");
		sb.append(" and t.ownerid='"+patrolId+"'");
		QueryUtil query;
		try {
			query = new QueryUtil();
			return query.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}*/

}
