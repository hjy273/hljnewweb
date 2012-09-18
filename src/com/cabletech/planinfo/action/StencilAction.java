package com.cabletech.planinfo.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.planinfo.beans.PlanBean;
import com.cabletech.planinfo.beans.StencilSubTaskBean;
import com.cabletech.planinfo.beans.StencilTaskBean;
import com.cabletech.planinfo.domainobjects.Plan;
import com.cabletech.planinfo.domainobjects.StencilTask;
import com.cabletech.planinfo.services.PlanBO;
import com.cabletech.planinfo.services.StencilBO;
import com.cabletech.planinfo.services.TaskBO;


/**
 * 任务模板
 * @author Administrator
 *
 */
public class StencilAction extends PlanInfoBaseDispatchAction {
	private Logger logger = Logger.getLogger("StencilAction");
	/**
	 * 显示添加计划模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addStencil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		StencilBO sbo = new StencilBO();
		List lgroup = sbo.getPatrol(userinfo);
		request.setAttribute("patrolgroup", lgroup);
		return mapping.findForward("addStencilForm");
		
	}
	/**
	 * 创建模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createStencil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
		StencilTaskBean stencil = (StencilTaskBean)form;
		//清空session

		session.removeAttribute("Stencil");
		session.removeAttribute("StencilTaskList");
		session.removeAttribute("EditS");
		session.removeAttribute("pointlist");
		session.removeAttribute("strsubline");
		session.removeAttribute("edittask");
		//session.removeAttribute("Index");
		//添加必要数据
		stencil.setRegionid(userinfo.getRegionid());
		stencil.setContractorid(userinfo.getDeptID());
		//获取巡检点信息
		//Vector subtaskVct = new Vector();
        //subtaskVct = super.getService().getPUnitListByPatrolid( stencil.getPatrolid(),
        //             "2", "1" );
        //request.setAttribute("pointlist", subtaskVct);
        //session.setAttribute("sublinelist", subtaskVct);
		TaskBO taskbo = new TaskBO();
		String taskHtml = taskbo.getPUnitListByPatrolid(stencil.getPatrolid(), "1","");
		request.setAttribute("tasksublineHtm", taskHtml);
		session.setAttribute("sublinelist", taskbo.getPSublineByPatrol(stencil.getPatrolid(), "1"));
		//保存到session中
        session.setAttribute("EditS", "add");
		session.setAttribute("Stencil", stencil);
		session.setAttribute("strsubline", "");
		session.setAttribute("edittask", "add");
		session.setAttribute("StencilTaskList", new ArrayList());
		return mapping.findForward("addstencilTask");
	}
	/**
	 * 保存模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveStencil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
		StencilTaskBean stencil = (StencilTaskBean)session.getAttribute("Stencil");
		List stenciltasklist = (List)session.getAttribute("StencilTaskList");
		if(stenciltasklist.size()<1){
			session.setAttribute("strsubline", "");
			return forwardInfoPage(mapping, request, "f20404");
		}
		// do save stencil
		StencilBO sbo = new StencilBO();
		boolean b = sbo.saveStencil(stencil,stenciltasklist);
		String strFor = "f20401";
		session.removeAttribute("Stencil");
		session.removeAttribute("StencilTaskList");
		session.removeAttribute("EditS");
		session.removeAttribute("pointlist");
		session.removeAttribute("sublinelist");
		session.removeAttribute("strsubline");
		session.removeAttribute("edittask");
		if(b){
			strFor = "s20401";
			log(request, " 添加巡检计划模板 （模板名称为："+stencil.getStencilname()+"）", " 巡检计划管理 ");
		}
		return forwardInfoPage(mapping, request, strFor);
	}
	/**
	 * 加载模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadStencil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
		//清除session
		session.removeAttribute("Stencil");
		session.removeAttribute("StencilTaskList");
		session.removeAttribute("EditS");
		
		StencilBO sbo = new StencilBO();
		StencilTask stencil = sbo.loadStencil(request.getParameter("id"));
		if(stencil == null){
			return forwardInfoPage(mapping, request, "e20402");
		}
		StencilTaskBean stencilbean = new StencilTaskBean();
		BeanUtil.objectCopy(stencil, stencilbean);
		List StencilTaskList = sbo.getStencilTaskList(stencilbean.getID());
		StencilSubTaskBean stencilb = (StencilSubTaskBean)StencilTaskList.get(0);
		logger.info(stencilb.toString());
		session.setAttribute("Stencil", stencilbean);
		session.setAttribute("StencilTaskList", StencilTaskList);
		session.setAttribute("EditS", "edit");
		session.setAttribute("strsubline", "");
		session.setAttribute("delStencilTaskList", new ArrayList());
		String forwardPage = "editStencilResult";
		return mapping.findForward(forwardPage);
	}
	/**
	 * 更新计划模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStencil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
		StencilTaskBean stencilbean = (StencilTaskBean)session.getAttribute("Stencil");
		StencilTask stencil = new StencilTask();
		BeanUtil.objectCopy(stencilbean, stencil);
		stencil.toString();
		List stencilTaskList = (List)session.getAttribute("StencilTaskList");
		List delTaskList = (List) session.getAttribute("delStencilTaskList");
		if(stencilTaskList.size()<1){
			session.setAttribute("strsubline", "");
			return forwardInfoPage(mapping, request, "f20404");
		}
		StencilBO sbo = new StencilBO();
		boolean b = sbo.saveOrUpdatePlan(stencil, stencilTaskList,delTaskList);
		String strFor = "f20402";
		if(b){
			strFor = "s20402";
		}
		session.removeAttribute("Stencil");
		session.removeAttribute("StencilTaskList");
		session.removeAttribute("EditS");
		session.removeAttribute("pointlist");
		session.removeAttribute("delStencilTaskList");
		session.removeAttribute("sublinelist");
		session.removeAttribute("strsubline");
		session.removeAttribute("edittask");
		log(request, " 更新巡检计划模板 （模板名称为："+stencil.getStencilname()+"）", " 巡检计划管理 ");
		return forwardInfoPage(mapping, request, strFor);
	}
		
	/**
	 * 删除模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delStencil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
		String id = request.getParameter("id");
		StencilBO sbo = new StencilBO();
		StencilTask stencil =sbo.loadStencil(id);
		sbo.removePlan(stencil);
		log(request, " 删除巡检计划模板（模板名称为："+stencil.getStencilname()+"） ", " 巡检计划管理 ");
		return forwardInfoPage(mapping, request, "s20403");
	}
	/**
	 * 查询模板(高级查询)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryStencil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
		String _regionid = request.getParameter("regionid");
		String _workID = request.getParameter("patrolid");
		StencilTaskBean bean = (StencilTaskBean)form;
		StencilBO sbo = new StencilBO();
		List list = sbo.queryStencil(userinfo,bean , _regionid, _workID);
		logger.info("Regionid=" + _regionid);
		logger.info("workID=" + _workID);
		session.setAttribute("queryresult", list);
		this.setPageReset(request);
		return mapping.findForward("queryStencilResult");
	}
	/**
	 * 加载高级查询表单。
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStencil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
		StencilBO sbo = new StencilBO();
		List lgroup = sbo.getPatrol(userinfo);
		request.setAttribute("patrolgroup", lgroup);
		return mapping.findForward("searchStencil");
	}
		
}
