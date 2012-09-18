package com.cabletech.machine.dao;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.machine.beans.PollingTaskBean;
import com.cabletech.machine.domainobjects.Property;

public class PollingTaskDAO {
	private static Logger logger = Logger.getLogger(PollingTaskDAO.class
			.getName());

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * �����豸Ѳ��ƻ�(���Ĳ������SDH)
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addPollingTaskForCoreAndSDH(List pollingTaskBeanList) {
		
		OracleIDImpl ora = new OracleIDImpl();
		String[] pid = ora.getSeqs("polling_task", 10, pollingTaskBeanList.size());
		
		UpdateUtil exec = null;
		try {
			exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			Iterator iter = pollingTaskBeanList.iterator();
			int i = 0;
			while(iter.hasNext()){
				PollingTaskBean bean = (PollingTaskBean)iter.next();
				String sql = "insert into polling_task(pid,tid,eid,layer,state) values('"
					+ pid[i++] + "','" + bean.getTid() + "','" + bean.getEid() + "','"
					+ bean.getLayer() + "','" + Property.NOT_POLLING + "')";
				exec.executeUpdate(sql);
			}
			exec.commit();
			exec.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			logger.error("���Ӻ��Ĳ������SDH�ƻ������쳣:" + e.getMessage());
			exec.rollback();
			return false;
		}
	}

	
	public boolean addPollingTaskForCoreAndSDH(PollingTaskBean bean) {
		OracleIDImpl ora = new OracleIDImpl();
		String pid = ora.getSeq("polling_task", 10);
		String sql = "insert into polling_task(pid,tid,eid,layer,state) values('"
				+ pid + "','" + bean.getTid() + "','" + bean.getEid() + "','"
				+ bean.getLayer() + "','" + Property.NOT_POLLING + "')";
		UpdateUtil exec = null;
		logger.info(sql);
		try {
			exec = new UpdateUtil();
			exec.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			logger.error("���Ӻ��Ĳ������SDH�ƻ������쳣:" + e.getMessage());
			return false;
		}
	}

	
	/**
	 * �����豸Ѳ��ƻ�(�����΢����)
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addPollingTaskForMicro(PollingTaskBean bean) {
		OracleIDImpl ora = new OracleIDImpl();
		String pid = ora.getSeq("polling_task", 10);
		String sql = "insert into polling_task(pid,tid,aeid,beid,layer,state) values('"
				+ pid
				+ "','"
				+ bean.getTid()
				+ "','"
				+ bean.getAeid()
				+ "','"
				+ bean.getBeid() + "','" + bean.getLayer() + "','" + Property.NOT_POLLING + "')";
		UpdateUtil exec = null;
		logger.info(sql);
		try {
			exec = new UpdateUtil();
			exec.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			logger.error("���ӽ����΢����ƻ������쳣:" + e.getMessage());
			return false;
		}
	}

	
	/**
	 * �����豸Ѳ��ƻ�(�����΢����)
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addPollingTaskForMicro(List pollingTaskBeanList) {
		OracleIDImpl ora = new OracleIDImpl();
		String[] pid = ora.getSeqs("polling_task", 10, pollingTaskBeanList.size());
		
		UpdateUtil exec = null;
		try {
			exec = new UpdateUtil();
			int i = 0;
			exec.setAutoCommitFalse();
			Iterator iter = pollingTaskBeanList.iterator();
			while(iter.hasNext())
			{
				PollingTaskBean bean = (PollingTaskBean)iter.next();
				String sql = "insert into polling_task(pid,tid,aeid,beid,layer,state) values('"
					+ pid[i++]
					+ "','"
					+ bean.getTid()
					+ "','"
					+ bean.getAeid()
					+ "','"
					+ bean.getBeid() + "','" + bean.getLayer() + "','" + Property.NOT_POLLING + "')";
				
				exec.executeUpdate(sql);
			}
			exec.commit();
			exec.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			logger.error("���ӽ����΢����ƻ������쳣:" + e.getMessage());
			exec.rollback();
			return false;
		}
	}

	
	/**
	 * �����豸Ѳ��ƻ�(����FSO)
	 * 
	 * @param bean
	 * @return
	 */
	public boolean addPollingTaskForFSO(List pollingTaskBeanList) {
		OracleIDImpl ora = new OracleIDImpl();
		String[] pid = ora.getSeqs("polling_task", 10, pollingTaskBeanList.size());
		
		
		UpdateUtil exec = null;
		try {
			exec = new UpdateUtil();
			exec.setAutoCommitFalse();
			int i = 0;
			Iterator iter = pollingTaskBeanList.iterator();
			while(iter.hasNext())
			{
				PollingTaskBean bean = (PollingTaskBean)iter.next();
				String sql = "insert into polling_task(pid,tid,aeid,beid,layer,equipment_model,machine_no,power_type,state) values('"
					+ pid[i++]
					+ "','"
					+ bean.getTid()
					+ "','"
					+ bean.getAeid()
					+ "','"
					+ bean.getBeid()
					+ "','"
					+ bean.getLayer()
					+ "','"
					+ bean.getEquipmentModel()
					+ "','"
					+ bean.getMachineNo()
					+ "','" + bean.getPowerType()  + "','" + Property.NOT_POLLING + "')";
				exec.executeUpdate(sql);
			}
			exec.commit();
			exec.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			logger.error("���ӽ����FSO�ƻ������쳣:" + e.getMessage());
			exec.rollback();
			return false;
		}
	}
	
