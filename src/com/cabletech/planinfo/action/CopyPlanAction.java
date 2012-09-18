package com.cabletech.planinfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.planinfo.bean.CopyBean;
import com.cabletech.planinfo.services.CopyPlanTread;
import com.cabletech.planinfo.services.PlanBO;
import com.cabletech.planinfo.services.PlanBaseService;

public class CopyPlanAction  extends PlanInfoBaseDispatchAction{
	
	public  ActionForward copyPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo)session.getAttribute("LOGIN_USER");
		PlanBO pbo = new PlanBO();
		PlanBaseService pbs = new PlanBaseService();
		CopyBean cb = new CopyBean();
		cb.setPrePlanId(request.getParameter("planId"));
		cb.setStartDate(request.getParameter("startDate"));
		cb.setEndDate(request.getParameter("endDate"));
		cb.setExecutorid(request.getParameter("executorid"));
		/*验证是否当月计划已制定并通过审批*/
		boolean b = pbs.isInstituteMPlan(cb.getStartDate().substring(5,7), cb.getStartDate().substring(0,4), user.getDeptID());
		if(!b){
			return forwardInfoPage(mapping, request, "w20304");
		}
		
		//判断在日期内是否有存在计划
		if(pbo.checkDate(cb.getStartDate(),cb.getExecutorid())){
			return forwardInfoPage(mapping, request, "w20303");
		}
		
		cb.setUserID(user.getUserID());
		System.out.println(cb);
		Thread copythread = new Thread(new CopyPlanTread(cb));
		System.out.println("start......");
		copythread.start();
		System.out.println("over");
		return forwardInfoPage(mapping, request, "COPY01");
	}
}
