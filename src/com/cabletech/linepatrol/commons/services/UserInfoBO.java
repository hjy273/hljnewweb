package com.cabletech.linepatrol.commons.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.ReplyApprover;

@Service("userInfoBo")
@Transactional
public class UserInfoBO extends BaseBisinessObject {
	private static Logger logger = Logger.getLogger(UserInfoBO.class);
	public static final String DEPART_PREX = "D";
	public static final String DEPART_NAME_PREX = "DN";

	public static final String USER_PREX = "U";

	public static final String MOBILE_PREX = "M";

	private long timer = 0l;

	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl dao = new UserInfoDAOImpl();
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDao = new ReplyApproverDAO();

	@Transactional(readOnly = true)
	/**
	 * 执行根据查询条件获取线维中心用户信息列表
	 */
	public List loadApprovers(String queryValue, UserInfo userInfo,
			String existValue, String notAllowValue, String displayType,
			String departId, String exceptSelf) {
		String condition = generateCondition(queryValue);
		if (displayType != null && displayType.equals("mobile")) {
			condition = condition + " and deptype='1' ";
			condition = condition + " and exists( ";
			condition = condition + " select r.regionid from region r ";
			condition = condition + " where r.regionid=u.regionid ";
			condition = condition + " start with r.regionid='";
			condition = condition + userInfo.getRegionid();
			condition = condition + "' ";
			condition = condition + " connect by prior ";
			condition = condition + " r.regionid=r.parentregionid ";
			condition = condition + " ) ";
			condition = condition + " and ugl.usergroupid='"
					+ WebAppUtils.approverGroupId + "' ";
		} else {
			condition = condition + " and deptype='2' ";
			condition = condition + " and exists( ";
			if (departId != null && !"".equals(departId)) {
				condition = condition
						+ " select c.contractorid from contractorinfo c ";
				condition = condition + " where c.contractorid=u.deptid ";
				condition = condition + " start with c.contractorid='";
				condition = condition + departId;
				condition = condition + "' ";
				condition = condition + " connect by prior ";
				condition = condition + " c.contractorid=c.parentcontractorid ";
			} else {
				condition = condition
						+ " select c.contractorid from contractorinfo c ";
				condition = condition + " where c.contractorid=u.deptid ";
				condition = condition + " start with c.contractorid='";
				condition = condition + userInfo.getDeptID();
				condition = condition + "' ";
				condition = condition + " connect by prior ";
				condition = condition + " c.contractorid=c.parentcontractorid ";
			}
			condition = condition + " ) ";
		}
		List list = dao.queryList(condition);
		List processList = new ArrayList();
		DynaBean bean;
		String userId;
		String[] existUserId = null;
		if (existValue != null) {
			existUserId = existValue.split(",");
		}
		String[] notAllowUserId = null;
		if (notAllowValue != null) {
			notAllowUserId = notAllowValue.split(",");
		}

		boolean flag = false;
		boolean flg = false;
		for (int i = 0; list != null && i < list.size(); i++) {
			flag = false;
			flg = false;
			bean = (DynaBean) list.get(i);
			userId = (String) bean.get("userid");
			if (exceptSelf != null && exceptSelf.equals("true")) {
				if (bean != null && userInfo != null) {
					if (userInfo.getUserID().equals(bean.get("userid"))) {
						continue;
					}
				}
			}
			for (int j = 0; existUserId != null && j < existUserId.length; j++) {
				if (userId != null && userId.equals(existUserId[j])) {
					flag = true;
					break;
				}
			}
			for (int j = 0; notAllowUserId != null && j < notAllowUserId.length; j++) {
				if (userId != null && userId.equals(notAllowUserId[j])) {
					flg = true;
					break;
				}
			}
			if (flg) {
				continue;
			}
			if (flag) {
				bean.set("is_checked", "1X");
			}
			processList.add(bean);
		}
		return processList;
	}

