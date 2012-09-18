package com.cabletech.baseinfo.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.analysis.beans.CurrentTimeBean;
import com.cabletech.baseinfo.beans.ResourceAssignBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ResourceAssignBO;
import com.cabletech.commons.web.ClientException;
import com.cabletech.planstat.services.CompAnalysisBO;

/**
 * ��Դ���·��� ResourceAssignAction
 * 
 * @author Administrator
 * 
 */
public class ResourceAssignAction extends BaseInfoBaseDispatchAction {
	private ResourceAssignBO service = new ResourceAssignBO();
	private static Logger logger = Logger.getLogger("ResourceAssignAction");
	private CompAnalysisBO compAnalysisBO = new CompAnalysisBO();
	/**
	 * ����Դ��ά��λ��Ϣ���г��ô�ά�µ������߶���Ϣ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward showAssignSubline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		HttpSession session = request.getSession();
		// 1����ά��λ��Ϣ����session��
		String contractorid = request.getParameter("contractor");
		
		session.setAttribute("fromContractor", contractorid);
		//2\ �ô�ά��λ�������·/
		request.setAttribute("lineCollection", service.getLine(contractorid));
		logger.info("contractorid " + contractorid);
		return mapping.findForward("sublineNext2");
	}

	/**
	 * ��ȡҪ���·����߶Σ����䱣�浽session�У�Ȼ����ʾ�߶η������ĵ����Լ�Ҫ������߶���Ϣ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward showAssignSublineResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		HttpSession session = request.getSession();
		// 1��ȡ�ô�������߶���Ϣ�����浽session��
		String sublines = request.getParameter("sublineid");
		session.setAttribute("sublines", sublines);
		ResourceAssignBean RABean = new ResourceAssignBean();
		RABean.setContractor(session.getAttribute("fromContractor").toString());
		request.setAttribute("RABean",RABean);
		UserInfo userinfo = (UserInfo) session.getAttribute( "LOGIN_USER" );
        List contractorList = compAnalysisBO.getContractorInfoList(userinfo);
        List patrolmanList = compAnalysisBO.getPatrolmanInfoList(userinfo);
		request.getSession().setAttribute( "coninfo", contractorList);
        request.getSession().setAttribute( "uinfo", patrolmanList);
		// 2�������߶�ȷ����Ϣ��ȷ����Ϣ�����߶Ρ���·����𡢸�����
		List list = service.getSpecifySubline(sublines);
		request.setAttribute("queryresult", list);
		return mapping.findForward("sublineNext3");
	}
	
	/**
	 * ���Ѳ���߶�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward getSubline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		HttpSession session = request.getSession();
		String lineid = request.getParameter("lineid");
		String contractorid = (String) session.getAttribute("fromContractor");
		String html = service.getSelectOptions(lineid,contractorid);
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(html);
		out.flush();
		return null;
	}

	/**
	 * ���û�ȷ�Ϻ󣬿�ʼ�����߶θ��µĴ�ά��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward assignSubline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		// ȡ��Ŀ���ά��λ��Ѳ����
		String patrolid = request.getParameter("patrolID");
		// sessionȡ����������߶���Ϣ
		String sublines = (String) session.getAttribute("sublines");
		String fromContractor = (String) session.getAttribute("fromContractor");
		logger.info("ԭ��ά��λ ��" + fromContractor);
		// ���з���
		String forward = "72501f";
		if (service.assignSubline(sublines, patrolid)) {
			forward = "72501s";
		}
		session.removeAttribute("fromContractor");
		session.removeAttribute("sublines");
		session.removeAttribute("assignResult");
		return forwardInfoPage(mapping, request, forward);
	}

	/**
	 * ����ԭ�豸������ά��λ��ɸѡ�豸��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward showAssignTerminal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		HttpSession session = request.getSession();
		// 1����ά��λ��Ϣ����session��
		String contractorid = request.getParameter("contractor");
		session.setAttribute("fromContractor", contractorid);
		List termCollection = service.getTerminal(contractorid);
		request.setAttribute("termCollection",termCollection);
		return mapping.findForward("terminalNext2");
	}

	/**
	 * ��ʾ�豸������豸��A��ά����B��ά����ʾ���·����豸��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward showAssignTerminalResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		HttpSession session = request.getSession();
		ResourceAssignBean RABean = new ResourceAssignBean();
		RABean.setContractor(session.getAttribute("fromContractor").toString());
		request.setAttribute("RABean",RABean);
		// 1��ȡ�ô�������豸��Ϣ�����浽session��
		String terminals = request.getParameter("terminalid");
		System.out.println("teminal " +terminals);
		session.setAttribute("terminals", terminals);
		// 2�������豸ȷ����Ϣ��ȷ����Ϣ�����豸��š��ͺš�simid��������
		List list = service.getSpecifyTerminal(terminals);
		request.setAttribute("queryresult", list);
		return mapping.findForward("terminalNext3");
	}

	/**
	 * ���û�ȷ�Ϻ󣬸����û������ߡ���֪ͨ��ά���豸���з���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward assignTerminal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		HttpSession session = request.getSession();
		// ȡ��Ŀ���ά��λ
		String targetContractor = request.getParameter("targetContractor");
		// sessionȡ����������豸��Ϣ
		String terminals = (String) session.getAttribute("terminals");
		// ���з���
		String forward = "72502f";
		if (service.assignTerminal(terminals, targetContractor)) {
			forward = "72502s";
		}
		return forwardInfoPage(mapping, request, forward);
	}
}
