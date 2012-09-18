package com.cabletech.linepatrol.expenses.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.expenses.model.ExpenseGradeFactor;
import com.cabletech.linepatrol.expenses.model.ExpenseUnitPrice;
import com.cabletech.linepatrol.expenses.service.ExpenseConstant;

/**
 * 维护单价
 * @author Administrator
 *
 */
@Repository
public class ExpenseUnitPriceDAO extends HibernateDao<ExpenseUnitPrice,String>{

	public void editUnitPrice(ExpenseUnitPrice price){
		getSession().update(price);
	}

	/**
	 * 根据代维查询维护单价列表
	 * @param contractorid
	 * @return
	 */
	public List<ExpenseUnitPrice> getPrices(String contractorid,String expenseType){
		String hql = "from ExpenseUnitPrice p where p.contractorid=? and p.expenseType=?";
		return find(hql,contractorid,expenseType);
	}
	
	public ExpenseUnitPrice getUnitPriceById(String id){
		String hql="from ExpenseUnitPrice where id='"+id+"'";
		return (ExpenseUnitPrice) getSession().createQuery(hql).uniqueResult();
	}

	/**
	 * 判断是否存在重复的光缆级别价格
	 * @param conid 代维单位系统id
	 * @param cableLevel 光缆级别
	 *  @param exceptId 光缆级别价格id，修改时用到
	 *  @param expenseType 费用类型
	 * @return
	 */
	public List judgeIsHaveUnitPrice(String conid,String cableLevel,
			String exceptId,String expenseType){
		String hql="from ExpenseUnitPrice e  " +
		"where e.contractorid=? and cableLevel=? and expenseType=?";
		if(exceptId!=null && !"".equals(exceptId)){
			hql+=" and e.id!='"+exceptId+"'";
		}
		return this.find(hql,conid,cableLevel,expenseType);
	}

	/**
	 * 判断是否存在管道价格
	 * @param conid 代维单位系统id
	 * @param exceptId 光缆级别价格id，修改时用到
	 * @param expenseType 费用类型
	 * @return
	 */
	public List judgeIsHavePipeUnitPrice(String conid,
			String exceptId,String expenseType){
		String hql="from ExpenseUnitPrice e  " +
		"where e.contractorid=? and expenseType=?";
		if(exceptId!=null && !"".equals(exceptId)){
			hql+=" and e.id!='"+exceptId+"'";
		}
		return  this.find(hql,conid,expenseType);
	}




	public List getUnitPriceList(UserInfo user){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select e.id id,contractorname,unit_price,explan,");
		sb.append(" decode(expense_type,'0','光缆','管道') expense_type,");
		sb.append(" decode(cable_level,'1','一干','2','骨干',");
		sb.append(" '3','汇聚','4','接入(144芯及以下)','4A','接入(144芯以上)') cable_level ");
		sb.append(" from lp_expense_unitprice e ,contractorinfo c");
		sb.append(" where e.contractor_id=c.contractorid ");
		sb.append(" and c.regionid ='"+user.getRegionid()+"'");
		sb.append(" order by contractor_id");
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), null);
	}


	public Map<String,ExpenseUnitPrice> getPricesMap(String contractorid){
		String expenstType = ExpenseConstant.EXPENSE_TYPE_CABLE;
		Map<String,ExpenseUnitPrice> map = new HashMap<String,ExpenseUnitPrice>();
		List<ExpenseUnitPrice> priceList = getPrices(contractorid,expenstType);
		for(ExpenseUnitPrice price:priceList){
			map.put(price.getCableLevel(), price);
		}
		return map;
	}


}
