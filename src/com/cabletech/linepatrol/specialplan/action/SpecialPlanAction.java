package com.cabletech.linepatrol.specialplan.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.jdbc.mapper.KeyValue;
import com.cabletech.linepatrol.safeguard.services.SafeguardPlanBo;
import com.cabletech.linepatrol.specialplan.beans.CircuitTaskBean;
import com.cabletech.linepatrol.specialplan.beans.SpecialPlanBean;
import com.cabletech.linepatrol.specialplan.beans.TaskRoute;
import com.cabletech.linepatrol.specialplan.beans.WatchTaskBean;
import com.cabletech.linepatrol.specialplan.module.SpecialPlan;
import com.cabletech.linepatrol.specialplan.service.SpecialPlanManager;
/***
 * ��Ѳ�ƻ��ƶ���
 * 
 * @author Administrator
 *
 */
public class SpecialPlanAction extends BaseDispatchAction{
	private Logger logger = Logger.getLogger(SpecialPlanAction.class);
	/**
	 * �����Ѳ�ƻ�
	 * url:/specialplan.do?method=addFrom&ptype=001&isApply=true&pName=��ֱ�ű�վ����&id=00001
	 * planType:�ƻ����� 001������002���ϣ�005����
	 * isApply:
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addFrom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SpecialPlanBean spBean = new SpecialPlanBean();
		spBean.setPlanType(request.getParameter("ptype"));
		spBean.setIsApply(request.getParameter("isApply"));
		spBean.setPlanName(request.getParameter("pName"));
		spBean.setStartDate(request.getParameter("startDate"));
		spBean.setEndDate(request.getParameter("endDate"));
		request.getSession().setAttribute("sPlan", spBean);
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		List<KeyValue> cableLevels = spm.getCableLevel();//��·����Ѳ��������ݼ�������Ĭ�ϵ�����
		List<KeyValue> patrolGroups = spm.getPatrolGroups(user);
		request.setAttribute("businessId",request.getParameter("id"));
		request.setAttribute("isApply", request.getParameter("isApply"));
		request.setAttribute("cableLevels",cableLevels);
		request.setAttribute("patrolgroups", patrolGroups);
		//����watchTasks �洢����
		Map<String,WatchTaskBean> watchTasks = null;
		watchTasks = new HashMap<String,WatchTaskBean>();
		request.getSession().setAttribute("watchTasks", watchTasks);
		//����circuitTasks �洢����
		Map<String,CircuitTaskBean> circuitTasks = null;
		circuitTasks = new HashMap<String,CircuitTaskBean>();
		request.getSession().setAttribute("circuitTasks", circuitTasks);
		//���������������ƶ���Ѳ�ƻ�
		String redo = (String)request.getParameter("redo");
		String endId = (String)request.getParameter("endId");
		request.setAttribute("redo", redo);
		request.setAttribute("endId", endId);
		
		return mapping.findForward("specialPlanForm");
	}
	/**
	 * ���ؼƻ�
	 * url��/specialplan.do?method=loadPlan&pid=000000000062&type=edit
	 * pid ���ƻ�id
	 * Type��edit ��������ʾΪ�鿴���༭ʱtype����Ϊedit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public  ActionForward loadPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String planId = request.getParameter("id");
		String type = request.getParameter("type");
		String ptype = request.getParameter("ptype");
		String isApply = request.getParameter("isApply");
		String businessId = request.getParameter("businessId"); 
		String flag = request.getParameter("flag");
		
		SpecialPlan sp = spm.getSpecialPlan(planId);
		//����watchTasks �洢����
		Map<String,WatchTaskBean> watchTasks = null;
		watchTasks = new HashMap<String,WatchTaskBean>();
		request.getSession().setAttribute("watchTasks", watchTasks);
		//����circuitTasks �洢����
		Map<String,CircuitTaskBean> circuitTasks = null;
		circuitTasks = new HashMap<String,CircuitTaskBean>();
		
		spm.getCircuitTask(sp,circuitTasks,user);
		spm.getWatchtTask(sp,watchTasks,user);
		
		request.getSession().setAttribute("sp", sp);
		request.getSession().setAttribute("circuitTasks", circuitTasks);
		
		List<KeyValue> cableLevels = spm.getCableLevel();//��·����Ѳ��������ݼ�������Ĭ�ϵ�����
		List<KeyValue> patrolGroups = spm.getPatrolGroups(user);
		String redo = request.getParameter("redo");
		request.setAttribute("ptype",ptype);
		request.setAttribute("isApply",isApply);
		request.setAttribute("businessId",businessId);
		request.setAttribute("cableLevels",cableLevels);
		request.setAttribute("patrolgroups", patrolGroups);
		request.setAttribute("flag", flag);
		request.setAttribute("type", type);
		request.setAttribute("redo", redo);
		if(type.equals("edit")){
			return mapping.findForward("editPlanForm");
		}else
			return mapping.findForward("viewPlanForm");
	}
	
	
	public ActionForward savePlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanBean spBean = (SpecialPlanBean)request.getSession().getAttribute("sPlan");
		if(spBean == null){
			spBean = new SpecialPlanBean();
		}
		Map<String,CircuitTaskBean> circuitTasks = (Map)request.getSession().getAttribute("circuitTasks");
		Map<String,WatchTaskBean> watchTasks = (Map)request.getSession().getAttribute("watchTasks");
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		String planName = request.getParameter("planName");
		String planType = request.getParameter("planType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String patrolGroupId = request.getParameter("patrolgroupid");
		String pid = request.getParameter("id");//��Ѳ�ƻ�ID
		String businessId = request.getParameter("businessId");
		String isApply = request.getParameter("isApply");
		String redo = request.getParameter("redo");
		String addOrEdit = request.getParameter("addOrEdit");
		String userId = request.getParameter("userId");
		String deptId = request.getParameter("deptId");
		spBean.setId(pid);
		spBean.setBusinessId(businessId);
		spBean.setPlanName(planName);
		spBean.setPlanType(planType);
		spBean.setStartDate(startDate);
		spBean.setEndDate(endDate);
		spBean.setIsApply(isApply);
		spBean.setPatrolGroupId(patrolGroupId);
		//����˳�����; ��������ȷ���Ƿ���Ҫȡ������˳�����
		boolean flag = spBean.getIsApply().equals("true") ? true : false;
		if(flag){
			String approver = request.getParameter("approver");
			String reader = request.getParameter("reader");
			spBean.setApprover(approver);
			spBean.setReader(reader);
		}
		try{
			if(patrolGroupId == null && "".equals(patrolGroupId)){
				return forwardErrorPage(mapping, request,"notgroupid");
			}
			if(circuitTasks.isEmpty()&& watchTasks.isEmpty()){
				return forwardErrorPage(mapping, request,"notsaveplan",new Object[]{spBean.getPlanName()});
			}
			SpecialPlan specialPlan = spm.saveSpecialPlan(spBean, circuitTasks, watchTasks,true,flag,user);
			if(specialPlan.getId()==null){
				return forwardErrorPage(mapping, request,"saveplanerror",new Object[]{spBean.getPlanName()});
			}
			//�ƻ�����ɹ�֮�����session�еı���
			request.getSession().removeAttribute("sPlan");
			request.getSession().removeAttribute("circuitTasks");
			request.getSession().removeAttribute("watchTasks");
			String url = (String)request.getSession().getAttribute("RETURNURL");
			if(spBean.getPlanType().equals(spm.WATCH_PLAN)){
				return forwardInfoPage(mapping, request,"savspecialplansuccessHiddanger",spBean.getPlanName());
			}else if(spBean.getPlanType().equals(spm.SAFEGUARD_PLAN)){
				request.setAttribute("businessId", businessId);
				String spPlanName = specialPlan.getPlanName();
				//��Ϊ���ϼƻ������ƶ�ʱ
				if("redo".equals(redo)){
					if("edit".equals(addOrEdit)){
						WebApplicationContext ctx2 = getWebApplicationContext();
						SafeguardPlanBo spb = (SafeguardPlanBo) ctx2.getBean("safeguardPlanBo");
						spb.editSpecialPlan(specialPlan.getId(), businessId, userId, spBean, spPlanName);
						return forwardInfoPage(mapping, request, "editSpecialPlanSuccess");
					}else{
						/*StringBuffer buf = new StringBuffer("");
						buf.append("<script type='text/javascript'>");
						buf.append("window.onload=function(");
						buf.append("){");
						buf.append("window.location.href='/WebApp/safeguardPlanAction.do?method=redoSpecialPlan&spid="+specialPlan.getId()+"&bussinessId="+businessId+"';");
						buf.append("}");
						buf.append("</script>");
						response.setCharacterEncoding("GBK");
						PrintWriter out = response.getWriter();
						out.print(buf.toString());
						System.out.println("*******buf.toString():" + buf.toString());
						out.close();
						return null;*/
						String endId = request.getParameter("endId");
						
