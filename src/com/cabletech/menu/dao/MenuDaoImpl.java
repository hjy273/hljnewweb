package com.cabletech.menu.dao;

import java.util.*;

import com.cabletech.commons.hb.*;
import com.cabletech.menu.domainobjects.*;
import org.apache.log4j.Logger;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import java.sql.ResultSet;

public class MenuDaoImpl {
	private static Logger logger = Logger.getLogger("MenuDaoImpl");

	public String getSql4(UserInfo userinfo) {
		String userID = userinfo.getUserID();
		String type = this.getGroupType(userinfo);
		// 取得指定编号用户拥有的三级菜单编号
		String sql4 = "	(						select SONMENUID "
				+ "							from USERGROUPMOUDULELIST  "
				+ "							where sonmenuid in ( select id "
				+ "							                     from sonmenu  "
				+ "							                     where  "// (INSTR(SONMENU.POWER,'"
														// + type + "') !=0) and
				+ " 														(type is null or type='' or type=(select deptype "
				+ "							                                                            from userinfo "
				+ "							                                                            where userid='"
				+ userID
				+ "')) "
				+ "											   ) "
				+ "							       and  usergroupid in (select usergroupid  "
				+ "							                                         from USERGOURPUSERLIST   "
				+ "							                                         where USERID='"
				+ userID + "' " + "													   ) " + " ) ";

		return sql4;
	}

	/**
	 * 取得一级菜单列表
	 * 
	 * @param regionid
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getFirstMenu(UserInfo userinfo,String businessType) throws Exception {
		Vector vct;
		Vector vMenu = new Vector();
		String userID = userinfo.getUserID();

		String sql = " select * "
				+ " from mainmodulemenu "
				+ " where id in ( "
				+ " 	select PARENTID from submenu  where id in ( "
				+ "    	select id  from submenu  where id in ( "
				+ " 	select PARENTID  from sonmenu   where ID in "
				+ 	this.getSql4(userinfo)
				+ "  ) ) )"
				+ " and businesstype in ("+businessType+")"
				+ " order by showno";
		QueryUtil queryutil = new QueryUtil();
		logger.info("一级菜单：" + sql);
		vct = queryutil.executeQueryGetStringVector(sql, "");
		for (int i = 0; i < vct.size(); i++) {
			Vector vTemp = (Vector) vct.get(i);
			FirstMenu menu = new FirstMenu();
			menu.setId((String) vTemp.get(0));
			menu.setLablename((String) vTemp.get(1));
			menu.setImgurl((String) vTemp.get(2));
			menu.setHrefurl((String) vTemp.get(3));
			menu.setRemark((String) vTemp.get(4));
			menu.setBusinessType(vTemp.get(5).toString());
			vMenu.add(menu);
		}
		return vMenu;
	}

	/**
	 * 通过用户组获得权限
	 * 
	 * @param ugs
	 * @return
	 * @throws Exception
	 */
	public Vector getFirstMenu(List ugs) throws Exception {

		Vector vct;
		Vector vMenu = new Vector();
		String ugs_str = "";
		for (int i = 0; i < ugs.size(); i++) {
			ugs_str += "'" + ugs.get(0) + "',";
		}
		String sql = "SELECT * FROM mainmodulemenu WHERE ID IN ("
				+ " 	SELECT parentid FROM submenu WHERE ID IN ("
				+ " 		SELECT ID FROM submenu WHERE ( type is NULL or type !='9') and ID IN ("
				+ "  			SELECT parentid FROM sonmenu WHERE ID IN ("
				+ "                SELECT sonmenuid FROM usergroupmoudulelist "
				+ "	   				WHERE usergroupid in ("
				+ ugs_str.substring(0, (ugs_str.length() - 1)) + "))"
				+ "						   )))";
		QueryUtil queryutil = new QueryUtil();
		logger.info("一级菜单：" + sql);
		vct = queryutil.executeQueryGetStringVector(sql, "");
		for (int i = 0; i < vct.size(); i++) {
			Vector vTemp = (Vector) vct.get(i);
			FirstMenu menu = new FirstMenu();
			menu.setId((String) vTemp.get(0));
			menu.setLablename((String) vTemp.get(1));
			menu.setImgurl((String) vTemp.get(2));
			menu.setHrefurl((String) vTemp.get(3));
			menu.setRemark((String) vTemp.get(4));
			vMenu.add(menu);
		}
		return vMenu;
	}

