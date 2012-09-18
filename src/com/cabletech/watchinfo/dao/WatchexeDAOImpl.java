package com.cabletech.watchinfo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.*;

import com.cabletech.analysis.dao.BaseAnalysisDAOImpl;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.exterior.beans.WatchExeStaCondtionBean;
import com.cabletech.exterior.beans.WatchExeStaResultBean;
import com.cabletech.exterior.dao.WatchExeAnalysisDAOImpl;
import com.cabletech.utils.*;
import com.cabletech.watchinfo.beans.*;
import com.cabletech.watchinfo.domainobjects.*;
import com.cabletech.watchinfo.action.WatchAction;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

public class WatchexeDAOImpl extends HibernateDaoSupport {
	private WatchExeAnalysisDAOImpl myDao = new WatchExeAnalysisDAOImpl();

	private static Logger logger = Logger.getLogger(WatchexeDAOImpl.class
			.getName());

	public WatchexeDAOImpl() {
	}

	public Watchexe addWatchexe(Watchexe watchexe) throws Exception {
		this.getHibernateTemplate().save(watchexe);
		return watchexe;
	}

	public Watchexe findById(String id) throws Exception {
		return (Watchexe) this.getHibernateTemplate().load(Watchexe.class, id);
	}

	public void removeWatchexe(Watchexe watchexe) throws Exception {
		this.getHibernateTemplate().delete(watchexe);
	}

	public Watchexe updateWatchexe(Watchexe watchexe) throws Exception {
		this.getHibernateTemplate().saveOrUpdate(watchexe);
		return watchexe;
	}

	/**
	 * 预处理查询条件
	 * 
	 * @param bean
	 *            WatchStaBean
	 * @return WatchStaBean
	 * @throws Exception
	 */
	public void prepareData(WatchStaBean bean) throws Exception {

		String queryBy = bean.getStatype();

		if (queryBy.equals("2")) {
			// 巡检员
			bean.setWatchid(null);
			// bean.setContractorid(null);

		} else {
			if (queryBy.equals("3")) {
				// 外力盯防
				bean.setExecutorid(null);
				bean.setContractorid(null);

			} else {
				// 代维单位
				bean.setWatchid(null);
				bean.setExecutorid(null);
			}
		}
	}

