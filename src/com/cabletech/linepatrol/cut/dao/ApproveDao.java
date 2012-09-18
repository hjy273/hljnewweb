package com.cabletech.linepatrol.cut.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.module.ApproveInfo;

/**
 * ������������
 * 
 * @author Administrator
 * 
 */
@Repository
public class ApproveDao extends HibernateDao<ApproveInfo, String> {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * ���������Ϣ
	 * @param objId��ҵ��ID
	 * @param objType��ҵ������
	 * @return
	 */
	public List getApproveInfos(String objId, String objType){
		String sql = "select approve.approve_result,approve.approve_remark,approve.approver_id,to_char(approve_time,'yyyy-mm-dd hh24:mi:ss') as approve_time from lp_approve_info approve where approve.object_id=? and approve.object_type=?";
		logger.info("***********getApproveInfos:" + sql);
		return this.getJdbcTemplate().queryForBeans(sql, objId, objType);
	}
}