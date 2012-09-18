package com.cabletech.analysis.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.HistoryDateInfoBean;
import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.dao.WorkInfoHistoryDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.util.DateUtil;

/**
 * 该类为巡检过程分析的业务逻辑处理类
 */
public class WorkInfoHistoryBO extends BaseBisinessObject {
	private Logger logger;

	private String sql = "";

	private WorkInfoHistoryDAOImpl workInfoHistoryDAOImpl;

	private GisConInfo config = GisConInfo.newInstance();

	private DateUtil dateUtil = new DateUtil();

	/**
	 * 构造方法
	 * 
	 */
	public WorkInfoHistoryBO() {
		workInfoHistoryDAOImpl = new WorkInfoHistoryDAOImpl();
		// 建立logger输出对象
		logger = Logger.getLogger(this.getClass().getName());
	}



	/**
	 * 得到用户所输起止日期范围的在线人数曲线图，
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @param bean
	 *            查询界面输入条件bean,保存查询时用户输入的信息。
	 * @return 返回用户所输起止日期范围的在线人数曲线图列表，Map类型
	 */
	public Map getOnlineNumberForDays(HistoryWorkInfoConditionBean bean,
			UserInfo userInfo) {
		// 构造SQL
		sql = createOnlineNumberSql(bean, userInfo);
		logger.info("查询所选起止日期内在线人数SQL:" + sql);
		List onlineNumberList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (onlineNumberList == null) {
			logger.info("所选起止日期内在线人数列表为空");
		}
		// 将List转换为Map型
		return listToMap(onlineNumberList);
	}

	/**
	 * 得到用户所选具体日期内各个时段的在线人数曲线图，
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @param bean
	 *            查询界面输入条件bean,保存查询时用户输入的信息。
	 * @param givenDate
	 *            用户所选具体日期。
	 * @return 返回用户所选具体日期内各个时段的在线人数曲线图列表，Map类型
	 */
	public Map getOnlineNumberForHours(String givenDate, UserInfo userInfo,
			HistoryWorkInfoConditionBean bean) {
		sql = createOnlineNumberForHoursSql(givenDate, userInfo, bean);
		logger.info("查询所指定具体日期在线人数或在线01图SQL:" + sql);
		List onlineNumberForHoursList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (onlineNumberForHoursList == null) {
			logger.info("指定具体日期在线人数或在线01图列表为空");
		}
		// 如果是市代维登录并选择了具体的巡检组，那么将出现01图
		if (UserType.CONTRACTOR.equals(userInfo.getType())
				&& (!"22".equals(bean.getRangeID()))) {
			return getFinal01GraphicMap(onlineNumberForHoursList);
		}
		// 将List转换为Map
		return listToMap(onlineNumberForHoursList);
	}

	/**
	 * 得到具体某一天的ToolTip信息
	 * 
	 * @param givenDate
	 *            具体日期 如：2007-11-11
	 * @param userInfo
	 *            用户
	 * @param bean
	 *            查询界面输入条件bean,保存查询时用户输入的信息。
	 * @return HistoryDateInfoBean
	 */
	public HistoryDateInfoBean getOnlineInfoGivenDay(String givenDate,
			UserInfo userInfo, HistoryWorkInfoConditionBean bean) {
		sql = createOnlineInfoGivenDaySql(givenDate, userInfo, bean); // 组织SQL
		logger.info("显示所鼠标所指向点的相关信息（日信息）SQL:" + sql);
		List list = workInfoHistoryDAOImpl.queryInfo(sql);
		if (list == null) {
			logger.info("显示所鼠标所指向点的相关信息列表为空");
		}
		HistoryDateInfoBean backBean = new HistoryDateInfoBean();
		// 组织返回bean,任一统计对象一天只有唯一一条记录
		backBean.setSmTotal(((BasicDynaBean) list.get(0)).get("totalnum")
				.toString());
		backBean.setOnlineNumber(((BasicDynaBean) list.get(0)).get(
				"onlinenumber").toString());
		backBean.setDistanceTotal(((BasicDynaBean) list.get(0)).get("dailykm")
				.toString());
		return backBean;
	}

