package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.beans.ApplyBean;
import com.cabletech.linepatrol.acceptance.model.Apply;

/**
 * 验收交维计划查询
 * 
 * @author liusq
 * 
 */

@Repository
public class AcceptancePlanQueryDao extends HibernateDao<Apply, String> {

	private static final String UNPASS = "0";
	private static final String PASSED = "1";
	
	/**
	 * 组合查询条件
	 * 
	 * @param bean
	 *            封装的查询条件
	 * @return 组合查询条件
	 */
	private String composeCondition(ApplyBean bean) {
		StringBuffer sb = new StringBuffer("");
		// 项目名称
		if (StringUtils.isNotBlank(bean.getName())) {
			sb.append(" and ap.name like '%");
			sb.append(bean.getName());
			sb.append("%'");
		}
		// 是否核准
		if (StringUtils.isNotBlank(bean.getIspassed())) {
			if (StringUtils.equals(bean.getIspassed(), UNPASS)) {
				sb.append(" and (not exists (select 1 from lp_acceptance_con con where con.apply_id=ap.id))");
			} else if (StringUtils.equals(bean.getIspassed(), PASSED)) {
				sb.append(" and (exists (select 1 from lp_acceptance_con con where con.apply_id=ap.id))");
			}
		}
		// 起始时间
		if (StringUtils.isNotBlank(bean.getBegintime())) {
			sb.append(" and ap.apply_date>=to_date('");
			sb.append(bean.getBegintime());
			sb.append("','yyyy/mm/dd')");
		}
		// 结束时间
		if (StringUtils.isNotBlank(bean.getEndtime())) {
			sb.append(" and ap.apply_date<=to_date('");
			sb.append(bean.getEndtime());
			sb.append("','yyyy/mm/dd')");
		}
		return sb.toString();
	}

	/**
	 * 根据查询条件查询结果
	 * 
	 * @param condition
	 * @return
	 */
	public List<DynaBean> queryAcceptancePlan(ApplyBean bean, UserInfo userInfo) {
		StringBuffer query = new StringBuffer("");
		query.append("select ap.id,ap.name,ap.code,ap.applicant,to_char(ap.apply_date,'yyyy-mm-dd') apply_date,ap.resource_type resource_type,");
		query.append("decode((select count(1) from lp_acceptance_con c where ap.id=c.apply_id),0,'can','not') can_modify ");
		query.append("from lp_acceptance_apply ap ");
		query.append("where ap.applicant='");
		query.append(userInfo.getUserID());
		query.append("'");
		String condition = composeCondition(bean);
		query.append(condition);
		logger.info("查询验收交维计划SQL：" + query.toString());
		return getJdbcTemplate().queryForBeans(query.toString());
	}
}
