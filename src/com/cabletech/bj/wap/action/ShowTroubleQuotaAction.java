package com.cabletech.bj.wap.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.analysis.action.MainGuideBarChartAction;
import com.cabletech.analysis.action.NormGuideBarChartAction;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;
import com.cabletech.linepatrol.trouble.services.TroubleQuotaBO;

public class ShowTroubleQuotaAction extends BaseInfoBaseDispatchAction {
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("userInfo", userInfo);
		if ("wap".equals(env) && userInfo != null) {
			return mapping.findForward("quotaIndex");
		} else {
			return mapping.findForward("relogin");
		}
	}

	public ActionForward queryQuota(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		WebApplicationContext ctx = getWebApplicationContext();
		TroubleQuotaBO bo = (TroubleQuotaBO) ctx.getBean("troubleQuotaBO");
		TroubleNormGuide mainGuide = bo.getQuotaByType("1");
		TroubleNormGuide normGuide = bo.getQuotaByType("0");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		String endTime = DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
//		Map mainGuideMap = bo.getTimeAreaTroubleQuotaList("", "1", year + "/01", endTime);
//		Map normGuideMap = bo.getTimeAreaTroubleQuotaList("", "0", year + "/01", endTime);
//
//		MainGuideBarChartAction mainGuideBarChart = new MainGuideBarChartAction();
//		mainGuideBarChart.setMainGuide(mainGuide);
//		mainGuideBarChart.setMainGuideMap(mainGuideMap);
//		String mainGuideFilename = mainGuideBarChart.generateChart(request.getSession(), new PrintWriter(response
//				.getOutputStream()), 280, 250);
//		String mainGuideGraphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + mainGuideFilename;
//		request.setAttribute("mainGuideGraphURL", mainGuideGraphURL);
//
//		NormGuideBarChartAction normGuideBarChart = new NormGuideBarChartAction();
//		normGuideBarChart.setNormGuide(normGuide);
//		normGuideBarChart.setNormGuideMap(normGuideMap);
//		String normGuideFilename = normGuideBarChart.generateChart(request.getSession(), new PrintWriter(response
//				.getOutputStream()), 280, 250);
//		String normGuideGraphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + normGuideFilename;
//		request.setAttribute("normGuideGraphURL", normGuideGraphURL);
		return mapping.findForward("listQuota");
	}
}
