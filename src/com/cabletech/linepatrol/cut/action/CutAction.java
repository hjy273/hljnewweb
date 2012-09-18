package com.cabletech.linepatrol.cut.action;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.cabletech.linepatrol.cut.beans.CutBean;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.cut.services.CutManager;
import com.cabletech.linepatrol.cut.workflow.CutWorkflowBO;

/**
 * 割接管理
 * 
 * @author liusq
 * 
 */
public class CutAction extends BaseInfoBaseDispatchAction {

	private static final long serialVersionUID = 740409341214831771L;
	private static Logger logger = Logger.getLogger(CutAction.class.getName());

	private CutManager getCutService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (CutManager) ctx.getBean("cutManager");
	}

	/**
	 * 添加割接申请时加载数据 1、工单编号：workOrderId 2、线维相关人员：mobiles 3、代维相关人员：cons
	 */
	public ActionForward addCutApplyForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String cutId = request.getParameter("cutId");
		Map map = getCutService().addCutApplyForm(userInfo, cutId);
		Cut cut = null;
		String sublineIds = null;

		String workOrderId = (String) map.get("workOrderId");
		List mobiles = (List) map.get("mobiles");
		List cons = (List) map.get("cons");
		Object object = map.get("cut");
		Object subline = map.get("sublineIds");
		request.setAttribute("workOrderId", workOrderId);
		request.setAttribute("mobiles", mobiles);
		request.setAttribute("cons", cons);

		if (object != null) {
			cut = (Cut) object;
			sublineIds = (String) subline;
			String[] approveInfo = (String[]) map.get("approveInfo");
			String[] readerInfo = (String[]) map.get("readerInfo");
			request.setAttribute("cut", cut);
			request.setAttribute("sublineIds", sublineIds);
			if (approveInfo != null) {
				request.setAttribute("approveInfoId", approveInfo[0]);
				request.setAttribute("approveInfoName", approveInfo[1]);
				request.setAttribute("approveInfoPhone", approveInfo[2]);
			} else {
				request.setAttribute("approveInfoId", "");
				request.setAttribute("approveInfoName", "");
				request.setAttribute("approveInfoPhone", "");
			}
			if (readerInfo != null) {
				request.setAttribute("readerInfoId", readerInfo[0]);
				request.setAttribute("readerInfoName", readerInfo[1]);
				request.setAttribute("readerInfoPhone", readerInfo[2]);
			} else {
				request.setAttribute("readerInfoId", "");
				request.setAttribute("readerInfoName", "");
				request.setAttribute("readerInfoPhone", "");
			}
			return mapping.findForward("prefectCutApply");
		}
		return mapping.findForward("addCutApplyForm");
	}

	/**
	 * 保存割接申请 1、当applyState为0时表示保存割接申请，当为1时表示割接申请临时保存 2、临时割接申请可以完善割接申请
	 * 3、files为上传的附件
	 */
	public ActionForward addCutApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CutBean cutBean = (CutBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		String applyState = cutBean.getApplyState();
		// 获得中继段id
		String trunks = request.getParameter("trunk");
		try {
			
			if (Cut.SAVE.equals(applyState)) {
				getCutService().addCutApply(cutBean, trunks, userInfo, files);
				log(request, "保存割接申请（割接名称为：" + cutBean.getCutName() + "）", "割接管理");
				return forwardInfoPage(mapping, request, "addCutApplySuccess");
			} else {
				getCutService().saveTempApply(cutBean, trunks, userInfo, files);
				log(request, "临时保存割接申请（割接名称为：" + cutBean.getCutName() + "）", "割接管理");
				return forwardInfoPage(mapping, request, "tempCutApplySuccess");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("添加割接申请出错，出错信息为：" + e.getMessage());
			return forwardErrorPage(mapping, request, "addCutApplyError");
		}
	}

	/**
	 * 获得该用户的临时割接申请列表 在割接申请表lp_cut中applyState未1的为临时割接申请
	 */
	public ActionForward cutTempList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String userId = userInfo.getUserID();
		List list = getCutService().cutTempList(userId);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("cutTempList");
	}

	/**
	 * 修改申请单前加载信息：通过割接申请ID查询相关信息 1、割接申请实体：cut 2、割接申请中涉及到的中继段ID：sublineIds
	 * 3、线维相关人员：mobiles 4、代维相关人员：cons
	 */
	public ActionForward editCutApplyForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String cutId = request.getParameter("cutId");
		Map map = this.getCutService().editCutApplyForm(cutId, userInfo.getRegionid(), userInfo.getDeptID());
		Cut cut = (Cut) map.get("cut");
		String sublineIds = (String) map.get("sublineIds");
		List mobiles = (List) map.get("mobiles");
		List cons = (List) map.get("cons");
		request.setAttribute("mobiles", mobiles);
		request.setAttribute("cons", cons);
		request.setAttribute("cut", cut);
		request.setAttribute("sublineIds", sublineIds);
		return mapping.findForward("editCutApplyForm");
	}

	/**
	 * 修改割接申请 1、cutBean割接申请Bean 2、trunks中继段IDS
	 */
	public ActionForward editCutApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		CutBean cutBean = (CutBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		// 获得中继段id
		String trunks = request.getParameter("trunk");
		try {
			getCutService().editCutApply(cutBean, userInfo, trunks, files);
			log(request, "修改割接申请（割接名称为：" + cutBean.getCutName() + "）", "割接管理");
			return forwardInfoPage(mapping, request, "editCutApplySuccess");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("编辑割接申请出错，出错信息为：" + e.getMessage());
			return forwardErrorPage(mapping, request, "editCutApplyError");
		}
	}

	/**
	 * 审批割接申请前加载信息 1、通过operator区分是审批还是转审。当operator为approve为审批，为transfer为转审
	 * 2、cutId为割接申请ID
	 */
	public ActionForward approveCutApplyForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String deadline = sdf.format(new Date(date.getTime() + 2 * 24 * 60 * 60 * 1000));
		String operator = request.getParameter("operator");
		String cutId = request.getParameter("cutId");
		Map map = getCutService().approveCutApplyForm(cutId);
		Cut cut = (Cut) map.get("cut");
		String sublineIds = (String) map.get("sublineIds");
		List approve_info_list = (List) map.get("approve_info_list");
		request.setAttribute("cut", cut);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("operator", operator);
		request.setAttribute("deadline", deadline);
		request.setAttribute("approve_info_list", approve_info_list);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("approve_cut_apply_wap_task");
		}
		return mapping.findForward("approveCutApplyForm");
	}

	/**
	 * 割接申请审批 通过审批结果判断是审批通过、不通过还是转审：0审批不通过 1审批通过 2转审
	 */
	public ActionForward approveCutApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		CutManager cm = (CutManager) ctx.getBean("cutManager");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		CutBean cutBean = (CutBean) form;
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		String approveResult = cutBean.getApproveResult();
		String env = request.getParameter("env");
		String cutName = cm.get(cutBean.getId()).getCutName();
		try {
			getCutService().approveCutApply(userInfo, cutBean, files);
			if (env != null && WAP_ENV.equals(env)) {
				String url = (String) request.getSession().getAttribute("S_BACK_URL");
				if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
					log(request, "割接申请审批未通过（割接名称为：" + cutName + "）", "割接管理");
					return forwardWapInfoPageWithUrl(mapping, request, "approveCutApplyUnpass", url);
				} else if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
					log(request, "割接申请审批通过（割接名称为：" + cutName + "）", "割接管理");
					return forwardWapInfoPageWithUrl(mapping, request, "approveCutApplyPass", url);
				} else {
					log(request, "割接申请审批转审（割接名称为：" + cutName + "）", "割接管理");
					return forwardWapInfoPageWithUrl(mapping, request, "approveCutApplyTransfer", url);
				}
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				log(request, "割接申请审批未通过（割接名称为：" + cutName + "）", "割接管理");
				return forwardInfoPage(mapping, request, "approveCutApplyUnpass");
			} else if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				log(request, "割接申请审批通过（割接名称为：" + cutName + "）", "割接管理");
				return forwardInfoPage(mapping, request, "approveCutApplyPass");
			} else {
				log(request, "割接申请审批转审（割接名称为：" + cutName + "）", "割接管理");
				return forwardInfoPage(mapping, request, "approveCutApplyTransfer");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("审批割接申请出错，出错信息为：" + e.getMessage());
			if (env != null && WAP_ENV.equals(env)) {
				String url = (String) request.getSession().getAttribute("S_BACK_URL");
				return super.forwardWapErrorPageWithUrl(mapping, request, "approveCutApplyError", url);
			}
			return forwardErrorPage(mapping, request, "approveCutApplyError");
		}
	}

	/**
	 * 获得代办工作列表
	 */
	public ActionForward getAllCut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		String type = request.getParameter("type");
		WebApplicationContext ctx = getWebApplicationContext();
		CutWorkflowBO workflowBo = (CutWorkflowBO) ctx.getBean("cutWorkflowBO");
		String env = request.getParameter("env");

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		List list = getCutService().getHandWork(userInfo.getUserID(), type, taskName);
		Integer num = new Integer(list.size());
		// new Integer(workflowBo.queryForHandleNumber(userInfo
		// .getUserID(), condition, taskName));
		if (env != null && WAP_ENV.equals(env)) {
			list = getCutService().getHandWork(userInfo.getUserID(), type, "");
			// list.addAll(getCutService().getHandWork(userInfo.getUserID(),
			// condition, "check_approve_task"));
			if (list != null && !list.isEmpty()) {
				num = new Integer(list.size());
			} else {
				num = new Integer(0);
			}
		}
		request.setAttribute("num", num);
		request.setAttribute("type", type);
		request.setAttribute("task_name", taskName);
		request.getSession().setAttribute("list", list);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("cut_task_wait_handle_wap_list");
		}
		return mapping.findForward("getAllCut");
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
	public ActionForward viewCutProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		CutManager bo = (CutManager) ctx.getBean("cutManager");
		String condition = "";
		List waitHandleTaskNum = bo.queryForHandleCutNum(condition, userInfo);
		request.setAttribute("wait_apply_num", waitHandleTaskNum.get(0));
		request.setAttribute("wait_apply_approve_num", waitHandleTaskNum.get(1));
		request.setAttribute("wait_work_num", waitHandleTaskNum.get(2));
		request.setAttribute("wait_work_approve_num", waitHandleTaskNum.get(3));
		request.setAttribute("wait_check_num", waitHandleTaskNum.get(4));
		request.setAttribute("wait_check_approve_num", waitHandleTaskNum.get(5));
		request.setAttribute("wait_evaluate_num", waitHandleTaskNum.get(6));
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null && !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("view_cut_process");
	}

	/**
	 * 查看割接申请 1、割接申请ID：cutId 2、割接申请涉及到的中继段ID：sublineIds
	 */
	public ActionForward viewCut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String cutId = request.getParameter("cutId");
		String isread = request.getParameter("isread");
		String env = request.getParameter("env");
		Map map = getCutService().viewCut(cutId);
		Cut cut = (Cut) map.get("cut");
		String sublineIds = (String) map.get("sublineIds");
		List approve_info_list = (List) map.get("approve_info_list");
		request.setAttribute("isread", isread);
		request.setAttribute("cut", cut);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("env", env);
		request.setAttribute("approve_info_list", approve_info_list);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("read_cut_apply_wap_task");
		}
		return mapping.findForward("viewCut");
	}

	/**
	 * 跳转到查询割接申请界面
	 */
	public ActionForward queryCutInfoForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("queryCutInfoForm");
	}

	public ActionForward readReply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String approverId = userInfo.getUserID();
		String cutId = request.getParameter("cutId");
		String env = request.getParameter("env");
		getCutService().readReply(userInfo, approverId, cutId);
		if (env != null && WAP_ENV.equals(env)) {
			String url = (String) request.getSession().getAttribute("S_BACK_URL");
			return super.forwardWapInfoPageWithUrl(mapping, request, "cutReadReplySuccess", url);
		}
		return forwardInfoPage(mapping, request, "cutReadReplySuccess");
	}

	/**
	 * 查询割接申请
	 */
	public ActionForward queryApplyList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super.setPageReset(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String userId = userInfo.getUserID();
		List list = new ArrayList();
		list = getCutService().queryApplyList(userId);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("viewApplyList");
	}

	/**
	 * 删除临时割接申请信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteTempSaveCut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		CutManager cm = (CutManager) ctx.getBean("cutManager");
		String cutId = request.getParameter("cutId");
		String cutName = cm.get(cutId).getCutName();
		try {
			getCutService().deleteTempSaveCut(cutId);
			log(request, "删除临时割接申请信息（割接名称为：" + cutName + "）", "割接管理");
			return forwardInfoPage(mapping, request, "deleteTempSaveCutSuccess");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("删除割接临时割接申请失败：" + e.getMessage());
			return forwardErrorPage(mapping, request, "deleteTempSaveCutError");
		}
	}

	/**
	 * 获得已办工作列表信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryFinishHandledCutApplyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		List list = getCutService().queryFinishHandledCutApplyList(userInfo, taskName, taskOutCome);
		int num = 0;
		if (list != null && list.size() > 0) {
			num = list.size();
		}
		request.getSession().setAttribute("list", list);
		request.setAttribute("num", num);
		return mapping.findForward("queryFinishHandledCutApplyList");
	}

	/**
	 * 割接申请作废
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward invalidCut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		CutManager cm = (CutManager) ctx.getBean("cutManager");
		String cutId = request.getParameter("cutId");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String cutName = cm.get(cutId).getCutName();
		try {
			getCutService().invalidCut(cutId, userInfo);
			log(request, "割接申请作废（割接名称为：" + cutName + "）", "割接管理");
			return forwardInfoPage(mapping, request, "invalidCutSuccess");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("作废割接申请失败：" + e.getMessage());
			return forwardErrorPage(mapping, request, "invalidCutError");
		}
	}

	/**
	 * 执行取消割接任务表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelCutForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String cutId = request.getParameter("cutId");
		request.setAttribute("cut_id", cutId);
		return mapping.findForward("cutCancelForm");
	}

	/**
	 * 执行取消割接任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelCut(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		CutBean bean = (CutBean) form;
		// bean.setId(request.getParameter("cutId"));
		CutManager cm = (CutManager) ctx.getBean("cutManager");
		cm.cancelCut(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_CUT_SUCCESS", url);
	}

	/**
	 * 查看割接申请历史
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewCutHistoryProcess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		// initialize(request);
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		String condition = "";
		// condition += ConditionGenerate.getUserQueryCondition(userInfo);
		List waitHandleTaskNum = getCutService().queryForHandledCutTaskNumList(condition, userInfo);
		request.setAttribute("apply_task_num", waitHandleTaskNum.get(0));
		request.setAttribute("apply_approve_task_num", waitHandleTaskNum.get(1));
		request.setAttribute("work_task_num", waitHandleTaskNum.get(2));
		request.setAttribute("work_approve_task_num", waitHandleTaskNum.get(3));
		request.setAttribute("check_task_num", waitHandleTaskNum.get(4));
		request.setAttribute("check_approve_task_num", waitHandleTaskNum.get(5));
		request.setAttribute("evaluate_task_num", waitHandleTaskNum.get(6));
		request.setAttribute("task_name", taskName);
		request.setAttribute("task_out_come", taskOutCome);
		return mapping.findForward("view_cut_history_process");
	}
}
