package com.cabletech.linepatrol.safeguard.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.jdbc.mapper.KeyValue;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.safeguard.beans.SpecialEndPlanBean;
import com.cabletech.linepatrol.safeguard.module.SpecialEndPlan;
import com.cabletech.linepatrol.safeguard.services.SafeguardSpplanBo;
import com.cabletech.linepatrol.safeguard.services.SpecialEndPlanBo;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardSubWorkflowBO;
import com.cabletech.linepatrol.specialplan.beans.CircuitTaskBean;
import com.cabletech.linepatrol.specialplan.beans.WatchTaskBean;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;
import com.cabletech.linepatrol.specialplan.service.SpecialPlanManager;

public class SpecialEndPlanAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(SpecialEndPlanAction.class
			.getName());

	private SpecialEndPlanBo getSpecialEndPlanBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (SpecialEndPlanBo) ctx.getBean("specialEndPlanBo");
	}

	/**
	 * 加载特巡计划信息，根据不通条件转向不同的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public  ActionForward specialEndPlanForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		SafeguardSpplanBo spm2 = (SafeguardSpplanBo)ctx.getBean("safeguardSpplanBo");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String planId = request.getParameter("id");
		String spEndId = request.getParameter("spEndId");
		String type = request.getParameter("type");
		String ptype = request.getParameter("ptype");
		String isApply = request.getParameter("isApply");
		String businessId = request.getParameter("businessId"); 
		String operator = request.getParameter("operator");
		String isread = request.getParameter("isread");
		
		SpecialEndPlan specialEndPlan = null;
		SpecialPlan sp = spm.getSpecialPlan(planId);
		String creator = sp.getCreator();
		//创建watchTasks 存储区域
		Map<String,WatchTaskBean> watchTasks = null;
		watchTasks = new HashMap<String,WatchTaskBean>();
		request.getSession().setAttribute("watchTasks", watchTasks);
		//创建circuitTasks 存储区域
		Map<String,CircuitTaskBean> circuitTasks = null;
		circuitTasks = new HashMap<String,CircuitTaskBean>();
		
		spm.getCircuitTask(sp,circuitTasks,user);
		spm.getWatchtTask(sp,watchTasks,user);
		
		if(spEndId == null || "".equals(spEndId)){
			spEndId = getSpecialEndPlanBo().getEndPlanIdBySpId(planId);
		}
		if(businessId == null){
			businessId = spm2.getPlanIdBySpId(planId);
		}
		request.getSession().setAttribute("sp", sp);
		request.getSession().setAttribute("circuitTasks", circuitTasks);
		if("view".equals(type) || "approve".equals(type)){
			specialEndPlan = getSpecialEndPlanBo().getSpecialEndPlan(spEndId);
		}
		
		List<KeyValue> cableLevels = spm.getCableLevel();//线路级别；巡回任务根据级别生成默认的任务。
		List<KeyValue> patrolGroups = spm.getPatrolGroups(user);
		request.setAttribute("ptype",ptype);
		request.setAttribute("isApply",isApply);
		request.setAttribute("businessId",businessId);
		request.setAttribute("cableLevels",cableLevels);
		request.setAttribute("patrolgroups", patrolGroups);
		request.setAttribute("operator", operator);
		request.setAttribute("specialEndPlan", specialEndPlan);
		request.setAttribute("isread", isread);
		request.setAttribute("planId", planId);
		request.setAttribute("creator", creator);
		
		if(type.equals("add")){
			return mapping.findForward("addSpecialEndPlan");
		}else if(type.equals("view")){
			return mapping.findForward("viewSpecialEndPlan");
		}else if(type.equals("approveSplan")){
			return mapping.findForward("approveSpecialPlan");
		}else if(type.equals("read")){
			return mapping.findForward("readSpecialPlan");
		}else{
			return mapping.findForward("approveSpecialEndPlan");
		}
	}
	
	//审批特巡计划
	public ActionForward approveSpecialPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SpecialEndPlanBean specialEndPlanBean = (SpecialEndPlanBean)form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
		String approveResult = specialEndPlanBean.getApproveResult();
		String spplan = request.getParameter("spplan");
		String creator = request.getParameter("creator");
		try{
			getSpecialEndPlanBo().approveSpecialPlan(specialEndPlanBean, userInfo, spplan, creator);
			if("0".equals(approveResult)){
				return forwardInfoPage(mapping, request, "approveSpecialPlanUnpassRedo");
			}else if("1".equals(approveResult)){
				return forwardInfoPage(mapping, request, "approveSpecialPlanPassRedo");
			}else{
				return forwardInfoPage(mapping, request, "approveSpecialPlanTransferRedo");
			}
		} catch (ServiceException e) {
			logger.error("审核保障方案出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "approveSafeguardPlanErrorRedo");
		}
	}
	
	/**
	 * 获得代办工作列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAgentList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		SafeguardSubWorkflowBO workflowBo = (SafeguardSubWorkflowBO) ctx
				.getBean("safeguardSubWorkflowBO");
		String deptype = userInfo.getDeptype();
		String dept = userInfo.getDeptID();
		String taskName = request.getParameter("task_name");
		String condition = "";
		if ("2".equals(deptype)) {
			condition += " and plan.contractor_id='" + dept + "' ";
		}
		List list = getSpecialEndPlanBo().getAgentList(userInfo, condition,
				taskName);
		Integer num = 0;
		/*num = new Integer(workflowBo.queryForHandleNumber(userInfo
				.getUserID(), condition, taskName));*/
		if(list != null && list.size() > 0){
			num = list.size();
		}
		request.setAttribute("num", num);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("agentEndPlanList");
	}
	
	/**
	 * 添加特巡终止计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public  ActionForward addSpecialEndPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		SpecialEndPlanBean specialEndPlanBean = (SpecialEndPlanBean)form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
		try{
			getSpecialEndPlanBo().addSpecialEndPlan(specialEndPlanBean, userInfo);
			return forwardInfoPage(mapping, request, "addSpecialEndPlanSuccess");
		}catch(ServiceException e){
			logger.info("添加保障特巡方案终止失败，失败原因：" + e.getMessage());
			e.printStackTrace();
			return forwardErrorPage(mapping, request, "addSpecialEndPlanError");
		}
	}
	
	/**
	 * 审批终止特巡计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public  ActionForward approveSpecialEndPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		SpecialEndPlanBean specialEndPlanBean = (SpecialEndPlanBean)form;
		String endType = specialEndPlanBean.getEndType();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
		String approveResult = specialEndPlanBean.getApproveResult();
		try{
			getSpecialEndPlanBo().approveSpecialEndPlan(specialEndPlanBean, userInfo);
			if("0".equals(approveResult)){
				return forwardInfoPage(mapping, request, "approveSpecialEndPlanUnpass");
			}else if("1".equals(approveResult)){
				if(endType.equals("2")){
					return forwardInfoPage(mapping, request, "approveSpecialEndPlanPassToRedo");
				}else{
					return forwardInfoPage(mapping, request, "approveSpecialEndPlanPass");
				}
			}else{
				return forwardInfoPage(mapping, request, "approveSpecialEndPlanTransfer");
			}
		} catch (ServiceException e) {
			logger.error("审核保障方案出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "approveSafeguardPlanError");
		}
	}
	
	/**
	 * 获得特巡计划终止列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward specialEndPlanList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
			"LOGIN_USER");
		List list = getSpecialEndPlanBo().getSpecialEndPlanList(userInfo);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("specialEndPlanList");
	}
	
	/**
	 * 查阅特巡终止计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward readReply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String approverId = userInfo.getUserID();
		String endId = request.getParameter("endId");
		getSpecialEndPlanBo().readReply(userInfo, approverId, endId);
		return forwardInfoPage(mapping, request,"planEndReadReplySuccess");
	}
	
	/**
	 * 查询特巡计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward readSpecialPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String approverId = userInfo.getUserID();
		String spId = request.getParameter("spId");
		getSpecialEndPlanBo().readSpecialReply(userInfo, approverId, spId);
		return forwardInfoPage(mapping, request,"readSpecialPlan");
	}
}
