package com.cabletech.linepatrol.maintenance.action;

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

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Point;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.linepatrol.maintenance.beans.TestPlanApproveBean;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestChipData;
import com.cabletech.linepatrol.maintenance.module.TestCoreData;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestPlanStation;
import com.cabletech.linepatrol.maintenance.module.TestProblem;
import com.cabletech.linepatrol.maintenance.module.TestStationData;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;
import com.cabletech.linepatrol.maintenance.service.TestPlanApproveBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanExportBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanLineBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanLineDataBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanStationBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanStationDataBO;
import com.cabletech.linepatrol.resource.model.RepeaterSection;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;

/**
 * 审核
 * @author Administrator
 *
 */
public class TestPlanApproveAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到审核测试计划的界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveTestPlanForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String planid = request.getParameter("planid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanApproveBO approveBO = (TestPlanApproveBO) ctx.getBean("testPlanApproveBO");
		TestPlan plan = planBO.getTestPlanById(planid);
		String userid = plan.getCreatorId();
		String conid = plan.getContractorId();
		UserInfo user = planBO.getUser(userid);
		Contractor c = planBO.getContratroById(conid);
		request.setAttribute("testPlan",plan);
		request.setAttribute("user",user);
		request.setAttribute("c",c);
		String act = request.getParameter("act");
		request.setAttribute("act",act);
		List planApproves = approveBO.getPlanApproveHistorys(planid, "plan");
		request.setAttribute("planApproves",planApproves);
		return mapping.findForward("approveTestPlanForm");
	}

	/**
	 * 保存审核测试计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveTestPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanApproveBO bo = (TestPlanApproveBO) ctx.getBean("testPlanApproveBO");
		TestPlanBO testPlanBO = (TestPlanBO) ctx.getBean("testPlanBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		TestPlanApproveBean bean = (TestPlanApproveBean)form;
		bean.setApproverId(userinfo.getUserID());
		bean.setObjectType(MainTenanceConstant.LP_TEST_PLAN);
		String planid =request.getParameter("planid");
		String act= request.getParameter("act");
		String testPlanName = testPlanBO.getTestPlanById(planid).getTestPlanName();
		if(act!=null && act.equals("transfer")){
			String approvers = request.getParameter("approver");
			bean.setTransfer(approvers);
			bean.setApproveResult(MainTenanceConstant.APPROVE_RESULT_TRANSFER);
		}
		try {
			bean.setApproverId(userinfo.getUserID());
			bean.setObjectId(planid);
			bo.addApproveTestPlan(userinfo,bean,planid);
			String approveResult = bean.getApproveResult();
			if(act!=null && act.equals("transfer")){
				log(request, "转审测试计划（计划名称为："+testPlanName+"）", "技术维护 ");
				return this.forwardInfoPage(mapping, request, "approveTestPlanTransferOK");
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_NO)){//不通过
				log(request, "审核未通过测试计划（计划名称为："+testPlanName+"）", "技术维护 ");
				return this.forwardInfoPage(mapping, request, "approveTestPlanNoPass");
			}
			log(request, "审核通过测试计划（计划名称为："+testPlanName+"）", "技术维护 ");
			return this.forwardInfoPage(mapping, request, "approveTestPlanPass");

		} catch (Exception e) {
			logger.error("审核测试计划失败:"+e.getMessage());
			e.printStackTrace();
		}
		return this.forwardErrorPage(mapping, request, "error");
	}

	/**
	 * 转到审核录入测试数据的界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveTestDataForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("dataApproves",null);
		request.getSession().setAttribute("approvetimes",null);
		String planid = request.getParameter("planid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanApproveBO approveBO = (TestPlanApproveBO) ctx.getBean("testPlanApproveBO");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestData data = planBO.getTestDataByPlanID(planid);
		TestPlan plan = planBO.getTestPlanById(planid);
		UserInfo user = planBO.getUser(plan.getCreatorId());
		String conid = plan.getContractorId();
		Contractor c = planBO.getContratroById(conid);
		String plantype = plan.getTestPlanType();
		int approvetimes = data.getApproveTimes();
		String dataid = data.getId();
		String act = request.getParameter("act");
		request.getSession().setAttribute("act",act);
		request.getSession().setAttribute("plan",plan);
		request.getSession().setAttribute("contraName",c.getContractorName());
	//	request.getSession().setAttribute("planName",plan.getTestPlanName());
		request.getSession().setAttribute("dataid",dataid);
		request.getSession().setAttribute("userName",user.getUserName());
		List dataApproves = approveBO.getPlanApproveHistorys(dataid, "data");
		request.getSession().setAttribute("dataApproves",dataApproves);
		request.getSession().setAttribute("approvetimes",approvetimes);
		if(plantype.equals(MainTenanceConstant.LINE_TEST)){
			List planCables = planBO.getPlanLinesByPlanId(planid);
			request.getSession().setAttribute("planLines",planCables);
			return mapping.findForward("approveCableDataForm");
		}
		if(plantype.equals(MainTenanceConstant.STATION_TEST)){
			List stations = planBO.getPlanStationsByPlanId(planid);
			request.getSession().setAttribute("stations",stations);
			return mapping.findForward("approveStationDataForm");
		}
		return mapping.findForward("approveCableDataForm");
	}


	/**
	 * 查看中继段录入数据的详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewCableData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String lineid = request.getParameter("lineid");
		String planid = request.getParameter("planid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanLineBO lineBO = (TestPlanLineBO) ctx.getBean("testPlanLineBO");
		TestPlanLineDataBO lineDataBO = (TestPlanLineDataBO)ctx.getBean("testPlanLineDataBO");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanLine line = lineBO.getTestPlanLineById(lineid);
		RepeaterSection res = planBO.getRepeaterSection(line.getCablelineId());
		line.setCablelineName(res.getSegmentname());
		String lid = line.getCablelineId();
		TestCableData data = lineDataBO.getLineDataByPlanIdAndLineId(planid, lid);
		String dataid= data.getId();
		List<TestProblem> problems = lineDataBO.getProblemsByPlanIdAndLineId(planid, lid);
		Map<Object,TestChipData> chips = lineDataBO.getChipsByCableDataId(dataid);
		request.setAttribute("line",line);
		request.setAttribute("data",data);
		request.setAttribute("cableProblems", problems);
		request.getSession().setAttribute("planChips", chips);
		request.setAttribute("coreNumber",res.getCoreNumber());
		return mapping.findForward("viewCableData");
	}
	
	
	/**
	 * 查看中继段录入数据的详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewCableDataById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String cableDataId = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanLineBO lineBO = (TestPlanLineBO) ctx.getBean("testPlanLineBO");
		TestPlanLineDataBO lineDataBO = (TestPlanLineDataBO)ctx.getBean("testPlanLineDataBO");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestCableData data =lineDataBO.getLineDataById(cableDataId);
		String planid = data.getTestPlanId();
		String cableid = data.getTestCablelineId();
		TestPlanLine line = lineBO.getTestPlanLineByPlanIdAndCableId(planid, cableid);
		RepeaterSection res = planBO.getRepeaterSection(line.getCablelineId());
		line.setCablelineName(res.getSegmentname());
		String dataid= data.getId();
		List<TestProblem> problems = lineDataBO.getProblemsByPlanIdAndLineId(planid, cableid);
		Map<Object,TestChipData> chips = lineDataBO.getChipsByCableDataId(dataid);
		request.setAttribute("querycable", "querycable");
		request.setAttribute("line",line);
		request.setAttribute("data",data);
		request.setAttribute("cableProblems", problems);
		request.getSession().setAttribute("planChips", chips);
		request.setAttribute("coreNumber",res.getCoreNumber());
		return mapping.findForward("viewCableData");
	}

	/**
	 * 查看基站录入数据的详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewStationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String planid = request.getParameter("planid");
		String stationid = request.getParameter("stationid");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanStationBO stationBO = (TestPlanStationBO) ctx.getBean("testPlanStationBO");
		TestPlanStationDataBO stationDataBO = (TestPlanStationDataBO)ctx.getBean("testPlanStationDataBO");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanStation station =  stationBO.getTestPlanStationById(stationid);
		Point point = planBO.getPoint(station.getTestStationId());
		station.setStationName(point.getPointName());
		String sid = station.getTestStationId();
		TestStationData data = stationDataBO.getStationDataByPlanIdAndStationId(planid, sid);
		request.setAttribute("station",station);
		request.setAttribute("data",data);
		return mapping.findForward("viewStationData");
	}

	/**
	 * 保存审核录入数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approveTestData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanApproveBO bo = (TestPlanApproveBO) ctx.getBean("testPlanApproveBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		TestPlanApproveBean bean = (TestPlanApproveBean)form;
		bean.setApproverId(userinfo.getUserID());
		TestPlan plan=(TestPlan)request.getSession().getAttribute("plan");
		bean.setObjectType(MainTenanceConstant.LP_TEST_DATA);
		String dataid =request.getParameter("dataid");
		String act= request.getParameter("act");
		if(act!=null && act.equals("transfer")){
			String approvers = request.getParameter("approver");
			bean.setTransfer(approvers);
			bean.setApproveResult(MainTenanceConstant.APPROVE_RESULT_TRANSFER);
		}
		try {
			bean.setApproverId(userinfo.getUserID());
			bean.setObjectId(dataid);
			bo.addApproveTestData(userinfo,bean);
			String approveResult = bean.getApproveResult();
			if(act!=null && act.equals("transfer")){
				log(request, "转审测数据录入（计划名称为："+plan.getTestPlanName()+"）", "技术维护 ");
				return this.forwardInfoPage(mapping, request, "approveTestDataTransfer");
			}
			log(request, "审核数据录入（计划名称为："+plan.getTestPlanName()+"）", "技术维护 ");
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_NO)){//不通过
				return this.forwardInfoPage(mapping, request, "approveTestDataNoPass");
			}
			return this.forwardInfoPage(mapping, request, "approveTestDataPass");

		} catch (Exception e) {
			logger.error("审核录入数据失败:"+e.getMessage());
			e.printStackTrace();
		}
		return this.forwardErrorPage(mapping, request, "error");
	}



	/**
	 * 导出光缆录入数据信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTestCabelData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String cableDataId = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanLineBO lineBO = (TestPlanLineBO) ctx.getBean("testPlanLineBO");
		TestPlanLineDataBO lineDataBO = (TestPlanLineDataBO)ctx.getBean("testPlanLineDataBO");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestCableData data =lineDataBO.getLineDataById(cableDataId);
		String planid = data.getTestPlanId();
		String cableid = data.getTestCablelineId();
		TestPlanLine line = lineBO.getTestPlanLineByPlanIdAndCableId(planid, cableid);
		RepeaterSection res = planBO.getRepeaterSection(line.getCablelineId());
		String dataid= data.getId();
		List<TestProblem> problems = lineDataBO.getProblemsByPlanIdAndLineId(planid, cableid);
		Map<Object,TestChipData> chips = lineDataBO.getChipsByCableDataId(dataid);
		TestPlanExportBO bo = new TestPlanExportBO();
		bo.exportTestCableDate(line,res,data,problems,chips, response);
		return null;
	}

	/**
	 * 导出数据分析
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportAnaylseData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*String chipDataId = request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanLineBO lineBO = (TestPlanLineBO) ctx.getBean("testPlanLineBO");
		TestPlanLineDataBO lineDataBO = (TestPlanLineDataBO)ctx.getBean("testPlanLineDataBO");
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestCoreData coreData = planBO.getCoreData(chipDataId);*/
		String id = request.getParameter("id");
		String trunkName = request.getParameter("cableLineName");
		int newSeq = 0;
		if(id!=null && !"".equals(id)){
			newSeq = Integer.parseInt(id);
		}
		Map<Object,TestChipData> planChips = (HashMap) request.getSession().getAttribute("planChips");
		TestChipData data = planChips.get(newSeq);
		TestPlanExportBO bo = new TestPlanExportBO();
		bo.exportAnaylseData(data,trunkName, response);
		return null;
	}

	
	

}