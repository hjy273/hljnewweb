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
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.module.AppraiseDaily;
import com.cabletech.linepatrol.appraise.service.AppraiseConstant;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyYearEndBO;

public class AppraiseDailyYearEndAction extends BaseDispatchAction {
	/**
	 * ���ռ��������ҳ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward appraiseDailyYearEndForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		String lastYear=DateUtil.getLastYear();
		request.getSession().setAttribute("cons", cons);
		request.setAttribute("lastYear", lastYear);
		return mapping.findForward("appraiseDailyYearEndForm");
	}
	/**
	 * ���ռ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward appraiseDailyYearEnd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		AppraiseDailyBean appraiseDailyBean=(AppraiseDailyBean)form;
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseDailyBO appraiseDailyBO=(AppraiseDailyBO)ctx.getBean("appraiseDailyYearEndBO");
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		appraiseDailyBean.setAppraiser(userInfo.getUserID());
		List<AppraiseDaily> appraiseDailies=appraiseDailyBO.getAppraiseDailyByBean(appraiseDailyBean,AppraiseConstant.TYPE_YEAREND);
		if(appraiseDailies.size()>0){
			return forwardInfoPage(mapping, request, "appraiseDailyYearEndRepeatError");
		}
		request.setAttribute("contractNo", appraiseDailyBean.getContractNo());
		request.setAttribute("year", appraiseDailyBean.getYear());
		request.setAttribute("contractorId", appraiseDailyBean.getContractorId());
		return mapping.findForward("appraiseDailyYearEnd");
	}
	/**
	 * ��̬����ҳ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveForm(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)form;
		request.getSession().setAttribute("appraiseDailyYearEndBean", appraiseDailyBean);
		return null;
	}
	/**
	 * �������ռ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveAppraiseDailyYearEnd(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response){
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyYearEndBean");
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseDailyYearEndBO appraiseDailyYearEndBO=(AppraiseDailyYearEndBO)ctx.getBean("appraiseDailyYearEndBO");
		appraiseDailyYearEndBO.saveDailyYearEnd(appraiseDailyBean);
		return forwardInfoPage(mapping, request, "appraiseDailyYearEndSuccess");
	}
	
	/**
	 * ���ռ���ֲ�ѯҳ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseDailyYearEndListForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		ContractorBO contractorBO = new ContractorBO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List cons = contractorBO.getAllContractor(userInfo);
		String lastYear=DateUtil.getLastYear();
		request.getSession().setAttribute("cons", cons);
		request.setAttribute("lastYear", lastYear);
		request.getSession().removeAttribute("appraiseDailys");
		return mapping.findForward("queryAppraiseDailyYearEndListForm");
	}
	/**
	 * ���ռ���ֲ�ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAppraiseDailyYearEndList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		UserInfoBO userInfoBO=new UserInfoBO();
		ContractorBO contractorBO=new ContractorBO();
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("LOGIN_USER");
		AppraiseDailyBean appraiseDailyBean=(AppraiseDailyBean)form;
		WebApplicationContext ctx=getWebApplicationContext();
		AppraiseDailyYearEndBO appraiseDailyYearEndBO=(AppraiseDailyYearEndBO)ctx.getBean("appraiseDailyYearEndBO");
		List<AppraiseDaily> appraiseDailys=appraiseDailyYearEndBO.getAppraiseDailyByBean(appraiseDailyBean,AppraiseConstant.TYPE_YEAREND);
		request.getSession().setAttribute("appraiseDailys", appraiseDailys);
		request.setAttribute("userIdAndName", userInfoBO.loadAllUserIdAndName());
		request.setAttribute("contractor", contractorBO.getContractorForMap(userInfo));
		return mapping.findForward("queryAppraiseDailyYearEndListForm");
	}
	/**
	 * �鿴���ռ����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewAppraiseDailyYearEnd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		String contractorId=request.getParameter("contractorId");
		request.setAttribute("id", id);
		request.setAttribute("contractorId", contractorId);
		return mapping.findForward("viewAppraiseDailyYearEnd");
	}
}
