package com.cabletech.linepatrol.drill.action;

import java.util.Date;
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
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillSummary;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.services.DrillExamBo;
import com.cabletech.linepatrol.drill.services.DrillTaskBo;

public class DrillExamAction extends BaseInfoBaseDispatchAction  {
	private static Logger logger = Logger.getLogger(DrillExamAction.class.getName());

	private DrillExamBo getDrillExamBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (DrillExamBo) ctx.getBean("drillExamBo");
	}
	
	/**
	 * 获得待评分列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward examList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = getDrillExamBo().getDrillExamList();
		request.getSession().setAttribute("list", list);
		return mapping.findForward("examList");
	}
	
	/**
	 * 演练评分前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward examDrillForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String summaryId = request.getParameter("summaryId");
		Map map = getDrillExamBo().getExamDrill(summaryId);
		DrillTask drillTask = (DrillTask)map.get("drillTask");
		DrillPlan drillPlan = (DrillPlan)map.get("drillPlan");
		DrillSummary drillSummary = (DrillSummary)map.get("drillSummary");
		List list = null;
		Object list2 = map.get("list");
		if(list2 != null && !"".equals(list2)){
			list = (List)list2;
		}
		long planCreateTime = drillPlan.getCreateTime().getTime();
		Date deadline = drillTask.getDeadline();
		long planDeadline = 0;
		if (deadline != null) {
			planDeadline = deadline.getTime();
		}
		double planDays = (planCreateTime - planDeadline) / 1000.0 / 60.0 / 60.0;
		if (planDays < 0.0) {
			planDays = 0.0 - planDays;
		}
		
		long sumCreateTime = drillSummary.getCreateTime().getTime();
		Date sumDeadlineDate = drillPlan.getDeadline();
		long sumDeadline = 0;
		if (sumDeadlineDate != null) {
			sumDeadline = sumDeadlineDate.getTime();
		}
		double sumDays = (sumCreateTime - sumDeadline) / 1000.0 / 60.0 / 60.0;
		if (sumDays < 0) {
			sumDays = 0.0 - sumDays;
		}
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("drillSummary", drillSummary);
		request.setAttribute("list", list);
		request.setAttribute("planDays", planDays);
		request.setAttribute("sumDays", sumDays);
		return mapping.findForward("examDrill");
	}
	
	/**
	 * 演练评分
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward examDrill(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		DrillTaskBo drillTaskBo=(DrillTaskBo)ctx.getBean("drillTaskBo");
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		
		//获得日常考核对象
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		
		String taskId = request.getParameter("taskId");
		String planId = request.getParameter("planId");
		String contractorId = request.getParameter("contractorId");
		try{
			getDrillExamBo().examDrill(userInfo, taskId, planId, contractorId,appraiseDailyBean,speicalBeans);
			log(request,"演练评分（演练名称为："+drillTaskBo.get(taskId).getName()+"）","演练管理");
			return forwardInfoPage(mapping, request,"examDrillSuccess");
		}catch(ServiceException e){
			logger.error("演练评分出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "examDrillError");
		}
	}
}
