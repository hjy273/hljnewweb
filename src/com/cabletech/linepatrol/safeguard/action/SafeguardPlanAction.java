package com.cabletech.linepatrol.safeguard.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.safeguard.beans.SafeguardPlanBean;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.services.SafeguardPlanBo;
import com.cabletech.linepatrol.safeguard.services.SafeguardTaskBo;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;

public class SafeguardPlanAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(SafeguardPlanAction.class.getName());

	private SafeguardPlanBo getSafeguardPlanBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (SafeguardPlanBo) ctx.getBean("safeguardPlanBo");
	}

	private void SetDataToRequest(HttpServletRequest request, Map map) {
		SafeguardTask safeguardTask = null;//��������
		SafeguardPlan safeguardPlan = null;//���Ϸ���
		String sublineIds = null;//�м̶�
		List safeguardSps = null;//���Ϸ�������Ѳ�ƻ�����
		List specialPlans = null;//��Ѳ�ƻ��б�

		Object safeguardTaskObj = map.get("safeguardTask");
		Object safeguardPlanObj = map.get("safeguardPlan");
		Object sublineIdsObj = map.get("sublineIds");
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		String[] approveInfo = (String[]) map.get("approveInfo");
		String approveMan = (String) map.get("approveMan");

		if (safeguardTaskObj != null) {
			safeguardTask = (SafeguardTask) safeguardTaskObj;
		}
		if (safeguardPlanObj != null) {
			safeguardPlan = (SafeguardPlan) safeguardPlanObj;
		}
		if (sublineIdsObj != null) {
			sublineIds = (String) sublineIdsObj;
		}
		if (safeguardSpObj != null) {
			safeguardSps = (List) safeguardSpObj;
		}
		if (specialPlanObj != null) {
			specialPlans = (List) specialPlanObj;
		}
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("approveInfo", approveInfo);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("approveMan", approveMan);
	}

	/**
	 * ��ӱ��Ϸ���ǰ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addSafeguardPlanForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskId = request.getParameter("taskId");
		Map map = getSafeguardPlanBo().addSafeguardPlanForm(taskId, userInfo);
		String existFlag = (String) map.get("existFlag");
		request.setAttribute("saveoreditflag", "save");
		SetDataToRequest(request, map);
		if (existFlag.equals("old")) {
			return mapping.findForward("editSafeguardPlan");
		}
		return mapping.findForward("addSafeguardPlan");
	}

	/**
	 * ������Ѳ�ƻ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward loadDataSpPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String id = request.getParameter("businessId");
		List<SpecialPlan> list = getSafeguardPlanBo().findSpPlanBySafeguardId(id);
		StringBuffer buf = new StringBuffer("");
		if (list != null && list.size() > 0) {
			buf.append("<table class='tablelist'>");
			for (SpecialPlan plan : list) {
				buf.append("<tr class='trcolor'>");
				buf.append("<td class='tablelisttd'>");
				buf.append(plan.getPlanName());
				buf.append("</td>");
				buf.append("<td class='tablelisttd'>");
				buf.append(sdf.format(plan.getStartDate()));
				buf.append(" -- ");
				buf.append(sdf.format(plan.getEndDate()));
				buf.append("</td>");
				buf.append("<td class='tablelisttd'>");
				buf.append(plan.getPatrolGroupId());
				buf.append("</td>");
				buf.append("<td class='tablelisttd'>");
				buf.append("<a onclick=editSpecPlan('");
				buf.append(id);
				buf.append("','");
				buf.append(plan.getId());
				buf.append("') style='cursor: pointer; color: blue;'>");
				buf.append("�޸�");
				buf.append("</a>");
				buf.append("</td>");
				buf.append("<td class='tablelisttd'>");
				buf.append("<a onclick=deleteProblem('");
				buf.append(id);
				buf.append("','");
				buf.append(plan.getId());
				buf.append("') style='cursor: pointer; color: blue;'>");
				buf.append("ɾ��");
				buf.append("</a>");
				buf.append("</td>");
				buf.append("</tr>");
			}
			buf.append("</table>");
		}
		super.outPrint(response, buf.toString());
		System.out.println("*******buf.toString():" + buf.toString());
		return null;
	}

	/**
	 * ��ӱ��Ϸ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward addSafeguardPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		SafeguardTaskBo stb = (SafeguardTaskBo) ctx.getBean("safeguardTaskBo");
		SafeguardPlanBean safeguardPlanBean = (SafeguardPlanBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String[] spplanIds = request.getParameterValues("spplanValue");
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		String isAllNet = request.getParameter("isAllNet");//�Ƿ�Ϊȫ������
		List<String> trunkList = null;

		String taskId = safeguardPlanBean.getSafeguardId();
		String name = stb.get(taskId).getSafeguardName();
		//���Ϊȫ�����ϲ���Ҫ�����м̶���Ϣ��
		if (!SafeguardPlan.IS_ALL_NET.equals(isAllNet)) {
			// ����м̶�id
			String trunks = request.getParameter("trunk");
			trunkList = new ArrayList<String>();
			if (StringUtils.isNotBlank(trunks)) {
				trunkList = Arrays.asList(trunks.split(","));
			}
		}
		String saveflag = request.getParameter("saveflag");

		try {
			if ("0".equals(saveflag)) {
				getSafeguardPlanBo().addSafeguardPlan(safeguardPlanBean, userInfo, trunkList, files, spplanIds);
				log(request, "��ӱ��Ϸ�������������Ϊ��" + name + "��", "���Ϲ���");
				return forwardInfoPage(mapping, request, "addSafeguardPlanSuccess");
			} else {
				getSafeguardPlanBo().tempSaveSafeguardPlan(safeguardPlanBean, userInfo, trunkList, files, spplanIds);
				log(request, "�ݴ汣�Ϸ�������������Ϊ��" + name + "��", "���Ϲ���");
				return forwardInfoPage(mapping, request, "tempSaveSafeguardPlanSuccess");
			}
		} catch (ServiceException e) {
			logger.error("��ӱ��Ϸ�������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "addSafeguardPlanError");
		}
	}

	/**
	 * �鿴���Ϸ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewSafeguardPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String flag = request.getParameter("flag");
		String planId = request.getParameter("planId");
		String isread = request.getParameter("isread");
		Map map = getSafeguardPlanBo().viewSafeguardPlanData(planId);
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		String sublineIds = (String) map.get("sublineIds");
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		List list = null;
		Object list2 = map.get("list");
		if (list2 != null && !"".equals(list2)) {
			list = (List) list2;
		}
		String conId = (String) map.get("conId");
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if (safeguardSpObj != null) {
			safeguardSps = (List) safeguardSpObj;
			specialPlans = (List) specialPlanObj;
		}
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("list", list);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("isread", isread);
		request.setAttribute("conId", conId);
		if ("terminate".equals(flag)) {
			return mapping.findForward("viewSafeguardPlanTerminate");
		} else {
			return mapping.findForward("viewSafeguardPlan");
		}
	}

	/**
	 * �������Ϸ���ǰ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveSafeguardPlanForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String planId = request.getParameter("planId");
		String operator = request.getParameter("operator");
		Map map = getSafeguardPlanBo().getSafeguardPlanData(planId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		String sublineIds = (String) map.get("sublineIds");
		List list = null;
		Object list2 = map.get("list");
		if (list2 != null && !"".equals(list2)) {
			list = (List) list2;
		}
		long planCreateTime = safeguardPlan.getMakeDate().getTime();
		Date deadline = safeguardTask.getDeadline();
		long planDeadline = 0;
		if (deadline != null) {
			planDeadline = deadline.getTime();
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if (safeguardSpObj != null) {
			safeguardSps = (List) safeguardSpObj;
			specialPlans = (List) specialPlanObj;
		}
		double days = (planCreateTime - planDeadline) / 1000.0 / 60.0 / 60.0;
		if (days < 0.0) {
			days = 0.0 - days;
		}
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("operator", operator);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("list", list);
		request.setAttribute("days", days);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		return mapping.findForward("approveSafeguardPlan");
	}

	/**
	 * �������Ϸ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveSafeguardPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		SafeguardTaskBo stb = (SafeguardTaskBo) ctx.getBean("safeguardTaskBo");
		SafeguardPlanBean safeguardPlanBean = (SafeguardPlanBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String approveResult = safeguardPlanBean.getApproveResult();
		String taskId = safeguardPlanBean.getSafeguardId();
		String name = stb.get(taskId).getSafeguardName();
		try {
			getSafeguardPlanBo().approveSafeguardPlan(safeguardPlanBean, userInfo);
			if ("0".equals(approveResult)) {
				log(request, "����δͨ�����Ϸ�������������Ϊ��" + name + "��", "���Ϲ���");
				return forwardInfoPage(mapping, request, "approveSafeguardPlanUnpass");
			} else if ("1".equals(approveResult)) {
				log(request, "����ͨ�����Ϸ�������������Ϊ��" + name + "��", "���Ϲ���");
				return forwardInfoPage(mapping, request, "approveSafeguardPlanPass");
			} else {
				log(request, "ת���Ϸ�������������Ϊ��" + name + "��", "���Ϲ���");
				return forwardInfoPage(mapping, request, "approveSafeguardPlanTransfer");
			}
		} catch (ServiceException e) {
			logger.error("��˱��Ϸ�������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "approveSafeguardPlanError");
		}
	}

	/**
	 * �޸ı��Ϸ���ǰ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editSafeguardPlanForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String planId = request.getParameter("planId");
		Map map = getSafeguardPlanBo().getSafeguardPlanData(planId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		String sublineIds = (String) map.get("sublineIds");
		String[] approveInfo = (String[]) map.get("approveInfo");
		List list = null;
		Object list2 = map.get("list");
		if (list2 != null && !"".equals(list2)) {
			list = (List) list2;
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if (safeguardSpObj != null) {
			safeguardSps = (List) safeguardSpObj;
			specialPlans = (List) specialPlanObj;
		}
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("list", list);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("approveInfo", approveInfo);
		request.setAttribute("approveMan", "approveMan");
		return mapping.findForward("editSafeguardPlan");
	}

	/**
	 * �޸���������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editSafeguardPlan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		SafeguardTaskBo stb = (SafeguardTaskBo) ctx.getBean("safeguardTaskBo");
		SafeguardPlanBean safeguardPlanBean = (SafeguardPlanBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		// ����м̶�id
		String trunks = request.getParameter("trunk");
		String saveflag = request.getParameter("saveflag");
		String saveoreditflag = request.getParameter("saveoreditflag");
		String[] spplanIds = request.getParameterValues("spplanValue");
		List<String> trunkList = new ArrayList<String>();

		String taskId = safeguardPlanBean.getSafeguardId();
		String name = stb.get(taskId).getSafeguardName();

		if (StringUtils.isNotBlank(trunks)) {
			trunkList = Arrays.asList(trunks.split(","));
		}
		try {
			if ("0".equals(saveflag)) {
				getSafeguardPlanBo().editSafeguardPlan(safeguardPlanBean, userInfo, trunkList, files);
			} else {
				getSafeguardPlanBo().tempSaveSafeguardPlan(safeguardPlanBean, userInfo, trunkList, files, spplanIds);
				log(request, "��ʱ���汣�Ϸ�������������Ϊ��" + name + "��", "���Ϲ���");
				return forwardInfoPage(mapping, request, "tempSaveSafeguardPlanSuccess");
			}
			if ("save".equals(saveoreditflag)) {
				log(request, "��ӱ��Ϸ�������������Ϊ��" + name + "��", "���Ϲ���");
				return forwardInfoPage(mapping, request, "addSafeguardPlanSuccess");
			} else {
				log(request, "�޸ı��Ϸ�������������Ϊ��" + name + "��", "���Ϲ���");
				return forwardInfoPage(mapping, request, "editSafeguardPlanSuccess");
			}
		} catch (ServiceException e) {
			logger.error("�༭���Ϸ�������������Ϣ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "editSafeguardPlanError");
		}
	}

	/**
	 * ������������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward readReply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String approverId = userInfo.getUserID();
		String planId = request.getParameter("planId");
		getSafeguardPlanBo().readReply(userInfo, approverId, planId);
		return forwardInfoPage(mapping, request, "safeguardPlanReadReplySuccess");
	}
}
