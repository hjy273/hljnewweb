package com.cabletech.baseinfo.services;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import com.cabletech.baseinfo.beans.*;
import com.cabletech.baseinfo.dao.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.util.*;
import com.cabletech.utils.*;

public class UserInfoBO extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger(UserInfoBO.class);

	private long timer = 0l;

	/**
	 * addUserInfo
	 */
	UserInfoDAOImpl dao = new UserInfoDAOImpl();

	// UserInfoDAO dao=DaoFactory.createDao();

	public void addUserInfo(UserInfo data) throws Exception {
		dao.addUserInfo(data);
	}

	public void removeUserInfo(UserInfo data) throws Exception {
		dao.removeUserInfo(data);
	}

	/**
	 * 通过id查找用户信息
	 * 
	 * @param id
	 *            String
	 * @return UserInfo
	 * @throws Exception
	 */
	public UserInfo loadUserInfo(String id) throws Exception {
		return dao.findById(id);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 *            UserInfo
	 * @return UserInfo
	 * @throws Exception
	 */
	public UserInfo updateUserInfo(UserInfo userInfo) throws Exception {

		return dao.updateUserInfo(userInfo);
	}

	/**
	 * 根据用户，得到该代维用户的部门ID
	 * 
	 * @param userId
	 * @return -1表示不是代维或者監理用户， 2为代维用户，3为监理用户
	 */
	public String getContractDeptType(String userId) {
		String sql = "select c.depttype from userinfo t ,contractorinfo c where t.deptid=c.contractorid and t.userid='"
				+ userId + "'";
		try{
			QueryUtil query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			if(rs.next()){
				return rs.getString("depttype");
			}
		}catch(Exception ex){
			logger.error("",ex);
		}
		return "-1";
	}

	/**
	 * loadDeptUser
	 * 
	 * @param deptid
	 *            String
	 * @return List
	 */
	public List loadDeptUser(UserInfo userInfo) {
		String sql = "";
		QueryUtil query = null;
		BasicDynaBean dynaBean = null;
		Vector resultVct = new Vector();
		ArrayList lableList = new ArrayList();
		sql = "select userid , username from userinfo u  ";
		if ("2".equals(userInfo.getDeptype())) {
			if (userInfo.getType().equals("11")) {
				sql += " WHERE u.deptid IN (SELECT contractorid"
						+ "                      FROM contractorinfo c"
						+ "                            CONNECT BY PRIOR c.contractorid = c.parentcontractorid"
						+ "                            START WITH c.contractorid = '"
						+ userInfo.getDeptID() + "')";

			} else {
				sql += " WHERE u.DEPTID = '" + userInfo.getDeptID() + "'";
			}
		} else {
			if (userInfo.getType().equals("11")) {
				sql += "  WHERE u.REGIONID in("
						+ "         SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
						+ userInfo.getRegionID()
						+ "'"
						+ "        )"

						+ "        and (u.DEPTYPE='1' OR (u.DEPTYPE='2' and u.REGIONID='"
						+ userInfo.getRegionID() + "'))";
			} else {
				sql += " WHERE u.REGIONID = '" + userInfo.getRegionID() + "'";
			}
		}
		sql += " and password is not null and state is null "
				+ "ORDER BY TO_NUMBER(u.positionno),u.REGIONID,u.DEPTYPE,u.POSITION,u.USERNAME";
		// + "ORDER BY u.REGIONID,u.USERNAME";
		// System.out.println( "SQL:" + sql );
		try {
			query = new QueryUtil();
			Iterator it = query.queryBeans(sql).iterator();
			while (it.hasNext()) {
				dynaBean = (BasicDynaBean) it.next();
				// lableList.add(new
				// LabelValueBean((String)(dynaBean.get("userid")),(String)(dynaBean.get("username")))
				// );
				lableList.add(dynaBean);
			}
			resultVct.add(lableList);
			// //System.out.println(resultVct);
			return resultVct;
		} catch (Exception ex) {
			logger.error("加载部门用户时出错：" + ex.getMessage());
			return null;
		}
	}

	/**
	 * loadAllUser
	 * 
	 * @param deptid
	 *            String
	 * @return List
	 */
	public List loadAllUser(UserInfo userInfo) {
		String sql = "";
		QueryUtil query = null;
		BasicDynaBean dynaBean = null;
		Vector resultVct = new Vector();
		ArrayList lableList = new ArrayList();
		sql = "select userid , username from userinfo u  ";
		if ("2".equals(userInfo.getDeptype())) {
			if (userInfo.getType().equals("11")) {
				sql += " WHERE u.deptid IN (SELECT contractorid"
						+ "                      FROM contractorinfo c"
						+ "                            CONNECT BY PRIOR c.contractorid = c.parentcontractorid"
						+ "                            START WITH c.contractorid = '"
						+ userInfo.getDeptID() + "')";

			} else {
				sql += " WHERE u.DEPTID = '" + userInfo.getDeptID() + "'";
			}
		} else {
			if (userInfo.getType().equals("11")) {
				sql += "  WHERE u.REGIONID in("
						+ "         SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
						+ userInfo.getRegionID() + "'" + "        )";
				// 去除不允许修改市代维用户的限制
				// + " and (u.DEPTYPE='1' OR (u.DEPTYPE='2' and u.REGIONID='" +
				// userInfo.getRegionID() + "'))";
			} else {
				sql += " WHERE u.REGIONID = '" + userInfo.getRegionID() + "'";
			}
		}
		sql += " and password is not null and state is null "
		// + "ORDER BY
				// TO_NUMBER(u.positionno),u.REGIONID,u.DEPTYPE,u.POSITION,u.USERNAME";
				+ "ORDER BY u.REGIONID,u.USERNAME";
		// System.out.println( "SQL:" + sql );
		try {
			query = new QueryUtil();
			Iterator it = query.queryBeans(sql).iterator();
			while (it.hasNext()) {
				dynaBean = (BasicDynaBean) it.next();
				// lableList.add(new
				// LabelValueBean((String)(dynaBean.get("userid")),(String)(dynaBean.get("username")))
				// );
				lableList.add(dynaBean);
			}
			resultVct.add(lableList);
			// //System.out.println(resultVct);
			return resultVct;
		} catch (Exception ex) {
			logger.error("加载部门用户时出错：" + ex.getMessage());
			return null;
		}
	}

	/**
	 * updatePsw
	 * 
	 * @param userid
	 *            String
	 * @param p1
	 *            String
	 * @return boolean
	 */
	public boolean updatePsw(UserInfo userinfo) {
		String sql = "update userinfo set password = '"
				+ userinfo.getPassword() + "',newpsdate = sysdate,oldps='"
				+ userinfo.getOldps() + "'  where userid='"
				+ userinfo.getUserID() + "'";
		// System.out.println( "SQL:" + sql );
		UpdateUtil update = null;
		try {
			update = new UpdateUtil();
			update.executeUpdate(sql);
			update.commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 验证密码是否与前5次重复
	 * 
	 * @param UserInfo
	 *            userinfo
	 * @return boolean
	 */
	public boolean validatePsRepeat(UserInfo userinfo) {
		StrReformSplit strsplit = new StrReformSplit();
		Vector vresult = new Vector();
		try {
			vresult = strsplit.getVctFromStrWithToken(userinfo.getOldps(), "*");
			for (int i = 0; i < vresult.size(); i++) {
				if (userinfo.getPassword().equals(vresult.get(i))) {
					return false;
				}
			}
			return true;
		} catch (Exception ex) {
			logger.info("验证用户密码时异常: " + ex.getMessage());
			return false;
		}
	}

	/**
	 * 获取新的组合字符串oldps
	 * 
	 * @param userinfo
	 *            UserInfo
	 * @return String
	 */
	public String getNewOldps(UserInfo userinfo) {
		StrReformSplit strsplit = new StrReformSplit();
		userinfo.getPassword();
		StringBuffer oldps = new StringBuffer();
		Vector oldpsvct = new Vector();
		try {

			oldpsvct = strsplit
					.getVctFromStrWithToken(userinfo.getOldps(), "*");// 将oldpassword
			// 字符串拆分为vector
			if (oldpsvct.size() != 0) {
				if (oldpsvct.size() == 5) {
					oldpsvct.remove(oldpsvct.size() - 1);// 从数组中移除最早的密码；
				}
				oldpsvct.add(0, userinfo.getPassword());
				for (int i = 0; i < oldpsvct.size() - 1; i++) {
					oldps.append(oldpsvct.get(i) + "*");
				}
				oldps.append(oldpsvct.get(oldpsvct.size() - 1));
				// //System.out.println( "test :" + oldps.toString() );
			} else {
				oldps.append(userinfo.getPassword());
			}
			return oldps.toString();

		} catch (Exception ex) {
			// System.out.println( "获取新的oldps字符串时异常: " + ex.getMessage() );
			return userinfo.getOldps();
		}
	}

	public int viladateNewPsDate(UserInfo userinfo) {
		// long timer = getPassWordTimer();
		long V1 = getPassWordTimer(); // 获取密码时效
		long V2 = 10 * 24 * 60 * 60 * 1000l; // 10天
		// timer = V1;
		setTimer(V1);
		if (userinfo.getNewpsdate() == null) {
			return 0;
		}
		long newpsdate = userinfo.getNewpsdate().getTime(); // 密码开始计时日期
		Date nowDate = new Date(); // 当前时间
		long now = nowDate.getTime();
		long start = newpsdate + (V1 - V2);// 提示密码开始日期
		long end = newpsdate + V1; // 提示密码结束日期
		logger.info("start " + DateUtil.DateToTimeString(new Date(start)));
		logger.info("now  " + DateUtil.DateToTimeString(new Date(now)));
		logger.info("end  " + DateUtil.DateToTimeString(new Date(end)));
		if (V1 != 0l) {
			if (start < now && now < end) {
				return 1; // 提示用户更改密码
			} else {
				if (now > end) {
					return 2; // 密码失效 ,禁止用户登陆
				} else {
					return 0; // 正常登陆
				}
			}
		} else {
			return 0;
		}
	}

	public long getPassWordTimer() {
		ResultSet rst = null;
		long timer = 0l;
		String sql = "select maxsize from sysdictionary where value='00000004'";
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQuery(sql);
			String value = "0";
			if (rst != null && rst.next()) {
				value = rst.getString("maxsize");
			}
			timer = Long.parseLong(value);
			timer = timer * 24 * 60 * 60 * 1000;
		} catch (Exception ex) {
			timer = 0l;
			logger.info("获取密码期限异常：" + ex.getMessage());
		} finally {
			try {
				rst.close();
			} catch (SQLException ex1) {
				rst = null;
				logger.error("ResultSet close Exception: " + ex1.getMessage());
			}
		}
		return timer;
	}

	public static void main(String[] args) {
		UserInfoBO bo = new UserInfoBO();
		UserInfo u = new UserInfo();
		u.setPassword("1231");
		u.setOldps("1221");
		logger.info(bo.getNewOldps(u));

		Date nowDate = new Date();
		String nowDateStr = DateUtil.DateToTimeString(nowDate);
		long a = 7776000000l;
		long b = 9314000000l;
		long t = nowDate.getTime() - b;
		Date d = new Date(t);

		u.setNewpsdate(d);

	}

	public boolean setuptime(UserInfoBean bean) {
		String sql = "update userinfo set accidenttime='"
				+ bean.getAccidenttime() + "',onlinemantime='"
				+ bean.getOnlinemantime() + "',watchtime='"
				+ bean.getWatchtime() + "' where userid='" + bean.getUserID()
				+ "' ";
		try {
			UpdateUtil up = new UpdateUtil();
			up.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			logger.warn("设置显示时间异常:" + e.getMessage());
			return false;
		}
	}

	public void setTimer(long timer) {
		this.timer = timer;
	}

	public long getTimer() {
		return timer;
	}

	/**
	 * 更新密码
	 * 
	 * @param data
	 *            UserInfo
	 */
	public void setingPassword(UserInfo userInfo) throws Exception {
		dao.updateUserInfo(userInfo);
	}
	/**
	 *获得所有用户信息
	*/
	@SuppressWarnings("unchecked")
	public List<UserInfo> loadAllUserInfo(){
		String hql="from UserInfo";
		List<UserInfo> userInfos=new ArrayList<UserInfo>();
			try {
				userInfos=(List<UserInfo>)dao.getHibernateTemplate().find(hql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return userInfos;
	}
	public Map<String,String> loadAllUserIdAndName(){
		Map<String,String> userIdName=new HashMap<String, String>();
			List<UserInfo> userInfos=loadAllUserInfo();
			for(UserInfo userInfo:userInfos){
				userIdName.put(userInfo.getUserID(), userInfo.getUserName());
			}
		return userIdName;
	}
}
