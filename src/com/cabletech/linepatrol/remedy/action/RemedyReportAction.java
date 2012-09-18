package com.cabletech.linepatrol.remedy.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.remedy.service.RemedyReportManager;

public class RemedyReportAction extends BaseInfoBaseDispatchAction {

	RemedyReportManager rrm = new RemedyReportManager();
	
	/**
	 * 获取前台要显示的数据（施工单位、维护区域）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryReceiveReportForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String regionId = user.getRegionid();
		List towns = rrm.queryAllTown();
		List contractors = rrm.queryAllContractor(regionId);
		request.setAttribute("towns", towns);
		request.setAttribute("contractors", contractors);
		return mapping.findForward("checkReportInfo");
	}
	
	/**
	 * 获取查询数据，放入list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCheckReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
		String contractorId = (String)request.getParameter("selectContractor");
		String remedyDate = (String)request.getParameter("remedyDate");
		String townId = (String)request.getParameter("selectTown");
		super.setPageReset(request);
		if(user.getDeptype().equals("2")){
			contractorId = user.getDeptID();
		}
		List list = rrm.getCheckReport(contractorId, remedyDate, townId);
		request.setAttribute("list", list);
		request.getSession().setAttribute("report", list);
		return mapping.findForward("showCheckReport");
	}
	
	/**
	 * 执行导出Excel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute("report");
		rrm.exportStorage(list,response);
		return null;
	}
}
