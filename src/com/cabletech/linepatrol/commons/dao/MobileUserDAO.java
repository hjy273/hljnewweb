package com.cabletech.linepatrol.commons.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.module.ApproveInfo;

@Repository("mobileUserDao")
public class MobileUserDAO extends HibernateDao<ApproveInfo, String> {

	public List getMobileDeptList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select distinct c.deptid,c.deptid||'-'||c.deptname as deptinfo,c.deptname ";
		sql = sql + " from deptinfo c,userinfo u,usergourpuserlist ugl ";
		sql = sql + " where (c.state is null or c.state<>'1') ";
		sql = sql + " and c.deptid=u.deptid and u.userid=ugl.userid ";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

	public List getMobileUserList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select u.userid,u.deptid,u.phone as mobile,u.username as name,u.position,u.userid||'-'||u.username as userinfo ";
		sql = sql + " from userinfo u,deptinfo c,usergourpuserlist ugl ";
		sql = sql + " where u.deptid=c.deptid and u.userid=ugl.userid ";
		sql = sql + " and (c.state is null or c.state<>'1')";
		// sql = sql + " and (u.state is null or u.state<>'1') ";
		sql = sql + condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}

}
