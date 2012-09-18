package com.cabletech.analysis.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;

/**
 * 以巡检组为单位的专门处理类
 */
public class WorkInfoHistoryBOPatrolman extends WorkInfoHistoryBaseBO {
	/**
	 * 构造"显示所鼠标所指向点的日信息"的SQL
	 * @return 组织好的SQL
	 */
	public String createOnlineInfoGivenDaySql() {
		String rangeID = boParam.getBean().getRangeID();
		// 如果选择了具体巡检组，则需要分组求和，因为一个组下面可能对应多个SIM卡
		sql = "select  sum(s.totalnum) totalnum,"
				+ "sum(s.onlinenumber) onlinenumber,"
				+ "sum(s.dailykm) dailykm " + "from stat_messagedaily s "
				+ "where s.patrolmanid = '" + rangeID + "' and "
				+ "s.operatedate = TO_DATE ('" + boParam.getGivenDate()
				+ "', 'YYYY-MM-DD') " + "group by s.patrolmanid ";
		return sql;
	}
	
	/**
	 * 构建得到具体所选某一天各时段在线人数分布的SQL
	 * @return 组织好的SQL
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
	 * 构建得到所输入时间起止范围内每天的在线人数分布的SQL
	 * @return 组织好的SQL
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
	 * 构造"显示所鼠标所指向点的相关时段信息"的SQL
	 * @return 不返回，因为巡检维护组BO类看不到这个tooltip，用01图代替
	 */
	public String createOnlineInfoGivenHourSql() {
		return null;
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
		sql = "select p.patrolname rangename,t.simid,"
				+ "SUM(t.totalnum) totalnum," + "SUM(t.patrolnum) patrolnum,"
				+ "SUM(t.troublenum) troublenum,"
				+ "SUM(t.collectnum) collectnum," + "SUM(t.watchnum) watchnum,"
				+ "SUM(t.othernum) othernum,SUM(t.dailykm) dailykm,"
				+ "sum(t.avgsendtime) avgsendtime,"
				+ "sum(t.avgsenddistance) avgsenddistance "
				+ "from stat_messagedaily t, patrolmaninfo p "
				+ "where p.patrolid = t.patrolmanid and p.state is null ";
		// 如果选择了具体某一天
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
	 * 得到SIM卡在一天中的某个时段是否在线的01图所需数据,返回Map
	 * 
	 * @param zeroOneList
	 *            01图列表，包括每个SIM卡及对应的具体某天的在线标志（多位）
	 * @return Map
	 */
	public Map getFinal01GraphicMap() {
		sql = createOnlineNumberForHoursSql();
		logger.info("查询所指定具体日期在线人数或在线01图SQL:" + sql);
		List zeroOneList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (zeroOneList == null) {
			logger.info("指定具体日期在线人数或在线01图列表为空");
		}
		/* 取得巡检开始结束时间 */
		Map map = new HashMap();
		int size = zeroOneList.size();
		String key = "";
		List flagList;
		for (int i = 0; i < size; i++) {
			key = ((BasicDynaBean) zeroOneList.get(i)).get("simid").toString();
			flagList = new ArrayList();
			String flag = ((BasicDynaBean) zeroOneList.get(i)).get("flag")
					.toString(); // 一天中各个时段的在线标志（多位）
			for (int j = 0; j < flag.length(); j++) {
				// 将每一个标志取出放入list
				flagList.add(new Integer(String.valueOf(flag.charAt(j))));
			}
			map.put(key, flagList); // sim卡及所对应的时段标志位放入map
		}
		return map;
	}

	public HistoryTimeInfoBean organizeBean() {
		// TODO Auto-generated method stub
		return null;
	}
}
