package com.cabletech.linepatrol.trouble.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleGuideYear;

@Repository
public class TroubleQuotaYearDAO extends HibernateDao<TroubleGuideYear, String> {



	/**
	 * 故障年统计
	 * @param type  指标类型
	 * @param year  统计年份
	 * @return
	 */
	public List getTimeAreaTroubleQuotaList(String contractorId,String quotaType,int beginYear,int numYear){
		List values = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append(" select g.id ," +
				"g.year ," +
				"g.GUIDE_TYPE,"+
				"g.maintenance_length," +
				"g.interdiction_norm_times," +
				"g.interdiction_dare_times," +
				"g.TROUBLE_TIMES trouble_times," +
				"g.interdiction_norm_time," +
				"g.interdiction_dare_time," +
				"g.interdiction_time interdiction_time ,"+
				"g.rtr_time_norm_value," +
				"g.rtr_time_dare_value," +
				"g.rtr_time_finish_value " 
			
				 );
		sb.append(" from lp_trouble_guide_year g  ");
		sb.append(" where   ");

		sb.append("  g.guide_type='"+quotaType+"' ");
		if(beginYear>0){
			sb.append(" and  g.year in ("+beginYear+" ");
		}
		for(int i=1;i<numYear+1;i++){
			sb.append(" ,"+(beginYear-i)+" ");
		}
		sb.append(" )");
		sb.append(" order by g.year ");
		List list =  getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		return list;
	}

	public List<TroubleGuideYear> queryAllQuota() {
		String hql = "from TroubleGuideYear order by guideType,year desc";
		return super.find(hql);
	}

	public List<TroubleGuideYear> isCreate(String guideType, String year) {
		String hql = "from TroubleGuideYear where guideType=? and year=?";
		return super.find(hql, guideType,year);
		
	}

	public TroubleGuideYear queryYearQuota(String id) {
		return super.get(id);
	}
	

}
