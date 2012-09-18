package com.cabletech.baseinfo.services;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.beans.UseTerminalBean;
import com.cabletech.baseinfo.dao.UseTerminalDAO;
import com.cabletech.commons.config.SysConfig;
import com.cabletech.commons.util.Conversion;

/**
 * ͳ���ֳ��豸ʹ����� ���������������� 1�������ŷ������� 2����Ѳ����� 3������������
 * 
 * @author Administrator
 * 
 */
public abstract class UseTerminalBO {
	private Logger logger = Logger.getLogger("UseTerminalBO");
	private UseTerminalDAO dao ;
	private SysConfig config ;
	protected UseTerminalBean queryCon;
	protected String commonsql = "select r.regionname,c.contractorname,p.patrolname , ut.MSID,ut.PATROLMANID,ut.SIMID,to_char(ut.STATEDMONTH,'yyyy-mm-dd') STATEDMONTH,ut.TOTALNUM,ut.PATROLNUM,ut.TROUBLENUM,ut.COLLECTNUM,ut.WATCHNUM,ut.OTHERNUM,ut.MONTHLYKM, to_char(ut.STATDATE,'yyyy-mm-dd') STATDATE,ut.onlinedays "
			+ " from STAT_TERMINALMONTHLYUSE ut,terminalinfo t,region r,patrolmaninfo p,contractorinfo c "
			+ " where t.simnumber = ut.simid and p.patrolid = ut.patrolmanid and t.regionid=r.regionid and t.contractorid=c.contractorid";

	public UseTerminalBO() {
		setDao(new UseTerminalDAO());
		setConfig(SysConfig.newInstance());
	}

	/**
	 * ��ð���ѯ��������(������������ 1�������ŷ������� 2����Ѳ����� 3������������) ���ݲ�ͬ������ͬ�ȼ�ͳ��ÿ����Χ�ڵ�����
	 * 
	 * @param querycon
	 *            ��ѯ����
	 * @return ���ظ��ȼ�����
	 */
	public Map getUseTerminal(){
		String sql = creatCondition(getGrade(),commonCondition(getQueryCon()), getField());		
		List list = getDao().getUseTerminal(sql);
		return Conversion.listToMap(list,"area","countvalue");
	}
	
	
	/**
	 * ���ݷ����ѯ������������ѯ������ѯ�豸ʹ������б�
	 * 
	 * @param querycon
	 *            ��ѯ����
	 * @param condition
	 *            ������ѯ����(ǰ50,��50)
	 * @param section
	 *            �����ѯ����
	 * @return ���ز�ѯ�豸ʹ������б�
	 */
	public List getUseTerminalCondition(String condition, String section){
		String sql = commonsql + createConditionSql(getQueryCon(),condition,section, getField());
		System.out.println("sql : " + sql);
		List list = getDao().getUseTerminal(sql);
		return list;
	}
	
	/**
	 * �����������豸������乫����ѯ����
	 * @param querycon ��ѯ����
	 * @return
	 */
	protected String commonCondition(UseTerminalBean querycon){
		String condition = " t.simnumber = ut.simid and t.regionid IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
			+ querycon.getRegionid()
			+ "') and"
			+ " statedMonth between to_date('"
			+ querycon.getYearMonth()
			+ "-01','yyyy-mm-dd') and last_day(to_date('"
			+ querycon.getYearMonth() + "-01','yyyy-mm-dd')) ";
		return condition;
	}
	/**
	 * ���ݲ�ѯ���͵ȼ���Ϣ�����ֶ���������������֯�������豸����sql��䡣 
	 * ��ѯ���Ͱ��� �����ŷ�����������Ѳ����̡�����������
	 * 
	 * @param gradeList
	 *            ��ѯ���ͼ���
	 * @param condition
	 *            ������ѯ����
	 * @param field
	 *            �ֶ���
	 * @return ���ز�ͬ�ȼ���ѯ���
	 */
	protected String creatCondition(List gradeList, String condition,
			String field) {
		String sqlConidtion = "";
		int size = gradeList.size();
		for (int i = 0; i < size; i++) {
			logger.info("list :" + gradeList.get(i));
			if (i == 0) {// ȡ��������͵�
				sqlConidtion += "(select '" + gradeList.get(i)
						+ "(δ����)' area,count(" + field + ") countvalue "
						+ "from STAT_TERMINALMONTHLYUSE ut,terminalinfo t "
						+ "where  ut." + field + "=" + gradeList.get(i)
						+ " and " + condition + ")";
			} else if (i == (size - 1)) {// ȡ��������ߵ�
				sqlConidtion += " union (select '" + gradeList.get(i) + "(����"
						+ gradeList.get(i) + ")����' area,count(" + field
						+ ") countvalue "
						+ "from STAT_TERMINALMONTHLYUSE ut,terminalinfo t "
						+ "where  ut." + field + ">" + gradeList.get(i)
						+ " and " + condition + ")";
			} else {
				sqlConidtion += " union (select '" + gradeList.get(i - 1) + "-"
						+ gradeList.get(i) + "(����" + gradeList.get(i - 1)
						+ ")' area,count(" + field + ") countvalue "
						+ "from STAT_TERMINALMONTHLYUSE ut,terminalinfo t "
						+ "where  ut." + field + ">" + gradeList.get(i - 1)
						+ " and " + field + "<=" + gradeList.get(i) + " and "
						+ condition + ")";
			}
		}
		return sqlConidtion;
	}

	

