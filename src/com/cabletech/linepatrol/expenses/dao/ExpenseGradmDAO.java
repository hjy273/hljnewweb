package com.cabletech.linepatrol.expenses.dao;


import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.expenses.model.ExpenseGradem;

/*
 * 分级维护费用
 */
@Repository
public class ExpenseGradmDAO extends HibernateDao<ExpenseGradem,String>{
	
	public ExpenseGradem saveExpenseGradem(ExpenseGradem g){
		save(g);
		return g;
	}

}
