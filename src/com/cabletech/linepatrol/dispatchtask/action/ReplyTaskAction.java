package com.cabletech.linepatrol.dispatchtask.action;

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
import com.cabletech.linepatrol.dispatchtask.beans.ReplyTaskBean;
import com.cabletech.linepatrol.dispatchtask.services.CheckTaskBO;
import com.cabletech.linepatrol.dispatchtask.services.DispatchTaskBO;
import com.cabletech.linepatrol.dispatchtask.services.ReplyTaskBO;

public class ReplyTaskAction extends BaseDispatchAction {
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
	 * 载入反馈派单任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward replyTaskForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		String dispatchId = request.getParameter("dispatch_id");
		String sendAcceptDeptId = request.getParameter("send_accept_dept_id");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		DispatchTaskBean bean = bo.viewDispatchTask(dispatchId);
		bean.setSubtaskid(sendAcceptDeptId);
		request.setAttribute("bean", bean);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("reply_wap_task");
		}
		return mapping.findForward("reply_task");
	}

	/**
	 * 执行反馈派单任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward replyTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		ReplyTaskBean bean = (ReplyTaskBean) form;
		ReplyTaskBO bo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		bo.saveReplyTask(bean, userInfo, files);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		String env = request.getParameter("env");
		if (env != null && WAP_ENV.equals(env)) {
			log(request,"派单反馈（任务主题为："+bean.getSendtopic()+"）","任务派单");
			return super.forwardWapInfoPageWithUrl(mapping, request, "110302", url);
		}
		log(request,"派单反馈（任务主题为："+bean.getSendtopic()+"）","任务派单");
		return super.forwardInfoPageWithUrl(mapping, request, "110302", url);
	}

	/**
	 * 载入修改反馈派单任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateReplyTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		String dispatchId = request.getParameter("dispatch_id");
		String replyId = request.getParameter("reply_id");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		ReplyTaskBO replyBo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		DispatchTaskBean bean = bo.viewDispatchTask(dispatchId);
		ReplyTaskBean replyBean = replyBo.viewReplyTask(replyId);
		CheckTaskBO checkBo = (CheckTaskBO) ctx.getBean("checkTaskBO");
		List taskList = checkBo.queryForCheckTask(replyId);
		bean.setSubtaskid(replyBean.getSendacceptdeptid());
		request.setAttribute("bean", bean);
		request.setAttribute("replybean", replyBean);
		request.setAttribute("check_task_list", taskList);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("reply_task_wap_mod");
		}
		return mapping.findForward("reply_task_mod");
	}

	/**
	 * 执行修改反馈派单任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateReplyTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		ReplyTaskBean bean = (ReplyTaskBean) form;
		ReplyTaskBO bo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		bo.updateReplyTask(bean, userInfo, files);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		String env = request.getParameter("env");
		if (env != null && WAP_ENV.equals(env)) {
			log(request,"派单反馈修改（任务主题为："+bean.getSendtopic()+"）","任务派单");
			return super.forwardWapInfoPageWithUrl(mapping, request, "110304", url);
		}
		log(request,"派单反馈修改（任务主题为："+bean.getSendtopic()+"）","任务派单");
		return super.forwardInfoPageWithUrl(mapping, request, "110304", url);
	}

	/**
	 * 执行删除反馈派单任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteReplyTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		ReplyTaskBean bean = (ReplyTaskBean) form;
		bean.setId(request.getParameter("reply_id"));
		ReplyTaskBO bo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		bo.deleteReplyTask(bean);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request,"删除反馈派单任务","任务派单");
		return super.forwardInfoPageWithUrl(mapping, request, "110304s1", url);
	}

	/**
	 * 载入反馈派单任务信息列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryForReplyTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String dispatchId = request.getParameter("dispatch_id");
		ReplyTaskBO bo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		List taskList = bo.queryForReplyTask(dispatchId, userInfo);
		request.setAttribute("reply_task_list", taskList);
		return mapping.findForward("reply_task_list");
	}

	/**
	 * 载入某个反馈派单任务详细信息页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewReplyTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String dispatchId = request.getParameter("dispatch_id");
		String replyId = request.getParameter("reply_id");
		String contractorId = request.getParameter("contractorId");
		DispatchTaskBO dispatchTaskBo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		ReplyTaskBO bo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		DispatchTaskBean dispatchTaskBean = dispatchTaskBo.viewDispatchTask(dispatchId);
		ReplyTaskBean bean = bo.viewReplyTask(replyId);
		bo.calculateReplyTime(dispatchTaskBean,bean);
		CheckTaskBO checkBo = (CheckTaskBO) ctx.getBean("checkTaskBO");
		List taskList = checkBo.queryForCheckTask(replyId);
		request.setAttribute("replybean", bean);
		request.setAttribute("reply_id", replyId);
		request.setAttribute("dispatch_id", dispatchId);
		request.setAttribute("check_task_list", taskList);
		request.setAttribute("contractorId", contractorId);
		return mapping.findForward("reply_task_view");
	}

}
