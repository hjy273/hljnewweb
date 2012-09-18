package com.cabletech.baseinfo.services;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.beans.UseTerminalBean;
import com.cabletech.baseinfo.dao.UseTerminalDAO;
import com.cabletech.commons.config.SysConfig;
import com.cabletech.commons.util.Conversion;

/**
 * 统计手持设备使用情况 分类条件包括三种 1、按短信发送数量 2、按巡检里程 3、按在线天数
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
	 * 获得按查询条件分类(条件包括三种 1、按短信发送数量 2、按巡检里程 3、按在线天数) 根据不同条件不同等级统计每个范围内的数量
	 * 
	 * @param querycon
	 *            查询条件
	 * @return 返回各等级数量
	 */
	public Map getUseTerminal(){
		String sql = creatCondition(getGrade(),commonCondition(getQueryCon()), getField());		
		List list = getDao().getUseTerminal(sql);
		return Conversion.listToMap(list,"area","countvalue");
	}
	
	
	/**
	 * 根据分类查询条件或排名查询条件查询设备使用情况列表。
	 * 
	 * @param querycon
	 *            查询条件
	 * @param condition
	 *            排名查询条件(前50,后50)
	 * @param section
	 *            分类查询条件
	 * @return 返回查询设备使用情况列表
	 */
	public List getUseTerminalCondition(String condition, String section){
		String sql = commonsql + createConditionSql(getQueryCon(),condition,section, getField());
		System.out.println("sql : " + sql);
		List list = getDao().getUseTerminal(sql);
		return list;
	}
	
	/**
	 * 构建各级别设备数量语句公共查询条件
	 * @param querycon 查询条件
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
	 * 根据查询类型等级信息，按字段名，公共条件组织各级别设备个数sql语句。 
	 * 查询类型包括 按短信发送数量、按巡检里程、按在线天数
	 * 
	 * @param gradeList
	 *            查询类型级别
	 * @param condition
	 *            公共查询条件
	 * @param field
	 *            字段名
	 * @return 返回不同等级查询语句
	 */
	protected String creatCondition(List gradeList, String condition,
			String field) {
		String sqlConidtion = "";
		int size = gradeList.size();
		for (int i = 0; i < size; i++) {
			logger.info("list :" + gradeList.get(i));
			if (i == 0) {// 取出级别最低的
				sqlConidtion += "(select '" + gradeList.get(i)
						+ "(未工作)' area,count(" + field + ") countvalue "
						+ "from STAT_TERMINALMONTHLYUSE ut,terminalinfo t "
						+ "where  ut." + field + "=" + gradeList.get(i)
						+ " and " + condition + ")";
			} else if (i == (size - 1)) {// 取出级别最高的
				sqlConidtion += " union (select '" + gradeList.get(i) + "(不含"
						+ gradeList.get(i) + ")以上' area,count(" + field
						+ ") countvalue "
						+ "from STAT_TERMINALMONTHLYUSE ut,terminalinfo t "
						+ "where  ut." + field + ">" + gradeList.get(i)
						+ " and " + condition + ")";
			} else {
				sqlConidtion += " union (select '" + gradeList.get(i - 1) + "-"
						+ gradeList.get(i) + "(不含" + gradeList.get(i - 1)
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
	 * 构建分类查询条件或排名查询条件部分查询语句,及结果集排序。
	 * @param querycon 查询条件
	 * @param condition 排序条件
	 * @param section 分类查询条件
	 * @param field 字段名
	 * @return 部分sql
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
		// 分类查询
		logger.info("section :" + section);
		if (section != null && !"".equals(section)) {
			conditionsql += conditionSyncopate(section, field);
		}
		// String sq = ">50";
		// 排名查询 前50 asc ;后50条 desc
		if (condition != null && !"".equals(condition)) {
			conditionsql += " and rownum  <= 50 ";
		}
		conditionsql += createOrderBy(condition,field);
		return conditionsql;
	}

	/**
	 * 组合查询分类条件语句。
	 * 
	 * @param condition
	 *            查询条件 条件格式：例：0(未工作);200-500(不含200)
	 * @param filedName
	 *            查询字段
	 * @return 返回条件语句，包含 and
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
	 * 根据排序条件condition对指定字段field进行排序语句。
	 * 
	 * @param condition
	 *            排序的条件
	 * @param field 要排序的字段
	 * @return sql 返回order by语句。
	 */
	protected String createOrderBy(String condition, String field) {
		return " order by ut."
				+ field
				+ " "
				+ ((condition != null && !"".equals(condition)) ? condition
						: "");
		
	}
	
	/**
	 * 要统计的字段名
	 * @return 
	 */
	public abstract String getField();
	/**
	 * 获得分类等级
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
