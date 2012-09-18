package com.cabletech.linepatrol.dispatchtask.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchExamBean;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchTaskBean;
import com.cabletech.linepatrol.dispatchtask.services.DispatchExamBO;
import com.cabletech.linepatrol.dispatchtask.services.DispatchTaskBO;

/**
 * 任务派单日常考核
 * @author liusq
 *
 */
public class DispatchExamAction extends BaseDispatchAction {

	private static final long serialVersionUID = 6482492516707401559L;

	/**
	 * 未考核列表
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
		String examFlag = request.getParameter("examFlag");
		String init = request.getParameter("init");
		WebApplicationContext ctx = getWebApplicationContext();
		DispatchExamBO dispatchExamBO = (DispatchExamBO)ctx.getBean("dispatchExamBO");
		DispatchExamBean bean = (DispatchExamBean)form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		//	查询未考核、已考核的列表
		List<DynaBean> examList = null;
		if(!StringUtils.equals("init", init)){
			examList = dispatchExamBO.getExamListByCondition(bean, userInfo, examFlag);
		}
		super.setPageReset(request);
		request.getSession().setAttribute("examList", examList);
		request.getSession().setAttribute("examFlag", examFlag);
		return mapping.findForward("examList");
	}
	
	/**
	 * 转向日常考核界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward examDispatchForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		String dispatchId = request.getParameter("dispatch_id");
		String replyId = request.getParameter("replyId");
		String contractorId = request.getParameter("contractorId");
		String examFlag = request.getParameter("examFlag");
		DispatchTaskBO bo = (DispatchTaskBO) ctx.getBean("dispatchTaskBO");
		DispatchTaskBean bean = bo.viewDispatchTask(dispatchId);
		request.setAttribute("bean", bean);
		request.setAttribute("dispatch_id", dispatchId);
		request.setAttribute("replyId", replyId);
		request.setAttribute("contractorId", contractorId);
		request.setAttribute("examFlag", examFlag);
		return mapping.findForward("examDispatchForm");
	}
	
	/**
	 * 日常考核
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward examDispatch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//获得日常考核对象
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		WebApplicationContext ctx = getWebApplicationContext();
		DispatchExamBO dispatchExamBO = (DispatchExamBO)ctx.getBean("dispatchExamBO");
		dispatchExamBO.examDispatch(appraiseDailyBean,speicalBeans);
		return forwardInfoPage(mapping, request, "examDispatchSuccess");
	}
}
