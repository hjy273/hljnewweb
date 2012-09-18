package com.cabletech.linepatrol.cut.action;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.cabletech.linepatrol.cut.beans.CutAcceptanceBean;
import com.cabletech.linepatrol.cut.module.Cut;
import com.cabletech.linepatrol.cut.module.CutAcceptance;
import com.cabletech.linepatrol.cut.module.CutFeedback;
import com.cabletech.linepatrol.cut.services.CutAcceptanceManager;
import com.cabletech.linepatrol.cut.services.CutManager;

/**
 * ���ϸ�ӹ���
 * 
 * @author liusq
 * 
 */
public class CutAcceptanceAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(CutAcceptanceAction.class
			.getName());

	/**
	 * ���CutManager����
	 */
	private CutAcceptanceManager getCutAcceptanceService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (CutAcceptanceManager) ctx.getBean("cutAcceptanceManager");
	}

	/**
	 * ������ս����������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCutAcceptanceForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String cutId = request.getParameter("cutId");
		Map map = getCutAcceptanceService().addCutAccptanceForm(cutId,
				userInfo.getRegionid());
		Cut cut = (Cut) map.get("cut");
		CutFeedback cutFeedback = (CutFeedback) map.get("cutFeedback");
		String sublineIds = (String) map.get("sublineIds");
		List subline = (List) map.get("subline");
		List approve_info_list = (List)map.get("approve_info_list");
		request.setAttribute("cut", cut);
		request.setAttribute("cutFeedback", cutFeedback);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("subline", subline);
		request.setAttribute("approve_info_list", approve_info_list);
		return mapping.findForward("addCutAcceptanceForm");
	}

	/**
	 * �ж��Ƿ���ڸ�����Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward judgeFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		StringBuffer buf = new StringBuffer("");
		if (files == null) {
			buf.append("no file");
		} else {
			buf.append("file");
		}
		response.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		out.print(buf.toString());
		System.out.println("*******buf.toString():" + buf.toString());
		out.close();
		return null;
	}

	/**
	 * ��Ӹ�����ս���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCutAcceptance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		CutManager cut=(CutManager)ctx.getBean("cutManager");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		CutAcceptanceBean cutAcceptancBean = (CutAcceptanceBean) form;
		try {
			getCutAcceptanceService().addCutAcceptance(cutAcceptancBean,
					userInfo, files);
			log(request, " ��Ӹ�����ս���  ���������Ϊ��"+cut.get(cutAcceptancBean.getCutId()).getCutName()+"��", " ��ӹ��� ");
			return super.forwardInfoPage(mapping, request,
					"addCutAcceptanceSuccess");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("��Ӹ�����ս������������ϢΪ��" + e.getMessage());
			return super.forwardErrorPage(mapping, request,
					"addCutAcceptanceError");
		}
	}

	/**
	 * �༭���ս���ʱ����ص���Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editCutAcceptanceForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String cutId = request.getParameter("cutId");
		Map map = getCutAcceptanceService().editCutAcceptanceForm(cutId,
				userInfo.getRegionid());
		Cut cut = (Cut) map.get("cut");
		CutFeedback cutFeedback = (CutFeedback) map.get("cutFeedback");
		CutAcceptance cutAcceptance = (CutAcceptance) map.get("cutAcceptance");
		String sublineIds = (String) map.get("sublineIds");
		List subline = (List) map.get("subline");
		List approve_info_list = (List)map.get("approve_info_list");
		request.setAttribute("cut", cut);
		request.setAttribute("cutFeedback", cutFeedback);
		request.setAttribute("cutAcceptance", cutAcceptance);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("subline", subline);
		request.setAttribute("approve_info_list", approve_info_list);
		return mapping.findForward("editCutAcceptanceForm");
	}

	/**
	 * ����༭���ս���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editCutAcceptance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		CutManager cut=(CutManager)ctx.getBean("cutManager");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List<FileItem> files = (List) request.getSession()
				.getAttribute("FILES");
		CutAcceptanceBean cutAcceptanceBean = (CutAcceptanceBean) form;
		String cutId=cutAcceptanceBean.getCutId();
		String cutName=cut.get(cutId).getCutName();
		try {
			getCutAcceptanceService().editCutAcceptance(cutAcceptanceBean,
					userInfo, files);
			log(request, " ����༭���ս���  ���������Ϊ��"+cutName+"��", " ��ӹ��� ");
			return super.forwardInfoPage(mapping, request,
					"editCutAcceptanceSuccess");
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("�༭������ս������������ϢΪ��" + e.getMessage());
			return super.forwardErrorPage(mapping, request,
					"editCutAcceptanceError");
		}
	}

	/**
	 * ���ս���ǰ������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cutAcceptanceApproveForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String cutId = request.getParameter("cutId");
		String operater = request.getParameter("operater");
		String env = request.getParameter("env");
		Map map = getCutAcceptanceService().cutAcceptanceApproveForm(cutId);
		Cut cut = (Cut) map.get("cut");
		CutFeedback cutFeedback = (CutFeedback) map.get("cutFeedback");
		CutAcceptance cutAcceptance = (CutAcceptance) map.get("cutAcceptance");
		String sublineIds = (String) map.get("sublineIds");
		List subline = (List) map.get("subline");
		List approve_info_list = (List)map.get("approve_info_list");
		request.setAttribute("cut", cut);
		request.setAttribute("cutFeedback", cutFeedback);
		request.setAttribute("cutAcceptance", cutAcceptance);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("operater", operater);
		request.setAttribute("subline", subline);
		request.setAttribute("env", env);
		request.setAttribute("approve_info_list", approve_info_list);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("approve_cut_acceptance_wap_task");
		}
		return mapping.findForward("cutAcceptanceApproveForm");
	}

	/**
	 * ���ս������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cutAcceptanceApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		CutManager cm=(CutManager)ctx.getBean("cutManager");
		CutAcceptanceBean cutAcceptanceBean = (CutAcceptanceBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String cutId = request.getParameter("cutId");
		String proposer = request.getParameter("proposer");
		String approveResult = cutAcceptanceBean.getApproveResult();
		String env = request.getParameter("env");
		String cutName=cm.get(cutId).getCutName();
		try {
			getCutAcceptanceService().cutAcceptanceApprove(cutAcceptanceBean,
					cutId, userInfo, proposer);
			if (env != null && WAP_ENV.equals(env)) {
				String url = (String) request.getSession().getAttribute(
						"S_BACK_URL");
				if (approveResult.equals("0")) {
					log(request, " ���ս������δͨ��  ���������Ϊ��"+cutName+"��", " ��ӹ��� ");
					return super.forwardWapInfoPageWithUrl(mapping, request,
							"cutAcceptanceApproveUnpass", url);
				} else if (approveResult.equals("1")) {
					log(request, " ���ս������ͨ��  ���������Ϊ��"+cutName+"��", " ��ӹ��� ");
					return super.forwardWapInfoPageWithUrl(mapping, request,
							"cutAcceptanceApprovePass", url);
				} else {
					log(request, " ���ս������ת�� ���������Ϊ��"+cutName+"��", " ��ӹ��� ");
					return super.forwardWapInfoPageWithUrl(mapping, request,
							"cutAcceptanceApproveTransfer", url);
				}
			}
			if (approveResult.equals("0")) {
				log(request, " ���ս������δͨ��  ���������Ϊ��"+cutName+"��", " ��ӹ��� ");
				return super.forwardInfoPage(mapping, request,
						"cutAcceptanceApproveUnpass");
			} else if (approveResult.equals("1")) {
				log(request, " ���ս������ͨ��  ���������Ϊ��"+cutName+"��", " ��ӹ��� ");
				return super.forwardInfoPage(mapping, request,
						"cutAcceptanceApprovePass");
			} else {
				log(request, " ���ս������ת�� ���������Ϊ��"+cutName+"��", " ��ӹ��� ");
				return super.forwardInfoPage(mapping, request,
						"cutAcceptanceApproveTransfer");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("����������ս������������ϢΪ��" + e.getMessage());
			if (env != null && WAP_ENV.equals(env)) {
				String url = (String) request.getSession().getAttribute(
						"S_BACK_URL");
				return super.forwardWapErrorPageWithUrl(mapping, request,
						"cutAcceptanceApproveError", url);
			}
			return super.forwardErrorPage(mapping, request,
					"cutAcceptanceApproveError");
		}
	}

	/**
	 * �鿴���ս���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewCutAcceptance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String cutId = request.getParameter("cutId");
		String isread = request.getParameter("isread");
		String env = request.getParameter("env");
		Map map = getCutAcceptanceService().viewCutAcceptance(cutId);
		request.setAttribute("map", map);
		request.setAttribute("isread", isread);
		request.setAttribute("env", env);
		if (env != null && WAP_ENV.equals(env)) {
			return mapping.findForward("read_cut_acceptance_wap_task");
		}
		return mapping.findForward("viewCutAcceptance");
	}

	/**
	 * ���ĸ��������Ϣ
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
		String cutAcceptanceId = request.getParameter("cutAcceptanceId");
		String env = request.getParameter("env");
		getCutAcceptanceService().readReply(userInfo, approverId,
				cutAcceptanceId);
		if (env != null && WAP_ENV.equals(env)) {
			String url = (String) request.getSession().getAttribute(
					"S_BACK_URL");
			return super.forwardWapInfoPageWithUrl(mapping, request,
					"cutAcceptanceReadReplySuccess", url);
		}
		return super.forwardInfoPage(mapping, request,
				"cutAcceptanceReadReplySuccess");
	}
}
