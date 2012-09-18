package com.cabletech.linepatrol.expenses.dao;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.expenses.service.ExpenseConstant;
import com.cabletech.linepatrol.resource.model.Pipe;
import com.cabletech.linepatrol.resource.model.RepeaterSection;


@Repository
public class ExpensesDAO extends HibernateDao{


	public void createExpenses(){

	}

	/**
	 * 查询代维
	 * @param user
	 * @return
	 */
	public Contractor getContractorById(String contractorID){
		String hql = " from Contractor c where contractorID='"+contractorID+"'";
	//	return (Contractor) findByUnique(contractorID, contractorID);
		return (Contractor) getSession().createQuery(hql).uniqueResult();
	}

	/**
	 * 移动查询代维
	 * @param deptid
	 * @return
	 */
	public List<Contractor> getConstractorByDeptId(String regionid){
		String hql = "from Contractor where regionid='"+regionid+"' and state is null";
		return find(hql);
	}


	/**
	 * 获取代维维护光缆长度
	 * @param contractorId
	 * @param month
	 * @return
	 */
	public double getCableLength(String contractorId,String month){
		String hql = "select sum(rep.grossLength) from RepeaterSection rep" +
		" where rep.maintenanceId='"+contractorId+"' and rep.isCheckOut='y' and scrapState='false'";
		hql+=" and rep.finishtime<=to_date('"+month+"', 'yyyy/MM')";
		Object cablelen = getSession().createQuery(hql).uniqueResult();
		if(cablelen!=null){
			return Double.parseDouble(cablelen.toString());
		}
		return 0;
	}

	/**
	 * 获取城区光缆信息
	 * @param contractorid代维
	 * @param month 交维时间在此月之前
	 * @return  r.scetion='1'城区
	 */
	public List<RepeaterSection> getCablesByContractorid(String contractorid,String month){
		String hql=getCableSql(contractorid,month);
		hql+="and r.scetion='1'" +
		" order by finishtime,kid";
		return find(hql);
	}

	public List<String> getCablePlaces(String contractorid,String month){
		String hql = "select distinct place";
		hql+=getCableSql(contractorid,month);
		hql+="and r.scetion='2' " ;
		return find(hql);
	}

	/**
	 * 根据区县获取光缆信息
	 * @param contractorid
	 * @param month
	 * @param place
	 * @return
	 */
	public List<RepeaterSection> getCableByPlace(String contractorid,String month,String place){
		String hql=getCableSql(contractorid,month);
		hql+="and r.scetion='2' and r.place=?" +
		" order by finishtime,kid";
		return find(hql,place);
	}

	public String getCableSql(String contractorid,String month){
		String hql = " from RepeaterSection r where r.maintenanceId='"+contractorid+"'";
		hql+=" and r.finishtime<=to_date('"+month+"', 'yyyy/MM') and r.isCheckOut='y' and scrapState='false' " ;
		return hql;
	}
	
	/**
	 * 获取每个代维维护管道的长度
	 * @param contractorid
	 * @param month
	 * @return
	 */
	public double getPipeLength(String contractorid,String month){
		String hql = "select sum(r.pipeLengthHole)  from Pipe r where r.maintenanceId='"+contractorid+"'";
		hql+=" and r.finishTime<=to_date('"+month+"', 'yyyy/MM') and r.isCheckOut='y' " ;
		hql+=" and r.pipeLengthHole>0";
		Object cablelen = getSession().createQuery(hql).uniqueResult();
		if(cablelen!=null){
			return Double.parseDouble(cablelen.toString());
		}
		return 0;
	}
	
	
	/**
	 * 获取每个代维维护管道
	 * @param contractorid
	 * @param month
	 * @return
	 */
	public List<Pipe> getPipe(String contractorid,String month){
		String hql = " from Pipe r where r.maintenanceId='"+contractorid+"'";
		hql+=" and r.finishTime<=to_date('"+month+"', 'yyyy/MM') and r.isCheckOut='y' " ;
		hql+=" and r.pipeLengthHole>0";
		return  getSession().createQuery(hql).list();
	}
	