	/**
	 * 取得盯防列表
	 * 
	 * @param conditionBean
	 *            WatchStaBean
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getWatchList(WatchStaBean bean, UserInfo userinfo)
			throws Exception {

		String begin = "";
		String end = "";

		Vector watchListVct = new Vector();
		String sql = "SELECT a.PLACEID ID, a.PLACENAME NAME, a.PRINCIPAL MAN, to_char(a.BEGINDATE,'yyyy/mm/dd') BEGINDATE, to_char(a.ENDDATE,'yyyy/mm/dd') ENDDATE, a.ORDERLYBEGINTIME, a.ORDERLYENDTIME, a.ERROR, a.LID SUBLINE, a.STARTPOINTID, a.ENDPOINTID FROM WATCHINFO a, PATROLMANINFO b";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		// -------------------zhj-------------------
		// 市移动
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sqlBuild
					.addConstant("a.regionid ='" + userinfo.getRegionID() + "'");
		} // 市代维
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sqlBuild.addConstant(" a.regionid IN ('" + userinfo.getRegionID()
					+ "') and a.CONTRACTORID = ('" + userinfo.getDeptID()
					+ "')");
		} // 省移动
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			if (!bean.getRegionid().substring(2, 6).equals("0000")) {
				sqlBuild
						.addConditionAnd("a.regionid = {0}", bean.getRegionid());
			}
		}
		// 省代维
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			if (!bean.getRegionid().substring(2, 6).equals("0000")) {
				sqlBuild
						.addConditionAnd("a.regionid = {0}", bean.getRegionid());
			}
		}
		sqlBuild.addAnd();
		sqlBuild.addConstant(" a.PRINCIPAL = b.PATROLID ");
		sqlBuild.addAnd();
		sqlBuild.addConstant(" a.enddate is not null");
		// modify by guixy 2008-11-12 添加statype不为4的判断，加上时间条件
	//	if (!"4".equals(bean.getStatype())) {			
		
			if (bean.getBegindate().equals("") && bean.getEnddate().equals("") && !"3".equals(bean.getStatype()) ) {
				sqlBuild.addAnd();
				sqlBuild
						.addConstant("a.BEGINDATE >= TO_DATE (to_char(sysdate,'yyyy/mm')||'/01', 'YYYY/MM/DD')");
				sqlBuild.addAnd();
				sqlBuild
						.addConstant("a.ENDDATE <= TO_DATE (to_char(last_day(sysdate),'YYYY/MM/dd') , 'YYYY/MM/DD')");
	
			} else {
				sqlBuild.addConditionAnd("a.BEGINDATE >= {0}", DateUtil
						.StringToDate(bean.getBegindate()));
				sqlBuild.addConditionAnd("a.ENDDATE <= {0}", DateUtil
						.StringToDate(bean.getEnddate()));
			}
	//	}
		if (!bean.getExecutorid().equals("")) {
			sqlBuild.addConditionAnd("a.PRINCIPAL = {0}", bean.getExecutorid());
		}
		if (!bean.getContractorid().equals("")) {
			sqlBuild.addConditionAnd("a.CONTRACTORID = {0}", bean
					.getContractorid());
		}
		if (!bean.getWatchid().equals("")) {
			sqlBuild.addConditionAnd("a.PLACEID = {0}", bean.getWatchid());
		}
		
		// add by guixy 2008-11-12
		if (bean.getQueryname() != null && !"".equals(bean.getQueryname().trim())) {
			String likename = "%" + bean.getQueryname() + "%";
			sqlBuild.addConditionAnd("a.PLACENAME LIKE {0}", likename);
		}
        

		logger.info("sql: " + sqlBuild.toSql());
		QueryUtil queryUtil = new QueryUtil();
		Vector resultVct = queryUtil.executeQueryGetStringVector(sqlBuild
				.toSql(), "");

		for (int i = 0; i < resultVct.size(); i++) {
			Vector innerVect = (Vector) resultVct.get(i);
			OneStaWatchBean oneWatchBean = new OneStaWatchBean();

			oneWatchBean.setPlaceID((String) innerVect.get(0));
			oneWatchBean.setPlaceName((String) innerVect.get(1));
			oneWatchBean.setPrincipal((String) innerVect.get(2));
			oneWatchBean.setBeginDate((String) innerVect.get(3));
			oneWatchBean.setEndDate((String) innerVect.get(4));
			oneWatchBean.setOrderlyBeginTime((String) innerVect.get(5));
			oneWatchBean.setOrderlyEndTime((String) innerVect.get(6));
			oneWatchBean.setError((String) innerVect.get(7));
			oneWatchBean.setLid((String) innerVect.get(8));
			oneWatchBean.setStartpointid((String) innerVect.get(9));
			oneWatchBean.setEndpointid((String) innerVect.get(10));

			watchListVct.add(oneWatchBean);
		}
		return watchListVct;
	}

	/**
	 * 取得统计结果
	 * 
	 * @param conditionBean
	 *            WatchStaBean
	 * @return WatchStaResultBean
	 * @throws Exception
	 */
	public WatchStaResultBean getStaResultBean(WatchStaBean conditionBean,
			UserInfo userinfo) throws Exception {

		WatchStaResultBean resultBean = new WatchStaResultBean();
		// 取得盯防列表
		// /////////////zhj--------------------
		// System.out.println( "encutorid :" + conditionBean.getExecutorid()
		// + "Contractorid :" + conditionBean.getContractorid() );
		// ////////////zhj---------------------
		Vector watchListVct = getWatchList(conditionBean, userinfo);
		if (watchListVct == null || watchListVct.size() == 0) { // 没有可用外力盯防
			return null;
		} else {
			String daterange = "";
			if (conditionBean.getBegindate().equals("")
					&& conditionBean.getEnddate().equals("")) {
				Date date = new Date();
				daterange = DateUtil.DateToString(date).substring(0, 8);
			} else {
				if (conditionBean.getBegindate().equals("")) {
					daterange = conditionBean.getEnddate() + " 以前";
				} else {
					if (conditionBean.getEnddate().equals("")) {
						daterange = conditionBean.getBegindate() + " 至今";
					} else {
						daterange = conditionBean.getBegindate() + "  --  "
								+ conditionBean.getEnddate();
					}
				}
			}

			resultBean.setWatchlist(watchListVct);
			resultBean.setWatchamount(String.valueOf(watchListVct.size()));
			resultBean.setDaterange(daterange);
			// System.out.println( daterange + " conditionbean: " +
			// conditionBean.getContractorid() );
			setOtherValues(resultBean, conditionBean);

			int countNeed = 0;
			int countDid = 0;
			int countAlert = 0;

			for (int i = 0; i < watchListVct.size(); i++) {
				OneStaWatchBean oneWatchBean = (OneStaWatchBean) watchListVct
						.get(i);
				// 统计一个盯防
				staOneWatch(oneWatchBean);
				// System.out.println( "resultBean : " + resultBean );
				countNeed += Integer.parseInt(oneWatchBean.getInfoneed()); // 外力盯防需求信息数量

				countDid += Integer.parseInt(oneWatchBean.getInfodid()); // 外力盯防执行信息数量

				countAlert += Integer.parseInt(oneWatchBean.getAlertamount());
				// System.out.println( "----> " + ( ( List
				// )resultBean.getWatchlist() ).get( 0 ) );
			}
			// if( countNeed == 0 ){
			// return null; //除数不能为零
			// }
			putRateValues(resultBean, countNeed, countDid, countAlert);
			// System.out.println( "---->3 " + resultBean.getWatchlist() );
			return resultBean;
		}
	}

