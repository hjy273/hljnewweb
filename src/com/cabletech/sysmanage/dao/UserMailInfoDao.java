package com.cabletech.sysmanage.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.sysmanage.domainobjects.UserMailInfo;

@Repository
public class UserMailInfoDao extends HibernateDao<UserMailInfo, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public List queryForList(String condition) {
		// TODO Auto-generated method stub
		// 查询派给代维单位的任务派单
		String sql = " select t.id,t.mail_name,t.email_address, ";
		sql += " to_char(t.order_number) as order_number ";
		sql += " from user_mail_info t ";
		sql += " where 1=1 ";
		sql += condition;
		sql += " order by t.order_number,t.id ";
		logger.info("查询派单信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}
}
