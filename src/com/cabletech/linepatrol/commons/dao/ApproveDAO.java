package com.cabletech.linepatrol.commons.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.module.ApproveInfo;

@Repository
public class ApproveDAO extends HibernateDao<ApproveInfo, String> {
	/**
	 * 保存用户审核信息
	 * 
	 * @param approve
	 * @return
	 */
	public ApproveInfo saveApproveInfo(ApproveInfo approve) {
		approve.setApproveTime(new Date());
		super.save(approve);
		return approve;
	}
	
	/**
	 * 删除审核信息
	 * @param objectId
	 * @param objectType
	 */
	public void deleteApproveInfo(String objectId,String objectType){
		String sql=" delete LP_APPROVE_INFO  where object_id='"+objectId+"' and object_type='"+objectType+"'";
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 根据查询条件获取审核信息列表
	 * 
	 * @param condition
	 * @return
	 */
	public List queryList(String condition) {
		String sql = "select u.username,approve.approve_remark, ";
		sql = sql + " to_char(approve.approve_time,'yyyy-mm-dd hh24:mi:ss') ";
		sql = sql + " as approve_time,approve.approve_result, ";
		sql = sql + " decode(approve.approve_result,'0','审核不通过','1', ";
		sql = sql + "'审核通过','转审') as approve_result_dis ";
		sql = sql + " from lp_approve_info approve,userinfo u ";
		sql = sql + " where approve.approver_id=u.userid ";
		sql = sql + condition;
		sql = sql + " order by approve.id";
		logger.info("Execute sql:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql, null);
	}
}
