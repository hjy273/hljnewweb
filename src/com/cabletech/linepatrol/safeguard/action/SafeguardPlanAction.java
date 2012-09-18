package com.cabletech.linepatrol.safeguard.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.safeguard.beans.SafeguardPlanBean;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.services.SafeguardPlanBo;
import com.cabletech.linepatrol.safeguard.services.SafeguardTaskBo;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;

public class SafeguardPlanAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(SafeguardPlanAction.class.getName());

	private SafeguardPlanBo getSafeguardPlanBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (SafeguardPlanBo) ctx.getBean("safeguardPlanBo");
	}

	private void SetDataToRequest(HttpServletRequest request, Map map) {
		SafeguardTask safeguardTask = null;//保障任务
		SafeguardPlan safeguardPlan = null;//保障方案
		String sublineIds = null;//中继段
		List safeguardSps = null;//保障方案与特巡计划管理
		List specialPlans = null;//特巡计划列表

		Object safeguardTaskObj = map.get("safeguardTask");
		Object safeguardPlanObj = map.get("safeguardPlan");
		Object sublineIdsObj = map.get("sublineIds");
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		String[] approveInfo = (String[]) map.get("approveInfo");
		String approveMan = (String) map.get("approveMan");

		if (safeguardTaskObj != null) {
			safeguardTask = (SafeguardTask) safeguardTaskObj;
		}
		if (safeguardPlanObj != null) {
			safeguardPlan = (SafeguardPlan) safeguardPlanObj;
		}
		if (sublineIdsObj != null) {
			sublineIds = (String) sublineIdsObj;
		}
		if (safeguardSpObj != null) {
			safeguardSps = (List) safeguardSpObj;
		}
		if (specialPlanObj != null) {
			specialPlans = (List) specialPlanObj;
		}
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("approveInfo", approveInfo);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("approveMan", approveMan);
	}

	/**
	 * 添加保障方案前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addSafeguardPlanForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskId = request.getParameter("taskId");
		Map map = getSafeguardPlanBo().addSafeguardPlanForm(taskId, userInfo);
		String existFlag = (String) map.get("existFlag");
		request.setAttribute("saveoreditflag", "save");
		SetDataToRequest(request, map);
		if (existFlag.equals("old")) {
			return mapping.findForward("editSafeguardPlan");
		}
		return mapping.findForward("addSafeguardPlan");
	}

	/**
	 * 加载特巡计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward loadDataSpPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String id = request.getParameter("businessId");
		List<SpecialPlan> list = getSafeguardPlanBo().findSpPlanBySafeguardId(id);
		StringBuffer buf = new StringBuffer("");
		if (list != null && list.size() > 0) {
			buf.append("<table class='tablelist'>");
			for (SpecialPlan plan : list) {
				buf.append("<tr class='trcolor'>");
				buf.append("<td class='tablelisttd'>");
				buf.append(plan.getPlanName());
				buf.append("</td>");
				buf.append("<td class='tablelisttd'>");
				buf.append(sdf.format(plan.getStartDate()));
				buf.append(" -- ");
				buf.append(sdf.format(plan.getEndDate()));
				buf.append("</td>");
				buf.append("<td class='tablelisttd'>");
				buf.append(plan.getPatrolGroupId());
				buf.append("</td>");
				buf.append("<td class='tablelisttd'>");
				buf.append("<a onclick=editSpecPlan('");
				buf.append(id);
				buf.append("','");
				buf.append(plan.getId());
				buf.append("') style='cursor: pointer; color: blue;'>");
				buf.append("修改");
				buf.append("</a>");
				buf.append("</td>");
				buf.append("<td class='tablelisttd'>");
				buf.append("<a onclick=deleteProblem('");
				buf.append(id);
				buf.append("','");
				buf.append(plan.getId());
				buf.append("') style='cursor: pointer; color: blue;'>");
				buf.append("删除");
				buf.append("</a>");
				buf.append("</td>");
				buf.append("</tr>");
			}
			buf.append("</table>");
		}
		super.outPrint(response, buf.toString());
		System.out.println("*******buf.toString():" + buf.toString());
		return null;
	}

	/**
	 * 添加保障方案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward addSafeguardPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		SafeguardTaskBo stb = (SafeguardTaskBo) ctx.getBean("safeguardTaskBo");
		SafeguardPlanBean safeguardPlanBean = (SafeguardPlanBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String[] spplanIds = request.getParameterValues("spplanValue");
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		String isAllNet = request.getParameter("isAllNet");//是否为全网保障
		List<String> trunkList = null;

		String taskId = safeguardPlanBean.getSafeguardId();
		String name = stb.get(taskId).getSafeguardName();
		//如果为全网保障不需要保持中继段信息。
		if (!SafeguardPlan.IS_ALL_NET.equals(isAllNet)) {
			// 获得中继段id
			String trunks = request.getParameter("trunk");
			trunkList = new ArrayList<String>();
			if (StringUtils.isNotBlank(trunks)) {
				trunkList = Arrays.asList(trunks.split(","));
			}
		}
		String saveflag = request.getParameter("saveflag");

		try {
			if ("0".equals(saveflag)) {
				getSafeguardPlanBo().addSafeguardPlan(safeguardPlanBean, userInfo, trunkList, files, spplanIds);
				log(request, "添加保障方案（任务名称为：" + name + "）", "保障管理");
				return forwardInfoPage(mapping, request, "addSafeguardPlanSuccess");
			} else {
				getSafeguardPlanBo().tempSaveSafeguardPlan(safeguardPlanBean, userInfo, trunkList, files, spplanIds);
				log(request, "暂存保障方案（任务名称为：" + name + "）", "保障管理");
				return forwardInfoPage(mapping, request, "tempSaveSafeguardPlanSuccess");
			}
		} catch (ServiceException e) {
			logger.error("添加保障方案出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "addSafeguardPlanError");
		}
	}

	/**
	 * 查看保障方案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewSafeguardPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String planId = request.getParameter("planId");
		String isread = request.getParameter("isread");
		Map map = getSafeguardPlanBo().viewSafeguardPlanData(planId);
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		String sublineIds = (String) map.get("sublineIds");
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		List list = null;
		Object list2 = map.get("list");
		if (list2 != null && !"".equals(list2)) {
			list = (List) list2;
		}
		String conId = (String) map.get("conId");
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if (safeguardSpObj != null) {
			safeguardSps = (List) safeguardSpObj;
			specialPlans = (List) specialPlanObj;
		}
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("list", list);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("isread", isread);
		request.setAttribute("conId", conId);
		if ("terminate".equals(flag)) {
			return mapping.findForward("viewSafeguardPlanTerminate");
		} else {
			return mapping.findForward("viewSafeguardPlan");
		}
	}

	/**
	 * 审批保障方案前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveSafeguardPlanForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String planId = request.getParameter("planId");
		String operator = request.getParameter("operator");
		Map map = getSafeguardPlanBo().getSafeguardPlanData(planId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		String sublineIds = (String) map.get("sublineIds");
		List list = null;
		Object list2 = map.get("list");
		if (list2 != null && !"".equals(list2)) {
			list = (List) list2;
		}
		long planCreateTime = safeguardPlan.getMakeDate().getTime();
		Date deadline = safeguardTask.getDeadline();
		long planDeadline = 0;
		if (deadline != null) {
			planDeadline = deadline.getTime();
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if (safeguardSpObj != null) {
			safeguardSps = (List) safeguardSpObj;
			specialPlans = (List) specialPlanObj;
		}
		double days = (planCreateTime - planDeadline) / 1000.0 / 60.0 / 60.0;
		if (days < 0.0) {
			days = 0.0 - days;
		}
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("operator", operator);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("list", list);
		request.setAttribute("days", days);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		return mapping.findForward("approveSafeguardPlan");
	}

	/**
	 * 审批保障方案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveSafeguardPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		SafeguardTaskBo stb = (SafeguardTaskBo) ctx.getBean("safeguardTaskBo");
		SafeguardPlanBean safeguardPlanBean = (SafeguardPlanBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String approveResult = safeguardPlanBean.getApproveResult();
		String taskId = safeguardPlanBean.getSafeguardId();
		String name = stb.get(taskId).getSafeguardName();
		try {
			getSafeguardPlanBo().approveSafeguardPlan(safeguardPlanBean, userInfo);
			if ("0".equals(approveResult)) {
				log(request, "审批未通过保障方案（任务名称为：" + name + "）", "保障管理");
				return forwardInfoPage(mapping, request, "approveSafeguardPlanUnpass");
			} else if ("1".equals(approveResult)) {
				log(request, "审批通过保障方案（任务名称为：" + name + "）", "保障管理");
				return forwardInfoPage(mapping, request, "approveSafeguardPlanPass");
			} else {
				log(request, "转审保障方案（任务名称为：" + name + "）", "保障管理");
				return forwardInfoPage(mapping, request, "approveSafeguardPlanTransfer");
			}
		} catch (ServiceException e) {
			logger.error("审核保障方案出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "approveSafeguardPlanError");
		}
	}

	/**
	 * 修改保障方案前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editSafeguardPlanForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String planId = request.getParameter("planId");
		Map map = getSafeguardPlanBo().getSafeguardPlanData(planId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		String sublineIds = (String) map.get("sublineIds");
		String[] approveInfo = (String[]) map.get("approveInfo");
		List list = null;
		Object list2 = map.get("list");
		if (list2 != null && !"".equals(list2)) {
			list = (List) list2;
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if (safeguardSpObj != null) {
			safeguardSps = (List) safeguardSpObj;
			specialPlans = (List) specialPlanObj;
		}
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("list", list);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("approveInfo", approveInfo);
		request.setAttribute("approveMan", "approveMan");
		return mapping.findForward("editSafeguardPlan");
	}

	/**
	 * 修改演练方案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editSafeguardPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		SafeguardTaskBo stb = (SafeguardTaskBo) ctx.getBean("safeguardTaskBo");
		SafeguardPlanBean safeguardPlanBean = (SafeguardPlanBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		// 获得中继段id
		String trunks = request.getParameter("trunk");
		String saveflag = request.getParameter("saveflag");
		String saveoreditflag = request.getParameter("saveoreditflag");
		String[] spplanIds = request.getParameterValues("spplanValue");
		List<String> trunkList = new ArrayList<String>();

		String taskId = safeguardPlanBean.getSafeguardId();
		String name = stb.get(taskId).getSafeguardName();

		if (StringUtils.isNotBlank(trunks)) {
			trunkList = Arrays.asList(trunks.split(","));
		}
		try {
			if ("0".equals(saveflag)) {
				getSafeguardPlanBo().editSafeguardPlan(safeguardPlanBean, userInfo, trunkList, files);
			} else {
				getSafeguardPlanBo().tempSaveSafeguardPlan(safeguardPlanBean, userInfo, trunkList, files, spplanIds);
				log(request, "临时保存保障方案（任务名称为：" + name + "）", "保障管理");
				return forwardInfoPage(mapping, request, "tempSaveSafeguardPlanSuccess");
			}
			if ("save".equals(saveoreditflag)) {
				log(request, "添加保障方案（任务名称为：" + name + "）", "保障管理");
				return forwardInfoPage(mapping, request, "addSafeguardPlanSuccess");
			} else {
				log(request, "修改保障方案（任务名称为：" + name + "）", "保障管理");
				return forwardInfoPage(mapping, request, "editSafeguardPlanSuccess");
			}
		} catch (ServiceException e) {
			logger.error("编辑保障方案出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "editSafeguardPlanError");
		}
	}

	/**
	 * 查阅演练方案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward readReply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String approverId = userInfo.getUserID();
		String planId = request.getParameter("planId");
		getSafeguardPlanBo().readReply(userInfo, approverId, planId);
		return forwardInfoPage(mapping, request, "safeguardPlanReadReplySuccess");
	}
}
