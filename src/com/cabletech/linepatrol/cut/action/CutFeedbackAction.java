package com.cabletech.linepatrol.cut.action;

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
import com.cabletech.linepatrol.cut.beans.CutFeedbackBean;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.cut.module.CutFeedback;
import com.cabletech.linepatrol.cut.services.CutFeedbackManager;
import com.cabletech.linepatrol.cut.services.CutManager;

/**
 * 材料割接反馈管理
 * 
 * @author liusq
 * 
 */
public class CutFeedbackAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(CutFeedbackAction.class
			.getName());

	/**
	 * 获得CutManager对象
	 */
	private CutFeedbackManager getCutFeedbackService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (CutFeedbackManager) ctx.getBean("cutFeedbackManager");
	}

	/**
	 * 添加反馈前加载信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCutFeedbackForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String regionId = userInfo.getRegionID();
		String cutId = request.getParameter("cutId");
		Map map = getCutFeedbackService().addCutFeedbackForm(cutId, regionId);
		String sublineIds = (String) map.get("sublineIds");
		Cut cut = (Cut) map.get("cut");
		List approve_info_list = (List)map.get("approve_info_list");
		CutFeedback cutFeedback = (CutFeedback) map.get("cutFeedback");
		List<UserInfo> mobiles = (List<UserInfo>) map.get("mobiles");
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("cut", cut);
		request.setAttribute("mobiles", mobiles);
		request.setAttribute("cutFeedback", cutFeedback);
		request.setAttribute("approve_info_list", approve_info_list);
		if (cutFeedback == null) {
			return mapping.findForward("addCutFeedbackForm");
		} else {
			return mapping.findForward("editCutFeedbackForm");
		}
	}

	/**
	 * 保存割接反馈信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCutFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		CutManager cm=(CutManager)ctx.getBean("cutManager");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		CutFeedbackBean cutFeedbackBean = (CutFeedbackBean) form;
		cutFeedbackBean.setObjectId(cutFeedbackBean.getCutId());
		String feedbackType = cutFeedbackBean.getFeedbackType();
		String cutId=cutFeedbackBean.getCutId();
		
		String cutName=cm.get(cutId).getCutName();
		try {
			if ("2".equals(feedbackType)) {
				getCutFeedbackService().tempSaveCutFeedback(cutFeedbackBean,
						userInfo, files);
				log(request,"临时保存割接反馈信息（割接名称为："+cutName+"）","割接管理");
				return super.forwardInfoPage(mapping, request,
						"tempSaveCutFeedbackSuccess");
			} else {
				getCutFeedbackService().addCutFeedback(cutFeedbackBean,
						userInfo, files);
				log(request,"保存割接反馈信息（割接名称为："+cutName+"）","割接管理");
				return super.forwardInfoPage(mapping, request,
						"addCutFeedbackSuccess");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("添加割接反馈出错，出错信息为：" + e.getMessage());
			return super.forwardErrorPage(mapping, request,
					"addCutFeedbackError");
		}
	}

	/**
	 * 修改申请反馈加载数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editCutFeedbackForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String deptId = userInfo.getDeptID();
		String regionId = userInfo.getRegionid();
		String cutId = request.getParameter("cutId");
		Map map = getCutFeedbackService().editCutFeedbackForm(cutId, deptId,
				regionId);
		List approve_info_list = (List)map.get("approve_info_list"); 
		String sublineIds = (String) map.get("sublineIds");
		Cut cut = (Cut) map.get("cut");
		CutFeedback cutFeedback = (CutFeedback) map.get("cutFeedback");
		List<UserInfo> mobiles = (List<UserInfo>) map.get("mobiles");
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("cut", cut);
		request.setAttribute("mobiles", mobiles);
		request.setAttribute("cutFeedback", cutFeedback);
		request.setAttribute("approve_info_list", approve_info_list);
		return mapping.findForward("editCutFeedbackForm");
	}

	/**
	 * 修改反馈信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editCutFeedback(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		CutManager cm=(CutManager)ctx.getBean("cutManager");
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		CutFeedbackBean cutFeedbackBean = (CutFeedbackBean) form;
		String saveflag = request.getParameter("saveflag");
		String cutId=cutFeedbackBean.getCutId();
		String cutName=cm.get(cutId).getCutName();
		try {
			if ("0".equals(saveflag)) {
				getCutFeedbackService().editCutFeedback(cutFeedbackBean,
						userInfo, files);
				log(request,"修改反馈信息（割接名称为："+cutName+"）","割接管理");
				return super.forwardInfoPage(mapping, request,
						"editCutFeedbackSuccess");
			} else {
				getCutFeedbackService().tempSaveCutFeedback(cutFeedbackBean,
						userInfo, files);
				log(request,"临时保存反馈信息（割接名称为："+cutName+"）","割接管理");
				return super.forwardInfoPage(mapping, request,
						"tempSaveCutFeedbackSuccess");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("修改割接反馈出错，出错信息为：" + e.getMessage());
			return super.forwardErrorPage(mapping, request,
					"editCutFeedbackError");
		}
	}

	/**
	 * 反馈前加载信息cut/cutFeedback
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCutFeedbackApproveForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String cutId = request.getParameter("cutId");
		String operater = request.getParameter("operater");
		String regionId = userInfo.getRegionid();
		String env = request.getParameter("env");
		Map map = getCutFeedbackService().addCutFeedbackApproveForm(cutId,
				regionId);
		List approve_info_list = (List)map.get("approve_info_list");
		Cut cut = (Cut) map.get("cut");
		CutFeedback cutFeedback = (CutFeedback) map.get("cutFeedback");
		String sublineIds = (String) map.get("sublineIds");
		request.setAttribute("cut", cut);
		request.setAttribute("cutFeedback", cutFeedback);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("operater", operater);
		request.setAttribute("env", env);
		request.setAttribute("approve_info_list", approve_info_list);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("approve_cut_feedback_wap_task");
		}
		return mapping.findForward("addCutFeedbackApproveForm");
	}

	/**
	 * 申请反馈审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCutFeedbackApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		CutManager cm=(CutManager)ctx.getBean("cutManager");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		CutFeedbackBean cutFeedbackBean = (CutFeedbackBean) form;
		String approveResult = cutFeedbackBean.getApproveResult();
		String feedbackType = cutFeedbackBean.getFeedbackType();
		String env = request.getParameter("env");
		String cutId=cutFeedbackBean.getCutId();
		String cutName=cm.get(cutId).getCutName();
		try {
			getCutFeedbackService().addCutFeedbackApprove(cutFeedbackBean,
					userInfo);
			if (env != null && WAP_ENV.equals(env)) {
				String url = (String) request.getSession().getAttribute(
						"S_BACK_URL");
				if (approveResult.equals(CutConstant.APPROVE_RESULT_NO)) {
					if (CutFeedback.CANCELFLOW.equals(feedbackType)) {
						log(request,"申请反馈审核未通过（割接名称为："+cutName+"）","割接管理");
						return super.forwardWapInfoPageWithUrl(mapping,
								request, "addCutFeedbackApproveUnpass", url);
					} else {
						log(request,"取消申请反馈审核未通过（割接名称为："+cutName+"）","割接管理");
						return super.forwardWapInfoPageWithUrl(mapping,
								request, "cancelCutFeedbackApproveUnpass", url);
					}
				} else if (approveResult
						.equals(CutConstant.APPROVE_RESULT_PASS)) {
					if (CutFeedback.CANCELFLOW.equals(feedbackType)) {
						log(request,"取消申请反馈审核通过（割接名称为："+cutName+"）","割接管理");
						return super.forwardWapInfoPageWithUrl(mapping,
								request, "cancelCutFeedbackApprovePass", url);
					} else {
						log(request,"申请反馈审核通过（割接名称为："+cutName+"）","割接管理");
						return super.forwardWapInfoPageWithUrl(mapping,
								request, "addCutFeedbackApprovePass", url);
					}
				} else {
					log(request,"申请反馈审核转审（割接名称为："+cutName+"）","割接管理");
					return super.forwardWapInfoPageWithUrl(mapping, request,
							"addCutFeedbackApproveTransfer", url);
				}
			}
			if (approveResult.equals(CutConstant.APPROVE_RESULT_NO)) {
				if (CutFeedback.CANCELFLOW.equals(feedbackType)) {
					log(request,"申请反馈审核未通过（割接名称为："+cutName+"）","割接管理");
					return super.forwardInfoPage(mapping, request,
							"addCutFeedbackApproveUnpass");
				} else {
					log(request,"取消申请反馈审核未通过（割接名称为："+cutName+"）","割接管理");
					return super.forwardInfoPage(mapping, request,
							"cancelCutFeedbackApproveUnpass");
				}
			} else if (approveResult.equals(CutConstant.APPROVE_RESULT_PASS)) {
				if (CutFeedback.CANCELFLOW.equals(feedbackType)) {
					log(request,"取消申请反馈审核通过（割接名称为："+cutName+"）","割接管理");
					return super.forwardInfoPage(mapping, request,
							"cancelCutFeedbackApprovePass");
				} else {
					log(request,"申请反馈审核通过（割接名称为："+cutName+"）","割接管理");
					return super.forwardInfoPage(mapping, request,
							"addCutFeedbackApprovePass");
				}
			} else {
				log(request,"申请反馈审核转审（割接名称为："+cutName+"）","割接管理");
				return super.forwardInfoPage(mapping, request,
						"addCutFeedbackApproveTransfer");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("审批割接反馈出错，出错信息为：" + e.getMessage());
			if (env != null && WAP_ENV.equals(env)) {
				String url = (String) request.getSession().getAttribute(
						"S_BACK_URL");
				return super.forwardWapErrorPageWithUrl(mapping, request,
						"addCutFeedbackApproveError", url);
			}
			return super.forwardErrorPage(mapping, request,
					"addCutFeedbackApproveError");
		}
	}

	/**
	 * 查看反馈
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewCutFeedback(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String cutId = request.getParameter("cutId");
		String isread = request.getParameter("isread");
		String env = request.getParameter("env");
		Map map = getCutFeedbackService().viewCutFeedback(cutId);
		request.setAttribute("map", map);
		request.setAttribute("isread", isread);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("read_cut_feedback_wap_task");
		}
		return mapping.findForward("viewCutFeedback");
	}

	/**
	 * 查阅割接反馈
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward readReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String approverId = userInfo.getUserID();
		String cutFeedbackId = request.getParameter("cutFeedbackId");
		String env = request.getParameter("env");
		getCutFeedbackService().readReply(userInfo, approverId, cutFeedbackId);
		if (env != null && WAP_ENV.equals(env)) {
			String url = (String) request.getSession().getAttribute(
					"S_BACK_URL");
			return super.forwardWapInfoPageWithUrl(mapping, request,
					"cutFeedbackReadReplySuccess", url);
		}
		return forwardInfoPage(mapping, request, "cutFeedbackReadReplySuccess");
	}
}
