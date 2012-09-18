package com.cabletech.linepatrol.safeguard.action;

import java.io.PrintWriter;
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
import com.cabletech.commons.util.DateUtil;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.safeguard.beans.SafeguardTaskBean;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.services.SafeguardTaskBo;
import com.cabletech.utils.StringUtil;

public class SafeguardTaskAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(SafeguardTaskAction.class.getName());

	private SafeguardTaskBo getSafeguardTaskBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (SafeguardTaskBo) ctx.getBean("safeguardTaskBo");
	}

	/**
	 * 添加保障任务前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addSafeguardTaskForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptId = userInfo.getDeptID();
		String year = DateUtil.DateToString(new Date(), "yyyy");
		String taskNumber = (getSafeguardTaskBo().getSafeguardTaskNumber(deptId)).toString();
		String safeguardCode = year + StringUtil.lpad(taskNumber, 4, "0");
		request.setAttribute("safeguardCode", safeguardCode);
		return mapping.findForward("addSafeguardTask");
	}

	/**
	 * 添加保障任务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addSafeguardTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SafeguardTaskBean safeguardTaskBean = (SafeguardTaskBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		String saveFlag = request.getParameter("saveflag");
		try {
			if ("0".equals(saveFlag)) {
				getSafeguardTaskBo().addSafeguardTask(safeguardTaskBean, userInfo, files);
				log(request, "添加保障任务（任务名称为：" + safeguardTaskBean.getSafeguardName() + "）", "保障管理");
				return forwardInfoPage(mapping, request, "addSafeguardTaskSuccess");
			} else {
				log(request, "临时保存保障任务（任务名称为：" + safeguardTaskBean.getSafeguardName() + "）", "保障管理");
				getSafeguardTaskBo().tempSaveSafeguardTask(safeguardTaskBean, userInfo, files);
				return forwardInfoPage(mapping, request, "tempSaveSafeguardTaskSuccess");
			}
		} catch (ServiceException e) {
			logger.error("保障任务派单出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "addSafeguardTaskError");
		}
	}

	/**
	 * 获得待完善保障任务列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getPerfectList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String creator = userInfo.getUserID();
		List list = getSafeguardTaskBo().getPerfectList(creator);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("safeguardPerfectList");
	}

	/**
	 * 完善保障任务前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward perfectSafeguardTaskForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		Map map = getSafeguardTaskBo().perfectSafeguardTaskForm(taskId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		String[] userIds = (String[]) map.get("userIds");
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("userIds", userIds);
		return mapping.findForward("perfectSafeguardTask");
	}

	/**
	 * 完善保障任务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward perfectSafeguardTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SafeguardTaskBean safeguardTaskBean = (SafeguardTaskBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		try {
			getSafeguardTaskBo().addSafeguardTask(safeguardTaskBean, userInfo, files);
			log(request, "完善保障任务（任务名称为：" + safeguardTaskBean.getSafeguardName() + "）", "保障管理");
			return forwardInfoPage(mapping, request, "addSafeguardTaskSuccess");
		} catch (ServiceException e) {
			logger.error("保障任务派单出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "addSafeguardTaskError");
		}
	}

	/**
	 * 删除临时保障任务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteTempTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		SafeguardTaskBo stb = (SafeguardTaskBo) ctx.getBean("safeguardTaskBo");
		String taskId = request.getParameter("taskId");

		String name = stb.get(taskId).getSafeguardName();

		try {
			getSafeguardTaskBo().deleteTempTask(taskId);

			log(request, "删除临时保障任务（任务名称为：" + name + "）", "保障管理");
			return forwardInfoPage(mapping, request, "deleteSafeguardTempTaskSuccess");
		} catch (ServiceException e) {
			return forwardErrorPage(mapping, request, "deleteSafeguardTempTaskError");
		}
	}

	/**
	 * 查看保障任务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewSafeguardTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String contractorId = userInfo.getDeptID();
		Map map = getSafeguardTaskBo().viewSafeguardTask(taskId, contractorId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		String conId = (String) map.get("conId");
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("conId", conId);
		return mapping.findForward("viewSafeguardTask");
	}

	/**
	 * 获得代办工作列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAgentList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptype = userInfo.getDeptype();
		String dept = userInfo.getDeptID();
		String taskName = request.getParameter("task_name");
		String condition = "";
		if ("2".equals(deptype)) {
			condition += " and taskCon.contractor_id='" + dept + "' ";
		}
		List list = getSafeguardTaskBo().getAgentList(userInfo, condition, taskName);
		Integer num = 0;
		if (list != null && list.size() > 0) {
			num = list.size();
		}
		request.setAttribute("num", num);
		request.setAttribute("task_name", taskName);
		request.getSession().setAttribute("list", list);
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
	public ActionForward viewSafeGuardProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		SafeguardTaskBo bo = (SafeguardTaskBo) ctx.getBean("safeguardTaskBo");
		String condition = "";
		List waitHandleTaskNum = bo.queryForHandleSafeGuardNum(condition, userInfo);
		request.setAttribute("wait_create_guard_proj_num", waitHandleTaskNum.get(0));
		request.setAttribute("wait_approve_guard_proj_num", waitHandleTaskNum.get(1));
		request.setAttribute("wait_guard_plan_execute_num", waitHandleTaskNum.get(2));
		request.setAttribute("wait_create_guard_summary_num", waitHandleTaskNum.get(3));
		request.setAttribute("wait_approve_guard_summary_num", waitHandleTaskNum.get(4));
		request.setAttribute("wait_evaluate_num", waitHandleTaskNum.get(5));
		request.setAttribute("wait_guard_plan_approve_num", waitHandleTaskNum.get(6));
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null && !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("view_safe_guard_process");
	}

	/**
	 * 获得已办工作列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryFinishHandledSafeguardList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		List list = getSafeguardTaskBo().queryFinishHandledSafeguardList(userInfo, taskName, taskOutCome);
		int num = 0;
		if (list != null && list.size() > 0) {
			num = list.size();
		}
		request.getSession().setAttribute("list", list);
		request.setAttribute("num", num);
		return mapping.findForward("queryFinishHandledSafeguardList");
	}

	/**
	 * 获得节点代办数量
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewSafeguardHistoryProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		//initialize(request);
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		String condition = "";
		//condition += ConditionGenerate.getUserQueryCondition(userInfo);
		List waitHandleTaskNum = getSafeguardTaskBo().queryForHandledSafeguardNumList(condition, userInfo);
		request.setAttribute("create_guard_proj_task_num", waitHandleTaskNum.get(0));
		request.setAttribute("approve_guard_proj_task_num", waitHandleTaskNum.get(1));
		request.setAttribute("guard_plan_execute_state_num", waitHandleTaskNum.get(2));
		request.setAttribute("create_guard_summary_task_num", waitHandleTaskNum.get(3));
		request.setAttribute("approve_guard_summary_task_num", waitHandleTaskNum.get(4));
		request.setAttribute("evaluate_task_num", waitHandleTaskNum.get(5));
		request.setAttribute("task_name", taskName);
		request.setAttribute("task_out_come", taskOutCome);
		return mapping.findForward("view_Safeguard_history_process");
	}

	/**
	 * 执行取消保障任务表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelSafeguardTaskForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String safeguardTaskId = request.getParameter("safeguard_task_id");
		request.setAttribute("safeguard_task_id", safeguardTaskId);
		return mapping.findForward("safeguard_cancel");
	}

	/**
	 * 执行取消保障任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelSafeguardTask(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		SafeguardTaskBo bo = getSafeguardTaskBo();
		SafeguardTaskBean bean = (SafeguardTaskBean) form;
		bo.cancelSafeguardTask(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_DRILL_TASK_SUCCESS", url);
	}
}
