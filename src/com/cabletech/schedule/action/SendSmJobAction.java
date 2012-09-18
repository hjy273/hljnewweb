package com.cabletech.schedule.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.schedule.bean.SendSmJobInfoBean;
import com.cabletech.schedule.module.SendSmJobInfo;
import com.cabletech.schedule.service.SendSmJobBO;
import com.cabletech.sysmanage.services.SendMessageBO;

public class SendSmJobAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(SendSmJobAction.class
			.getName());

	private SendSmJobBO getBusinessService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (SendSmJobBO) ctx.getBean("sendSmJobBO");
	}

	/**
	 * 执行取消定时任务信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward cancelSchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		String scheduleName = request.getParameter("schedule_name");
		WebApplicationContext ctx = getWebApplicationContext();
		SendMessageBO sendMessageBo = (SendMessageBO) ctx
				.getBean("sendMessageBO");
		sendMessageBo.cancelSendMessageScheduleByName(scheduleName);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		return forwardInfoPageWithUrl(mapping, request, "75101s", url);
	}

	/**
	 * 载入查询定时任务表单页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("schedule_job_query_form");
	}

	/**
	 * 执行根据输入条件查询定时任务信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryForList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		String sendType = request.getParameter("send_type");
		String beginTime = request.getParameter("begin_time");
		String endTime = request.getParameter("end_time");
		SendSmJobBO bo = getBusinessService();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List list = bo.findByCondition(userInfo, sendType, beginTime, endTime);
		request.getSession().setAttribute("DATA_LIST", list);
		return mapping.findForward("schedule_job_list");
	}

	/**
	 * 执行查看定时任务信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward viewScheduleInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		SendSmJobBO bo = getBusinessService();
		SendSmJobInfoBean job = bo.viewSendSmJobInfo(id);
		request.setAttribute("job_detail", job);
		return mapping.findForward("schedule_job_view");
	}
}
