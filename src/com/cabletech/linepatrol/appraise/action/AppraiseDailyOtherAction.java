package com.cabletech.linepatrol.appraise.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;

public class AppraiseDailyOtherAction extends BaseDispatchAction {
	/**
	 * 其他日常考核页面加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward appraiseDailyOtherForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		request.getSession().setAttribute("cons", cons);
		return mapping.findForward("appraiseDailyOtherForm");
	}
	
	public ActionForward appraiseDailyOther(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		AppraiseDailyBean appraiseDailyBean=(AppraiseDailyBean)form;
		request.setAttribute("contractorId", appraiseDailyBean.getContractorId());
		return mapping.findForward("appraiseDailyOther");
	}
	public ActionForward saveAppraiseDailyOther(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		AppraiseDailyBean appraiseDailyBean=(AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		if(appraiseDailyBean==null){
			return this.forwardInfoPage(mapping, request, "appraiseDailyOtherError");
		}
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseDailyDailyBO appraiseDailyDailyBO=(AppraiseDailyDailyBO)ctx.getBean("appraiseDailyDailyBO");
		AppraiseDaily appraiseDaily=appraiseDailyBean.getAppriseDailyFromAppraiseDailyBean();
		appraiseDailyDailyBO.saveBusinessEqId(appraiseDaily);
		return this.forwardInfoPage(mapping, request, "appraiseDailyOtherSuccess");
	}
	/**
	 * 加载其他项目日常考核查询页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseDailyOtherListForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		request.setAttribute("cons", cons);
		request.getSession().removeAttribute("appraiseDailys");
		return mapping.findForward("queryAppraiseDailyOtherFrom");
	}
	public ActionForward queryAppraiseDailyOtherList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		UserInfoBO userInfoBO=new UserInfoBO();
		String contractorId = request.getParameter("contractorId");
		String contractNO = request.getParameter("contractNO");
		String appraiseTime = request.getParameter("appraiseTime");
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseDailyDailyBO appraiseDailyBO=(AppraiseDailyDailyBO)ctx.getBean("appraiseDailyDailyBO");
		List<AppraiseDaily> appraiseDailys = appraiseDailyBO.queryAppraiseDaily(contractorId,contractNO,appraiseTime,AppraiseDaily.BUSINESSMODULE_OTHER);
		request.getSession().setAttribute("contractor", contractorBO.getContractorForMap(userInfo));
		request.getSession().setAttribute("appraiseDailys", appraiseDailys);
		request.setAttribute("appraiseTime", appraiseTime);
		request.setAttribute("userIdAndName", userInfoBO.loadAllUserIdAndName());
		return mapping.findForward("queryAppraiseDailyOtherFrom");
	}
	
	/**
	 * 查看日常考核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewAppraiseDaily(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String contractorId=request.getParameter("contractorId");
		request.setAttribute("id", id);
		request.setAttribute("contractorId", contractorId);
		return mapping.findForward("viewAppraiseDaily");
	}

}
