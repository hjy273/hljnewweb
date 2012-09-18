package com.cabletech.linepatrol.expenses.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.expenses.beans.ExpensePriceBean;
import com.cabletech.linepatrol.expenses.dao.ExpenseUnitPriceDAO;
import com.cabletech.linepatrol.expenses.dao.ExpensesDAO;
import com.cabletech.linepatrol.expenses.model.ExpenseUnitPrice;

@Service
@Transactional
public class ExpensesUnitPriceBO extends EntityManager<ExpenseUnitPrice, String> {

	@Resource(name="expenseUnitPriceDAO")
	private ExpenseUnitPriceDAO dao;

	public void saveUnitPrice(ExpenseUnitPrice price,ExpensePriceBean bean){
		String expenseType = price.getExpenseType();
		if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_PL)){
			price.setUnitPrice(bean.getUnitPipePrice());
			price.setExplan("");
			price.setCableLevel("");
		}
		dao.save(price);
	}
	
	public void editUnitPrice(ExpenseUnitPrice price,ExpensePriceBean bean){
		String expenseType = price.getExpenseType();
		if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_PL)){
			price.setUnitPrice(bean.getUnitPipePrice());
			price.setExplan("");
			price.setCableLevel("");
		}
		dao.editUnitPrice(price);
	}

	/**
	 * 查询光缆维护级别单价
	 * @param id
	 * @return
	 */
	public ExpenseUnitPrice getTypePriceById(String id){
		return dao.get(id);
	}

	
	/**
	 * 查询维护单价列表
	 * @param user
	 * @return
	 */
	public List getUnitPriceList(UserInfo user){
		return dao.getUnitPriceList(user);
	}
	
	/**
	 * 判断是否存在重复的光缆级别价格
	 * @return
	 */
	public boolean judgeIsHaveUnitPrice(ExpenseUnitPrice price){
		String conid = price.getContractorid();
		String cableLevel = price.getCableLevel();
		String exceptId = price.getId();
		String expenseType = price.getExpenseType();
		List list = null;
		if(expenseType.equals(ExpenseConstant.EXPENSE_TYPE_CABLE)){
			list = dao.judgeIsHaveUnitPrice(conid, cableLevel, exceptId, expenseType);
		}else{
			list = dao.judgeIsHavePipeUnitPrice(conid, exceptId, expenseType);
		}
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}


	@Override
	protected HibernateDao<ExpenseUnitPrice, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}



}
