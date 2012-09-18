package com.cabletech.linepatrol.appraise.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;

@Repository
public class AppraiseResultDao extends HibernateDao<AppraiseResult, String> {
	public void saveAppraiseResult(AppraiseResult appraiseResult) {
		this.save(appraiseResult);
	}

	/**
	* 获得月考核所需信息
	* @param contractorId
	* @param bean
	* @return
	*/
	public List queryForAppraiseMonth(String contractorId, String theStartDate) {
		String sql = "  SELECT pm.patrolid,pm.patrolname executorid,ps.overallpatrolp patrolp,round((PS.PLANPOINTN-PS.FACTPOINTN)/ps.PLANPOINTN*100,2) factp,(SELECT COUNT ( * ) FROM plan_stat WHERE executorid = ps.executorid AND enddate <= LAST_DAY (TO_DATE ('"
				+ theStartDate
				+ "', 'yyyy-mm')) AND startdate >= TO_DATE ('"
				+ theStartDate
				+ "', 'YYYY-MM')) planNumber "
				+ "FROM plan_patrolstat ps,patrolmaninfo pm "
				+ "WHERE pm.patrolid = ps.executorid AND ps.contractorid = '"
				+ contractorId
				+ "' AND ps.factdate <= LAST_DAY (TO_DATE ('"
				+ theStartDate
				+ "', 'yyyy-mm')) AND ps.factdate >= TO_DATE ('" + theStartDate + "', 'YYYY-MM')";
		System.out.println(sql);
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List queryForAppraiseMonthAll(String contractorId, String theStartDate) {
		String sql = "select C.LOSS_PATROL_RATE lossP,C.PATROLP patrolP, (SELECT   COUNT ( * ) FROM plan_stat s,patrolmaninfo p WHERE s.executorid=p.patrolid and P.PARENTID=C.CDEPTID AND s.enddate <= LAST_DAY (TO_DATE ('"
				+ theStartDate
				+ "', 'yyyy-mm')) AND s.startdate >= TO_DATE ('"
				+ theStartDate
				+ "', 'YYYY-MM')) planNum from plan_cstat c where to_char(C.FACTDATE,'yyyy-MM')='"
				+ theStartDate
				+ "' and C.CDEPTID='" + contractorId + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 按条件查询考核结果
	 * @param appraiseResultBean
	 * @return
	 */
	public List<AppraiseResult> getAppraiseResultByBean(AppraiseResultBean appraiseResultBean) {
		String hql = "from AppraiseResult where 1=1";
		if (StringUtils.isNotBlank(appraiseResultBean.getStartDate())&&StringUtils.isNotBlank(appraiseResultBean.getTableId())) {
			if (StringUtils.isNotBlank(appraiseResultBean.getContractorId())) {
				hql += " and contractorId='" + appraiseResultBean.getContractorId() + "'";
			}
			hql += " and tableId='" + appraiseResultBean.getTableId() + "'";
		} else {
			if (StringUtils.isNotBlank(appraiseResultBean.getAppraiseTime())) {
				if(appraiseResultBean.getAppraiseTime().split("-").length>1){
					hql += " and to_char(appraiseTime,'yyyy-MM')='" + appraiseResultBean.getAppraiseTime() + "'";
				}else{
					hql +=" and tableId=(select id from AppraiseTable where year='"+appraiseResultBean.getAppraiseTime()+"' and type="+AppraiseConstant.APPRAISE_YEAREND+")";
				}
			}
			if(StringUtils.isNotBlank(appraiseResultBean.getType())){
				hql+=" and tableId in (select id from AppraiseTable where type='"+appraiseResultBean.getType()+"')";
			}
			if (StringUtils.isNotBlank(appraiseResultBean.getStartDate())) {
				hql += " and startDate>= to_date('" + appraiseResultBean.getStartDate()
						+ "-01-01','yyyy-MM-dd') and endDate<=to_date('" + appraiseResultBean.getEndDate()
						+ "-12-31','yyyy-MM-dd')";
			}
			if (StringUtils.isNotBlank(appraiseResultBean.getContractNO())) {
				hql += " and contractNO='" + appraiseResultBean.getContractNO() + "'";
			}
			if (StringUtils.isNotBlank(appraiseResultBean.getContractorId())) {
				hql += " and contractorId='" + appraiseResultBean.getContractorId() + "'";
			}
			if (StringUtils.isNotBlank(appraiseResultBean.getTableId())) {
				hql += " and tableId='" + appraiseResultBean.getTableId() + "'";
			}
			if (StringUtils.isNotBlank(appraiseResultBean.getConfirmResult())) {
				hql += " and confirmResult in (" + appraiseResultBean.getConfirmResult() + ")";
			}
			if (StringUtils.isNotBlank(appraiseResultBean.getId())) {
				hql += " and id='" + appraiseResultBean.getId() + "'";
			}
		}
		return find(hql);

	}

	/**
	 * 查询统计月考核
	 * @param appraiseResultBean
	 * @return
	 */
	public List<AppraiseResult> queryStat(AppraiseResultBean appraiseResultBean) {
		String hql = "from AppraiseResult where 1=1";
		if(StringUtils.isNotBlank(appraiseResultBean.getScore())){
			hql+=" and result" + appraiseResultBean.getScope() + appraiseResultBean.getScore();
		}
		
		if (StringUtils.isNotBlank(appraiseResultBean.getFromDate())) {
			hql += " and appraiseTime>= to_date('" + appraiseResultBean.getFromDate()
					+ "','yyyy-MM') and appraiseTime<=to_date('" + appraiseResultBean.getToDate() + "','yyyy-MM')";
		}
		if (StringUtils.isNotBlank(appraiseResultBean.getAppraiseTime())) {
			if(appraiseResultBean.getAppraiseTime().split("-").length>1){
				hql += " and to_char(appraiseTime,'yyyy-MM')='" + appraiseResultBean.getAppraiseTime() + "'";
			}else{
				hql +=" and tableId=(select id from AppraiseTable where year='"+appraiseResultBean.getAppraiseTime()+"' and type="+AppraiseConstant.APPRAISE_YEAREND+")";
			}
		}
		if (StringUtils.isNotBlank(appraiseResultBean.getStartDate())) {
			hql += " and startDate>= to_date('" + appraiseResultBean.getStartDate()
					+ "-01-01','yyyy-MM-dd') and endDate<=to_date('" + appraiseResultBean.getEndDate() + "-12-31','yyyy-MM-dd')";
		}
		if (StringUtils.isNotBlank(appraiseResultBean.getTableId())) {
			hql += " and tableId='" + appraiseResultBean.getTableId() + "'";
		}
		if (StringUtils.isNotBlank(appraiseResultBean.getContractorId())) {
			hql += " and contractorId=" + "'" + appraiseResultBean.getContractorId() + "'";
		}
		if (StringUtils.isNotBlank(appraiseResultBean.getFromDate())) {
			hql += " order by  contractorId,appraiseTime,contractNO";
		}else if (StringUtils.isNotBlank(appraiseResultBean.getStartDate())) {
			hql += " order by  tableId,contractorId,startDate";
		}else{
			hql += " order by contractorId,contractNO";
		}
		return find(hql);
	}

	/**
	 * 按条件查询日期范围内考核结果
	 * @param appraiseResultBean
	 * @return
	 */
	public List<AppraiseResult> getAppraiseResultInDate(AppraiseResultBean appraiseResultBean) {
		String hql = "from AppraiseResult where 1=1";
		if (StringUtils.isNotBlank(appraiseResultBean.getStartDate())) {
			hql += " and ((startDate<=to_date('" + appraiseResultBean.getStartDate()
					+ "','yyyy-MM-dd') and endDate>=to_date('" + appraiseResultBean.getStartDate()
					+ "','yyyy-MM-dd')) or ((startDate<=to_date('" + appraiseResultBean.getEndDate()
					+ "','yyyy-MM-dd') and endDate>=to_date('" + appraiseResultBean.getEndDate() + "','yyyy-MM-dd'))))";
		}
		if (StringUtils.isNotBlank(appraiseResultBean.getContractNO())) {
			hql += " and contractNO='" + appraiseResultBean.getContractNO() + "'";
		}
		if (StringUtils.isNotBlank(appraiseResultBean.getContractorId())) {
			hql += " and contractorId='" + appraiseResultBean.getContractorId() + "'";
		}
		if (StringUtils.isNotBlank(appraiseResultBean.getTableId())) {
			hql += " and tableId='" + appraiseResultBean.getTableId() + "'";
		}

		return find(hql);

	}
	/**
	 * 更新考核结果状态
	 * @param appraiseResultBean
	 */
	public void updateResultStateAndMark(AppraiseResultBean appraiseResultBean) {
		String sql="update lp_appraise_result set confirm_result='"+appraiseResultBean.getConfirmResult()+"'";
		sql+=" where id in ("+appraiseResultBean.getIds()+")";
		getJdbcTemplate().execute(sql);
	}
	/**
	 * 查询年终考核所需要的信息
	 * @param appraiseResultBean
	 * @return
	 */
	public List<Map<String, Object>> getYearResultByBean(AppraiseResultBean appraiseResultBean) {
		String sql = "SELECT SUM (appresult.result) / COUNT (appresult.id) AVG,apptable.TYPE TYPE FROM lp_appraise_result appresult,lp_appraise_table apptable WHERE contractor_id = '"
				+ appraiseResultBean.getContractorId()
				+ "' AND confirm_result = '"
				+ AppraiseConstant.STATE_VERIFY_PASS
				+ "' AND (contract_no = '"
				+ appraiseResultBean.getContractNO()
				+ "' OR contract_no IS NULL) and (year='"
				+ appraiseResultBean.getAppraiseTime()
				+ "' or (to_char(apptable.start_date,'yyyy') like '"
				+ appraiseResultBean.getAppraiseTime()
				+ "%' and to_char(apptable.end_date,'yyyy') like '"
				+ appraiseResultBean.getAppraiseTime()
				+ "%')) and apptable.id=appresult.table_id GROUP BY apptable.type";
		return this.getJdbcTemplate().queryForList(sql);
	}
	/**
	 * 获得一年的考核结果列表
	 * @param appraiseResultBean
	 * @param type
	 * @return
	 */
	public List<Map> queryApprasieResultInYear(AppraiseResultBean appraiseResultBean, String type) {
		String appraiseTime = appraiseResultBean.getAppraiseTime();
		String sql = "select appresult.id id,appresult.appraise_time appraiseTime,appresult.result result,apptable.table_name tableName,appresult.appraise_date appraiseDate,appresult.appraiser appraiser from lp_appraise_result appresult,lp_appraise_table apptable where appresult.table_id=apptable.id and (year='"
				+ appraiseTime
				+ "' or (to_char(apptable.start_date,'yyyy') = '"
				+ appraiseTime
				+ "' and to_char(apptable.end_date,'yyyy') = '"
				+ appraiseTime
				+ "')) and contractor_id='"
				+ appraiseResultBean.getContractorId()
				+ "' and (contract_no='"
				+ appraiseResultBean.getContractNO()
				+ "' OR contract_no IS NULL) and confirm_result='"
				+ AppraiseConstant.STATE_VERIFY_PASS
				+ "' and apptable.type='" + type + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
}
