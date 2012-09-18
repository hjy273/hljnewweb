package com.cabletech.linepatrol.appraise.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.appraise.beans.AppraiseMonthStatBean;
import com.cabletech.linepatrol.appraise.module.AppraiseMonthStat;

/**
 * 月考核统计操作类
 * 
 * @author liusq
 * 
 */
@Repository
public class AppraiseMonthStatDao extends HibernateDao<AppraiseMonthStat, String> {

	/**
	 * 月考核统计
	 * @param bean
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DynaBean> monthStatAppraise(AppraiseMonthStatBean bean) {
		StringBuffer sb = new StringBuffer("");
		sb.append("select t.id,t.contractor_id,c.contractorname,t.contract_no,");
		sb.append("to_char(t.appraise_time,'yyyy-mm') appraise_time,''||t.result result ");
		sb.append("from lp_appraise_result t,contractorinfo c ");
		sb.append("where t.contractor_id=c.contractorid ");
//		//开始时间
//		if(StringUtils.isNotBlank(bean.getStartDate())){
//			sb.append("and t.appraise_date>to_date('");
//			sb.append(bean.getStartDate());
//			sb.append("-01 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
//		}
//		//结束时间
//		if(StringUtils.isNotBlank(bean.getEndDate())){
//			sb.append("and t.appraise_date<to_date('");
//			sb.append(bean.getEndDate());
//			sb.append("-01 00:00:00','yyyy-mm-dd hh24:mi:ss') ");
//		}
		if(StringUtils.isNotBlank(bean.getStartDate())&&StringUtils.isNotBlank(bean.getEndDate())){
			sb.append("and t.appraise_time>to_date('"+bean.getStartDate()+"','yyyy-MM') and t.appraise_time<to_date('"+bean.getEndDate()+"','yyyy-MM')");
		}
		//考核分数
		if(StringUtils.isNotBlank(bean.getScore())){
			sb.append("and t.result");
			sb.append(bean.getScoreType());
			sb.append(bean.getScore());
			sb.append(" ");
		}
		//代维公司
		if(StringUtils.isNotBlank(bean.getContractorId())){
			sb.append("and t.contractor_id='");
			sb.append(bean.getContractorId());
			sb.append("' ");
		}
		sb.append("order by t.contractor_id,t.appraise_time");
		return getJdbcTemplate().queryForBeans(sb.toString());
	}

}
