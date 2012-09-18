package com.cabletech.linepatrol.maintenance.action;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.maintenance.beans.TestPlanBean;
import com.cabletech.linepatrol.maintenance.module.TestProblem;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

/**
 * 问题光缆
 * @author
 * 
 */
public class ProblemCableAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());


	/**
	 * 转到问题光缆一览表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getProblemCables(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List problems = planBO.getProblems(userinfo,MainTenanceConstant.PROBLEM_STATE_N);
		request.getSession().setAttribute("problems",problems);
		return mapping.findForward("problemCables");
	}
	
	/**
	 * 转到已处理问题光缆一览表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getFinishedProblemCables(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List problems = planBO.getProblems(userinfo,MainTenanceConstant.PROBLEM_STATE_Y);
		request.getSession().setAttribute("finishedProblems",problems);
		return mapping.findForward("finishedProblemCables");
	}
	

	/**
	 * 转到处理光缆问题的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward handleProblemForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String problemId = request.getParameter("pid");
		String act = request.getParameter("act");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestProblem problem = planBO.getTestProblem(problemId);
		String cableid = problem.getTestCablelineId();
		RepeaterSection res = planBO.getRepeaterSection(cableid);
		problem.setCableName(res.getSegmentname());
		request.setAttribute("problem",problem);
		if(act!=null && act.equals("view")){
			return mapping.findForward("viewProblemForm");
		}
		return mapping.findForward("handleProblemForm");
	}

	/**
	 *保存处理光缆问题
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveProblem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestPlanBean bean = (TestPlanBean)form;
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		String problemComment = bean.getProcessComment();
		String problemState = bean.getProblemState();
		String problemMethod = bean.getTestMethod();
		String reason = bean.getReason();
		String testTime = bean.getTestTime();
		try{	
			String id = request.getParameter("pid");
			TestProblem problem = planBO.getTestProblem(id);
			problem.setProcessComment(problemComment);
			problem.setProblemState(problemState);
			problem.setSolveTime(new Date());
			problem.setTestMethod(problemMethod);
			problem.setReason(reason);
			problem.setTestTime(null);
			if(testTime!=null &&!"".equals(testTime)){
				problem.setTestTime(DateUtil.parseDate(testTime));
			}
			planBO.saveTestProblem(problem);
			return forwardInfoPage(mapping, request, "testPlanProblemCableOK");
		}catch(Exception e){
			logger.info("保存问题光缆出现异常:"+e.getMessage());
			e.getStackTrace();
		}
		return forwardErrorPage(mapping, request, "error");
	}
}
