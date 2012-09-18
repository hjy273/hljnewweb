package com.cabletech.analysis.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.CurrentTimeBean;
import com.cabletech.analysis.dao.RealTimeOnlineDAO;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.util.DateUtil;

/**
 * 用于实时在线人数查询，可以提供省、市、代维用户查询各自管辖区域的代维人员巡检人数
 * 
 * @author Administrator
 * 
 */
public class RealTimeOnlineBO {
	private Logger logger = Logger.getLogger("RealTimeOnlineService");
	private RealTimeOnlineDAO onlineDao;
	private DateUtil dateUtil;
	private GisConInfo config;
	private int spacingtime = 30; // 巡检是否在线的最小时间间隔，现最小间隔为30分钟。
	private long interval = 0L;

	/**
	 * 初始化onlineDao\config\dateUtil\spacingtime\interval
	 */
	public RealTimeOnlineBO() {
		onlineDao = new RealTimeOnlineDAO();
		config = GisConInfo.newInstance();
		dateUtil = new DateUtil();
		try{
			spacingtime = Integer.parseInt(config.getSpacingTime());
		}catch(NumberFormatException e){
			spacingtime = 30;	
		}
		interval = 1000L * 60 * spacingtime; // 刷新间隔,单位毫秒
	}

	/**
	 * 查询所有地区的在线人数,根剧参数查询条件组合 user:用户信息，将用户信息作为查询条件。
	 * 
	 * @param user
	 *            用户信息
	 * @param regionid
	 *            查询指定区域，如果为null，以用户所在区域为准。
	 * @return Map 时间段interval,人数的map对象
	 */
	public Map getAllOnlineNum(UserInfo user, String regionid) {
		//logger.info("getAllOnlineNum11");
		String sql = "";
		if (regionid == null) {
			regionid = user.getRegionid();
		}
		//logger.info("getAllOnlineNum22");
		sql = "select count(simid) simid,interval1 from ( select distinct(simid) simid,interval1 from ( "
				+ "		select simid,interval1 from ( "
				+ "			select simid,to_char(arrivetime,'hh24:mi:ss') arrivetime,"
				+ constructCondition()
				+ " interval1 "
				+ "			from RECIEVEMSGLOG r ,terminalinfo t, region n "
				+ "         where arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy/MM/dd")
				+ "','YYYY-MM-DD hh24:mi:ss')"
				+ " and t.simnumber = r.simid and t.regionid=n.regionid"
				+ " and n.regionid in (SELECT regionid FROM region CONNECT BY PRIOR regionid=parentregionid START WITH regionid='"
				+ regionid
				+ "')) group by simid,interval1 ) )group by interval1 order by interval1";

		logger.info("查询所有地区的在线人数" + sql);
		List list = onlineDao.queryOnlineNum(sql);