	public List loadWapApprovers(UserInfo userInfo, String existValue,
			String displayType, String departId, String exceptSelf,
			String objectId, String objectType, String approverType) {
		// TODO Auto-generated method stub
		String condition = "";
		if (displayType != null && displayType.equals("mobile")) {
			condition = condition + " and deptype='1' ";
			condition = condition + " and exists( ";
			condition = condition + " select r.regionid from region r ";
			condition = condition + " where r.regionid=u.regionid ";
			condition = condition + " start with r.regionid='";
			condition = condition + userInfo.getRegionid();
			condition = condition + "' ";
			condition = condition + " connect by prior ";
			condition = condition + " r.regionid=r.parentregionid ";
			condition = condition + " ) ";
			condition = condition + " and ugl.usergroupid='"
					+ WebAppUtils.approverGroupId + "' ";
		} else {
			condition = condition + " and deptype='2' ";
			condition = condition + " and exists( ";
			if (departId != null && !"".equals(departId)) {
				condition = condition
						+ " select c.contractorid from contractorinfo c ";
				condition = condition + " where c.contractorid=u.deptid ";
				condition = condition + " start with c.contractorid='";
				condition = condition + departId;
				condition = condition + "' ";
				condition = condition + " connect by prior ";
				condition = condition + " c.contractorid=c.parentcontractorid ";
			} else {
				condition = condition
						+ " select c.contractorid from contractorinfo c ";
				condition = condition + " where c.contractorid=u.deptid ";
				condition = condition + " start with c.contractorid='";
				condition = condition + userInfo.getDeptID();
				condition = condition + "' ";
				condition = condition + " connect by prior ";
				condition = condition + " c.contractorid=c.parentcontractorid ";
			}
			condition = condition + " ) ";
		}
		List list = dao.queryList(condition);
		List approverList = approverDao.getApprovers(objectId, objectType);
		List processList = new ArrayList();
		DynaBean bean;
		String userId;
		ReplyApprover approver;
		String[] existUserId = null;
		if (existValue != null) {
			existUserId = existValue.split(",");
		}

		boolean isApproverChecked = false;
		boolean isReaderChecked = false;
		for (int i = 0; list != null && i < list.size(); i++) {
			isApproverChecked = false;
			isReaderChecked = false;
			bean = (DynaBean) list.get(i);
			userId = (String) bean.get("userid");
			if (exceptSelf != null && exceptSelf.equals("true")) {
				if (bean != null && userInfo != null) {
					if (userInfo.getUserID().equals(bean.get("userid"))) {
						continue;
					}
				}
			}
			for (int j = 0; existUserId != null && j < existUserId.length; j++) {
				if (userId != null && userId.equals(existUserId[j])) {
					isApproverChecked = true;
					break;
				}
			}
			for (int j = 0; approverList != null && j < approverList.size(); j++) {
				approver = (ReplyApprover) approverList.get(j);
				if (approver != null && approver.getApproverId().equals(userId)) {
					if (CommonConstant.APPROVE_MAN.equals(approver
							.getApproverType())) {
						bean.set("is_checked", "1X");
					}
					if (CommonConstant.APPROVE_TRANSFER_MAN.equals(approver
							.getApproverType())) {
						bean.set("is_checked", "1X");
					}
					if (approverType == null || !approverType.equals("transfer")) {
						if (CommonConstant.APPROVE_READ.equals(approver
								.getApproverType())) {
							bean.set("is_reader_checked", "1X");
						}
					}
				}
			}
			if (isApproverChecked) {
				bean.set("is_checked", "1X");
			}
			processList.add(bean);
		}
		return processList;
	}

