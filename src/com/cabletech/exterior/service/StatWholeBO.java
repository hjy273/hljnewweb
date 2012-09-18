package com.cabletech.exterior.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.analysis.dao.StatForWholeDAO;
import com.cabletech.commons.exception.SubtrahendException;
import com.cabletech.commons.util.DateUtil;
/**
 * StatWholeBO
 * 提供全省近六个月情况对比数据
 * @author Administrator
 *
 */
public class StatWholeBO  {
	private StatForWholeDAO dao = new StatForWholeDAO();
	private Logger logger = Logger.getLogger("StatWholeBO");
	static final int space = -11;//间隔5
	/**
	 * 根据查询月份获得该月份以前几个月的对比情况.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return List
	 */
	public List getMonthlyContrast(String year, String month) {
		String sql = "";
			sql = "select to_char(p.factdate, 'mm') startmonth,sum(p.planpoint) planpoint,sum(p.factpoint) factpoint" +
			" from plan_cstat p,contractorinfo con" +
			" where p.CDEPTID = con.contractorid " +
			" and p.factdate between add_months(to_date('"+year+"-"+month+"', 'yyyy-mm'),-"+space+") and last_day(to_date('"+year+"-"+month+"', 'yyyy-mm'))" +
			" group by p.factdate" +
			" order by to_char(p.factdate,'yyyy-mm')" ;
			logger.info("sql "+sql);
			return dao.queryBeans(sql);
	}
	/**
	 * 指定日期减去指定的月数
	 * @param date 日期
	 * @return 返回日期字符串
	 * @throws SubtrahendException 
	 */
	public String dateSubtract(Date date) throws SubtrahendException{
		return DateUtil.getAfterNMonth(date, space);//
	}
	/**
	 * 指定日期减去指定的月数
	 * @param date 日期
	 * @param subtract 月数，减去的月数
	 * @return 返回日期字符串
	 */
	public String dateSubtract(Date date,int subtract){
			return DateUtil.getAfterNMonth(date, subtract);
	}
}
