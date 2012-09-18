package com.cabletech.linepatrol.overhaul.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.overhaul.beans.OverHaulExamBean;
import com.cabletech.linepatrol.overhaul.model.Constant;
import com.cabletech.linepatrol.overhaul.model.OverHaulApply;

@Repository
public class OverHaulExamDao extends HibernateDao<OverHaulApply, String> {

	/**
	 * 根据查询条件查询待考核的列表
	 * @param bean
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DynaBean> getExamListByCondition(OverHaulExamBean bean, UserInfo userInfo) {
		StringBuffer sb = new StringBuffer("");
		sb.append("select o.id task_id,o.project_name,to_char(o.start_time,'yyyy-mm-dd hh24:mi:ss') start_time,");
		sb.append("to_char(o.end_time,'yyyy-mm-dd hh24:mi:ss') end_time,o.budget_fee,o.project_remark,");
		sb.append("to_char(o.create_time,'yyyy-mm-dd hh24:mi:ss') create_time,p.id apply_id,");
		sb.append("p.contractorid,c.contractorname,p.fee,p.state,o.creator,u.username ");
		sb.append("from lp_overhaul o,lp_overhaul_apply p,contractorinfo c,userinfo u ");
		sb.append("where o.id=p.task_id and p.contractorid=c.contractorid and o.creator=u.userid ");
		sb.append("and o.creator='");
		sb.append(userInfo.getUserID());
		sb.append("' and p.state='");
		sb.append(Constant.PASS);
		sb.append("' ");
		//大修项目名称
		if(StringUtils.isNotBlank(bean.getProjectName())){
			sb.append("and o.project_name like '%");
			sb.append(bean.getProjectName());
			sb.append("%' ");
		}
		sb.append("order by p.id desc");
		return getJdbcTemplate().queryForBeans(sb.toString());
	}
}
