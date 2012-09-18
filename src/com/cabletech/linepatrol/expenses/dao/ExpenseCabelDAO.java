package com.cabletech.linepatrol.expenses.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.expenses.model.ExpenseCable;
import com.cabletech.linepatrol.expenses.service.ExpenseConstant;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;


@Repository
public class ExpenseCabelDAO extends HibernateDao<ExpenseCable,String>{

	
	/**
	 * 关联光缆
	 * @param expenseid 月费用主键
	 * @return
	 */
	public List getExpenseCableBy(String expenseid){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select rep.segmentname,");
		sb.append(" decode(up.cable_level,'1','一干','2','骨干','3','汇聚层',");
		sb.append(" '4','接入层') cable_level,");
		sb.append(" decode(rep.scetion,'1','城区','2','郊区') scetion,");
		sb.append(" to_char(rep.finishtime,'yyyy/MM') finishtime, ");
		sb.append(" rep.grosslength,rep.corenumber ");
		sb.append(" from lp_expense_cable cable,");
		sb.append(" lp_expense_gradem g,repeatersection rep,lp_expense_month m ");
		sb.append(" where g.id=cable.gradem_id and cable.cable_id=rep.kid");
		sb.append(" and m.id=? and m.id=g.expense_id");
		values.add(expenseid);
		sb.append("order by scetion,cable_level");
		return  this.getJdbcTemplate().queryForBeans(sb.toString(), values.toArray());
	}
}
