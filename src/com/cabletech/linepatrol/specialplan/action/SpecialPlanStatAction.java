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
	//���мƻ�ͳ����Ϣ
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
	
	//ĳ���ƻ�����ͳ����Ϣ
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
	
	//������ͳ��
	public ActionForward viewAreaStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getAreaStat1(id));
		return mapping.findForward("viewAreaStat");
	}
	
	//���߶�ͳ��
	public ActionForward viewSublineStat(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getSublineStat(id));
		return mapping.findForward("viewSublineStat");
	}
	
	//�Ѽ���ϸ
	public ActionForward viewPatrolDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getPatrolDetail(id));
		return mapping.findForward("viewPatrolDetail");
	}
	
	//©����ϸ
	public ActionForward viewLeakDetail(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getLeakDetail(id));
		return mapping.findForward("viewLeakDetail");
	}
	
	//��Ч����
	public ActionForward viewValidWatch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanStatManager hs = (SpecialPlanStatManager) ctx.getBean("specialPlanStatManager");
		
		String id = request.getParameter("id");
		request.getSession().setAttribute("result", hs.getValidWatch(id));
		return mapping.findForward("viewValidWatch");
	}
	
	//��Ч����
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
