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
	 * ��ʾ���Ų���ҳ��
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

		// ��ʾ��ѯҳ���־
		String flg = request.getParameter("flg");
		if ("0".equals(flg)) {
			// ����ִ�еĶ���������־
			request.setAttribute("flg", "0");
		} else {
			// �ѽ����Ķ���������־
			request.setAttribute("flg", "1");
		}
		//forwardStr = "querymsgpage";
		// ȡ�õ�¼��Ա����Ϣ
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		WatchMsgDao msgDao = new WatchMsgDao();
		List conList = msgDao.getdeptInfo(userinfo);
		request.getSession().setAttribute("coninfolist", conList);

		return mapping.findForward("querymsgpage");
	}

	/**
	 * ���ݴ�ά����Ѳ��ά������Ϣ
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
		// ȡ�ò���Id
		String conId = request.getParameter("conid");
		// ȡ�õ�¼��Ա����Ϣ
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
	 * ����Ѳ��ά������Ҷ�����Ϣ
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
		// ȡ��Ѳ��ά����
		String patrolId = request.getParameter("patrolid");
		// ȡ������ҳ��ı�־
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
	 * ִ�ж���������־�Ĳ�ѯ
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
		// ȡ������bean
		WatchMsgBean bean = (WatchMsgBean)form;
		// ҳ���־
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
			logger.error("��ʾ����������־����:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
		
	}
	
	 /**
	  * ����ת��
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
