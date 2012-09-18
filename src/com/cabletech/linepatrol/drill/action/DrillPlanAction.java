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
import com.cabletech.linepatrol.drill.beans.DrillPlanBean;
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.services.DrillPlanBo;
import com.cabletech.linepatrol.drill.services.DrillTaskBo;

public class DrillPlanAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(DrillPlanAction.class
			.getName());

	private DrillPlanBo getDrillPlanBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (DrillPlanBo) ctx.getBean("drillPlanBo");
	}

	/**
	 * �����������ǰ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDrillPlanForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String taskId = request.getParameter("taskId");
		DrillTask drillTask = null;
		String[] approveInfo = null;
		DrillPlan drillPlan = null;
		// ����taskId�Ƿ�Ϊ���ж��Ƿ����ƶ���������
		// ��taskIdΪ��ʱ����ʾ��ά��Ա�Զ�����������
		if (taskId != null && !"".equals(taskId)) {
			Map map = getDrillPlanBo().getDrillTaskByTaskId(taskId,
					userInfo.getDeptID());
			drillTask = (DrillTask) map.get("drillTask");
			approveInfo = (String[]) map.get("approveInfo");
			drillPlan = (DrillPlan) map.get("drillPlan");
		}
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("approveInfo", approveInfo);
		request.setAttribute("flag", "tempSave");
		if (drillPlan != null) {
			return mapping.findForward("editDrillPlan");
		}
		return mapping.findForward("addDrillPlan");
	}

	/**
	 * �����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDrillPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String tempFlag = request.getParameter("tempFlag");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		DrillPlanBean drillPlanBean = (DrillPlanBean) form;
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		try {
			if ("0".equals(tempFlag)) {
				getDrillPlanBo().addDrillPlan(drillPlanBean, userInfo, files);
				log(request,"���������������������Ϊ��"+drillPlanBean.getName()+"��","��������");
				return forwardInfoPage(mapping, request, "addDrillPlanSuccess");
			} else {
				getDrillPlanBo().tempSaveDrillPlan(drillPlanBean, userInfo,
						files);
				log(request,"�ݴ�������������������Ϊ��"+drillPlanBean.getName()+"��","��������");
				return forwardInfoPage(mapping, request,
						"tempSaveDrillPlanSuccess");
			}
		} catch (ServiceException e) {
			logger.info("��������ƻ�����������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "addDrillPlanError");
		}
	}

	/**
	 * �鿴��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewDrillPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String env = request.getParameter("env");
		String planId = request.getParameter("planId");
		String isread = request.getParameter("isread");
		Map map = getDrillPlanBo().getTaskAndPlan(planId);
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		String conId = (String)map.get("conId");
		List planEndList = (List) map.get("planEndList");
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("list", planEndList);
		request.setAttribute("isread", isread);
		request.setAttribute("conId", conId);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("read_drill_plan_wap_task");
		}
		return mapping.findForward("viewDrillPlan");
	}

	/**
	 * ������������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward readReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		DrillTaskBo drillTaskBo=(DrillTaskBo)ctx.getBean("drillTaskBo");
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		String env = request.getParameter("env");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String approverId = userInfo.getUserID();
		String planId = request.getParameter("planId");
		getDrillPlanBo().readReply(userInfo, approverId, planId);
		if (env != null && WAP_ENV.equals(env)) {
			return forwardWapInfoPageWithUrl(mapping, request,
					"drillPlanReadReplySuccess", url);
		}
		String taskid=getDrillPlanBo().get(planId).getTaskId();
		String name=drillTaskBo.get(taskid).getName();
		log(request,"����������������������Ϊ��"+name+"��","��������");
		return forwardInfoPage(mapping, request, "drillPlanReadReplySuccess");
	}

	/**
	 * �޸���������ǰ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editDrillPlanForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String userId = userInfo.getUserID();
		String planId = request.getParameter("planId");
		Map map = getDrillPlanBo().getTaskAndPlan(planId);
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		// ApproveInfo approveInfo = (ApproveInfo)map.get("approveInfo");
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("userId", userId);
		// request.setAttribute("approveInfo", approveInfo);
		return mapping.findForward("editDrillPlan");
	}

	/**
	 * �޸���������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editDrillPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String taskCreator = request.getParameter("taskCreator");
		String flag = request.getParameter("flag");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		DrillPlanBean drillPlanBean = (DrillPlanBean) form;
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		String tempFlag = request.getParameter("tempFlag");
		try {
			if ("0".equals(tempFlag)) {
				getDrillPlanBo().editDrillPlan(drillPlanBean, userInfo,
						taskCreator, files);
			} else {
				getDrillPlanBo().tempSaveDrillPlan(drillPlanBean, userInfo,
						files);
				log(request,"�ݴ�������������������Ϊ��"+drillPlanBean.getName()+"��","��������");
				return forwardInfoPage(mapping, request,
						"tempSaveDrillPlanSuccess");
			}
			if (flag != null) {
				log(request,"�޸�������������������Ϊ��"+drillPlanBean.getName()+"��","��������");
				return forwardInfoPage(mapping, request, "addDrillPlanSuccess");
			}
			return forwardInfoPage(mapping, request, "editDrillPlanSuccess");
		} catch (ServiceException e) {
			logger.info("�༭�����ƻ�����������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "editDrillPlanError");
		}
	}

	/**
	 * ������������ǰ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveDrillPlanForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		String planId = request.getParameter("planId");
		String operator = request.getParameter("operator");
		Map map = getDrillPlanBo().getTaskAndPlan(planId);
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		List planEndList = (List) map.get("planEndList");
		long planCreateTime = drillPlan.getCreateTime().getTime();
		Date deadline = drillTask.getDeadline();
		long planDeadline = 0;
		if (deadline != null) {
			planDeadline = deadline.getTime();
		}
		double days = (planCreateTime - planDeadline) / 1000.0 / 60.0 / 60.0;
		if (days < 0.0) {
			days = 0.0 - days;
		}
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("planEndList", planEndList);
		request.setAttribute("operator", operator);
		request.setAttribute("days", days);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("approve_drill_plan_wap_task");
		}
		return mapping.findForward("approveDrillPlan");
	}

	/**
	 * ������������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveDrillPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		DrillTaskBo dtb=(DrillTaskBo)ctx.getBean("drillTaskBo");
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		String env = request.getParameter("env");
		DrillPlanBean drillPlanBean = (DrillPlanBean) form;
		String approveResult = drillPlanBean.getApproveResult();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String taskId=drillPlanBean.getTaskId();
		String name=dtb.get(taskId).getName();
		try {
			getDrillPlanBo().approveDrillPlan(drillPlanBean, userInfo);
			if (env != null && WAP_ENV.equals(env)) {
				if ("0".equals(approveResult)) {
					log(request,"����δͨ������������"+name+"��","��������");
					return forwardWapInfoPageWithUrl(mapping, request,
							"approveDrillPlanUnpass", url);
				} else if ("1".equals(approveResult)) {
					log(request,"����ͨ������������"+name+"��","��������");
					return forwardWapInfoPageWithUrl(mapping, request,
							"approveDrillPlanPass", url);
				} else {
					log(request,"ת������������"+name+"��","��������");
					return forwardWapInfoPageWithUrl(mapping, request,
							"approveDrillPlanTransfer", url);
				}
			}
			if ("0".equals(approveResult)) {
				log(request,"����δͨ������������"+name+"��","��������");
				return forwardInfoPage(mapping, request,
						"approveDrillPlanUnpass");
			} else if ("1".equals(approveResult)) {
				log(request,"����ͨ������������"+name+"��","��������");
				return forwardInfoPage(mapping, request, "approveDrillPlanPass");
			} else {
				log(request,"ת������������"+name+"��","��������");
				return forwardInfoPage(mapping, request,
						"approveDrillPlanTransfer");
			}
		} catch (ServiceException e) {
			logger.error("�����ƻ���˳���������Ϣ��" + e.getMessage());
			if (env != null && WAP_ENV.equals(env)) {
				return forwardWapErrorPageWithUrl(mapping, request,
						"approveDrillPlanError", url);
			}
			return forwardErrorPage(mapping, request, "approveDrillPlanError");
		}
	}
}
