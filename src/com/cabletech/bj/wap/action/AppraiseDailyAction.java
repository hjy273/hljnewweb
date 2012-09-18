package com.cabletech.bj.wap.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;

public class AppraiseDailyAction extends BaseDispatchAction {
	
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("userInfo", userInfo);
		if ("wap".equals(env) && userInfo != null) {
			return mapping.findForward("index");
		} else {
			return mapping.findForward("relogin");
		}
	}
	
	public ActionForward appraiseDailyForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		request.getSession().setAttribute("cons", cons);
		if ("wap".equals(env) && userInfo != null) {
			return mapping.findForward("appraiseDailyForm");
		} else {
			return mapping.findForward("relogin");
		}
	}
	
	public ActionForward appraiseDaily(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		AppraiseDailyBean appraiseDailyBean=(AppraiseDailyBean)form;
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseDailyDailyBO appraiseDailyBO = (AppraiseDailyDailyBO) ctx.getBean("appraiseDailyDailyBO");
		Map<String, Object> map = null;
		try {
			map = appraiseDailyBO.getAppraiseDailyData(appraiseDailyBean.getContractorId(),
					"input", appraiseDailyBean);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		AppraiseTable appraiseTable = (AppraiseTable) map.get("appraiseTable");
		String[] contractNoArray = (String[]) map.get("contractNoArray");
		String contractorName = (String) map.get("contractorName");
		String tableId = (String) map.get("tableId");
		request.setAttribute("appraiseTable", appraiseTable);
		request.setAttribute("contractNoArray", contractNoArray);
		request.setAttribute("contractorName", contractorName);
		request.setAttribute("contractorId", appraiseDailyBean.getContractorId());
		request.setAttribute("tableId", tableId);
		return mapping.findForward("appraiseDaily");
	}
	public ActionForward saveDaily(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		AppraiseDailyBean appraiseDailyBean=(AppraiseDailyBean)form;
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseDailyDailyBO appraiseDailyBO=(AppraiseDailyDailyBO)ctx.getBean("appraiseDailyDailyBO");
		AppraiseDaily appraiseDaily=appraiseDailyBean.getAppriseDailyFromAppraiseDailyBean();
		appraiseDailyBO.saveAppraiseDaily(appraiseDaily);
		return mapping.findForward("saveAppraiseDailyForm");
	}
}
