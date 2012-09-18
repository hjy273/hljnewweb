package com.cabletech.linepatrol.maintenance.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.maintenance.beans.TestPlanQueryStatBean;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanExportBO;

/**
 * 测试计划查询统计
 * 
 * @author
 * 
 */
public class TestPlanQueryStatAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到测试计划查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryTestPlanForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		List<Contractor> list = planBO.getContractors(userInfo);
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("deptype", userInfo.getDeptype());
		request.getSession().setAttribute("conId", userInfo.getDeptID());
		request.getSession().setAttribute("conName", userInfo.getDeptName());
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("plans");
		return mapping.findForward("queryTestPlan");
	}

	/**
	 * 转到测试计划页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward statTestPlanForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		String deptype = user.getDeptype();
		if (deptype.equals("1")) {
			List<Contractor> cons = planBO.getContractors(user);
			request.getSession().setAttribute("cons", cons);
		}
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("plans");
		return mapping.findForward("statTestPlan");
	}

	/**
	 * 根据条件查询测试计划列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTestPlans(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestPlanQueryStatBean bean = (TestPlanQueryStatBean) form;
		String[] types = request.getParameterValues("planType");
		bean.setPlanType(types);
		if (null == request.getParameter("isQuery")) {
			bean = (TestPlanQueryStatBean) request.getSession().getAttribute(
					"queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", bean);
		}
		if (null == bean) {
			bean = new TestPlanQueryStatBean();
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		List plans = planBO.getTestPlans(bean, user, "");
		request.getSession().setAttribute("plans", plans);
		request.setAttribute("task_names", bean.getTaskNames());
		super.setPageReset(request);
		return mapping.findForward("testPlans");
	}

	/**
	 * 根据条件统计测试计划列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward statTestPlans(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestPlanQueryStatBean bean = (TestPlanQueryStatBean) form;
		String[] types = request.getParameterValues("planType");
		bean.setPlanType(types);
		if (null == request.getParameter("isQuery")) {
			bean = (TestPlanQueryStatBean) request.getSession().getAttribute(
					"queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition", bean);
		}
		if (null == bean) {
			bean = new TestPlanQueryStatBean();
		}
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		String act = "stat";
		List plans = planBO.getTestPlans(bean, user, act);
		request.getSession().setAttribute("statPlans", plans);
		super.setPageReset(request);
		return mapping.findForward("statTestPlans");
	}

	/**
	 * 转到查询已经录入的光缆页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryCableForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		String deptype = user.getDeptype();
		if (deptype.equals("1")) {
			List<Contractor> cons = planBO.getContractors(user);
			request.setAttribute("cons", cons);
		}
		return mapping.findForward("queryCableForm");
	}

	/**
	 * 查询已经录入的光缆
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getCablesList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanQueryStatBean bean = (TestPlanQueryStatBean) form;
		List cables = planBO.getCables(user, bean);
		request.getSession().setAttribute("cables", cables);
		return mapping.findForward("cableLists");
	}

	/**
	 * 导出年计划统计信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTestPlansStatList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TestPlanExportBO bo = new TestPlanExportBO();
		List list = (List) request.getSession().getAttribute("statPlans");
		bo.exportTestPlanStats(list, response);
		return null;
	}

}
