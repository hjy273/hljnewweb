package com.cabletech.linepatrol.hiddanger.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.Evaluate;
import com.cabletech.linepatrol.hiddanger.beans.CloseBean;
import com.cabletech.linepatrol.hiddanger.beans.HiddangerExamBean;
import com.cabletech.linepatrol.hiddanger.beans.QueryBean;
import com.cabletech.linepatrol.hiddanger.beans.RegistBean;
import com.cabletech.linepatrol.hiddanger.beans.ReportBean;
import com.cabletech.linepatrol.hiddanger.beans.TreatBean;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.hiddanger.service.HiddangerCloseManager;
import com.cabletech.linepatrol.hiddanger.service.HiddangerConstant;
import com.cabletech.linepatrol.hiddanger.service.HiddangerRegistManager;
import com.cabletech.linepatrol.hiddanger.service.HiddangerReportManager;
import com.cabletech.linepatrol.hiddanger.service.HiddangerTreatManager;
import com.cabletech.linepatrol.trouble.beans.TroubleExamBean;

public class HiddangerAction extends BaseInfoBaseDispatchAction {
	private static final long serialVersionUID = 1L;

	/**
	 * ���������Ǽ�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward registLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		RegistBean registBean = new RegistBean();
		registBean.setCreater(userInfo.getUserID());
		registBean.setCreaterDept(userInfo.getDeptName());
		registBean.setHiddangerNumber(hm.generateCode(userInfo.getDeptName(),userInfo.getDeptID()));

		request.setAttribute("dept", hm.getDeptOptions(userInfo));
		request.setAttribute("registBean", registBean);
		return mapping.findForward("registLink");
	}

	public ActionForward getTroubleCodeSelection(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		String typeId = request.getParameter("typeId");
		String html = hm.getTroubleCodeString(typeId);

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
	 * ��������Ǽ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward addRegist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		RegistBean registBean = (RegistBean) form;
		hm.saveRegist(registBean, userInfo, false);
		log(request, "��������Ǽǣ��Ǽ�����Ϊ��" + registBean.getName() + "��", "��������");
		return forwardInfoPage(mapping, request, "hiddangerRegistAdd");
	}

	/**
	 * �����ֳ��豸�Ǽ�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward terminalRegistResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		request.getSession().setAttribute("codes", hm.getCodes());
		request.getSession().setAttribute("types", hm.getTypes());
		request.getSession().setAttribute("result",
				hm.getTerminalResult(userInfo));
		return mapping.findForward("terminalResult");
	}

	/**
	 * �����ֳ��豸�Ǽ�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward terminalRegistLink(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		RegistBean registBean = hm.getRegistInfo(id);
		registBean.setCreater(userInfo.getUserID());
		registBean.setCreaterDept(userInfo.getDeptName());
		registBean.setHiddangerNumber(hm.generateCode(userInfo.getDeptName(),userInfo.getDeptID()));
		request.setAttribute("dept", hm.getDeptOptions(userInfo));
		request.setAttribute("registBean", registBean);

		return mapping.findForward("terminalRegist");
	}

	/**
	 * �ֳ��豸�Ǽ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward terminalRegist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		RegistBean registBean = (RegistBean) form;
		hm.saveRegist(registBean, userInfo, true);
		log(request, "����ֳ��豸�Ǽǣ��Ǽ�����Ϊ��" + registBean.getName() + "��", "��������");
		return forwardInfoPage(mapping, request, "hiddangerRegistAdd");
	}

	/**
	 * ���ر༭�Ǽ�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward editRegistLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		RegistBean registBean = hm.getRegistInfo(id);
		registBean.setCreater(userInfo.getUserID());
		registBean.setCreaterDept(userInfo.getDeptName());

		request.setAttribute("registBean", registBean);
		return mapping.findForward("editRegistLink");
	}

	/**
	 * �༭�Ǽ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward editRegist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		RegistBean registBean = (RegistBean) form;
		hm.editRegist(registBean, userInfo);
		log(request, "�༭�����Ǽǣ��Ǽ�����Ϊ��" + registBean.getName() + "��", "��������");
		return forwardInfoPage(mapping, request, "hiddangerEdit");
	}

	/**
	 * ������������ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward evalLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		String id = request.getParameter("id");
		RegistBean registBean = hm.getRegistInfo(id);
		registBean.setHiddangerLevel(HiddangerConstant.IGNORE);

		request.setAttribute("registBean", registBean);
		return mapping.findForward("evalLink");
	}

	/**
	 * ��������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward eval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		RegistBean registBean = (RegistBean) form;
		registBean = hm.saveBegintreat(registBean, userInfo);
		request.setAttribute("registBean", registBean);
		log(request,
				"�����������Ǽ�����Ϊ��" + hm.get(registBean.getId()).getName() + "��",
				"��������");
		
		//ֱ��ת��'/WebApp/hiddangerAction.do?method=beginTreat&
		//return forwardInfoPage(mapping, request, "hiddangerEval");
		request.setAttribute("id", registBean.getId());
		return mapping.findForward("beginTreat");
	}

	public ActionForward beginTreat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");

		String id = request.getParameter("id");
		if(id == null){
			id = (String)request.getAttribute("id");
		}
		RegistBean registBean = hm.getRegistInfo(id);
		ReportBean reportBean = hr.getReportInfo(id);

		request.setAttribute("registBean", registBean);
		request.setAttribute("reportBean", reportBean);
		request.setAttribute("principal", hm.getPrincipalOptions(id));

		String level = registBean.getHiddangerLevel();
		if (level.equals(HiddangerConstant.FIRST)) {
			return mapping.findForward("report");
		} else if (level.equals(HiddangerConstant.FOURTH)) {
			return mapping.findForward("treat");
		} else if (level.equals(HiddangerConstant.IGNORE)) {
			return mapping.findForward("findToDoWork");
		}
		else {
			return mapping.findForward("general");
		}
	}

	public ActionForward treat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerTreatManager ht = (HiddangerTreatManager) ctx
				.getBean("hiddangerTreatManager");

		String trunks = request.getParameter("trunk");
		List<String> trunkList = new ArrayList<String>();
		if (StringUtils.isNotBlank(trunks)) {
			trunkList = Arrays.asList(trunks.split(","));
		}

		List<FileItem> files = (List<FileItem>) request.getSession().getAttribute("FILES");

		TreatBean treatBean = (TreatBean) form;
		ht.saveTreat(treatBean, trunkList, userInfo, files);

		return forwardInfoPage(mapping, request, "hiddangerTreat");
	}

	/**
	 * ��������ϱ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward report(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		String trunks = request.getParameter("trunk");
		List<String> trunkList = new ArrayList<String>();
		if (StringUtils.isNotBlank(trunks)) {
			trunkList = Arrays.asList(trunks.split(","));
		}

		ReportBean reportBean = (ReportBean) form;

		String approver = request.getParameter("approver");
		String reader = request.getParameter("reader");
		List<FileItem> files = (List<FileItem>) request.getSession().getAttribute("FILES");

		hr.saveReport(reportBean, trunkList, userInfo, approver, reader, files);
		log(request, "�����ϱ����Ǽ�����Ϊ��"
				+ hm.get(reportBean.getHiddangerId()).getName() + "��", "��������");
		return forwardInfoPage(mapping, request, "hiddangerReport");
	}

	/**
	 * ���ر༭�����ϱ�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward editReportLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");

		String id = request.getParameter("id");
		RegistBean registBean = hm.getRegistInfo(id);
		ReportBean reportBean = hr.getReportInfo(id);

		request.setAttribute("principal", hm.getPrincipalOptions(id));
		request.setAttribute("registBean", registBean);
		request.setAttribute("reportBean", reportBean);
		return mapping.findForward("editReportLink");
	}

	/**
	 * �༭�����ϱ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward editReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		ReportBean reportBean = (ReportBean) form;

		String trunks = request.getParameter("trunk");
		List<String> trunkList = new ArrayList<String>();
		if (StringUtils.isNotBlank(trunks)) {
			trunkList = Arrays.asList(trunks.split(","));
		}

		List<FileItem> files = (List<FileItem>) request.getSession()
				.getAttribute("FILES");
		hr.editReport(reportBean, trunkList, userInfo, files);
		log(request, "�༭�����ϱ����Ǽ�����Ϊ��"
				+ hm.get(reportBean.getHiddangerId()).getName() + "��", "��������");
		return forwardInfoPage(mapping, request, "hiddangerEdit");
	}

	public ActionForward general(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");

		String trunks = request.getParameter("trunk");
		List<String> trunkList = new ArrayList<String>();
		if (StringUtils.isNotBlank(trunks)) {
			trunkList = Arrays.asList(trunks.split(","));
		}

		ReportBean reportBean = (ReportBean) form;
		reportBean.setAuthorId(userInfo.getUserID());
		List<FileItem> files = (List<FileItem>) request.getSession()
				.getAttribute("FILES");

		hr.saveReport(reportBean, trunkList, userInfo, files);

		return forwardInfoPage(mapping, request, "hiddangerReport");
	}

	/**
	 * �������ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward approveLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");

		String id = request.getParameter("id");
		String transfer = request.getParameter("transfer");
		RegistBean registBean = hm.getRegistInfo(id, userInfo);
		ReportBean reportBean = hr.getReportInfo(id);

		request.setAttribute("transfer", transfer);
		request.setAttribute("registBean", registBean);
		request.setAttribute("reportBean", reportBean);
		if (transfer.equals("1")) {
			return mapping.findForward("transferApprove");
		} else {
			return mapping.findForward("approve");
		}
	}

	/**
	 * ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward approve(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String userId = userInfo.getUserID();
		String hiddangerId = request.getParameter("hiddangerId");
		String approveResult = request.getParameter("approveResult");
		String approveRemark = request.getParameter("approveRemark");

		ApproveInfo approve = new ApproveInfo();
		approve.setObjectId(hiddangerId);
		approve.setApproverId(userId);
		approve.setApproveResult(approveResult);
		approve.setApproveRemark(approveRemark);

		hr.approve(approve, userInfo);
		if (approveResult.equals("1")) {
			log(request, "�������ͨ�����Ǽ�����Ϊ��" + hm.get(hiddangerId).getName() + "��",
					"��������");
			return forwardInfoPage(mapping, request, "hiddangerApprove");
		} else {
			log(request,
					"�������δͨ�����Ǽ�����Ϊ��" + hm.get(hiddangerId).getName() + "��",
					"��������");
			return forwardInfoPage(mapping, request, "hiddangerNotApprove");
		}
	}

	/**
	 * ת�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward transferApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String approver = request.getParameter("approver");
		String id = request.getParameter("hiddangerId");
		hr.transferApprove(id, approver, userInfo);

		log(request, "�������ת�󣨵Ǽ�����Ϊ��" + hm.get(id).getName() + "��", "��������");
		return forwardInfoPage(mapping, request, "hiddangerTransferApprove");
	}

	/**
	 * ���عر�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward closeLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");
		HiddangerCloseManager hc = (HiddangerCloseManager) ctx
				.getBean("hiddangerCloseManager");

		String id = request.getParameter("id");
		RegistBean registBean = hm.getRegistInfo(id);
		ReportBean reportBean = hr.getReportInfo(id);
		CloseBean closeBean = hc.getCloseInfo(id);
		List<Map> plans = hm.getSplans(id);

		request.setAttribute("registBean", registBean);
		request.setAttribute("reportBean", reportBean);
		request.setAttribute("closeBean", closeBean);
		request.setAttribute("plans", plans);
		request.setAttribute("planStats", hm.getWatchPlanStats(plans));

		return mapping.findForward("reportClose");
	}

	/**
	 * �����ر�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward reportClose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerCloseManager hc = (HiddangerCloseManager) ctx
				.getBean("hiddangerCloseManager");
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		CloseBean closeBean = (CloseBean) form;
		String hiddangerId = request.getParameter("hiddangerId");
		closeBean.setHiddangerId(hiddangerId);

		String approver = request.getParameter("approver");
		String reader = request.getParameter("reader");

		hc.reportClose(closeBean, userInfo, approver, reader);
		log(request, "�����رգ��Ǽ�����Ϊ��" + hm.get(hiddangerId).getName() + "��",
				"��������");
		return forwardInfoPage(mapping, request, "hiddangerClose");
	}

	/**
	 * ���ر༭�ر�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward editCloseLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");
		HiddangerCloseManager hc = (HiddangerCloseManager) ctx
				.getBean("hiddangerCloseManager");

		String id = request.getParameter("id");
		RegistBean registBean = hm.getRegistInfo(id);
		ReportBean reportBean = hr.getReportInfo(id);
		CloseBean closeBean = hc.getCloseInfo(id);
		List<Map> plans = hm.getSplans(id);

		request.setAttribute("registBean", registBean);
		request.setAttribute("reportBean", reportBean);
		request.setAttribute("closeBean", closeBean);
		request.setAttribute("plans", plans);
		request.setAttribute("planStats", hm.getWatchPlanStats(plans));

		return mapping.findForward("editCloseLink");
	}

	/**
	 * �༭�ر�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward editClose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerCloseManager hc = (HiddangerCloseManager) ctx
				.getBean("hiddangerCloseManager");
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		CloseBean closeBean = (CloseBean) form;

		hc.editClose(closeBean, userInfo);
		log(request, "�༭�����رգ��Ǽ�����Ϊ��"
				+ hm.get(closeBean.getHiddangerId()).getName() + "��", "��������");
		return forwardInfoPage(mapping, request, "hiddangerEdit");
	}

	/**
	 * ���عر����ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward closeApproveLink(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");
		HiddangerCloseManager hc = (HiddangerCloseManager) ctx
				.getBean("hiddangerCloseManager");

		String id = request.getParameter("id");
		String transfer = request.getParameter("transfer");
		RegistBean registBean = hm.getRegistInfo(id, userInfo);
		ReportBean reportBean = hr.getReportInfo(id);
		CloseBean closeBean = hc.getCloseInfo(id);
		List<Map> plans = hm.getSplans(id);

		request.setAttribute("registBean", registBean);
		request.setAttribute("reportBean", reportBean);
		request.setAttribute("closeBean", closeBean);
		request.setAttribute("plans", plans);
		request.setAttribute("planStats", hm.getWatchPlanStats(plans));

		if (transfer.equals("1")) {
			return mapping.findForward("closeTransferApprove");
		} else {
			return mapping.findForward("closeApprove");
		}
	}

	/**
	 * �����ر����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward closeApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerCloseManager hc = (HiddangerCloseManager) ctx
				.getBean("hiddangerCloseManager");

		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String userId = userInfo.getUserID();
		String hiddangerId = request.getParameter("hiddangerId");
		String approveResult = request.getParameter("approveResult");
		String approveRemark = request.getParameter("approveRemark");

		ApproveInfo approve = new ApproveInfo();
		approve.setObjectId(hiddangerId);
		approve.setApproverId(userId);
		approve.setApproveResult(approveResult);
		approve.setApproveRemark(approveRemark);

		hc.approve(approve, userInfo);
		if (approveResult.equals("1")) {
			log(request, "�����ر����ͨ�����Ǽ�����Ϊ��" + hm.get(hiddangerId).getName()
					+ "��", "��������");
			return forwardInfoPage(mapping, request, "hiddangerCloseApprove");
		} else {
			log(request, "�����ر����δͨ�����Ǽ�����Ϊ��" + hm.get(hiddangerId).getName()
					+ "��", "��������");
			return forwardInfoPage(mapping, request, "hiddangerCloseNotApprove");
		}
	}

	/**
	 * �ر�ת�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward closeTransferApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerCloseManager hc = (HiddangerCloseManager) ctx
				.getBean("hiddangerCloseManager");
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String approver = request.getParameter("approver");
		String hiddangerId = request.getParameter("hiddangerId");
		log(request, "�����ر�ת�󣨵Ǽ�����Ϊ��" + hm.get(hiddangerId).getName() + "��",
				"��������");
		hc.closeTransferApprove(hiddangerId, approver, userInfo);

		return forwardInfoPage(mapping, request, "hiddangerTransferApprove");
	}

	public ActionForward queryLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		QueryBean queryBean = (QueryBean) form;
		// ���session�еĲ�ѯ�����Ͳ�ѯ���
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("result");
		request.getSession().setAttribute("queryBean", queryBean);
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		request.setAttribute("dept", hm.getDeptOptions(userInfo));
		return mapping.findForward("queryLink");
	}

	/**
	 * ��ѯҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		QueryBean queryBean ;
		if (null == request.getParameter("isQuery")) {
			queryBean = (QueryBean) request.getSession().getAttribute(
					"queryCondition");
		} else {
			queryBean = (QueryBean) form;
			request.getSession().setAttribute("queryCondition", queryBean);
		}
		if (queryBean == null) {
			queryBean = new QueryBean();
		}
		request.getSession().setAttribute("dept", hm.getDeptOptions(userInfo));
		request.getSession().setAttribute("codes", hm.getCodes());
		request.getSession().setAttribute("types", hm.getTypes());
		request.getSession().setAttribute("depts", hm.getDepts());
		request.getSession().setAttribute("users", hm.getUsers());
		request.getSession().setAttribute("result",hm.getQueryResult(queryBean, userInfo));
		request.getSession().setAttribute("queryBean", queryBean);
		request.setAttribute("task_names", queryBean.getTaskNames());
		super.setPageReset(request);
		return mapping.findForward("queryLink");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerTreatManager ht = (HiddangerTreatManager) ctx
				.getBean("hiddangerTreatManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");
		HiddangerCloseManager hc = (HiddangerCloseManager) ctx
				.getBean("hiddangerCloseManager");

		String id = request.getParameter("id");
		String iswin = request.getParameter("param");
		RegistBean registBean = hm.getRegistInfo(id);
		TreatBean treatBean = ht.getTreatInfo(id);
		ReportBean reportBean = hr.getReportInfo(id);
		CloseBean closeBean = hc.getCloseInfo(id);
		Evaluate evaluate = hm.getEvaluate(id);
		List<Map> plans = hm.getSplans(id);

		request.getSession().setAttribute("iswin", iswin);
		request.setAttribute("registBean", registBean);
		request.setAttribute("treatBean", treatBean);
		request.setAttribute("reportBean", reportBean);
		request.setAttribute("closeBean", closeBean);
		request.setAttribute("evaluate", evaluate);
		request.setAttribute("plans", plans);
		request.setAttribute("planStats", hm.getWatchPlanStats(plans));

		return mapping.findForward("view");
	}

	/**
	 * �鿴����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward viewHiddanger(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");

		String id = request.getParameter("id");
		RegistBean registBean = hm.getRegistInfo(id);
		ReportBean reportBean = hr.getReportInfo(id);

		request.setAttribute("registBean", registBean);
		request.setAttribute("reportBean", reportBean);

		return mapping.findForward("viewHiddanger");
	}

	/**
	 * ���칤��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward findToDoWork(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String taskName = request.getParameter("task_name");
		List<HiddangerRegist> list = hm.getToDoWork(userInfo, taskName);
		request.setAttribute("task_name", taskName);
		session.setAttribute("result", list);
		session.setAttribute("number", list.size());
		session.setAttribute("codes", hm.getCodes());
		session.setAttribute("types", hm.getTypes());
		session.setAttribute("depts", hm.getDepts());
		return mapping.findForward("toDoWork");
	}

	/**
	 * �Ѱ칤��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward getFinishedWork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		super.setPageReset(request);
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		List<HiddangerRegist> list = hm.getHandeledWorks(userInfo);
		session.setAttribute("result", list);
		session.setAttribute("number", list.size());
		session.setAttribute("codes", hm.getCodes());
		session.setAttribute("types", hm.getTypes());
		session.setAttribute("depts", hm.getDepts());
		return mapping.findForward("finishedWork");
	}

	public ActionForward examLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerTreatManager ht = (HiddangerTreatManager) ctx
				.getBean("hiddangerTreatManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");
		HiddangerCloseManager hc = (HiddangerCloseManager) ctx
				.getBean("hiddangerCloseManager");

		String id = request.getParameter("id");
		RegistBean registBean = hm.getRegistInfo(id);
		TreatBean treatBean = ht.getTreatInfo(id);
		ReportBean reportBean = hr.getReportInfo(id);
		CloseBean closeBean = hc.getCloseInfo(id);
		List<Map> plans = hm.getSplans(id);

		request.setAttribute("id", id);
		request.setAttribute("registBean", registBean);
		request.setAttribute("treatBean", treatBean);
		request.setAttribute("reportBean", reportBean);
		request.setAttribute("closeBean", closeBean);
		request.setAttribute("plans", plans);
		request.setAttribute("planStats", hm.getWatchPlanStats(plans));

		return mapping.findForward("examLink");
	}

	/**
	 * ������������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward exam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");

		//����ճ����˶���
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		
		hm.saveEvaluate(userInfo, id, appraiseDailyBean,speicalBeans);
		log(request, "�������֣��Ǽ�����Ϊ��" + hm.get(id).getName() + "��", "��������");
		return forwardInfoPage(mapping, request, "examComplete");
	}

	public ActionForward read(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		String type = request.getParameter("type");

		hm.read(id, type, userInfo);
		return forwardInfoPage(mapping, request, "readSuccess");
	}

	public ActionForward hiddangerStatLink(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		// ���session�еĲ�ѯ�����Ͳ�ѯ���
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("result");
		return mapping.findForward("statLink");
	}

	public ActionForward queryStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");

		QueryBean queryBean = (QueryBean) form;
		if (null == request.getParameter("isQuery")) {
			queryBean = (QueryBean) request.getSession().getAttribute(
					"queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", queryBean);
		}
		if (queryBean == null) {
			queryBean = new QueryBean();
		}
		request.getSession().setAttribute("codes", hm.getCodes());
		request.getSession().setAttribute("types", hm.getTypes());
		request.getSession().setAttribute("depts", hm.getDepts());
		request.getSession().setAttribute("users", hm.getUsers());
		request.getSession().setAttribute("result", hm.getQueryStat(queryBean));

		super.setPageReset(request);
		return mapping.findForward("statList");
	}

	// ͳ����Ϣ��������Ϣ
	public ActionForward viewHiddangerForStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		HiddangerReportManager hr = (HiddangerReportManager) ctx
				.getBean("hiddangerReportManager");

		String id = request.getParameter("id");
		RegistBean registBean = hm.getRegistInfo(id);
		ReportBean reportBean = hr.getReportInfo(id);
		List<Map> plans = hm.getSplans(id);
		String planIds = hm.getSplanIds(plans);

		request.setAttribute("registBean", registBean);
		request.setAttribute("reportBean", reportBean);
		request.setAttribute("planIds", planIds);
		request.setAttribute("plans", plans);
		request.setAttribute("planStats", hm.getWatchPlanStats(plans));

		return mapping.findForward("hiddangerForStat");
	}

	// �����ɵ�����ͼҳ��
	public ActionForward viewHideDangerProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		HiddangerRegistManager bo = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		String condition = "";
		List waitHandleTaskNum = bo.queryForHandleHideDangerNum(condition,
				userInfo);
		request.setAttribute("wait_confirm_num", waitHandleTaskNum.get(0));
		request.setAttribute("wait_report_num", waitHandleTaskNum.get(1));
		request.setAttribute("wait_add_approve_num", waitHandleTaskNum.get(2));
		request.setAttribute("wait_make_plan_num", waitHandleTaskNum.get(3));
		request.setAttribute("wait_make_plan_approve_num", waitHandleTaskNum
				.get(4));
		request.setAttribute("wait_end_plan_num", waitHandleTaskNum.get(5));
		request.setAttribute("wait_end_plan_approve_num", waitHandleTaskNum
				.get(6));
		request.setAttribute("wait_close_num", waitHandleTaskNum.get(7));
		request
				.setAttribute("wait_close_approve_num", waitHandleTaskNum
						.get(8));
		request.setAttribute("wait_evaluate_num", waitHandleTaskNum.get(9));
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null
				&& !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("view_hide_danger_process");
	}

	/**
	 * ִ��ȡ�����������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelHideDangerForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String hdieDangerId = request.getParameter("hide_danger_id");
		request.setAttribute("hide_danger_id", hdieDangerId);
		return mapping.findForward("cancel_hide_danger");
	}

	/**
	 * ִ��ȡ����������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelHideDanger(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		HiddangerRegistManager bo = (HiddangerRegistManager) ctx
				.getBean("hiddangerRegistManager");
		RegistBean bean = (RegistBean) form;
		bo.cancelHideDanger(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_HIDE_DANGER_SUCCESS", url);
	}
}