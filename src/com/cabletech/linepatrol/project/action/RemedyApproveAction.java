package com.cabletech.linepatrol.project.action;

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

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.project.constant.ExecuteCode;
import com.cabletech.linepatrol.project.constant.RemedyConstant;
import com.cabletech.linepatrol.project.service.CountyInfoBO;
import com.cabletech.linepatrol.project.service.RemedyApplyManager;
import com.cabletech.linepatrol.project.service.RemedyApproveManager;
import com.cabletech.linepatrol.project.service.RemedyItemManager;
import com.cabletech.linepatrol.project.service.RemedyTypeManager;

public class RemedyApproveAction extends BaseDispatchAction {
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

	/**
	 * 执行修缮申请的查看处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approveApplyForm(ActionMapping mapping, ActionForm form,
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

		List itemList = itemBo.getRemedyItemList(userInfo);
		request.setAttribute("item_list", itemList);
		List itemTypeList = itemTypeBo.getRemedyItemTypeList(userInfo);
		request.setAttribute("item_type_list", itemTypeList);
		if(type.equals(RemedyConstant.IS_TRANSFER)){
			return mapping.findForward("transapprove_remedy_apply");
		}else{
			request.setAttribute("type", type);
			return mapping.findForward("approve_remedy_apply");
		}
	}

	public ActionForward approveApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx=getWebApplicationContext();
		RemedyApplyManager applyManager=(RemedyApplyManager)ctx.getBean("remedyApplyManager");
		initialize(request);
		String applyId = request.getParameter("apply_id");
		String backUrl = (String) request.getSession().getAttribute("S_BACK_URL");

		String approveResult = request.getParameter("approveResult");
		String approveRemark = request.getParameter("approveRemark");
		String approver = request.getParameter("approver");
		
		ApproveInfo approve = new ApproveInfo();
		approve.setObjectId(applyId);
		approve.setApproverId(userInfo.getUserID());
		approve.setApproveResult(approveResult);
		approve.setApproveRemark(approveRemark);
		
		String operationCode = ExecuteCode.FAIL_CODE;
		try {
			operationCode = approveBo.approveApply(userInfo, approve, applyId, approver);
		} catch (Exception e) {
			logger.error("执行审批修缮申请业务异常：" + e);
		}
		if (ExecuteCode.NO_DATA_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotFindDataError", backUrl);
		}
		if (ExecuteCode.NOT_APPROVED_APPLY_ERROR.equals(operationCode)) {
			return super.forwardErrorPageWithUrl(mapping, request,
					"NotApprovedApply", backUrl);
		}
		if (!"transcation".equals(request.getParameter("operator"))) {
			log(request, " 审核修缮申请  （工程名称为：" + applyManager.get(applyId).getProjectName() + "）", " 工程管理");
		} else {
			log(request, " 转审修缮申请  （工程名称为：" + applyManager.get(applyId).getProjectName() + "）", " 工程管理");
		}
		return super.forwardInfoPageWithUrl(mapping, request, "SuccessCode",
				backUrl);
	}

}
