package com.cabletech.linepatrol.expenses.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.expenses.model.ExpenseMonth;
import com.cabletech.linepatrol.expenses.service.ExpenseConstant;


@Repository
public class ExpenseMonthDAO extends HibernateDao<ExpenseMonth,String>{

	public void updateExpenseMonth(ExpenseMonth m){
		getSession().saveOrUpdate(m);
	}

	/**
	 * 查询月维护费用
	 * @param month
	 * @return
	 */
	public List getExpenses(String month,String expenseType){
		String hql = "from ExpenseMonth e where e.yearmonth=to_date('"+month+"', 'yyyy/MM') and expenseType=?";
		return find(hql,expenseType);
	}

	/**
	 * 根据参数年查询改年 已经统计的月维护费用的月份
	 * @param year  年
	 * @return
	 */
	public List getExpenseMonthByYear(String year,String expenseType){
		String hql = "select distinct yearmonth" +
		" from ExpenseMonth e where to_char(e.yearmonth,'yyyy')='"+year+"' and e.expenseType=?" +
		" order by yearmonth";
		return find(hql,expenseType);
	}

	/**
	 * 根据代维、月份查询费用
	 * @param month
	 * @param contractorId
	 * @return
	 */
	public List getExpenseMonth(String month,
			String contractorId,String expenseType){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select m.id, m.cable_num,m.cable_length,m.month_price,");
		sb.append(" m.rectify_money,m.remark,m.deduction_money,up.unit_price");
		sb.append(" from lp_expense_month m,lp_expense_unitprice up");
		sb.append(" where m.contractor_id=up.contractor_id");
		sb.append(" and m.yearmonth=to_date('"+month+"', 'yyyy/MM')");
		sb.append(" and m.contractor_id=?");
		sb.append(" and up.expense_type=? and m.expense_type=?");
		values.add(contractorId);
		values.add(expenseType);
		values.add(expenseType);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 根据代维、月份查询费用
	 * @param month
	 * @param contractorId
	 * @return
	 */
	public List<ExpenseMonth> getExpenseByContractorid(String beginmonth,
			String endmonth,String contractorId){
		String hql = "from ExpenseMonth e " +
		"where e.yearmonth>=to_date('"+beginmonth+"', 'yyyy/MM')" +
		" and e.yearmonth<=to_date('"+endmonth+"', 'yyyy/MM')" +
		" and e.contractorId=? and expenseType=?";
		return find(hql,contractorId,ExpenseConstant.EXPENSE_TYPE_CABLE);
	}



}
