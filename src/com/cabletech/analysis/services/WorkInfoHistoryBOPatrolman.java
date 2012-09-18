package com.cabletech.analysis.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;

/**
 * ��Ѳ����Ϊ��λ��ר�Ŵ�����
 */
public class WorkInfoHistoryBOPatrolman extends WorkInfoHistoryBaseBO {
	/**
	 * ����"��ʾ�������ָ��������Ϣ"��SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineInfoGivenDaySql() {
		String rangeID = boParam.getBean().getRangeID();
		// ���ѡ���˾���Ѳ���飬����Ҫ������ͣ���Ϊһ����������ܶ�Ӧ���SIM��
		sql = "select  sum(s.totalnum) totalnum,"
				+ "sum(s.onlinenumber) onlinenumber,"
				+ "sum(s.dailykm) dailykm " + "from stat_messagedaily s "
				+ "where s.patrolmanid = '" + rangeID + "' and "
				+ "s.operatedate = TO_DATE ('" + boParam.getGivenDate()
				+ "', 'YYYY-MM-DD') " + "group by s.patrolmanid ";
		return sql;
	}
	
	/**
	 * �����õ�������ѡĳһ���ʱ�����������ֲ���SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineNumberForHoursSql() {
		String givenDate = boParam.getGivenDate();
		String rangeID = boParam.getBean().getRangeID();
		sql = "select s.simid," + "to_char(s.firstsmreceivetime,"
				+ "'hh24:mi:ss') firstsmreceivetime ," + "s.flag "
				+ "from stat_onlinestatus s,"
				+ "patrolmaninfo p,terminalinfo t "
				+ "where s.simid = t.simnumber "
				+ "and p.patrolid = t.ownerid " + "and p.state is null "
				+ "and (s.firstsmreceivetime between to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') " + "and to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') + 1) " + "and p.patrolid = '"
				+ rangeID + "' " + "order by s.simid";
		return sql;
	}
	
	/**
	 * �����õ�������ʱ����ֹ��Χ��ÿ������������ֲ���SQL
	 * @return ��֯�õ�SQL
	 */
	public String createOnlineNumberSql() {
		HistoryWorkInfoConditionBean bean = boParam.getBean();
		String rangeID = bean.getRangeID();
		String startDate = bean.getStartDate();
		String endDate = bean.getEndDate();
		sql = "select to_char(s.operatedate, 'YYYY-MM-DD') operatedate,"
				+ "sum(s.onlinenumber) onlinenumber "
				+ "from stat_messagedaily s " + "where s.patrolmanid = '"
				+ rangeID + "' and " + "s.operatedate >=TO_DATE ('" + startDate
				+ "', 'YYYY-MM-DD') " + "and s.operatedate <=TO_DATE ('"
				+ endDate + "', 'YYYY-MM-DD') " + "group by s.operatedate "
				+ "order by operatedate";
		return sql;
	}

	/**
	 * ����"��ʾ�������ָ�������ʱ����Ϣ"��SQL
	 * @return �����أ���ΪѲ��ά����BO�࿴�������tooltip����01ͼ����
	 */
	public String createOnlineInfoGivenHourSql() {
		return null;
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
		sql = "select p.patrolname rangename,t.simid,"
				+ "SUM(t.totalnum) totalnum," + "SUM(t.patrolnum) patrolnum,"
				+ "SUM(t.troublenum) troublenum,"
				+ "SUM(t.collectnum) collectnum," + "SUM(t.watchnum) watchnum,"
				+ "SUM(t.othernum) othernum,SUM(t.dailykm) dailykm,"
				+ "sum(t.avgsendtime) avgsendtime,"
				+ "sum(t.avgsenddistance) avgsenddistance "
				+ "from stat_messagedaily t, patrolmaninfo p "
				+ "where p.patrolid = t.patrolmanid and p.state is null ";
		// ���ѡ���˾���ĳһ��
		if (bean == null && givenDate != null && !"".equals(givenDate)) {
			sql += "AND t.operatedate = TO_DATE ('" + givenDate
					+ "', 'yyyy-mm-dd') ";
		} else {
			sql += "AND (t.operatedate between TO_DATE ('" + startDate
					+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
					+ "', 'yyyy-mm-dd')) ";
		}
		sql += "and p.patrolid = '" + boParam.getSmRangeID() + "' "
				+ "GROUP BY t.simid,p.patrolname "
				+ "order by p.patrolname, t.simid";
		return sql;
	}

	/**
	 * �õ�SIM����һ���е�ĳ��ʱ���Ƿ����ߵ�01ͼ��������,����Map
	 * 
	 * @param zeroOneList
	 *            01ͼ�б�����ÿ��SIM������Ӧ�ľ���ĳ������߱�־����λ��
	 * @return Map
	 */
	public Map getFinal01GraphicMap() {
		sql = createOnlineNumberForHoursSql();
		logger.info("��ѯ��ָ������������������������01ͼSQL:" + sql);
		List zeroOneList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (zeroOneList == null) {
			logger.info("ָ������������������������01ͼ�б�Ϊ��");
		}
		/* ȡ��Ѳ�쿪ʼ����ʱ�� */
		Map map = new HashMap();
		int size = zeroOneList.size();
		String key = "";
		List flagList;
		for (int i = 0; i < size; i++) {
			key = ((BasicDynaBean) zeroOneList.get(i)).get("simid").toString();
			flagList = new ArrayList();
			String flag = ((BasicDynaBean) zeroOneList.get(i)).get("flag")
					.toString(); // һ���и���ʱ�ε����߱�־����λ��
			for (int j = 0; j < flag.length(); j++) {
				// ��ÿһ����־ȡ������list
				flagList.add(new Integer(String.valueOf(flag.charAt(j))));
			}
			map.put(key, flagList); // sim��������Ӧ��ʱ�α�־λ����map
		}
		return map;
	}

	public HistoryTimeInfoBean organizeBean() {
		// TODO Auto-generated method stub
		return null;
	}
}
