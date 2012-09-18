package com.cabletech.linepatrol.drill.action;

import java.io.PrintWriter;
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
import com.cabletech.linepatrol.drill.beans.DrillTaskBean;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.services.DrillTaskBo;
import com.cabletech.linepatrol.drill.workflow.DrillWorkflowBO;

public class DrillTaskAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(DrillTaskAction.class
			.getName());

	private DrillTaskBo getDrillTaskBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (DrillTaskBo) ctx.getBean("drillTaskBo");
	}

	/**
	 * 转向添加演练任务界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDrillTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("addDrillTask");
	}

	/**
	 * 添加演练任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDrillTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DrillTaskBean drillTaskBean = (DrillTaskBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		String saveflag = request.getParameter("saveflag");
		try {
			if ("0".equals(saveflag)) {
				log(request, "添加演练任务（演练名称为：" + drillTaskBean.getName() + "）",
						"演练管理");
				getDrillTaskBo().addDrillTask(drillTaskBean, userInfo, files);
				return forwardInfoPage(mapping, request, "addDrillTaskSuccess");
			} else {
				getDrillTaskBo().tempSaveDrillTask(drillTaskBean, userInfo,
						files);
				log(request, "暂存演练任务（演练名称为：" + drillTaskBean.getName() + "）",
						"演练管理");
				return forwardInfoPage(mapping, request,
						"tempSaveDrillTaskSuccess");
			}
		} catch (ServiceException e) {
			logger.error("制定演练任务失败，出错信息为：" + e.getMessage());
			return forwardErrorPage(mapping, request, "addDrillTaskError");
		}
	}

	/**
	 * 获得待完善演练任务列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward perfectDrillTaskList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String creator = userInfo.getUserID();
		List list = getDrillTaskBo().perfectDrillTaskList(creator);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("perfectDrillTaskList");
	}

	/**
	 * 完善演练任务前加载数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward perfectDrillTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		Map map = getDrillTaskBo().perfectDrillTaskForm(taskId);
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		String[] userIds = (String[]) map.get("userIds");
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("userIds", userIds);
		return mapping.findForward("perfectDrillTask");
	}

	/**
	 * 完善演练任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward perfectDrillTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DrillTaskBean drillTaskBean = (DrillTaskBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		try {
			getDrillTaskBo().perfectDrillTask(drillTaskBean, userInfo, files);
			log(request, "完善演练任务（演练名称为：" + drillTaskBean.getName() + "）",
					"演练管理");
			return forwardInfoPage(mapping, request, "perfectDrillTaskSuccess");
		} catch (ServiceException e) {
			logger.error("制定演练任务失败，出错信息为：" + e.getMessage());
			return forwardErrorPage(mapping, request, "addDrillTaskError");
		}
	}

	/**
	 * 删除临时演练任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteTempTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		try {
			DrillTask task = getDrillTaskBo().get(taskId);
			String name = task.getName();
			getDrillTaskBo().deleteTempTask(taskId);
			log(request, "删除临时演练任务（演练名称为：" + name + "）", "演练管理");
			return forwardInfoPage(mapping, request,
					"deleteDrillTempTaskSuccess");
		} catch (ServiceException e) {
			logger.error("删除演练任务失败，出错信息为：" + e.getMessage());
			e.printStackTrace();
			return forwardErrorPage(mapping, request,
					"deleteDrillTempTaskError");
		}
	}

	/**
	 * 查看演练任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewDrillTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String contractorId = userInfo.getDeptID();
		Map map = getDrillTaskBo().viewDrillTask(taskId, contractorId);
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		String conId = (String) map.get("conId");
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("conId", conId);
		return mapping.findForward("viewDrillTask");
	}

	/**
	 * 获得代办工作列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAgentList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		DrillWorkflowBO workflowBo = (DrillWorkflowBO) ctx
				.getBean("drillWorkflowBO");
		String env = request.getParameter("env");

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String deptype = userInfo.getDeptype();
		String dept = userInfo.getDeptID();
		String condition = " ";
		String taskName = request.getParameter("task_name");
		if ("2".equals(deptype)) {
			condition += " and taskcon.contractor_id='" + dept + "' ";
		}
		List list = getDrillTaskBo()
				.getAgentList(userInfo, condition, taskName);
		if (env != null && WAP_ENV.equals(env)) {
			taskName = "approve_drill_proj_task,approve_change_drill_proj_task,approve_drill_summary_task";
			list = getDrillTaskBo().getAgentList(userInfo, condition, taskName);
		}
		Integer num = 0;
		if (list != null && list.size() > 0) {
			num = list.size();
		}
		/*
		 * num = new Integer(workflowBo.queryForHandleNumber(userInfo
		 * .getUserID(), condition, taskName));
		 */
		request.setAttribute("env", env);
		request.setAttribute("num", num);
		request.setAttribute("task_name", taskName);
		request.getSession().setAttribute("list", list);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("drill_task_wait_handle_wap_list");
		}
		return mapping.findForward("agentList");
	}

	/**
	 * 载入派单流程图页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewDrillTaskProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		DrillTaskBo bo = (DrillTaskBo) ctx.getBean("drillTaskBo");
		String condition = "";
		List waitHandleTaskNum = bo.queryForHandleDrillTaskNum(condition,
				userInfo);
		request.setAttribute("wait_create_drill_proj_num", waitHandleTaskNum
				.get(0));
		request.setAttribute("wait_approve_drill_proj_num", waitHandleTaskNum
				.get(1));
		request.setAttribute("wait_change_drill_proj_num", waitHandleTaskNum
				.get(2));
		request.setAttribute("wait_approve_change_drill_proj_num",
				waitHandleTaskNum.get(3));
		request.setAttribute("wait_create_drill_summary_num", waitHandleTaskNum
				.get(4));
		request.setAttribute("wait_approve_drill_summary_num",
				waitHandleTaskNum.get(5));
		request.setAttribute("wait_evaluate_num", waitHandleTaskNum.get(6));
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null
				&& !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("view_drill_task_process");
	}

	/**
	 * 获得已办工作列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryFinishHandledDrillList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		List list = getDrillTaskBo().queryFinishHandledDrillList(userInfo,
				taskName, taskOutCome);
		int num = 0;
		if (list != null && list.size() > 0) {
			num = list.size();
		}
		request.getSession().setAttribute("list", list);
		request.setAttribute("num", num);
		return mapping.findForward("queryFinishHandledDrillList");
	}

	/**
	 * 查看处理流程
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewDrillHistoryProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// initialize(request);
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		String condition = "";
		// condition += ConditionGenerate.getUserQueryCondition(userInfo);
		List waitHandleTaskNum = getDrillTaskBo().queryForHandledDrillNumList(
				condition, userInfo);
		request.setAttribute("create_drill_proj_task_num", waitHandleTaskNum
				.get(0));
		request.setAttribute("approve_drill_proj_task_num", waitHandleTaskNum
				.get(1));
		request.setAttribute("change_drill_proj_task_num", waitHandleTaskNum
				.get(2));
		request.setAttribute("approve_change_drill_proj_task_num",
				waitHandleTaskNum.get(3));
		request.setAttribute("create_drill_summary_task_num", waitHandleTaskNum
				.get(4));
		request.setAttribute("approve_drill_summary_task_num",
				waitHandleTaskNum.get(5));
		request.setAttribute("evaluate_task_num", waitHandleTaskNum.get(6));
		request.setAttribute("task_name", taskName);
		request.setAttribute("task_out_come", taskOutCome);
		return mapping.findForward("view_drill_history_process");
	}
	
	/**
	 * 执行取消演练任务表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelDrillTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String drillTaskId = request.getParameter("drill_task_id");
		request.setAttribute("drill_task_id", drillTaskId);
		return mapping.findForward("drill_cancel");
	}

	/**
	 * 执行取消演练任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelDrillTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		DrillTaskBo bo = getDrillTaskBo();
		DrillTaskBean bean = (DrillTaskBean) form;
		bo.cancelDrillTask(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		//out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_DRILL_TASK_SUCCESS", url);
	}
}