	/**
	 * 得到具体某个时段的ToolTip信息
	 * 
	 * @param givenDateAndTime
	 *            具体时段 如：9:00:00,实际上应选择08:30:00到09:00:00的信息（不包括09:00:00)
	 * @param userInfo
	 *            用户
	 * @return HisotryTimeInfoBean
	 */
	public HistoryTimeInfoBean getOnlineInfoGivenHour(String givenDateAndTime,
			UserInfo userInfo) {
		sql = createOnlineInfoGivenHourSql(givenDateAndTime, userInfo); // 组织SQL
		logger.info("显示所鼠标所指向点的相关时段信息SQL:" + sql);
		List list = workInfoHistoryDAOImpl.queryInfo(sql);
		if (list == null) {
			logger.info("显示所鼠标所指向点的相关时段信息列表为空");
		}
		HistoryTimeInfoBean backBean = new HistoryTimeInfoBean();
		backBean = organizeBean(list, userInfo, givenDateAndTime); // 组织Bean
		return backBean;
	}

	/**
	 * 得到短信信息（短信汇总和短信图例都使用之）
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @param bean
	 *            查询界面输入条件bean,保存查询时用户输入的信息。
	 * @param givenDate
	 *            用户所选具体日期。
	 * @param smRangeID
	 *            省移动登录：其值要么为“11",要么为么各地ID，市移动登录：其值要么为“21",要么为该市各代维公司ID，
	 *            市代维登录：其值要么为“22",要么为用户所属代维公司下的巡检组ID，
	 * @return 返回短信信息列表，List类型
	 */
	public List getSMInfoAllType(HistoryWorkInfoConditionBean bean,
			UserInfo userInfo, String smRangeID, String givenDate) {
		sql = createSMAllTypeSql(bean, userInfo, smRangeID, givenDate);
		if ("0".equals(givenDate)) { // 如果没有选择具体某天，即为多天
			logger.info("起止日期内历史短信信息SQL:" + sql);
		} else {
			logger.info(givenDate + "该天各时段历史短信信息SQL:" + sql);
		}
		List smAllTypeList = null;
		smAllTypeList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (smAllTypeList == null) {
			if ("0".equals(givenDate)) { // 如果没有选择具体某天，即为多天
				logger.info("起止日期内历史短信信息列表为空");
			} else {
				logger.info(givenDate + "该天各时段历史短信信息列表为空");
			}
		}
		return smAllTypeList;
	}



