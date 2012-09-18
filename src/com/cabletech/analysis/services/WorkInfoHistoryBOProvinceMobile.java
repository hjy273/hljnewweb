package com.cabletech.analysis.services;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;

/**
 * ��ʡ�ƶ�Ϊ��λ��ר�Ŵ�����
 */
public class WorkInfoHistoryBOProvinceMobile extends WorkInfoHistoryBaseBO {
	/**
	 * �����õ�������ʱ����ֹ��Χ��ÿ������������ֲ���SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineNumberSql() {
		HistoryWorkInfoConditionBean bean = boParam.getBean();
		String startDate = bean.getStartDate();
		String endDate = bean.getEndDate();
		sql = "select to_char(s.operatedate, 'YYYY-MM-DD') operatedate,"
				+ "sum(s.onlinenumber) onlinenumber "
				+ "from stat_regionmessagedaily s where "
				+ "s.operatedate >=TO_DATE ('" + startDate
				+ "', 'YYYY-MM-DD') " + "and s.operatedate <=TO_DATE ('"
				+ endDate + "', 'YYYY-MM-DD') " + "group by s.operatedate "
				+ "order by operatedate";
		return sql;
	}
	
	/**
	 * ����"��ʾ�������ָ��������Ϣ"��SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineInfoGivenDaySql() {
		sql = "select sum(s.totalnum) totalnum,"
				+ "sum(s.onlinenumber) onlinenumber,"
				+ "sum(s.dailykm) dailykm "
				+ "from stat_regionmessagedaily s where "
				+ "s.operatedate = TO_DATE ('" + boParam.getGivenDate() + "', 'YYYY-MM-DD')";
		return sql;
	}
	
	/**
	 * �����õ�������ѡĳһ���ʱ�����������ֲ���SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineNumberForHoursSql() {
		String givenDate = boParam.getGivenDate();
		sql = "Select TO_CHAR(so.intersectpoint,"
				+ "'yyyy-mm-dd hh24:mi:ss') operatedate,"
				+ "SUM(so.onlinenumber) onlinenumber "
				+ "from stat_onlinenumber so "
				+ "where (so.intersectpoint between to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') " + "and to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') + 1) "
				+ "group by intersectpoint order by operatedate";
		return sql;
	}

	/**
	 * ����"��ʾ�������ָ�������ʱ����Ϣ"��SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineInfoGivenHourSql() {
		// ��ʡ�ƶ��û�ѡ��ȫʡ����ʱ���Ż����µ�URL���ӣ�Ҳ�Ͳ��д˹���
		sql += "select so.regionid,r.regionname rangename,so.smtotalnum,"
				+ "sum(so.onlinenumber) totalnumber "
				+ "from STAT_ONLINENUMBER so,region r "
				+ "where so.intersectpoint = to_date('" + boParam.getGivenDateAndTime()
				+ "','yyyy-mm-dd hh24:mi:ss') "
				+ "and so.regionid = r.regionid and r.state is null "
				+ "and substr (r.regionid, 3, 4) != '1111' "
				+ "and substr (r.regionid, 3, 4) != '0000' "
				+ "group by so.regionid,so.smtotalnum,r.regionname "
				+ "order by so.regionid";
		return sql;
	}

	/**
	 * �����õ��������Ͷ���SQL
	 * @return ��֯�õ�SQL
	 */
	public String createSMAllTypeSql() {
		HistoryWorkInfoConditionBean bean = boParam.getBean();
		String givenDate = boParam.getGivenDate();
		String startDate = "";
		String endDate = "";
		if (bean != null) {
			startDate = bean.getStartDate(); // �õ��û��������ʼ����
			endDate = bean.getEndDate(); // �õ��û��������ֹ����
		}
		sql = "SELECT sr.regionid rangeid,r.regionname rangename,"
				+ "SUM(sr.totalnum) totalnum," + "SUM(sr.patrolnum) patrolnum,"
				+ "SUM(sr.watchnum) watchnum,"
				+ "SUM(sr.troublenum) troublenum,"
				+ "SUM(sr.collectnum) collectnum,"
				+ "SUM(sr.othernum) othernum, SUM(sr.dailykm) dailykm "
				+ "FROM STAT_REGIONMESSAGEDAILY sr, REGION r "
				+ "WHERE sr.regionid = r.regionid  ";
		// ����û�ѡ���˾���ĳһ��
		if (bean == null && givenDate != null && !"".equals(givenDate)) {
			sql += "AND sr.operatedate = TO_DATE ('" + givenDate
					+ "', 'yyyy-mm-dd') ";
		} else {
			sql += "AND (sr.operatedate between TO_DATE ('" + startDate
					+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
					+ "', 'yyyy-mm-dd')) ";
		}
		sql += "GROUP BY sr.regionid,r.regionname " + "ORDER BY r.regionname";
		return sql;
	}
}
