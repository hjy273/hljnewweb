package com.cabletech.linepatrol.dispatchtask.action;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchTaskBean;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.services.ConditionGenerate;
import com.cabletech.linepatrol.dispatchtask.services.DispatchTaskBO;
import com.cabletech.linepatrol.dispatchtask.services.QueryDispatchTaskBO;
import com.cabletech.linepatrol.dispatchtask.template.SendTaskTemplate;

public class DispatchTaskAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private WebApplicationContext ctx;
	private UserInfo userInfo;
	private List<FileItem> files;

	/**
	 * 执行初始化动作
	 * 
	 * @param request
	 */
	public void initialize(HttpServletRequest request) {
		ctx = getWebApplicationContext();
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		files = (List) request.getSession().getAttribute("FILES");
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
	public ActionForward viewDispatchTaskProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		String condition = "";
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		condition += conditionGenerate.getUserQueryCondition(userInfo);
		List waitHandleTaskNum = bo.queryForHandleDispatchTaskNum(condition,
				userInfo);
		request.setAttribute("wait_sign_in_task_num", waitHandleTaskNum.get(0));
		request.setAttribute("wait_transfer_sign_in_task_num",
				waitHandleTaskNum.get(1));
		request.setAttribute("wait_refuse_confirm_task_num", waitHandleTaskNum
				.get(2));
		request.setAttribute("wait_reply_task_num", waitHandleTaskNum.get(3));
		request.setAttribute("wait_check_task_num", waitHandleTaskNum.get(4));
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null
				&& !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("view_dispatch_task_process");
	}

	/**
	 * 载入派单已办流程图页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewDispatchTaskHistoryProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		String condition = "";
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		condition += conditionGenerate.getUserQueryCondition(userInfo);
		List handledTaskNum = bo.queryForHandledDispatchTaskNum(condition,
				userInfo);
		request.setAttribute("dispatched_task_num", handledTaskNum.get(0));
		request.setAttribute("signed_in_task_num", handledTaskNum.get(1));
		request.setAttribute("transfered_sign_in_task_num", handledTaskNum
				.get(2));
		request.setAttribute("replyed_task_num", handledTaskNum.get(3));
		request.setAttribute("checked_task_num", handledTaskNum.get(4));
		request.setAttribute("canceled_task_num", handledTaskNum.get(5));
		request.setAttribute("refused_task_num", handledTaskNum.get(6));
		request.setAttribute("confirmed_task_num", handledTaskNum.get(7));
		request.setAttribute("task_name", taskName);
		request.setAttribute("task_out_come", taskOutCome);
		return mapping.findForward("view_dispatch_task_history_process");
	}

	/**
	 * 载入派单任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dispatchTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		if (env != null && WAP_ENV.equals(env)) {
			QueryDispatchTaskBO bo = (QueryDispatchTaskBO) ctx
					.getBean("queryDispatchTaskBO");
			String condition = "";
			if ("1".equals(userInfo.getDeptype())) {
				condition += " and assortment_id='dispatch_task' ";
			}
			if ("2".equals(userInfo.getDeptype())) {
				condition += " and assortment_id='dispatch_task_con'  ";
			}
			List dispatchTaskTypeList = bo.getQueryDispatchTaskDao()
					.queryDispatchTaskTypeList(condition);
			request.setAttribute("dispatch_task_type_list",
					dispatchTaskTypeList);
			request.setAttribute("env", env);
			request.setAttribute("input_name_map", request
					.getAttribute("input_name_map"));
			return mapping.findForward("dispatch_task_wap_add");
		}
		return mapping.findForward("dispatch_task_add");
	}

	/**
	 * 执行派单任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dispatchTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		DispatchTaskBean bean = (DispatchTaskBean) form;
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		bo.saveDispatchTask(bean, userInfo, files);
		log(request, "保存派单任务（任务主题为：" + bean.getSendtopic() + "）", "任务派单");
		String url = request.getContextPath()
				+ "/dispatchtask/dispatch_task.do?method=dispatchTaskForm";
		if (env != null && WAP_ENV.equals(env)) {
			url = request.getContextPath() + "/wap/login.do?method=index&&env="
					+ env;
			return super.forwardWapInfoPageWithUrl(mapping, request, "110102",
					url);
		}
		return super.forwardInfoPageWithUrl(mapping, request, "110102", url);
	}

	/**
	 * 载入修改派单任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateDispatchTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String dispatchId = request.getParameter("dispatch_id");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		DispatchTaskBean bean = bo.viewDispatchTask(dispatchId);
		request.setAttribute("bean", bean);
		return mapping.findForward("dispatch_task_mod");
	}

	/**
	 * 执行修改派单任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateDispatchTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		DispatchTaskBean bean = (DispatchTaskBean) form;
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		bo.updateDispatchTask(bean, userInfo, files);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request, "修改派单任务（任务主题为：" + bean.getSendtopic() + "）", "任务派单");
		return super.forwardInfoPageWithUrl(mapping, request, "110104", url);
	}

	/**
	 * 执行删除派单任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteDispatchTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		DispatchTaskBean bean = (DispatchTaskBean) form;
		bean.setId(request.getParameter("dispatch_id"));
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		bo.deleteDispatchTask(bean);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request, "删除派单任务（任务主题为：" + bean.getSendtopic() + "）", "任务派单");
		return super.forwardInfoPageWithUrl(mapping, request, "110801", url);
	}

	/**
	 * 执行调入取消派单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelDispatchTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String dispatchId = request.getParameter("dispatch_id");
		request.setAttribute("dispatch_id", dispatchId);
		return mapping.findForward("dispatch_task_cancel");
	}

	/**
	 * 执行取消派单任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelDispatchTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		DispatchTaskBean bean = (DispatchTaskBean) form;
		// bean.setId(request.getParameter("dispatch_id"));
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		String name = bo.get(bean.getId()).getSendtopic();
		bo.cancelDispatchTask(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request, "取消派单任务（任务主题为：" + name + "）", "任务派单");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request, "110901", url);
	}

	/**
	 * 执行查询待办派单任务列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryForHandleDispatchTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		initialize(request);
		super.setPageReset(request);
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		String condition = "";
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		condition += conditionGenerate.getUserQueryCondition(userInfo);
		String taskName = request.getParameter("task_name");
		List handleTaskList = bo.queryForHandleDispatchTask(taskName,
				condition, userInfo);
		if (env != null && WAP_ENV.equals(env)) {
			// handleTaskList = bo.processHandleTaskList(handleTaskList);
		}
		request.getSession().setAttribute("DISPATCH_TASK_LIST", handleTaskList);
		if (handleTaskList != null && !handleTaskList.isEmpty()) {
			request.getSession().setAttribute("DISPATCH_TASK_LIST_NUM",
					"" + handleTaskList.size());
		} else {
			request.getSession().setAttribute("DISPATCH_TASK_LIST_NUM", "0");
		}
		request.setAttribute("task_name", taskName);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("dispatch_task_wait_handle_wap_list");
		}
		return mapping.findForward("dispatch_task_wait_handle_list");
	}

	/**
	 * 执行查询已办派单任务列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryForFinishHandledDispatchTask(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		super.setPageReset(request);
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		String condition = "";
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		condition += conditionGenerate.getDateCondition("d.sendtime", request
				.getParameter("begintime"), "add_months(sysdate,-1)", ">=",
				"00:00:00");
		condition += conditionGenerate.getDateCondition("d.sendtime", request
				.getParameter("endtime"), "sysdate", "<=", "23:59:59");
		// condition += conditionGenerate.getConditionByInputValues(
		// "process.handled_task_name", taskName,
		// "process.handled_task_name");
		// condition += conditionGenerate.getConditionByInputValues(
		// "process.task_out_come", taskName, "process.task_out_come");
		condition += conditionGenerate.getUserQueryCondition(userInfo);
		List list = bo.queryForFinishHandledDispatchTask(condition, userInfo,
				taskName, taskOutCome);
		request.getSession().setAttribute("DISPATCH_TASK_LIST", list);
		request.setAttribute("begin_time", request.getParameter("begintime"));
		request.setAttribute("end_time", request.getParameter("endtime"));
		return mapping.findForward("dispatch_task_finish_handled_list");
	}

	/**
	 * 执行查询派单任务列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryForDispatchTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		super.setPageReset(request);
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		String condition = "";
		ConditionGenerate conditionGenerate = new ConditionGenerate();
		condition += conditionGenerate.getUserQueryCondition(userInfo);
		condition += conditionGenerate
				.getInputCondition((DispatchTaskBean) form);
		List taskList = bo.queryForDispatchTask(condition);
		request.getSession().setAttribute("DISPATCH_TASK_LIST", taskList);
		return mapping.findForward("dispatch_task_list");
	}

	/**
	 * 执行载入查询派单任务表单列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryDispatchTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		// DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		QueryDispatchTaskBO bo = (QueryDispatchTaskBO) ctx
				.getBean("queryDispatchTaskBO");
		List dispatchTaskTypeList = bo.getQueryDispatchTaskDao()
				.queryDispatchTaskTypeList(" and assortment_id='dispatch_task' ");
		request.setAttribute("dispatch_task_type_list", dispatchTaskTypeList);
		return mapping.findForward("dispatch_task_query");
	}

	/**
	 * 执行查看派单任务详细信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewDispatchTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String dispatchId = request.getParameter("dispatch_id");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		DispatchTaskBean bean = bo.viewDispatchTask(dispatchId);
		request.setAttribute("bean", bean);
		request.setAttribute("dispatch_id", dispatchId);
		return mapping.findForward("dispatch_task_view");
	}

	/**
	 * 执行查看派单任务流程历史信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewProcessHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String dispatchId = request.getParameter("dispatch_id");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		List list = bo.getDispatchTaskAcceptUsers(dispatchId, userInfo);
		request.setAttribute("process_list", list);
		request.setAttribute("dispatch_id", dispatchId);
		return mapping.findForward("view_process_history");
	}

	/**
	 * 导出派单信息一览表
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward exportDispatchTaskResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		OutputStream out;
		initResponse(response, "派单信息一览表.xls");
		out = response.getOutputStream();
		List list = (List) request.getSession().getAttribute(
				"DISPATCH_TASK_LIST");
		logger.info("得到list");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		SendTaskTemplate template = bo.exportDispatchTaskResult(list);
		template.write(out);
		return null;
	}

	public void initResponse(HttpServletResponse response, String outfilename)
			throws Exception {
		response.reset();
		response.setContentType(DispatchTaskConstant.CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));
	}
}
