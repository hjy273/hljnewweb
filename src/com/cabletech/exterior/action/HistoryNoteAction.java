package com.cabletech.exterior.action;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.beans.HistoryWorkInfoCreateBOParam;
import com.cabletech.analysis.services.WorkInfoHistoryContext;
import com.cabletech.analysis.services.WorkInfoHistoryContextSM;
import com.cabletech.analysis.util.WorkInfoHistoryCommon;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.config.UserType;

public class HistoryNoteAction extends BaseInfoBaseDispatchAction {
	private HistoryWorkInfoCreateBOParam boParam = null;

	private WorkInfoHistoryCommon workInfoCommon = new WorkInfoHistoryCommon();

	private WorkInfoHistoryContext context = null;

	private UserInfoBO ubo = new UserInfoBO();

	private Logger logger = Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;

	public ActionForward getNoteNumInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		String year = request.getParameter("theyear");
		String month = request.getParameter("themonth");
		// 获取 默认月份 = 当前月份-1
		if (year == null && month == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			year = String.valueOf(cal.get(Calendar.YEAR));
			month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		}
		HistoryWorkInfoConditionBean bean = new HistoryWorkInfoConditionBean();
		bean.setBeanAccordtoCondition("11", year, month);
//		logger.info(bean.getEndDate() + "," + bean.getStartDate() + ","
//				+ bean.getRangeID());
		// String userID = request.getParameter("uid");
		String userID = "aa";
		UserInfo userInfo = ubo.loadUserInfo(userID);
		userInfo.setType(UserType.PROVINCE);
		context = new WorkInfoHistoryContextSM(); // 建立模板方法模式的抽象模板
		try {
			// 得到查询页面所需载入的查询范围列表
			List rangeList = workInfoCommon.getRangeList(userInfo);
			request.getSession().setAttribute("rangeinfo", rangeList);
		} catch (Exception e) {
			logger.error("查询范围信息时出错:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
		boParam = new HistoryWorkInfoCreateBOParam(userInfo, bean, "0", null,
				"11");
		// 得到显示各种类型短信数量的列表(图例）
		List smHistoryList = context.createBO(boParam).getSMInfoAllType();
		session.setAttribute("smgraphichistoryinfo", smHistoryList);
		session.setAttribute("HistoryWorkInfoConBean", bean);
		session.setAttribute("SMGraphicRangeID", bean.getRangeID());
		// 初始化givenDate，当用户选择了具体某一天时，会重新给givenDate赋值。
		session.setAttribute("givenDate", "0");
		// 由于曲线图最终清除掉givenDate(为了防止从时段显示返回到日显示依然保留givenDate从而导致出错）,
		// flagGivenDate留作后用（比如查看某一日的短信）
		session.setAttribute("flagGivenDate", "0");
		session.setAttribute("LOGIN_USER", userInfo);
		request.setAttribute("theyear", year);
		request.setAttribute("themonth", month);
		session.setAttribute("thewidth", new Integer(750));
		session.setAttribute("theheight", new Integer(350));
		return mapping.findForward("showHistoryNote");
	}

	public ActionForward getSMInfoByChangeRange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// 得到用户在界面上选择的具体RangeID
		String smRangeID = (String) request.getParameter("rangeID");
		if (smRangeID == null || "".equals(smRangeID)){
			smRangeID = "11";
		}
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) session
				.getAttribute("HistoryWorkInfoConBean");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// 得到显示各种类型短信数量的列表
		context = new WorkInfoHistoryContextSM(); // 建立模板方法模式的抽象模板
		boParam = new HistoryWorkInfoCreateBOParam(userInfo, bean, "0", null,
				smRangeID);
		List smHistoryList = context.createBO(boParam).getSMInfoAllType();
		// 如果用户选择的是“短信图例”tab项
		request.getSession().setAttribute("SMGraphicRangeID", smRangeID);
		request.getSession()
				.setAttribute("smgraphichistoryinfo", smHistoryList);
//		response.setContentType("text/html; charset=GBK");
//		PrintWriter out = response.getWriter();
//		out.flush();
		return mapping.findForward("showHistoryNoteChart");
	}
}