	/**
	 * 计算并置入完成率
	 * 
	 * @param countNeed
	 *            int
	 * @param countDid
	 *            int
	 * @throws Exception
	 */
	private void putRateValues(WatchStaResultBean resultBean, int countNeed,
			int countDid, int countAlert) throws Exception {
		// System.out.println( "countNeed: " + countNeed + " countDid " +
		// countDid + " countAlert " + countAlert );
		String workRate = "100%";
		String workRateNumber = "100";
		if (countNeed == 0) {
			resultBean.setUndorate("0");
			resultBean.setUndoratenumber("0");
			resultBean.setWatchexecuterate("0.0%");
			resultBean.setWorkratenumber("0");
			resultBean.setInfodid("0");
			resultBean.setInfoneeded("0");
			resultBean.setAlertcount("0");
			// System.out.println( "除数为0" );
		} else {
			workRateNumber = String.valueOf((int) Math.floor(100 * countDid
					/ countNeed));

			workRate = String.valueOf(Math.floor(100 * countDid / countNeed))
					+ "%";

			float dRate = 100 * countDid / countNeed;

			float udRate = 100 - dRate;

			String undoRate = String.valueOf(udRate) + "%";

			String undoRateNumber = "0";

			undoRateNumber = String.valueOf((int) udRate);

			resultBean.setUndorate(undoRate);
			resultBean.setUndoratenumber(undoRateNumber);
			resultBean.setWatchexecuterate(workRate);
			resultBean.setWorkratenumber(workRateNumber);
			resultBean.setInfodid(String.valueOf(countDid));
			resultBean.setInfoneeded(String.valueOf(countNeed));
			resultBean.setAlertcount(String.valueOf(countAlert));
		}
	}

	/**
	 * 设置其他值
	 * 
	 * @param resultBean
	 *            WatchStaResultBean
	 * @param conditionBean
	 *            WatchStaBean
	 */
	private void setOtherValues(WatchStaResultBean resultBean,
			WatchStaBean conditionBean) throws Exception {
		String sql_a = "select CONTRACTORNAME from CONTRACTORINFO";
		String sql_b = "select b.CONTRACTORNAME, a.patrolname  from PATROLMANINFO a, CONTRACTORINFO b";
		String sql_c = "select b.CONTRACTORNAME , a.patrolname from PATROLMANINFO a, CONTRACTORINFO b, WATCHINFO c";

		String sql = "";

		String queryBy = conditionBean.getStatype();
		// System.out.println( "queryBy: " + queryBy );
		// System.out.println( "单位 : " + conditionBean.getContractorid() );
		QuerySqlBuild sqlBuild;

		if (queryBy.equals("2")) {
			// 巡检维护组/人
			sqlBuild = QuerySqlBuild.newInstance(sql_b);
			sqlBuild.addConstant(" a.PARENTID = b.CONTRACTORID ");
			sqlBuild.addConditionAnd("a.patrolid = {0}", conditionBean
					.getExecutorid());
			sqlBuild.addConditionAnd("b.contractorid = {0}", conditionBean
					.getContractorid());
			sql = sqlBuild.toSql();

		} else {
			if (queryBy.equals("3")) {
				// 外力盯防
				sqlBuild = QuerySqlBuild.newInstance(sql_c);

				sqlBuild.addConstant(" a.PARENTID = b.CONTRACTORID ");
				sqlBuild.addAnd();
				sqlBuild.addConstant("a.PATROLID = c.PRINCIPAL ");

				sqlBuild.addConditionAnd("c.PLACEID = {0}", conditionBean
						.getWatchid());

				sql = sqlBuild.toSql();
			} else {
				// 代维单位
				sqlBuild = QuerySqlBuild.newInstance(sql_a);
				sqlBuild.addConditionAnd("CONTRACTORID = {0}", conditionBean
						.getContractorid());

				sql = sqlBuild.toSql();
			}
		}
		if (!conditionBean.getExecutorid().equals("")
				|| !conditionBean.getContractorid().equals("")) {
			// System.out.println( "sql: " + sql );
			QueryUtil queryUtil = new QueryUtil();
			String[][] conArr = queryUtil.executeQueryGetArray(sql, "");

			resultBean.setContractor(conArr[0][0]);

			if (queryBy.equals("2") | queryBy.equals("3")) {
				resultBean.setPatrolname(conArr[0][1]);
			} else {

				resultBean.setPatrolname("--");
			}
		} else {
			resultBean.setContractor("所有");
		}
	}

