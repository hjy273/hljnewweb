package com.cabletech.linepatrol.cut.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.beans.UserInfoBean;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.cut.module.CutFeedback;

/**
 * ��ӷ���������
 * @author Administrator
 *
 */
@Repository
public class CutFeedbackDao extends HibernateDao<CutFeedback, String> {
	/**
	 * �����ӷ�����Ϣ
	 * @param cutFeedback����ӷ���ʵ��
	 * @return
	 */
	public CutFeedback saveCutFeedback(CutFeedback cutFeedback){
		this.save(cutFeedback);
		this.initObject(cutFeedback);
		return cutFeedback;
	}
	
	/**
	 * �ɴ�ά��λ���ƻ�ô�ά��λID
	 * @param contractorName����ά��λ����
	 * @return
	 */
	public String getContractorId(String contractorName){
		String sql = "select u.deptid from userinfo u where u.userid='" + contractorName + "'";
		List list = this.getJdbcTemplate().queryForList(sql);
		return ((UserInfoBean)list.get(0)).getDeptID();
	}
	
	/**
	 * ���ø�ӷ���δͨ������
	 * @param feedbackId����ӷ���ID
	 */
	public void setUnapproveNumber(String feedbackId){
		CutFeedback cutFeedback = this.findByUnique("id",feedbackId);
		int unapproveNumber = cutFeedback.getUnapproveNumber();
		cutFeedback.setUnapproveNumber(++unapproveNumber);
		this.save(cutFeedback);
	}
}
