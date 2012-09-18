package com.cabletech.analysis.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.analysis.services.StatForWholeBO;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.commons.exception.SubtrahendException;

/**
 * 全省巡检计划月统计
 */
public class PlanMonthlyStatForWholeAction extends BaseInfoBaseDispatchAction {
	private Logger logger = Logger.getLogger("PlanMonthlyStatForWholeAction");
	private StatForWholeBO statBo = new StatForWholeBO();
	/**
	 * 查询全省区域的计划总体执行情况，从表单取得查询年月份 
	 * 1、获取各地市移动公司的考核结果 
	 * 2、获得全省合格比例图表信息。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getWhole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String year = request.getParameter("endYear");
		String month = request.getParameter("endMonth");
		session.setAttribute("year", year);
		session.setAttribute("month", month);
		List whole = statBo.getCollectivityInfo(year, month);
		Map wholepie = statBo.getChartData(year, month);
		if(whole != null && whole.size() != 0){
			session.setAttribute("whole", whole);
			session.setAttribute("wholepie", wholepie);
			logger.info("whole");
			return mapping.findForward("provinceStatframe");
		}else{
			logger.info("s120201fs120201fs120201f");
			return forwardInfoPage( mapping, request, "s120201f" );
		}
	}

	/**
	 * 获取各地市的巡检计划执行结果。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getExecuteResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String year = (String)session.getAttribute("year");
		String month = (String)session.getAttribute("month");
		List exeList = statBo.getExecuteForAllArea(year, month);
		session.setAttribute("exeList", exeList);
		return mapping.findForward("executeplan");
	}

	/**
	 * 获得按月及按地区的对比分析情况数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws SubtrahendException 
	 */
	public ActionForward getContrastDataForWhole(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws SubtrahendException {
		HttpSession session = request.getSession();
		String year = (String)session.getAttribute("year");
		String month = (String)session.getAttribute("month");
		List monthList = statBo.getMonthlyContrast(year, month);
		List areaList = statBo.getAreaContrast(year, month);
		session.setAttribute("monthList", monthList);
		session.setAttribute("areaList", areaList);
		return mapping.findForward("contraststat");
	}

	/**
	 * 获得巡检线段巡检情况分配图数据,包括合格的,未合格的,未计划的
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward getSublinePatrolForChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String year = (String)session.getAttribute("year");
		String month = (String)session.getAttribute("month");
		Map map = statBo.getSublinePatrolForChart(year, month);
		session.setAttribute("sublinemap", map);
		return mapping.findForward("sublinestat");
	}
}
