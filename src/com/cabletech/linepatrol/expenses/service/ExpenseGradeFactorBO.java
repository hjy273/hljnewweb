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
	 * ������Ϣ
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
	 * �޸ķּ�ȡ��ϵ��
	 * @param f
	 */
	public void updateFactor(ExpenseGradeFactor factor){
		try{
			dao.updateFactor(factor);
		}catch(Exception e){
			logger.error("�޸ķּ�ȡ��ϵ�������쳣:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * ����������ά�����ȷּ�ȡ�ѱ�׼
	 * @param user
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getFacotorList(UserInfo user){
		return dao.getFacotorList(user);
	}
	
	
	/**
	 * ��ѯ
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public ExpenseGradeFactor getLengthPriceById(String id){
		return dao.getGradeFactor(id);
	}
	/**
	 * �ж��Ƿ���ͬ��ϵ��
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
