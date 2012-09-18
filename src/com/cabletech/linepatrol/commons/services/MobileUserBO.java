package com.cabletech.linepatrol.commons.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.web.WebAppUtils;
import com.cabletech.linepatrol.commons.dao.MobileUserDAO;

@Service("mobileUserBo")
@Transactional
public class MobileUserBO extends BaseBisinessObject {
	public static final String DEPART_PREX = "D";
	public static final String DEPART_NAME_PREX = "DN";

	public static final String USER_PREX = "U";

	public static final String MOBILE_PREX = "M";

	private static Logger logger = Logger.getLogger(MobileUserBO.class);

	@Resource(name = "mobileUserDao")
	private MobileUserDAO dao;
	private long timer = 0l;

	/**
	 * 执行根据查询条件获取线维中心用户信息列表
	 * 
	 * @param inputType
	 */
	public String loadProcessors(String queryValue, UserInfo userInfo,
			String existValue, String inputType) {
		String[] existUserId = null;
		if (existValue != null) {
			existUserId = existValue.split(",");
		}
		String jsonText = "";

		List departList = null;
		List userList = null;
		String job = "";

		String condition = "";
		condition = condition + " and exists( ";
		condition = condition + " select r.regionid from region r ";
		condition = condition + " where r.regionid=c.regionid ";
		condition = condition + " start with r.regionid='";
		condition = condition + userInfo.getRegionid();
		condition = condition + "' ";
		condition = condition + " connect by prior ";
		condition = condition + " r.regionid=r.parentregionid ";
		condition = condition + " ) ";
		condition = condition + " and ugl.usergroupid='"
				+ WebAppUtils.approverGroupId + "' ";
		departList = dao.getMobileDeptList(condition);
		condition = generateCondition(queryValue);
		condition = condition + " and exists( ";
		condition = condition + " select r.regionid from region r ";
		condition = condition + " where r.regionid=c.regionid ";
		condition = condition + " start with r.regionid='";
		condition = condition + userInfo.getRegionid();
		condition = condition + "' ";
		condition = condition + " connect by prior ";
		condition = condition + " r.regionid=r.parentregionid ";
		condition = condition + " ) ";
		condition = condition + " and ugl.usergroupid='"
				+ WebAppUtils.approverGroupId + "' ";
		userList = dao.getMobileUserList(condition);

		JSONObject root = new JSONObject();
		root.put("id", "0");
		JSONObject departNode;
		JSONObject userNode;
		JSONArray departArray;
		JSONArray userArray;
		departArray = new JSONArray();
		DynaBean oneDepart;
		DynaBean oneUser;
		for (int i = 0; departList != null && i < departList.size(); i++) {
			boolean flag = false;
			oneDepart = (DynaBean) departList.get(i);
			String departId = (String) oneDepart.get("deptid");
			departNode = new JSONObject();
			departNode.put("id", DEPART_PREX + departId + DEPART_NAME_PREX
					+ oneDepart.get("deptname"));
			departNode.put("text", (String) oneDepart.get("deptname"));
			if (inputType != null && "radio".equals(inputType)) {
				departNode.put("radio", "1");
			}
			userArray = new JSONArray();
			for (int j = 0; userList != null && j < userList.size(); j++) {
				oneUser = (DynaBean) userList.get(j);
				if (oneUser != null && userInfo != null) {
					if (userInfo.getUserID().equals(oneUser.get("userid"))) {
						continue;
					}
				}
				if (departId != null
						&& departId.equals((String) oneUser.get("deptid"))) {
					userNode = new JSONObject();
					userNode.put("id", DEPART_PREX + departId
							+ DEPART_NAME_PREX + oneDepart.get("deptname")
							+ USER_PREX + (String) oneUser.get("userinfo")
							+ MOBILE_PREX + (String) oneUser.get("mobile"));
					// userNode.put("text", oneUser.get("name"));
					if (oneUser.get("position") != null) {
						job = (String) oneUser.get("position");
						userNode.put("text", oneUser.get("name") + "--职位："
								+ job);
					} else {
						job = "";
						userNode.put("text", oneUser.get("name"));
					}
					// if (existUserList != null
					// && existUserList.contains((String)
					// oneUser.get("userid"))) {
					// userNode.put("checked", "true");
					// }
					if (inputType != null && "radio".equals(inputType)) {
						userNode.put("radio", "1");
					}
					for (int index = 0; existUserId != null
							&& index < existUserId.length; index++) {
						if (existUserId[index] != null
								&& existUserId[index].equals(oneUser
										.get("userid"))) {
							userNode.put("checked", "true");
							break;
						}
					}
					userArray.add(userNode);
					flag = true;
				}
				if (flag) {
					departNode.put("item", userArray);
				}
			}
			if (flag) {
				departArray.add(departNode);
			}
		}
		root.put("item", departArray);

		if (root != null) {
			jsonText = root.toString();
		}
		return jsonText;
	}

