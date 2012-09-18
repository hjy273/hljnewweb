package com.cabletech.sysmanage.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.sysmanage.domainobjects.UserLinkInfo;

@Repository
public class UserLinkInfoDao extends HibernateDao<UserLinkInfo, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public List queryForList(String condition) {
		// TODO Auto-generated method stub
		// 查询派给代维单位的任务派单
		String sql = " select t.id,t.link_name,t.link_address,t.link_type, ";
		sql += " to_char(t.order_number) as order_number ";
		sql += " from user_link_info t ";
		sql += " where 1=1 ";
		sql += condition;
		sql += " order by t.link_type,t.order_number,t.id ";
		logger.info("查询派单信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}
}
