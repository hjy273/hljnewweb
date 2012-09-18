package com.cabletech.analysis.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.NoteInfoBean;
import com.cabletech.analysis.dao.RealTimeNoteDAO;
import com.cabletech.baseinfo.domainobjects.UserInfo;

/**
 * 根据不同用户及不同的区域，取得短信发送数量信息。
 */
public class RealTimeNoteBO {
	private RealTimeNoteDAO noteDao;
	private Logger logger = Logger.getLogger("RealTimeNoteBO");
	private String commonsql = " r.simid,to_char(max(r.arrivetime),'yyyy-mm-dd hh24:mi:ss') arrivetime,"
			+ " r.handlestate, count(r.id) as item,to_char(min(r.arrivetime), 'yyyy-mm-dd hh24:mi:ss') minarrivetime "
			+ " from recievemsglog r,terminalinfo tl,patrolmaninfo t,contractorinfo c ,region d"
			+ " where c.contractorid = t.parentid and t.patrolid = tl.ownerid "
			+ "and tl.simnumber = r.simid and c.regionid = d.regionid  "
			+ " and c.state is null and t.state is null and (tl.state <> '1' or tl.state is null)"
			+ " and r.arrivetime between trunc(sysdate) and sysdate ";

	// + " and r.arrivetime > to_date('2007-10-18','YYYY-MM-DD hh24:mi:ss') ";
	/**
	 * 初始化noteDao实例．
	 */
	public RealTimeNoteBO() {
		noteDao = new RealTimeNoteDAO();
	}

	/**
	 * 默认查询用户所在区域所用巡检员发送的短信信息，如果当前选择区域currentRegion不为Null ，则加载该区域的短信信息。
	 * 注：该方法仅限省级用户查询。
	 * 
	 * @param currentRegion
	 *            当前选择查看的区域 当区域为null时，根据用户查询该用户下的所有区域信息，否则，查询该区域下所有代维单位的信息
	 * @param user
	 *            当前登录用户
	 * @return List 返回查询到的指定区域下的所有短信记录情况
	 */
	public List getAllNoteNum(String currentRegion, UserInfo user) {
		String sql = "";
		if (currentRegion == null) {
			sql = "select d.regionname titlename,d.regionid titleid,";
			sql += commonsql;
			sql += " group by d.regionid,d.regionname,r.simid, r.handlestate ";
			sql += " order by d.regionid,r.simid,r.handlestate";
		} else {
			sql = "select c.contractorname titlename,c.contractorid titleid,";
			sql += commonsql;
			sql += " and c.regionid='" + currentRegion + "'";
			sql += " group by c.contractorname,c.contractorid,r.simid, r.handlestate ";
			sql += " order by c.contractorid,r.simid,r.handlestate";
		}
		logger.info("sql" + sql);
		// todo1 ：执行查询
		ResultSet rs = noteDao.queryNoteNum(sql);

		// for{
		// todo2：运算产生短信总数、巡检、盯防、采集、隐患、其他数量信息。返回map
		// list.add(map);
		// }
		return sortStatForName(rs);
	}

	/**
	 * 该方法提供市移动用户查询本区域或代维单位短信发送情况。默认是查看部区域的短信信息 。若查看代维id不为null则查询该代维单位的短信发送情况。
	 * 注：仅限市移动用户查询
	 * 
	 * @param connid
	 *            当前选择查看的代维单位id，当代维单位id为null时，根据用户查询该
	 *            用户所在区域短信情况，否则，查询该代维单位下所有巡检组的短信情况。
	 * @param user
	 *            当前登录用户信息
	 * @return List 返回本区域或代维单位短信发送情况
	 */
	public List getAreaNoteNum(String connid, UserInfo user) {
		String sql = "";
		if (connid == null) {
			sql = "select c.contractorname titlename,c.contractorid titleid,";
			sql += commonsql;
			sql += " and c.regionid='" + user.getRegionid() + "'";
			sql += " group by c.contractorname,c.contractorid,r.simid, r.handlestate ";
			sql += " order by c.contractorid,r.simid,r.handlestate";
		} else {
			sql = "select t.patrolid titleid,t.patrolname titlename,";
			sql += commonsql;
			sql += " and c.contractorid='" + connid + "'";
			sql += " group by t.patrolid, t.patrolname,r.simid, r.handlestate ";
			sql += " order by t.patrolid,r.simid,r.handlestate";
		}
		logger.info("sql" + sql);
		// todo1 ：执行查询
		ResultSet rs = noteDao.queryNoteNum(sql);
		// for{
		// todo2：运算产生短信总数、巡检、盯防、采集、隐患、其他数量信息。返回map
		// list.add(map);
		// }
		if (connid != null) {
			logger.info("connid = " + connid);
			return sortStatForCon(rs);
		} else {
			return sortStatForName(rs);
		}
	}

	/**
	 * 该方法提供了代维用户查询所需要的功能，默认查询本代维单位的巡检员的短信发送情况。 若查询的巡检组id
	 * （patrolid）不为null时，查看该巡检组的短信发送情况。
	 * 
	 * @param patrolid
	 *            巡检组id 。当巡检组id为null时，根据用户查询该用户所在代维单位短信情况，否则，查询该巡检组下所有设备的短信情况
	 * @param user
	 *            当前登录用户信息
	 * @return List 返回本代维单位或该单位下巡检组的信息
	 */
	public List getConNoteNum(String patrolid, UserInfo user) {
		String sql = "";
		sql = "select t.patrolid titleid,t.patrolname titlename,";
		sql += commonsql;
		if (patrolid == null) {
			sql += " and c.contractorid='" + user.getDeptID() + "'";
		} else {
			sql += "and t.patrolid = '" + patrolid + "'";
		}
		sql += " group by t.patrolid, t.patrolname,r.simid, r.handlestate ";
		sql += " order by t.patrolid,r.simid,r.handlestate";
		logger.info("sql" + sql);
		// todo1 ：执行查询
		ResultSet rs = noteDao.queryNoteNum(sql);
		return sortStatForCon(rs);
	}

