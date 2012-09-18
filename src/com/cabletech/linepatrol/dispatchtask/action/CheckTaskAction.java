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
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.services.CheckTaskBO;
import com.cabletech.linepatrol.dispatchtask.services.DispatchTaskBO;
import com.cabletech.linepatrol.dispatchtask.services.ReplyTaskBO;
import com.cabletech.linepatrol.dispatchtask.beans.CheckTaskBean;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchTaskBean;
import com.cabletech.linepatrol.dispatchtask.beans.ReplyTaskBean;

public class CheckTaskAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private WebApplicationContext ctx;
	private UserInfo userInfo;
	private List<FileItem> files;

	/**
	 * ִ�г�ʼ������
	 * 
	 * @param request
	 */
	public void initialize(HttpServletRequest request) {
		ctx = getWebApplicationContext();
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		files = (List) request.getSession().getAttribute("FILES");
	}

	/**
	 * ������֤�ɵ�����ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkDispatchTaskForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		String dispatchId = request.getParameter("dispatch_id");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		ReplyTaskBO replyBo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		DispatchTaskBean bean = bo.viewDispatchTask(dispatchId);
		List replyList = replyBo.queryForWaitCheckReplyTask(dispatchId,
				userInfo.getUserID());
		request.setAttribute("bean", bean);
		request.setAttribute("reply_list", replyList);
		request.setAttribute("dispatch_id", dispatchId);
		if (replyList != null && !replyList.isEmpty()) {
			if (env != null && WAP_ENV.equals(env)) {
				request.setAttribute("env", env);
				return mapping.findForward("check_task_wap_dispatch");
			} else {
				return mapping.findForward("check_task_dispatch");
			}
		} else {
			String url = (String) request.getSession().getAttribute(
					"S_BACK_URL");
			return super.forwardInfoPageWithUrl(mapping, request, "110402e1",
					url);
		}
	}

	/**
	 * �����Ķ�ĳ���ɵ���������ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkTaskReadForm(ActionMapping mapping,
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
		replyBo.calculateReplyTime(bean, replyBean);
		bean.setSubtaskid(replyBean.getSendacceptdeptid());
		CheckTaskBO checkBo = (CheckTaskBO) ctx.getBean("checkTaskBO");
		List taskList = checkBo.queryForCheckTask(replyId);
		request.setAttribute("bean", bean);
		request.setAttribute("dispatch_id", dispatchId);
		request.setAttribute("replybean", replyBean);
		request.setAttribute("check_task_list", taskList);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("check_task_wap_read");
		}
		return mapping.findForward("check_task_read");
	}

	/**
	 * ����ת��ĳ���ɵ���������ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkTaskTransferForm(ActionMapping mapping,
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
		replyBo.calculateReplyTime(bean, replyBean);
		bean.setSubtaskid(replyBean.getSendacceptdeptid());
		CheckTaskBO checkBo = (CheckTaskBO) ctx.getBean("checkTaskBO");
		List taskList = checkBo.queryForCheckTask(replyId);
		request.setAttribute("bean", bean);
		request.setAttribute("dispatch_id", dispatchId);
		request.setAttribute("replybean", replyBean);
		request.setAttribute("check_task_list", taskList);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("check_task_wap_transfer");
		}
		return mapping.findForward("check_task_transfer");
	}

	/**
	 * ������֤ĳ���ɵ���������ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkTaskForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		String dispatchId = request.getParameter("dispatch_id");
		String replyId = request.getParameter("reply_id");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		ReplyTaskBO replyBo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		DispatchTaskBean bean = bo.viewDispatchTask(dispatchId);
		ReplyTaskBean replyBean = replyBo.viewReplyTask(replyId);
		replyBo.calculateReplyTime(bean, replyBean);
		bean.setSubtaskid(replyBean.getSendacceptdeptid());
		CheckTaskBO checkBo = (CheckTaskBO) ctx.getBean("checkTaskBO");
		List taskList = checkBo.queryForCheckTask(replyId);
		request.setAttribute("bean", bean);
		request.setAttribute("dispatch_id", dispatchId);
		request.setAttribute("replybean", replyBean);
		request.setAttribute("check_task_list", taskList);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("check_wap_task");
		}
		return mapping.findForward("check_task");
	}

	/**
	 * ִ����֤�ɵ�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		String dispatchId = request.getParameter("dispatch_id");
		CheckTaskBean bean = (CheckTaskBean) form;
		ReplyTaskBO replyBo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		CheckTaskBO bo = (CheckTaskBO) ctx.getBean("checkTaskBO");
		bo.checkTask(bean, dispatchId, userInfo, files);
		List replyList = replyBo.queryForWaitCheckReplyTask(dispatchId,
				userInfo.getUserID());
		String url = request.getContextPath()
				+ "/dispatchtask/check_task.do?method=checkDispatchTaskForm&&env="
				+ env + "&dispatch_id=" + dispatchId;
		if (replyList == null || replyList.isEmpty()) {
			url = (String) request.getSession().getAttribute("S_BACK_URL");
		}
		if (env != null && WAP_ENV.equals(env)) {
			if (DispatchTaskConstant.NOT_PASSED
					.equals(bean.getValidateresult())) {
				log(request,"��֤δͨ���ɵ�������������Ϊ��"+bean.getSendtopic()+"��","�����ɵ�");
				return super.forwardWapInfoPageWithUrl(mapping, request,
						"11040201", url);
			}
			if (DispatchTaskConstant.TRANFER_APPROVE.equals(bean
					.getValidateresult())) {
				log(request,"ת���ɵ�������������Ϊ��"+bean.getSendtopic()+"��","�����ɵ�");
				return super.forwardWapInfoPageWithUrl(mapping, request,
						"11040202", url);
			}
			if (DispatchTaskConstant.PASSED.equals(bean.getValidateresult())) {
				log(request,"��֤δͨ���ɵ�������������Ϊ��"+bean.getSendtopic()+"��","�����ɵ�");
				return super.forwardWapInfoPageWithUrl(mapping, request,
						"110402", url);
			}
		}
		if (DispatchTaskConstant.NOT_PASSED.equals(bean.getValidateresult())) {
			log(request,"��֤δͨ���ɵ�������������Ϊ��"+bean.getSendtopic()+"��","�����ɵ�");
			return super.forwardInfoPageWithUrl(mapping, request, "11040201",
					url);
		}
		if (DispatchTaskConstant.TRANFER_APPROVE.equals(bean
				.getValidateresult())) {
			log(request,"ת���ɵ�������������Ϊ��"+bean.getSendtopic()+"��","�����ɵ�");
			return super.forwardInfoPageWithUrl(mapping, request, "11040202",
					url);
		}
		if (DispatchTaskConstant.PASSED.equals(bean.getValidateresult())) {
			log(request,"��֤ͨ���ɵ�������������Ϊ��"+bean.getSendtopic()+"��","�����ɵ�");
			return super
					.forwardInfoPageWithUrl(mapping, request, "110402", url);
		}
		return super.forwardErrorPageWithUrl(mapping, request, "110402e", url);
	}

	/**
	 * ִ����֤�ɵ�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkReadTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String env = request.getParameter("env");
		String dispatchId = request.getParameter("dispatch_id");
		CheckTaskBean bean = (CheckTaskBean) form;
		ReplyTaskBO replyBo = (ReplyTaskBO) ctx.getBean("replyTaskBO");
		CheckTaskBO bo = (CheckTaskBO) ctx.getBean("checkTaskBO");
		bo.checkReadTask(bean, dispatchId, userInfo, files);
		String url = request.getContextPath()
				+ "/dispatchtask/check_task.do?method=checkDispatchTaskForm&&env="
				+ env + "&dispatch_id=" + dispatchId;
		List replyList = replyBo.queryForWaitCheckReplyTask(dispatchId,
				userInfo.getUserID());
		if (replyList == null || replyList.isEmpty()) {
			url = (String) request.getSession().getAttribute("S_BACK_URL");
		}
		if (env != null && WAP_ENV.equals(env)) {
			return super.forwardWapInfoPageWithUrl(mapping, request,
					"11040203", url);
		}
		return super.forwardInfoPageWithUrl(mapping, request, "11040203", url);
	}

	/**
	 * ���뷴���ɵ�������Ϣ�б�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryForCheckTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String replyId = request.getParameter("reply_id");
		CheckTaskBO bo = (CheckTaskBO) ctx.getBean("checkTaskBO");
		List taskList = bo.queryForCheckTask(replyId);
		request.setAttribute("check_task_list", taskList);
		logger.info("taskList:" + taskList);
		return mapping.findForward("check_task_list");
	}

	/**
	 * ����ĳ����֤�ɵ�������ϸ��Ϣҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewCheckTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String dispatchId = request.getParameter("dispatch_id");
		String checkId = request.getParameter("check_id");
		CheckTaskBO bo = (CheckTaskBO) ctx.getBean("checkTaskBO");
		CheckTaskBean bean = bo.viewCheckTask(checkId);
		request.setAttribute("checkbean", bean);
		request.setAttribute("dispatch_id", dispatchId);
		return mapping.findForward("check_task_view");
	}

}
