package com.cabletech.bj.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;

@Repository
public class WaitHandleWorkDao extends HibernateDao {
	private static Logger logger = Logger.getLogger(WaitHandleWorkDao.class
			.getName());

	/**
	 * ���ݲ�ѯ������ȡ��������¼ƻ�����
	 * 
	 * @param condition
	 * @return
	 */
	public List getWaitApprovedYMPlanList(String condition) {
		String sql = "select id, planname from yearmonthplan ymp ";
		sql += " where ifinnercheck =  '1'  and status='0' and remark != '0' ";
		sql = sql + condition;
		logger.info("���ݲ�ѯ������ȡ��������¼ƻ�������" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ���ݲ�ѯ������ȡ����ļƻ�����
	 * 
	 * @param condition
	 * @return
	 */
	public List getWaitApprovedPlanList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select id from plan where status='0' ";
		sql = sql + condition;
		logger.info("���ݲ�ѯ������ȡ����ļƻ�������" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ���ݲ�ѯ������ȡ����Ĳ��������������
	 * 
	 * @param condition
	 * @return
	 */
	public List getWaitApprovedMaterialApplyList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select mt.id from lp_mt_new mt,userinfo u ";
		sql = sql + " where mt.creator=u.userid and mt.state='1' ";
		sql = sql + condition;
		logger.info("���ݲ�ѯ������ȡ����Ĳ����������������" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ���ݲ�ѯ������ȡ����Ĳ����̵���������
	 * 
	 * @param condition
	 * @return
	 */
	public List getWaitApprovedMaterialUsedList(String condition) {
		// TODO Auto-generated method stub
		String sql = "select mt.id from lp_mt_used mt,userinfo u ";
		sql = sql + " where mt.creator=u.userid and mt.state='1' ";
		sql = sql + condition;
		logger.info("���ݲ�ѯ������ȡ����Ĳ����̵�����������" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ���ݵ�ǰ��¼�û��Ĳ��ű����Ϣ��ȡ������ɵ�����
	 * 
	 * @param userDeptId
	 * @return
	 */
	public List getWaitHandleSendTaskList(String userDeptId) {
		// TODO Auto-generated method stub
		String sql = "select s.id ";
		sql = sql + " from lp_sendtask s,userinfo ua , ";
		sql = sql + " lp_sendtask_acceptdept ad ";
		sql = sql + " where s.id = ad.sendtaskid(+) ";
		sql = sql + " and ad.userid = ua.userid(+) ";
		sql = sql + " and ad.workstate='1��ǩ��' ";
		sql = sql + " and ad.deptid='" + userDeptId + "' ";
		sql = sql + " union ";
		sql = sql + " select s.id ";
		sql = sql + " from lp_sendtask s,userinfo ua, ";
		sql = sql + " lp_sendtask_acceptdept ad ";
		sql = sql + " where s.id = ad.sendtaskid and ad.userid = ua.userid(+) ";
		sql = sql + " and (ad.workstate='3���ظ�' or ad.workstate='2������') ";
		sql = sql + " and ad.deptid='" + userDeptId + "' ";
		sql = sql + " union ";
		sql = sql + " select s.id ";
		sql = sql + " from lp_sendtask s,userinfo us, ";
		sql = sql + " lp_sendtaskreply sr,lp_sendtask_acceptdept ad ";
		sql = sql + " where s.senduserid = us.userid(+) ";
		sql = sql + " and s.id = ad.sendtaskid(+) ";
		sql = sql + " and ad.id = sr.sendaccid(+) ";
		sql = sql + " and ad.workstate = '6����֤' ";
		sql = sql + " and s.senddeptid = '" + userDeptId + "'";
		logger.info("���ݵ�ǰ��¼�û��Ĳ��ű����Ϣ��ȡ������ɵ�������" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}
}
