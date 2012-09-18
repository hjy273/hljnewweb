package com.cabletech.sso.frame;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.DepartBO;
import com.cabletech.baseinfo.services.RegionBO;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.bj.services.InitDisplayBO;
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

public class IndexAuth extends BaseInfoBaseDispatchAction {
	private Logger logger = Logger.getLogger(IndexAuth.class);
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
			return mapping.findForward("index");
		}

		request.getSession().setAttribute("password", password);

		UserInfoBO bou = new UserInfoBO();
		String Flag = "nodisplay";

		java.util.Date date = new java.util.Date();
		// �û�������
		if (!logindao.validateUserExist(userID)) {
			request.setAttribute("loginerror", "nouser");
			return mapping.findForward("errorformward");
		} else { // ���ڸ��û�
			userInfo = super.getService().loadUserInfo(userID);
			//String password1 = MD5.encode(password, 0); // �������
			if (userInfo.getPassword().length() == 32) {
				if (!userInfo.getPassword().equals(password)) {
					request.setAttribute("loginerror", "passerror");
					return mapping.findForward("errorformward");
				}
			} else {
				if (!userInfo.getPassword().equals(password)) {
					request.setAttribute("loginerror", "passerror");
					return mapping.findForward("errorformward");
				}
			}
			// ����ʺ��Ƿ����
			if (userInfo.getAccountTerm().before(date)) {
				request.setAttribute("loginerror", "accounttermerror");
				return mapping.findForward("errorformward");
			}
			logger.info(userInfo.getNewpsdate());

			int st = bou.viladateNewPsDate(userInfo);
			long timer = bou.getTimer();
			// logger.info( userInfo.getNewpsdate() +
			// "1 ��ʾ�û���������  2 ����ʧЧ ,��ֹ�û���½  0 ������½  ��ǰֵ��" + st );
			if (st == 2) {
				request.setAttribute("loginerror", "perror");
				return mapping.findForward("errorformward");
			}
			if (st == 1) {
				Flag = "display";
			}
			// ����˺�Ϊ��ͣʹ�ã�
			if (!userInfo.getAccountState().equals("1")) {
				request.setAttribute("loginerror", "accerror");
				return mapping.findForward("errorformward");
			}

			// ������û��Ĳ˵�
			MenuService menuService = new MenuService();
			Vector firstMenu = menuService.getFirstMenu(userInfo,businessType);
			request.getSession().setAttribute("FIRSTMENUMAP", array2Map(firstMenu));
			request.getSession().setAttribute("MENU_FIRSTMENU", firstMenu);
			// ���û��Ȩ�� 2005-05-31
			if (firstMenu == null || firstMenu.size() < 1) {
				request.setAttribute("loginerror", "powererror");
				return mapping.findForward("errorformward");
			}
			// ���ض����������˵�
			String strFirstMenuID = null;
			if (firstMenu.size() > 0) {
				FirstMenu menu = (FirstMenu) firstMenu.elementAt(0);
				strFirstMenuID = menu.getId();
			}
			logger.info("��ʼ��������˵�");
			HashMap mapSecondlyMenu = new HashMap();
			Vector secondlyMenu = menuService.getSecondlyMenu(strFirstMenuID, userInfo);
			mapSecondlyMenu.put(strFirstMenuID, secondlyMenu);
			request.getSession().setAttribute("MENU_SECONDLYMENU", mapSecondlyMenu);

			logger.info("��ʼ���������˵�");
			String strThirdMenuID = null;
			if (firstMenu.size() > 0) {
				SecondlyMenu menu = (SecondlyMenu) secondlyMenu.elementAt(0);
				strThirdMenuID = menu.getId();
			}
			HashMap mapThirdMenu = new HashMap();
			Vector thirdMenu = menuService.getThirdMenu(strThirdMenuID, userInfo);
			mapThirdMenu.put(strThirdMenuID, thirdMenu);
			request.getSession().setAttribute("MENU_THIRDMENU", mapThirdMenu);

			// �����½�ɹ�,ת���û���Ϣ
			//			request.getSession().setMaxInactiveInterval(1800); // Sesion��Чʱ��������Ϊ��λ
			// 30����
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
				request.getSession().setAttribute("display", Flag); // ������ʾ���������ʾ
				request.getSession().setAttribute("enddate", enddate.substring(0, 10));
			}

			if (userType.equals("2") || userType.equals("3")) { // ��Ϊ��λ
				Contractor contractor = super.getService().loadContractor(userInfo.getDeptID());
				request.getSession().setAttribute("LOGIN_USER_DEPT_NAME", contractor.getContractorName());
				request.getSession().setAttribute("LOGIN_USER_DEPT_ID", contractor.getContractorID());
			} else { // �ڲ�����
				Depart depart = super.getService().loadDepart(userInfo.getDeptID());
				request.getSession().setAttribute("LOGIN_USER_DEPT_NAME", depart.getDeptName());
			}
			RegionBO rbo = new RegionBO();

			request.getSession().setAttribute("REGION_ROOT", rbo.getRegionID());
			// log(request, " ��½ϵͳ ", "��½ϵͳ ");
			OnLineUsers online = OnLineUsers.getInstance();
			String loginIp = request.getRemoteAddr();

			if (!online.existUser(userInfo.getUserID())) {
				request.getSession().setAttribute(userInfo.getUserID(), online);
				logger.info("��ӭ���û�: " + userInfo.getUserID() + " ��½��ϵͳ��");
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
			// �ж��Ƿ��ǵ�һ�ε�¼����¼ʱ��д�����ݿ�
			if (loginflag == null) {
				SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strDt = dtFormat.format(nowDate);

				OracleIDImpl ora = new OracleIDImpl();
				String uid = ora.getSeq("useronlinetime", 16);
				logger.info("�������id" + uid);
				request.getSession().setAttribute("userkeyid", uid);
				String sql2 = "insert into useronlinetime ul (ul.KEYID, ul.USERID, ul.LOGINIP, ul.LOGINTIME) values ( "
						+ "'" + uid + "', " + "'" + userInfo.getUserID() + "','" + loginIp + "'," + "to_date('" + strDt
						+ "','YYYY-MM-DD HH24:MI:SS'))";
				updateU.executeUpdate(sql2);
			}

			InitDisplayBO initbo = new InitDisplayBO();
			// ȡ�û�������ַ�������
			String meetString = initbo.getMeetInfo();
			request.getSession().setAttribute("MEET_INFO_STR", meetString);
			List userMailList = initbo.queryMailLatestList(userInfo);
			request.getSession().setAttribute("MAIL_LATEST_LIST", userMailList);

			// �ж�Ѳ����Ա�ǰ��黹�ǰ��˽��й���
			SysDictionary service = new SysDictionary();
			if (service.isManageByArry().equals("1")) {
				request.getSession().setAttribute("PMType", "group");
			} else {
				request.getSession().setAttribute("PMType", "notGroup");
			}

			// �ж��Ƿ���ʾ������Ϣ
			if (service.isShowFIB().equals("1")) {
				request.getSession().setAttribute("ShowFIB", "show");
			} else {
				request.getSession().setAttribute("ShowFIB", "noShow");
			}

			// �ж��Ƿ��Ͷ���
			if (service.isSendSM().equals("1")) {
				request.getSession().setAttribute("isSendSm", "send");
			} else {
				request.getSession().setAttribute("isSendSm", "nosend");
			}
			long end = System.currentTimeMillis();
			long total = (end - start) / 1000;
			log.info("����ʱ�䣺" + total / 60 + "��" + total % 60 + "��");
			return null;

		}
	}
	public ActionForward patrolIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		
		return mapping.findForward("patrolIndex");
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
