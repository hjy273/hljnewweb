package com.cabletech.linepatrol.overhaul.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.overhaul.beans.OverHaulExamBean;
import com.cabletech.linepatrol.overhaul.service.OverHaulExamManager;
import com.cabletech.linepatrol.overhaul.service.OverhaulManager;

/**
 * 大修项目日常考核
 * 
 * @author liusq
 * 
 */
public class OverHaulExamAction extends BaseDispatchAction {

	private static final long serialVersionUID = 5491016759070796096L;

	/**
	 * 大修项目待考核列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward examList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String init = request.getParameter("init");
		OverHaulExamBean bean = (OverHaulExamBean)form;
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		OverHaulExamManager ohem = (OverHaulExamManager) ctx.getBean("overHaulExamManager");
		List<DynaBean> examList = null;
		if(!StringUtils.equals("init", init)){
			examList = ohem.getExamListByCondition(bean, userInfo);
		}
		session.setAttribute("examList", examList);
		return mapping.findForward("examList");
	}
	
	/**
	 * 转向考核大修项目界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward examOverHaulForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String applyId = request.getParameter("applyId");
		String contractorId = request.getParameter("contractorId");
		WebApplicationContext ctx = getWebApplicationContext();
		OverhaulManager ohm = (OverhaulManager) ctx.getBean("overhaulManager");

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");

		String id = request.getParameter("id");
		String type = request.getParameter("type");

		request.setAttribute("type", type);
		request.setAttribute("cons", ohm.loadOverHaulCon(id));
		request.setAttribute("OverHaul", ohm.getViewOverHaul(id, userInfo));
		request.setAttribute("applyId", applyId);
		request.setAttribute("contractorId", contractorId);
		return mapping.findForward("overHaulExamForm");
	}
	
	/**
	 * 考核大修项目
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward examOverHaul(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//获得日常考核对象
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		WebApplicationContext ctx = getWebApplicationContext();
		OverHaulExamManager ohem = (OverHaulExamManager) ctx.getBean("overHaulExamManager");
		ohem.examOverHaul(appraiseDailyBean,speicalBeans);
		return forwardInfoPage(mapping, request, "examOverHaulSuccess");
	}
}
