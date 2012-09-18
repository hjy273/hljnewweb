package com.cabletech.linepatrol.maintenance.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanExamBO;

/**
 * ���Լƻ�����
 * @author Administrator
 *
 */
public class TestPlanExamAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * ת�����Լƻ��Ŀ��˽���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward examTestPlanForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanExamBO bo = (TestPlanExamBO) ctx.getBean("testPlanExamBO");
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		String regionid = user.getRegionid();
		List exams = bo.getWaitExams(regionid);
		request.getSession().setAttribute("exams",exams);
		return mapping.findForward("examForm");
	}

	/**
	 * ת��һ���ƻ���������ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward examForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String planid = request.getParameter("planid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		BasicDynaBean plan = planBO.getPlanInfo(planid);
		if(plan==null){
			return this.forwardInfoPage(mapping, request, "nofindPlan");
		}
		int problemNum = planBO.getProblemNumByPlanId(planid);
		int solveNum = planBO.getSolveProblemNum(planid);
		String rate= "100%";
		if(problemNum!=0){
			 double r = (double)solveNum/problemNum;   
	 		 rate=r*100+"%";
		}
		String contractorId = (String)plan.get("contractor_id");
		request.setAttribute("problemNum", problemNum);
		request.setAttribute("solveNum", solveNum);
		request.setAttribute("rate", rate);
		request.setAttribute("plan",plan);
		request.setAttribute("planid",planid);
		request.setAttribute("contractorId", contractorId);
		return mapping.findForward("examOneForm");
	}
	
	
	/**
	 * ���濼������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveEvaluate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		/*String score = request.getParameter("entityScore");
		String remark= request.getParameter("entityRemark");*/
		
		//����ճ����˶���
		AppraiseDailyBean appraiseDailyBean = (AppraiseDailyBean)request.getSession().getAttribute("appraiseDailyDailyBean");
		List<AppraiseDailyBean> speicalBeans=(List<AppraiseDailyBean>)request.getSession().getAttribute("appraiseDailySpecialBean");
		
		String planid = request.getParameter("planid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanExamBO bo = (TestPlanExamBO) ctx.getBean("testPlanExamBO");
		TestPlanBO testPlanBO=(TestPlanBO) ctx.getBean("testPlanBO");
		
		bo.saveEvaluate(user,planid,appraiseDailyBean,speicalBeans);
		log(request, "���������ƻ�ά�����ƻ�����Ϊ��"+testPlanBO.getTestPlanById(planid).getTestPlanName()+"��", "����ά�� ");
		return this.forwardInfoPage(mapping, request, "testPlanevaluateOK");
	}
	
	

}