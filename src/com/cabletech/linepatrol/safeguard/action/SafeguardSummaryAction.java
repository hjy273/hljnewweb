package com.cabletech.linepatrol.safeguard.action;

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
import com.cabletech.linepatrol.safeguard.beans.SafeguardSummaryBean;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSummary;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.services.SafeguardSummaryBo;
import com.cabletech.linepatrol.safeguard.services.SafeguardTaskBo;

public class SafeguardSummaryAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger
			.getLogger(SafeguardSummaryAction.class.getName());

	private SafeguardSummaryBo getSafeguardSummaryBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (SafeguardSummaryBo) ctx.getBean("safeguardSummaryBo");
	}

	/**
	 * 添加保障总结前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addSafeguardSummaryForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String planId = request.getParameter("planId");
		Map map = getSafeguardSummaryBo().addSafeguardSummaryForm(planId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		List list = null;
		Object list2 = map.get("list");
		if(list2 != null && !"".equals(list2)){
			list = (List)list2;
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if(safeguardSpObj != null){
			safeguardSps = (List)safeguardSpObj;
			specialPlans = (List)specialPlanObj;
		}
		SafeguardSummary safeguardSummary = (SafeguardSummary)map.get("safeguardSummary");
		String sublineIds = (String) map.get("sublineIds");
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("list", list);
		request.setAttribute("safeguardSummary", safeguardSummary);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("flag", "tempSave");
		if(safeguardSummary != null){
			return mapping.findForward("editSafeguardSummary");
		}
		return mapping.findForward("addSafeguardSummary");
	}

	/**
	 * 添加保障总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addSafeguardSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		SafeguardTaskBo stb=(SafeguardTaskBo)ctx.getBean("safeguardTaskBo");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		SafeguardSummaryBean safeguardSummaryBean = (SafeguardSummaryBean) form;
		List<FileItem> files = (List)request.getSession().getAttribute("FILES");
		String flag = request.getParameter("flag");
		String taskId=safeguardSummaryBean.getTaskId();
		String name=stb.get(taskId).getSafeguardName();
		try {
			if("0".equals(flag)){
				getSafeguardSummaryBo().addSafeguardSummary(safeguardSummaryBean, userInfo, files);
				log(request,"添加保障总结（任务名称为："+name+"）","保障管理");
				return forwardInfoPage(mapping, request, "addSafeguardSummarySuccess");
			}else{
				getSafeguardSummaryBo().tempSaveSafeguardSummary(safeguardSummaryBean, userInfo, files);
				log(request,"临时保存保障总结（任务名称为："+name+"）","保障管理");
				return forwardInfoPage(mapping, request, "tempSaveSafeguardSummarySuccess");
			}
		} catch (ServiceException e) {
			logger.error("添加保障总结出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "addSafeguardSummaryError");
		}
	}

	/**
	 * 查看保障总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewSafeguardSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String summaryId = request.getParameter("summaryId");
		String isread = request.getParameter("isread");
		Map map = getSafeguardSummaryBo().getSafeguardSummaryData(summaryId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		SafeguardSummary safeguardSummary = (SafeguardSummary) map
				.get("safeguardSummary");
		String conId = (String)map.get("conId");
		List list = null;
		Object list2 = map.get("list");
		if(list2 != null && !"".equals(list2)){
			list = (List)list2;
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if(safeguardSpObj != null){
			safeguardSps = (List)safeguardSpObj;
			specialPlans = (List)specialPlanObj;
		}
		String sublineIds = (String) map.get("sublineIds");
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("safeguardSummary", safeguardSummary);
		request.setAttribute("list", list);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("isread", isread);
		request.setAttribute("conId", conId);
		return mapping.findForward("viewSafeguardSummary");
	}

	/**
	 * 审批保障总结前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveSafeguardSummaryForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String summaryId = request.getParameter("summaryId");
		String operator = request.getParameter("operator");
		Map map = getSafeguardSummaryBo().getSafeguardSummaryData(summaryId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		SafeguardSummary safeguardSummary = (SafeguardSummary) map
				.get("safeguardSummary");
		List list = null;
		Object list2 = map.get("list");
		if(list2 != null && !"".equals(list2)){
			list = (List)list2;
		}
		long sumCreateTime = safeguardSummary.getSumDate().getTime();
		Date deadline = safeguardPlan.getDeadline();
		long sumDeadline = 0;
		if(deadline != null){
			sumDeadline = deadline.getTime();
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if(safeguardSpObj != null){
			safeguardSps = (List)safeguardSpObj;
			specialPlans = (List)specialPlanObj;
		}
		double days = (sumCreateTime - sumDeadline)/1000.0/60.0/60.0;
		if(days < 0){
			days = 0.0 - days;
		}
		String sublineIds = (String) map.get("sublineIds");
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("safeguardSummary", safeguardSummary);
		request.setAttribute("operator", operator);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("list", list);
		request.setAttribute("days", days);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		return mapping.findForward("approveSafeguardSummary");
	}

	/**
	 * 审批保障总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveSafeguardSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		WebApplicationContext ctx=getWebApplicationContext();
		SafeguardTaskBo stb=(SafeguardTaskBo)ctx.getBean("safeguardTaskBo");

		SafeguardSummaryBean safeguardSummaryBean = (SafeguardSummaryBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String approveResult = safeguardSummaryBean.getApproveResult();
		String taskId=safeguardSummaryBean.getTaskId();
		String name=stb.get(taskId).getSafeguardName();
		try {
			getSafeguardSummaryBo().approveSafeguardSummary(safeguardSummaryBean, userInfo);
			if("0".equals(approveResult)){
				log(request,"审批未通过保障总结（任务名称为："+name+"）","保障管理");
				return forwardInfoPage(mapping, request, "approveSafeguardSummaryUnpass");
			}else if("1".equals(approveResult)){
				log(request,"审批通过保障总结（任务名称为："+name+"）","保障管理");
				return forwardInfoPage(mapping, request, "approveSafeguardSummaryPass");
			}else{
				log(request,"转审保障总结（任务名称为："+name+"）","保障管理");
				return forwardInfoPage(mapping, request, "approveSafeguardSummaryTransfer");
			}
		} catch (ServiceException e) {
			logger.error("审核保障总结出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request,"approveSafeguardSummaryError");
		}
	}

	/**
	 * 添加保障总结前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editSafeguardSummaryForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String summaryId = request.getParameter("summaryId");
		Map map = getSafeguardSummaryBo().getSafeguardSummaryData(summaryId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		SafeguardSummary safeguardSummary = (SafeguardSummary) map.get("safeguardSummary");
		List list = null;
		Object list2 = map.get("list");
		if(list2 != null && !"".equals(list2)){
			list = (List)list2;
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if(safeguardSpObj != null){
			safeguardSps = (List)safeguardSpObj;
			specialPlans = (List)specialPlanObj;
		}
		String sublineIds = (String) map.get("sublineIds");
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("safeguardSummary", safeguardSummary);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("list", list);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		return mapping.findForward("editSafeguardSummary");
	}

	/**
	 * 修改演练总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editSafeguardSummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		WebApplicationContext ctx=getWebApplicationContext();
		SafeguardTaskBo stb=(SafeguardTaskBo)ctx.getBean("safeguardTaskBo");

		SafeguardSummaryBean safeguardSummaryBean = (SafeguardSummaryBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List<FileItem> files = (List)request.getSession().getAttribute("FILES");
		String flag = request.getParameter("flag");
		String saveflag = request.getParameter("saveflag");
		String taskId=safeguardSummaryBean.getTaskId();
		String name=stb.get(taskId).getSafeguardName();
		try {
			if("0".equals(saveflag)){
				getSafeguardSummaryBo().editSafeguardSummary(safeguardSummaryBean,userInfo,files, flag);
			}else{
				log(request,"临时保存保障总结（任务名称为："+name+"）","保障管理");
				getSafeguardSummaryBo().tempSaveSafeguardSummary(safeguardSummaryBean, userInfo, files);
				return forwardInfoPage(mapping, request, "tempSaveSafeguardSummarySuccess");
			}
			if(flag == null || "".equals(flag)){
				log(request,"修改保障总结（任务名称为："+name+"）","保障管理");
				return forwardInfoPage(mapping, request,"editSafeguardSummarySuccess");
			}else{
				log(request,"添加保障总结（任务名称为："+name+"）","保障管理");
				return forwardInfoPage(mapping, request, "addSafeguardSummarySuccess");
			}
		} catch (ServiceException e) {
			logger.error("修改保障总结出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request,"editSafeguardSummaryError");
		}
	}
	
	/**
	 * 查阅保障总结
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward readReply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String approverId = userInfo.getUserID();
		String summaryId = request.getParameter("summaryId");
		getSafeguardSummaryBo().readReply(userInfo, approverId, summaryId);
		return forwardInfoPage(mapping, request,"safeguardSummaryReadReplySuccess");
	}
}