	public String[] parseProcessors(String selectMans) {
		String[] selectMan;
		String depart;
		String departName = "";
		String user;
		String mobile;
		String departs = "";
		String users = "";
		String departUsers = "";
		String userNames = "";
		String mobiles = "";
		List departList = new ArrayList();
		List userList = new ArrayList();
		List mobileList = new ArrayList();
		if (selectMans != null && !selectMans.equals("")) {
			if (selectMans.indexOf("~") != -1) {
				selectMan = selectMans.split("~");
				for (int i = 0; selectMan != null && i < selectMan.length; i++) {
					if (selectMan[i].indexOf(USER_PREX) != -1) {
						depart = selectMan[i].substring(0, selectMan[i]
								.indexOf(USER_PREX));
						if (depart.startsWith(DEPART_PREX)) {
							departName = depart.substring(depart
									.indexOf(DEPART_NAME_PREX) + 2);
							depart = depart.substring(1, depart
									.indexOf(DEPART_NAME_PREX));
						}
						if (!departList.contains(depart)) {
							departList.add(depart);
							departs = departs + depart + ",";
							userNames = userNames + departName + ":";
						}
						user = selectMan[i].substring(selectMan[i]
								.indexOf(USER_PREX), selectMan[i]
								.indexOf(MOBILE_PREX));
						// userInfo = new ArrayList();
						if (user.startsWith(USER_PREX)) {
							user = user.substring(1);
						}
						if (!userList.contains(user)) {
							// userInfo.add(depart);
							// userInfo.add(user);
							// userList.add(userInfo);
							// existUserList.add(user);
							userList.add(user);
							if (user != null && user.indexOf("-") != -1) {
								users = users + user.split("-")[0] + ",";
								departUsers += (depart + ";"
										+ user.split("-")[0] + ",");
								userNames = userNames + user.split("-")[1]
										+ ",";
							} else {
								users = users + user + ",";
								departUsers += (depart + ";" + user + ",");
								userNames = userNames + user + ",";
							}
						}
						mobile = selectMan[i].substring(selectMan[i]
								.indexOf(MOBILE_PREX));
						if (mobile.startsWith(MOBILE_PREX)) {
							mobile = mobile.substring(1);
						}
						if (!mobileList.contains(mobile)) {
							mobileList.add(mobile);
							mobiles = mobiles + mobile + ",";
						}
					} else {
						depart = selectMan[i];
						if (depart.startsWith(DEPART_PREX)) {
							departName = depart.substring(depart
									.indexOf(DEPART_NAME_PREX) + 2);
							depart = depart.substring(1, depart
									.indexOf(DEPART_NAME_PREX));
						}
						if (!departList.contains(depart)) {
							departList.add(depart);
							departs = departs + depart + ",";
							userNames = userNames + departName + ":";
						}
					}
				}
			} else {
				if (selectMans.indexOf(USER_PREX) != -1) {
					depart = selectMans.substring(0, selectMans
							.indexOf(USER_PREX));
					if (depart.startsWith(DEPART_PREX)) {
						departName = depart.substring(depart
								.indexOf(DEPART_NAME_PREX) + 2);
						depart = depart.substring(1, depart
								.indexOf(DEPART_NAME_PREX));
					}
					departList.add(depart);
					departs = departs + depart + ",";
					userNames = userNames + departName + ":";
					user = selectMans.substring(selectMans.indexOf(USER_PREX),
							selectMans.indexOf(MOBILE_PREX));
					if (user.startsWith(USER_PREX)) {
						user = user.substring(1);
					}
					// userInfo = new ArrayList();
					// userInfo.add(depart);
					// userInfo.add(user);
					// userList.add(userInfo);
					// existUserList.add(user);
					userList.add(user);
					if (user != null && user.indexOf("-") != -1) {
						users = users + user.split("-")[0] + ",";
						departUsers += (depart + ";" + user.split("-")[0] + ",");
						userNames = userNames + user.split("-")[1] + ",";
					} else {
						users = users + user + ",";
						departUsers += (depart + ";" + user + ",");
						userNames = userNames + user + ",";
					}
					mobile = selectMans.substring(selectMans
							.indexOf(MOBILE_PREX));
					if (mobile.startsWith(MOBILE_PREX)) {
						mobile = mobile.substring(1);
					}
					if (!mobileList.contains(mobile)) {
						mobileList.add(mobile);
						mobiles = mobiles + mobile + ",";
					}
				} else {
					depart = selectMans;
					if (depart.startsWith(DEPART_PREX)) {
						departName = depart.substring(depart
								.indexOf(DEPART_NAME_PREX) + 2);
						depart = depart.substring(1, depart
								.indexOf(DEPART_NAME_PREX));
					}
					departList.add(depart);
					departs = departs + depart + ",";
					userNames = userNames + departName + ":";
				}
			}
		}
		return new String[] { departs, users, mobiles, departUsers, userNames };
	}

