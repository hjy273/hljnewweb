package com.cabletech.linepatrol.expenses.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.expenses.beans.BudgetFactorBean;
import com.cabletech.linepatrol.expenses.dao.BudgeExpeseDAO;
import com.cabletech.linepatrol.expenses.model.ExpenseBudget;
import com.cabletech.linepatrol.expenses.model.ExpenseGradeFactor;

@Service
@Transactional
public class BudgetBO extends EntityManager<ExpenseBudget, String> {
	@Resource(name="budgeExpeseDAO")
	private BudgeExpeseDAO dao;
	
	@Transactional(readOnly=true)
	
	
	public  ExpenseBudget getBudgetById(String id){
		return dao.get(id);
	}
	
	/**
	 * ������Ϣ
	 * @param f
	 */
	public void saveBudget(BudgetFactorBean bean){
		try{
			ExpenseBudget budget = new ExpenseBudget();
			BeanUtil.objectCopy(bean,budget);
			dao.saveBudget(budget);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �޸�Ԥ��
	 * @param f
	 */
	public void updateBudget(BudgetFactorBean bean){
		try{
			ExpenseBudget budget = dao.get(bean.getId());
			BeanUtil.objectCopy(bean,budget);
			dao.updateBudget(budget);
		}catch(Exception e){
			logger.error("�޸�Ԥ������쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * �ж��Ƿ��Ѿ����ڸ�Ԥ��
	 * @param contratorid
	 * @param year
	 * @param type
	 * @return
	 */
	public boolean judgeIsHaveBudget(BudgetFactorBean bean){
		String contratorid = bean.getContractorId();
		String year = bean.getYear();
		String type = bean.getExpenseType();
		String id = bean.getId();
		List list =  dao.judgeIsHaveBudget(contratorid, year, type, id);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * ��ѯԤ��
	 * @return
	 */
	public List getBudgets(){
		return dao.getBudgets();
	}
	
	
	
	@Override
	protected HibernateDao<ExpenseBudget, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
