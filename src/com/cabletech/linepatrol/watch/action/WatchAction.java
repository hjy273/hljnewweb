package com.cabletech.linepatrol.watch.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.ctf.dao.jdbc.mapper.KeyValue;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.hiddanger.model.HiddangerEndplan;
import com.cabletech.linepatrol.hiddanger.service.HiddangerRegistManager;
import com.cabletech.linepatrol.specialplan.beans.CircuitTaskBean;
import com.cabletech.linepatrol.specialplan.beans.WatchTaskBean;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;
import com.cabletech.linepatrol.specialplan.service.SpecialPlanManager;
import com.cabletech.linepatrol.watch.service.WatchManager;

public class WatchAction extends BaseInfoBaseDispatchAction{
	public ActionForward approvePlanLink(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		WatchManager wm = (WatchManager)ctx.getBean("watchManager");
		HiddangerRegistManager hm = (HiddangerRegistManager) ctx.getBean("hiddangerRegistManager");
		
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		
		String hiddangerId = request.getParameter("id");
		String transfer = request.getParameter("transfer");
		String end = request.getParameter("end");
		String endapprove = request.getParameter("endapprove");
		String planId = wm.getPlanId(hiddangerId);
		
		SpecialPlan sp = spm.getSpecialPlan(planId);
		//创建watchTasks 存储区域
		Map<String,WatchTaskBean> watchTasks = null;
		watchTasks = new HashMap<String,WatchTaskBean>();
		request.getSession().setAttribute("watchTasks", watchTasks);
		//创建circuitTasks 存储区域
		Map<String,CircuitTaskBean> circuitTasks = new HashMap<String,CircuitTaskBean>();
		request.getSession().setAttribute("circuitTasks", circuitTasks);
		
		spm.getCircuitTask(sp,circuitTasks,user);	
		spm.getWatchtTask(sp,watchTasks,user);

		List<KeyValue> cableLevels = spm.getCableLevel();//线路级别；巡回任务根据级别生成默认的任务。
		List<KeyValue> patrolGroups = spm.getPatrolGroups(user);
		
		request.getSession().setAttribute("sp", sp);
		request.setAttribute("cableLevels",cableLevels);
		request.setAttribute("patrolgroups", patrolGroups);
		
		request.setAttribute("readOnly", hm.getRegistInfo(hiddangerId, user).isReadOnly());
		request.setAttribute("hiddangerId", hiddangerId);
		
		if(end!=null && end.equals("1")){
			request.setAttribute("hiddangerEndplan", wm.getEndPlanFromHiddangerId(hiddangerId));
			return mapping.findForward("endplan");
		}else if(endapprove!=null && endapprove.equals("1")){
			request.setAttribute("hiddangerEndplan", wm.getEndPlanFromHiddangerId(hiddangerId));
			if(transfer.equals("1")){
				return mapping.findForward("endapprovetransfer");
			}else{
				return mapping.findForward("endapprove");
			}
		}else{
			if(transfer.equals("1")){
				return mapping.findForward("transferApprove");
			}else{
				return mapping.findForward("approveLink");
			}
		}
	}
	/**
	 * 盯防计划审核转审
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward transferApprove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		WatchManager wm = (WatchManager)ctx.getBean("watchManager");
		
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute("LOGIN_USER");
		
		String approver = request.getParameter("approver");
		String hiddangerId = request.getParameter("hiddangerId");
		
		wm.transferApprove(hiddangerId, approver, userInfo);
		log(request,"盯防转审 （盯防名称为："+wm.get(hiddangerId).getName()+"）","隐患盯防");
		return forwardInfoPage( mapping, request, "hiddangerTransferApprove" );
	}
	/**
	 * 审核盯防计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward approveWatchPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		WatchManager wm = (WatchManager)ctx.getBean("watchManager");
		
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute("LOGIN_USER");
		
		String userId = userInfo.getUserID();
		String hiddangerId = request.getParameter("hiddangerId");
		String approveResult = request.getParameter("approveResult");
		String approveRemark = request.getParameter("approveRemark");
		
		ApproveInfo approve = new ApproveInfo();
		approve.setApproverId(userId);
		approve.setApproveResult(approveResult);
		approve.setApproveRemark(approveRemark);
		
		wm.approve(approve, userInfo, hiddangerId);
		if(approveResult.equals("1")){
			log(request,"盯防计划审核通过 （盯防名称为："+wm.get(hiddangerId).getName()+"）","隐患盯防");
			return forwardInfoPage( mapping, request, "planApprove" );
		}else{
			log(request,"盯防计划审核未通过 （盯防名称为："+wm.get(hiddangerId).getName()+"）","隐患盯防");
			return forwardInfoPage( mapping, request, "planNotApprove" );
		}
	}
	/**
	 * 终止盯防计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward endWatchPlan(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		WatchManager wm = (WatchManager)ctx.getBean("watchManager");
		
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute("LOGIN_USER");
		
		String hiddangerId = request.getParameter("hiddangerId");
		String endType = request.getParameter("type");
		String reason = request.getParameter("reason");
		HiddangerEndplan endplan = wm.loadEndPlan(hiddangerId);
		endplan.setEndType(endType);
		endplan.setReason(reason);
		
		String approver = request.getParameter("approver");
		String reader = request.getParameter("reader");
		
		wm.endWatchPlan(userInfo, hiddangerId, endplan, approver, reader);
		log(request,"终止盯防计划 （盯防名称为："+wm.get(hiddangerId).getName()+"）","隐患盯防");
		return forwardInfoPage( mapping, request, "endplan" );
	}
	/**
	 * 终止计划审核
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward endPlanApprove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		WatchManager wm = (WatchManager)ctx.getBean("watchManager");
		
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute("LOGIN_USER");
		
		String userId = userInfo.getUserID();
		String hiddangerId = request.getParameter("hiddangerId");
		String approveResult = request.getParameter("approveResult");
		String approveRemark = request.getParameter("approveRemark");
		
		ApproveInfo approve = new ApproveInfo();
		approve.setApproverId(userId);
		approve.setApproveResult(approveResult);
		approve.setApproveRemark(approveRemark);
		
		wm.endPlanApprove(approve, userInfo, hiddangerId);
		if(approveResult.equals("1")){
			log(request,"结束计划审核通过 （盯防名称为："+wm.get(hiddangerId).getName()+"）","隐患盯防");
			return forwardInfoPage( mapping, request, "endplanApprove" );
		}else{
			log(request,"结束计划审核未通过（盯防名称为："+wm.get(hiddangerId).getName()+"）","隐患盯防");
			return forwardInfoPage( mapping, request, "endplanNotApprove" );
		}
	}
	/**
	 * 终止计划转审
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward endPlanTransfer(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		WatchManager wm = (WatchManager)ctx.getBean("watchManager");
		
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute("LOGIN_USER");
		
		String approver = request.getParameter("approver");
		String hiddangerId = request.getParameter("hiddangerId");
		
		wm.endPlanTransfer(hiddangerId, approver, userInfo);
		log(request,"终止计划转审 （盯防名称为："+wm.get(hiddangerId).getName()+"）","隐患盯防");
		return forwardInfoPage( mapping, request, "endplanTransferApprove" );
	}
	
}
