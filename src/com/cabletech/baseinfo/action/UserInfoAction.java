package com.cabletech.baseinfo.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.beans.UserInfoBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.domainobjects.UsergroupUserList;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.md5.MD5;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.power.CheckPower;
import com.cabletech.sysmanage.dao.ExportDao;

public class UserInfoAction extends BaseInfoBaseDispatchAction {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private static Logger logger = Logger.getLogger(UserInfoAction.class.getName());

	/**
	 * 添加用户
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward addUserinfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		if (!CheckPower.checkPower(request.getSession(), "70502")) {
			return mapping.findForward("powererror");
		}

		UserInfoBean bean = (UserInfoBean) form;
		int flag = checkUserId(bean.getUserID()); // 验证用户是否被占用
		String msg = "";

		if (flag == 1) {

			UserInfo data = new UserInfo();
			BeanUtil.objectCopy(bean, data);
			Date nowDate = new Date();
			data.setNewpsdate(nowDate);
			data.setPassword(MD5.encode(bean.getPassword(), 0)); // 密码加密
			data.setOldps(bean.getPassword());
			data.setAccidenttime("24");
			data.setOnlinemantime("2");
			data.setWatchtime("72");
			// if ("3".equals(data.getDeptype())) {
			// data.setDeptype("2");
			// data.setIsSuperviseUnit("1");
			// }
			super.getService().createUserInfo(data);

			// begin 添加进用户组
			UsergroupUserList UgUList = new UsergroupUserList();

			UgUList.setId(super.getDbService().getSeq("USERGOURPUSERLIST", 20));
			UgUList.setUserid(data.getUserID());
			UgUList.setUsergroupid(bean.getUsergroupid());

			super.getService().addUgUList(UgUList);
			// end 添加进用户组

			// Log
			log(request, " 增加用户信息 （用户名称为：" + bean.getUserName() + "）", " 系统管理 ");

			return forwardInfoPage(mapping, request, "0091");

		} else {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
			String ugp = "select u.id,u.groupname,u.regionid,u.type " + " from usergroupmaster u,region b"
					+ " WHERE u.regionid = b.regionid  "
					+ " and ( u.regionid IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid "
					+ "START WITH RegionID='" + userInfo.getRegionID() + "')   )  ";
			ugp += " order by regionid,u.type";
			List ugpList = super.getDbService().queryBeans(ugp);
			String dept = "select d.deptid,d.deptname,d.regionid " + "from deptinfo d "
					+ "where (d.state is null or d.state<>'1')";
			String con = "select c.contractorid,c.contractorname,c.regionid " + "from contractorinfo c "
					+ "where (c.state is null or c.state<>'1') and c.depttype='2'";
			String supercon = "select c.contractorid,c.contractorname ,c.regionid " + "from contractorinfo c "
					+ "where (c.state is null or c.state<>'1') and c.depttype='3'";
			String type = userInfo.getType();
			if (type.equals("12")) {
				dept += " and regionid='" + userInfo.getRegionID() + "'";
				con += " and regionid='" + userInfo.getRegionID() + "'";
			}
			dept += " order by regionid";
			con += " order by regionid";
			List deptList = super.getDbService().queryBeans(dept);
			List conList = super.getDbService().queryBeans(con);
			List superconList = super.getDbService().queryBeans(supercon);
			request.setAttribute("ugplist", ugpList);
			request.setAttribute("deptlist", deptList);
			request.setAttribute("conlist", conList);
			request.setAttribute("superconList", superconList);

			msg = "该用户名已经被占用，请更换后重试！";
			request.setAttribute("innerMsg", msg);
			return mapping.findForward("addUserInfo");
		}

	}

	/**
	 * 显示单个用户信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward loadUserinfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		if (!CheckPower.checkPower(request.getSession(), "70504")) {
			return mapping.findForward("powererror");
		}

		List regionList = null;
		List ugpList = null;
		List deptList = null;
		List conList = null;
		List superconList = null;
		String region = "select r.regionid,r.regionname "
				+ " from region r "
				+ " where (r.state is null or r.state<>'1') and substr(REGIONID,3,4) != '1111' "
				+ " and regionid in (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
				+ userInfo.getRegionid() + "')  order by r.regionid";
		String ugp = "select u.id,u.groupname,u.regionid,u.type " + " from usergroupmaster u,region b"
				+ " WHERE u.regionid = b.regionid  "
				+ " and ( u.regionid IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid "
				+ "START WITH RegionID='" + userInfo.getRegionID() + "')   )  ";
		String dept = "select d.deptid,d.deptname,d.regionid " + "from deptinfo d "
				+ "where (d.state is null or d.state<>'1')";

		String con = "select c.contractorid,c.contractorname,c.regionid " + "from contractorinfo c "
				+ "where (c.state is null or c.state<>'1') and c.depttype='2'";
		String supercon = "select c.contractorid,c.contractorname ,c.regionid " + "from contractorinfo c "
				+ "where (c.state is null or c.state<>'1') and c.depttype='3'";
		String type = userInfo.getType();
		if (type.equals("11")) {
			// ugp+=" where u.type='1' or (u.type='2' and u.regionid like
			// '%0000')";
			// ugp+=" where u.type='1'";
			// con+=" and regionid not like '%0000'";
		}
		if (type.equals("12")) {
			dept += " and regionid='" + userInfo.getRegionID() + "'";
			con += " and regionid='" + userInfo.getRegionID() + "'";
		}
		ugp += " order by regionid,u.type";
		dept += " order by regionid";
		con += " order by regionid";
		regionList = super.getDbService().queryBeans(region);
		ugpList = super.getDbService().queryBeans(ugp);
		deptList = super.getDbService().queryBeans(dept);
		conList = super.getDbService().queryBeans(con);
		superconList = super.getDbService().queryBeans(supercon);
		// if(type.equals("11")){
		// con1+="and regionid like '%0000' order by regionid";
		// List l=super.getDbService().queryBeans(con1);
		// for(int i=0;i<l.size();i++)
		// deptList.add(l.get(i));
		// }

		request.setAttribute("regionlist", regionList);
		request.setAttribute("ugplist", ugpList);
		request.setAttribute("deptlist", deptList);
		request.setAttribute("conlist", conList);
		request.setAttribute("superconList", superconList);

		UserInfo data = super.getService().loadUserInfo(request.getParameter("id"));
		UserInfoBean bean = new UserInfoBean();
		if (data.getNewpsdate() == null || data.getNewpsdate().equals("")) {
			data.setNewpsdate(new Date());
		}
		BeanUtil.objectCopy(data, bean);
		Date accountDate = data.getAccountTerm();
		if (accountDate != null) {
			String acctime = sdf.format(accountDate);
			bean.setAccountTerm(acctime);
		}

		// 用户组
		String usergroupid = "";

		String sql = "select usergroupid from usergourpuserlist where userid = '" + bean.getUserID() + "'";
		QueryUtil queryutil = new QueryUtil();
		String[][] resultArr = queryutil.executeQueryGetArray(sql, " ");

		if (resultArr != null) {
			usergroupid = resultArr[0][0];
		}

		bean.setUsergroupid(usergroupid);
		// 用户组

		request.setAttribute("userInfoBean", bean);
		// request.getSession().setAttribute("userinfoBean", bean);
		// //System.out.println("测试");
		return mapping.findForward("updateUserinfo");
	}

	public ActionForward queryUserinfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String userid = "";

		UserInfoBean bean = (UserInfoBean) form;
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");

		String sql = "select a.userID userid,a.userName username,decode(a.password,null,'平台用户','本地用户') usertype, "
				+ "nvl(decode(a.deptype, '1', c.deptname, '2', d.contractorname,'3', d.contractorname, c.deptname),'') deptid , "
				+ "nvl(b.regionname,'') regionID,a.workID,a.password,to_char(a.accountTerm,'yyyy/mm/dd'), to_char(a.passwordTerm,'yyyy/mm/dd'),a.accountState,a.phone,a.email,a.position, "
				+ "decode(a.deptype, '1', '内部部门','2','代维单位',3,'监理单位') deptype,a.deptype userstype,b.regionid userregion "
				+ "from UserInfo a, region b, deptinfo c, contractorinfo d";
		if (bean.getUserID() == null) {
			userid = "";
		} else {
			userid = bean.getUserID();
		}

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);

		sqlBuild.addConstant("a.regionid = b.regionid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("a.deptid = d.contractorid(+)");
		sqlBuild.addAnd();
		sqlBuild.addConstant("a.deptid = c.deptid(+)");
		sqlBuild.addConditionAnd("a.userid like {0}", "%" + userid + "%");
		sqlBuild.addConditionAnd("a.username like {0}", "%" + bean.getUserName() + "%");
		sqlBuild.addConditionAnd("a.regionid = {0}", bean.getRegionID());
		sqlBuild.addConditionAnd("a.deptype = {0}", bean.getDeptype());
		sqlBuild.addConditionAnd("a.deptid = {0}", bean.getDeptID());
		sqlBuild.addConditionAnd("a.workid = {0}", bean.getWorkID());
		sqlBuild.addConditionAnd("a.accountState = {0}", bean.getAccountState());
		// ysj add
		sqlBuild.addConstant(" and a.state is null");
		sqlBuild.addTableRegion("a.regionid", super.getLoginUserInfo(request).getRegionid());
		if (userInfo.getType().equals("22")) {
			sqlBuild.addAnd();
			sqlBuild.addConstant("a.deptid='" + userInfo.getDeptID() + "'");
		}
		if (userInfo.getType().equals("21")) {
			sqlBuild.addConstant(" and a.deptid in( SELECT contractorid " + "FROM contractorinfo "
					+ "CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userInfo.getDeptID() + "')");
		}
		sql = sqlBuild.toSql();
		// sql += "ORDER BY
		// TO_NUMBER(a.positionno),a.REGIONID,a.DEPTYPE,a.POSITION,a.USERNAME";
		sql += "ORDER BY c.deptname,d.contractorname,a.userName";
		System.out.println("查询用户 : " + sql);

		List list = super.getDbService().queryBeans(sql);
		// request.setAttribute("queryuserinfo", list);
		request.getSession().setAttribute("queryresult", list);
		super.setPageReset(request);
		return mapping.findForward("queryuserinforesult");
	}

	public ActionForward queryUserinfoForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String userid = "";
		UserInfo userInfo = ((UserInfo) request.getSession().getAttribute("LOGIN_USER"));
		List regionList = null;
		List deptList = null;
		List conList = null;
		String region = "select r.regionid,r.regionname "
				+ " from region r "
				+ " where (r.state is null or r.state<>'1') "
				+ " and regionid in (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
				+ userInfo.getRegionid() + "')  order by r.regionid";
		String dept = "select d.deptid,d.deptname,d.regionid " + "from deptinfo d "
				+ "where (d.state is null or d.state<>'1')";
		String con = "select c.contractorid deptid,c.contractorname deptname,c.regionid " + "from contractorinfo c "
				+ "where (c.state is null or c.state<>'1')";
		String con1 = "select c.contractorid deptid,c.contractorname deptname,c.regionid " + "from contractorinfo c "
				+ "where (c.state is null or c.state<>'1')";

		
		String type = userInfo.getType();
		if (type.equals("12")) {
			region += " and regionid='" + userInfo.getRegionID() + "'";
			dept += " and regionid='" + userInfo.getRegionID() + "'";
			con += " and regionid='" + userInfo.getRegionID() + "'";
		}
		if (type.equals("21")) {
			con += " and contractorid in (SELECT contractorid " + "FROM contractorinfo "
					+ "CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userInfo.getDeptID() + "')";
			/*
			 * region+=" and regionid like '%1111' or (regionid in(" +" select
			 * regionid " +" from contractorinfo" +" where (state is null or
			 * state<>'1')" +" and contractorid in(" +" select contractorid" +"
			 * from contractorinfo" +" where (state is null or state<>'1')" +"
			 * and parentcontractorid='"+userInfo.getDeptID()+"' or regionid
			 * like '%0000'" +" )" +" )" +" )";
			 */
			// region+=" and regionid='"+userInfo.getRegionID()+"'";
			// con+=" and regionid='"+userInfo.getRegionID()+"' and contractorid
			// in (SELECT contractorid FROM contractorinfo CONNECT BY PRIOR
			// contractorid=PARENTCONTRACTORID START WITH
			// contractorid='"+userInfo.getDeptID()+"')";
		}
		if (type.equals("22")) {
			con += " and contractorid='" + userInfo.getDeptID() + "'";
			region += " and regionid='" + userInfo.getRegionID() + "'";
		}
		if (type.equals("11")) {
			con += " and regionid not like '%0000'";
		}
		dept += " order by regionid";
		con += " order by regionid";
		regionList = super.getDbService().queryBeans(region);
		deptList = super.getDbService().queryBeans(dept);
		conList = super.getDbService().queryBeans(con);

		if (type.equals("11")) {
			con1 += "and regionid like '%0000' order by regionid";
			List l = super.getDbService().queryBeans(con1);
			for (int i = 0; i < l.size(); i++)
				deptList.add(l.get(i));
		}
		request.setAttribute("regionlist", regionList);
		request.setAttribute("deptlist", deptList);
		request.setAttribute("conlist", conList);

		return mapping.findForward("queryuserinfoform");
	}

	public ActionForward addUserInfoForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String userid = "";

		List regionList = null;
		List ugpList = null;
		List deptList = null;
		List conList = null;
		List superconList = null;
		String region = "select r.regionid,r.regionname "
			+ " from region r "
			+ " where (r.state is null or r.state<>'1') "
			+ " and regionid in (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"
			+ userInfo.getRegionid() + "')  order by r.regionid";
		/*String ugp = "select u.id,u.groupname,u.regionid,u.type "
			+ "from usergroupmaster u";*/
		String ugp = "select u.id,u.groupname,u.regionid,u.type " + " from usergroupmaster u,region b"
				+ " WHERE u.regionid = b.regionid  "
				+ " and ( u.regionid IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid "
				+ "START WITH RegionID='" + userInfo.getRegionID() + "')   )  ";
		String dept = "select d.deptid,d.deptname,d.regionid " + "from deptinfo d "
				+ "where (d.state is null or d.state<>'1')";
		String con = "select c.contractorid,c.contractorname,c.regionid " + "from contractorinfo c "
				+ "where (c.state is null or c.state<>'1') and c.depttype='2'";
		String supercon = "select c.contractorid,c.contractorname ,c.regionid " + "from contractorinfo c "
				+ "where (c.state is null or c.state<>'1') and c.depttype='3'";

		String type = userInfo.getType();
		if (type.equals("11")) {
			// ugp+=" where u.type='1' or (u.type='2' and u.regionid like
			// '%0000')";
			// ugp+=" where u.type='1'";
			// con+=" and regionid not like '%0000'";
		}
		if (type.equals("12")) {
			dept += " and regionid='" + userInfo.getRegionID() + "'";
			con += " and regionid='" + userInfo.getRegionID() + "'";
		}
		ugp += " order by regionid,u.type";
		dept += " order by regionid";
		con += " order by regionid";
		regionList = super.getDbService().queryBeans(region);
		ugpList = super.getDbService().queryBeans(ugp);
		deptList = super.getDbService().queryBeans(dept);
		conList = super.getDbService().queryBeans(con);
		superconList = super.getDbService().queryBeans(supercon);

		// if(type.equals("11")){
		// con1+="and regionid like '%0000' order by regionid";
		// List l=super.getDbService().queryBeans(con1);
		// for(int i=0;i<l.size();i++)
		// deptList.add(l.get(i));
		// }

		request.setAttribute("regionlist", regionList);
		request.setAttribute("ugplist", ugpList);
		request.setAttribute("deptlist", deptList);
		request.setAttribute("conlist", conList);
		request.setAttribute("superconList", superconList);

		return mapping.findForward("addUserInfo");
	}

	public ActionForward updateUserinfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		UserInfoBean bean = (UserInfoBean) form;
		UserInfo data = super.getService().loadUserInfo(bean.getUserID());
		data.setUserName(bean.getUserName());
		data.setRegionID(bean.getRegionid());
		data.setDeptID(bean.getDeptID());
		data.setWorkID(bean.getWorkID());
		data.setAccountTerm(DateUtil.StringToDate(bean.getAccountTerm()));
		data.setAccountState(bean.getAccountState());
		data.setPhone(bean.getPhone());
		data.setEmail(bean.getEmail());
		data.setPosition(bean.getPosition());
		data.setDeptype(bean.getDeptype());
		//		if ("3".equals(bean.getDeptype())) {
		//			data.setDeptype("2");
		//			data.setIsSuperviseUnit("1");
		//		}
		super.getService().updateUserInfo(data);
		// begin 添加进用户组
		/*
		 * 1.删除以前的用户用户组对应信息 2.生成新的用户用户组对应表id 3.添加用户用户组对应信息
		 */
		String sql = "delete from usergourpuserlist where userid='" + data.getUserID() + "'";
		UpdateUtil uputil = new UpdateUtil();
		uputil.executeUpdate(sql);

		UsergroupUserList UgUList = new UsergroupUserList();

		UgUList.setId(super.getDbService().getSeq("USERGOURPUSERLIST", 20));
		UgUList.setUserid(data.getUserID());
		UgUList.setUsergroupid(bean.getUsergroupid());

		super.getService().addUgUList(UgUList);
		// end 添加进用户组

		// Log
		log(request, " 更新用户信息（用户名称为：" + bean.getUserName() + "） ", " 系统管理 ");
		String strQueryString = getTotalQueryString("method=queryUserinfo&userName=", (UserInfoBean) request
				.getSession().getAttribute("theQueryBean"));
		Object[] args = getURLArgs("/WebApp//userinfoAction.do", strQueryString, (String) request.getSession()
				.getAttribute("S_BACK_URL"));
		return forwardInfoPage(mapping, request, "0092", null, args);
	}

	/**
	 * 删除用户信息
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward deleteUserinfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		if (!CheckPower.checkPower(request.getSession(), "70505")) {
			return mapping.findForward("powererror");
		}
		// 并非真正删除用户,只是置一个标记
		String id = (String) request.getParameter("id");
		String sql = "update userinfo set state='1' where userid='" + id + "'";
		UpdateUtil exec = new UpdateUtil();
		try {
			exec.executeUpdate(sql);
			log(request, " 删除用户信息 ", " 系统管理 ");
			String strQueryString = getTotalQueryString("method=queryUserinfo&userName=", (UserInfoBean) request
					.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp//userinfoAction.do", strQueryString, (String) request.getSession()
					.getAttribute("S_BACK_URL"));
			return forwardInfoPage(mapping, request, "0093", null, args);
		} catch (Exception e) {
			// System.out.println( "删除用户异常:" + e.getMessage() );
			return forwardErrorPage(mapping, request, "error");
		}
	}

	public String getTotalQueryString(String queryString, UserInfoBean bean) {
		if (bean.getUserName() != null) {
			queryString = queryString + bean.getUserName();
		}
		if (bean.getId() != null) {
			queryString = queryString + "&id=" + bean.getId();
		}
		if (bean.getRegionID() != null) {
			queryString = queryString + "&regionID=" + bean.getRegionID();
		}
		if (bean.getDeptID() != null) {
			queryString = queryString + "&deptID=" + bean.getDeptID();
		}
		if (bean.getRegionid() != null) {
			queryString = queryString + "&regionid=" + bean.getRegionid();
		}
		if (bean.getDeptype() != null) {
			queryString = queryString + "&deptype=" + bean.getDeptype();
		}
		return queryString;
	}

	/**
	 * 检查用户名是否被占用
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws ClientException
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward ifUserIDValid(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {

		String msg = "该用户名可用，请继续完成注册！";

		int flag = checkUserId(request.getParameter("userID"));
		if (flag == 0) {
			msg = "该用户名已经被占用，请更换后重试！";
		}

		request.setAttribute("innerMsg", msg);
		return mapping.findForward("addUserInnerMsg");
	}

	/**
	 * 检查用户名是否被占用
	 * 
	 * @param userid
	 *            String
	 * @return int
	 */
	public int checkUserId(String userid) throws Exception {
		int flag = 1; // 1,Ok 2,err

		String sql = "select * from userinfo ";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		sqlBuild.addConditionAnd("userid = {0}", userid);
		// sqlBuild.addConstant(" and (state is null or state<>'1')");

		sql = sqlBuild.toSql();
		// //System.out.println(sql);

		Vector vct = new Vector();

		try {
			vct = super.getDbService().queryVector(sql, "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (vct.size() > 0) {
			flag = 0;
		}

		return flag;
	}

	public ActionForward validatePsw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		List list = null;
		// UserInfo userInfo = ( UserInfo )request.getSession().getAttribute(
		// "LOGIN_USER" );
		String uid = ((UserInfo) request.getSession().getAttribute("LOGIN_USER")).getUserID();
		UserInfo userInfo = new UserInfoBO().loadUserInfo(uid);
		String p2 = request.getParameter("p2");
		// String p1 = request.getParameter( "p1" );
		if (userInfo.getPassword().length() == 32) {
			if (userInfo.getPassword().equals(MD5.encode(p2, 0))) {
				logger.info("p2 " + p2 + "userinfo" + userInfo.getPassword());
				list = super.getService().getAllUserList(userInfo);
				request.setAttribute("userlist", list);
				return mapping.findForward("updatepsw");
			} else {
				return forwardInfoPage(mapping, request, "0094");
			}
		} else {
			if (userInfo.getPassword().equals(p2)) {
				list = super.getService().getAllUserList(userInfo);
				request.setAttribute("userlist", list);
				return mapping.findForward("updatepsw");
			} else {
				return forwardInfoPage(mapping, request, "0094");
			}
		}

	}

	public ActionForward updatePsw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String userid = request.getParameter("userid");
		String p1 = request.getParameter("p1");
		String p2 = request.getParameter("p2");
		UserInfo userinfo = super.getService().loadUserInfo(userid);

		boolean b = false;
		if (p1.equals(p2)) {
			// userinfo.setPassword( MD5.encode( p1, 0 ) );
			userinfo.setPassword(p1);
			UserInfoBO uibo = new UserInfoBO();
			if (uibo.validatePsRepeat(userinfo)) {
				userinfo.setOldps(uibo.getNewOldps(userinfo));
				userinfo.setPassword(MD5.encode(p1, 0));
				userinfo.setNewpsdate(new Date());
				if (super.getService().updatePsw(userinfo)) {
					log(request, "修改密码", "系统管理");
					return forwardInfoPage(mapping, request, "0096"); // 密码修改成功
				} else {
					return forwardInfoPage(mapping, request, "0097"); // 密码修改失败
				}
			} else {
				return forwardInfoPage(mapping, request, "0098"); // 密码已经使用过
			}

		} else {
			return forwardInfoPage(mapping, request, "0095"); // 密码不相同
		}
	}

	public ActionForward exportUserinfoResult(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.info(" 创建dao");
			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("得到list");
			super.getService().exportUserinfoResult(list, response);
			logger.info("输出excel成功");
			return null;
		} catch (Exception e) {
			logger.error("导出信息报表出现异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	public ActionForward setuptime(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfoBean bean = (UserInfoBean) form;
		bean.setUserID(((UserInfo) request.getSession().getAttribute("LOGIN_USER")).getUserID());
		try {
			if (super.getService().setuptime(bean)) {
				return forwardInfoPage(mapping, request, "setuptime");
			} else {
				return forwardErrorPage(mapping, request, "setuptimeerror");
			}
		} catch (Exception e) {
			logger.warn("设置显示时间异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "setuptimeerror");

		}
	}

	public ActionForward exportUserOnlineTime(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {

			List list = (List) request.getSession().getAttribute("useronlineinfo");
			logger.info("成功获得数据");
			ExportDao dao = new ExportDao();
			dao.exportUserOnlineTimeResult(list, response);
			logger.info("输出excel成功");
			return null;
		} catch (Exception e) {
			logger.error("导出信息结果表出现异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

}
