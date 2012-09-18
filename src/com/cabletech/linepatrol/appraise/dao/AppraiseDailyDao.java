package com.cabletech.linepatrol.appraise.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;

/**
 * 日常考核操作类
 * 
 * @author liusq
 * 
 */
@Repository
public class AppraiseDailyDao extends HibernateDao<AppraiseDaily, String> {

	/**
	 * 获得日常考核的详细信息
	 * 
	 * @param bean
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DynaBean> getAppraiseDailyMarkInfo(AppraiseDailyBean bean) {
		String contractNo = bean.getContractNo();
		String businessModule = bean.getBusinessModule();
		String businessId = bean.getBusinessId();

		StringBuffer sql = new StringBuffer("");
		sql.append("select tab.id tab_id,tab.table_name,item.id item_id,item.item_name,''||item.weight item_weight,");
		sql.append("con.id con_id,con.content_description con_name,''||con.weight con_weight,ru.id rule_id,");
		sql.append("ru.rule_description rule_name,''||ru.weight ru_weight,da.id daily_id,mark.id mark_id,mark.remark,");
		sql.append("u.username,to_char(da.appraise_date,'yyyy-mm-dd') appraise_date ");
		sql.append("from lp_appraise_table tab,lp_appraise_item item,lp_appraise_content con,");
		sql.append("lp_appraise_rule ru,lp_appraise_daily da,lp_appraise_daily_mark mark,userinfo u ");
		sql.append("where tab.id=item.table_id and item.id=con.item_id and con.id=ru.content_id ");
		sql.append("and tab.id=da.table_id and da.id=mark.daily_id and ru.id=mark.rule_id and u.userid=da.appraiser ");
		//通过ID查询
		if(StringUtils.isNotBlank(bean.getId())){
			sql.append("and da.id=? ");
			sql.append("order by tab.id,item.id,con.id,ru.id");
			return getJdbcTemplate().queryForBeans(sql.toString(), bean.getId());
		}else{
			//通过其他条件查询
			sql.append("and da.business_module=? and da.business_id=? ");
			if (StringUtils.isNotBlank(contractNo)) {
				sql.append("and da.contract_no='");
				sql.append(contractNo);
				sql.append("' ");
			}
			sql.append("order by tab.id,item.id,con.id,ru.id");
			return getJdbcTemplate().queryForBeans(sql.toString(), businessModule,
					businessId);
		}
	}

	/**
	 * 通过条件查询日考核基本信息
	 * 
	 * @param bean
	 * @return
	 */
	public AppraiseDaily getAppraiseDailyByCondition(AppraiseDailyBean bean) {
		String contractNo = bean.getContractNo();
		String businessModule = bean.getBusinessModule();
		String businessId = bean.getBusinessId();

		String hql = "from AppraiseDaily daily where daily.businessModule=? and daily.businessId=? ";
		if (StringUtils.isNotBlank(contractNo)) {
			hql += "and daily.contractNo='" + contractNo + "'";
		}
		return super.findUnique(hql, businessModule, businessId);
	}
	/**
	 * 通过标包号和考核月份查询日常考核
	 * @param contractorId
	 * @param contractNo 标底包号
	 * @param month 考核月份
	 * @return
	 */
	public List<AppraiseDaily> getDailyByContractNoAndMonth(String contractorId, String contractNo, String month,String startDate,String endDate,String typeName) {
		String hql="from AppraiseDaily daily where 1=1";
		if(StringUtils.isNotBlank(contractorId)){
			hql += " and daily.contractorId='"+contractorId+"'";
		}
		if(StringUtils.isNotBlank(typeName)){
			hql+="  and daily.businessModule like '"+typeName+"%'";
		}
		if(StringUtils.isNotBlank(month)){
			if(typeName.equals(AppraiseConstant.TYPE_YEAREND)){
				hql+=" and daily.tableId=(select id from AppraiseTable where year='"+month+"' and type="+AppraiseConstant.APPRAISE_YEAREND+")";
			}else{
				hql+=" and to_char(daily.appraiseDate,'yyyy-MM')='"+month+"'";
			}
		}
		if(StringUtils.isNotBlank(startDate)&&StringUtils.isNotBlank(endDate)){
			hql+=" and daily.appraiseDate>=to_date('"+startDate+"','yyyy-MM-dd') and daily.appraiseDate<=to_date('"+endDate+"','yyyy-MM-dd')";
		}
//		if(module.equals(AppraiseDaily.BUSINESSMODULE_OTHER)){
//			hql += " and daily.businessModule = '"+AppraiseDaily.BUSINESSMODULE_OTHER+"'";
//		}
		if(StringUtils.isNotBlank(contractNo)){
			hql+=" and (daily.contractNo like '%"+contractNo+"%' or daily.contractNo is null)";//按照标包考核包括不限的，和选择的标包记录。
		}
		
		return find(hql);
	}

	public List<AppraiseDaily> getAppraiseDailyByBean(AppraiseDailyBean appraiseDailyBean,String type) {
		String hql="from AppraiseDaily daily where 1=1";
		if(StringUtils.isNotBlank(appraiseDailyBean.getContractorId())){
			hql+=" and contractorId='"+appraiseDailyBean.getContractorId()+"'";
		}
		if(StringUtils.isNotBlank(appraiseDailyBean.getContractNo())){
			hql+=" and contractNo='"+appraiseDailyBean.getContractNo()+"'";
		}
		if(StringUtils.isNotBlank(appraiseDailyBean.getAppraiser())){
			hql+=" and appraiser='"+appraiseDailyBean.getAppraiser()+"'";
		}
		if(StringUtils.isNotBlank(appraiseDailyBean.getYear())){
			hql+=" and tableId=(select id from AppraiseTable where year='"+appraiseDailyBean.getYear()+"' and type='4')";
		}
		hql+=" and businessModule like '"+type+"%'";
		return find(hql);
	}

}
