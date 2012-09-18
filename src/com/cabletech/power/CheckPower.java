package com.cabletech.power;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.services.DBService;
import com.cabletech.menu.dao.MenuDaoImpl;

public class CheckPower {
	private static Logger logger = Logger.getLogger(CheckPower.class);

	public CheckPower() {
	}

	/**
	 *<p> 功 能：查询指定用户在系统中拥有权限的所有模块。</p>     *
	 *<p> 参 数：用户的ID</p>
	 *<p> 返回值:用户拥有权限模块的id值和对应的模块名称id和模块名称之间用空格隔开，各id之间用逗号隔开。</p>
	 **/
	public static String getMoudules(UserInfo userinfo) {
		String strMoud = "";
		String strTemp = "";
		String userId = userinfo.getUserID();
		String result[][];
		MenuDaoImpl dao = new MenuDaoImpl();
		String type = dao.getGroupType(userinfo);
		String sql = "select distinct sonmenu.ID,sonmenu.LABLENAME "
				+ " from usergroupmaster,usergroupmoudulelist,sonmenu "
				+ " where usergroupmaster.ID=usergroupmoudulelist.USERGROUPID "
				+ " and sonmenu.ID=usergroupmoudulelist.SONMENUID " + " and (INSTR(SONMENU.POWER,'" + type + "') !=0) "
				+ " and usergroupmaster.ID in (select distinct usergroupid from usergourpuserlist " + " where userid='"
				+ userId + "')" + " order by sonmenu.ID";
		logger.info("SQL:" + sql);
		try {
			DBService dbservice = new DBService();
			result = dbservice.queryArray(sql, strTemp);
			for (String[] element : result) {
				strMoud = strMoud + element[0] + "  " + element[1] + ",";
			}
			return strMoud.trim();
		} catch (Exception e) {
			logger.error("取得用户权限错误:" + e.getMessage());
			return " ";
		}

	}

	public static String getMoudules(String userType, List ugroups) {
		String strMoud = "";
		String strTemp = "";
		String ugs_str = "";
		for (int i = 0; i < ugroups.size(); i++) {
			ugs_str += "'" + ugroups.get(0) + "',";
		}
		String result[][];
		MenuDaoImpl dao = new MenuDaoImpl();
		String sql = "select distinct sonmenu.ID,sonmenu.LABLENAME "
				+ " from usergroupmaster,usergroupmoudulelist,sonmenu "
				+ " where usergroupmaster.ID=usergroupmoudulelist.USERGROUPID "
				+ " and sonmenu.ID=usergroupmoudulelist.SONMENUID " + " and (INSTR(SONMENU.POWER,'" + userType
				+ "') !=0) " + " and usergroupmaster.ID in (" + ugs_str.substring(0, (ugs_str.length() - 1)) + ")"
				+ " order by sonmenu.ID";
		logger.info("SQL:" + sql);
		try {
			DBService dbservice = new DBService();
			result = dbservice.queryArray(sql, strTemp);
			for (String[] element : result) {
				strMoud = strMoud + element[0] + "  " + element[1] + ",";
			}
			return strMoud.trim();
		} catch (Exception e) {
			logger.error("取得用户权限错误:" + e.getMessage());
			return " ";
		}

	}

	/***
	 * <p> 功 能：检查当前用户对指定的模块是否具有指定的权限。
	 * <p> 参 数：1、session， 2、二级菜单模块id（可以从sonmenu表中查看）；
	 *           3、不超过两位的权限值，一般规定：1，查询功能；2，增加功能；3，高级查询功能；4，修改功能；5，删除功能</p>
	 * <p>返回值：拥有权限返回真值，没有权限返回假值。</p>
	 * **/
	public static boolean checkPower(HttpSession session, String secMoudcode, String power) {
		String strUserPower = ""; //当前用户拥有权限的所有模块id和名称的列表
		String strMoudcode = ""; //所要查询的功能模块id
		int posit = 0;
		if (power.length() >= 3) {
			logger.info("错误的输入：权限的长度超过两位");
			return false;
		}
		if (power.length() == 1) {
			power = "0" + power;
		}

		strUserPower = (String) session.getAttribute("USERCURRENTPOWER");
		strMoudcode = secMoudcode + power;
		posit = strUserPower.indexOf(strMoudcode);
		if (posit < 0)
			return false;
		return true;
	}

	/***
	 * <p>  功  能：检查当前用户对指定的模块是否具有指定的权限。
	 * <p>  参  数：1、session， 2、三级菜单模块id（可以从sonmenu表中查看），在三级菜单中最后两位一般规定：01，查询功能；02，增加功能；03，高级查询功能；04，修改功能；05，删除功能
	 * <p>  返回值：拥有权限返回真值，没有权限返回假值。
	 * **/

	public static boolean checkPower(HttpSession session, String moudcode) {
		String strUserPower = ""; //当前用户拥有权限的所有模块id和名称的列表
		String strMoudcode = ""; //所要查询的模块id
		int posit = 0;

		strUserPower = (String) session.getAttribute("USERCURRENTPOWER");
		strMoudcode = moudcode;
		posit = strUserPower.indexOf(strMoudcode);
		// logger.info("POWER:" + strMoudcode);
		//logger.info("POWERPSIT:" + posit);
		if (posit < 0)
			return false;
		return true;
	}

	/***
	 * <p>  功  能：检查当前用户对指定的模块是否具有指定的权限。
	 * <p>  参  数：1、session， 2、三级菜单模块名称（可以从sonmenu表中查看）。
	 * <p>  返回值：拥有权限返回真值，没有权限返回假值。
	 * **/

	public static boolean checkPowerByName(HttpSession session, String moudname) {
		String strUserPower = ""; //当前用户拥有权限的所有模块id和名称的列表
		int posit = 0;

		strUserPower = (String) session.getAttribute("USERCURRENTPOWER");
		posit = strUserPower.indexOf(moudname);
		if (posit < 0)
			return false;
		return true;
	}

}
