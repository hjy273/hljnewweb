package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;


public class RemedyDistriStatDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(RemedyDistriStatDao.class.getName());
	
	/**
	 * 移动查询代维
	 * @param user
	 * @return
	 */
	public List getConsByDeptId(UserInfo user){
		List list = new ArrayList();
		QueryUtil util = null;
		String sql = "select c.contractorid,c.contractorname from contractorinfo c" +
		" where c.state is null and c.depttype=2 and c.regionid='"+user.getRegionID()+"'";
		try {
			util = new QueryUtil();
			logger.info("移动查询代维："+sql);
			list = util.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}	
	
	/**
	 * 查询分布统计总表
	 * @param user
	 * @return
	 */
	public List getDistriReports(String contractorid,String startmonth,String endmonth){
		List list = new ArrayList();
		QueryUtil util = null;
		try {
			StringBuffer sb = new StringBuffer();
			//sb.append(" select distinct con.contractorid,con.contractorname");
			sb.append(" select distinct town.town, con.contractorid,con.contractorname,to_char(sum(bla.totalfee)) totalfee ");
			sb.append(" from linepatrol_remedy remedy,linepatrol_remedy_balance bla,");
			sb.append(" linepatrol_towninfo town,contractorinfo con");
			sb.append(" where remedy.id=bla.remedyid and town.id=remedy.townid ");
			sb.append(" and con.contractorid=remedy.contractorid");
			sb.append(" and to_char(remedy.remedydate,'yyyy-MM')>='"+startmonth+"'");
			sb.append(" and to_char(remedy.remedydate,'yyyy-MM')<='"+endmonth+"'");
			if(contractorid!=null && !contractorid.equals("")){
				sb.append(" and remedy.contractorid='"+contractorid+"'");
			}
			sb.append(" group by town.town, con.contractorid,con.contractorname");
			sb.append("  order by con.contractorid");
			util = new QueryUtil();
			logger.info("查询分布统计总表："+sb.toString());
			list = util.queryBeans(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}	
	
	/**
	 * 查询分布统计总表所包含的代维
	 * @param user
	 * @return
	 */
	public List getDistriReportCon(String contractorid,String startmonth,String endmonth){
		List list = new ArrayList();
		QueryUtil util = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct con.contractorid,con.contractorname ");
			sb.append(" from linepatrol_remedy remedy,linepatrol_remedy_balance bla,");
			sb.append(" linepatrol_towninfo town,contractorinfo con");
			sb.append(" where remedy.id=bla.remedyid and town.id=remedy.townid ");
			sb.append(" and con.contractorid=remedy.contractorid");
			sb.append(" and to_char(remedy.remedydate,'yyyy-MM')>='"+startmonth+"'");
			sb.append(" and to_char(remedy.remedydate,'yyyy-MM')<='"+endmonth+"'");
			if(contractorid!=null && !contractorid.equals("")){
				sb.append(" and remedy.contractorid='"+contractorid+"'");
			}
			sb.append("  order by con.contractorid");
			util = new QueryUtil();
			logger.info("查询分布统计总表包含的代维  ："+sb.toString());
			list = util.queryBeans(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}	
	
}
