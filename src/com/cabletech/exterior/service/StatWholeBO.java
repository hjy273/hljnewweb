package com.cabletech.exterior.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.analysis.dao.StatForWholeDAO;
import com.cabletech.commons.exception.SubtrahendException;
import com.cabletech.commons.util.DateUtil;
/**
 * StatWholeBO
 * �ṩȫʡ������������Ա�����
 * @author Administrator
 *
 */
public class StatWholeBO  {
	private StatForWholeDAO dao = new StatForWholeDAO();
	private Logger logger = Logger.getLogger("StatWholeBO");
	static final int space = -11;//���5
	/**
	 * ���ݲ�ѯ�·ݻ�ø��·���ǰ�����µĶԱ����.
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
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
	 * ָ�����ڼ�ȥָ��������
	 * @param date ����
	 * @return ���������ַ���
	 * @throws SubtrahendException 
	 */
	public String dateSubtract(Date date) throws SubtrahendException{
		return DateUtil.getAfterNMonth(date, space);//
	}
	/**
	 * ָ�����ڼ�ȥָ��������
	 * @param date ����
	 * @param subtract ��������ȥ������
	 * @return ���������ַ���
	 */
	public String dateSubtract(Date date,int subtract){
			return DateUtil.getAfterNMonth(date, subtract);
	}
}