	/**
	 * 计算每个sim卡号发送的短信数量、及巡检、盯防、采集、事故数量等， 统计的每个sim卡的信息放到map中以list返回。
	 * 
	 * @param rs
	 *            查询数据库返回的结果集
	 * @return list 统计的每个sim卡的信息放到map中以list返回
	 */
	private List sortStatForCon(ResultSet rs) {
		int intItems = 0;
		NoteInfoBean note = new NoteInfoBean();
		if (rs == null) {
			return null;
		}
		List resultList = new ArrayList();
		try {
			String prevValue = "";
			String currentValue = "";
			String prevtitleName = "";
			while (rs.next()) {
				note.setTitleId(rs.getString("titleid"));
				String handleState = rs.getString("handlestate");
				currentValue = rs.getString("simid");
				if (!currentValue.equals(prevValue) && !"".equals(prevValue)) {
					note.setTitleName(prevtitleName);
					note.setSimid(prevValue);
					resultList.add(setMap(note, ""));
					note.clear();
				}
				/* 分类计算各类型短信个数 */
				intItems = rs.getInt("item");
				note = countNoteNumForType(intItems, handleState, note);
				// 取得最近一条短信时间和当天第一条短信时间
				if ("".equals(note.getLastTime())
						&& "".equals(note.getFirstTime())) {
					note.setLastTime(rs.getString("arrivetime"));
					note.setFirstTime(rs.getString("minarrivetime"));
				} else {
					if (rs.getString("arrivetime")
							.compareTo(note.getLastTime()) > 0) {
						note.setLastTime(rs.getString("arrivetime"));
					}
					if (rs.getString("minarrivetime").compareTo(
							note.getFirstTime()) < 0) {
						note.setFirstTime(rs.getString("minarrivetime"));
					}
				}
				prevValue = currentValue;
				prevtitleName = rs.getString("titlename");
			}
			note.setTitleName(prevtitleName);
			note.setSimid(prevValue);
			resultList.add(setMap(note, ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * 按照名称（包括区域、代维单位）统计短信发送量。 短信数量、及巡检、盯防、采集、事故数量等， 统计的每个sim卡的信息放到map中以list返回。
	 * 
	 * @param rs
	 *            ResultSet 查询的结果集
	 * @return list 统计的每个sim卡的信息放到map中以list返回
	 */
	private List sortStatForName(ResultSet rs) {
		int intItems = 0;
		NoteInfoBean note = new NoteInfoBean();
		if (rs == null) {
			return null;
		}
		int k = 0;
		List resultList = new ArrayList();
		try {
			String prevValue = "";
			String currentValue = "";
			while (rs.next()) {
				k++;
				String handleState = rs.getString("handlestate");
				note.setTitleId(rs.getString("titleid"));
				currentValue = rs.getString("titlename");
				if (!currentValue.equals(prevValue) && !"".equals(prevValue)) {
					note.setTitleName(prevValue);
					resultList.add(setMap(note, null));
					note.clear();
				}
				/* 分类计算各类型短信个数 */
				intItems = rs.getInt("item");
				note = countNoteNumForType(intItems, handleState, note);
				prevValue = currentValue;
			}
			note.setTitleName(prevValue);
			resultList.add(setMap(note, null));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	/**
	 * 分类计算各类型短信个数，然后以NoteInfoBean返回
	 * 
	 * @param intItems
	 *            系统接收的短信数目。
	 * @param handleState
	 *            设备状态 包括采集、隐患、盯防、匹配及其他。
	 * @param note
	 *            短信信息
	 * @return NoteInfoBean
	 */
	private NoteInfoBean countNoteNumForType(int intItems, String handleState,
			NoteInfoBean note) {
		if (handleState.equals("3")) { // 采集
			note.setIntCollect(intItems);
		} else if (handleState.equals("20")) { // 隐患
			note.setIntTrouble(intItems);
		} else if (handleState.equals("6") || handleState.equals("9")
				|| handleState.equals("11")) { // 盯防
			note.setIntWatch(intItems);
		} else if (handleState.equals("7") || handleState.equals("8")
				|| handleState.equals("4") || handleState.equals("12")
				|| handleState.equals("10")) { // 匹配
			note.setIntPatrol(intItems);
		} else {
			// 其它
			note.setIntOther(intItems);
		}
		// 计算总数
		note.setIntTotal(intItems);
		return note;
	}

	/**
	 * 将noteinfo信息装载到map中。
	 * 
	 * @param note
	 *            短信信息
	 * @param type
	 *            为null时表示 不需要simid，firsttime，lasttime信息，否则，需要。
	 * @return Map 返回指定名称的短信分类.
	 */
	private Map setMap(NoteInfoBean note, String type) {
		Map map = new HashMap();

		if (type != null) {
			map.put("simid", note.getSimid());
			map.put("firsttime", note.getFirstTime());
			map.put("lasttime", note.getLastTime());
		}
		map.put("titlename", note.getTitleName());
		map.put("total", new Integer(note.getIntTotal()));
		map.put("patrol", new Integer(note.getIntPatrol()));
		map.put("collect", new Integer(note.getIntCollect()));
		map.put("watch", new Integer(note.getIntWatch()));
		map.put("trouble", new Integer(note.getIntWatch()));
		map.put("other", new Integer(note.getIntOther()));
		return map;
	}

}
