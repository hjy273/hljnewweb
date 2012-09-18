package com.cabletech.exterior.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.analysis.beans.HistoryDateInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.beans.HistoryWorkInfoCreateBOParam;
import com.cabletech.analysis.services.WorkInfoHistoryContext;
import com.cabletech.analysis.services.WorkInfoHistoryContextCurve;
import com.cabletech.analysis.util.WorkInfoHistoryCommon;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.config.UserType;

public class HistoryOnlineNumAction extends BaseInfoBaseDispatchAction {
	private HistoryWorkInfoCreateBOParam boParam = null;
    private WorkInfoHistoryContext context  = null;
	private WorkInfoHistoryCommon workInfoCommon = new WorkInfoHistoryCommon();
	private UserInfoBO ubo = new UserInfoBO();
	private Logger logger =Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;
	
	public ActionForward getOnlineNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		String year = request.getParameter("theyear");
		String month = request.getParameter("themonth");
        //获取 默认月份 = 当前月份-1
		if(year == null && month == null){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH,-1);
			year = String.valueOf(cal.get(Calendar.YEAR));
			month = String.valueOf(cal.get(Calendar.MONTH)+1);
		}
		HistoryWorkInfoConditionBean bean = new HistoryWorkInfoConditionBean();
		bean.setBeanAccordtoCondition("11", year, month);
//		logger.info(bean.getEndDate() + "," + bean.getStartDate() + "," + bean.getRangeID());
		
		//String userID = request.getParameter("uid");
		String userID = "aa";
		UserInfo userInfo = ubo.loadUserInfo(userID);
		userInfo.setType(UserType.PROVINCE);
		try {
			// 得到查询页面所需载入的查询范围列表
			List rangeList = workInfoCommon.getRangeList(userInfo);
			request.getSession().setAttribute("rangeinfo", rangeList);
		} catch (Exception e) {
			logger.error("查询范围信息时出错:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
		boParam = new HistoryWorkInfoCreateBOParam(userInfo, bean, "0", null,
				null); // 封装参数
		if (boParam == null){
			logger.info("boParam is null");
		}
		context = new WorkInfoHistoryContextCurve(); // 建立模板方法模式的抽象模板
		// 得到用户输入的起止日期内在线人数分布图返回结果，Map型
		Map mapOnlineNumberForDays = context.createBO(boParam)
				.getOnlineNumberForDays();
		request.getSession().setAttribute("MapOnlineNumberForDays",
				mapOnlineNumberForDays);
		request.getSession().setAttribute("HistoryWorkInfoConBean", bean);
		session.setAttribute("LOGIN_USER", userInfo);
		request.setAttribute("theyear", year);
		request.setAttribute("themonth", month);
		session.setAttribute("thewidth", new Integer(750));
		session.setAttribute("theheight",new Integer(350));
//		 初始化givenDate，当用户选择了具体某一天时，会重新给givenDate赋值。
		session.setAttribute("givenDate", "0");
		return mapping.findForward("showHistoryCurve");
	}
	
	public ActionForward getOnlineNumForDaysByChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("givenDate", "0");
		request.getSession().setAttribute("flagGivenDate", "0");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// 得到在界面上选择的rangeID
		String selectedRangeID = request.getParameter("selectedRangeID");
		if (selectedRangeID == null || "".equals(selectedRangeID)){
			selectedRangeID = "11";
		}
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		// 让HistoryWorkInfoConditionBean中始终保持最新的rangeID
		bean.setRangeID(selectedRangeID);
		// 把最新的HistoryWorkInfoConditionBean放入session,以备后用
		request.getSession().setAttribute("HistoryWorkInfoConBean", bean);
		context = new WorkInfoHistoryContextCurve(); //建立模板方法模式的抽象模板
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,null); //封装参数
        //得到用户输入的起止日期内在线人数分布图返回结果，Map型
		Map mapOnlineNumberForDays = context.createBO(boParam).getOnlineNumberForDays();
		request.getSession().setAttribute("MapOnlineNumberForDays",
				mapOnlineNumberForDays);
		return mapping.findForward("showHistoryCurveChart");
	}
	
	/**
	 * 得到用户在线信息（即当用鼠标放在热区点上时，弹出的当前日期对应的信息框）
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            请求
	 * @param response
	 *            回复
	 * @return ActionForward
	 */
	public ActionForward getOnlineInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String givenDate = request.getParameter("strDate"); // 得到鼠标放所在热区点对应的日期
		logger.info("givendate:" + givenDate);
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		// 得到所需信息列表
		context = new WorkInfoHistoryContextCurve(); //建立模板方法模式的抽象模板
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,givenDate,null,null);
		HistoryDateInfoBean backBean  = context.createBO(boParam).getOnlineInfoGivenDay();
		if ( backBean == null){
			return null;
		}
		// 组织返回信息
		String backString = backBean.getBackString(givenDate);
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(backString);
		out.flush();
		return null;
	}
}
