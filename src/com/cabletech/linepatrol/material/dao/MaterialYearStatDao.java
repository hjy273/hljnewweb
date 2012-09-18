package com.cabletech.linepatrol.material.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MaterialYearStat;

@Repository
public class MaterialYearStatDao extends HibernateDao<MaterialYearStat, String> {
	private static Logger logger = Logger.getLogger(MaterialYearStatDao.class.getName());
	
	/**
	 * 移动查询代维
	 * @param user
	 * @return
	 */
	public List getConsByDeptId(UserInfo user){
		String sql = "select c.contractorid,c.contractorname from contractorinfo c" +
				" where c.state is null and c.depttype=2 and c.regionid=?";
		logger.info("regionId:" + user.getRegionID());
		logger.info("移动查询代维:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, user.getRegionID());
	}	
	
	
	/**
	 * 材料年统计
	 * @return
	 */
	public List statYearMT(String contraid,String year){
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct m.materialid,mm.modelname,mt.typename,mm.unit,sum(m.materialcount) as use_number,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),1, m.materialcount,0)) jan,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),2, m.materialcount,0)) feb,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),3, m.materialcount,0)) mar,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),4, m.materialcount,0)) apr,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),5, m.materialcount,0)) may,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),6, m.materialcount,0)) june,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),7, m.materialcount,0)) july,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),8, m.materialcount,0)) aug,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),9, m.materialcount,0)) sep,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),10, m.materialcount,0)) oct,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),11, m.materialcount,0)) nov,");
		sb.append(" sum(decode(ceil(to_char(remedy.remedydate,'mm')),12, m.materialcount,0)) dece");
	//	sb.append(" from LP_remedy_material m,LP_mt_base mb,LP_mt_model mm,");//修缮申请材料使用情况
		sb.append(" from LP_REMEDY_BAL_MATERIAL m,LP_mt_base mb,LP_mt_model mm,");//修缮结算材料使用情况
		sb.append(" LP_mt_type mt,LP_remedy remedy ");
		sb.append(" where m.materialid=mb.id and mb.modelid=mm.id and mm.typeid=mt.id and m.remedyid=remedy.id");
		sb.append(" and remedy.state='401'");
		if(year!=null && !year.trim().equals("")){
			sb.append(" and to_char(remedy.remedydate,'yyyy') like '"+year+"'");
		}
		sb.append(" and remedy.contractorid='"+contraid+"'");
		sb.append(" group by typename,m.materialid,modelname,unit");
		logger.info("材料年统计："+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}
}