	public boolean addPollingTaskForFSO(PollingTaskBean bean) {
		OracleIDImpl ora = new OracleIDImpl();
		String pid = ora.getSeq("polling_task", 10);
		String sql = "insert into polling_task(pid,tid,aeid,beid,layer,equipment_model,machine_no,power_type,state) values('"
				+ pid
				+ "','"
				+ bean.getTid()
				+ "','"
				+ bean.getAeid()
				+ "','"
				+ bean.getBeid()
				+ "','"
				+ bean.getLayer()
				+ "','"
				+ bean.getEquipmentModel()
				+ "','"
				+ bean.getMachineNo()
				+ "','" + bean.getPowerType()  + "','" + Property.NOT_POLLING + "')";
		UpdateUtil exec = null;
		logger.info(sql);
		try {
			exec = new UpdateUtil();
			exec.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			logger.error("���ӽ����FSO�ƻ������쳣:" + e.getMessage());
			return false;
		}
	}

	public List getEquTaskList(String id, String type, HttpSession session) {
		QueryUtil qu = null;
		StringBuffer sb = null;
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type) ||Property.FIBER_LAYER.equals(type)) {// ����Ǻ��Ĳ���߽����SDH���߹⽻ά��
			sb = new StringBuffer();
			sb
					.append(
							"select pt.pid,u.username as exename,con.contractorname as conname, ")
					.append(
							" to_char(mt.executetime,'yyyy-mm-dd' )as exetime,mt.numerical,mt.state, ")
					.append(" equ.equipment_name as ename ")
					.append(
							" from mobile_task mt,contractorinfo con,userinfo u, polling_task pt, equipment_info equ ")
					.append(
							"where mt.contractorid=con.contractorid and mt.userid=u.userid(+) and pt.tid=mt.tid and pt.eid=equ.eid")
					.append(" and pt.tid='" + id + "'");
		}
		if (Property.MICRO_LAYER.equals(type)) {// ����ǽ����΢��
			sb = new StringBuffer();
			sb
					.append(
							"select pt.pid,u.username as exename,con.contractorname as conname,")
					.append(
							" to_char(mt.executetime,'yyyy-mm-dd' )as exetime,mt.numerical,mt.state, ")
					.append(
							" equa.equipment_name as eaname, equb.equipment_name as ebname ")
					.append(
							" from mobile_task mt,contractorinfo con,userinfo u, polling_task pt, ")
					.append(" equipment_info equa, equipment_info equb ")
					.append(
							" where mt.contractorid=con.contractorid and mt.userid=u.userid(+) and pt.tid=mt.tid ")
					.append(
							" and pt.aeid=equa.eid and pt.beid=equb.eid and pt.tid='"
									+ id + "'");
		}
		if (Property.FSO_LAYER.equals(type)) {// ����ǽ����FSO
			sb = new StringBuffer();
			sb
					.append(
							"select pt.pid,u.username as exename,con.contractorname as conname,")
					.append(
							" to_char(mt.executetime,'yyyy-mm-dd' )as exetime,mt.numerical,pt.equipment_model,pt.machine_no,pt.power_type, mt.state, ")
					.append(
							" equa.equipment_name as eaname, equb.equipment_name as ebname ")
					.append(
							" from mobile_task mt,contractorinfo con,userinfo u, polling_task pt, ")
					.append(" equipment_info equa, equipment_info equb ")
					.append(
							" where mt.contractorid=con.contractorid and mt.userid=u.userid(+) and pt.tid=mt.tid ")
					.append(
							" and pt.aeid=equa.eid and pt.beid=equb.eid and pt.tid='"
									+ id + "'");
		}
		
		logger.info(sb.toString());
		session.setAttribute("oneTasksql", sb.toString());
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error("��ȡ����Ѳ��������豸�б�����쳣:" + e.getMessage());
			return null;
		}
	}
	
	public List getEquTaskListForRestore(String id, String type, HttpSession session) {
		QueryUtil qu = null;
		StringBuffer sb = null;
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type) ||Property.FIBER_LAYER.equals(type)) {// ����Ǻ��Ĳ���߽����SDH
			sb = new StringBuffer();
			sb
					.append(
							"select pt.pid,u.username as exename,con.contractorname as conname, ")
					.append(
							" to_char(mt.executetime,'yyyy-mm-dd' )as exetime,mt.numerical,mt.state, ")
					.append(" equ.equipment_name as ename ")
					.append(
							" from mobile_task mt,contractorinfo con,userinfo u, polling_task pt, equipment_info equ ")
					.append(
							"where pt.state='0' and  mt.contractorid=con.contractorid and mt.userid=u.userid(+) and pt.tid=mt.tid and pt.eid=equ.eid")
					.append(" and pt.tid='" + id + "'");
		}
		if (Property.MICRO_LAYER.equals(type)) {// ����ǽ����΢��
			sb = new StringBuffer();
			sb
					.append(
							"select pt.pid,u.username as exename,con.contractorname as conname,")
					.append(
							" to_char(mt.executetime,'yyyy-mm-dd' )as exetime,mt.numerical,mt.state, ")
					.append(
							" equa.equipment_name as eaname, equb.equipment_name as ebname ")
					.append(
							" from mobile_task mt,contractorinfo con,userinfo u, polling_task pt, ")
					.append(" equipment_info equa, equipment_info equb ")
					.append(
							" where pt.state='0' and   mt.contractorid=con.contractorid and mt.userid=u.userid(+) and pt.tid=mt.tid ")
					.append(
							" and pt.aeid=equa.eid and pt.beid=equb.eid and pt.tid='"
									+ id + "'");
		}
		if (Property.FSO_LAYER.equals(type)) {// ����ǽ����FSO
			sb = new StringBuffer();
			sb
					.append(
							"select pt.pid,u.username as exename,con.contractorname as conname,")
					.append(
							" to_char(mt.executetime,'yyyy-mm-dd' )as exetime,mt.numerical,pt.equipment_model,pt.machine_no,pt.power_type, mt.state, ")
					.append(
							" equa.equipment_name as eaname, equb.equipment_name as ebname ")
					.append(
							" from mobile_task mt,contractorinfo con,userinfo u, polling_task pt, ")
					.append(" equipment_info equa, equipment_info equb ")
					.append(
							" where pt.state='0' and   mt.contractorid=con.contractorid and mt.userid=u.userid(+) and pt.tid=mt.tid ")
					.append(
							" and pt.aeid=equa.eid and pt.beid=equb.eid and pt.tid='"
									+ id + "'");
		}
		logger.info(sb.toString());
		session.setAttribute("oneTasksql", sb.toString());
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error("��ȡ����Ѳ��������豸�б�����쳣:" + e.getMessage());
			return null;
		}
	}
	
	public List getEquTaskListForCheck(String id, String type, HttpSession session) {
		QueryUtil qu = null;
		StringBuffer sb = null;
		if (Property.CORE_LAYER.equals(type) || Property.SDH_LAYER.equals(type) || Property.FIBER_LAYER.equals(type)) {// ����Ǻ��Ĳ���߽����SDH
			sb = new StringBuffer();
			sb
					.append(
							"select pt.pid,u.username as exename,con.contractorname as conname, ")
					.append(
							" to_char(mt.executetime,'yyyy-mm-dd' )as exetime,mt.numerical,mt.state, ")
					.append(" equ.equipment_name as ename ")
					.append(
							" from mobile_task mt,contractorinfo con,userinfo u, polling_task pt, equipment_info equ ")
					.append(
							"where pt.state='1' and  mt.contractorid=con.contractorid and mt.userid=u.userid(+) and pt.tid=mt.tid and pt.eid=equ.eid")
					.append(" and pt.tid='" + id + "'");
		}
		if (Property.MICRO_LAYER.equals(type)) {// ����ǽ����΢��
			sb = new StringBuffer();
			sb
					.append(
							"select pt.pid,u.username as exename,con.contractorname as conname,")
					.append(
							" to_char(mt.executetime,'yyyy-mm-dd' )as exetime,mt.numerical,mt.state, ")
					.append(
							" equa.equipment_name as eaname, equb.equipment_name as ebname ")
					.append(
							" from mobile_task mt,contractorinfo con,userinfo u, polling_task pt, ")
					.append(" equipment_info equa, equipment_info equb ")
					.append(
							" where pt.state='1' and   mt.contractorid=con.contractorid and mt.userid=u.userid(+) and pt.tid=mt.tid ")
					.append(
							" and pt.aeid=equa.eid and pt.beid=equb.eid and pt.tid='"
									+ id + "'");
		}
		if (Property.FSO_LAYER.equals(type)) {// ����ǽ����FSO
			sb = new StringBuffer();
			sb
					.append(
							"select pt.pid,u.username as exename,con.contractorname as conname,")
					.append(
							" to_char(mt.executetime,'yyyy-mm-dd' )as exetime,mt.numerical,pt.equipment_model,pt.machine_no,pt.power_type, mt.state, ")
					.append(
							" equa.equipment_name as eaname, equb.equipment_name as ebname ")
					.append(
							" from mobile_task mt,contractorinfo con,userinfo u, polling_task pt, ")
					.append(" equipment_info equa, equipment_info equb ")
					.append(
							" where pt.state='1' and   mt.contractorid=con.contractorid and mt.userid=u.userid(+) and pt.tid=mt.tid ")
					.append(
							" and pt.aeid=equa.eid and pt.beid=equb.eid and pt.tid='"
									+ id + "'");
		}
		logger.info(sb.toString());
		session.setAttribute("oneTasksql", sb.toString());
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sb.toString());
		} catch (Exception e) {
			logger.error("��ȡ����Ѳ��������豸�б�����쳣:" + e.getMessage());
			return null;
		}
	}
	
	public List backToPrePage(String sql) {
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			logger.error("��ȡ����Ѳ��������豸�б�����쳣:" + e.getMessage());
			return null;
		}
	}
	
	public boolean modEquState(String pid) {
		String sql ="update polling_task pt set pt.state='" + Property.POLLINGED + "' where pt.pid='" + pid + "'";
		UpdateUtil update = null;
		try {
			update = new UpdateUtil();
			update.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean judgeIsEnd(String tid) {
		String sql = "select pt.state from polling_task pt where pt.tid='" + tid + "'";
		QueryUtil qu = null;
		ResultSet rs = null;
		try {
			qu = new QueryUtil();
			rs = qu.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString("state").equals(Property.NOT_POLLING)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addCheck(String pid,String auditResult, String checkRemark) {
		String sql = "update polling_task pt set pt.state='2',pt.auditResult='" + auditResult + "',checkRemark='" + checkRemark + "' where pt.pid='" + pid + "'";
		UpdateUtil update = null;
		try {
			update = new UpdateUtil();
			update.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * �жϸô�����ĺ˲��Ƿ����
	 * @param tid
	 * @return
	 */
	public boolean judgeCheckIsEnd(String tid) {
		String sql = "select pt.state from polling_task pt where pt.tid='" + tid + "'";
		QueryUtil qu = null;
		ResultSet rs = null;
		try {
			qu = new QueryUtil();
			rs = qu.executeQuery(sql);
			while(rs.next()) {
				if(!rs.getString("state").equals("2")) {
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public List back(String sql) {
		QueryUtil qu = null;
		try {
			qu = new QueryUtil();
			return qu.queryBeans(sql);
		} catch (Exception e) {
			logger.error("��ȡ����Ѳ��������豸�б�����쳣:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * ɾ���豸Ѳ��ƻ�
	 * 
	 * @param pid
	 * @return
	 */
	public boolean delPollingTask(String pid) {
		return false;
	}

	/**
	 * �޸��豸Ѳ��ƻ�
	 * 
	 * @param pid
	 * @param bean
	 * @return
	 */
	public boolean modPollingTask(String pid, PollingTaskBean bean) {
		
		return false;
	}

	/**
	 * �����ƶ��ƶ��ƻ���TID��ȡ�üƻ��µ��豸Ѳ��ƻ����б�
	 * 
	 * @param tid
	 * @return
	 */
	public List getOneTask(String tid) {

		return null;
	}

	/**
	 * ��ȡһ���豸Ѳ��ƻ�����ϸ��Ϣ
	 * 
	 * @param pid
	 * @return
	 */
	public PollingTaskBean getOneTaskInfo(String pid) {
		String sql = "select pt.checkremark,pt.auditresult from polling_task pt where pt.pid='" + pid + "'";
		QueryUtil qu = null;
		ResultSet rs = null;
		PollingTaskBean bean = null;
		try {
			qu = new QueryUtil();
			rs = qu.executeQuery(sql);
			if(rs.next()) {
				bean = new PollingTaskBean();
				bean.setAuditResult(rs.getString("auditresult"));
				bean.setCheckRemark(rs.getString("checkremark"));
			}
			return bean;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * ִ�в�ѯ
	 * 
	 * @return
	 */
	public List doQuery(String condition) {
		return null;
	}

	private void initResponse(HttpServletResponse response, String outfilename)
			throws Exception {
		logger.info("��ʼreset");
		response.reset();
		logger.info("reset");
		response.setContentType(CONTENT_TYPE);
		logger.info("setContentType");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));
		logger.info("setHeader");
	}
}
