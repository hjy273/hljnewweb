package com.cabletech.linepatrol.expenses.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.expenses.model.ExpenseGradeFactor;

/*
 * 分级取费系数
 */
@Repository
public class ExpenseGradeFactorDAO extends HibernateDao<ExpenseGradeFactor,String>{
	
	public void saveFactor(ExpenseGradeFactor f){
		save(f);
	}
	
	public void updateFactor(ExpenseGradeFactor f){
		getSession().update(f);
	}

	public List<ExpenseGradeFactor> getGradeFactors(String contractorid){
		String hql="from ExpenseGradeFactor where contractorid=? order by grade1";
		return find(hql,contractorid);
	}
	
	public ExpenseGradeFactor getGradeFactor(String id){
		String hql="from ExpenseGradeFactor where id='"+id+"'";
		return (ExpenseGradeFactor) getSession().createQuery(hql).uniqueResult();
	}
	
	/**
	 * 城域网光缆维护长度分级取费标准
	 * @param user
	 * @return
	 */
	public List getFacotorList(UserInfo user){
		StringBuffer sb = new StringBuffer();
		sb.append(" select e.id id,contractorname,e.factor,explain,");
		sb.append(" decode(expense_type,'0','光缆','管道') expense_type,");
		sb.append("(e.grade1 || '至' ||  e.grade2) cablelen");
		sb.append(" from lp_expense_gradefactor e ,contractorinfo c");
		sb.append(" where e.contractor_id=c.contractorid ");
		sb.append("  and c.regionid ='"+user.getRegionid()+"'");
		sb.append(" order by contractor_id");
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), null);
	}
	
	/**
	 * 判断是否相同的系数
	 * @param conid
	 * @param lengthStart
	 * @param lengthEnd
	 * @param exceptId
	 * @return
	 */
	public boolean judgeIsHaveFactor(String conid,int lengthStart,int lengthEnd,String exceptId){
		String hql="from ExpenseGradeFactor e  " +
				"where e.contractorid='"+conid+"'" +
						" and grade1='"+lengthStart+"' and grade2='"+lengthEnd+"' ";
		if(exceptId!=null && !"".equals(exceptId)){
			hql+=" and e.id!='"+exceptId+"'";
		}
		List list = this.find(hql);
		if(list!=null && list.size()>0){
			return true;
		}
		return false;
	}


}
