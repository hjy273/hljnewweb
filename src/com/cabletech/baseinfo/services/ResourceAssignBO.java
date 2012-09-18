package com.cabletech.baseinfo.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.cabletech.baseinfo.dao.LineDAOImpl;
import com.cabletech.baseinfo.dao.SublineDAOImpl;
import com.cabletech.baseinfo.dao.TerminalDAOImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.util.StrReformSplit;

/**
 * 资源重新分配，现在包括巡检线段、设备
 * 
 * @author Administrator
 * 
 */
public class ResourceAssignBO {
	private SublineDAOImpl sublineDao = new SublineDAOImpl();
	private TerminalDAOImpl terminalDao = new TerminalDAOImpl();
	private LineDAOImpl lineDao = new LineDAOImpl();
	private static Logger logger = Logger.getLogger("ResourceAssignBO");

	/**
	 * 线段所用权的重新分配。
	 * 
	 * @param sublineList
	 *            包含线段id的数组
	 * @param toObject
	 *            线段所有者,指巡检组
	 */
	public boolean assignSubline(String sublineStr, String toObject) {
		// 1、sublineList用户逗号隔开
		// String sublineStr = StrReformSplit.compartStr(sublineList, ",");
		// 2、create sql
		String sql = "update sublineinfo set PATROLID='" + toObject
				+ "' where sublineid in (" + sublineStr + ")";
		logger.info("将" + sublineStr + " 分配给 " + toObject);
		logger.info("update sql : " + sql);
		// 3、更新
		return sublineDao.updateSublineInfo(sql);
		// return true;
	}

	/**
	 * 设备所有权分配。将设备分配给代维单位
	 * 
	 * @param terminalStr
	 *            设备列表
	 * @param toObject
	 *            目标对象，指代维单位
	 */
	public boolean assignTerminal(String terminalStr, String toObject) {
		// 1、terminalList 用逗号分隔
		// String terminalStr = StrReformSplit.compartStr(terminalList, ",");
		// 2、create sql
		String sql = "update TERMINALINFO set CONTRACTORID = '" + toObject
				+ "',OWNERID='' where TERMINALID in ('" + terminalStr + "')";
		// 3、更新
		logger.info("update sql : " + sql);
		return terminalDao.updateTerminal(sql);
		// return true;
	}

	/**
	 * 根据线路id获得指定线段信息，id、线路名称
	 * 
	 * @param lineid
	 *            线路id
	 * @return
	 */
	public List getSubline(String lineid) {
		String sql = "select sublineid,sublinename from sublineinfo where lineid="
				+ lineid + " order by sublinename";

		return sublineDao.querySublineInfo(sql);
	}

	/**
	 * 根据代维单位信息获得其的维护线路
	 * 
	 * @param contractor 代维单位id
	 * @return
	 */
	public List getLine(String contractor) {
		String sql = "select distinct l.lineid,l.linename from lineinfo l,sublineinfo s ,patrolmaninfo p "
				+ "where l.lineid= s.lineid and p.patrolid= s.patrolid and p.parentid='"
				+ contractor + "' order by l.linename";
		return lineDao.getLine(sql);
	}

	/**
	 * 根据代维单位id获得设备信息。
	 * 
	 * @param contractorid 代维单位id
	 * @return
	 */
	public List getTerminal(String contractorid) {
		String sql = "select terminalid,simnumber,deviceid from terminalinfo where CONTRACTORID='"
				+ contractorid + "' and state is null order by simnumber";
		return terminalDao.queryTerminal(sql);
	}

	/**
	 * 获得将要重新分配的指定的线段
	 * 
	 * @param sublineStr 以逗号分割的线段id字符串
	 * @return
	 */
	public List getSpecifySubline(String sublineStr) {
		// String sublineStr = StrReformSplit.compartStr(sublineList, ",");
		if (sublineStr == null && "".equals(sublineStr)) {
			sublineStr = "''";
		}
		String sql = "select sl.sublinename,l.linename,d.name linetype,p.patrolname "
				+ " from sublineinfo sl,lineinfo l,patrolmaninfo p ,lineclassdic d "
				+ " where l.lineid=sl.lineid and p.patrolid=sl.patrolid and d.code=l.linetype"
				+ " and sl.sublineid in("
				+ sublineStr
				+ ")"
				+ " order by l.linename,sl.sublinename";
		logger.info("sql" + sql);
		return sublineDao.querySublineInfo(sql);
	}

	/**
	 * 获得将要重新分配的指定的设备
	 * 
	 * @param terminalStr 以逗号分割的设备id字符串
	 * @return 
	 */
	public List getSpecifyTerminal(String terminalStr) {
		// String terminalStr = StrReformSplit.compartStr(terminalList, ",");
		if (terminalStr == null || "".equals(terminalStr)) {
			terminalStr = "''";
		}
		terminalStr = "'" + terminalStr.replaceAll(",", "','") + "'";
		String sql = "select t.terminalID,t.terminalmodel,t.simnumber,p.patrolname,t.deviceid "
				+ "from terminalinfo t ,patrolmaninfo p "
				+ "where t.ownerid =p.patrolid"
				+ " and t.terminalID in ("
				+ terminalStr + ") order by p.patrolname ";
		logger.info("sql " + sql);
		return sublineDao.querySublineInfo(sql);
	}

	/**
	 * 组合select列表框中的内容，用于显示代维单位负责指定线路下的线段。
	 * @param lineid
	 *            线路id
	 * @param contractorid
	 *            代维单位id
	 * @return 
	 */
	public String getSelectOptions(String lineid, String contractorid) {
		List list = getSubline4line(lineid, contractorid);
		int size = list.size();
		String options = "<select name=\"original\" style=\"width: 200px\" size=\"15\" id=\"original\" multiple=\"multiple\" >";
		for (int i = 0; i < size; i++) {
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			options += "<option value=\"" + bean.get("sublineid") + "\">"
					+ bean.get("sublinename") + "</option>\n";
		}
		options += "</select>";
		return options;
	}

	/**
	 * 获得代维单位负责指定线路下的线段
	 * 
	 * @param lineid
	 *            线路id
	 * @param contractorid
	 *            代维单位id
	 * @return
	 */
	private List getSubline4line(String lineid, String contractorid) {
		String sql = "select s.sublineid,s.sublinename "
				+ "from sublineinfo s ,patrolmaninfo p "
				+ "where  p.patrolid= s.patrolid and p.parentid='"
				+ contractorid + "' and s.lineid='" + lineid + "'";
		logger.info("SQL :" + sql);
		return sublineDao.querySublineInfo(sql);
	}

}
