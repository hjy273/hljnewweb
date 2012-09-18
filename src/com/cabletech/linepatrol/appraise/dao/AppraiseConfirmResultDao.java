package com.cabletech.linepatrol.appraise.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.appraise.module.AppraiseConfirmResult;
@Repository
public class AppraiseConfirmResultDao extends HibernateDao<AppraiseConfirmResult, String> {
	/**
	 * ������п���ȷ�ϵ����һ����¼
	 * @return
	 */
	public List<AppraiseConfirmResult> getAllLastConfirm(){
		String hql="from AppraiseConfirmResult where id in(select max(id) from AppraiseConfirmResult group by resultId) and resultId in (select id from AppraiseResult where confirmResult='3')";
		return find(hql);
	}
	/**
	 * ͨ��resultId������п��˽��ȷ��
	 * @param resultId
	 * @return
	 */
	public List<AppraiseConfirmResult> getConfirmResultByResultId(String resultId) {
		String hql="from AppraiseConfirmResult where resultId=?";
		return find(hql,resultId);
	}
}
