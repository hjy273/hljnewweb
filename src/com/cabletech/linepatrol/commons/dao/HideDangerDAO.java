package com.cabletech.linepatrol.commons.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.module.ApproveInfo;

@Repository
public class HideDangerDAO extends HibernateDao<ApproveInfo, String> {
	/**
	 * 执行根据查询条件获取最近的隐患信息列表
	 * 
	 * @param condition
	 * @return
	 */
	public List queryList(String condition) {
		String sql = " select id,name,name||','||to_char(find_time,'yyyy-mm-dd') as hide_danger_info,'0' as is_checked ";
		sql += " from lp_hiddanger_regist t,contractorinfo c,region r ";
		sql += " where t.treat_department=c.contractorid and c.regionid=r.regionid ";
		sql += condition;
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}
}
