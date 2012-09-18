package com.cabletech.linepatrol.safeguard.action;

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
import com.cabletech.linepatrol.safeguard.beans.SafeguardExamBean;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSummary;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.services.SafeguardExamBo;
import com.cabletech.linepatrol.safeguard.services.SafeguardTaskBo;

public class SafeguardExamAction extends BaseInfoBaseDispatchAction  {
	private static Logger logger = Logger.getLogger(SafeguardExamAction.class.getName());

	private SafeguardExamBo getSafeguardExamBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (SafeguardExamBo) ctx.getBean("safeguardExamBo");
	}
	
	/**
	 * 保障评分前加载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward examSafeguardForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String summaryId = request.getParameter("summaryId");
		Map map = getSafeguardExamBo().getSafeguardSummaryData(summaryId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = (SafeguardPlan) map.get("safeguardPlan");
		SafeguardSummary safeguardSummary = (SafeguardSummary) map
				.get("safeguardSummary");
		List list = null;
		Object list2 = map.get("list");
		if(list2 != null && !"".equals(list2)){
			list = (List)list2;
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if(safeguardSpObj != null){
			safeguardSps = (List)safeguardSpObj;
			specialPlans = (List)specialPlanObj;
		}
		long planCreateTime = safeguardPlan.getMakeDate().getTime();
		Date deadline = safeguardTask.getDeadline();
		long planDeadline = 0;
		if(deadline != null){
			planDeadline = deadline.getTime();
		}
		double planDays = (planCreateTime - planDeadline)/1000.0/60.0/60.0;
		if(planDays < 0.0){
			planDays = 0.0 - planDays;
		}
		
		long sumCreateTime = safeguardSummary.getSumDate().getTime();
		Date sumDeadlineDate = safeguardPlan.getDeadline();
		long sumDeadline = 0;
		if(sumDeadlineDate != null){
			sumDeadline = sumDeadlineDate.getTime();
		}
		double sumDays = (sumCreateTime - sumDeadline)/1000.0/60.0/60.0;
		if(sumDays < 0){
			sumDays = 0.0 - sumDays;
		}
		
		String sublineIds = (String) map.get("sublineIds");
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("safeguardSummary", safeguardSummary);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("list", list);
		request.setAttribute("planDays", planDays);
		request.setAttribute("sumDays", sumDays);
		return mapping.findForward("examSafeguard");
	}
	
	/**
	 * 保障评分
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward examSafeguard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		SafeguardTaskBo stb=(SafeguardTaskBo)ctx.getBean("safeguardTaskBo");
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		SafeguardExamBean safeguardExamBean = (SafeguardExamBean)form;
		//获得日常考核对象
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		String taskId=safeguardExamBean.getTaskId();
		String name=stb.get(taskId).getSafeguardName();
		try{
			getSafeguardExamBo().examSafeguard(safeguardExamBean, userInfo,appraiseDailyBean,speicalBeans);
			log(request,"保障评分（保障名称为： "+name+"）","保障管理");
			return forwardInfoPage(mapping, request,"examSafeguardSuccess");
		}catch(ServiceException e){
			logger.error("保障评分出错，出错信息：" + e.getMessage());
			return forwardErrorPage(mapping, request, "examSafeguardError");
		}
	}
}
