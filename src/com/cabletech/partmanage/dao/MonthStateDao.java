package com.cabletech.partmanage.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;

public class MonthStateDao {
	Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * �ƶ���ѯ��ά
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
			logger.info("��ѯ��ά��"+sql);
			list = util.queryBeans(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}	
	/**
	 * ��ȡ�������������Ϣ
	 * @param Month
	 * @param contractorid
	 * @return
	 */
	public List getBaseInfo(String Month,String contractorid){
		List list = new ArrayList();
		QueryUtil util = null;
		try {

			StringBuffer sb = new StringBuffer();
			sb.append("select remedy.id,to_char(remedy.remedydate,'yyyy-MM') remedydate,remedy.remedycode,remedy.projectname,");
			sb.append(" remedy.remedyaddress,u.username creator,c.contractorname from userinfo u,linepatrol_remedy remedy,contractorinfo c where");
			sb.append(" remedy.contractorid=c.contractorid and u.userid=remedy.creator and remedy.state='401'");
			if(Month!=null && !Month.trim().equals("")){
				sb.append(" and to_char(remedy.remedydate,'yyyy-MM') like '"+Month+"'");
			}
			sb.append(" and remedy.contractorid='"+contractorid+"'");
			sb.append(" order by remedy.id desc");
			util = new QueryUtil();
			logger.info("��ȡ�������������Ϣsql:"+sb.toString());
			list = util.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.info("��ȡ�������������Ϣ�쳣:" + e.getMessage());
		}
		return list;
	}
	/**
	 * ��ȡ����λ��Ϣ
	 * @param Month
	 * @param contractorid
	 * @return
	 */
	public List getIntendance(String Month,String contractorid){
		List list = new ArrayList();
		QueryUtil util = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select remedy.remedycode remedycode,remedy.id from linepatrol_remedy remedy where 1=1");
			if(Month!=null && !Month.trim().equals("")){
				sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
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
				sc.append("select c.contractorname from linepatrol_remedy_workflow wf,userinfo u ,contractorinfo c,linepatrol_remedy remedy where");
				sc.append(" wf.state>wf.prev_state and wf.last_man=u.userid and u.deptid=c.contractorid and wf.state='1011' and wf.remedyid = remedy.id");
				sc.append(" and remedy.id='"+remedyid+"'");
				sc.append(" and step_id='3'");
				if(Month!=null && !Month.trim().equals("")){
					sc.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
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
	 * @param Month
	 * @param contractorid
	 * @return
	 */
	public List getRegionPrincipal(String Month,String contractorid){
		List list = new ArrayList();
		QueryUtil util = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select remedy.remedycode remedycode,remedy.id from linepatrol_remedy remedy where 1=1 ");
			if(Month!=null && !Month.trim().equals("")){
				sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
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
				sc.append("select u.username from linepatrol_remedy_workflow wf,userinfo u ,linepatrol_remedy remedy where");
				sc.append(" wf.state>wf.prev_state and wf.last_man=u.userid and wf.state='1021' and wf.remedyid = remedy.id");
				sc.append(" and remedy.id='"+remedyid+"'");
				sc.append(" and step_id='4'");
				if(Month!=null && !Month.trim().equals("")){
					sc.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
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
	 * @param Month
	 * @param contractorid
	 * @return
	 */
	public List getMaterialName(String Month,String contractorid){
		List list = new ArrayList();
		QueryUtil util = null;
		try {
			StringBuffer sb = new StringBuffer();
	/*		sb.append("select distinct m.materialid,mb.name||'��'||mt.typename||'����'||mm.modelname||'��'  as material_name from");
			sb.append(" linepatrol_remedy_material m,linepatrol_mt_base mb,linepatrol_mt_model mm,linepatrol_mt_type mt,linepatrol_remedy remedy where");
			sb.append(" m.materialid=mb.id and mb.modelid=mm.id and mm.typeid=mt.id and m.remedyid=remedy.id");
			if(Month!=null && !Month.trim().equals("")){
				sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
			}
			sb.append(" and remedy.contractorid='"+contractorid+"'");
			sb.append(" and remedy.state='401'");*/
			
			sb.append("select distinct m.materialid,mb.name||'��'||mt.typename||'����'||mm.modelname||'��'  as material_name ");
			sb.append(" from LINEPATROL_REMEDY_BAL_MATERIAL m,linepatrol_mt_base mb,linepatrol_mt_model mm,");//���ɽ������ʹ�����
			sb.append(" linepatrol_mt_type mt,linepatrol_remedy remedy ");
			sb.append(" where m.materialid=mb.id and mb.modelid=mm.id and mm.typeid=mt.id and m.remedyid=remedy.id");
			if(Month!=null && !Month.trim().equals("")){
				sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
			}
			sb.append(" and remedy.contractorid='"+contractorid+"'");
			sb.append(" and remedy.state='401'");
			util = new QueryUtil();
			//logger.info("��ȡʹ�ò������ƣ�"+sb.toString());
			list = util.queryBeans(sb.toString());
			logger.info("��ȡʹ�ò�������sql::::::::::::"+sb.toString());
		} catch (Exception e) {
			logger.info("��ȡʹ�ò��������쳣:" + e.getMessage());
		}
		return list;
	}
	/**
	 * ͳ��ʹ�ò�����Ϣ����
	 * @param Month
	 * @param contractorid
	 * @return
	 */
	public List getMaterialInfo(String Month,String contractorid){
		List list = new ArrayList();

		QueryUtil util = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select remedy.remedycode remedycode,remedy.id from linepatrol_remedy remedy where 1=1 ");
			if(Month!=null && !Month.trim().equals("")){
				sb.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
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
				sc.append(" LINEPATROL_REMEDY_BAL_MATERIAL m,linepatrol_remedy remedy where m.remedyid=remedy.id");
				sc.append(" and remedy.id='"+remedyid+"'");
				if(Month!=null && !Month.trim().equals("")){
					sc.append(" and to_char(remedy.remedydate,'yyyy-mm') like '"+Month+"'");
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
