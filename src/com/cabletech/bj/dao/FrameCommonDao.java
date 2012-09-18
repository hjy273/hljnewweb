package com.cabletech.bj.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.web.WebAppUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.constant.CommonConstant;

public class FrameCommonDao extends HibernateDao {
	private static Logger logger = Logger.getLogger(FrameCommonDao.class
			.getName());

	/**
	 * 查询通讯录
	 * 
	 * @param user
	 * @param userName
	 * @param mobile
	 * @return
	 */
	public List getAddressList(UserInfo user, String departName,
			String userName, String mobile) {
		String regionid = user.getRegionid();
		StringBuffer sb = new StringBuffer();
		sb
				.append(" select u.username name,u.phone,d.deptname,u.position, d.deptid contractorid");
		sb.append(" from userinfo u,deptinfo d ");
		sb.append(" where u.deptid=d.deptid and u.deptype='1' ");
		if (userName != null && !userName.equals("")) {
			sb.append(" and u.username like '%" + userName + "%'");
		}
		if (departName != null && !departName.equals("")) {
			sb.append(" and d.deptname like '%" + departName + "%'");
		}
		if (mobile != null && !mobile.equals("")) {
			sb.append(" and u.phone like '%" + mobile + "%'");
		}
		sb.append(" and u.regionid='" + regionid + "'");
		sb.append(" and u.state is null");
//		sb.append(" union ");
//		sb
//				.append(" select u.username name,u.phone,c.contractorname deptname,u.position,c.contractorid ");
//		sb.append(" from userinfo u,contractorinfo c ");
//		sb.append(" where u.deptid=c.contractorid");
//		if (userName != null && !userName.equals("")) {
//			sb.append(" and u.username like '%" + userName + "%'");
//		}
//		if (departName != null && !departName.equals("")) {
//			sb.append(" and c.contractorname like '%" + departName + "%'");
//		}
//		if (mobile != null && !mobile.equals("")) {
//			sb.append(" and u.phone like '%" + mobile + "%'");
//		}
//		if ("2".equals(user.getDeptype())) {
//			sb.append(" and u.deptid='" + user.getDeptID() + "'");
//		} else {
//			sb.append(" and u.regionid='" + regionid + "'");
//		}
//		sb.append(" and u.state is null");
		sb.append(" union ");
		sb
				.append(" select c.name,c.mobile phone,con.contractorname deptname,c.jobinfo position,con.contractorid");
		sb.append("  from contractorperson c,contractorinfo con");
		sb.append("  where c.contractorid=con.contractorid");
		if (userName != null && !userName.equals("")) {
			sb.append(" and c.name like '%" + userName + "%'");
		}
		if (departName != null && !departName.equals("")) {
			sb.append(" and con.contractorname like '%" + departName + "%'");
		}
		if (mobile != null && !mobile.equals("")) {
			sb.append(" and c.mobile like '%" + mobile + "%'");
		}
		if ("2".equals(user.getDeptype())) {
			sb.append(" and c.contractorid='" + user.getDeptID() + "'");
		} else {
			sb.append(" and c.regionid='" + regionid + "'");
		}
		sb.append(" order by contractorid");
		QueryUtil query;
		try {
			query = new QueryUtil();
			logger.info(sb.toString());
			return query.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取代维单位
	 * 
	 * @param user
	 * @return
	 */
	public List getCons(UserInfo user) {
		String deptype = user.getDeptype();
		String sb = " select * from contractorinfo c ";
		if (deptype.equals("2")) {
			sb += " where c.contractorid='" + user.getDeptID() + "'";
		}
		if (deptype.equals("1")) {
			sb += " where c.regionid='" + user.getRegionID()
					+ "' and c.state is null";
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

	/**
	 * 获取在线巡检组
	 * 
	 * @param conid
	 *            代维单位id
	 * @return
	 */
	public List getPatrolGroup(String conid) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from patrolmaninfo p where p.state is null and p.parentid='"+ conid + "'");
//		sb.append("	and p.patrolid in (");
//		sb.append(" select t.ownerid from terminalinfo t,onlineman m where t.simnumber=m.simid");
//		sb.append(" and ROUND(TO_NUMBER(sysdate- m.activetime) * 24 *60) <="+ WebAppUtils.online_period + ")");
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
	 * 
	 * @param user
	 * @return
	 */
	public List getCons(UserInfo user, String orderString) {
		String deptype = user.getDeptype();
		String sb = " select * from contractorinfo c ";
		if (deptype.equals("2")) {
			sb += " where c.contractorid='" + user.getDeptID() + "'";
		}
		if (deptype.equals("1")) {
			sb += " where c.regionid='" + user.getRegionID()
					+ "' and c.state is null";
		}
		sb += orderString;
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

	/**
	 * 获取在线巡检组
	 * 
	 * @param conid
	 *            代维单位id
	 * @return
	 */
	public List getPatrolGroup(String conid, String orderString) {
		StringBuffer sb = new StringBuffer();
		sb
				.append(" select * from patrolmaninfo p where p.state is null and p.parentid='"
						+ conid + "'");
		sb.append("	and p.patrolid in (");
		sb
				.append(" select t.ownerid from terminalinfo t,onlineman m where t.simnumber=m.simid");
		sb.append(" and ROUND(TO_NUMBER(sysdate- m.activetime) * 24 *60) <="
				+ WebAppUtils.online_period + ")");
		sb.append(orderString);
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
	 * 获取代维巡检组个数
	 * 
	 * @param conid
	 *            代维单位id
	 * @return
	 */
	public int getPatrolGroupNum(String conid) {
		String sb = " select *  from patrolmaninfo p where p.parentid='"
				+ conid + "' and p.state is null";
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sb);
			if (list != null) {
				return list.size();
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取在线的sim
	 * 
	 * @param patrolId
	 *            巡检组id
	 * @return
	 */
	public List getPatrolSim(String patrolId) {
		StringBuffer sb = new StringBuffer();
		sb
				.append(" select t.simnumber simid,to_char(m.activetime,'HH24:MI') activetime, operate,t.holder ");
		sb
				.append("	from onlineman m,terminalinfo t where m.simid=t.simnumber ");
		sb.append(" and ROUND(TO_NUMBER(sysdate- m.activetime) * 24*60) <"
				+ WebAppUtils.online_period);
		sb.append(" and t.ownerid='" + patrolId + "'");
		logger.info("getPatrolSim =========== " + sb.toString());
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
	 * 查询巡检组下的sim卡数量
	 * 
	 * @param patrolId
	 *            巡检组id
	 * @return
	 */
	public int getPatrolSimNUM(String patrolId) {
		String sb = " select * from terminalinfo t where t.ownerid='"
				+ patrolId + "' and t.state is null";
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sb);
			if (list != null) {
				return list.size();
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取代维下sim卡个数
	 * 
	 * @param conid
	 *            代维单位id
	 * @return
	 */
	public int getSimNumByConid(String conid) {
		String sb = " select * from terminalinfo t"
				+ " where t.contractorid='" + conid + "' and t.state is null ";
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sb);
			if (list != null) {
				return list.size();
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取代维下巡检组下的sim卡个数
	 * 
	 * @param conid
	 *            代维单位id
	 * @return
	 */
	public int getPatrolSimNumByConid(String conid) {
		String sb = " select *  from patrolmaninfo p,terminalinfo t"
				+ " where p.parentid='" + conid + "' and p.state is null "
				+ "and t.ownerid=p.patrolid and t.state is null";
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sb);
			if (list != null) {
				return list.size();
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return 0;
	}

	public List getMeetInfo() {
		String sql = " select t.id,t.title,to_char(t.meet_time,'yyyymmdd') as meet_time ";
		sql += " from notice_clob t where type='会议' and t.isissue='y' and t.is_canceled='0' ";
		sql += " order by t.meet_time desc ";
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sql);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public List queryMailList(String condition) {
		// TODO Auto-generated method stub
		// 查询派给代维单位的任务派单
		String sql = " select t.id,t.mail_name,t.email_address,to_char(t.order_number) as order_number ";
		sql += " from user_mail_info t ";
		sql += " where 1=1 ";
		sql += condition;
		sql += " order by t.order_number,t.id ";
		logger.info("查询派单信息sql：" + sql);
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sql);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
}
