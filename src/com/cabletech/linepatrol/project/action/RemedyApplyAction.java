package com.cabletech.linepatrol.project.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.project.beans.RemedyApplyBean;
import com.cabletech.linepatrol.project.constant.ExecuteCode;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;
import com.cabletech.linepatrol.project.service.CountyInfoBO;
import com.cabletech.linepatrol.project.service.RemedyApplyManager;
import com.cabletech.linepatrol.project.service.RemedyApproveManager;
import com.cabletech.linepatrol.project.service.RemedyItemManager;
import com.cabletech.linepatrol.project.service.RemedyTypeManager;

public class RemedyApplyAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private WebApplicationContext ctx;
	private UserInfo userInfo;
	private List<FileItem> files;
	private RemedyApplyManager applyBo;
	private RemedyApproveManager approveBo;

	public void initialize(HttpServletRequest request) {
		ctx = getWebApplicationContext();
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		files = (List) request.getSession().getAttribute("FILES");
		applyBo = (RemedyApplyManager) ctx.getBean("remedyApplyManager");
		approveBo = (RemedyApproveManager) ctx.getBean("remedyApproveManager");
	}

	// �����ɵ�����ͼҳ��
	public ActionForward viewRemedyProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		String condition = "";
		List waitHandleTaskNum = applyBo.queryForHandleRemedyNum(condition,
				userInfo, taskName);
		request.setAttribute("wait_apply_task_num", waitHandleTaskNum.get(0));
		request.setAttribute("wait_approve_task_num", waitHandleTaskNum.get(1));
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null
				&& !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("view_remedy_process");
	}

	/**
	 * ִ���ж��Ƿ������ͬ����������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward judgeExistSameApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		boolean flag = applyBo.judgeExistSameApply(request);
		PrintWriter out = response.getWriter();
		if (flag) {
			out.print("1");
		} else {
			out.print("0");
		}
		out.close();
		return null;
	}

	/**
	 * ִ�н���������������ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insertApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		RemedyItemManager itemBo = (RemedyItemManager) ctx
				.getBean("remedyItemManager");
		RemedyTypeManager itemTypeBo = (RemedyTypeManager) ctx
				.getBean("remedyTypeManager");
		Integer remedyNumber = applyBo.getRemedyApplyNumber(userInfo);
		String generateId = applyBo.getRemedySerialPrex(userInfo);
		request.setAttribute("generate_id", generateId
				+ remedyNumber.toString());
		List itemList = itemBo.getRemedyItemList(userInfo);
		request.setAttribute("item_list", itemList);
		List itemTypeList = itemTypeBo.getRemedyItemTypeList(userInfo);
		request.setAttribute("item_type_list", itemTypeList);
		return mapping.findForward("insert_remedy_apply");
	}

	/**
	 * ִ����������Ĳ鿴����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateApplyForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		CountyInfoBO countyBo = (CountyInfoBO) ctx.getBean("countyInfoBO");
		RemedyItemManager itemBo = (RemedyItemManager) ctx
				.getBean("remedyItemManager");
		RemedyTypeManager itemTypeBo = (RemedyTypeManager) ctx
				.getBean("remedyTypeManager");
		String applyId = request.getParameter("apply_id");
		Map applyMap = applyBo.viewApply(applyId);
		if (applyMap == null) {
			String backUrl = (String) request.getSession().getAttribute(
					"S_BACK_URL");
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotFindDataError", backUrl);
		}
		request.setAttribute("apply_map", applyMap);

		List townList = countyBo.getTownList(userInfo);
		request.setAttribute("town_list", townList);
		List itemList = itemBo.getRemedyItemList(userInfo);
		request.setAttribute("item_list", itemList);
		List itemTypeList = itemTypeBo.getRemedyItemTypeList(userInfo);
		request.setAttribute("item_type_list", itemTypeList);
		return mapping.findForward("update_remedy_apply");
	}

	/**
	 * ��ѯ��������ҳ��
	 */
	public ActionForward queryApplyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List<Contractor> list = applyBo.getContractors(userInfo);
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("deptype", userInfo.getDeptype());
		request.getSession().setAttribute("conId", userInfo.getDeptID());
		request.getSession().setAttribute("conName", userInfo.getDeptName());
		
		// ���session�еĲ�ѯ�����Ͳ�ѯ���
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("APPLY_LIST");
		return mapping.findForward("query_remedy_apply");
	}

	/**
	 * ִ����������Ĳ�ѯ����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		RemedyApplyBean bean = (RemedyApplyBean) form;
		// �ж�formbean���Ƿ��в�ѯ��Ҫ��������ݣ����û����FormBean�е����ݷ���session�У������session��ȡ����ǰ������
		if (null == request.getParameter("isQuery")) {
			bean = (RemedyApplyBean) request.getSession().getAttribute(
					"queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", bean);
		}
		List list = applyBo.queryList(userInfo, bean);
		request.getSession().setAttribute("APPLY_LIST", list);
		request.setAttribute("task_names", bean.getTaskNames());
		// ����url��session�е�S_BACK_URL����
		super.setPageReset(request);
		return mapping.findForward("list_remedy_apply");
	}

	/**
	 * ִ����������Ĳ�ѯ����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryWaitHandleList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPageReset(request);
		initialize(request);
		RemedyApplyBean bean = (RemedyApplyBean) form;
		String taskName = request.getParameter("task_name");
		List list = applyBo.queryWaitHandleList(userInfo, bean, taskName);
		request.getSession().setAttribute("APPLY_LIST", list);
		return mapping.findForward("list_wait_handle_remedy_apply");
	}

	/**
	 * ִ�в�������������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insertApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String url = request.getContextPath()
				+ "/project/remedy_apply.do?method=";
		String operationCode = ExecuteCode.FAIL_CODE;
		RemedyApplyBean applyBean = (RemedyApplyBean) form;
		try {
			operationCode = applyBo.insertApply(userInfo, applyBean, files);
		} catch (Exception e) {
			logger.error("ִ�в�����������ҵ���쳣��" + e);
		}
		if (ExecuteCode.EXIST_SAME_APPLY_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"ExistSameApply", url + "insertApplyForm");
		}
		if (ExecuteCode.NOT_EXIST_ITEM_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotExistItem", url + "insertApplyForm");
		}
		if (ExecuteCode.NOT_EXIST_MATERIAL_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotExistMaterial", url + "insertApplyForm");
		}
		if (ExecuteCode.NOT_ENOUGH_STORAGE_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotEnoughStorage", url + "insertApplyForm");
		}
		// if (ExecuteCode.NOT_EXIST_MT_USED_ERROR.equals(operationCode)) {
		// return super.forwardErrorPageWithUrl(mapping, request,
		// "NotExistMtUsedError", url + "insertApplyForm");
		// }
		log(request, " ��������������Ϣ  ����������Ϊ��" + applyBean.getProjectName() + "��",
				" ���̹���");
		return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode",
				url + "insertApplyForm");
	}

	/**
	 * ִ���޸�����������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String applyId = request.getParameter("id");
		String url = request.getContextPath() + "/remedy_apply.do?method=";
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");
		String operationCode = ExecuteCode.FAIL_CODE;
		RemedyApplyBean applyBean = (RemedyApplyBean) form;
		try {
			operationCode = applyBo.updateApply(userInfo, applyBean, files);
		} catch (Exception e) {
			logger.error("ִ���޸���������ҵ���쳣��" + e);
		}
		if (ExecuteCode.NO_DATA_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotFindDataError", backUrl);
		}
		if (ExecuteCode.NOT_EDITED_APPLY_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotEditedApply", backUrl);
		}
		if (ExecuteCode.EXIST_SAME_APPLY_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"ExistSameApply", url + "view");
		}
		if (ExecuteCode.NOT_EXIST_ITEM_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotExistItem", url + "view");
		}
		if (ExecuteCode.NOT_EXIST_MATERIAL_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotExistMaterial", url + "view");
		}
		if (ExecuteCode.NOT_ENOUGH_STORAGE_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotEnoughStorage", url + "view");
		}
		// if (ExecuteCode.NOT_EXIST_MT_USED_ERROR.equals(operationCode)) {
		// return super.forwardErrorPageWithUrl(mapping, request,
		// "NotExistMtUsedError", backUrl);
		// }
		log(request, "  �޸�����������Ϣ  ����������Ϊ��" + applyBean.getProjectName() + "��",
				" ���̹���");
		return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode",
				backUrl);
	}

	/**
	 * ִ��ɾ������������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String backUrl = (String) request.getSession().getAttribute(
				"S_BACK_URL");
		String operationCode = ExecuteCode.FAIL_CODE;
		RemedyApplyBean applyBean = (RemedyApplyBean) form;
		try {
			operationCode = applyBo.deleteApply(request, form);
		} catch (Exception e) {
			logger.error("ִ��ɾ����������ҵ���쳣��" + e);
		}
		if (ExecuteCode.NO_DATA_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotFindDataError", backUrl);
		}
		if (ExecuteCode.NOT_DELETED_APPLY_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotDeletedApply", backUrl);
		}
		log(request, " ɾ������������Ϣ  ����������Ϊ��" + applyBean.getProjectName() + "��",
				" ���̹���");
		return super.forwardErrorPageWithUrl(mapping, request, "SuccessCode",
				backUrl);
	}

	/**
	 * ִ����������Ĳ鿴����
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
		initialize(request);
		CountyInfoBO countyBo = (CountyInfoBO) ctx.getBean("countyInfoBO");
		RemedyItemManager itemBo = (RemedyItemManager) ctx
				.getBean("remedyItemManager");
		RemedyTypeManager itemTypeBo = (RemedyTypeManager) ctx
				.getBean("remedyTypeManager");
		String applyId = request.getParameter("apply_id");
		String type = request.getParameter("type");
		Map applyMap = applyBo.viewApply(applyId);
		if (applyMap == null) {
			String backUrl = (String) request.getSession().getAttribute(
					"S_BACK_URL");
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotFindDataError", backUrl);
		}
		request.setAttribute("apply_map", applyMap);
		request.setAttribute("type", type);
		// List townList = countyBo.getTownList(userInfo);
		// request.setAttribute("town_list", townList);
		List itemList = itemBo.getRemedyItemList(userInfo);
		request.setAttribute("item_list", itemList);
		List itemTypeList = itemTypeBo.getRemedyItemTypeList(userInfo);
		request.setAttribute("item_type_list", itemTypeList);
		return mapping.findForward("view_remedy_apply");
	}

	public ActionForward toDoWork(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.setPageReset(request);
		initialize(request);

		String processName = request.getParameter("process_name");
		String taskName = request.getParameter("task_name");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		List<ProjectRemedyApply> list = applyBo.getToDoWork(userInfo, taskName);
		request.setAttribute("task_name", taskName);
		request.setAttribute("process_name", processName);
		request.getSession().setAttribute("result", list);
		request.getSession().setAttribute("number", list.size());
		return mapping.findForward("toDoWork");
	}

	public ActionForward getFinishedWork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		initialize(request);
		List<ProjectRemedyApply> list = applyBo.getHandeledWorks(userInfo);
		super.setPageReset(request);
		request.getSession().setAttribute("result", list);
		request.getSession().setAttribute("number", list.size());
		return mapping.findForward("finishedWork");
	}

	public ActionForward read(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		initialize(request);

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		RemedyApplyBean applyBean = (RemedyApplyBean) form;
		String id = request.getParameter("id");
		String type = request.getParameter("type");

		applyBo.read(id, type, userInfo);
		log(request, " ���Ķ�����������Ϣ  ����������Ϊ��" + applyBean.getProjectName() + "��",
				" ���̹���");
		return forwardInfoPage(mapping, request, "remedyreadSuccess");
	}

	/**
	 * ִ��ȡ������ά�������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelRemedyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String applyId = request.getParameter("apply_id");
		request.setAttribute("apply_id", applyId);
		return mapping.findForward("cancel_remedy_apply");
	}

	/**
	 * ִ��ȡ��������������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelRemedy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		RemedyApplyBean bean = (RemedyApplyBean) form;
		// bean.setId(request.getParameter("cutId"));
		applyBo.cancelRemedy(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_REMEDY_SUCCESS", url);
	}

}
