package com.cabletech.linepatrol.cut.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.cut.module.QueryCondition;
import com.cabletech.linepatrol.cut.services.CutQueryStatManager;

/**
 * 根据查询条件查询、统计
 * 
 * @author liusq
 * 
 */
public class CutQueryStatAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(CutQueryStatAction.class.getName());

	/**
	 * 获得CutQueryStatManager对象
	 */
	private CutQueryStatManager getCutQueryStatService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (CutQueryStatManager) ctx.getBean("cutQueryStatManager");
	}

	/**
	 * 获得查询时的加载条件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cutQueryForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptType = userInfo.getDeptype();
		String operator = request.getParameter("operator");
		String deptId = userInfo.getDeptID();
		if ("1".equals(deptType)) {
			List list = getCutQueryStatService().getAllContractor();
			request.getSession().setAttribute("list", list);
		} else {
			BasicDynaBean bean = getCutQueryStatService().getContractorById(deptId);
			request.setAttribute("bean", bean);
		}
		request.getSession().setAttribute("operator", operator);
		request.getSession().setAttribute("depttype", deptType);
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("result");
		request.getSession().removeAttribute("cableLevels");
		return mapping.findForward("cutQueryForm");
	}

	/**
	 * 根据条件查询割接申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryCutInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		QueryCondition queryCondition = this.getQueryCondition(request);
		String operator = request.getParameter("operator");
		if (null == request.getParameter("isQuery")) {
			queryCondition = (QueryCondition) request.getSession().getAttribute("queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", queryCondition);
		}
		if (queryCondition == null) {
			queryCondition = new QueryCondition();
		}
		request.setAttribute("task_names", queryCondition.getTaskNames());
		request.getSession().setAttribute("cableLevels", setToString(queryCondition.getCableLevels()));
		super.setPageReset(request);
		if ("query".equals(operator)) {
			try {
				List list = getCutQueryStatService().queryCutInfo(queryCondition, userInfo);
				request.getSession().setAttribute("result", list);
				return mapping.findForward("queryCutInfo");
			} catch (ServiceException e) {
				return super.forwardErrorPage(mapping, request, "queryCutInfoError");
			}
		} else {
			List list = getCutQueryStatService().cutStatForm(queryCondition);
			request.setAttribute("operator", "stat");
			request.getSession().setAttribute("result", list);
			return mapping.findForward("cutStatForm");
		}
	}

	/**
	 * 通过快捷菜单查看割接信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryCutInfoByMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String regionId = userInfo.getRegionID();
		String type = request.getParameter("type");
		String state = "";
		if (type.equals("apply")) {
			state = "'1','2','3'";
		} else if (type.equals("feedback")) {
			state = "'4','5','6'";
		} else {
			state = "'7','8','9'";
		}
		try {
			List list = getCutQueryStatService().queryCutInfoByMenu(state, regionId);
			request.getSession().setAttribute("list", list);
			return mapping.findForward("queryCutInfo");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("查询割接出错，出错信息为：" + e.getMessage());
			return super.forwardErrorPage(mapping, request, "queryCutInfoError");
		}
	}

	/**
	 * 封装查询条件
	 * 
	 * @param request
	 * @return
	 */
	public QueryCondition getQueryCondition(HttpServletRequest request) {
		String contractorId = request.getParameter("contractorId");
		String[] cutLevels = request.getParameterValues("cutLevels");
		String[] cableLevels = request.getParameterValues("cabletype");
		String[] states = request.getParameterValues("states");
		String[] taskStates = request.getParameterValues("taskStates");
		String isInterrupt = request.getParameter("isInterrupt");
		String isTimeOut = request.getParameter("isTimeOut");
		String timeType = request.getParameter("timeType");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String cancelState = request.getParameter("cancelState");
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setContractorId(contractorId);
		queryCondition.setCutLevels(cutLevels);
		queryCondition.setCableLevels(cableLevels);
		queryCondition.setStates(states);
		queryCondition.setIsInterrupt(isInterrupt);
		queryCondition.setIsTimeOut(isTimeOut);
		queryCondition.setTimeType(timeType);
		queryCondition.setBeginTime(beginTime);
		queryCondition.setEndTime(endTime);
		queryCondition.setTaskStates(taskStates);
		queryCondition.setCancelState(cancelState);
		return queryCondition;
	}

	/**
	 * 导出查询割接列表信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportCutList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = (List) request.getSession().getAttribute("result");
		getCutQueryStatService().exportCutList(list, response);
		return null;
	}

	/**
	 * 导出割接统计信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportCutStat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = (List) request.getSession().getAttribute("result");
		getCutQueryStatService().doExportCutStat(list, response);
		return null;
	}

	/**
	 * 查看割接信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewQueryCut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String cutId = request.getParameter("cutId");
		String type = request.getParameter("type");
		Map map = getCutQueryStatService().viewQueryCut(cutId);
		request.setAttribute("map", map);
		request.setAttribute("type", type);
		return mapping.findForward("viewQueryCut");
	}
	
	/**
	 * 将字符串数组转化为字符串
	 * @param strs	字符串数组
	 * @return
	 */
	public String setToString(String[] strs) {
		StringBuffer sb = new StringBuffer();
		if (strs != null && strs.length > 0) {
			sb.append(strs[0]);
			for (int i = 1; i < strs.length; i++) {
				sb.append("," + strs[i]);
			}
		}
		return sb.toString();
	}
}
