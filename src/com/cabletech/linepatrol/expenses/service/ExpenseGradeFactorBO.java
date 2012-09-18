package com.cabletech.linepatrol.expenses.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.expenses.dao.ExpenseGradeFactorDAO;
import com.cabletech.linepatrol.expenses.model.ExpenseGradeFactor;

@Service
@Transactional
public class ExpenseGradeFactorBO extends EntityManager<ExpenseGradeFactor, String> {
	@Resource(name="expenseGradeFactorDAO")
	private ExpenseGradeFactorDAO dao;
	
	@Transactional(readOnly=true)
	public  ExpenseGradeFactor getFactorById(String id){
		return dao.get(id);
	}
	
	/**
	 * 保存信息
	 * @param f
	 */
	public void saveFactor(ExpenseGradeFactor f){
		try{
			dao.saveFactor(f);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改分级取费系数
	 * @param f
	 */
	public void updateFactor(ExpenseGradeFactor factor){
		try{
			dao.updateFactor(factor);
		}catch(Exception e){
			logger.error("修改分级取费系数出现异常:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 城域网光缆维护长度分级取费标准
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getFacotorList(UserInfo user){
		return dao.getFacotorList(user);
	}
	
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public ExpenseGradeFactor getLengthPriceById(String id){
		return dao.getGradeFactor(id);
	}
	/**
	 * 判断是否相同的系数
	 * @param conid
	 * @param lengthStart
	 * @param lengthEnd
	 * @param exceptId
	 * @return
	 */
	@Transactional(readOnly=true)
	public boolean judgeIsHaveFactor(String conid,ExpenseGradeFactor factor){
		int lengthStart = factor.getGrade1();
		int lengthEnd = factor.getGrade2();
		String exceptId = factor.getId();
		return dao.judgeIsHaveFactor(conid, lengthStart, lengthEnd, exceptId);
	}

	@Override
	protected HibernateDao<ExpenseGradeFactor, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
