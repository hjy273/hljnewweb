package com.cabletech.watchinfo.action;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.watchinfo.beans.WatchMsgBean;
import com.cabletech.watchinfo.dao.WatchMsgDao;

public class WatchMsgAction extends WatchinfoBaseDispatchAction {
	private static Logger logger = Logger
			.getLogger(WatchAction.class.getName());

	public WatchMsgAction() {

	}

	/**
	 * 显示短信查找页面
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward showMsgQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {

		// 显示查询页面标志
		String flg = request.getParameter("flg");
		if ("0".equals(flg)) {
			// 正在执行的盯防短信日志
			request.setAttribute("flg", "0");
		} else {
			// 已结束的盯防短信日志
			request.setAttribute("flg", "1");
		}
		//forwardStr = "querymsgpage";
		// 取得登录人员的信息
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		WatchMsgDao msgDao = new WatchMsgDao();
		List conList = msgDao.getdeptInfo(userinfo);
		request.getSession().setAttribute("coninfolist", conList);

		return mapping.findForward("querymsgpage");
	}

	/**
	 * 根据代维查找巡检维护组信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPatrolInfoByConId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/json; charset=GBK");
		// 取得部门Id
		String conId = request.getParameter("conid");
		// 取得登录人员的信息
//		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
//				"LOGIN_USER");

		WatchMsgDao msgDao = new WatchMsgDao();
		List patrolList = msgDao.getPatrolInfo(conId);

		JSONArray ja = JSONArray.fromObject(patrolList);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}
	
	
	/**
	 * 根据巡检维护组查找盯防信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getWatchInfoByPatrolId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/json; charset=GBK");
		// 取得巡检维护组
		String patrolId = request.getParameter("patrolid");
		// 取得所有页面的标志
		String flg = request.getParameter("flg");

		WatchMsgDao msgDao = new WatchMsgDao();
		List watchList = msgDao.getWacthInfo(flg, patrolId);

		JSONArray ja = JSONArray.fromObject(watchList);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}
	
	/**
	 * 执行盯防短信日志的查询
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward doMsgQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		// 取得数据bean
		WatchMsgBean bean = (WatchMsgBean)form;
		// 页面标志
//		String pageType = bean.getPagetype();
		String forwardStr = "showquerymsgresultpage";
//		if("0".equals(pageType)) {
//			forwardStr = "";
//		} else {
//			forwardStr = "";
//		}

		try {
			WatchMsgDao msgDao = new WatchMsgDao();
			List msgList = msgDao.getWacthMsgInfo(bean);
			
			if(msgList != null ) {
				DynaBean linedata;
				String[] gpsStr;
				for (int i = 0; i < msgList.size(); i++) {
					linedata = (DynaBean)msgList.get(i);
					gpsStr = getGps(String.valueOf(linedata.get("xx")));
					linedata.set("xx", gpsStr[0]);
					linedata.set("yy", gpsStr[1]);
				}
			}			
			request.getSession().setAttribute("bean", bean);
			request.getSession().setAttribute("msginfo", msgList);
			return mapping.findForward(forwardStr);
		} catch(Exception e) {
			logger.error("显示盯防短信日志出错:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
		
	}
	
	 /**
	  * 坐标转换
	  * @param strGPSCoordinate
	  * @return String
	  */
	private String[] getGps(String strGPSCoordinate){
		if (strGPSCoordinate == null || "null".equals(strGPSCoordinate) 
				|| "".equals(strGPSCoordinate.trim())) {
			return null;
		}
		String strLatDu = strGPSCoordinate.substring(0, 2);
		String strLatFen = strGPSCoordinate.substring(2, 8);
		String strLongDu = strGPSCoordinate.substring(8, 11);
		String strLongFen = strGPSCoordinate.substring(11, 17);

		double dbLatDu = java.lang.Double.parseDouble(strLatDu);
		double dbLatFen = java.lang.Double.parseDouble(strLatFen);
		double dbLongDu = java.lang.Double.parseDouble(strLongDu);
		double dbLongFen = java.lang.Double.parseDouble(strLongFen);

		dbLatFen = dbLatFen / 600000;
		dbLongFen = dbLongFen / 600000;

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(8);
		nf.setMaximumIntegerDigits(3);

		dbLatDu = dbLatDu + dbLatFen;
		dbLongDu = dbLongDu + dbLongFen;
		nf.format(dbLatDu);
		nf.format(dbLongDu);
		String dtLd = String.valueOf(dbLongDu);
		if (dtLd.length() > 12)
			dtLd = dtLd.substring(0, 11);
		String dtLf = String.valueOf(dbLatDu);
		if (dtLf.length() > 12)
			dtLf = dtLf.substring(0, 11);
		
		String gps[] = new String[2];
		gps[0] = dtLd;
		gps[1] = dtLf;
		return gps;
		//return dtLd + "," + dtLf;
	}
}
