package com.cabletech.linepatrol.maintenance.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Point;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.maintenance.beans.TestStationDataBean;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestPlanStation;
import com.cabletech.linepatrol.maintenance.module.TestStationData;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanStationBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanStationDataBO;

/**
 * 基站录入数据
 * @author Administrator
 *
 */
public class EnteringStationDataAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到基站测试数据结果列表页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward enteringDateListForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("allenter", null);
		String planid = request.getParameter("planid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlan plan = planBO.getTestPlanById(planid);
		UserInfo user = planBO.getUser(plan.getCreatorId());
		String conid = plan.getContractorId();
		Contractor c = planBO.getContratroById(conid);
		request.getSession().setAttribute("plan",plan);
		request.getSession().setAttribute("contraName",c.getContractorName());
		//request.getSession().setAttribute("planName",plan.getTestPlanName());
		request.getSession().setAttribute("userName",user.getUserName());
		List planstations = planBO.getPlanStationsByPlanId(planid);
		request.getSession().setAttribute("stations", planstations);
		boolean allenter = planBO.judgeAllEnteringStation(planid);
		request.getSession().setAttribute("allenter", allenter);
		TestPlanStationDataBO stationDataBO = (TestPlanStationDataBO)ctx.getBean("testPlanStationDataBO");
		TestData data = stationDataBO.getDataByPlanId(planid);
		request.getSession().setAttribute("dataid",null);
		if(data!=null){
			request.getSession().setAttribute("dataid", data.getId());
		}
		return mapping.findForward("enteringStationDateListForm");
	}

	/**
	 * 转到录入基站数据页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addEnteringStationForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String stationid = request.getParameter("stationid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanStationBO stationBO = (TestPlanStationBO) ctx.getBean("testPlanStationBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanStation station =  stationBO.getTestPlanStationById(stationid);
		Point point = planBO.getPoint(station.getTestStationId());
		station.setStationName(point.getPointName());
		List tester = planBO.getUsers(userinfo,"");
		request.setAttribute("station",station);
		request.setAttribute("testMan",tester);
		return mapping.findForward("enteringStationForm");
	}

	/**
	 * 保存基站信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addEnteringStationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestStationDataBean bean = (TestStationDataBean)form;
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanStationDataBO stationDataBO = (TestPlanStationDataBO)ctx.getBean("testPlanStationDataBO");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String tempstate = request.getParameter("tempstate");
		String stationId = request.getParameter("stationId");
		try {
			String act = request.getParameter("act");
			if(act!=null && act.equals("edit")){
				stationDataBO.updateStationData(bean,tempstate,stationId);
			}else{
				TestStationData data = stationDataBO.saveStationData(bean,user,tempstate);
			}
			//	TestStationData data = stationDataBO.saveStationData(bean,user);
			String planid =bean.getTestPlanId();
			List planstations = planBO.getPlanStationsByPlanId(planid);
			request.getSession().setAttribute("stations", planstations);
			response.setCharacterEncoding("GBK");
			String script = "<script>window.parent.location.href=window.parent.location.href;parent.close();</script>";
			response.getWriter().write(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 转到修改基站数据页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward eidtEnteringStationForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String stationid = request.getParameter("stationid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanStationBO stationBO = (TestPlanStationBO) ctx.getBean("testPlanStationBO");
		TestPlanStationDataBO stationDataBO = (TestPlanStationDataBO)ctx.getBean("testPlanStationDataBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanStation station =  stationBO.getTestPlanStationById(stationid);
		Point point = planBO.getPoint(station.getTestStationId());
		station.setStationName(point.getPointName());
		List tester = planBO.getUsers(userinfo,"");
		request.setAttribute("station",station);
		request.setAttribute("testMan",tester);
		String planid = station.getTestPlanId();
		String sid = station.getTestStationId();
		TestStationData data = stationDataBO.getStationDataByPlanIdAndStationId(planid, sid);
		request.setAttribute("data",data);
		return mapping.findForward("enteringStationEditForm");
	}




	/**
	 * 保存基站录入数据的审核人
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveEnteringApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
			String approvers = request.getParameter("approvers");
			String mobiles = request.getParameter("mobiles");
			String rmobiles = request.getParameter("rmobiles");
			String reads = request.getParameter("reads");
			String testDataId = request.getParameter("dataid");
			String planid = request.getParameter("planid");
			WebApplicationContext ctx = getWebApplicationContext();
			TestPlanStationDataBO stationDataBO = (TestPlanStationDataBO)ctx.getBean("testPlanStationDataBO");
			stationDataBO.saveApprove(user,testDataId,planid,approvers,mobiles,reads,rmobiles);
			log(request, "数据录入", "技术维护 ");
		}catch(Exception e){
			e.printStackTrace();
		}
		return forwardInfoPage(mapping, request, "25saveEnteringApproveOK");
	}



















}