	/**
	 * 通过用户组获得用户二级菜单
	 * 
	 * @param ugs
	 * @return
	 * @throws Exception
	 */
	public Vector getSecondMenu(String parentID, List ugs) throws Exception {
		String ugs_str = "";
		for (int i = 0; i < ugs.size(); i++) {
			ugs_str += "'" + ugs.get(0) + "',";
		}
		// 取得指定编号用户拥有的三级菜单编号
		String sql4 = "	(	select SONMENUID from USERGROUPMOUDULELIST  "
				+ "							where sonmenuid in ( select id from sonmenu ) "
				+ "							and  usergroupid in ("
				+ ugs_str.substring(0, (ugs_str.length() - 1)) + "	) ) ";
		// 取得指定一级菜单编号和指定用户编号的二级菜单所有信息
		String sql2 = "select * from submenu" + " where parentid='" + parentID
				+ "'" + "       and id in( select parentid from sonmenu"
				+ "                                  where id in " + sql4
				+ "                ) and (type is null or type != '9')"
				+ " order by showno";
		Vector vct;
		logger.info("二级菜单：" + sql2);
		Vector vMenu = new Vector();
		QueryUtil queryutil = new QueryUtil();
		vct = queryutil.executeQueryGetStringVector(sql2, "");

		for (int i = 0; i < vct.size(); i++) {
			Vector vTemp = (Vector) vct.get(i);
			SecondlyMenu menu = new SecondlyMenu();

			menu.setId((String) vTemp.get(0));
			menu.setLablename((String) vTemp.get(1));
			menu.setHrefurl((String) vTemp.get(2));
			menu.setParentid((String) vTemp.get(3));
			menu.setRemark((String) vTemp.get(4));
			vMenu.add(menu);
		}
		return vMenu;
	}

