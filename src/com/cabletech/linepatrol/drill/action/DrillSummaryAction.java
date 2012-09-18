package com.cabletech.linepatrol.drill.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.drill.beans.DrillSummaryBean;
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillSummary;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.services.DrillPlanBo;
import com.cabletech.linepatrol.drill.services.DrillSummaryBo;
import com.cabletech.linepatrol.drill.services.DrillTaskBo;

public class DrillSummaryAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(DrillSummaryAction.class
			.getName());

	private DrillSummaryBo getDrillSummaryBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (DrillSummaryBo) ctx.getBean("drillSummaryBo");
	}

	/**
	 * 添加演练总结前加载数据信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDrillSummaryForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String planId = request.getParameter("planId");
		Map map = getDrillSummaryBo().addDrillSummaryForm(planId);
		List planEndList = (List) map.get("planEndList");
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		DrillSummary drillSummary = (DrillSummary) map.get("drillSummary");
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("list", planEndList);
		request.setAttribute("drillSummary", drillSummary);
		request.setAttribute("flag", "tempSave");
		if (drillSummary != null) {
			return mapping.findForward("editDrillSummary");
		}
		return mapping.findForward("addDrillSummary");
	}

	/**
	 * 添加演练总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDrillSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		DrillTaskBo drillTaskBo=(DrillTaskBo)ctx.getBean("drillTaskBo");
		DrillSummaryBean drillSummaryBean = (DrillSummaryBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		String flag = request.getParameter("flag");
		String taskId=drillSummaryBean.getTaskId();
		String name=drillTaskBo.get(taskId).getName();
		try {
			if ("0".equals(flag)) {
				getDrillSummaryBo().addDrillSummary(drillSummaryBean, userInfo,
						files);
				log(request,"添加演练总结（演练名称为："+name+"）","演练管理");
				return forwardInfoPage(mapping, request,
						"addDrillSummarySuccess");
			} else {
				getDrillSummaryBo().tempSaveDrillSummary(drillSummaryBean,
						userInfo, files);
				log(request,"暂存演练总结（演练名称为："+name+"）","演练管理");
				return forwardInfoPage(mapping, request,
						"tempSaveDrillSummarySuccess");
			}
		} catch (ServiceException e) {
			logger.info("添加演练总结出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "addDrillSummaryError");
		}
	}

	/**
	 * 修改演练总结前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editDrillSummaryForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String summaryId = request.getParameter("summaryId");
		Map map = getDrillSummaryBo().getTaskPlanSummary(summaryId);
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		DrillSummary drillSummary = (DrillSummary) map.get("drillSummary");
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("drillSummary", drillSummary);
		return mapping.findForward("editDrillSummary");
	}

	/**
	 * 修改演练总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editDrillSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		DrillTaskBo drillTaskBo=(DrillTaskBo)ctx.getBean("drillTaskBo");
		DrillSummaryBean drillSummaryBean = (DrillSummaryBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String flag = request.getParameter("tempFlag");
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		String taskId=drillSummaryBean.getTaskId();
		String name=drillTaskBo.get(taskId).getName();
		try {
			if ("0".equals(flag)) {
				getDrillSummaryBo().editDrillSummary(drillSummaryBean,
						userInfo, files);
			} else {
				getDrillSummaryBo().tempSaveDrillSummary(drillSummaryBean,
						userInfo, files);
				log(request,"暂存演练总结（演练名称为："+name+"）","演练管理");
				return forwardInfoPage(mapping, request,
						"tempSaveDrillSummarySuccess");
			}
			if (flag == null) {
				log(request,"修改演练总结（演练名称为："+name+"）","演练管理");
				return forwardInfoPage(mapping, request,
						"editDrillSummarySuccess");
			} else {
				log(request,"添加演练总结（演练名称为："+name+"）","演练管理");
				return forwardInfoPage(mapping, request,
						"addDrillSummarySuccess");
			}
		} catch (ServiceException e) {
			logger.info("修改演练总结出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "editDrillSummaryError");
		}
	}

	/**
	 * 查看演练总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewDrillSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		String summaryId = request.getParameter("summaryId");
		String isread = request.getParameter("isread");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		Map map = getDrillSummaryBo().getTaskPlanSummary(summaryId);
		String conId = (String)map.get("conId");
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		DrillSummary drillSummary = (DrillSummary) map.get("drillSummary");
		List planEndList = (List) map.get("planEndList");
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("drillSummary", drillSummary);
		request.setAttribute("planEndList", planEndList);
		request.setAttribute("isread", isread);
		request.setAttribute("conId", conId);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("read_drill_summary_wap_task");
		}
		return mapping.findForward("viewDrillSummary");
	}

	/**
	 * 查阅演练总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward readReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		String env = request.getParameter("env");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String approverId = userInfo.getUserID();
		String summaryId = request.getParameter("summaryId");
		getDrillSummaryBo().readReply(userInfo, approverId, summaryId);
		if (env != null && WAP_ENV.equals(env)) {
			return forwardWapInfoPageWithUrl(mapping, request,
					"drillSummaryReadReplySuccess", url);
		}
		return forwardInfoPage(mapping, request, "drillSummaryReadReplySuccess");
	}

	/**
	 * 审批演练总结前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveDrillSummaryForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		String summaryId = request.getParameter("summaryId");
		String operator = request.getParameter("operator");
		Map map = getDrillSummaryBo().getTaskPlanSummary(summaryId);
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		DrillSummary drillSummary = (DrillSummary) map.get("drillSummary");
		String haveApproveInfo = (String)map.get("haveApproveInfo");
		long sumCreateTime = drillSummary.getCreateTime().getTime();
		Date deadline = drillPlan.getDeadline();
		long sumDeadline = 0;
		if (deadline != null) {
			sumDeadline = deadline.getTime();
		}
		double days = (sumCreateTime - sumDeadline) / 1000.0 / 60.0 / 60.0;
		if (days < 0) {
			days = 0.0 - days;
		}
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("drillSummary", drillSummary);
		request.setAttribute("operator", operator);
		request.setAttribute("days", days);
		request.setAttribute("env", env);
		request.setAttribute("haveApproveInfo", haveApproveInfo);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("approve_drill_summary_wap_task");
		}
		return mapping.findForward("approveDrillSummary");
	}

	/**
	 * 审批演练总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveDrillSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		DrillTaskBo drillTaskBo=(DrillTaskBo)ctx.getBean("drillTaskBo");
		DrillSummaryBo dsb=(DrillSummaryBo)ctx.getBean("drillSummaryBo");
		DrillPlanBo dpb=(DrillPlanBo)ctx.getBean("drillPlanBo");
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		String env = request.getParameter("env");
		DrillSummaryBean drillSummaryBean = (DrillSummaryBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(   
				"LOGIN_USER");
		String approveResult = drillSummaryBean.getApproveResult();
		String planId=dsb.get(drillSummaryBean.getId()).getPlanId();
		String taskId=dpb.get(planId).getTaskId();
		String name=drillTaskBo.get(taskId).getName();
		try {
			getDrillSummaryBo().approveDrillSummary(drillSummaryBean, userInfo);
			if (env != null && WAP_ENV.equals(env)) {
				if ("0".equals(approveResult)) {
					log(request,"审批未通过演练总结（演练名称为："+name+"）","演练管理");
					return forwardWapInfoPageWithUrl(mapping, request,
							"approveDrillSummaryUnpass", url);
				} else if ("1".equals(approveResult)) {
					log(request,"审批通过演练总结（演练名称为："+name+"）","演练管理");
					return forwardWapInfoPageWithUrl(mapping, request,
							"approveDrillSummaryPass", url);
				} else {
					log(request,"转审演练总结（演练名称为："+name+"）","演练管理");
					return forwardWapInfoPageWithUrl(mapping, request,
							"approveDrillSummaryTransfer", url);
				}
			}
			if ("0".equals(approveResult)) {
				log(request,"审批未通过演练总结（演练名称为："+name+"）","演练管理");
				return forwardInfoPage(mapping, request,
						"approveDrillSummaryUnpass");
			} else if ("1".equals(approveResult)) {
				log(request,"审批通过演练总结（演练名称为："+name+"）","演练管理");
				return forwardInfoPage(mapping, request,
						"approveDrillSummaryPass");
			} else {
				log(request,"转审演练总结（演练名称为："+name+"）","演练管理");
				return forwardInfoPage(mapping, request,
						"approveDrillSummaryTransfer");
			}
		} catch (ServiceException e) {
			logger.error("演练方案审核出错，出错信息：" + e.getMessage());
			if (env != null && WAP_ENV.equals(env)) {
				return forwardWapErrorPageWithUrl(mapping, request,
						"approveDrillSummaryError", url);
			}
			return forwardErrorPage(mapping, request,
					"approveDrillSummaryError");
		}
	}
}