						WebApplicationContext ctx2 = getWebApplicationContext();
						SafeguardPlanBo spb = (SafeguardPlanBo) ctx2.getBean("safeguardPlanBo");
						spb.redoSpecialPlan(specialPlan.getId(), businessId, userId, spBean, endId, spPlanName);
						return forwardInfoPage(mapping, request, "redoSpecialPlanSuccess");
					}
				}
				return mapping.findForward("close");
			}
			else
				return forwardInfoPageUrl(mapping, request,"savspecialplansuccess",new Object[]{spBean.getPlanName()},url);
		}catch(Exception e){
			e.printStackTrace();
			return forwardErrorPage(mapping, request,"savespecialplanfail",new Object[]{spBean.getPlanName()});
		}
		
	}
	
	
	
	/**
	 * ��ȡ����ת����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addTaskFrom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String cableLevel = request.getParameter("cl");
		String cableLevelName = request.getParameter("cn");
		String patrolNum = request.getParameter("pn");
		request.setAttribute("cableLevel", cableLevel);
		request.setAttribute("cableLevelName", cableLevelName);
		request.setAttribute("patrolNum", patrolNum);

		return mapping.findForward("circuitTaskForm");
	}
	
	/**
	 * �༭Ѳ������ʱ��ͨ����ǰ�û���Ϣ����·�����߶ιؼ��ּ��������߶�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward loadSubline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		String lineLevel = request.getParameter("lineLevel");
		String lineNameKey = request.getParameter("key");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		Map<String,CircuitTaskBean> circuitTasks = (Map)request.getSession().getAttribute("circuitTasks");
		
		CircuitTaskBean circuitTask = circuitTasks.get(lineLevel);
		Map<String,TaskRoute> taskSublines = null;
		if(circuitTask != null){
			taskSublines = circuitTask.getTaskSubline();
		}
		String tasksublineHtml = spm.getTaskSubline(taskSublines,user,lineLevel,lineNameKey);
		outPrint(response,tasksublineHtml);
		return null;
	}
	/**
	 * ���Ѳ�����������б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public  ActionForward addCircuitTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		Map<String,CircuitTaskBean> circuitTasks = (Map)request.getSession().getAttribute("circuitTasks");
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String taskName = request.getParameter("taskName");
		String lineLevel = request.getParameter("lineLevel");
		String patrolNum = request.getParameter("patrolNum");
		String taskSubline = request.getParameter("tasksubline");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		//�������Ѳ�������б���
		List taskSublineList = StringUtils.string2List(taskSubline,",");
		CircuitTaskBean circuitTask = null;
		if(circuitTasks.containsKey(lineLevel)){
			circuitTask = circuitTasks.get(lineLevel);
		}else{
			circuitTask = new CircuitTaskBean();
		}
		circuitTask.setLineLevel(lineLevel);
		circuitTask.setPatrolNum(patrolNum);
		circuitTask.setTaskName(taskName);
		circuitTask.setStartTime(startTime);
		circuitTask.setEndTime(endTime);
		circuitTask = spm.addTask2Map(circuitTask,taskSublineList,user);
		
		circuitTasks.put(lineLevel, circuitTask);
		
		request.getSession().setAttribute("circuitTasks", circuitTasks);
		//��ѡ���·�ɶη��ؽ��������ʾ
		Map<String,TaskRoute> taskSublines = null;
		if(circuitTask != null){
			taskSublines = circuitTask.getTaskSubline();
		}
		Iterator it = taskSublines.keySet().iterator();
		StringBuilder outHtml = new StringBuilder();
		while(it.hasNext()){
			String key = it.next().toString();
			TaskRoute tr = taskSublines.get(key);
			outHtml.append(tr.getSublineName()+";");
		}
		outPrint(response,outHtml.toString());
		
		return null;
	}
	/**
	 * ���Ѳ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public  ActionForward clearCircuitTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,CircuitTaskBean> circuitTasks = (Map)request.getSession().getAttribute("circuitTasks");
		String lineLevel = request.getParameter("lineLevel");
		CircuitTaskBean bean = circuitTasks.get(lineLevel);
		if(bean != null){
			bean.getTaskSubline().clear();
		}
		outPrint(response,"");
		return null;
	}
	
	/**
	 *  ��Ӷ�������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward addWatchTaskForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		return mapping.findForward("watchTaskForm");
	}
	/**
	 * ���ڶ�������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward loadTaskArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		Map<String,WatchTaskBean> watchTasks = (Map)request.getSession().getAttribute("watchTasks");
		String taskAreaHtml = spm.getTaskArea(user,watchTasks);
		outPrint(response,taskAreaHtml);
		return null;
	}
	
	/**
	 * ��Ӷ���������ʱ����watchTasks
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward addWatchTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String startTime = request.getParameter("startTime");
		String endTime =request.getParameter("endTime");
		String space = request.getParameter("space");
		String taskArea = request.getParameter("taskArea");
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		//������������ʱ���浽map��
		Map<String,WatchTaskBean> watchTasks = spm.addWatchTask2Map(user,startTime,endTime,space,taskArea);
		request.getSession().setAttribute("watchTasks", watchTasks);
		
		//����ѡ��Ķ��������ҳ��
		Iterator it = watchTasks.keySet().iterator();
		StringBuilder outHtml = new StringBuilder();
		String areaName= null;
		while(it.hasNext()){
			String key = it.next().toString();
			areaName = watchTasks.get(key).getAreaName();
			outHtml.append(areaName+";");
		}
		outPrint(response,outHtml.toString());
		
		return null;
	}
	/**
	 * ��ն�������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public  ActionForward clearWatchTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,WatchTaskBean> watchTasks = (Map)request.getSession().getAttribute("watchTasks");
		watchTasks.clear();
		outPrint(response,"");
		return null;
	}
	public  ActionForward setWatchEndDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		String planId = request.getParameter("planId");
		String endDate = request.getParameter("endDate");
		try{
			spm.setWatchEndDate(planId, endDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public  ActionForward deleteSpecialPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		WebApplicationContext ctx = getWebApplicationContext();
		SpecialPlanManager spm = (SpecialPlanManager)ctx.getBean("specialPlanManager");
		String spplanId = request.getParameter("spplanId");
		String businessId = request.getParameter("businessId");
		
		try{
			spm.deleteSpecialPlan(spplanId);
			//request.setAttribute("businessId", businessId);
			WebApplicationContext ctx2 = getWebApplicationContext();
			SafeguardPlanBo spb = (SafeguardPlanBo) ctx2.getBean("safeguardPlanBo");
			
			List<SpecialPlan> list = spb.findSpPlanBySafeguardId(businessId);
			StringBuffer buf = new StringBuffer("");
			if(list != null && list.size() > 0){
				buf.append("<table style='border:#9A9A9A solid;border-width:1 1 1 1;width=100%;'>");
				for(SpecialPlan plan :list){
					buf.append("<tr>");
					buf.append("<td style='tdc'>");
					buf.append(plan.getPlanName());
					buf.append("</td>");
					buf.append("<td style='tdc'>");
					buf.append(sdf.format(plan.getStartDate()));
					buf.append("</td>");
					buf.append("<td style='tdc'>");
					buf.append(sdf.format(plan.getEndDate()));
					buf.append("</td>");
					/*buf.append("<td>");
					buf.append("<apptag:assorciateAttr table='patrolmaninfo' label='patrolname' key='patrolid' keyValue='" + sp.getPatrolGroupId() + "' />" + "&nbsp;&nbsp;");
					buf.append("</td>");*/
					buf.append("<td style='tdc'>");
					buf.append("<a onclick=editSpecPlan('");
					buf.append(businessId);
					buf.append("','");
					buf.append(spplanId);
					buf.append("') style='cursor: pointer; color: blue;'>");
					buf.append("�޸�");
					buf.append("</a>");
					buf.append("</td>");
					buf.append("<td>");
					buf.append("<a onclick=deleteProblem('");
					buf.append(businessId);
					buf.append("','");
					buf.append(spplanId);
					buf.append("') style='cursor: pointer; color: blue;'>");
					buf.append("ɾ��");
					buf.append("</a>");
					buf.append("</td>");
					buf.append("</tr>");
				}
				buf.append("</table>");
			}
			response.setCharacterEncoding("GBK");
			PrintWriter out = response.getWriter();
			out.print(buf.toString());
			System.out.println("*******buf.toString():" + buf.toString());
			out.close();
			/*String script = "<script>loadData();</script>";
			outPrint(response,script);*/
			return null;
		}catch(Exception e){
			logger.error("ɾ����Ѳ�ƻ�ʧ�ܣ�ʧ��ԭ��" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
