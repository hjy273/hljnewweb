package com.cabletech.analysis.services;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;

/**
 * 以市移动为单位的专门处理类
 */
public class WorkInfoHistoryBOCityMobile extends WorkInfoHistoryBaseBO {
	/**
	 * 构建得到所输入时间起止范围内每天的在线人数分布的SQL
	 * @return 组织好的SQL
	 */
	public String createOnlineNumberSql() {
		HistoryWorkInfoConditionBean bean = boParam.getBean();
		String startDate = bean.getStartDate();
		String endDate = bean.getEndDate();
		sql = "select to_char(s.operatedate, 'YYYY-MM-DD') operatedate,"
				+ "sum(s.onlinenumber) onlinenumber "
				+ "from stat_regionmessagedaily s where " + "s.regionid = '"
				+ rangeID + "' and "
				+ "s.operatedate >=TO_DATE ('" + startDate
				+ "', 'YYYY-MM-DD') " + "and s.operatedate <=TO_DATE ('"
				+ endDate + "', 'YYYY-MM-DD') " + "group by s.operatedate "
				+ "order by operatedate";
		return sql;
	}

	/**
	 * 构造"显示所鼠标所指向点的日信息"的SQL
	 * @return 组织好的SQL
	 */	
	public String createOnlineInfoGivenDaySql() {
		sql = "select  s.totalnum,s.onlinenumber,s.dailykm "
				+ "from stat_regionmessagedaily s where " + "s.regionid = '"
				+ rangeID + "' and " + "s.operatedate = TO_DATE ('"
				+ boParam.getGivenDate() + "', 'YYYY-MM-DD')";
		return sql;
	}
	
	/**
	 * 构建得到具体所选某一天各时段在线人数分布的SQL
	 * @return 组织好的SQL
	 */
	public String createOnlineNumberForHoursSql() {
		String givenDate = boParam.getGivenDate();
		sql = "Select TO_CHAR(so.intersectpoint,"
				+ "'yyyy-mm-dd hh24:mi:ss') operatedate,"
				+ "SUM(so.onlinenumber) onlinenumber "
				+ "from stat_onlinenumber so "
				+ "where (so.intersectpoint between to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') " + "and to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') + 1) " + "and so.regionid = '"
				+ rangeID + "' "
				+ "group by intersectpoint order by operatedate";
		return sql;
	}

	/**
	 * 构造"显示所鼠标所指向点的相关时段信息"的SQL
	 * @return 组织好的SQL
	 */
	public String createOnlineInfoGivenHourSql() {
		// 当市移动用户选择所在地市时，才会有新的URL链接，也就才有此功能
		sql += "select so.contractorid,c.contractorname rangename,"
				+ "so.smtotalnum," + "sum(so.onlinenumber) totalnumber "
				+ "from STAT_ONLINENUMBER so,contractorinfo c "
				+ "where so.intersectpoint = to_date('" + boParam.getGivenDateAndTime()
				+ "','yyyy-mm-dd hh24:mi:ss') " + "and so.regionid = '"
				+ rangeID + "' "
				+ "and so.contractorid = c.contractorid "
				+ "and c.state is null "
				+ "group by so.contractorid,so.smtotalnum,c.contractorname "
				+ "order by so.contractorid";
		return sql;
	}

	/**
	 * 构建得到各种类型短信SQL
	 * @return 组织好的SQL
	 */
	public String createSMAllTypeSql() {
		HistoryWorkInfoConditionBean bean = boParam.getBean();
		String givenDate = boParam.getGivenDate();
		String startDate = "";
		String endDate = "";
		if (bean != null) {
			startDate = bean.getStartDate(); // 得到用户输入的起始日期
			endDate = bean.getEndDate(); // 得到用户输入的终止日期
		}
		sql = "SELECT sc.contractorid rangeid,"
				+ "con.contractorname rangename,"
				+ "SUM(sc.totalnum) totalnum," + "SUM(sc.patrolnum) patrolnum,"
				+ "SUM(sc.watchnum) watchnum, "
				+ "SUM(sc.troublenum) troublenum,"
				+ "SUM(sc.collectnum) collectnum,"
				+ "SUM(sc.othernum) othernum," + "SUM(sc.dailykm) dailykm "
				+ "FROM STAT_CONMESSAGEDAILY sc, CONTRACTORINFO con "
				+ "WHERE sc.contractorid = con.contractorid "
				+ "AND con.regionid = '" + boParam.getSmRangeID() + "' ";
		// 如果用户选择了具体某一天
		if (bean == null && givenDate != null && !"".equals(givenDate)) {
			sql += "AND sc.operatedate = TO_DATE ('" + givenDate
					+ "', 'yyyy-mm-dd') ";
		} else {
			sql += "AND (sc.operatedate between TO_DATE ('" + startDate
					+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
					+ "', 'yyyy-mm-dd')) ";
		}
		sql += "GROUP BY sc.contractorid,con.contractorname "
				+ "ORDER BY con.contractorname";
		return sql;
	}

}
