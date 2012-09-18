package com.cabletech.linepatrol.appraise.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.appraise.module.AppraiseConfirmResult;
@Repository
public class AppraiseConfirmResultDao extends HibernateDao<AppraiseConfirmResult, String> {
	/**
	 * 获得所有考核确认的最后一条记录
	 * @return
	 */
	public List<AppraiseConfirmResult> getAllLastConfirm(){
		String hql="from AppraiseConfirmResult where id in(select max(id) from AppraiseConfirmResult group by resultId) and resultId in (select id from AppraiseResult where confirmResult='3')";
		return find(hql);
	}
	/**
	 * 通过resultId获得所有考核结果确认
	 * @param resultId
	 * @return
	 */
	public List<AppraiseConfirmResult> getConfirmResultByResultId(String resultId) {
		String hql="from AppraiseConfirmResult where resultId=?";
		return find(hql,resultId);
	}
}
