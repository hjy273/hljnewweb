package com.cabletech.linepatrol.overhaul.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.services.CutManager;
import com.cabletech.linepatrol.overhaul.beans.OverHaulApplyBean;
import com.cabletech.linepatrol.overhaul.beans.OverHaulBean;
import com.cabletech.linepatrol.overhaul.beans.OverHaulQueryBean;
import com.cabletech.linepatrol.overhaul.model.OverHaul;
import com.cabletech.linepatrol.overhaul.model.OverHaulApply;
import com.cabletech.linepatrol.overhaul.model.OverHaulCut;
import com.cabletech.linepatrol.overhaul.model.OverHaulProject;
import com.cabletech.linepatrol.overhaul.model.Process;
import com.cabletech.linepatrol.overhaul.service.OverHaulApplyManager;
import com.cabletech.linepatrol.overhaul.service.OverhaulManager;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;
import com.cabletech.linepatrol.project.service.RemedyApplyManager;

/**
 * @author zhufeng
 */
public class OverHaulAction extends BaseDispatchAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 转到大修任务添加页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTaskForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("addTask");
	}

	/**
	 * 添加大修任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		// 判断用户的部门号是否为“1”，如果是1为移动公司用户
		if (!userInfo.getDeptype().equals("1")) {
			return forwardInfoPage(mapping, request, "notMobile");
		}

		OverHaulBean overHaulBean = (OverHaulBean) form;
		ohm.addTask(overHaulBean, userInfo);
		log(request,
				" 添加大修任务  （大修项目名称为：" + overHaulBean.getProjectName() + "）",
				" 大修项目 ");
		return forwardInfoPage(mapping, request, "overHaulAddTask");
	}

	/**
	 * 转到大修申请页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward applyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");

		String id = request.getParameter("id");
		OverHaul overHual = ohm.loadOverHaul(id); // 大修任务

		request.setAttribute("overHaul", overHual);
		request.getSession().setAttribute("OverHaulApply", new OverHaulApply());
		return mapping.findForward("apply");
	}

	/**
	 * 转到修改大修申请页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editApplyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");
		OverHaulApplyManager oam = (OverHaulApplyManager) ctx
				.getBean("overHaulApplyManager");
		CutManager cm = (CutManager) ctx.getBean("cutManager");
		RemedyApplyManager rm = (RemedyApplyManager) ctx
				.getBean("remedyApplyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String subflowId = request.getParameter("id");
		OverHaul overHaul = ohm.loadOverHaulFromSubflowId(subflowId);
		OverHaulApply overHaulApply = oam.loadOverHaulApplyFromSubflowId(
				subflowId, userInfo);
		List<Cut> cutList = cm.getCutForOverHaul(userInfo.getDeptID(),
				new OverHaulQueryBean(overHaulApply.getId())); // 符合条件的割接
		List<ProjectRemedyApply> projectList = rm.getProjectForOverHaul(
				userInfo.getDeptID(), new OverHaulQueryBean(overHaulApply
						.getId())); // 符合条件的工程

		request.setAttribute("OverHaul", overHaul);
		request.getSession().setAttribute("OverHaulApply", overHaulApply);
		request.getSession().setAttribute("cutList", cutList);
		request.getSession().setAttribute("projectList", projectList);
		return mapping.findForward("editApply");
	}

	/**
	 * 验证任务条数与添加的附件数是否相同
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validateFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		List<FileItem> files = (List<FileItem>) session.getAttribute("FILES");
		List<UploadFileInfo> ufiles = (List<UploadFileInfo>) session
				.getAttribute("OLDFILE");
		String number = request.getParameter("number");
		String html = "";
		int size = 0;
		if (files != null) {
			size += files.size();
		}
		if (ufiles != null) {
			size += ufiles.size();
		}
		if (size != Integer.valueOf(number)) {
			html = "每个割接或工程信息都必须有一个附件";
		}

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(html);
		out.flush();
		return null;
	}

	/**
	 * 大修申请添加的操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverHaulApplyManager oam = (OverHaulApplyManager) ctx
				.getBean("overHaulApplyManager");
		OverhaulManager om = (OverhaulManager) ctx.getBean("overhaulManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		List<FileItem> files = (List<FileItem>) session.getAttribute("FILES");

		OverHaulApplyBean overHaulApplyBean = (OverHaulApplyBean) form;

		// 得到选择的割接信息列表
		List<OverHaulCut> cutList = new ArrayList<OverHaulCut>();
		String[] cuts = request.getParameterValues("cutid");
		for (String cutId : cuts) {
			String cutFee = request.getParameter("cut," + cutId);
			String cutRefFee = request.getParameter("cutref," + cutId);

			OverHaulCut overHaulCut = new OverHaulCut(cutId, cutFee, cutRefFee);
			cutList.add(overHaulCut);
		}

		// 得到选择的工程信息列表
		List<OverHaulProject> projectList = new ArrayList<OverHaulProject>();
		String[] projects = request.getParameterValues("projectid");
		for (String projectId : projects) {
			String projectFee = request.getParameter("project," + projectId);
			String projectRefFee = request.getParameter("projectref,"
					+ projectId);

			OverHaulProject overHaulProject = new OverHaulProject(projectId,
					projectFee, projectRefFee);
			projectList.add(overHaulProject);
		}

		String type = request.getParameter("type");
		if (type.equals("add")) {
			oam.addApply(userInfo, overHaulApplyBean, cutList, projectList,
					files);
		} else {
			oam.editApply(userInfo, overHaulApplyBean, cutList, projectList,
					files);
		}
		String id = overHaulApplyBean.getTaskId();
		String name = om.loadOverHaul(id).getProjectName();
		log(request, " 添加大修任务  （大修项目名称为：" + name + "）", " 大修项目 ");
		return forwardInfoPage(mapping, request, "overHaulApply");
	}

	/**
	 * 转到大修申请的审核页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approveForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");
		OverHaulApplyManager oam = (OverHaulApplyManager) ctx
				.getBean("overHaulApplyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String subflowId = request.getParameter("id");
		String type = request.getParameter("type");
		OverHaul overHual = ohm.loadOverHaulFromSubflowId(subflowId);
		OverHaulApply overHaulApply = oam.loadOverHaulApplyFromSubflowId(
				subflowId, userInfo);

		request.setAttribute("type", type);
		request.setAttribute("OverHaul", overHual);
		request.setAttribute("OverHaulApply", overHaulApply);
		return mapping.findForward("approve");
	}

	/**
	 * 大修申请的审核操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approve(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverHaulApplyManager oam = (OverHaulApplyManager) ctx
				.getBean("overHaulApplyManager");
		OverhaulManager om = (OverhaulManager) ctx.getBean("overhaulManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String applyId = request.getParameter("applyId");
		String approveResult = request.getParameter("approveResult");
		String approveRemark = request.getParameter("approveRemark");

		ApproveInfo approve = new ApproveInfo();
		approve.setObjectId(applyId);
		approve.setApproverId(userInfo.getUserID());
		approve.setApproveResult(approveResult);
		approve.setApproveRemark(approveRemark);

		oam.approve(approve, userInfo);
		log(request, " 大修申请审核  （大修项目名称为："
				+ om.get(oam.get(applyId).getTaskId()).getProjectName() + "）",
				" 大修项目 ");
		return forwardInfoPage(mapping, request, "overHaulApprove");
	}

	/**
	 * 大修申请的转审操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward transfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverHaulApplyManager oam = (OverHaulApplyManager) ctx
				.getBean("overHaulApplyManager");
		OverhaulManager om = (OverhaulManager) ctx.getBean("overhaulManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String applyId = request.getParameter("id");
		String approver = request.getParameter("approver");

		oam.transfer(userInfo, applyId, approver);
		log(request, " 大修申请转审  （大修项目名称为："
				+ om.get(oam.get(applyId).getTaskId()).getProjectName() + "）",
				" 大修项目 ");
		return forwardInfoPage(mapping, request, "overHaulTransfer");
	}

	public ActionForward read(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String projectName = ohm.get(id).getProjectName();
		ohm.read(id, type, userInfo);
		log(request,
				" 已阅读大修项目  （大修项目名称为：" + ohm.get(id).getProjectName() + "）",
				" 大修项目 ");
		return forwardInfoPage(mapping, request, "overHaulReadSuccess");
	}

	/**
	 * 转到大修处理页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward treatForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");

		String id = request.getParameter("id");
		OverHaul overHaul = ohm.loadOverHaulWithPassedApply(id);
		String json = ohm.getJsonFromOverHaul(overHaul);
		request.setAttribute("json", json);
		request.setAttribute("OverHaul", overHaul);
		return mapping.findForward("treat");
	}

	/**
	 * 大修处理操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward treat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("taskId");
		String flag = request.getParameter("flag");
		String checked = request.getParameter("checked");
		ohm.treat(id, flag, checked, userInfo);

		String projectName = ohm.loadOverHaul(id).getProjectName();
		log(request, " 大修处理  （大修项目名称为：" + projectName + "）", " 大修项目 ");
		return forwardInfoPage(mapping, request, "overHaulTreat");
	}

	/**
	 * 转到查询统计页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<Contractor> list = ohm.getContractors(userInfo);
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("deptype", userInfo.getDeptype());
		request.getSession().setAttribute("conId", userInfo.getDeptID());
		request.getSession().setAttribute("conName", userInfo.getDeptName());
		
		// 清空session中的查询条件和查询结果
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("result");
		return mapping.findForward("query");
	}

	public ActionForward queryCut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		CutManager cm = (CutManager) ctx.getBean("cutManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		OverHaulQueryBean queryBean = (OverHaulQueryBean) form;

		List<Cut> cutList = cm.getCutForOverHaul(userInfo.getDeptID(),
				queryBean);
		session.setAttribute("overHaulQueryBean", queryBean);
		session.setAttribute("cutList", cutList);
		return mapping.findForward("queryCut");
	}

	public ActionForward queryProject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		RemedyApplyManager rm = (RemedyApplyManager) ctx
				.getBean("remedyApplyManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		OverHaulQueryBean queryBean = (OverHaulQueryBean) form;

		List<ProjectRemedyApply> projectList = rm.getProjectForOverHaul(
				userInfo.getDeptID(), queryBean);
		session.setAttribute("overHaulQueryBean", queryBean);
		session.setAttribute("projectList", projectList);
		return mapping.findForward("queryProject");
	}

	/**
	 * 转到查询结果页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward result(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		OverHaulQueryBean queryBean = (OverHaulQueryBean) form;
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			queryBean = (OverHaulQueryBean) request.getSession().getAttribute(
					"queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", queryBean);
		}
		// 如果queryBean为空的话，要创建queryBean对象
		if (queryBean == null) {
			queryBean = new OverHaulQueryBean();
		}
		List<OverHaul> list = ohm.getResult(queryBean, userInfo);
		for (OverHaul overhaul : list) {
			overhaul.setUseFee(ohm
					.loadOverHaulWithPassedApply(overhaul.getId()).getUseFee());
		}
		session.setAttribute("result", list);
		request.setAttribute("task_names", queryBean.getTaskNames());
		return mapping.findForward("result");
	}

	/**
	 * 转到大修查看页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		String type = request.getParameter("type");

		request.setAttribute("type", type);
		request.setAttribute("cons", ohm.loadOverHaulCon(id));
		request.setAttribute("OverHaul", ohm.getViewOverHaul(id, userInfo));
		return mapping.findForward("view");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toDoWork(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		List<OverHaul> list = ohm.getToDoWork(userInfo, taskName);
		for (OverHaul overhaul : list) {
			overhaul.setUseFee(ohm
					.loadOverHaulWithPassedApply(overhaul.getId()).getUseFee());
		}
		session.setAttribute("task_name", taskName);
		session.setAttribute("result", list);
		session.setAttribute("number", list.size());
		return mapping.findForward("toDoWork");
	}

	public ActionForward processMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");

		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		Process process = ohm.getProcessNumber(userInfo);
		request.setAttribute("process", process);
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null
				&& !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("processMap");
	}

	public ActionForward finishedWork(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		List<OverHaul> list = ohm.getFinishedWork(userInfo);
		for (OverHaul overhaul : list) {
			overhaul.setUseFee(ohm
					.loadOverHaulWithPassedApply(overhaul.getId()).getUseFee());
		}
		session.setAttribute("result", list);
		return mapping.findForward("finishedWork");
	}

	/**
	 * 执行取消大修申请任务表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelOverHaulForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String overHaulId = request.getParameter("overhaul_id");
		request.setAttribute("overhaul_id", overHaulId);
		return mapping.findForward("cancel_over_haul");
	}

	/**
	 * 执行取消大修申请任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelOverHaul(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		OverHaulBean bean = (OverHaulBean) form;
		// bean.setId(request.getParameter("cutId"));
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");
		ohm.cancelOverHaul(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_OVER_HAUL_SUCCESS", url);
	}
	
	/**
	 * 查看考核信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewExamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskId = request.getParameter("taskId");
		String contractorId = request.getParameter("contractorId");
		request.setAttribute("taskId", taskId);
		request.setAttribute("contractorId", contractorId);
		return mapping.findForward("viewExamInfo");
	}
}