	/**
	 * 构建得到具体所选某一天各时段在线人数分布的SQL
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @param bean
	 *            查询界面输入条件bean,保存查询时用户输入的信息。
	 * @param givenDate
	 *            用户所选具体日期。
	 * @return 返回构建得到用户范围的SQL
	 */
	public String createOnlineNumberForHoursSql(String givenDate,
			UserInfo userInfo, HistoryWorkInfoConditionBean bean) {
		String userType = userInfo.getType();
		String rangeID = bean.getRangeID();
		String sql = "";
		sql = "Select TO_CHAR(so.intersectpoint,"
				+ "'yyyy-mm-dd hh24:mi:ss') operatedate,"
				+ "SUM(so.onlinenumber) onlinenumber "
				+ "from stat_onlinenumber so "
				+ "where (so.intersectpoint between to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') " + "and to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') + 1) ";
		if (UserType.PROVINCE.equals(userType)) { // 如果是省移动用户
			// 如果所选范围不是“全省范围“，即为具体地市ID
			if (!"11".equals(rangeID)) {
				sql += "and so.regionid = '" + rangeID + "' ";
			}
		} else if (UserType.SECTION.equals(userType)) { // 如果是市移动用户
			// 如果所选范围不是用户所在地市，即为具体代维公司ID
			if (!"12".equals(rangeID)) {
				sql += "and so.contractorid = '" + rangeID + "' ";
			} else {
				sql += "and so.regionid = '" + userInfo.getRegionid() + "' ";
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // 如果是市代维用户
			// 如果所选范围是用户所在代维公司
			if ("22".equals(rangeID)) {
				sql += "and so.contractorid = '" + userInfo.getDeptID() + "' ";
			} else {
				sql = "select s.simid," + "to_char(s.firstsmreceivetime,"
						+ "'hh24:mi:ss') firstsmreceivetime ," + "s.flag "
						+ "from stat_onlinestatus s,"
						+ "patrolmaninfo p,terminalinfo t "
						+ "where s.simid = t.simnumber "
						+ "and p.patrolid = t.ownerid "
						+ "and p.state is null "
						+ "and (s.firstsmreceivetime between to_date('"
						+ givenDate + "', 'yyyy-mm-dd hh24:mi:ss') "
						+ "and to_date('" + givenDate
						+ "', 'yyyy-mm-dd hh24:mi:ss') + 1) "
						+ "and p.patrolid = '" + rangeID + "' "
						+ "order by s.simid";
				return sql;
			}
		}
		sql += "group by intersectpoint order by operatedate";
		return sql;
	}

	/**
	 * 构建得到所输入时间起止范围内每天的在线人数分布的SQL
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @param bean
	 *            查询界面输入条件bean,保存查询时用户输入的信息。
	 * @return 返回所输入时间起止范围内每天的在线人数分布的SQL
	 */
	public String createOnlineNumberSql(HistoryWorkInfoConditionBean bean,
			UserInfo userInfo) {
		String userType = userInfo.getType();
		String sql = "";
		String rangeID = bean.getRangeID();
		String startDate = bean.getStartDate();
		String endDate = bean.getEndDate();
		sql = "select to_char(s.operatedate, 'YYYY-MM-DD') operatedate,"
				+ "sum(s.onlinenumber) onlinenumber ";
		if (UserType.PROVINCE.equals(userType)) { // 如果是省移动用户
			sql += "from stat_regionmessagedaily s where ";
			if (!"11".equals(rangeID)) { // 如果所选范围不是“全省范围“，即为具体地市ID
				sql += "s.regionid = '" + rangeID + "' and ";
			}
		} else if (UserType.SECTION.equals(userType)) { // 如果是市移动用户
			// 如果所选范围为用户所在地市
			if ("12".equals(rangeID)) {
				sql += "from stat_regionmessagedaily s where ";
				sql += "s.regionid = '" + userInfo.getRegionid() + "' and ";
			} else {
				sql += "from stat_conmessagedaily s ";
				sql += "where s.contractorid = '" + rangeID + "' and ";
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // 如果是市代维用户
			// 如果所选范围为用户所在代维公司
			if ("22".equals(rangeID)) {
				sql += "from stat_conmessagedaily s ";
				sql += "where s.contractorid = '" + userInfo.getDeptID()
						+ "' and ";
			} else {
				sql += "from stat_messagedaily s ";
				sql += "where s.patrolmanid = '" + rangeID + "' and ";
			}
		}
		sql += "s.operatedate >=TO_DATE ('" + startDate + "', 'YYYY-MM-DD') "
				+ "and s.operatedate <=TO_DATE ('" + endDate
				+ "', 'YYYY-MM-DD') " + "group by s.operatedate "
				+ "order by operatedate";
		return sql;
	}

	/**
	 * 构建得到各种类型短信SQL
	 * 
	 * @param userInfo
	 *            用户信息。
	 * @param bean
	 *            查询界面输入条件bean,保存查询时用户输入的信息。
	 * @param givenDate
	 *            用户所选具体日期。
	 * @param smRangeID
	 *            省移动登录：其值要么为“11",要么为么各地ID，市移动登录：其值要么为“21",要么为该市各代维公司ID，
	 *            市代维登录：其值要么为“22",要么为用户所属代维公司下的巡检组ID，
	 * @return 返回得到各种类型短信SQL
	 */
	public String createSMAllTypeSql(HistoryWorkInfoConditionBean bean,
			UserInfo userInfo, String smRangeID, String givenDate) {
		String userType = userInfo.getType();
		String sql = "";
		String startDate = "";
		String endDate = "";
		if (bean != null) {
			startDate = bean.getStartDate(); // 得到用户输入的起始日期
			endDate = bean.getEndDate(); // 得到用户输入的终止日期
		}
		if (UserType.PROVINCE.equals(userType)) { // 如果是省移动用户
			if ("11".equals(smRangeID)) { // 如果所选范围是“全省范围“
				sql = "SELECT sr.regionid rangeid,r.regionname rangename,"
						+ "SUM(sr.totalnum) totalnum,"
						+ "SUM(sr.patrolnum) patrolnum,"
						+ "SUM(sr.watchnum) watchnum,"
						+ "SUM(sr.troublenum) troublenum,"
						+ "SUM(sr.collectnum) collectnum,"
						+ "SUM(sr.othernum) othernum, SUM(sr.dailykm) dailykm "
						+ "FROM STAT_REGIONMESSAGEDAILY sr, REGION r "
						+ "WHERE sr.regionid = r.regionid  ";
				// 如果用户选择了具体某一天
				if (bean == null && givenDate != null 
						&& !"".equals(givenDate)) {
					sql += "AND sr.operatedate = TO_DATE ('" + givenDate
							+ "', 'yyyy-mm-dd') ";
				} else {
					sql += "AND (sr.operatedate between TO_DATE ('" + startDate
							+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
							+ "', 'yyyy-mm-dd')) ";
				}
				sql += "GROUP BY sr.regionid,r.regionname "
						+ "ORDER BY r.regionname";
			} else { // 如果所选范围为具体地市
				sql = "SELECT sc.contractorid rangeid,"
						+ "con.contractorname rangename,"
						+ "SUM(sc.totalnum) totalnum,"
						+ "SUM(sc.patrolnum) patrolnum,"
						+ "SUM(sc.watchnum) watchnum, "
						+ "SUM(sc.troublenum) troublenum,"
						+ "SUM(sc.collectnum) collectnum,"
						+ "SUM(sc.othernum) othernum,"
						+ "SUM(sc.dailykm) dailykm "
						+ "FROM STAT_CONMESSAGEDAILY sc, CONTRACTORINFO con "
						+ "WHERE sc.contractorid = con.contractorid "
						+ "AND con.regionid = '" + smRangeID + "' ";
				// 如果用户选择了具体某一天
				if (bean == null && givenDate != null 
						&& !"".equals(givenDate)) {
					sql += "AND sc.operatedate = TO_DATE ('" + givenDate
							+ "', 'yyyy-mm-dd') ";
				} else {
					sql += "AND (sc.operatedate between TO_DATE ('" + startDate
							+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
							+ "', 'yyyy-mm-dd')) ";
				}
				sql += "GROUP BY sc.contractorid,con.contractorname "
						+ "ORDER BY con.contractorname";
			}
		} else if (UserType.SECTION.equals(userType)) { // 如果是市移动用户
			if ("12".equals(smRangeID)) { // 如果所选范围是用户所在地市
				sql = "SELECT sc.contractorid rangeid,"
						+ "con.contractorname rangename,"
						+ "SUM(sc.dailykm) dailykm,SUM(sc.totalnum) totalnum,"
						+ "SUM(sc.patrolnum) patrolnum,"
						+ "SUM(sc.watchnum) watchnum,"
						+ "SUM(sc.collectnum) collectnum,"
						+ "SUM(sc.troublenum) troublenum,"
						+ "SUM(sc.othernum) othernum "
						+ "FROM STAT_CONMESSAGEDAILY sc, CONTRACTORINFO con "
						+ "WHERE sc.contractorid = con.contractorid "
						+ "AND con.regionid = '" + userInfo.getRegionid()
						+ "' ";
				// 如果用户选择了具体某一天
				if (bean == null && givenDate != null 
						&& !"".equals(givenDate)) {
					sql += "AND sc.operatedate = TO_DATE ('" + givenDate
							+ "', 'yyyy-mm-dd') ";
				} else {
					sql += "AND (sc.operatedate between TO_DATE ('" + startDate
							+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
							+ "', 'yyyy-mm-dd')) ";
				}
				sql += "GROUP BY sc.contractorid,con.contractorname "
						+ "ORDER BY con.contractorname";
			} else { // 如果所选范围是具体的代维公司ID
				sql = "select p.patrolname rangename,t.simid,"
						+ "SUM(t.totalnum) totalnum,"
						+ "SUM(t.patrolnum) patrolnum,"
						+ "SUM(t.troublenum) troublenum,"
						+ "SUM(t.collectnum) collectnum,"
						+ "SUM(t.watchnum) watchnum,"
						+ "SUM(t.othernum) othernum,SUM(t.dailykm) dailykm,"
						+ "sum(t.avgsendtime) avgsendtime,"
						+ "sum(t.avgsenddistance) avgsenddistance "
						+ "from stat_messagedaily t, patrolmaninfo p "
						+ "where p.patrolid = t.patrolmanid "
						+ "and p.parentid = '" + smRangeID
						+ "' and p.state is null ";
				// 如果用户选择了具体某一天
				if (bean == null && givenDate != null
						&& !"".equals(givenDate)) {
					sql += "AND t.operatedate = TO_DATE ('" + givenDate
							+ "', 'yyyy-mm-dd') ";
				} else {
					sql += "AND (t.operatedate between TO_DATE ('" + startDate
							+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
							+ "', 'yyyy-mm-dd')) ";
				}
				sql += "GROUP BY t.simid,p.patrolname "
						+ "order by p.patrolname, t.simid";
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // 如果是市代维用户
			sql = "select p.patrolname rangename,t.simid,"
					+ "SUM(t.totalnum) totalnum,"
					+ "SUM(t.patrolnum) patrolnum,"
					+ "SUM(t.troublenum) troublenum,"
					+ "SUM(t.collectnum) collectnum,"
					+ "SUM(t.watchnum) watchnum,"
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
			if ("22".equals(smRangeID)) { // 如果所选范围是用户所在代维公司
				sql += "and p.parentid = '" + userInfo.getDeptID() + "' ";
			} else { // 如果所选范围是具体巡检维护组
				sql += "and p.patrolid = '" + smRangeID + "' ";
			}
			sql += "GROUP BY t.simid,p.patrolname "
					+ "order by p.patrolname, t.simid";
		}
		return sql;
	}

	/**
	 * list型转换为map
	 * 
	 * @param onlineNumberList
	 *            包含在线人数,时间段（或日期）interval信息的list。
	 * @return 返回时间段（或日期）interval,人数的map对象
	 */
	private Map listToMap(List onlineNumberList) {
		Map map = new HashMap();
		int size = onlineNumberList.size();
		String key = "";
		String value = "";
		for (int i = 0; i < size; i++) {
			// 时间段（或日期）
			key = ((BasicDynaBean) onlineNumberList.get(i)).get("operatedate")
					.toString();
			value = ((BasicDynaBean) onlineNumberList.get(i)).get(
					"onlinenumber")// 在线人数
					.toString();
			map.put(key, new Integer(Integer.parseInt(value)));
		}
		return map;
	}

	/**
	 * 得到SIM卡在一天中的某个时段是否在线的01图所需数据,返回Map
	 * 
	 * @param zeroOneList
	 *            01图列表，包括每个SIM卡及对应的具体某天的在线标志（多位）
	 * @return Map
	 */
	private Map getFinal01GraphicMap(List zeroOneList) {
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

	/**
	 * 构造"显示所鼠标所指向点的日信息"的SQL
	 * 
	 * @param givenDate
	 *            具体日期
	 * @param userInfo
	 *            用户
	 * @param bean
	 *            查询界面输入条件bean,保存查询时用户输入的信息。
	 * @return String
	 */
	public String createOnlineInfoGivenDaySql(String givenDate,
			UserInfo userInfo, HistoryWorkInfoConditionBean bean) {
		String userType = userInfo.getType();
		String rangeID = bean.getRangeID();
		String sql = "";
		if (UserType.PROVINCE.equals(userType)) { // 如果是省移动用户
			sql = "select sum(s.totalnum) totalnum,"
					+ "sum(s.onlinenumber) onlinenumber,"
					+ "sum(s.dailykm) dailykm "
					+ "from stat_regionmessagedaily s where ";
			if (!"11".equals(rangeID)) { // 如果所选范围不是“全省范围“，即为具体地市ID
				sql += "s.regionid = '" + rangeID + "' and ";
			}
		} else if (UserType.SECTION.equals(userType)) { // 如果是市移动用户
			sql = "select  s.totalnum,s.onlinenumber,s.dailykm ";
			// 如果所选范围为用户所在地市
			if ("12".equals(rangeID)) {
				sql += "from stat_regionmessagedaily s where ";
				sql += "s.regionid = '" + userInfo.getRegionid() + "' and ";
			} else {
				sql += "from stat_conmessagedaily s ";
				sql += "where s.contractorid = '" + rangeID + "' and ";
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // 如果是市代维用户
			// 如果所选范围为用户所在代维公司
			if ("22".equals(rangeID)) {
				sql = "select  s.totalnum,s.onlinenumber,s.dailykm "
						+ "from stat_conmessagedaily s ";
				sql += "where s.contractorid = '" + userInfo.getDeptID()
						+ "' and ";
			} else { // 如果选择了具体巡检组，则需要分组求和，因为一个组下面可能对应多个SIM卡
				sql = "select  sum(s.totalnum) totalnum,"
						+ "sum(s.onlinenumber) onlinenumber,"
						+ "sum(s.dailykm) dailykm "
						+ "from stat_messagedaily s "
						+ "where s.patrolmanid = '" + rangeID + "' and "
						+ "s.operatedate = TO_DATE ('" + givenDate
						+ "', 'YYYY-MM-DD') " + "group by s.patrolmanid ";
				return sql;
			}
		}
		sql += "s.operatedate = TO_DATE ('" + givenDate + "', 'YYYY-MM-DD')";
		return sql;
	}

	/**
	 * 构造"显示所鼠标所指向点的相关时段信息"的SQL
	 * 
	 * @param givenDateAndTime
	 *            具体时段 如：9:00:00,实际上应选择08:30:00到09:00:00的信息（不包括09:00:00)
	 * @param userInfo
	 *            用户
	 * @return String
	 */
	public String createOnlineInfoGivenHourSql(String givenDateAndTime,
			UserInfo userInfo) {
		String userType = userInfo.getType();
		String sql = "";
		if (UserType.PROVINCE.equals(userType)) { // 如果是省移动用户
			// 当省移动用户选择全省区域时，才会有新的URL链接，也就才有此功能
			sql += "select so.regionid,r.regionname rangename,so.smtotalnum,"
					+ "sum(so.onlinenumber) totalnumber "
					+ "from STAT_ONLINENUMBER so,region r "
					+ "where so.intersectpoint = to_date('" + givenDateAndTime
					+ "','yyyy-mm-dd hh24:mi:ss') "
					+ "and so.regionid = r.regionid and r.state is null "
					+ "and substr (r.regionid, 3, 4) != '1111' "
					+ "and substr (r.regionid, 3, 4) != '0000' "
					+ "group by so.regionid,so.smtotalnum,r.regionname "
					+ "order by so.regionid";
		} else if (UserType.SECTION.equals(userType)) { // 如果是市移动用户
			// 当市移动用户选择所在地市时，才会有新的URL链接，也就才有此功能
			sql += "select so.contractorid,c.contractorname rangename,"
					+ "so.smtotalnum,"
					+ "sum(so.onlinenumber) totalnumber "
					+ "from STAT_ONLINENUMBER so,contractorinfo c "
					+ "where so.intersectpoint = to_date('"
					+ givenDateAndTime
					+ "','yyyy-mm-dd hh24:mi:ss') "
					+ "and so.regionid = '"
					+ userInfo.getRegionid()
					+ "' "
					+ "and so.contractorid = c.contractorid "
					+ "and c.state is null "
					+ "group by so.contractorid,so.smtotalnum,c.contractorname "
					+ "order by so.contractorid";
		} else if (UserType.CONTRACTOR.equals(userType)) { // 如果是市代维用户
			// //当市代维用户选择所在代维公司时，才会有新的URL链接，也就才有此功能
			sql += "select so.smtotalnum,so.onlinenumber,so.onlinesiminfo "
					+ "from STAT_ONLINENUMBER so "
					+ "where so.intersectpoint = to_date('" + givenDateAndTime
					+ "','yyyy-mm-dd hh24:mi:ss') " + "and so.regionid = '"
					+ userInfo.getRegionid() + "' " + "and so.contractorid='"
					+ userInfo.getDeptID() + "'";
		}
		WorkInfoHistoryBaseBO bo = new WorkInfoHistoryBOCityContractor();
		bo.organizeBean(null);
		return sql;
	}

	/**
	 * 将传入的list组织成需要的HisotryTimeInfoBean
	 * 
	 * @param list
	 *            查询所得的包括“短信总数”及“在线人数”等信息的列表
	 * @param userInfo
	 *            用户
	 * @param givenDateAndTime
	 *            具体时段 如：9:00:00,实际上应选择08:30:00到09:00:00的信息（不包括09:00:00)
	 * @return HisotryTimeInfoBean
	 */
	public HistoryTimeInfoBean organizeBean(List list, UserInfo userInfo,
			String givenDateAndTime) {
		HistoryTimeInfoBean bean = new HistoryTimeInfoBean();
		String userType = userInfo.getType();
		int size = list.size();
		int smnum = 0;
		int onlinenum = 0;
		Map map = new HashMap();
		int smnumeachloop = 0;
		int onlinenumeachloop = 0;
		String regionanme = "";
		String contractorname = "";
		if (UserType.PROVINCE.equals(userType)) { // 如果是省移动用户
			for (int i = 0; i < size; i++) { // 通过此循环，得到“短信总数”及“在线人员总数”等信息
				smnumeachloop = Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("smtotalnum").toString());
				onlinenumeachloop = Integer.parseInt(((BasicDynaBean) list
						.get(i)).get("totalnumber").toString());
				regionanme = ((BasicDynaBean) list.get(i)).get("rangename")
						.toString();
				smnum += smnumeachloop;
				onlinenum += onlinenumeachloop;
				map.put(regionanme, String.valueOf(onlinenumeachloop));
			}
			bean.setOnlineStatList(map);
		} else if (UserType.SECTION.equals(userType)) { // 如果是市移动用户
			for (int i = 0; i < size; i++) { // 通过此循环，得到“短信总数”及“在线人员总数”等信息
				smnumeachloop = Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("smtotalnum").toString());
				onlinenumeachloop = Integer.parseInt(((BasicDynaBean) list
						.get(i)).get("totalnumber").toString());
				contractorname = ((BasicDynaBean) list.get(i)).get("rangename")
						.toString();
				smnum += smnumeachloop;
				onlinenum += onlinenumeachloop;
				map.put(contractorname, String.valueOf(onlinenumeachloop));
			}
			bean.setOnlineStatList(map);
		} else if (UserType.CONTRACTOR.equals(userType)) { // 如果是市代维用户
			smnum = Integer.parseInt(((BasicDynaBean) list.get(0)).get(
					"smtotalnum").toString());
			onlinenum = Integer.parseInt(((BasicDynaBean) list.get(0)).get(
					"onlinenumber").toString());
			String onlineInfo = ((BasicDynaBean) list.get(0)).get(
					"onlinesiminfo").toString();
			bean.setOnlineInfo(onlineInfo);
		}
		bean.setSmTotal(String.valueOf(smnum)); // 置短信总数入bean
		bean.setOnlineNumber(String.valueOf(onlinenum)); // 置在线人数总数入bean
		String endPoint = givenDateAndTime.substring(givenDateAndTime
				.indexOf(" ") + 1); // 得到终止时间时分秒
		long spacingtime = 1000L * 60 * Integer.parseInt(config
				.getSpacingTime());
		long startDateAndTimeLong = dateUtil
				.strDateAndTimeToLong(givenDateAndTime)
				- spacingtime; // 得到开始时间毫秒数
		String startPoint = dateUtil.longTostrTime(startDateAndTimeLong,
				"HH:mm:ss"); // 得到开始时间时分秒
		String interSectPoint = startPoint + "-" + endPoint;
		bean.setIntersectPoint(interSectPoint); // 置时间段范围入bean
		return bean;
	}
}