	/**
	 * 计算单个盯防的工作指标
	 * 
	 * @param oneWatchBean
	 *            OneStaWatchBean
	 */
	public void staOneWatch(OneStaWatchBean oneWatchBean) throws Exception {

		String infoNumberOfAWatch = String
				.valueOf(countDaysInAWatch(oneWatchBean)
						* countInfosOfADayWatch(oneWatchBean));

		// 该盯防共需发回信息数
		oneWatchBean.setInfoneed(infoNumberOfAWatch);
		// System.out.println("infoNumberOfAWatch : "+infoNumberOfAWatch);
		String sql = "SELECT to_char(EXECUTETIME,'yyyy/mm/dd hh24:mi:ss'), nvl(CONTENT,'0000') FROM WATCHEXECUTE ";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);

		sqlBuild.addConditionAnd("WATCHID = {0}", oneWatchBean.getPlaceID());
		sqlBuild.addConstant(" order by EXECUTETIME");
		// System.out.println( " sqlBuild.toSql() : " + sqlBuild.toSql() );
		QueryUtil queryUtil = new QueryUtil();
		Vector resultVct = queryUtil.executeQueryGetStringVector(sqlBuild
				.toSql(), "");

		// 该盯防实际发回合法信息数
		StaInfoIndeed(oneWatchBean, resultVct);

	}

	/**
	 * 盯防实际发回合法信息数
	 * 
	 * @param oneWatchBean
	 *            OneStaWatchBean
	 * @param resultVct
	 *            Vector
	 */
	private void StaInfoIndeed(OneStaWatchBean oneWatchBean, Vector resultVct)
			throws Exception {

		// if(resultVct.size()==0){
		// oneWatchBean.setInfodid( "0");
		// oneWatchBean.setAlertamount("0");
		// }

		Vector dateTimeVct = getDateTimeList(oneWatchBean);
		String strFomart = "yyyy/MM/dd HH:mm:ss";

		int alertNums = 0;
		int infoIndeed = 0;

		for (int i = 0; i < dateTimeVct.size() - 1; i++) {

			Date dateTimeB = DateUtil.StringToDate((String) dateTimeVct.get(i),
					strFomart);
			Date dateTimeE = DateUtil.StringToDate((String) dateTimeVct
					.get(i + 1), strFomart);

			int flag = 0;
			for (int k = 0; k < resultVct.size(); k++) {
				Vector oneRecord = (Vector) resultVct.get(k);

				Date Exectime = DateUtil.StringToDate(
						(String) oneRecord.get(0), strFomart);
				String content = (String) oneRecord.get(1);
				if (dateTimeB.compareTo(Exectime) <= 0
						&& Exectime.compareTo(dateTimeE) < 0) {
					if (!content.equals("0000")) {
						alertNums++;
					}
					flag++;
				}
			}

			if (flag > 0) {
				infoIndeed++;
			}
		}
		// System.out.println( "infoIndeed " + infoIndeed );
		// System.out.println( "alertNums " + alertNums );
		oneWatchBean.setAlertamount(String.valueOf(alertNums));
		oneWatchBean.setInfodid(String.valueOf(infoIndeed));

	}

	/**
	 * 取得一个盯防的时间区间列表
	 * 
	 * @param oneWatchBean
	 *            OneStaWatchBean
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getDateTimeList(OneStaWatchBean oneWatchBean)
			throws Exception {
		Vector dateTimeVct = new Vector();

		int dayNumInAWatch = countDaysInAWatch(oneWatchBean);
		int infoNumInADay = countInfosOfADayWatch(oneWatchBean);

		int cyc = Integer.parseInt(oneWatchBean.getError());

		String begindate = oneWatchBean.getBeginDate();
		String[] begindateArr = DateUtil.parseStringForDate(1, begindate);

		Calendar cal = Calendar.getInstance();
		// 开始日期
		int iYear = Integer.parseInt(begindateArr[0]);
		int iMonth = Integer.parseInt(begindateArr[1]) - 1;
		int iDate = Integer.parseInt(begindateArr[2]);

		String begintime = oneWatchBean.getOrderlyBeginTime();
		String[] begintimeArr = DateUtil.parseStringForDate(3, begintime);
		// 开始时间
		int iHour = Integer.parseInt(begintimeArr[0]);
		int iMini = Integer.parseInt(begintimeArr[1]);
		int iSeco = Integer.parseInt(begintimeArr[2]);

		for (int i = 0; i < dayNumInAWatch; i++) {
			for (int k = 0; k <= infoNumInADay; k++) {
				cal.set(iYear, iMonth, iDate, iHour, iMini, iSeco);
				// 存入一个日期
				dateTimeVct.add(makeStrDate(cal));
				iHour++;
			}

			iDate++;
			iHour = Integer.parseInt(begintimeArr[0]);
		}
		//
		// logTimeList(dateTimeVct);
		return dateTimeVct;
	}

	/**
	 * 转化成String
	 * 
	 * @param cal
	 *            Calendar
	 * @return String
	 * @throws Exception
	 */
	private String makeStrDate(Calendar cal) throws Exception {
		String dateStr = "2000/01/01 00:00:00";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateStr = sdf.format(cal.getTime()).toString();

		return dateStr;
	}

	/**
	 * 计算一个盯防需执行天数
	 * 
	 * @param oneWatchBean
	 *            OneStaWatchBean
	 */
	public int countDaysInAWatch(OneStaWatchBean oneWatchBean) throws Exception {

		String begindate = oneWatchBean.getBeginDate();
		String enddate = oneWatchBean.getEndDate();

		String[] begindateArr = DateUtil.parseStringForDate(1, begindate);
		String[] enddateArr = DateUtil.parseStringForDate(1, enddate);

		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		// 开始日期
		int iYear = Integer.parseInt(begindateArr[0]);
		int iMonth = Integer.parseInt(begindateArr[1]) - 1;
		int iDate = Integer.parseInt(begindateArr[2]);

		calBegin.set(iYear, iMonth, iDate);
		// 结束日期
		iYear = Integer.parseInt(enddateArr[0]);
		iMonth = Integer.parseInt(enddateArr[1]) - 1;
		iDate = Integer.parseInt(enddateArr[2]);

		calEnd.set(iYear, iMonth, iDate);

		long beginMill = calBegin.getTimeInMillis();
		long endMill = calEnd.getTimeInMillis();

		long iADay = 86400000;

		int iDays = (int) ((endMill - beginMill) / iADay);

		return iDays + 1;
	}

	/**
	 * 计算一天之内需求盯防信息数
	 * 
	 * @param oneWatchBean
	 *            OneStaWatchBean
	 * @return int
	 */
	public int countInfosOfADayWatch(OneStaWatchBean oneWatchBean)
			throws Exception {

		String begintime = oneWatchBean.getOrderlyBeginTime();
		String endtime = oneWatchBean.getOrderlyEndTime();

		int cyc = Integer.parseInt(oneWatchBean.getError());

		String[] begintimeArr = DateUtil.parseStringForDate(3, begintime);
		String[] endtimeArr = DateUtil.parseStringForDate(3, endtime);

		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();

		// 开始时间
		int iHour = Integer.parseInt(begintimeArr[0]);
		int iMini = Integer.parseInt(begintimeArr[1]);
		int iSeco = Integer.parseInt(begintimeArr[2]);

		calBegin.set(2005, 5, 15, iHour, iMini, iSeco);
		// 结束时间
		iHour = Integer.parseInt(endtimeArr[0]);
		iMini = Integer.parseInt(endtimeArr[1]);
		iSeco = Integer.parseInt(endtimeArr[2]);

		calEnd.set(2005, 5, 15, iHour, iMini, iSeco);

		long beginMill = calBegin.getTimeInMillis();
		long endMill = calEnd.getTimeInMillis();

		int iCount = 0;

		if(cyc != 0) {
			while (beginMill < endMill) {
				iCount++;
				beginMill += cyc * 3600000;
			}
		}
		

		return iCount;
	}

	/**
	 * 取得统计结果(省移动用户 by steven)
	 * 
	 * @param conditionBean
	 *            WatchExeStaCondtionBean
	 * @return WatchExeStaResultBean
	 * @throws Exception
	 */
	public List getStaResultBeanForAA(WatchExeStaCondtionBean conditionBean)
			throws Exception {
		WatchExeStaResultBean resultBeanEachCon = null;
		// 取得盯防数量
		List backList = new ArrayList();;
		Map map;
		List watchList = getWatchExeList(conditionBean);
		if (watchList == null || watchList.size() == 0) {
			logger.info("watchList is null in getStaResultBeanForAA");
			return null;
		}
		int watchSize = watchList.size();
		int countNeed = 0;
		int countDid = 0;
		int countAlert = 0;
		int sizeEachCon = 0;
		String strLastConName = "";
		String strConID = "";
		String strLastConID = "";
		String strConName = "";
		String strRegionName = "";
		String strLastRegionName = "";
		boolean equalFlag = true;
		for (int i = 0; i < watchSize; i++) {
			BasicDynaBean oneRecord = (BasicDynaBean) watchList.get(i);
			strConID = (String) oneRecord.get("conid");
			strConName = (String) oneRecord.get("conname");
			strRegionName = (String) oneRecord.get("regname");
			if (equalFlag == true || strConID.equals(strLastConID)) {
				equalFlag = false;
			} else {
				resultBeanEachCon = new WatchExeStaResultBean();
				putRateValuesForAA(resultBeanEachCon, countNeed, countDid,
						countAlert, sizeEachCon, strLastConID, strLastConName,
						strLastRegionName);
//				logger.info(strLastRegionName + strLastConID + strLastConName
//						+ "," + sizeEachCon + "," + countNeed + "," + countDid
//						+ "," + resultBeanEachCon.getWatchExecuterate() + ","
//						+ countAlert);
//				if (resultBeanEachCon == null ){
//					logger.info("resultBeanEachCon is null");
//				}
				backList.add(resultBeanEachCon);
				countNeed = 0;
				countDid = 0;
				countAlert = 0;
				sizeEachCon = 0;
			}
			// 统计一个盯防
			map = new HashMap();
			staOneWatchRecord(oneRecord, map);
			countNeed += Integer.parseInt((String) map.get("infoNeed")); // 外力盯防需求信息数量
			countDid += Integer.parseInt((String) map.get("infoIndeed")); // 外力盯防执行信息数量
			countAlert += Integer.parseInt((String) map.get("alertNums"));
			sizeEachCon++;
			strLastConID = strConID;
			strLastRegionName = strRegionName;
			strLastConName = strConName;
		}
		resultBeanEachCon = new WatchExeStaResultBean();
		putRateValuesForAA(resultBeanEachCon, countNeed, countDid, countAlert,
				sizeEachCon, strLastConID, strLastConName, strLastRegionName);
//		logger.info(strLastRegionName + strLastConID + strLastConName + ","
//				+ sizeEachCon + "," + countNeed + "," + countDid + ","
//				+ resultBeanEachCon.getWatchExecuterate() + "," + countAlert);
		backList.add(resultBeanEachCon);
		return backList;
	}

	/**
	 * 取得盯防列表记录数
	 * 
	 * @param conditionBean
	 *            WatchExeStaCondtionBean
	 * @return int
	 * @throws Exception
	 */
	public List getWatchExeList(WatchExeStaCondtionBean bean) throws Exception {
		List watchList = null;
		String theYearMonth = bean.getYear() + "-" + bean.getMonth();
		String theBeginDate = theYearMonth + "-01";
		String sql = "SELECT   p.parentid conid, c.contractorname conname,"
				+ "p.regionid regid, r.regionname regname, a.placeid placeid,"
				+ "a.placename placename, a.principal man,"
				+ "TO_CHAR (a.begindate, 'yyyy/mm/dd') begindate,"
				+ "TO_CHAR (a.enddate, 'yyyy/mm/dd') enddate, a.orderlybegintime orderlybegintime,"
				+ "a.orderlyendtime orderlyendtime, a.error error, a.lid subline,"
				+ "a.startpointid startpointid,a.endpointid endpointid "
				+ "FROM watchinfo a, patrolmaninfo p, contractorinfo c, region r "
				+ "WHERE a.principal = p.patrolid "
				+ "AND p.parentid = c.contractorid "
				+ "AND p.regionid = r.regionid "
				+ "AND a.begindate BETWEEN TO_DATE ('" + theBeginDate + "', 'yyyy-mm-dd') "
				+ "AND LAST_DAY (TO_DATE ('" + theYearMonth + "', 'yyyy-mm')) "
				+ "ORDER BY p.parentid";
		logger.info("省移动用户盯防查询:" + sql);
		watchList = myDao.queryInfo(sql);
		return watchList;
	}

	public void staOneWatchRecord(BasicDynaBean oneRecord, Map map)
			throws Exception {
		String infoNumberOfAWatch = String
				.valueOf(countDaysInAWatchForAA(oneRecord)
						* countInfosOfADayWatchForAA(oneRecord));
		// 该盯防共需发回信息数
		map.put("infoNeed", infoNumberOfAWatch);
//		logger.info("infoNeed:" + infoNumberOfAWatch);
		String sql = "SELECT to_char(EXECUTETIME,'yyyy/mm/dd hh24:mi:ss') executetime, nvl(CONTENT,'0000') content FROM WATCHEXECUTE ";
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		sqlBuild.addConditionAnd("WATCHID = {0}", (String) oneRecord
				.get("placeid"));
		sqlBuild.addConstant(" order by EXECUTETIME");
		logger.info(" sqlBuild.toSql() : " + sqlBuild.toSql());
		QueryUtil queryUtil = new QueryUtil();
		Vector resultVct = queryUtil.executeQueryGetStringVector(sqlBuild
				.toSql(), "");
		if (resultVct == null) {
			map.put("alertNums", "0");
			map.put("infoIndeed", "0");
//			logger.info("resultVct is null");
		} else {
			// 该盯防实际发回合法信息数
			StaInfoIndeedForAA(oneRecord, map, resultVct);
		}
	}

	public int countDaysInAWatchForAA(BasicDynaBean oneRecord) throws Exception {

		String begindate = (String) oneRecord.get("begindate");
		String enddate = (String) oneRecord.get("enddate");
		logger.info("begindate:" + begindate + ",enddate" + enddate);
		if (enddate == null || "".equals(enddate)) {
			enddate = begindate;
		}
		String[] begindateArr = DateUtil.parseStringForDate(1, begindate);
		String[] enddateArr = DateUtil.parseStringForDate(1, enddate);

		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		// 开始日期
		int iYear = Integer.parseInt(begindateArr[0]);
		int iMonth = Integer.parseInt(begindateArr[1]) - 1;
		int iDate = Integer.parseInt(begindateArr[2]);

		calBegin.set(iYear, iMonth, iDate);
		// 结束日期
		iYear = Integer.parseInt(enddateArr[0]);
		iMonth = Integer.parseInt(enddateArr[1]) - 1;
		iDate = Integer.parseInt(enddateArr[2]);

		calEnd.set(iYear, iMonth, iDate);

		long beginMill = calBegin.getTimeInMillis();
		long endMill = calEnd.getTimeInMillis();

		long iADay = 86400000;

		int iDays = (int) ((endMill - beginMill) / iADay);

		return iDays + 1;
	}

	public int countInfosOfADayWatchForAA(BasicDynaBean oneRecord)
			throws Exception {

		String begintime = (String) oneRecord.get("orderlybegintime");
		String endtime = (String) oneRecord.get("orderlyendtime");
		if (endtime == null || "".equals(endtime)) {
			endtime = begintime;
		}
		int cyc = Integer.parseInt((oneRecord.get("error").toString()));

		String[] begintimeArr = DateUtil.parseStringForDate(3, begintime);
		String[] endtimeArr = DateUtil.parseStringForDate(3, endtime);

		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();

		// 开始时间
		int iHour = Integer.parseInt(begintimeArr[0]);
		int iMini = Integer.parseInt(begintimeArr[1]);
		int iSeco = Integer.parseInt(begintimeArr[2]);

		calBegin.set(2005, 5, 15, iHour, iMini, iSeco);
		// 结束时间
		iHour = Integer.parseInt(endtimeArr[0]);
		iMini = Integer.parseInt(endtimeArr[1]);
		iSeco = Integer.parseInt(endtimeArr[2]);

		calEnd.set(2005, 5, 15, iHour, iMini, iSeco);

		long beginMill = calBegin.getTimeInMillis();
		long endMill = calEnd.getTimeInMillis();

		int iCount = 0;

		while (beginMill < endMill) {
			iCount++;
			beginMill += cyc * 3600000;
		}

		return iCount;
	}

	private void StaInfoIndeedForAA(BasicDynaBean oneRecord, Map map,
			Vector resultVct) throws Exception {
		Vector dateTimeVct = getDateTimeListForAA(oneRecord);
//		logger.info("size of dateTimeVct:" + dateTimeVct.size() +",size of resultVct" + resultVct.size());
//		logger.info("dateTimeVct is not null");
		String strFomart = "yyyy/MM/dd HH:mm:ss";
		int alertNums = 0;
		int infoIndeed = 0;
		// for (int i = 0; i < dateTimeVct.size(); i++) {
		// logger.info((String) dateTimeVct.get(i));
		// }
		for (int i = 0; i < dateTimeVct.size() - 1; i++) {
			Date dateTimeB = DateUtil.StringToDate((String) dateTimeVct.get(i),
					strFomart);
			Date dateTimeE = DateUtil.StringToDate((String) dateTimeVct
					.get(i + 1), strFomart);
			// Vector currentOneRecord = (Vector) resultVct.get(i);
			// Vector nextOneRecord = (Vector) resultVct.get(i + 1);
			// dateTimeB = DateUtil.StringToDate((String) currentOneRecord
			// .get(0), strFomart);
			// dateTimeE = DateUtil.StringToDate((String) nextOneRecord
			// .get(0), strFomart);
			// logger.info("StrdateTimeB:" + (String) dateTimeVct.get(i));
			// logger.info("StrdateTimeE:" + (String) dateTimeVct.get(i+1));
			// SimpleDateFormat formatter = new SimpleDateFormat(
			// "yyyy/MM/dd HH:mm:ss");
			// try {
			// logger.info("dateTimeB:" + formatter.format(dateTimeB));
			// logger.info("dateTimeE:" + formatter.format(dateTimeE));
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			int flag = 0;
			for (int k = 0; k < resultVct.size(); k++) {
				Vector resultOneRecord = (Vector) resultVct.get(k);

				Date Exectime = DateUtil.StringToDate((String) resultOneRecord
						.get(0), strFomart);
//				logger.info("hello,i am in for loop:");
//				 try {
//				 logger.info("Exectime:" + Exectime.toString()
//				 + ",dateTimeB:" + dateTimeB.toString()+ ",dateTimeE:" + dateTimeE.toString());
//				 } catch (Exception e) {
//				 e.printStackTrace();
//				 }
				String content = (String) resultOneRecord.get(1);
				if (dateTimeB.compareTo(Exectime) <= 0
						&& Exectime.compareTo(dateTimeE) <= 0) {
//					logger.info("value between start and end range");
					if (!content.equals("0000")) {
						alertNums++;
					}
					flag++;
				}
			}

			if (flag > 0) {
				infoIndeed++;
			}
		}
//		logger.info("alertNums:" + alertNums + ",infoIndeed:" + infoIndeed);
		map.put("alertNums", String.valueOf(alertNums));
		map.put("infoIndeed", String.valueOf(infoIndeed));
	}

	public Vector getDateTimeListForAA(BasicDynaBean oneRecord)
			throws Exception {
		Vector dateTimeVct = new Vector();

		int dayNumInAWatch = countDaysInAWatchForAA(oneRecord);
		int infoNumInADay = countInfosOfADayWatchForAA(oneRecord);
//		String strErrorNum = oneRecord.get("error").toString();
//		int cyc = Integer.parseInt(strErrorNum);
		String begindate = (String) oneRecord.get("begindate");

		String[] begindateArr = DateUtil.parseStringForDate(1, begindate);

		Calendar cal = Calendar.getInstance();
		// 开始日期
		int iYear = Integer.parseInt(begindateArr[0]);
		int iMonth = Integer.parseInt(begindateArr[1]) - 1;
		int iDate = Integer.parseInt(begindateArr[2]);
		String begintime = (String) oneRecord.get("orderlybegintime");
		String[] begintimeArr = DateUtil.parseStringForDate(3, begintime);
		// 开始时间
		int iHour = Integer.parseInt(begintimeArr[0]);
		int iMini = Integer.parseInt(begintimeArr[1]);
		int iSeco = Integer.parseInt(begintimeArr[2]);

		for (int i = 0; i < dayNumInAWatch; i++) {
			for (int k = 0; k < infoNumInADay; k++) {
				cal.set(iYear, iMonth, iDate, iHour, iMini, iSeco);
				// 存入一个日期
				dateTimeVct.add(makeStrDate(cal));
				iHour++;
			}

			iDate++;
			iHour = Integer.parseInt(begintimeArr[0]);
		}
		//
		// logTimeList(dateTimeVct);
		return dateTimeVct;
	}

	private void putRateValuesForAA(WatchExeStaResultBean resultBeanEachCon,
			int countNeed, int countDid, int countAlert, int sizeEachCon,
			String strConID, String strConName, String strRegionName)
			throws Exception {
		String workRate = "100%";
		if (countNeed == 0) {
			resultBeanEachCon.setWatchExecuterate("0.0%");
			resultBeanEachCon.setInfoDone("0");
			resultBeanEachCon.setInfoNeeded("0");
			resultBeanEachCon.setAlertCount("0");
			resultBeanEachCon.setWatchSize("0");
		} else {
			workRate = String.valueOf(Math.floor(100 * countDid / countNeed))
					+ "%";
			resultBeanEachCon.setWatchExecuterate(workRate);
			resultBeanEachCon.setInfoDone(String.valueOf(countDid));
			resultBeanEachCon.setInfoNeeded(String.valueOf(countNeed));
			resultBeanEachCon.setAlertCount(String.valueOf(countAlert));
			resultBeanEachCon.setWatchSize(String.valueOf(sizeEachCon));
		}
		resultBeanEachCon.setContractorID(strConID);
		resultBeanEachCon.setContractorName(strConName);
		resultBeanEachCon.setRegionName(strRegionName);
	}
}