	/**
	 * 取得二级菜单列表
	 * 
	 * @param regionid
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getSecondMenu(String parentID, UserInfo userinfo)
			throws Exception {
		// 取得指定一级菜单编号和指定用户编号的二级菜单所有信息
		String sql2 = "select * from submenu" + " where parentid='" + parentID
				+ "'" + "       and id in( select parentid from sonmenu"
				+ "                                  where id in "
				+ this.getSql4(userinfo)
				+ "                ) and (type is null or type != '9')"
				+ " order by showno";
		Vector vct;
		Vector vMenu = new Vector();
		QueryUtil queryutil = new QueryUtil();
		vct = queryutil.executeQueryGetStringVector(sql2, "");
		logger.info("二级菜单：" + sql2);
		for (int i = 0; i < vct.size(); i++) {
			Vector vTemp = (Vector) vct.get(i);
			SecondlyMenu menu = new SecondlyMenu();

			menu.setId((String) vTemp.get(0));
			menu.setLablename((String) vTemp.get(1));
			menu.setHrefurl((String) vTemp.get(2));
			menu.setParentid((String) vTemp.get(3));
			menu.setRemark((String) vTemp.get(4));
			vMenu.add(menu);
		}
		return vMenu;
	}

	/**
	 * 取得二级菜单列表
	 * 
	 * @param regionid
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getSecondMenu(UserInfo userinfo) throws Exception {
		// 取得指定一级菜单编号和指定用户编号的二级菜单所有信息
		String sql2 = "select * from submenu" + " where 1=1 "
				+ "       and id in( select parentid from sonmenu"
				+ "                                  where id in "
				+ this.getSql4(userinfo)
				+ "                ) and (type is null or type != '9')"
				+ " order by showno";
		Vector vct;
		Vector vMenu = new Vector();
		QueryUtil queryutil = new QueryUtil();
		vct = queryutil.executeQueryGetStringVector(sql2, "");
		logger.info("二级菜单：" + sql2);
		for (int i = 0; i < vct.size(); i++) {
			Vector vTemp = (Vector) vct.get(i);
			SecondlyMenu menu = new SecondlyMenu();

			menu.setId((String) vTemp.get(0));
			menu.setLablename((String) vTemp.get(1));
			menu.setHrefurl((String) vTemp.get(2));
			menu.setParentid((String) vTemp.get(3));
			menu.setRemark((String) vTemp.get(4));
			vMenu.add(menu);
		}
		return vMenu;
	}

	public Vector<FirstMenu> getFirstMenu() throws Exception {
		Vector<FirstMenu> vMenu = new Vector<FirstMenu>();
		String sql = "select distinct id, lablename,businessType from mainmodulemenu order by id";
		QueryUtil qUtil = new QueryUtil();
		ResultSet rs = qUtil.executeQuery(sql);
		while (rs.next()) {
			FirstMenu fm = new FirstMenu();
			fm.setId(rs.getString("id"));
			fm.setLablename(rs.getString("lablename"));
			fm.setBusinessType(rs.getString("businessType"));
			vMenu.add(fm);
		}
		return vMenu;
	}

	public Vector<SecondlyMenu> getSecondMenu() throws Exception {
		Vector<SecondlyMenu> vMenu = new Vector<SecondlyMenu>();
		String sql = "select distinct id, lablename, parentid from submenu where (type !='9' or type is null ) order by id";
		QueryUtil qUtil = new QueryUtil();
		ResultSet rs = qUtil.executeQuery(sql);
		while (rs.next()) {
			SecondlyMenu sm = new SecondlyMenu();
			sm.setId(rs.getString("id"));
			sm.setLablename(rs.getString("lablename"));
			sm.setParentid(rs.getString("parentid"));
			vMenu.add(sm);
		}
		return vMenu;
	}

	public Vector<ThirdMenu> getThirdMenu(String type) throws Exception {
		Vector<ThirdMenu> vMenu = new Vector<ThirdMenu>();
		String sql = "select distinct id, lablename, parentid from sonmenu where (type !='9' or type is null )  order by id";// and
																																// (INSTR(SONMENU.POWER,'"
																																// +
																																// type
																																// +
																																// "')
																																// !=0)
		QueryUtil qUtil = new QueryUtil();
		ResultSet rs = qUtil.executeQuery(sql);
		while (rs.next()) {
			ThirdMenu tm = new ThirdMenu();
			tm.setId(rs.getString("id"));
			tm.setLablename(rs.getString("lablename"));
			tm.setParentid(rs.getString("parentid"));
			vMenu.add(tm);
		}
		return vMenu;
	}

	/**
	 * 取得三级菜单列表
	 * 
	 * @param regionid
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getThirdMenu(String parentID, UserInfo rinuserinfofo)
			throws Exception {
		// 取得指定二级菜单编号和指定用户编号的三级菜单所有信息
		String sql3 = "		select * " + "	from sonmenu " + "	where parentid='"
				+ parentID + "' " + "   	  and hrefurl is not null "
				+ "	      and id in ( " + this.getSql4(rinuserinfofo) + ")"
				+ " and (type is null or type != '9')" + " order by id ";
		Vector vct;
		Vector vMenu = new Vector();
		QueryUtil queryutil = new QueryUtil();
		vct = queryutil.executeQueryGetStringVector(sql3, "");
		for (int i = 0; i < vct.size(); i++) {
			Vector vTemp = (Vector) vct.get(i);
			ThirdMenu menu = new ThirdMenu();
			menu.setId((String) vTemp.get(0));
			menu.setLablename((String) vTemp.get(1));
			menu.setParentid((String) vTemp.get(2));
			menu.setHrefurl((String) vTemp.get(3));
			menu.setRemark((String) vTemp.get(4));
			vMenu.add(menu);
		}
		logger.info("三级菜单：" + sql3);
		return vMenu;
	}

	public String getGroupType(UserInfo userinfo) {
		String type = "";
		String regionid = "";
		String sql = " SELECT um.ID,um.REGIONID,um.TYPE "
				+ " FROM USERGROUPMASTER um,USERGOURPUSERLIST uu "
				+ " WHERE uu.USERGROUPID=um.ID AND uu.USERID='"
				+ userinfo.getUserID() + "'";
		logger.info("SQL:" + sql);
		try {
			QueryUtil qu = new QueryUtil();
			ResultSet rs = qu.executeQuery(sql);
			if (rs.next()) {
				type = rs.getString("type");
				regionid = rs.getString("regionid");

			}
			rs.close();
			// if(regionid.substring(2).equals("0000"))
			if ("1".equals(type)) {
				if (userinfo.getType().equals("11"))
					type = type + "1";
				else
					type = type + "2";
			} else {
				type = type + "2";
			}
			return type;

		} catch (Exception ex) {
			logger.info("获取组类型出错：" + ex.getMessage(), ex);
			return "";
		}
	}

	/**
	 * 取得三级菜单列表
	 * 
	 * @param regionid
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getThirdMenu(String parentID, List ugs) throws Exception {
		String ugs_str = "";
		for (int i = 0; i < ugs.size(); i++) {
			ugs_str += "'" + ugs.get(0) + "',";
		}
		String sql4 = "	(	select SONMENUID from USERGROUPMOUDULELIST  "
				+ "							where sonmenuid in ( select id from sonmenu ) "
				+ "							and  usergroupid in ("
				+ ugs_str.substring(0, (ugs_str.length() - 1)) + "	) ) ";
		// 取得指定二级菜单编号和指定用户编号的三级菜单所有信息
		String sql3 = "		select * " + "	from sonmenu " + "	where parentid='"
				+ parentID + "' " + "   	  and hrefurl is not null "
				+ "	      and id in " + sql4
				+ " and (type is null or type != '9')" + " order by id ";
		Vector vct;
		Vector vMenu = new Vector();
		logger.info("三级菜单：" + sql3);
		QueryUtil queryutil = new QueryUtil();
		vct = queryutil.executeQueryGetStringVector(sql3, "");
		for (int i = 0; i < vct.size(); i++) {
			Vector vTemp = (Vector) vct.get(i);
			ThirdMenu menu = new ThirdMenu();
			menu.setId((String) vTemp.get(0));
			menu.setLablename((String) vTemp.get(1));
			menu.setParentid((String) vTemp.get(2));
			menu.setHrefurl((String) vTemp.get(3));
			menu.setRemark((String) vTemp.get(4));
			vMenu.add(menu);
		}

		return vMenu;
	}

	public Vector getResources() throws Exception {
		String sql ="select code,resourcename from resources where productenabled != '1'";
		Vector vct;
		Vector vResource = new Vector();
		logger.info("维护专业：" + sql);
		QueryUtil queryutil = new QueryUtil();
		vct = queryutil.executeQueryGetStringVector(sql, "");
		for (int i = 0; i < vct.size(); i++) {
			Vector vTemp = (Vector) vct.get(i);
			Resource res = new Resource();
			res.setCode(vTemp.get(0).toString());
			res.setResourceName(vTemp.get(1).toString());
			vResource.add(res);
		}

		return vResource;
	}
}
