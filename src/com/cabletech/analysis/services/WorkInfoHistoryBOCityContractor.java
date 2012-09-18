package com.cabletech.analysis.services;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;

import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;

/**
 * 以市代维为单位的专门处理类
 */
public class WorkInfoHistoryBOCityContractor extends WorkInfoHistoryBaseBO {	
	/**
	 * 构造"显示所鼠标所指向点的日信息"的SQL
	 * @return 组织好的SQL
	 */
	public String createOnlineInfoGivenDaySql() {
		sql = "select  s.totalnum,s.onlinenumber,s.dailykm "
				+ "from stat_conmessagedaily s " + "where s.contractorid = '"
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
				+ "', 'yyyy-mm-dd hh24:mi:ss') + 1) "
				+ "and so.contractorid = '" + rangeID + "' "
				+ "group by intersectpoint order by operatedate";
		return sql;
	}
	
	/**
	 * 构建得到所输入时间起止范围内每天的在线人数分布的SQL
	 * @return 组织好的SQL
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
	 * 构造"显示所鼠标所指向点的相关时段信息"的SQL
	 * @return 组织好的SQL
	 */
	public String createOnlineInfoGivenHourSql() {
		// //当市代维用户选择所在代维公司时，才会有新的URL链接，也就才有此功能
		sql += "select so.smtotalnum,so.onlinenumber,so.onlinesiminfo "
				+ "from STAT_ONLINENUMBER so "
				+ "where so.intersectpoint = to_date('" + boParam.getGivenDateAndTime()
				+ "','yyyy-mm-dd hh24:mi:ss') "
				+ "and so.contractorid='"
				+ boParam.getUserInfo().getDeptID() + "'";
		return sql;
	}

	/**
	 * 设置返回bean值
	 * @param list 传入的即将转化成HistoryTimeInfoBean的list，包括onlinesiminfo等信息
	 * @param bean 用户界面上查询时输入的条件bean
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
		bean.setSmTotal(String.valueOf(smnum)); // 置短信总数入bean
		bean.setOnlineNumber(String.valueOf(onlinenum)); // 置在线人数总数入bean
	}

	/**
	 * 构建得到各种类型短信SQL
	 * @return 组织好的SQL
	 */
	public String createSMAllTypeSql() {
		String startDate = "";
		String endDate = "";
		String givenDate = boParam.getGivenDate();
		HistoryWorkInfoConditionBean bean = boParam.getBean();
		if (bean != null) {
			startDate = bean.getStartDate(); // 得到用户输入的起始日期
			endDate = bean.getEndDate(); // 得到用户输入的终止日期
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
		// 如果用户选择了具体某一天
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
