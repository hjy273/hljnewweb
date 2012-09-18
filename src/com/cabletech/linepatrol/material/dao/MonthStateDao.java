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
	 * �ƶ���ѯ��ά
	 * @param user
	 * @return
	 */
	public List getConsByDeptId(UserInfo user){
		String sql = "select c.contractorid,c.contractorname from contractorinfo c" +
			" where c.state is null and c.depttype=2 and c.regionid=?";
		logger.info("regionId:" + user.getRegionID());
		logger.info("�ƶ���ѯ��ά:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, user.getRegionID());
	}	
	/**
	 * ��ȡ�������������Ϣ
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
		logger.info("��ȡ�������������Ϣsql:"+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}
	/**
	 * ��ȡ����λ��Ϣ
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
			logger.info("��ѯ��ͳ��sql��"+sb.toString());
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
				logger.info("��ȡ����λ��Ϣ��"+sc.toString());
				ResultSet r = util.executeQuery(sc.toString());
				String rm="";
				if(r.next()){
					rm = r.getString("contractorname");
					list.add(rm);
				}else if(!r.next()){
					rm="��";
					list.add(rm);
				}
				logger.info("��ȡ����λ��sql::::::::::::"+sc.toString());
			}
		} catch (Exception e) {
			logger.info("��ȡ����λ��Ϣ�쳣:" + e.getMessage());
		}
		return list;
	}
	/**
	 * ��ȡ����������Ϣ
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
				logger.info("��ȡ����������Ϣ��"+sc.toString());
				ResultSet r = util.executeQuery(sc.toString());
				String rm="";
				if(r.next()){
					rm = r.getString("username");
					list.add(rm);
				}else if(!r.next()){
					rm="��";
					list.add(rm);
				}
				logger.info("��ȡ����������Ϣsql::::::::::::"+sc.toString());
			}
		} catch (Exception e) {
			logger.info("��ȡ����������Ϣ�쳣:" + e.getMessage());
		}
		return list;
	}
	/**
	 * ��ȡʹ�ò�������
	 * @param month
	 * @param contractorid
	 * @return
	 */
	public List getMaterialName(String month,String contractorid){
		List list = new ArrayList();
		StringBuffer sb = new StringBuffer();
/*		sb.append("select distinct m.materialid,mb.name||'��'||mt.typename||'����'||mm.modelname||'��'  as material_name from");
		sb.append(" LP_remedy_material m,LP_mt_base mb,LP_mt_model mm,LP_mt_type mt,LP_remedy remedy where");
		sb.append(" m.materialid=mb.id and mb.modelid=mm.id and mm.typeid=mt.id and m.remedyid=remedy.id");
		if(Month!=null && !Month.trim().equals("")){
			sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
		}
		sb.append(" and remedy.contractorid='"+contractorid+"'");
		sb.append(" and remedy.state='401'");*/
		
		sb.append("select distinct m.materialid,mb.name||'��'||mt.typename||'����'||mm.modelname||'��'  as material_name ");
		sb.append(" from LP_REMEDY_BAL_MATERIAL m,LP_mt_base mb,LP_mt_model mm,");//���ɽ������ʹ�����
		sb.append(" LP_mt_type mt,LP_remedy remedy ");
		sb.append(" where m.materialid=mb.id and mb.modelid=mm.id and mm.typeid=mt.id and m.remedyid=remedy.id");
		if(month!=null && !month.trim().equals("")){
			sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+month+"'");
		}
		sb.append(" and remedy.contractorid='"+contractorid+"'");
		sb.append(" and remedy.state='401'");
		//logger.info("��ȡʹ�ò������ƣ�"+sb.toString());
		logger.info("��ȡʹ�ò�������sql::::::::::::"+sb.toString());
		return this.getJdbcTemplate().queryForBeans(sb.toString());
	}
	/**
	 * ͳ��ʹ�ò�����Ϣ����
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
				logger.info("ͳ��ʹ�ò�����Ϣ����sql:"+sc.toString());
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
			logger.info("ͳ��ʹ�ò�����Ϣ�����쳣:" + e.getMessage());
		}
		return list;
	}
}
