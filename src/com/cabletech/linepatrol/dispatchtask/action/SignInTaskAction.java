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
import com.cabletech.linepatrol.dispatchtask.beans.SignInTaskBean;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.services.DispatchTaskBO;
import com.cabletech.linepatrol.dispatchtask.services.SignInTaskBO;
import com.cabletech.linepatrol.dispatchtask.workflow.DispatchTaskWorkflowBO;

public class SignInTaskAction extends BaseDispatchAction {
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
	 * 载入签收派单任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward signInTaskForm(ActionMapping mapping, ActionForm form,
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
			return mapping.findForward("sign_in_wap_task");
		}
		return mapping.findForward("sign_in_task");
	}

	/**
	 * 载入转派派单任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transferDispatchTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			return mapping.findForward("transfer_dispatch_wap_task");
		}
		return mapping.findForward("transfer_dispatch_task");
	}

	/**
	 * 载入拒签派单确认列表查看任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refuseDispatchTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		String dispatchId = request.getParameter("dispatch_id");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		SignInTaskBO signInBo = (SignInTaskBO) ctx.getBean("signInTaskBO");
		DispatchTaskBean bean = bo.viewDispatchTask(dispatchId);
		List signInList = signInBo.queryForWaitRefuseConfirmTask(dispatchId,
				userInfo.getUserID());
		request.setAttribute("bean", bean);
		request.setAttribute("dispatch_id", dispatchId);
		request.setAttribute("sign_in_list", signInList);
		if (signInList != null && !signInList.isEmpty()) {
			if (env != null && WAP_ENV.equals(env)) {
				request.setAttribute("env", env);
				return mapping.findForward("refuse_confirm_task_wap_dispatch");
			} else {
				return mapping.findForward("refuse_confirm_task_dispatch");
			}
		} else {
			String url = (String) request.getSession().getAttribute(
					"S_BACK_URL");
			return super.forwardInfoPageWithUrl(mapping, request, "110203e1",
					url);
		}
	}

	/**
	 * 载入拒签派单确认任务页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refuseConfirmTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		String dispatchId = request.getParameter("dispatch_id");
		String signInId = request.getParameter("sign_in_id");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		SignInTaskBO signInBo = (SignInTaskBO) ctx.getBean("signInTaskBO");
		DispatchTaskBean bean = bo.viewDispatchTask(dispatchId);
		SignInTaskBean signInBean = signInBo.viewSignInTask(signInId);
		bean.setSubtaskid(signInBean.getSendacceptdeptid());
		request.setAttribute("bean", bean);
		request.setAttribute("dispatch_id", dispatchId);
		request.setAttribute("endorsebean", signInBean);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("refuse_confirm_wap_task");
		}
		return mapping.findForward("refuse_confirm_task");
	}

	/**
	 * 执行签收派单任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward signInTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		SignInTaskBean bean = (SignInTaskBean) form;
		SignInTaskBO bo = (SignInTaskBO) ctx.getBean("signInTaskBO");
		bo.signInTask(bean, userInfo, files);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if (env != null && WAP_ENV.equals(env)) {
			if (DispatchTaskConstant.IS_TRANSFER.equals(bean.getIsTransfer())) {
				log(request,"转签签收派单（任务主题为："+bean.getSendtopic()+"）","任务派单");
				return super
						.forwardWapInfoPageWithUrl(mapping, request, "110301", url);
			} else if (DispatchTaskConstant.REFUSE_ACTION.equals(bean.getResult())) {
				if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(bean
						.getSignInState())) {
					log(request,"签收派单（任务主题为："+bean.getSendtopic()+"）","任务派单");
					return super.forwardWapInfoPageWithUrl(mapping, request,
							"110203s0", url);
				} else {
					log(request,"签收派单（任务主题为："+bean.getSendtopic()+"）","任务派单");
					return super.forwardWapInfoPageWithUrl(mapping, request,
							"110203s", url);
				}
			} else {
				log(request,"签收派单（任务主题为："+bean.getSendtopic()+"）","任务派单");
				return super
						.forwardWapInfoPageWithUrl(mapping, request, "110202", url);
			}
		}
		if (DispatchTaskConstant.IS_TRANSFER.equals(bean.getIsTransfer())) {
			log(request,"转签签收派单（任务主题为："+bean.getSendtopic()+"）","任务派单");
			return super
					.forwardInfoPageWithUrl(mapping, request, "110301", url);
		} else if (DispatchTaskConstant.REFUSE_ACTION.equals(bean.getResult())) {
			if (DispatchTaskWorkflowBO.SIGN_IN_TASK.equals(bean
					.getSignInState())) {
				log(request,"签收派单（任务主题为："+bean.getSendtopic()+"）","任务派单");
				return super.forwardInfoPageWithUrl(mapping, request,
						"110203s0", url);
			} else {
				log(request,"签收派单（任务主题为："+bean.getSendtopic()+"）","任务派单");
				return super.forwardInfoPageWithUrl(mapping, request,
						"110203s", url);
			}
		} else {
			log(request,"签收派单（任务主题为："+bean.getSendtopic()+"）","任务派单");
			return super
					.forwardInfoPageWithUrl(mapping, request, "110202", url);
		}
	}

	/**
	 * 执行拒签派单确认任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward refuseConfirmTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		String dispatchId = request.getParameter("dispatch_id");
		SignInTaskBean bean = (SignInTaskBean) form;
		SignInTaskBO bo = (SignInTaskBO) ctx.getBean("signInTaskBO");
		bo.refuseConfirmTask(bean, userInfo);
		List signInList = bo.queryForWaitRefuseConfirmTask(dispatchId, userInfo
				.getUserID());
		String url = request.getContextPath()
				+ "/dispatchtask/sign_in_task.do?method=refuseDispatchTaskForm&&env="
				+ env + "&dispatch_id=" + dispatchId;
		if (signInList == null || signInList.isEmpty()) {
			url = (String) request.getSession().getAttribute("S_BACK_URL");
		}
		if (env != null && WAP_ENV.equals(env)) {
			if (DispatchTaskConstant.NOT_PASSED.equals(bean.getConfirmResult())) {
				log(request,"拒签确认未通过（任务主题为："+bean.getSendtopic()+"）","任务派单");
				return super.forwardWapInfoPageWithUrl(mapping, request,
						"110203s2", url);
			}
			if (DispatchTaskConstant.PASSED.equals(bean.getConfirmResult())) {
				log(request,"拒签确认通过（任务主题为："+bean.getSendtopic()+"）","任务派单");
				return super.forwardWapInfoPageWithUrl(mapping, request,
						"110203s1", url);
			}
		}
		if (DispatchTaskConstant.NOT_PASSED.equals(bean.getConfirmResult())) {
			log(request,"拒签确认未通过（任务主题为："+bean.getSendtopic()+"）","任务派单");
			return super.forwardInfoPageWithUrl(mapping, request, "110203s2",
					url);
		}
		if (DispatchTaskConstant.PASSED.equals(bean.getConfirmResult())) {
			log(request,"拒签确认通过（任务主题为："+bean.getSendtopic()+"）","任务派单");
			return super.forwardInfoPageWithUrl(mapping, request, "110203s1",
					url);
		}
		return mapping.findForward("");
	}

	/**
	 * 载入签收派单任务信息列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryForSignInTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String subTaskId = request.getParameter("sub_id");
		String dispatchId = request.getParameter("dispatch_id");
		SignInTaskBO bo = (SignInTaskBO) ctx.getBean("signInTaskBO");
		List taskList = bo.queryForSignTask(dispatchId, subTaskId, userInfo);
		request.setAttribute("sign_in_task_list", taskList);
		return mapping.findForward("sign_in_task_list");
	}

	/**
	 * 载入拒签派单确认任务信息列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryForRefuseConfirmTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String signInId = request.getParameter("sign_in_id");
		SignInTaskBO bo = (SignInTaskBO) ctx.getBean("signInTaskBO");
		List taskList = bo.queryForRefuseConfirmTask(signInId);
		request.setAttribute("refuse_confirm_task_list", taskList);
		return mapping.findForward("refuse_confirm_task_list");
	}

	/**
	 * 载入某个签收派单任务详细信息页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewSignInTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String dispatchId = request.getParameter("dispatch_id");
		String signInId = request.getParameter("sign_in_id");
		SignInTaskBO bo = (SignInTaskBO) ctx.getBean("signInTaskBO");
		SignInTaskBean bean = bo.viewSignInTask(signInId);
		SignInTaskBean refuseConfirmBean = bo
				.viewRefuseConfirmTaskBySignInId(signInId);
		request.setAttribute("endorsebean", bean);
		request.setAttribute("refuseConfirmBean", refuseConfirmBean);
		request.setAttribute("dispatch_id", dispatchId);
		return mapping.findForward("sign_in_task_view");
	}

	/**
	 * 载入某个拒签派单确认任务详细信息页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewRefuseConfirmTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String dispatchId = request.getParameter("dispatch_id");
		String refuseId = request.getParameter("refuse_id");
		SignInTaskBO bo = (SignInTaskBO) ctx.getBean("signInTaskBO");
		SignInTaskBean bean = bo.viewRefuseConfirmTask(refuseId);
		request.setAttribute("refusebean", bean);
		request.setAttribute("dispatch_id", dispatchId);
		return mapping.findForward("refuse_confirm_task_view");
	}

}
