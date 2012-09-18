package com.cabletech.linepatrol.expenses.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.expenses.model.ExpenseAffirm;

/*
 * 分级取费系数
 */
@Repository
public class ExpenseAffirmDAO extends HibernateDao<ExpenseAffirm, String> {

	public void saveBudget(ExpenseAffirm f) {
		save(f);
	}

	public void updateBudget(ExpenseAffirm f) {
		getSession().update(f);
	}

	
	public ExpenseAffirm getAffirm(String id){
		return get(id);
	}
	
	/**
	 * 判断是否已经存在该费用核实
	 * 
	 * @param contratorid
	 * @param year
	 * @param type
	 * @return
	 */
	public List<ExpenseAffirm> judgeIsHaveAffirm(String contratorid,
			String beginMonth, String endMonth, String budgetId, String id) {
		String hql = " from ExpenseAffirm a where a.contractorId=? and a.budgetId=? ";
		hql += " and a.startMonth=to_date('" + beginMonth + "/01"
				+ "','yyyy/mm/dd') ";
		hql += " and a.endMonth=to_date('" + endMonth + "/01"
				+ "','yyyy/mm/dd') ";
		if (id != null && !"".equals(id)) {
			hql += " and id!='" + id + "'";
		}
		return super.find(hql, contratorid, budgetId);
	}

	public List judgeIsExistSameMonthAffirm(String contractorid,
			String beginMonth, String endMonth, String budgetId) {
		String hql = " from ExpenseAffirm a where a.contractorId=? and a.budgetId=? ";
		hql += " and ( ";
		hql += " a.startMonth<=to_date('"+endMonth + "/01"+"','yyyy/mm/dd') ";
		hql += " and a.endMonth>=to_date('"+beginMonth + "/01"+"','yyyy/mm/dd') ";
		hql += " ) ";
		return super.find(hql, contractorid, budgetId);
	}
	
	/**
	 * 给定一个日期判断该月是否已经付费
	 * @param contractorid
	 * @param yearmonth
	 * @return
	 */
	public List<ExpenseAffirm> judgeIsExistAffirm(String contractorid,
			String yearmonth) {
		String hql = " from ExpenseAffirm a where a.contractorId=? ";
		hql += " and ( ";
		hql += " a.startMonth<=to_date('"+yearmonth + "/01"+"','yyyy/mm/dd') ";
		hql += " and a.endMonth>=to_date('"+yearmonth + "/01"+"','yyyy/mm/dd') ";
		hql += " ) ";
		return super.find(hql, contractorid);
	}
	
	
	/**
	 * 查找固定时间段费用确认信息
	 * @return
	 */
	public List getExpenseAffrims(){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select affirm.id,");
		sb.append(" decode(bud.expense_type,'0','光缆','管道')expense_type,");
		sb.append(" c.contractorname,bud.budget_money,bud.pay_money, ");
		sb.append(" to_char(start_month,'yyyy/MM') start_month,");
		sb.append(" to_char(end_month,'yyyy/MM') end_month");
		sb.append(" from lp_expense_affirm affirm,");
		sb.append(" lp_expense_budget bud,contractorinfo c");
		sb.append(" where c.contractorid=bud.contractor_id ");
		sb.append(" and affirm.budget_id=bud.id ");
		//sb.append(" and affirm.budget_id=bud.id and bud.expense_type=? ");
		sb.append(" order by start_month,contractorname,expense_type");
	//	values.add(expensetype);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	
	
}
