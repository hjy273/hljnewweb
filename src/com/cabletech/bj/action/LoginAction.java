package com.cabletech.bj.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.DepartBO;
import com.cabletech.baseinfo.services.RegionBO;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.bj.services.InitDisplayBO;
import com.cabletech.bj.services.WaitHandleWorkBO;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.md5.MD5;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.menu.domainobjects.FirstMenu;
import com.cabletech.menu.domainobjects.SecondlyMenu;
import com.cabletech.menu.services.MenuService;
import com.cabletech.power.CheckPower;
import com.cabletech.sysmanage.dao.LoginDao;
import com.cabletech.sysmanage.services.SysDictionary;
import com.cabletech.sysmanage.util.OnLineUsers;

/**
 * 北京移动专用 用户登陆
 * 
 * @author zh
 * 
 */
public class LoginAction extends BaseInfoBaseDispatchAction {
	private Logger logger = Logger.getLogger(LoginAction.class);

	public ActionForward loginForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String sVersion = "";
		// sVersion = "V" + dao.getVersion(request.getRealPath(""));
		request.setAttribute("version", sVersion);
		return mapping.findForward("login_form");
	}

	/**
	 * 系统登陆
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		long start = System.currentTimeMillis();
		LoginDao logindao = new LoginDao();
		String userID = request.getParameter("userid");
		String password = request.getParameter("password");
		String loginflag = request.getParameter("loginflag");
		String businessType = request.getParameter("type");
		UserInfo userInfo = null;
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		if (userInfo != null) {
			return mapping.findForward("loginformward");
		}

		request.getSession().setAttribute("password", password);

		UserInfoBO bou = new UserInfoBO();
		String Flag = "nodisplay";

		java.util.Date date = new java.util.Date();
		// 用户不存在
		if (!logindao.validateUserExist(userID)) {
			request.setAttribute("loginerror", "nouser");
			return mapping.findForward("errorformward");
		} else { // 存在该用户
			userInfo = super.getService().loadUserInfo(userID);
			String password1 = MD5.encode(password, 0); // 密码加密
			if (userInfo.getPassword().length() == 32) {
				if (!userInfo.getPassword().equals(password1)) {
					request.setAttribute("loginerror", "passerror");
					return mapping.findForward("errorformward");
				}
			} else {
				if (!userInfo.getPassword().equals(password)) {
					request.setAttribute("loginerror", "passerror");
					return mapping.findForward("errorformward");
				}
			}
			// 检查帐号是否过期
			if (userInfo.getAccountTerm() ==null &&userInfo.getAccountTerm().before(date)) {
				request.setAttribute("loginerror", "accounttermerror");
				return mapping.findForward("errorformward");
			}
			logger.info(userInfo.getNewpsdate());

			int st = bou.viladateNewPsDate(userInfo);
			long timer = bou.getTimer();
			// logger.info( userInfo.getNewpsdate() +
			// "1 提示用户更改密码  2 密码失效 ,禁止用户登陆  0 正常登陆  当前值：" + st );
			if (st == 2) {
				request.setAttribute("loginerror", "perror");
				return mapping.findForward("errorformward");
			}
			if (st == 1) {
				Flag = "display";
			}
			// 如果账号为暂停使用，
			if (!userInfo.getAccountState().equals("1")) {
				request.setAttribute("loginerror", "accerror");
				return mapping.findForward("errorformward");
			}

			// 载入该用户的菜单
			MenuService menuService = new MenuService();
			Vector firstMenu = menuService.getFirstMenu(userInfo,businessType);
			request.getSession().setAttribute("FIRSTMENUMAP", array2Map(firstMenu));
			request.getSession().setAttribute("MENU_FIRSTMENU", firstMenu);
			// 如果没有权限 2005-05-31
			if (firstMenu == null || firstMenu.size() < 1) {
				request.setAttribute("loginerror", "powererror");
				return mapping.findForward("errorformward");
			}
			// 加载二级，三级菜单
			String strFirstMenuID = null;
			if (firstMenu.size() > 0) {
				FirstMenu menu = (FirstMenu) firstMenu.elementAt(0);
				strFirstMenuID = menu.getId();
			}
			logger.info("开始载入二级菜单");
			HashMap mapSecondlyMenu = new HashMap();
			Vector secondlyMenu = menuService.getSecondlyMenu(strFirstMenuID, userInfo);
			mapSecondlyMenu.put(strFirstMenuID, secondlyMenu);
			request.getSession().setAttribute("MENU_SECONDLYMENU", mapSecondlyMenu);

			logger.info("开始载入三级菜单");
			String strThirdMenuID = null;
			if (firstMenu.size() > 0) {
				SecondlyMenu menu = (SecondlyMenu) secondlyMenu.elementAt(0);
				strThirdMenuID = menu.getId();
			}
			HashMap mapThirdMenu = new HashMap();
			Vector thirdMenu = menuService.getThirdMenu(strThirdMenuID, userInfo);
			mapThirdMenu.put(strThirdMenuID, thirdMenu);
			request.getSession().setAttribute("MENU_THIRDMENU", mapThirdMenu);

			// 如果登陆成功,转载用户信息
			//			request.getSession().setMaxInactiveInterval(1800); // Sesion有效时长，以秒为单位
			// 30分钟
			if (userInfo.getRegionID().substring(2).equals("0000") && !userInfo.getRegionid().startsWith("11"))
				userInfo.setType(userInfo.getDeptype() + "1");
			else
				userInfo.setType(userInfo.getDeptype() + "2");
			logger.info("UserType:" + userInfo.getType());

			String deptId = userInfo.getDeptID();
			if ("1".equals(userInfo.getDeptype())) {
				String deptName = new DepartBO().loadDepart(deptId).getDeptName();
				userInfo.setDeptName(deptName);
			}
			if ("2".equals(userInfo.getDeptype()) || "3".equals(userInfo.getDeptype())) {
				String deptName = new ContractorBO().loadContractor(deptId).getContractorName();
				userInfo.setDeptName(deptName);
			}

			String regionId = userInfo.getRegionid();
			String regionName = new RegionBO().loadRegion(regionId).getRegionName();
			userInfo.setRegionName(regionName);

			HashMap map = new HashMap();
			map.put(regionId, regionName);
			request.getSession().setAttribute("regionInfo", map);
			request.getSession().setAttribute("LOGIN_USER_REGION_NAME", regionName);

			request.getSession().setAttribute("LOGIN_USER", userInfo);
			request.getSession().setAttribute("USERCURRENTPOWER", CheckPower.getMoudules(userInfo)); // ysj add
			String userType = userInfo.getDeptype();
			// zhj
			if (Flag.equals("display")) {
				String enddate = DateUtil.DateToTimeString(new Date(userInfo.getNewpsdate().getTime() + timer));
				request.getSession().setAttribute("display", Flag); // 设置提示密码过期显示
				request.getSession().setAttribute("enddate", enddate.substring(0, 10));
			}

			if (userType.equals("2") || userType.equals("3")) { // 代为单位
				Contractor contractor = super.getService().loadContractor(userInfo.getDeptID());
				request.getSession().setAttribute("LOGIN_USER_DEPT_NAME", contractor.getContractorName());
				request.getSession().setAttribute("LOGIN_USER_DEPT_ID", contractor.getContractorID());
			} else { // 内部部门
				Depart depart = super.getService().loadDepart(userInfo.getDeptID());
				request.getSession().setAttribute("LOGIN_USER_DEPT_NAME", depart.getDeptName());
			}
			RegionBO rbo = new RegionBO();

			request.getSession().setAttribute("REGION_ROOT", rbo.getRegionID());
			// log(request, " 登陆系统 ", "登陆系统 ");
			OnLineUsers online = OnLineUsers.getInstance();
			String loginIp = request.getRemoteAddr();

			if (!online.existUser(userInfo.getUserID())) {
				request.getSession().setAttribute(userInfo.getUserID(), online);
				logger.info("欢迎新用户: " + userInfo.getUserID() + " 登陆到系统！");
			}

			Date nowDate = new Date();
			String nowDateStr = DateUtil.DateToTimeString(nowDate);
			String sql = "update USERINFO set LASTLOGINTIME = to_date('" + nowDateStr
					+ "','yy/mm/dd hh24:mi'), LASTLOGINIP = '" + loginIp + "'  where USERID = '" + userInfo.getUserID()
					+ "'";
			UpdateUtil updateU = new UpdateUtil();
			updateU.executeUpdate(sql);

			String onlineInfoSql = "select t.*,to_char(t.logintime,'yyyy-mm-dd hh24:mi:ss') as login_time from useronlinetime t where userid='"
					+ userInfo.getUserID() + "' order by keyid desc ";
			QueryUtil query = new QueryUtil();
			List onlineInfoList = query.queryBeans(onlineInfoSql);
			if (onlineInfoList != null && !onlineInfoList.isEmpty()) {
				request.getSession().setAttribute("CURRENT_USER_LOGIN_TIMES", (onlineInfoList.size() + 1) + "");
				DynaBean bean = (DynaBean) onlineInfoList.get(0);
				if (bean != null) {
					request.getSession().setAttribute("CURRENT_USER_LAST_LOGIN_TIME", bean.get("login_time"));
				}
			} else {
				request.getSession().setAttribute("CURRENT_USER_LOGIN_TIMES", "1");
				request.getSession().setAttribute("CURRENT_USER_LAST_LOGIN_TIME", "");
			}
			// 判断是否是第一次登录，登录时间写入数据库
			if (loginflag == null) {
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strDt = dtFormat.format(nowDate);

				OracleIDImpl ora = new OracleIDImpl();
				String uid = ora.getSeq("useronlinetime", 16);
				logger.info("获得主键id" + uid);
				request.getSession().setAttribute("userkeyid", uid);
				String sql2 = "insert into useronlinetime ul (ul.KEYID, ul.USERID, ul.LOGINIP, ul.LOGINTIME) values ( "
						+ "'" + uid + "', " + "'" + userInfo.getUserID() + "','" + loginIp + "'," + "to_date('" + strDt
						+ "','YYYY-MM-DD HH24:MI:SS'))";
				updateU.executeUpdate(sql2);
			}

			InitDisplayBO initbo = new InitDisplayBO();
			// 取得会议议程字符串序列
			String meetString = initbo.getMeetInfo();
			request.getSession().setAttribute("MEET_INFO_STR", meetString);
			List userMailList = initbo.queryMailLatestList(userInfo);
			request.getSession().setAttribute("MAIL_LATEST_LIST", userMailList);

			// 取得公告列表
			//			String noticestr = initbo.getNoticeInfoForNew(rbo.getRegionID(),
			//					userInfo.getRegionID(), "");
			//			// 巡检人员列表
			//			
			//			request.getSession().setAttribute("noticeli", noticestr);
			// 判断巡检人员是按组还是按人进行管理
			SysDictionary service = new SysDictionary();
			if (service.isManageByArry().equals("1")) {
				request.getSession().setAttribute("PMType", "group");
			} else {
				request.getSession().setAttribute("PMType", "notGroup");
			}

			// 判断是否显示光缆信息
			if (service.isShowFIB().equals("1")) {
				request.getSession().setAttribute("ShowFIB", "show");
			} else {
				request.getSession().setAttribute("ShowFIB", "noShow");
			}

			// 判断是否发送短信
			if (service.isSendSM().equals("1")) {
				request.getSession().setAttribute("isSendSm", "send");
			} else {
				request.getSession().setAttribute("isSendSm", "nosend");
			}
			long end = System.currentTimeMillis();
			long total = (end - start) / 1000;
			log.info("运行时间：" + total / 60 + "分" + total % 60 + "秒");
			return mapping.findForward("loginformward");

		}
	}
	public ActionForward mainweb(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		RegionBO rbo = new RegionBO();
		request.getSession().setAttribute("REGION_ROOT", rbo.getRegionID());
		return mapping.findForward("login_form");
	}

	public ActionForward reloginNoSession(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String password = (String) request.getSession().getAttribute("password");
		String loginflag = "1";
		// System.out.println( "UserInfo.id:" + userinfo.getUserID()+ "::" +
		// password );
		response.sendRedirect("/WebApp/login.do?method=login&userid=" + userinfo.getUserID() + "&password=" + password
				+ "&loginflag=" + loginflag);
		return null;
	}

	public ActionForward relogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		// 系统登出、重新登陆。卸载当前SESSION中的所有对象，并且返回到登陆界
		LoginDao dao = new LoginDao();
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("userkeyid");
		dao.logout(uid);
		if (session != null) {
			Enumeration senum = session.getAttributeNames();
			while (senum.hasMoreElements()) {
				// 遍历SESSION中的对象
				String obj = senum.nextElement().toString();
				logger.info("Session Object Removed:  " + obj);
				super.removeSessionAttribute(request, obj); // 卸载对象
			}
			session.invalidate();
		}
		return mapping.findForward("relogin");
	}

	/**
	 * 退出登陆
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		LoginDao dao = new LoginDao();
		HttpSession session = request.getSession();
		if (dao.logout((String) request.getSession().getAttribute("userkeyid"))) {
			if (session != null) {
				Enumeration senum = session.getAttributeNames();
				while (senum.hasMoreElements()) {
					// 遍历SESSION中的对象
					String obj = senum.nextElement().toString();
					logger.info("Session Object Removed:  " + obj);
					super.removeSessionAttribute(request, obj); // 卸载对象
				}
				session.invalidate();
			}
			log.info("===========用户退出系统处理完毕！=============");
		} else {
			log.info("===========用户退出系统处理失败！=============");
		}
		return null;
	}

	public ActionForward showWaitProcessWorkNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		Vector firstMenu = (Vector) request.getSession().getAttribute("MENU_FIRSTMENU");
		WebApplicationContext ctx = getWebApplicationContext();
		WaitHandleWorkBO waitHandleWorkBo = (WaitHandleWorkBO) ctx.getBean("waitHandleWorkBO");
		//验证用户是否登陆
		waitHandleWorkBo.processMenuVector(firstMenu, userInfo);
		request.setAttribute("MENU_FIRSTMENU", firstMenu);
		request.setAttribute("isdowork", request.getParameter("isdowork"));
		return mapping.findForward("show_wait_process_work_num");
	}

	private Map array2Map(Vector v) {
		FirstMenu menu = null;
		Map firstMenuMap = new HashMap();
		for (int i = 0; i < v.size(); i++) {
			menu = (FirstMenu) v.get(i);
			firstMenuMap.put(menu.getId(), menu);
		}
		return firstMenuMap;
	}
}
