package com.cabletech.linepatrol.cut.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.beans.UserInfoBean;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.cut.module.CutFeedback;

/**
 * 割接反馈操作类
 * @author Administrator
 *
 */
@Repository
public class CutFeedbackDao extends HibernateDao<CutFeedback, String> {
	/**
	 * 保存割接反馈信息
	 * @param cutFeedback：割接反馈实体
	 * @return
	 */
	public CutFeedback saveCutFeedback(CutFeedback cutFeedback){
		this.save(cutFeedback);
		this.initObject(cutFeedback);
		return cutFeedback;
	}
	
	/**
	 * 由代维单位名称获得代维单位ID
	 * @param contractorName：代维单位名称
	 * @return
	 */
	public String getContractorId(String contractorName){
		String sql = "select u.deptid from userinfo u where u.userid='" + contractorName + "'";
		List list = this.getJdbcTemplate().queryForList(sql);
		return ((UserInfoBean)list.get(0)).getDeptID();
	}
	
	/**
	 * 设置割接反馈未通过次数
	 * @param feedbackId：割接反馈ID
	 */
	public void setUnapproveNumber(String feedbackId){
		CutFeedback cutFeedback = this.findByUnique("id",feedbackId);
		int unapproveNumber = cutFeedback.getUnapproveNumber();
		cutFeedback.setUnapproveNumber(++unapproveNumber);
		this.save(cutFeedback);
	}
}
