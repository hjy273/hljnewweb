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
 * ��ӹ���
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
	 * ��Ӹ������ʱ�������� 1��������ţ�workOrderId 2����ά�����Ա��mobiles 3����ά�����Ա��cons
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
	 * ���������� 1����applyStateΪ0ʱ��ʾ���������룬��Ϊ1ʱ��ʾ���������ʱ���� 2����ʱ�������������Ƹ������
	 * 3��filesΪ�ϴ��ĸ���
	 */
	public ActionForward addCutApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CutBean cutBean = (CutBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		String applyState = cutBean.getApplyState();
		// ����м̶�id
		String trunks = request.getParameter("trunk");
		try {
			
			if (Cut.SAVE.equals(applyState)) {
				getCutService().addCutApply(cutBean, trunks, userInfo, files);
				log(request, "���������루�������Ϊ��" + cutBean.getCutName() + "��", "��ӹ���");
				return forwardInfoPage(mapping, request, "addCutApplySuccess");
			} else {
				getCutService().saveTempApply(cutBean, trunks, userInfo, files);
				log(request, "��ʱ���������루�������Ϊ��" + cutBean.getCutName() + "��", "��ӹ���");
				return forwardInfoPage(mapping, request, "tempCutApplySuccess");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("��Ӹ���������������ϢΪ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "addCutApplyError");
		}
	}

	/**
	 * ��ø��û�����ʱ��������б� �ڸ�������lp_cut��applyStateδ1��Ϊ��ʱ�������
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
	 * �޸����뵥ǰ������Ϣ��ͨ���������ID��ѯ�����Ϣ 1���������ʵ�壺cut 2������������漰�����м̶�ID��sublineIds
	 * 3����ά�����Ա��mobiles 4����ά�����Ա��cons
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
	 * �޸ĸ������ 1��cutBean�������Bean 2��trunks�м̶�IDS
	 */
	public ActionForward editCutApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		CutBean cutBean = (CutBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		// ����м̶�id
		String trunks = request.getParameter("trunk");
		try {
			getCutService().editCutApply(cutBean, userInfo, trunks, files);
			log(request, "�޸ĸ�����루�������Ϊ��" + cutBean.getCutName() + "��", "��ӹ���");
			return forwardInfoPage(mapping, request, "editCutApplySuccess");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("�༭����������������ϢΪ��" + e.getMessage());
			return forwardErrorPage(mapping, request, "editCutApplyError");
		}
	}

	/**
	 * �����������ǰ������Ϣ 1��ͨ��operator��������������ת�󡣵�operatorΪapproveΪ������ΪtransferΪת��
	 * 2��cutIdΪ�������ID
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
	 * ����������� ͨ����������ж�������ͨ������ͨ������ת��0������ͨ�� 1����ͨ�� 2ת��
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
					log(request, "�����������δͨ�����������Ϊ��" + cutName + "��", "��ӹ���");
					return forwardWapInfoPageWithUrl(mapping, request, "approveCutApplyUnpass", url);
				} else if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
					log(request, "�����������ͨ�����������Ϊ��" + cutName + "��", "��ӹ���");
					return forwardWapInfoPageWithUrl(mapping, request, "approveCutApplyPass", url);
				} else {
					log(request, "�����������ת�󣨸������Ϊ��" + cutName + "��", "��ӹ���");
					return forwardWapInfoPageWithUrl(mapping, request, "approveCutApplyTransfer", url);
				}
			}
			if (CutConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				log(request, "�����������δͨ�����������Ϊ��" + cutName + "��", "��ӹ���");
				return forwardInfoPage(mapping, request, "approveCutApplyUnpass");
			} else if (CutConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				log(request, "�����������ͨ�����������Ϊ��" + cutName + "��", "��ӹ���");
				return forwardInfoPage(mapping, request, "approveCutApplyPass");
			} else {
				log(request, "�����������ת�󣨸������Ϊ��" + cutName + "��", "��ӹ���");
				return forwardInfoPage(mapping, request, "approveCutApplyTransfer");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("��������������������ϢΪ��" + e.getMessage());
			if (env != null && WAP_ENV.equals(env)) {
				String url = (String) request.getSession().getAttribute("S_BACK_URL");
				return super.forwardWapErrorPageWithUrl(mapping, request, "approveCutApplyError", url);
			}
			return forwardErrorPage(mapping, request, "approveCutApplyError");
		}
	}

	/**
	 * ��ô��칤���б�
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
	 * �����ɵ�����ͼҳ��
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
	 * �鿴������� 1���������ID��cutId 2����������漰�����м̶�ID��sublineIds
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
	 * ��ת����ѯ����������
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
	 * ��ѯ�������
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
	 * ɾ����ʱ���������Ϣ
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
			log(request, "ɾ����ʱ���������Ϣ���������Ϊ��" + cutName + "��", "��ӹ���");
			return forwardInfoPage(mapping, request, "deleteTempSaveCutSuccess");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("ɾ�������ʱ�������ʧ�ܣ�" + e.getMessage());
			return forwardErrorPage(mapping, request, "deleteTempSaveCutError");
		}
	}

	/**
	 * ����Ѱ칤���б���Ϣ
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
	 * �����������
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
			log(request, "����������ϣ��������Ϊ��" + cutName + "��", "��ӹ���");
			return forwardInfoPage(mapping, request, "invalidCutSuccess");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("���ϸ������ʧ�ܣ�" + e.getMessage());
			return forwardErrorPage(mapping, request, "invalidCutError");
		}
	}

	/**
	 * ִ��ȡ����������
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
	 * ִ��ȡ���������
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
	 * �鿴���������ʷ
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
