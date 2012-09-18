package com.cabletech.linepatrol.specialplan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.specialplan.service.SpecialPlanStatManager;
import com.cabletech.linepatrol.specialplan.service.SpecialPlanManager;

public class SpecialPlanStatAction  extends BaseDispatchAction{
	//所有计划统计信息
	public ActionForward viewPlansStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.setAttribute("planStat", hs.getWatchPlansStat(id));
		return mapping.findForward("viewPlansStat");
	}
	
	public ActionForward viewPlanListStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getPlansStat(id));
		return mapping.findForward("viewPlanListStat");
	}
	
	//某个计划整体统计信息
	public ActionForward viewPlanStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		
		String id = request.getParameter("id");
		
		request.setAttribute("planStat", hs.getWatchPlanStat(id));
		request.setAttribute("plan", spm.getSpecialPlan(id));
		return mapping.findForward("viewStat");
	}
	
	//按区域统计
	public ActionForward viewAreaStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getAreaStat1(id));
		return mapping.findForward("viewAreaStat");
	}
	
	//按线段统计
	public ActionForward viewSublineStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getSublineStat(id));
		return mapping.findForward("viewSublineStat");
	}
	
	//已检详细
	public ActionForward viewPatrolDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getPatrolDetail(id));
		return mapping.findForward("viewPatrolDetail");
	}
	
	//漏检详细
	public ActionForward viewLeakDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getLeakDetail(id));
		return mapping.findForward("viewLeakDetail");
	}
	
	//有效盯防
	public ActionForward viewValidWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getValidWatch(id));
		return mapping.findForward("viewValidWatch");
	}
	
	//无效盯防
	public ActionForward viewInvalidWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getInvalidWatch(id));
		return mapping.findForward("viewInvalidWatch");
	}
}
