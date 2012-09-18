package com.cabletech.linepatrol.commons.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.module.ApproveInfo;

@Repository("userInfoDao")
public class UserInfoDAOImpl extends HibernateDao<ApproveInfo, String> {
	public String getDepartTableSql() {
		String sql = " ( ";
		sql += " select deptid as departid,deptname as departname,regionid,state ";
		sql += " from deptinfo ";
		sql += " union ";
		sql += " select contractorid as departid,contractorname as departname,regionid,state ";
		sql += " from contractorinfo ";
		sql += " ) ";
		return sql;
	}

	/**
	 * 执行根据查询条件获取用户信息列表
	 * 
	 * @param condition
	 * @return
	 */
	public List queryList(String condition) {
		String sql = " select distinct u.userid,u.username,u.phone,u.position,u.username||'-'||u.phone as userinfo, ";
		sql = sql + " '0X' as is_checked,'0X' as is_reader_checked ";
		sql = sql + " from userinfo u,usergourpuserlist ugl ";
		sql = sql + " where (u.state is null or u.state<>'1') ";
		sql = sql + " and u.userid=ugl.userid ";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	public List getUserDepartList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select departid,departid||'-'||departname as departinfo,departname ";
		sql = sql + " from " + getDepartTableSql() + " d ";
		sql = sql + " where (state is null or state<>'1') ";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	public List getUserList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select u.userid,u.deptid,u.phone as mobile,u.username as name,u.position,u.userid||'-'||u.username as userinfo ";
		sql = sql + " from userinfo u," + getDepartTableSql() + " d ";
		sql = sql + " where u.deptid=d.departid ";
		sql = sql + " and (d.state is null or d.state<>'1')";
		sql = sql + " and (u.state is null or u.state<>'1') ";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	public List getDepartList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select d.departid,d.departname ";
		sql = sql + " from " + getDepartTableSql() + " d ";
		sql = sql + " where (d.state is null or d.state<>'1')";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	public List getMobileUserAndContractorPersonList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select u.userid,u.deptid,u.phone as mobile,u.username as name,u.position,u.userid||'-'||u.username as userinfo ";
		sql = sql + " from userinfo u,deptinfo d ";
		sql = sql + " where u.deptid=d.deptid ";
		sql = sql + " and (d.state is null or d.state<>'1')";
		sql = sql + " and (u.state is null or u.state<>'1') ";
		sql = sql + condition;
		sql = sql + " union ";
		sql = sql
				+ " select u.id as userid,u.contractorid as deptid,u.mobile, u.name,u.jobinfo as position,u.id||'-'||u.name as userinfo ";
		sql = sql + " from contractorperson u,contractorinfo d ";
		sql = sql + " where u.contractorid=d.contractorid ";
		sql = sql + " and (d.state is null or d.state<>'1')";
		sql = sql + " and (u.state is null or u.state<>'1') ";
		sql = sql + " and (u.issendsms='1') ";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	/**
	 * 执行根据用户编号获取用户名称信息
	 * 
	 * @param userId
	 *            状态编号
	 * @return String 代维单位名称信息
	 * @throws Exception
	 */
	public String getUserName(String userId) {
		String sql = "select username from userinfo where userid='" + userId
				+ "'";
		List list = super.getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			DynaBean bean = (DynaBean) list.get(0);
			String userName = (String) bean.get("username");
			return userName;
		}
		return "";
	}

	/**
	 * 通过id查找用户信息
	 * 
	 * @param id
	 *            String
	 * @return UserInfo
	 * @throws Exception
	 */
	public DynaBean findByUserAndContractorId(String id) throws Exception {
		String sql = " select u.userid,u.deptid,u.phone as mobile,u.username as name,u.position,u.userid||'-'||u.username as userinfo ";
		sql = sql + " from userinfo u,deptinfo d ";
		sql = sql + " where u.deptid=d.deptid ";
		sql = sql + " and (d.state is null or d.state<>'1')";
		sql = sql + " and (u.state is null or u.state<>'1') ";
		sql = sql + " and u.userid='" + id + "' ";
		sql = sql + " union ";
		sql = sql
				+ " select u.id as userid,u.contractorid as deptid,u.mobile, u.name,u.jobinfo as position,u.id||'-'||u.name as userinfo ";
		sql = sql + " from contractorperson u,contractorinfo d ";
		sql = sql + " where u.contractorid=d.contractorid ";
		sql = sql + " and (d.state is null or d.state<>'1')";
		sql = sql + " and (u.state is null or u.state<>'1') ";
		sql = sql + " and (u.issendsms='1') ";
		sql = sql + " and u.id='" + id + "' ";
		List list = super.getJdbcTemplate().queryForBeans(sql);
		if (list != null && !list.isEmpty()) {
			return (DynaBean) list.get(0);
		}
		return null;
	}
	
	/**
	 * 通过用户Id查询用户部门Id
	 * @param userId
	 * @return
	 */
	public String getDeptIdByUserId(String userId){
		String deptId = "";
		String sql = "select u.deptid from userinfo u where u.userid='" + userId + "'";
		List<DynaBean> list = getJdbcTemplate().queryForBeans(sql);
		if (list != null && !list.isEmpty()) {
			DynaBean bean = (DynaBean)list.get(0);
			deptId = (String)bean.get("deptid");
		}
		return deptId;
	}

}
