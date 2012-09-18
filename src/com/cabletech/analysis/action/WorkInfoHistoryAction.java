package com.cabletech.analysis.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryDateInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.beans.HistoryWorkInfoCreateBOParam;
import com.cabletech.analysis.services.WorkInfoHistoryContext;
import com.cabletech.analysis.services.WorkInfoHistoryContextCurve;
import com.cabletech.analysis.services.WorkInfoHistoryContextSM;
import com.cabletech.analysis.util.WorkInfoHistoryCommon;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.config.UserType;
import com.cabletech.power.CheckPower;

/**
 * 历史巡检过程分析的Action类
 */
public class WorkInfoHistoryAction extends BaseDispatchAction {
	private Logger logger =Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;
	private WorkInfoHistoryCommon workInfoCommon = new WorkInfoHistoryCommon();
	private HistoryWorkInfoCreateBOParam boParam = null;
    private WorkInfoHistoryContext context  = null;
	/**
	 * 巡检过程历史信息查询页面
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
	public ActionForward queryWorkInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// 判断是否有查询巡检过程历史信息的权限
		if (!CheckPower.checkPower(request.getSession(), "121001")) {
			return mapping.findForward("powererror");
		}
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		try {
			// 得到查询页面所需载入的查询范围列表
			List rangeList = workInfoCommon.getRangeList(userInfo);
			request.getSession().setAttribute("rangeinfo", rangeList);
			return mapping.findForward("queryWorkInfoHistory");
		} catch (Exception e) {
			logger.error("查询范围信息时出错:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 得到用户输入的起止日期内在线人数分布图
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            为界面上用户输入的条件封装后的formbean
	 * @param request
	 *            请求
	 * @param response
	 *            回复
	 * @return ActionForward
	 */
	public ActionForward getOnlineNumberForDays(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) form;
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,null); //封装参数
		context = new WorkInfoHistoryContextCurve(); //建立模板方法模式的抽象模板
        //得到用户输入的起止日期内在线人数分布图返回结果，Map型
		Map mapOnlineNumberForDays = context.createBO(boParam).getOnlineNumberForDays(); 
		request.getSession().setAttribute("MapOnlineNumberForDays",
				mapOnlineNumberForDays);
		// 以下四行分别用于“历史曲线”、“巡检轨迹”、”短信汇总“及”短信图例“
		request.getSession().setAttribute("HistoryWorkInfoConBean", bean);
		request.getSession().setAttribute("TrackRangeID", bean.getRangeID());
		request.getSession().setAttribute("SMRangeID", bean.getRangeID());
		request.getSession()
				.setAttribute("SMGraphicRangeID", bean.getRangeID());
		// 初始化givenDate，当用户选择了具体某一天时，会重新给givenDate赋值。
		request.getSession().setAttribute("givenDate", "0");
		// 由于曲线图最终清除掉givenDate(为了防止从时段显示返回到日显示依然保留givenDate从而导致出错）,
		// flagGivenDate留作后用（比如查看某一日的短信）
		request.getSession().setAttribute("flagGivenDate", "0");
		return mapping.findForward("showWorkInfoHistory");
	}

	/**
	 * 当用户在界面上选择select框后,得到用户输入的起止日期内在线人数分布图
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
	public ActionForward getOnlineNumForDaysByChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("givenDate", "0");
		request.getSession().setAttribute("flagGivenDate", "0");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// 得到在界面上选择的rangeID
		String selectedRangeID = request.getParameter("selectedRangeID");
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
		return mapping.findForward("commonHistoryCurve");
	}

	/**
	 * 得到用户选定的具体某一天中的各个时段在线人数分布图
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
	public ActionForward getOnlineNumberForHours(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// 获得用户在“起止日期内在线人数分布图”上所选择的具体日期hitDate.
		String givenDate = request.getParameter("hitDate");
		// 由于曲线图最终清除掉givenDate(为了防止从时段显示返回到日显示依然保留givenDate从而导致出错,
		// flagGivenDate留作后用（比如查看某一日的短信）
		request.getSession().setAttribute("flagGivenDate", givenDate);
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		Map mapOnlineNumberForHours = null;
		context = new WorkInfoHistoryContextCurve(); //建立模板方法模式的抽象模板
        //封装参数
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,givenDate,null,null);
		if (UserType.CONTRACTOR.equals(userInfo.getType())
				&& (!"22".equals(bean.getRangeID()))) { //如果市代维用户选了具体巡检组，得到01图
			mapOnlineNumberForHours = context.createBO(boParam).getFinal01GraphicMap();
		}else{
            //得到用户选择的具体某一天中各时段在线人数分布图返回结果，Map型
			mapOnlineNumberForHours = context.createBO(boParam).getOnlineNumberForHours();
		}
		request.getSession().setAttribute("MapOnlineNumberForHours",
				mapOnlineNumberForHours);
		// 以时段为单位显示曲线图
		request.getSession().setAttribute("givenDate", givenDate);
		return mapping.findForward("showWorkInfoGivenDay");
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
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
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

	/**
	 * 得到用户在线信息（即当用鼠标放在热区点上时，弹出的当前时段对应的信息框）
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
	public ActionForward getOnlineInfoTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 得到鼠标放所在热区点对应的时段
		String givenDateAndTime = request.getParameter("strDateAndTime");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// 得到所需信息列表
		context = new WorkInfoHistoryContextCurve(); //建立模板方法模式的抽象模板
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,null,"0",givenDateAndTime,null);
		HistoryTimeInfoBean backBean  = context.createBO(boParam).getOnlineInfoGivenHour();
		if (backBean == null){
			return null;
		}
		// 组织返回信息
		String backString = backBean.getBackString(userInfo);
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(backString);
		out.flush();
		return null;
	}

	/**
	 * 得到短信信息（短信汇总和短信图例都使用之）
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
	public ActionForward getSMInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("flagGivenDate", "0");
		// 得到“是否选择了’短信图例‘Tab项"
		String flagGraphic = request.getParameter("flagGraphic");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		String smRangeID = "";
		context = new WorkInfoHistoryContextSM(); //建立模板方法模式的抽象模板
		if ("0".equals(flagGraphic)) { // 如果选择的是“短信汇总”tab项
			// 得到显示各种类型短信数量的列表（汇总）
			smRangeID = (String) request.getSession().getAttribute("SMRangeID");
			boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,smRangeID);
			List smHistoryList  = context.createBO(boParam).getSMInfoAllType();
			request.getSession().setAttribute("smhistoryinfo", smHistoryList);
			return mapping.findForward("showHistorySMText");
		}
		// 如果用户选择的是“短信图例”tab项
		smRangeID = (String) request.getSession().getAttribute(
				"SMGraphicRangeID");
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,smRangeID);
		// 得到显示各种类型短信数量的列表(图例）
		List smHistoryList  = context.createBO(boParam).getSMInfoAllType();
		request.getSession()
				.setAttribute("smgraphichistoryinfo", smHistoryList);
		return mapping.findForward("showHistorySMGraphic");
	}

	/**
	 * 当用户在界面上选择select框后，通过Ajax调用并得到短信信息（短信汇总和短信图例都使用之）
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
	public ActionForward getSMInfoByChangeRange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 得到“是否选择了’短信图例‘Tab项"
		String flagGraphic = request.getParameter("flagGraphic");
		// 得到用户在界面上选择的具体RangeID
		String smRangeID = (String) request.getParameter("rangeID");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		// 得到显示各种类型短信数量的列表
		context = new WorkInfoHistoryContextSM(); //建立模板方法模式的抽象模板
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,bean,"0",null,smRangeID);
		List smHistoryList  = context.createBO(boParam).getSMInfoAllType();
		if ("0".equals(flagGraphic)) { // 如果选择的是“短信汇总”tab项
			request.getSession().setAttribute("SMRangeID", smRangeID);
			request.getSession().setAttribute("smhistoryinfo", smHistoryList);
			return mapping.findForward("showHistorySMInfoText");
		}
		// 如果用户选择的是“短信图例”tab项
		request.getSession().setAttribute("SMGraphicRangeID", smRangeID);
		request.getSession()
				.setAttribute("smgraphichistoryinfo", smHistoryList);
		return mapping.findForward("commonHistorySMGraphic");
	}

	/**
	 * 得到具体所选某一天的短信（短信汇总和短信图例都使用之）
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
	public ActionForward getSMInfoGivenDay(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 得到用户是否选择了“短信图例”或者“短信汇总”的标志
		String flagGraphic = request.getParameter("flagGraphic");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HistoryWorkInfoConditionBean bean = (HistoryWorkInfoConditionBean) request
				.getSession().getAttribute("HistoryWorkInfoConBean");
		// 具体某一天的短信的区域范围只能跟着起止日期内的曲线图区域范围而变化。
		String smRangeID = bean.getRangeID();
		// 由于曲线图最终清除掉givenDate(为了防止从时段显示返回到日显示依然保留givenDate从而导致出错),
		// flagGivenDate留作后用（比如查看某一日的短信）
		String givenDate = (String) request.getSession().getAttribute(
				"flagGivenDate");
		context = new WorkInfoHistoryContextSM(); //建立模板方法模式的抽象模板
		boParam = new HistoryWorkInfoCreateBOParam(userInfo,null,givenDate,null,smRangeID);
		// 得到具体所选某一天的短信列表，此时givenDate有值，而第一个参数HistoryWorkInfoConditionBean则为空
		List smHistoryListGivenDay  = context.createBO(boParam).getSMInfoAllType();
		if ("0".equals(flagGraphic)) { // 如果选择的是“短信汇总”tab项
			request.getSession().setAttribute("SMRangeID", smRangeID);
			request.getSession().setAttribute("smhistoryinfogivenday",
					smHistoryListGivenDay);
			return mapping.findForward("showHistorySMTextGivenDay");
		}
		// 如果用户选择的是“短信图例”tab项
		request.getSession().setAttribute("smgraphichistoryinfogivenday",
				smHistoryListGivenDay);
		return mapping.findForward("commonHistorySMGraphic");
	}
}
