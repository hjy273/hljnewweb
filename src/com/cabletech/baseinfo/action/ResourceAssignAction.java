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
 * 资源重新分配 ResourceAssignAction
 * 
 * @author Administrator
 * 
 */
public class ResourceAssignAction extends BaseInfoBaseDispatchAction {
	private ResourceAssignBO service = new ResourceAssignBO();
	private static Logger logger = Logger.getLogger("ResourceAssignAction");
	private CompAnalysisBO compAnalysisBO = new CompAnalysisBO();
	/**
	 * 根据源代维单位信息，列出该代维下的所有线段信息，
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
		// 1、代维单位信息存入session中
		String contractorid = request.getParameter("contractor");
		
		session.setAttribute("fromContractor", contractorid);
		//2\ 该代维单位负责的线路/
		request.setAttribute("lineCollection", service.getLine(contractorid));
		logger.info("contractorid " + contractorid);
		return mapping.findForward("sublineNext2");
	}

	/**
	 * 获取要重新分配线段，将其保存到session中，然后显示线段分配由哪到哪以及要分配的线段信息。
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
		// 1、取得待分配的线段信息。保存到session中
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
		// 2、加载线段确认信息，确认信息包括线段、线路、类别、负责人
		List list = service.getSpecifySubline(sublines);
		request.setAttribute("queryresult", list);
		return mapping.findForward("sublineNext3");
	}
	
	/**
	 * 获得巡检线段
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
	 * 经用户确认后，开始分配线段给新的代维，
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
		// 取得目标代维单位、巡检组
		String patrolid = request.getParameter("patrolID");
		// session取出待分配的线段信息
		String sublines = (String) session.getAttribute("sublines");
		String fromContractor = (String) session.getAttribute("fromContractor");
		logger.info("原代维单位 ：" + fromContractor);
		// 进行分配
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
	 * 根据原设备所属代维单位，筛选设备，
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
		// 1、代维单位信息存入session中
		String contractorid = request.getParameter("contractor");
		session.setAttribute("fromContractor", contractorid);
		List termCollection = service.getTerminal(contractorid);
		request.setAttribute("termCollection",termCollection);
		return mapping.findForward("terminalNext2");
	}

	/**
	 * 显示设备结果，设备有A代维拨给B代维。显示重新分配设备。
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
		// 1、取得待分配的设备信息。保存到session中
		String terminals = request.getParameter("terminalid");
		System.out.println("teminal " +terminals);
		session.setAttribute("terminals", terminals);
		// 2、加载设备确认信息，确认信息包括设备编号、型号、simid、持有人
		List list = service.getSpecifyTerminal(terminals);
		request.setAttribute("queryresult", list);
		return mapping.findForward("terminalNext3");
	}

	/**
	 * 经用户确认后，给新用户所有者。并通知代维对设备进行分配
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
		// 取得目标代维单位
		String targetContractor = request.getParameter("targetContractor");
		// session取出待分配的设备信息
		String terminals = (String) session.getAttribute("terminals");
		// 进行分配
		String forward = "72502f";
		if (service.assignTerminal(terminals, targetContractor)) {
			forward = "72502s";
		}
		return forwardInfoPage(mapping, request, forward);
	}
}
