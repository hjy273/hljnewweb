package com.cabletech.linepatrol.drill.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.drill.beans.DrillPlanModifyBean;
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillPlanModify;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.services.DrillPlanModifyBo;
import com.cabletech.linepatrol.drill.services.DrillTaskBo;

public class DrillPlanModifyAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(DrillPlanModifyAction.class
			.getName());

	private DrillPlanModifyBo getDrillPlanModifyBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (DrillPlanModifyBo) ctx.getBean("drillPlanModifyBo");
	}

	public ActionForward getApproveModifyList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String planId = request.getParameter("planId");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String deptype = userInfo.getDeptype();
		String dept = userInfo.getDeptID();
		String condition = " and mod.plan_id='" + planId + "'";
		if ("2".equals(deptype)) {
			condition += " and taskcon.contractor_id='" + dept + "' ";
		}
		String taskName = request.getParameter("task_name");
		List list = getDrillPlanModifyBo().getAgentList(userInfo, condition,
				taskName);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("agentList");
	}

	/**
	 * ��÷�������б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getModifyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String condition = "";
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String deptype = userInfo.getDeptype();
		if ("2".equals(deptype)) {
			condition += " and taskCon.Contractor_Id='" + userInfo.getDeptID()
					+ "' ";
		}
		List list = getDrillPlanModifyBo().getQueryList(condition);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("drillEndQueryList");
	}

	/**
	 * �����������ǰ����������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDrillPlanModifyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String planId = request.getParameter("planId");
		Map map = getDrillPlanModifyBo().addDrillPlanModifyForm(planId);
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("drillTask", drillTask);
		return mapping.findForward("addDrillPlanModify");
	}

	/**
	 * ����������ID�鿴�����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewDrillPlanModifyByPlanId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String planId = request.getParameter("planId");
		List list = getDrillPlanModifyBo().viewDrillPlanModifyByPlanId(planId);
		request.setAttribute("list", list);
		return mapping.findForward("viewDrillPlanModifyByPlanId");
	}

	/**
	 * ��������������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDrillPlanModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		DrillTaskBo drillTaskBo=(DrillTaskBo)ctx.getBean("drillTaskBo");
		DrillPlanModifyBean drillPlanModifyBean = (DrillPlanModifyBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		try {
			getDrillPlanModifyBo().addDrillPlanModify(drillPlanModifyBean,
					userInfo);
			String taskId=drillPlanModifyBean.getTaskId();
			String name=drillTaskBo.get(taskId).getName();
			log(request,"����������������"+name+"��","��������");
			return forwardInfoPage(mapping, request,
					"addDrillPlanModifySuccess");
		} catch (ServiceException e) {
			logger.info("���������������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "addDrillPlanModifyError");
		}
	}

	/**
	 * �鿴�����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewDrillPlanModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		String planModifyId = request.getParameter("planModifyId");
		String isread = request.getParameter("isread");
		Map map = getDrillPlanModifyBo().approveDrillPlanModifyForm(
				planModifyId);
		DrillPlanModify drillPlanModify = (DrillPlanModify) map
				.get("drillPlanModify");
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		String conId = (String)map.get("conId");
		List list = (List) map.get("list");
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlanModify", drillPlanModify);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("list", list);
		request.setAttribute("isread", isread);
		request.setAttribute("env", env);
		request.setAttribute("conId", conId);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("read_drill_plan_modify_wap_task");
		}
		return mapping.findForward("viewDrillPlanModify");
	}

	/**
	 * ���������������ǰ����������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveDrillPlanModifyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		String planModifyId = request.getParameter("planModifyId");
		String operator = request.getParameter("operator");
		Map map = getDrillPlanModifyBo().approveDrillPlanModifyForm(
				planModifyId);
		DrillPlanModify drillPlanModify = (DrillPlanModify) map
				.get("drillPlanModify");
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan) map.get("drillPlan");
		List list = (List) map.get("list");
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlanModify", drillPlanModify);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("operator", operator);
		request.setAttribute("list", list);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("approve_drill_plan_modify_wap_task");
		}
		return mapping.findForward("approveDrillPlanModify");
	}

	/**
	 * ���������������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveDrillPlanModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		DrillTaskBo drillTaskBo=(DrillTaskBo)ctx.getBean("drillTaskBo");
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		String env = request.getParameter("env");
		DrillPlanModifyBean drillPlanModifyBean = (DrillPlanModifyBean) form;
		String approveResult = drillPlanModifyBean.getApproveResult();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String taskId=drillPlanModifyBean.getTaskId();
		String name=drillTaskBo.get(taskId).getName();
		try {
			getDrillPlanModifyBo().approveDrillPlanModify(drillPlanModifyBean,
					userInfo);
			if (env != null && WAP_ENV.equals(env)) {
				if ("0".equals(approveResult)) {
					log(request,"���δͨ�������������������Ϊ��"+name+"��","��������");
					return forwardWapInfoPageWithUrl(mapping, request,
							"approveDrillPlanModifyUnpass", url);
				} else if ("1".equals(approveResult)) {
					log(request,"���ͨ�������������������Ϊ��"+name+"��","��������");
					return forwardWapInfoPageWithUrl(mapping, request,
							"approveDrillPlanModifyPass", url);
				} else {
					log(request,"ת�������������������Ϊ��"+name+"��","��������");
					return forwardWapInfoPageWithUrl(mapping, request,
							"approveDrillPlanModifyTransfer", url);
				}
			}
			if ("0".equals(approveResult)) {
				log(request,"���δͨ�������������������Ϊ��"+name+"��","��������");
				return forwardInfoPage(mapping, request,
						"approveDrillPlanModifyUnpass");
			} else if ("1".equals(approveResult)) {
				log(request,"���ͨ�������������������Ϊ��"+name+"��","��������");
				return forwardInfoPage(mapping, request,
						"approveDrillPlanModifyPass");
			} else {
				log(request,"ת�������������������Ϊ��"+name+"��","��������");
				return forwardInfoPage(mapping, request,
						"approveDrillPlanModifyTransfer");
			}
		} catch (ServiceException e) {
			logger.error("�������������˳���������Ϣ��" + e.getMessage());
			if (env != null && WAP_ENV.equals(env)) {
				return forwardWapErrorPageWithUrl(mapping, request,
						"approveDrillPlanModifyError", url);
			}
			return forwardErrorPage(mapping, request,
					"approveDrillPlanModifyError");
		}
	}

	/**
	 * ���������������
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
		String modifyId = request.getParameter("modifyId");
		getDrillPlanModifyBo().readReply(userInfo, approverId, modifyId);
		if (env != null && WAP_ENV.equals(env)) {
			return forwardWapInfoPageWithUrl(mapping, request,
					"drillPlanModifyReadReplySuccess", url);
		}
		return forwardInfoPage(mapping, request,
				"drillPlanModifyReadReplySuccess");
	}
}