		return listToMap(list);
	}

	/**
	 * <lu>组合查询条件<lu>
	 * @return String 返回的字符串为sql中的case...when ...else .. end 部分
	 */
	private String constructCondition() {
		logger.info("constructCondition111");
		/* 取得巡检开始结束时间 */
		String startTime = config.getPatrolStartTime(); // 格式"6:30:00";
		String endTime = config.getPatrolEndTime(); // 格式"21:30:00";

		String sql = "case ";
		logger.info("constructCondition2222");
		long currentStartTime = dateUtil.strTimeToLong(startTime);
		long currentEndTime = dateUtil.strTimeToLong(endTime) + interval;
		logger.info("currentStartTime = "+dateUtil.longTostrTime(currentStartTime, "HH:mm:ss"));
		logger.info("currentEndTime = "+dateUtil.longTostrTime(currentEndTime, "HH:mm:ss"));
		logger.info("constructCondition3333");
		while (currentStartTime <= currentEndTime) {
			logger.info("currentStartTime || currentEndTime "+dateUtil.longTostrTime(currentStartTime, "HH:mm:ss") +" = "+ dateUtil.longTostrTime(currentEndTime, "HH:mm:ss"));
			sql += " when TO_CHAR(r.arrivetime,'HH24:mi') BETWEEN '"
					+ dateUtil.longTostrTime((currentStartTime - interval),
							"HH:mm:ss")
					+ "' AND '"
					+ dateUtil
							.longTostrTime((currentStartTime - 1), "HH:mm:ss")
					+ "' THEN '"
					+ dateUtil.longTostrTime(currentStartTime, "HH:mm:ss")
					+ "'";
			currentStartTime = currentStartTime + interval;
		}
		sql += " else 'other' end";
		logger.info("case when :"+sql);
		return sql;
	}

	/**
	 * list型转换为map
	 * 
	 * @param simInterval
	 *            包含在线人数,时间段interval信息的list。
	 * @return 返回时间段interval,人数的map对象
	 */
	private Map listToMap(List simInterval) {
		Map map = new HashMap();
		int size = simInterval.size();
		String key = "";
		String value = "";
		for (int i = 0; i < size; i++) {
			key = ((BasicDynaBean) simInterval.get(i)).get("interval1")
					.toString();
			value = ((BasicDynaBean) simInterval.get(i)).get("simid")
					.toString();
			map.put(key, new Integer(Integer.parseInt(value)));
		}
		return map;
	}

	/**
	 * 计算出每个时间段内不重复的sim卡个数,即在线人数
	 * 
	 * @param simInterval
	 *            List 包含simid,时间段interval信息的list,并且要起list按时间段进行排序。
	 * @return Map 返回时间段interval,人数的map对象
	 */
	private Map countOnlineNum(List simInterval) {
		Map map = new HashMap();
		int size = simInterval.size();
		String prevValue = "";
		String currentValue = "";
		int patrolNum = 0;
		for (int i = 0; i < size; i++) {
			currentValue = ((BasicDynaBean) simInterval.get(i))
					.get("interval1").toString();
			if (i == 0) {
				prevValue = ((BasicDynaBean) simInterval.get(i)).get(
						"interval1").toString();
			} else {
				prevValue = ((BasicDynaBean) simInterval.get(i - 1)).get(
						"interval1").toString();
			}
			if (!currentValue.equals(prevValue)) {
				map.put(prevValue, new Integer(patrolNum));
				patrolNum = 1;
			} else {
				patrolNum++;
			}
		}

		return map;
	}

	/**
	 * 获取指定区域以及代维的在线人员数 如果connid为null,那么查询的将是本区域每时刻所有代维单位的在线人数
	 * 返回map对象中包含时间段,该时刻的在线人数. 注：提供给市移动查询使用
	 * 
	 * @param user
	 *            用户信息
	 * @param connid
	 *            查询指定代维，如果为null，以用户所在区域为准。
	 * @return Map 时间段interval,人数的map对象
	 */
	public Map getAreaOnlineNum(UserInfo user, String connid) {
		String sql = "select distinct(simid),interval1 from ( "
				+ "		select simid,interval1 from ( "
				+ "			select simid,to_char(arrivetime,'hh24:mi:ss') arrivetime,"
				+ constructCondition()
				+ " interval1 "
				+ "			from RECIEVEMSGLOG r ,terminalinfo t, region n ,CONTRACTORINFO c"
				+ "         where arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy/MM/dd")
				+ "','YYYY-MM-DD hh24:mi:ss')"
				+ " and t.simnumber = r.simid and t.regionid=n.regionid and c.contractorid = t.contractorid";
		if (connid == null) {
			sql += " and n.regionid ='" + user.getRegionid() + "'";
		} else {
			sql += " and c.contractorid = '" + connid + "'";
		}
		sql += " ) group by simid,interval1 ) order by interval1";
		logger.info("查询所在地区的在线人数" + sql);
		List list = onlineDao.queryOnlineNum(sql);

		return countOnlineNum(list);
	}

	/**
	 * 根据登陆用户,以及巡检组信息查询巡检员在线情况. 如果巡检组信息为null,那么将加载本单位的在线人数.
	 * 若巡检组信息不为null,调用halfBin(List)方法,将list转换位每时刻的在线状态.即:01110 111 return Map
	 * 返回map对象中包含时间段,该时刻的在线人数. 注：提供给代维用户查询使用
	 * 
	 * @param user
	 *            用户信息
	 * @param patrolid
	 *            巡检组id
	 * @return Map 时间段interval,人数的map对象
	 */
	public Map getConOnlineNum(UserInfo user, String patrolid) {
		String sql = "select distinct(simid),interval1 from ( "
				+ "		select simid,interval1 from ( "
				+ "			select simid,to_char(arrivetime,'hh24:mi:ss') arrivetime,"
				+ constructCondition()
				+ " interval1 "
				+ "			from RECIEVEMSGLOG r ,terminalinfo t, CONTRACTORINFO c,patrolmaninfo p"
				+ "         where arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy/MM/dd")
				+ "','YYYY-MM-DD hh24:mi:ss')"
				+ " and t.simnumber = r.simid and c.contractorid = t.contractorid and t.ownerid=p.patrolid ";
		if (patrolid == null) {
			sql += " and c.contractorid='" + user.getDeptID() + "'";
		} else {
			sql += " and p.patrolid='" + patrolid + "'";
		}
		sql += " ) group by simid,interval1 ) ";
		if (patrolid == null) {
			sql += " order by interval1";
		} else {
			sql += " order by simid,interval1";
		}
		logger.info("查询代维或巡检组在线人数" + sql);
		List list = onlineDao.queryOnlineNum(sql);
		if (patrolid == null) {
			return countOnlineNum(list);
		} else {
			return halfBin(list);
		}
	}

	/**
	 * 对数据进行处理将list转换位每时刻的在线状态.即:01110111, 返回Map
	 * 
	 * @param simidInterval
	 *            List 包含simid,时间段interval信息的list,并且要使list按simid,时间段进行排序。
	 * @return Map map中包含以simid为键值的map对象，该map对象中
	 */
	private Map halfBin(List simidInterval) {
		int size = simidInterval.size();
		Map map = new HashMap();
		String prevValue = "";
		String currentValue = "";
		Map simMap = new HashMap();
		for (int i = 0; i < size; i++) {
			currentValue = ((BasicDynaBean) simidInterval.get(i)).get("simid")
					.toString();
			if (i == 0) {
				prevValue = ((BasicDynaBean) simidInterval.get(i)).get("simid")
						.toString();
			} else {
				prevValue = ((BasicDynaBean) simidInterval.get(i - 1)).get(
						"simid").toString();
			}
			// 若prevValue与currentValue不等
			if (!prevValue.equals(currentValue)) {
				map.put(prevValue, simMap);
				simMap = new HashMap();
			} else {
				String interval = ((BasicDynaBean) simidInterval.get(i)).get(
						"interval1").toString();
				simMap.put(interval, new Integer(1));
			}
		}
		return map;
	}

	/**
	 * 获取某时间段内的(各区域或各代维单位)的在线人员数
	 * 
	 * @param user
	 *            用户信息
	 * @param time
	 *            查看的时间点
	 * @param currentRegion
	 *            当前区域id,查询的某个区域
	 * @param connid
	 *            代维单位id，查询的某个代维单位
	 * @return com.cabletech.analyse.beans.CurrentTimeBean
	 * @see com.cabletech.analysis.beans.CurrentTimeBean
	 */
	public CurrentTimeBean getSegmentOnlineNum(UserInfo user, String time,
			String currentRegion, String connid) {
		long intervalTime = dateUtil.strDateAndTimeToLong(time); // 查看时间点信息
		logger.info("connid="+connid);
		CurrentTimeBean currentTime = new CurrentTimeBean();
		String sql = constructNumSql(user, currentRegion, connid, intervalTime);
		String noteNumSql = noteNumSql(user, currentRegion, connid,
				intervalTime);
		logger.info("sql : " + sql);
		List onlineNumList = onlineDao.queryOnlineNum(sql);
		if (onlineNumList != null) {
			logger.info("onlineNumList : " + onlineNumList.size());
		}
		currentTime.setIntersectPoint(dateUtil.longTostrTime(intervalTime
				- interval, "HH:mm:ss")
				+ "-" + dateUtil.longTostrTime(intervalTime - 1, "HH:mm:ss"));
		//statOnlineNum();// 统计在线人数onlineNumList.size()
		if(UserType.CONTRACTOR.equals(user.getType())){
			currentTime.setOnlineNum(onlineNumList.size());
		}else{
			currentTime.setOnlineNum(statOnlineNum(onlineNumList,connid));
		}
		currentTime.setNumList(onlineNumList);
		//logger.info("sql_noteNum : " + noteNumSql);
		currentTime.setNoteNum(onlineDao.queryNoteNum(noteNumSql));
		return currentTime;
	}
	
	/**
	 * 根据connid是否为空判断如何统计在线人数
	 * @param onlineNumList
	 * @param connid
	 * @return
	 */
	private int statOnlineNum(List onlineNumList, String connid) {
		int count = 0;
		logger.info("connid " +connid);
		if(connid != null && !"".equals(connid)){
			
			return onlineNumList.size();
		}else{
			int size = onlineNumList.size();
			for(int i=0;i<size;i++){
				BasicDynaBean dynaBean = (BasicDynaBean)onlineNumList.get(i);
				count += Integer.parseInt(dynaBean.get("count").toString());
			}
			return count;
		}
	}
	
	public static void main(String []args){
		List onlineNumList = new ArrayList(); 
		RealTimeOnlineBO b = new RealTimeOnlineBO();
		String connid = "11111";
		b.statOnlineNum(onlineNumList,connid);
	}
	/**
	 * 创建短信数量查询语句
	 * 
	 * @param user
	 *            登录用户信息
	 * @param currentRegion
	 *            当前选择区域
	 * @param connid
	 *            代维单位id
	 * @param intervalTime
	 *            间隔时间
	 * @return String 返回查询语句
	 */
	private String noteNumSql(UserInfo user, String currentRegion,
			String connid, long intervalTime) {
		String sql = "";
		String commonCondition = " where TO_CHAR(r.arrivetime,'hh24:mi') BETWEEN '"
				+ dateUtil.longTostrTime(intervalTime - interval, "HH:mm:ss")
				+ "' AND  '"
				+ dateUtil.longTostrTime(intervalTime - 1, "HH:mm:ss")
				+ "' "
				+ " and arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy-MM-dd")
				+ "','YYYY-MM-DD hh24:mi:ss') "
				+ " and t.simnumber = r.simid and t.regionid=n.regionid";
		String sql_province = " select  count(simid) num from recievemsglog r ,terminalinfo t,region n"
				+ commonCondition;
		String sql_section = "select  count(simid) num from recievemsglog r ,terminalinfo t,region n ,CONTRACTORINFO c "
				+ commonCondition + " and c.contractorid = t.contractorid ";
		String sql_contractor = "select count(simid) num  from recievemsglog r ,terminalinfo t,region n ,patrolmaninfo p ,CONTRACTORINFO c"
				+ commonCondition
				+ " and c.contractorid = t.contractorid and t.ownerid=p.patrolid ";
		if (UserType.PROVINCE.equals(user.getType())) {
			if (currentRegion == null) {
				sql = sql_province;
			} else {
				sql = sql_section + " and n.regionid = '" + currentRegion + "'";
			}
		} else if (UserType.SECTION.equals(user.getType())) {
			if (connid == null && UserType.SECTION.equals(user.getType())) {
				sql = sql_section + " and n.regionid = '" + user.getRegionid()
						+ "'";
			} else {
				sql = sql_contractor + " and c.contractorid = '" + connid + "'";
			}
		} else {
			sql = sql_contractor + " and c.contractorid = '" + user.getDeptID()
					+ "'";
		}
		return sql;
	}

	/**
	 * 根据当前用户以及传入条件组合查询指定时间的在线人员信息的sql。
	 * 
	 * @param user
	 *            用户信息
	 * @param currentRegion
	 *            当前区域id,查询的某个区域
	 * @param connid
	 *            代维单位id，查询的某个代维单位
	 * @param intervalTime
	 *            查看时间点的信息
	 * @return String 返回查询指定时间的在线人员信息的sql。
	 */
	private String constructNumSql(UserInfo user, String currentRegion,
			String connid, long intervalTime) {
		String sql = "";
		String commonCondition = " where TO_CHAR(r.arrivetime,'hh24:mi') BETWEEN '"
				+ dateUtil.longTostrTime(intervalTime - interval, "HH:mm:ss")
				+ "' AND  '"
				+ dateUtil.longTostrTime(intervalTime - 1, "HH:mm:ss")
				+ "' "
				+ " and arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy-MM-dd")
				+ "','YYYY-MM-DD hh24:mi:ss') "
				+ " and t.simnumber = r.simid and t.regionid=n.regionid";

		String sql_province = "select count(simid) count,groupname from ( select distinct(simid),n.regionname groupname from recievemsglog r ,terminalinfo t,region n"
				+ commonCondition + " order by n.regionname";
		String sql_section = "select count(simid) count,groupname from ( select distinct(simid),c.contractorname groupname from recievemsglog r ,terminalinfo t,region n ,CONTRACTORINFO c "
				+ commonCondition + " and c.contractorid = t.contractorid ";
		String sql_contractor = "select distinct(simid) count,p.patrolname groupname from recievemsglog r ,terminalinfo t,region n ,patrolmaninfo p ,CONTRACTORINFO c"
				+ commonCondition
				+ " and c.contractorid = t.contractorid and t.ownerid=p.patrolid ";
		if (UserType.PROVINCE.equals(user.getType())) {
			if (currentRegion == null) {
				sql = sql_province + ")  group by groupname";
			} else {
				sql = sql_section + " and n.regionid = '" + currentRegion
						+ "' order by c.contractorname )  group by groupname";
			}
		} else if (UserType.SECTION.equals(user.getType())) {
			if (connid == null && UserType.SECTION.equals(user.getType())) {
				sql = sql_section + " and n.regionid = '" + user.getRegionid()
						+ "' order by c.contractorname )  group by groupname";
			} else {
				sql = sql_contractor + " and c.contractorid = '" + connid
						+ "' order by p.patrolname";
			}
		} else {
			sql = sql_contractor + " and c.contractorid = '" + user.getDeptID()
					+ "' order by p.patrolname";
		}
		return sql;
	}

	/**
	 * 将bean转化为html文本串。
	 * 
	 * @param bean
	 *            CurrentTimeBean
	 * @return html 文本字符串。
	 * @see com.cabletech.analysis.beans.CurrentTimeBean
	 */
	public String compagesHtmlText(CurrentTimeBean bean) {
		int size = bean.getNumList().size();
		String onlinerTemp = "";
		for (int i = 0; i < size; i++) {
			BasicDynaBean dynaBean = (BasicDynaBean) bean.getNumList().get(i);
			onlinerTemp += dynaBean.get("groupname") + " :"
					+ dynaBean.get("count") + "<br>";
		}
		String htmlString = "所选日期:"
				+ bean.getIntersectPoint()
				+ "<br>"
				+ "在线人数:"
				+ bean.getOnlineNum()
				+ "<br>"
				+ "任务执行总数："
				+ bean.getNoteNum()
				+ "<br>"
				+ "在线人员列表:<br>"
				+ onlinerTemp;
				//+ " 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br>";
		return htmlString;
	}

}
