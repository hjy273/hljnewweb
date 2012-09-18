package com.cabletech.linepatrol.appraise.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseYearResult;

@Repository
public class AppraiseYearResultDao extends HibernateDao<AppraiseYearResult, String> {
	/**
	 * 通过appraiseResultBean获得年度考核列表
	 * @param appraiseResultBean
	 * @return
	 */
	public List<AppraiseYearResult> getResultByAppraiseResultBean(AppraiseResultBean appraiseResultBean) {
		String hql = "from AppraiseYearResult where 1=1";
		if(StringUtils.isNotBlank(appraiseResultBean.getAppraiseTime())){
			hql+=" and year='" + appraiseResultBean.getAppraiseTime()+ "'";
		}
		if(StringUtils.isNotBlank(appraiseResultBean.getContractorId())){
			hql+=" and contractorId='" + appraiseResultBean.getContractorId() + "'";
		}
		if(StringUtils.isNotBlank(appraiseResultBean.getContractNO())){
			hql+=" and contractNO='"+ appraiseResultBean.getContractNO() + "'";
		}
		if(StringUtils.isNotBlank(appraiseResultBean.getConfirmResult())){
			hql+=" and confirmResult in("+appraiseResultBean.getConfirmResult()+")";
		}
		hql+=" order by contractorId,contractNO";
		return find(hql);
	}

	public void updateResultConfirmResult(AppraiseResultBean appraiseResultBean) {
		String sql="update lp_appraise_year_result set confirm_result='"+appraiseResultBean.getConfirmResult()+"'";
		sql+=" where id in ("+appraiseResultBean.getIds()+")";
		getJdbcTemplate().execute(sql);
	}

}
