package com.cabletech.linepatrol.dispatchtask.action;

import java.io.OutputStream;
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
import com.cabletech.linepatrol.dispatchtask.beans.DispatchTaskBean;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.services.QueryDispatchTaskBO;
import com.cabletech.linepatrol.dispatchtask.template.SendTaskTemplate;

public class QueryDispatchTaskAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private WebApplicationContext ctx;
	private UserInfo userInfo;
	private QueryDispatchTaskBO bo;

	/**
	 * 执行初始化动作
	 * 
	 * @param request
	 */
	public void initialize(HttpServletRequest request) {
		ctx = getWebApplicationContext();
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		bo = (QueryDispatchTaskBO) ctx.getBean("queryDispatchTaskBO");
	}

	/**
	 * 打开查询统计页面
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward queryDispatchTaskTotalForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		// 从session中移除查询条件，及查询的数据
		request.getSession().removeAttribute("queryCondi");
		request.getSession().removeAttribute("TOTAL_LIST");

		List ldept = bo.getAcceptdept(userInfo);
		request.getSession().setAttribute("deptinfo", ldept);
		List dispatchTaskTypeList = bo.getQueryDispatchTaskDao()
				.queryDispatchTaskTypeList(" and assortment_id='dispatch_task' ");
		request.getSession().setAttribute("dispatch_task_type_list",
				dispatchTaskTypeList);
		return mapping.findForward("query_dispatch_task_total");
	}

	/**
	 * 进行查询统计操作
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward queryDispatchTaskTotal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		DispatchTaskBean bean = (DispatchTaskBean) form;
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			bean = (DispatchTaskBean) request.getSession().getAttribute(
					"queryCondi");
		} else {
			request.getSession().setAttribute("queryCondi", bean);
		}
		List totalList = bo.queryTotalList(userInfo, bean);
		request.setAttribute("task_names", bean.getTaskNames());
		request.getSession().setAttribute("TOTAL_LIST", totalList);
		// 保持url到session中的S_BACK_URL变量
		super.setPageReset(request);
		return mapping.findForward("query_dispatch_task_total_list");
	}

	/**
	 * 打开个人工单统计页面
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward queryPersonDispatchTaskTotalForm(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		List ldept = bo.getAcceptdept(userInfo);
		List luser = bo.getAcceptUser(userInfo);
		request.getSession().setAttribute("deptinfo", ldept);
		request.getSession().setAttribute("userinfo", luser);
		List dispatchTaskTypeList = bo.getQueryDispatchTaskDao()
				.queryDispatchTaskTypeList(" and assortment_id='dispatch_task' ");
		request.setAttribute("dispatch_task_type_list", dispatchTaskTypeList);
		// 清空session中查询条件和查询结果
		request.getSession().removeAttribute("querycon");
		request.getSession().removeAttribute("PERSON_TOTAL_NUM_LIST");
		return mapping.findForward("query_person_dispatch_task_total");
	}

	/**
	 * 进行个人工单数量查询统计操作
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward queryPersonDispatchTaskTotalNum(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		DispatchTaskBean bean = (DispatchTaskBean) form;
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			bean = (DispatchTaskBean) request.getSession().getAttribute(
					"querycon");
		} else {
			request.getSession().setAttribute("querycon", bean);
		}
		if (bean == null) {
			bean = new DispatchTaskBean();
		}
		List personTotalNumList = bo.queryPersonTotalNumList(userInfo, bean);
		request.getSession().setAttribute("PERSON_TOTAL_NUM_LIST",
				personTotalNumList);
		super.setPageReset(request);
		return mapping.findForward("query_person_dispatch_task_total_num_list");
	}

	/**
	 * 显示个人工单信息
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward queryPersonDispatchTaskTotal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		String queryFlg = request.getParameter("flg");
		String userName = request.getParameter("username");
		DispatchTaskBean bean = (DispatchTaskBean) request.getSession()
				.getAttribute("querycon");
		List personTotalList = bo.queryPersonTotalList(userInfo, bean);
		request.getSession().setAttribute("PERSON_TOTAL_LIST", personTotalList);
		request.getSession().setAttribute("queryflg", queryFlg);
		request.getSession().setAttribute("username", userName);
		request.getSession().setAttribute("datacount",
				personTotalList.size() + "");
		super.setPageReset(request);
		return mapping.findForward("query_person_dispatch_task_total_list");
	}

	/**
	 * 打开部门工单统计页面
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward queryDepartDispatchTaskTotalForm(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		List ldept = bo.getAcceptdept(userInfo);
		request.getSession().setAttribute("deptinfo", ldept);
		List dispatchTaskTypeList = bo.getQueryDispatchTaskDao()
				.queryDispatchTaskTypeList(" and assortment_id='dispatch_task' ");
		request.setAttribute("dispatch_task_type_list", dispatchTaskTypeList);
		// 清空session中的查询条件和查询结果
		request.getSession().removeAttribute("querycon");
		request.getSession().removeAttribute("DEPART_TOTAL_LIST");
		return mapping.findForward("query_depart_dispatch_task_total");
	}

	/**
	 * 部门工单统计查询操作
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward queryDepartDispatchTaskTotal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		DispatchTaskBean bean = (DispatchTaskBean) form;
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			bean = (DispatchTaskBean) request.getSession().getAttribute(
					"querycon");
		} else {
			request.getSession().setAttribute("querycon", bean);
		}
		if (bean == null) {
			bean = new DispatchTaskBean();
		}
		List deptTotalList = bo.queryDepartTotalList(userInfo, bean);
		request.getSession().setAttribute("DEPART_TOTAL_LIST", deptTotalList);
		super.setPageReset(request);
		return mapping.findForward("query_depart_dispatch_task_total_list");
	}

	/**
	 * 查询统计导出信息一览表
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward exportTotalResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		List list = (List) request.getSession().getAttribute("TOTAL_LIST");
		OutputStream out;
		initResponse(response, "派单查询统计信息一览表.xls");
		out = response.getOutputStream();
		SendTaskTemplate template = bo.exportTotalResult(list);
		template.write(out);
		return null;
	}

	/**
	 * 个人工单信息导出信息一览表
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 * 
	 * 
	 */
	public ActionForward exportPersonTotalResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		OutputStream out;
		initResponse(response, "个人工单信息一览表.xls");
		out = response.getOutputStream();
		List list = (List) request.getSession().getAttribute(
				"PERSON_TOTAL_LIST");
		String queryFlg = String.valueOf(request.getSession().getAttribute(
				"queryflg"));
		String username = String.valueOf(request.getSession().getAttribute(
				"username"));
		DispatchTaskBean bean = (DispatchTaskBean) request.getSession()
				.getAttribute("querycon");
		String dataCount = String.valueOf(request.getSession().getAttribute(
				"datacount"));
		logger.info("得到list");
		SendTaskTemplate template = bo.exportPersonTotalResult(list);
		template.write(out);
		return null;
	}

	/**
	 * 个人工单数量统计导出信息一览表
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward exportPersonNumTotalResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		OutputStream out;
		initResponse(response, "个人工单统计一览表.xls");
		out = response.getOutputStream();
		List list = (List) request.getSession().getAttribute(
				"PERSON_TOTAL_NUM_LIST");
		DispatchTaskBean bean = (DispatchTaskBean) request.getSession()
				.getAttribute("querycon");
		logger.info("得到list");
		SendTaskTemplate template = bo.exportPersonNumTotalResult(list, bean);
		template.write(out);
		return null;
	}

	/**
	 * 导出部门工单统计信息一览表
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward exportDepartTotalResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		OutputStream out;
		initResponse(response, "部门工单统计信息一览表.xls");
		out = response.getOutputStream();
		List list = (List) request.getSession().getAttribute(
				"DEPART_TOTAL_LIST");
		DispatchTaskBean bean = (DispatchTaskBean) request.getSession()
				.getAttribute("querycon");
		logger.info("得到list");
		SendTaskTemplate template = bo.exportDepartTotalResult(list, bean);
		template.write(out);
		return null;
	}

	public void initResponse(HttpServletResponse response, String outfilename)
			throws Exception {
		response.reset();
		response.setContentType(DispatchTaskConstant.CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));
	}

}