	public List getWapMobileDepartList(UserInfo userInfo) {
		String condition = "";
		condition = condition + " and exists( ";
		condition = condition + " select r.regionid from region r ";
		condition = condition + " where r.regionid=c.regionid ";
		condition = condition + " start with r.regionid='";
		condition = condition + userInfo.getRegionid();
		condition = condition + "' ";
		condition = condition + " connect by prior ";
		condition = condition + " r.regionid=r.parentregionid ";
		condition = condition + " ) ";
		condition = condition + " and ugl.usergroupid='"
				+ WebAppUtils.approverGroupId + "' ";
		return dao.getMobileDeptList(condition);
	}

	public List getWapMobileUserList(UserInfo userInfo) {
		String condition = "";
		condition = condition + " and exists( ";
		condition = condition + " select r.regionid from region r ";
		condition = condition + " where r.regionid=c.regionid ";
		condition = condition + " start with r.regionid='";
		condition = condition + userInfo.getRegionid();
		condition = condition + "' ";
		condition = condition + " connect by prior ";
		condition = condition + " r.regionid=r.parentregionid ";
		condition = condition + " ) ";
		condition = condition + " and ugl.usergroupid='"
				+ WebAppUtils.approverGroupId + "' ";
		return dao.getMobileUserList(condition);
	}

	/**
	 * 根据用户请求生成查询条件
	 * 
	 * @param queryValue
	 * @return
	 */
	public String generateCondition(String queryValue) {
		if (queryValue != null && !"".equals(queryValue)) {
			StringBuffer buf = new StringBuffer();
			buf.append(" and (");
			buf.append(" (c.deptname like '%" + queryValue + "%') or ");
			buf.append(" (u.username like '%" + queryValue + "%') or ");
			buf.append(" (u.phone like '%" + queryValue + "%') ");
			// buf.append(" (u.jobinfo like '%" + queryValue + "%') ");
			buf.append(") ");
			return buf.toString();
		}
		return "";
	}
}
