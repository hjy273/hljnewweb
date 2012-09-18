package com.cabletech.linepatrol.appraise.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseDailyMark;

@Repository
public class AppraiseDailyMarkDao extends HibernateDao<AppraiseDailyMark, String> {
	//查询日常考核记录。
	public Map<String, String> queryAppraiseDailyMark(String tableId, AppraiseResultBean appraiseResultBean, String typeName) {
		String hql = "select mark.ruleId,count(mark) from AppraiseDailyMark mark,AppraiseDaily daily where daily.id=mark.appraiseDaily and daily.businessModule like '"
				+ typeName + "%' and daily.tableId=? and daily.contractorId=?";
		if(typeName.equals("YearEnd")){
			hql+="";
		}else if (StringUtils.isNotBlank(appraiseResultBean.getAppraiseTime())) {
			hql += " and to_char(daily.appraiseDate,'yyyy-MM')='" + appraiseResultBean.getAppraiseTime() + "'";
		}else
		if (StringUtils.isNotBlank(appraiseResultBean.getStartDate()) && StringUtils.isNotBlank(appraiseResultBean.getEndDate())) {
			hql += " and daily.appraiseDate>=to_date('" + appraiseResultBean.getStartDate()
					+ "','yyyy-MM-dd') and daily.appraiseDate<=to_date('" + appraiseResultBean.getEndDate() + "','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(appraiseResultBean.getContractNO())) {
			hql += " and (daily.contractNo like '%" + appraiseResultBean.getContractNO() + "%' or daily.contractNo is null)";//按照标包考核包括不限的，和选择的标包记录。
		}
		hql += " group by mark.ruleId";
		Map<String, String> dailyMarks = new HashMap<String, String>();
		Query query = this.createQuery(hql, tableId, appraiseResultBean.getContractorId());
		setResultToMap(dailyMarks, query);
		return dailyMarks;
	}

	/**
	 * 按年查看考核结果记录
	 * @param tableId
	 * @param contractorId
	 * @param contractNO
	 * @param year
	 * @param typeName
	 * @return
	 */
	public List<Map> queryAppraiseDailyMarkDeductionByBean(String tableId, AppraiseResultBean appraiseResultBean, String typeName) {
		String sql = "select mark.rule_id as ruleId,SUM(mark.MARK_DEDUCTIONS)/COUNT(daily.id) as avgMarkDeduction from lp_appraise_daily_mark mark, lp_appraise_daily daily WHERE DAILY.ID = MARK.DAILY_ID AND DAILY.BUSINESS_MODULE LIKE '"
				+ typeName
				+ "%' AND DAILY.CONTRACTOR_ID = '"
				+ appraiseResultBean.getContractorId()
				+ "' and DAILY.CONTRACT_NO='"
				+ appraiseResultBean.getContractNO()
				+ "' AND daily.table_id in ( select id from lp_appraise_table where year= '" + appraiseResultBean.getAppraiseTime() + "' and type='4') GROUP BY   MARK.RULE_ID order by mark.rule_id";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 将查询结果保存到map中
	 * @param dailyMarks
	 * @param query
	 */
	private void setResultToMap(Map<String, String> dailyMarks, Query query) {
		Iterator iter = query.iterate();
		while (iter.hasNext()) {
			Object[] obj = (Object[]) iter.next();
			dailyMarks.put(obj[0].toString(), obj[1].toString());

		}
	}
	/**
	 * 通过dailyId查询dailyMark列表
	 * @param id
	 * @return
	 */
	public List<AppraiseDailyMark> queryDailyMarkByDailyId(String dailyId) {
		String hql="from AppraiseDailyMark where appraiseDaily.id=?";
		return find(hql,dailyId);
	}
}
