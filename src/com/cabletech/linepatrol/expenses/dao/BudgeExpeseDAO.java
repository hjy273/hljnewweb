package com.cabletech.linepatrol.expenses.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.expenses.model.ExpenseBudget;

/*
 * �ּ�ȡ��ϵ��
 */
@Repository
public class BudgeExpeseDAO extends HibernateDao<ExpenseBudget,String>{

	public void saveBudget(ExpenseBudget f){
		save(f);
	}

	public void updateBudget(ExpenseBudget f){
		getSession().update(f);
	}

	/**
	 * �ж��Ƿ��Ѿ����ڸ�Ԥ��
	 * @param contratorid
	 * @param year
	 * @param type
	 * @return
	 */
	public List<ExpenseBudget> judgeIsHaveBudget(String contratorid,
			String year,String type,String id){
		String hql = " from ExpenseBudget e where contractorId=? and expenseType=? " +
		"and year=?";
		if(id!=null && !"".equals(id)){
			hql+=" and id!='"+id+"'";
		}
		return find(hql,contratorid,type,year);
	}

	/**
	 * ��ѯԤ��
	 * @return
	 */
	public List getBudgets(){
		StringBuffer sb = new StringBuffer();
		sb.append("  select bud.id,year,");
		sb.append(" decode(bud.expense_type,'0','����','�ܵ�')expense_type,");
		sb.append(" c.contractorname,bud.budget_money,bud.pay_money ");
		sb.append("   from lp_expense_budget bud,contractorinfo c ");
		sb.append("   where c.contractorid=bud.contractor_id ");
		sb.append(" order by year,contractorname,expense_type");
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), null);
	}


}