	/**
	 * ���������ѯ������������ѯ�������ֲ�ѯ���,�����������
	 * @param querycon ��ѯ����
	 * @param condition ��������
	 * @param section �����ѯ����
	 * @param field �ֶ���
	 * @return ����sql
	 */
	protected String createConditionSql(UseTerminalBean querycon,
			String condition, String section, String field) {
		String conditionsql = " and ut.STATEDMONTH between to_date('"
				+ querycon.getYearMonth()
				+ "-01','yyyy-mm-dd') and last_day(to_date('"
				+ querycon.getYearMonth()
				+ "-01','yyyy-mm-dd'))"
				+ " and t.regionid IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
				+ querycon.getRegionid() + "')  ";
		// �����ѯ
		logger.info("section :" + section);
		if (section != null && !"".equals(section)) {
			conditionsql += conditionSyncopate(section, field);
		}
		// String sq = ">50";
		// ������ѯ ǰ50 asc ;��50�� desc
		if (condition != null && !"".equals(condition)) {
			conditionsql += " and rownum  <= 50 ";
		}
		conditionsql += createOrderBy(condition,field);
		return conditionsql;
	}

	/**
	 * ��ϲ�ѯ����������䡣
	 * 
	 * @param condition
	 *            ��ѯ���� ������ʽ������0(δ����);200-500(����200)
	 * @param filedName
	 *            ��ѯ�ֶ�
	 * @return ����������䣬���� and
	 */
	protected String conditionSyncopate(String condition, String filedName) {
		int endPlace = condition.indexOf("(");
		String start = condition.substring(0, endPlace);
		String conn = "";
		if ("0".equals(start)) {
			conn = " and ut." + filedName + " ='" + start + "'";
		} else {
			int middlePlace = condition.indexOf("-");
			if (middlePlace != -1) {
				conn = " and ut." + filedName + " > '"
						+ condition.substring(0, middlePlace) + "' and  ut." + filedName + " <='"
						+ condition.substring(middlePlace + 1, endPlace) + "'";
			} else {
				conn = " and ut." + filedName + " > " + start;
			}
		}
		return conn;
	}
	
	/**
	 * ������������condition��ָ���ֶ�field����������䡣
	 * 
	 * @param condition
	 *            ���������
	 * @param field Ҫ������ֶ�
	 * @return sql ����order by��䡣
	 */
	protected String createOrderBy(String condition, String field) {
		return " order by ut."
				+ field
				+ " "
				+ ((condition != null && !"".equals(condition)) ? condition
						: "");
		
	}
	
	/**
	 * Ҫͳ�Ƶ��ֶ���
	 * @return 
	 */
	public abstract String getField();
	/**
	 * ��÷���ȼ�
	 * @return
	 */
	public abstract List getGrade();
	public UseTerminalBean getQueryCon(){
		System.out.println(queryCon.toString());
		return this.queryCon;
	}
	
	public UseTerminalDAO getDao() {
		return dao;
	}

	public void setDao(UseTerminalDAO dao) {
		this.dao = dao;
	}

	public SysConfig getConfig() {
		return config;
	}

	public void setConfig(SysConfig config) {
		this.config = config;
	}

}