	/**
	 * 获取每个代维维护管道的长度
	 * @param contractorid
	 * @param month
	 * @return
	 */
	public int getPipeNum(String contractorid,String month){
		String hql = "select count(*)  from Pipe r where r.maintenanceId='"+contractorid+"'";
		hql+=" and r.finishTime<=to_date('"+month+"', 'yyyy/MM') and r.isCheckOut='y' " ;
		hql+=" and r.pipeLengthHole>0";
		Object cableNum = getSession().createQuery(hql).uniqueResult();
		if(cableNum!=null){
			return Integer.parseInt(cableNum.toString());
		}
		return 0;
	}
	

	/**
	 * 根据代维单位，系数查询改级别下的单价
	 * @param contractorid
	 * @param factor
	 * @return
	 */
	public List getUnitPrice(String contractorid,double factor){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select up.contractor_id,up.cable_level, ");
		sb.append("up.unit_price*"+factor+" unit_price,");
		sb.append("  0 as maintenance_length, 0 as maintenance_num,");
		sb.append(" 0 as maintain_money,0 month_price ");
		sb.append(" from lp_expense_unitprice up");
		sb.append(" where up.contractor_id=? and up.expense_type=?");
		values.add(contractorid);
		values.add(ExpenseConstant.EXPENSE_TYPE_CABLE);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 
	 * @param factorid 取费系数的主键id
	 * @param yearMonth
	 * @return
	 */
	public List getExpensesByFactorid(String contractorid,String factorid,String yearmonth){
		StringBuffer sb = new StringBuffer();
		StringBuffer sbcondition = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select up.contractor_id,up.cable_level, ");
		sb.append("gradem.unit_price,");
		sb.append("gradem.maintenance_length,gradem.maintenance_num,");
		sb.append(" trunc(maintenance_length*gradem.unit_price,2) as maintain_money");
		sb.append(",em.month_price ");
		sbcondition.append(" from lp_expense_gradem gradem,lp_expense_gradefactor gf, ");
		sbcondition.append("lp_expense_unitprice up,lp_expense_month em");
		sbcondition.append(" where gradem.unit_price_id = up.id and ");
		sbcondition.append(" gf.id=gradem.grade_factor_id and ");
		sbcondition.append("em.id=gradem.expense_id and gradem.grade_factor_id='"+factorid+"' ");
		sbcondition.append("and em.yearmonth=to_date('"+yearmonth+"','yyyy/MM')");
		sbcondition.append(" and em.expense_type='"+ExpenseConstant.EXPENSE_TYPE_CABLE+"'");
		
		StringBuffer unionsb = new StringBuffer();
		unionsb.append(" union ");
		unionsb.append(" select  '' contractor_id, up.cable_level,");
		unionsb.append(" (up.unit_price*gf.factor) unit_price,");
		unionsb.append(" 0 maintenance_length,0 maintenance_num,");
		unionsb.append(" 0 maintain_money, 0 month_price  ");
		unionsb.append(" from lp_expense_unitprice up,lp_expense_gradefactor gf");
		unionsb.append(" where gf.id=? ");
		unionsb.append(" and up.contractor_id=? and  up.cable_level not in(");
		unionsb.append(" select up.cable_level ");
		values.add(factorid);
		values.add(contractorid);
		unionsb.append(sbcondition.toString());
		unionsb.append(" )");
		sb.append(sbcondition.toString());
		sb.append(unionsb.toString());
		logger.info("查询费用sql:"+sb.toString());
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}


	/**
	 * 查询月费用与不同光缆级别长度、数量、费用的 合计
	 * @param contractorid 代维公司
	 * @return
	 */
	public List getExpenseAmount(String contractorid,String yearmonth){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select m.id, m.month_price ,m.rectify_money,m.cable_num,m.cable_length,");
		sb.append(" p.cable_level,m.deduction_money, m.remark,");
		sb.append(" decode(p.cable_level,'1','一干','2','骨干','3','汇聚层',");
		sb.append(" '4','接入层（小于144芯）','4A','接入层（大于144芯）') cable_type,");
		sb.append("sum(case p.cable_level when '1' then g.maintenance_num else 0 end) num1,");
		sb.append("sum(case p.cable_level when '2' then g.maintenance_num else 0 end) num2,");
		sb.append("sum(case p.cable_level when '3' then g.maintenance_num else 0 end) num3,");
		sb.append("sum(case p.cable_level when '4' then g.maintenance_num else 0 end) num4,");
		sb.append("sum(case p.cable_level when '4A' then g.maintenance_num else 0 end) num4A,");
		sb.append("sum(case p.cable_level when '1' then g.maintenance_length else 0 end) len1,");
		sb.append("sum(case p.cable_level when '2' then g.maintenance_length else 0 end) len2,");
		sb.append("sum(case p.cable_level when '3' then g.maintenance_length else 0 end) len3,");
		sb.append("sum(case p.cable_level when '4' then g.maintenance_length else 0 end) len4,");
		sb.append("sum(case p.cable_level when '4A' then g.maintenance_length else 0 end) len4A,");
		sb.append("sum(case p.cable_level when '1' then g.unit_price*g.maintenance_length else 0 end) price1,");
		sb.append("sum(case p.cable_level when '2' then g.unit_price*g.maintenance_length else 0 end) price2,");
		sb.append("sum(case p.cable_level when '3' then g.unit_price*g.maintenance_length else 0 end) price3,");
		sb.append("sum(case p.cable_level when '4' then g.unit_price*g.maintenance_length else 0 end) price4,");
		sb.append("sum(case p.cable_level when '4A' then g.unit_price*g.maintenance_length else 0 end) price4A");
		sb.append(" from lp_expense_month m ,lp_expense_gradem g,lp_expense_unitprice p");
		sb.append(" where g.expense_id=m.id and g.unit_price_id=p.id ");
		sb.append(" and m.contractor_id=? and m.expense_type=?");
		sb.append(" and m.yearmonth=to_date('"+yearmonth+"','yyyy/MM')");
		sb.append(" group by m.id,m.month_price,m.rectify_money,m.cable_num,m.cable_length,");
		sb.append(" cable_level ,m.deduction_money, m.remark ");
		sb.append(" order by cable_level");
		values.add(contractorid);
		values.add(ExpenseConstant.EXPENSE_TYPE_CABLE);
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}
	
	/**
	 * 根据开始结束日期查询代维月费用基本信息
	 * @param contractorid
	 * @param begiondate
	 * @param endtime
	 * @return
	 */
	public List getExpenses(String contractorid,String begiondate,String endtime){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select up.contractor_id,up.cable_level,");
		sb.append(" decode(up.cable_level,'1','一干','2','骨干','3','汇聚层',");
		sb.append(" '4','接入层（小于144芯）','4A','接入层（大于144芯）') ca_level,");
		sb.append(" gradem.id gradem_id,");
		sb.append(" gradem.maintenance_length,gradem.maintenance_num,m.cable_length,");
		sb.append(" m.cable_num,m.rectify_money,m.yearmonth");
		sb.append(" from lp_expense_gradem gradem,lp_expense_unitprice up ,lp_expense_month m");
		sb.append(" where gradem.expense_id=m.id and up.id=gradem.unit_price_id");
		sb.append(" and m.yearmonth between (to_date('"+begiondate+"','yyyy/MM')) ");
		sb.append(" and (to_date('"+endtime+"','yyyy/MM'))");
		sb.append(" and m.contractor_id=? and m.expense_type=?");
		values.add(contractorid);
		values.add(ExpenseConstant.EXPENSE_TYPE_CABLE);
		logger.info("费用确认sql:contractorid:"+contractorid+" sql:"+sb.toString());
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}

	/**
	 * 根据开始结束日期查询代维月费用基本信息
	 * @param contractorid
	 * @param begiondate
	 * @param endtime
	 * @param expenseType 
	 * @return
	 */
	public Double getExpensesSum(String contractorid,String begiondate,String endtime, String expenseType){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select m.contractor_id,sum(m.rectify_money) as money_sum ");
		sb.append(" from lp_expense_month m");
		sb.append(" where m.yearmonth between (to_date('"+begiondate+"','yyyy/MM')) ");
		sb.append(" and (to_date('"+endtime+"','yyyy/MM'))");
		sb.append(" and m.contractor_id=? and m.expense_type=?");
		sb.append(" group by m.contractor_id ");
		values.add(contractorid);
		values.add(expenseType);
		List list=super.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
		if(list!=null&&!list.isEmpty()){
			DynaBean bean=(DynaBean)list.get(0);
			if(bean!=null){
				BigDecimal d=(BigDecimal)bean.get("money_sum");
				DecimalFormat df=new DecimalFormat("#.00");
				return new Double(df.format(d.doubleValue()));
			}
		}
		return new Double(0);
	}
}
