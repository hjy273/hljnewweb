package com.cabletech.linepatrol.material.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.material.domain.MonthStat;

@Repository
public class MonthStateDao extends HibernateDao<MonthStat, String>{
	Logger logger = Logger.getLogger(this.getClass().getName());
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
	 * 获取修缮申请基本信息
	 * @param month
	 * @param contractorid
	 * @return
	 */
	public List getBaseInfo(String month,String contractorid){
		StringBuffer sb = new StringBuffer();
		sb.append("select remedy.id,to_char(remedy.remedydate,'yyyy-MM') remedydate,remedy.remedycode,remedy.projectname,");
		sb.append(" remedy.remedyaddress,u.username creator,c.contractorname from userinfo u,LP_remedy remedy,contractorinfo c where");
		sb.append(" remedy.contractorid=c.contractorid and u.userid=remedy.creator and remedy.state='401'");
		if(month!=null && !month.trim().equals("")){
			sb.append(" and to_char(remedy.remedydate,'yyyy-MM') like '"+month+"'");
		}
		sb.append(" and remedy.contractorid='"+contractorid+"'");
		sb.append(" order by remedy.id desc");
		logger.info("获取修缮申请基本信息sql:"+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}
	/**
	 * 获取监理单位信息
	 * @param month
	 * @param contractorid
	 * @return
	 */
	public List getIntendance(String month,String contractorid){
		List list = new ArrayList();
		QueryUtil util = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select remedy.remedycode remedycode,remedy.id from LP_remedy remedy where 1=1");
			if(month!=null && !month.trim().equals("")){
				sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+month+"'");
			}
			sb.append(" and remedy.contractorid='"+contractorid+"'");
			sb.append(" and remedy.state='401'");
			sb.append(" order by remedy.id desc");
			util = new QueryUtil();
			logger.info("查询月统计sql："+sb.toString());
			ResultSet rs = util.executeQuery(sb.toString());
			//logger.info("sql::::::::::::"+sb.toString());
			while (rs.next()) {
				String remedyid = rs.getString("id");
				StringBuffer sc = new StringBuffer();
				sc.append("select c.contractorname from LP_remedy_workflow wf,userinfo u ,contractorinfo c,LP_remedy remedy where");
				sc.append(" wf.state>wf.prev_state and wf.last_man=u.userid and u.deptid=c.contractorid and wf.state='1011' and wf.remedyid = remedy.id");
				sc.append(" and remedy.id='"+remedyid+"'");
				sc.append(" and step_id='3'");
				if(month!=null && !month.trim().equals("")){
					sc.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+month+"'");
				}
				sc.append(" and remedy.contractorid='"+contractorid+"'");
				sc.append("order by wf.remedyid desc");
				util = new QueryUtil();
				logger.info("获取监理单位信息："+sc.toString());
				ResultSet r = util.executeQuery(sc.toString());
				String rm="";
				if(r.next()){
					rm = r.getString("contractorname");
					list.add(rm);
				}else if(!r.next()){
					rm="空";
					list.add(rm);
				}
				logger.info("获取监理单位信sql::::::::::::"+sc.toString());
			}
		} catch (Exception e) {
			logger.info("获取监理单位信息异常:" + e.getMessage());
		}
		return list;
	}
	/**
	 * 获取区域负责人信息
	 * @param month
	 * @param contractorid
	 * @return
	 */
	public List getRegionPrincipal(String month,String contractorid){
		List list = new ArrayList();
		QueryUtil util = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select remedy.remedycode remedycode,remedy.id from LP_remedy remedy where 1=1 ");
			if(month!=null && !month.trim().equals("")){
				sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+month+"'");
			}
			sb.append(" and remedy.contractorid='"+contractorid+"'");
			sb.append(" and remedy.state='401'");
			sb.append(" order by remedy.id desc");
			util = new QueryUtil();
			ResultSet rs = util.executeQuery(sb.toString());
			//logger.info("sql::::::::::::"+sb.toString());
			while (rs.next()) {
				String remedyid = rs.getString("id");
				StringBuffer sc = new StringBuffer();
				sc.append("select u.username from LP_remedy_workflow wf,userinfo u ,LP_remedy remedy where");
				sc.append(" wf.state>wf.prev_state and wf.last_man=u.userid and wf.state='1021' and wf.remedyid = remedy.id");
				sc.append(" and remedy.id='"+remedyid+"'");
				sc.append(" and step_id='4'");
				if(month!=null && !month.trim().equals("")){
					sc.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+month+"'");
				}
				sc.append(" and remedy.contractorid='"+contractorid+"'");
				sc.append("order by wf.remedyid desc");
				util = new QueryUtil();
				logger.info("获取区域负责人信息："+sc.toString());
				ResultSet r = util.executeQuery(sc.toString());
				String rm="";
				if(r.next()){
					rm = r.getString("username");
					list.add(rm);
				}else if(!r.next()){
					rm="空";
					list.add(rm);
				}
				logger.info("获取区域负责人信息sql::::::::::::"+sc.toString());
			}
		} catch (Exception e) {
			logger.info("获取区域负责人信息异常:" + e.getMessage());
		}
		return list;
	}
	/**
	 * 获取使用材料名称
	 * @param month
	 * @param contractorid
	 * @return
	 */
	public List getMaterialName(String month,String contractorid){
		List list = new ArrayList();
		StringBuffer sb = new StringBuffer();
/*		sb.append("select distinct m.materialid,mb.name||'（'||mt.typename||'）（'||mm.modelname||'）'  as material_name from");
		sb.append(" LP_remedy_material m,LP_mt_base mb,LP_mt_model mm,LP_mt_type mt,LP_remedy remedy where");
		sb.append(" m.materialid=mb.id and mb.modelid=mm.id and mm.typeid=mt.id and m.remedyid=remedy.id");
		if(Month!=null && !Month.trim().equals("")){
			sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
		}
		sb.append(" and remedy.contractorid='"+contractorid+"'");
		sb.append(" and remedy.state='401'");*/
		
		sb.append("select distinct m.materialid,mb.name||'（'||mt.typename||'）（'||mm.modelname||'）'  as material_name ");
		sb.append(" from LP_REMEDY_BAL_MATERIAL m,LP_mt_base mb,LP_mt_model mm,");//修缮结算材料使用情况
		sb.append(" LP_mt_type mt,LP_remedy remedy ");
		sb.append(" where m.materialid=mb.id and mb.modelid=mm.id and mm.typeid=mt.id and m.remedyid=remedy.id");
		if(month!=null && !month.trim().equals("")){
			sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+month+"'");
		}
		sb.append(" and remedy.contractorid='"+contractorid+"'");
		sb.append(" and remedy.state='401'");
		//logger.info("获取使用材料名称："+sb.toString());
		logger.info("获取使用材料名称sql::::::::::::"+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}
	/**
	 * 统计使用材料信息数量
	 * @param month
	 * @param contractorid
	 * @return
	 */
	public List getMaterialInfo(String month,String contractorid){
		List list = new ArrayList();

		QueryUtil util = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select remedy.remedycode remedycode,remedy.id from LP_remedy remedy where 1=1 ");
			if(month!=null && !month.trim().equals("")){
				sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+month+"'");
			}
			sb.append(" and remedy.contractorid='"+contractorid+"'");
			sb.append(" and remedy.state='401'");
			sb.append(" order by remedy.id desc");
			util = new QueryUtil();
			ResultSet rs = util.executeQuery(sb.toString());
			logger.info("sql::::::::::::"+sb.toString());
			while (rs.next()) {
				String remedyid = rs.getString("id");
				//String remedycode = rs.getString("remedycode");
				StringBuffer sc = new StringBuffer();
				sc.append("select m.materialid, sum(m.materialcount) as use_number from");
				sc.append(" LP_REMEDY_BAL_MATERIAL m,LP_remedy remedy where m.remedyid=remedy.id");
				sc.append(" and remedy.id='"+remedyid+"'");
				if(month!=null && !month.trim().equals("")){
					sc.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+month+"'");
				}
				sc.append(" and remedy.contractorid='"+contractorid+"'");
				sc.append(" and remedy.state='401'");
				sc.append(" group by m.materialid");
				util = new QueryUtil();
				logger.info("统计使用材料信息数量sql:"+sc.toString());
				ResultSet r = util.executeQuery(sc.toString());
				List count = new ArrayList();
				while(r.next()){
					HashMap map = new HashMap();
					String materialid  = r.getString("materialid");
					String use_number = r.getString("use_number");
					if(use_number==null){
						map.put(materialid, "0");
					}else{
						map.put(materialid, use_number);
					}
					count.add(map);
				}
				list.add(count);
				//map.put(remedycode, list);
			}
		}catch(Exception e){
			logger.info("统计使用材料信息数量异常:" + e.getMessage());
		}
		return list;
	}
}