	/**
	 * 执行根据查询条件获取线维中心用户信息列表
	 * 
	 * @param inputType
	 * @param exceptSelf
	 */
	public String loadProcessors(String queryValue, UserInfo userInfo,
			String existValue, String inputType, String exceptSelf) {
		String[] existUserId = null;
		if (existValue != null) {
			existUserId = existValue.split(",");
		}
		String jsonText = "";

		List departList = null;
		List userList = null;
		String job = "";

		String condition = "";
		condition += " and d.departname like '%" + queryValue + "%' ";
		departList = dao.getUserDepartList(condition);
		condition = "";
		condition += generateUserCondition(queryValue);
		userList = dao.getUserList(condition);

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
			String departId = (String) oneDepart.get("departid");
			departNode = new JSONObject();
			departNode.put("id", DEPART_PREX + departId + DEPART_NAME_PREX
					+ oneDepart.get("departname"));
			departNode.put("text", (String) oneDepart.get("departname"));
			userArray = new JSONArray();
			for (int j = 0; userList != null && j < userList.size(); j++) {
				oneUser = (DynaBean) userList.get(j);
				if (exceptSelf != null && exceptSelf.equals("true")) {
					if (oneUser != null && userInfo != null) {
						if (userInfo.getUserID().equals(oneUser.get("userid"))) {
							continue;
						}
					}
				}
				if (departId != null
						&& departId.equals((String) oneUser.get("deptid"))) {
					userNode = new JSONObject();
					userNode.put("id", DEPART_PREX + departId
							+ DEPART_NAME_PREX + oneDepart.get("departname")
							+ USER_PREX + ((String) oneUser.get("userinfo"))
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

	/**
	 * 执行根据查询条件获取线维中心用户信息列表
	 * 
	 * @param inputType
	 * @param exceptSelf
	 */
	public String loadMobileAndContractorProcessors(String queryValue,
			UserInfo userInfo, String existValue, String inputType,
			String exceptSelf) {
		String[] existUserId = null;
		if (existValue != null) {
			existUserId = existValue.split(",");
		}
		String jsonText = "";

		List departList = null;
		List userList = null;
		String job = "";

		String condition = "";
		condition += " and d.departname like '%" + queryValue + "%' ";
		departList = dao.getUserDepartList(condition);
		condition = "";
		// condition += generateUserCondition(queryValue);
		userList = dao.getMobileUserAndContractorPersonList(condition);

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
			String departId = (String) oneDepart.get("departid");
			departNode = new JSONObject();
			departNode.put("id", DEPART_PREX + departId + DEPART_NAME_PREX
					+ oneDepart.get("departname"));
			departNode.put("text", (String) oneDepart.get("departname"));
			userArray = new JSONArray();
			for (int j = 0; userList != null && j < userList.size(); j++) {
				oneUser = (DynaBean) userList.get(j);
				if (exceptSelf != null && exceptSelf.equals("true")) {
					if (oneUser != null && userInfo != null) {
						if (userInfo.getUserID().equals(oneUser.get("userid"))) {
							continue;
						}
					}
				}
				if (departId != null
						&& departId.equals((String) oneUser.get("deptid"))) {
					userNode = new JSONObject();
					userNode.put("id", DEPART_PREX + departId
							+ DEPART_NAME_PREX + oneDepart.get("departname")
							+ USER_PREX + ((String) oneUser.get("userinfo"))
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

	public List getWapDepartList() {
		String condition = "";
		return dao.getUserDepartList(condition);

	}

	public List getWapUserAndContractorPersonList(String exceptSelf,
			UserInfo userInfo, String[] departs) {
		String condition = "";
		String departStr = "";
		for (int i = 0; departs != null && i < departs.length; i++) {
			departStr += "'" + departs[i] + "'";
			if (i < departs.length - 1) {
				departStr += ",";
			}
		}
		condition += " and u.deptid in (" + departStr + ") ";
		if (exceptSelf != null && exceptSelf.equals("true")) {
			condition += " and u.userid<>'" + userInfo.getUserID() + "' ";
		}
		return dao.getMobileUserAndContractorPersonList(condition);
	}

	public List getWapUserList(String exceptSelf, UserInfo userInfo,
			String[] departs) {
		String condition = "";
		String departStr = "";
		for (int i = 0; departs != null && i < departs.length; i++) {
			departStr += "'" + departs[i] + "'";
			if (i < departs.length - 1) {
				departStr += ",";
			}
		}
		condition += " and u.deptid in (" + departStr + ") ";
		if (exceptSelf != null && exceptSelf.equals("true")) {
			condition += " and u.userid<>'" + userInfo.getUserID() + "' ";
		}
		return dao.getUserList(condition);
	}

	/**
	 * 根据用户请求生成查询条件
	 * 
	 * @param queryValue
	 * @return
	 */
	public String generateUserCondition(String queryValue) {
		if (queryValue != null && !"".equals(queryValue)) {
			StringBuffer buf = new StringBuffer();
			buf.append(" and (");
			buf.append(" (d.departname like '%" + queryValue + "%') or ");
			buf.append(" (u.name like '%" + queryValue + "%') or ");
			buf.append(" (u.mobile like '%" + queryValue + "%') or ");
			buf.append(" (u.jobinfo like '%" + queryValue + "%') ");
			buf.append(") ");
			return buf.toString();
		}
		return "";
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
			buf.append(" (username like '%" + queryValue + "%') or ");
			buf.append(" (phone like '%" + queryValue + "%') or ");
			buf.append(" (position like '%" + queryValue + "%') ");
			buf.append(") ");
			return buf.toString();
		}
		return "";
	}

	/**
	 * 根据WAP选择的用户信息生成对应input_name的用户值信息Map
	 * 
	 * @param users
	 * @param inputName
	 * @return
	 */
	public Map getInputNameMap(String[] users, String inputName) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		String departId = "";
		String userId = "";
		String userName = "";
		String mobileId = "";
		String departUserId = "";
		if (inputName != null) {
			if (inputName.indexOf(",") != -1) {
				for (int i = 0; users != null && i < users.length; i++) {
					departId += users[i].substring(users[i].indexOf("D") + 1,
							users[i].indexOf("U"));
					userId += users[i].substring(users[i].indexOf("U") + 1,
							users[i].indexOf("M"));
					mobileId += users[i].substring(users[i].indexOf("M") + 1,
							users[i].indexOf("N"));
					userName += users[i].substring(users[i].indexOf("N") + 1);
					departUserId += users[i].substring(
							users[i].indexOf("D") + 1, users[i].indexOf("U"))
							+ ";";
					departUserId += users[i].substring(
							users[i].indexOf("U") + 1, users[i].indexOf("M"));
					if (i < users.length - 1) {
						departId += ",";
						userId += ",";
						mobileId += ",";
						userName += ",";
						departUserId += ",";
					}
				}
				String[] input = inputName.split(",");
				if (input.length >= 1) {
					map.put(input[0], departId);
				}
				if (input.length >= 2) {
					map.put(input[1], userId);
				}
				if (input.length >= 3) {
					map.put(input[2], mobileId);
				}
				if (input.length >= 4) {
					map.put(input[3], departUserId);
				}
				map.put("span_value", userName);
			} else {
				for (int i = 0; users != null && i < users.length; i++) {
					userId += users[i].substring(users[i].indexOf("U") + 1,
							users[i].indexOf("M"));
					userName += users[i].substring(users[i].indexOf("N") + 1);
					if (i < users.length - 1) {
						userId += ",";
						userName += ",";
					}
				}
				map.put(inputName, userId);
				map.put("span_value", userName);
			}
		}
		return map;
	}

	public void parseUserInfo(Map map, String inputName, String[] userInfo) {
		if (userInfo != null) {
			String[] userId = new String[userInfo.length];
			String[] userName = new String[userInfo.length];
			String[] userTel = new String[userInfo.length];
			String userIds = "";
			String userNames = "";
			String userTels = "";
			for (int i = 0; i < userInfo.length; i++) {
				if (userInfo[i] != null && userInfo[i].indexOf("-") != -1) {
					userId[i] = userInfo[i].split("-")[0];
					userName[i] = userInfo[i].split("-")[1];
					if (userInfo[i].split("-").length >= 3) {
						userTel[i] = userInfo[i].split("-")[2];
					} else {
						userTel[i] = "";
					}
					userIds = userIds + userId[i];
					userNames = userNames + userName[i];
					userTels = userTels + userTel[i];
					if (i != userInfo.length - 1) {
						userIds = userIds + ",";
						userNames = userNames + ",";
						userTels = userTels + ",";
					}
				}
			}
			if (inputName != null) {
				if (inputName.indexOf(",") != -1) {
					String[] inputNames = inputName.split(",");
					if (inputNames.length >= 1) {
						map.put(inputNames[0], userIds);
					}
					if (inputNames.length >= 2) {
						map.put(inputNames[1], userTels);
					}
				} else {
					map.put(inputName, userIds);
				}
				map.put("span_value", userNames);
			}
		}
	}

	/**
	 * 通过id查找用户信息
	 * 
	 * @param id
	 *            String
	 * @return UserInfo
	 * @throws Exception
	 */
	public DynaBean loadUserAndContractorPersonInfo(String id) throws Exception {
		return dao.findByUserAndContractorId(id);
	}

}
