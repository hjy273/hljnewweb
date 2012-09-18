package com.cabletech.analysis.services;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;

import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;

/**
 * ���д�άΪ��λ��ר�Ŵ�����
 */
public class WorkInfoHistoryBOCityContractor extends WorkInfoHistoryBaseBO {	
	/**
	 * ����"��ʾ�������ָ��������Ϣ"��SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineInfoGivenDaySql() {
		sql = "select  s.totalnum,s.onlinenumber,s.dailykm "
				+ "from stat_conmessagedaily s " + "where s.contractorid = '"
				+ rangeID + "' and " + "s.operatedate = TO_DATE ('"
				+ boParam.getGivenDate() + "', 'YYYY-MM-DD')";
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
				+ "and so.contractorid = '" + rangeID + "' "
				+ "group by intersectpoint order by operatedate";
		return sql;
	}
	
	/**
	 * �����õ�������ʱ����ֹ��Χ��ÿ������������ֲ���SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineNumberSql() {
		String startDate = boParam.getBean().getStartDate();
		String endDate = boParam.getBean().getEndDate();
		sql = "select to_char(s.operatedate, 'YYYY-MM-DD') operatedate,"
				+ "sum(s.onlinenumber) onlinenumber "
				+ "from stat_conmessagedaily s " + "where s.contractorid = '"
				+ rangeID + "' and " + "s.operatedate >=TO_DATE ('"
				+ startDate + "', 'YYYY-MM-DD') "
				+ "and s.operatedate <=TO_DATE ('" + endDate
				+ "', 'YYYY-MM-DD') " + "group by s.operatedate "
				+ "order by operatedate";
		return sql;
	}

	/**
	 * ����"��ʾ�������ָ�������ʱ����Ϣ"��SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineInfoGivenHourSql() {
		// //���д�ά�û�ѡ�����ڴ�ά��˾ʱ���Ż����µ�URL���ӣ�Ҳ�Ͳ��д˹���
		sql += "select so.smtotalnum,so.onlinenumber,so.onlinesiminfo "
				+ "from STAT_ONLINENUMBER so "
				+ "where so.intersectpoint = to_date('" + boParam.getGivenDateAndTime()
				+ "','yyyy-mm-dd hh24:mi:ss') "
				+ "and so.contractorid='"
				+ boParam.getUserInfo().getDeptID() + "'";
		return sql;
	}

	/**
	 * ���÷���beanֵ
	 * @param list ����ļ���ת����HistoryTimeInfoBean��list������onlinesiminfo����Ϣ
	 * @param bean �û������ϲ�ѯʱ���������bean
	 */
	public void setBackBeanValue(List list, HistoryTimeInfoBean bean) {
		int smnum = 0;
		int onlinenum = 0;
		smnum = Integer.parseInt(((BasicDynaBean) list.get(0))
				.get("smtotalnum").toString());
		onlinenum = Integer.parseInt(((BasicDynaBean) list.get(0)).get(
				"onlinenumber").toString());
		String onlineInfo = ((BasicDynaBean) list.get(0)).get("onlinesiminfo")
				.toString();
		bean.setOnlineInfo(onlineInfo);
		bean.setSmTotal(String.valueOf(smnum)); // �ö���������bean
		bean.setOnlineNumber(String.valueOf(onlinenum)); // ����������������bean
	}

	/**
	 * �����õ��������Ͷ���SQL
	 * @return ��֯�õ�SQL
	 */
	public String createSMAllTypeSql() {
		String startDate = "";
		String endDate = "";
		String givenDate = boParam.getGivenDate();
		HistoryWorkInfoConditionBean bean = boParam.getBean();
		if (bean != null) {
			startDate = bean.getStartDate(); // �õ��û��������ʼ����
			endDate = bean.getEndDate(); // �õ��û��������ֹ����
		}
		sql = "select p.patrolname rangename,t.simid,"
				+ "SUM(t.totalnum) totalnum," + "SUM(t.patrolnum) patrolnum,"
				+ "SUM(t.troublenum) troublenum,"
				+ "SUM(t.collectnum) collectnum," + "SUM(t.watchnum) watchnum,"
				+ "SUM(t.othernum) othernum,SUM(t.dailykm) dailykm,"
				+ "sum(t.avgsendtime) avgsendtime,"
				+ "sum(t.avgsenddistance) avgsenddistance "
				+ "from stat_messagedaily t, patrolmaninfo p "
				+ "where p.patrolid = t.patrolmanid " + "and p.state is null ";
		// ����û�ѡ���˾���ĳһ��
		if (bean == null && givenDate != null && !"".equals(givenDate)) {
			sql += "AND t.operatedate = TO_DATE ('" + givenDate
					+ "', 'yyyy-mm-dd') ";
		} else {
			sql += "AND (t.operatedate between TO_DATE ('" + startDate
					+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
					+ "', 'yyyy-mm-dd')) ";
		}
		sql += "and p.parentid = '" + boParam.getSmRangeID() + "' "
				+ "GROUP BY t.simid,p.patrolname "
				+ "order by p.patrolname, t.simid";
		return sql;
	}

}
