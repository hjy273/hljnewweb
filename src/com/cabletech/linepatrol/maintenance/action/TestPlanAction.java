package com.cabletech.linepatrol.maintenance.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Point;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.linepatrol.commons.module.Evaluate;
import com.cabletech.linepatrol.maintenance.beans.TestPlanBean;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestPlanStation;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;
import com.cabletech.linepatrol.maintenance.service.TestPlanApproveBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanBO;
import com.cabletech.linepatrol.maintenance.service.TestPlanExamBO;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

/**
 * 测试计划
 * 
 * @author Administrator
 * 
 */
public class TestPlanAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 转到添加测试计划的界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addTestPlanForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().setAttribute("testPlan", null);
		request.getSession().setAttribute("planStations", null);
		request.getSession().setAttribute("planLines", null);
		return mapping.findForward("addTestPlanForm");
	}

	/**
	 * 转到待办列表界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getActWorkForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		// String userid = user.getUserID();
		String taskName = request.getParameter("task_name");
		List waitPlans = planBO.getWaitWork(user, taskName);
		request.setAttribute("task_name", taskName);
		request.getSession().setAttribute("waitPlans", waitPlans);
		super.setPageReset(request);
		return mapping.findForward("waitWorklist");
	}

	/**
	 * 载入派单流程图页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTestPlanProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskNames = request.getParameter("task_names");
		TestPlanBO bo = (TestPlanBO) ctx.getBean("testPlanBO");
		String condition = "";
		List waitHandleTaskNum = bo.queryForHandleTestPlanNum(condition,
				userInfo);
		request.setAttribute("wait_create_plan_num", waitHandleTaskNum.get(0));
		request.setAttribute("wait_approve_plan_num", waitHandleTaskNum.get(1));
		request.setAttribute("wait_record_num", waitHandleTaskNum.get(2));
		request.setAttribute("wait_approve_record_num", waitHandleTaskNum
				.get(3));
		request.setAttribute("wait_evaluate_num", waitHandleTaskNum.get(4));
		request.setAttribute("task_name", taskName);
		if (taskNames != null) {
			request.setAttribute("task_names", taskNames.split(","));
		}
		if (request.getParameter("forward") != null
				&& !"".equals(request.getParameter("forward").trim())) {
			return mapping.findForward(request.getParameter("forward"));
		}
		return mapping.findForward("view_test_plan_process");
	}

	/**
	 * 保存测试计划到session，然后进行下一步操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addTestPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestPlanBean bean = (TestPlanBean) request.getSession().getAttribute(
				"testPlan");
		if (bean == null) {
			bean = (TestPlanBean) form;
		}
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		try {
			request.getSession().setAttribute("testPlan", bean);
			String plantype = bean.getTestPlanType();
			if (plantype.equals(MainTenanceConstant.LINE_TEST)) {
				// String begindate = bean.getTestBeginDate();
				// int cablenum = planBO.getCableNumber(userinfo,begindate);
				// request.setAttribute("cablenum", cablenum);
				Map planCables = (HashMap) request.getSession().getAttribute(
						"planLines");
				if (planCables == null) {
					planCables = new HashMap();
				}
				request.getSession().setAttribute("planLines", planCables);
				return mapping.findForward("addCableListForm");
			}
			if (plantype.equals(MainTenanceConstant.STATION_TEST)) {
				Map planStations = (HashMap) request.getSession().getAttribute(
						"planStations");
				if (planStations == null) {
					planStations = new HashMap();
				}
				request.getSession().setAttribute("planStations", planStations);
				return mapping.findForward("addStationListForm");
			}
			return mapping.findForward("addCableListForm");
		} catch (Exception e) {
			e.printStackTrace();
			return forwardErrorPage(mapping, request, "error");
		}

	}

	public ActionForward getCablesByLevelAndName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		response.setContentType("text/json; charset=GBK");
		String level = request.getParameter("level");
		String cableName = request.getParameter("cableName");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanBean bean = (TestPlanBean) request.getSession().getAttribute(
				"testPlan");
		String begindate = bean.getTestBeginDate();
		List cables = planBO.getCable(user, level, cableName, begindate);
		JSONArray ja = JSONArray.fromObject(cables);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	public ActionForward getTestNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		response.setContentType("text/json; charset=GBK");
		String cablelineId = request.getParameter("cablelineId");
		String testBeginDate = request.getParameter("testBeginDate");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		int testNum = planBO.getTestNumYearPlan(user, cablelineId,
				testBeginDate);
		String str = "";
		if (testNum > 0) {
			int finishedNum = planBO.getFinishedNum(user, cablelineId,
					testBeginDate);
			str = "年计划申请测试次数:" + testNum + "次，已经做入计划：" + finishedNum + "次";
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 查询光缆ab段信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getCablePointInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String cablelineId = request.getParameter("cablelineId");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		RepeaterSection r = planBO.getRepeaterSection(cablelineId);
		String pointa = r.getPointa();
		String pointz = r.getPointz();
		// 2010-6-1 杨隽 修改 没有数据时显示“A端（B端）没有数据” START
		if (pointa == null) {
			pointa = "数据没有录入";
		}
		if (pointz == null) {
			pointz = "数据没有录入";
		}
		// 2010-6-1 杨隽 修改 没有数据时显示“A端（B端）没有数据” END
		StringBuffer sb = new StringBuffer();
		sb
				.append("<input name=\"cablelineTestPort\" type=\"radio\" value=\"A\" checked=\"checked\" />");
		sb.append("A端:" + pointa);
		sb
				.append("<input type=\"radio\" name=\"cablelineTestPort\" value=\"B\" />");
		sb.append(" B端:" + pointz);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(sb.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 转到增加中继段页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCableForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// List cables = planBO.getCable(userinfo,"","");
		// request.setAttribute("cables", cables);
		return mapping.findForward("addCableForm");
	}

	/**
	 * 保存中继段信息到session
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestPlanBean bean = (TestPlanBean) form;
		TestPlanLine cable = new TestPlanLine();
		String testBeginDate = bean.getTestBeginDate();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		try {
			Map<String, TestPlanLine> planCables = (HashMap) request
					.getSession().getAttribute("planLines");
			response.setCharacterEncoding("GBK");

			String[] cableIds = bean.getCablelineIds();
			for (int i = 0; cableIds != null && i < cableIds.length; i++) {
				cable = new TestPlanLine();
				String cableId = cableIds[i];// bean.getCablelineId();
				if (planCables == null) {
					planCables = new HashMap();
				} else {
					if (planCables.containsKey(cableId)) {
						String script = "<script >alert(\"中继段不能重复!\");history.go(-1);</script>";
						response.getWriter().write(script);
						return null;
					}
				}
				BeanUtil.objectCopy(bean, cable);
				cable.setCablelineId(cableId);
				WebApplicationContext ctx = getWebApplicationContext();
				TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
				// ConPerson user = planBO.getConPerson(cable.getTestMan());
				RepeaterSection res = planBO.getRepeaterSection(cableId);// cable.getCablelineId()
				// cable.setTestManName(user.getName());
				cable.setCablelineName(res.getSegmentname());
				cable.setContractorTime(DateUtil.DateToString(res
						.getFinishtime(), "yyyy年MM月"));

				int testNum = planBO.getTestNumYearPlan(user, cableId,
						testBeginDate);
				cable.setCablePlanTestNum(testNum);
				if (testNum > 0) {
					int finishedNum = planBO.getFinishedNum(user, cableId,
							testBeginDate);
					cable.setCablePlanedTestNum(finishedNum);
				}

				planCables.put(cableId, cable);
			}

			request.getSession().setAttribute("planLines", planCables);
			String script = "<script> window.parent.location.href=window.parent.location.href; parent.close();</script>";
			response.getWriter().write(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转到修改中继段的页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward updateCableForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String cableid = request.getParameter("cableid");
		String testBeginDate = request.getParameter("testBeginDate");
		Map<String, TestPlanLine> planCables = (HashMap) request.getSession()
				.getAttribute("planLines");
		if (planCables.containsKey(cableid)) {
			TestPlanLine line = planCables.get(cableid);
			WebApplicationContext ctx = getWebApplicationContext();
			TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
			// UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
			// "LOGIN_USER");
			// List cables = planBO.getCable(userinfo,"","");
			int testNum = planBO.getTestNumYearPlan(user, cableid,
					testBeginDate);
			String str = "";
			if (testNum > 0) {
				int finishedNum = planBO.getFinishedNum(user, cableid,
						testBeginDate);
				str = "年计划申请测试次数:" + testNum + "次，已经做入计划：" + finishedNum + "次";
			}
			// request.setAttribute("cables", cables);
			RepeaterSection repeat = planBO.getRepeaterSection(line
					.getCablelineId());
			request.setAttribute("repeat", repeat);
			request.setAttribute("TestPlanBean", line);
			request.setAttribute("str", str);
			return mapping.findForward("editCableForm");
		}
		return null;
	}

	/**
	 * 修改中继段
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String oldCablelineId = request.getParameter("oldCablelineId");
		TestPlanBean bean = (TestPlanBean) form;
		TestPlanLine cable = new TestPlanLine();
		try {
			Map<String, TestPlanLine> planCables = (HashMap) request
					.getSession().getAttribute("planLines");
			planCables.remove(oldCablelineId);
			String cableId = bean.getCablelineId();
			response.setCharacterEncoding("GBK");
			if (planCables.containsKey(cableId)) {
				String script = "<script >alert(\"中继段不能重复!\");history.go(-1);</script>";
				response.getWriter().write(script);
				return null;
			}
			BeanUtil.objectCopy(bean, cable);
			WebApplicationContext ctx = getWebApplicationContext();
			TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
			// UserInfo user = planBO.getUser(cable.getTestMan());
			// ConPerson user = planBO.getConPerson(cable.getTestMan());
			RepeaterSection res = planBO.getRepeaterSection(cable
					.getCablelineId());
			// cable.setTestManName(user.getName());
			cable.setCablelineName(res.getSegmentname());
			cable.setContractorTime(DateUtil.DateToString(res.getFinishtime(),
					"yyyy年MM月"));
			planCables.put(cableId, cable);
			request.getSession().setAttribute("planLines", planCables);
			String script = "<script> window.parent.location.href=window.parent.location.href; parent.close();</script>";
			response.getWriter().write(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除中继段
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public void deleteCable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String cableid = request.getParameter("cableid");
		Map<String, TestPlanLine> planCables = (HashMap) request.getSession()
				.getAttribute("planLines");
		if (planCables.containsKey(cableid)) {
			planCables.remove(cableid);
		}
		request.getSession().setAttribute("planLines", planCables);
	}

	/**
	 * 转到增加基站页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addStationForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List stations = planBO.getStation(userinfo);
		request.setAttribute("stations", stations);
		return mapping.findForward("addStationForm");
	}

	/**
	 * 保存测试基站信息到session
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addStation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TestPlanBean bean = (TestPlanBean) form;
		TestPlanStation station = new TestPlanStation();
		try {
			String stationId = bean.getTestStationId();
			Map<String, TestPlanStation> planStations = (HashMap) request
					.getSession().getAttribute("planStations");
			response.setCharacterEncoding("GBK");
			if (planStations == null) {
				planStations = new HashMap<String, TestPlanStation>();
			} else {
				if (planStations.containsKey(stationId)) {
					String script = "<script >alert(\"基站不能重复!\");history.go(-1);</script>";
					response.getWriter().write(script);
					return null;
				}
			}
			BeanUtil.objectCopy(bean, station);
			WebApplicationContext ctx = getWebApplicationContext();
			TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
			// UserInfo user = planBO.getUser(station.getTestMan());
			// ConPerson user = planBO.getConPerson(station.getTestMan());
			Point point = planBO.getPoint(station.getTestStationId());
			// station.setTestManName(user.getName());
			station.setStationName(point.getPointName());
			planStations.put(stationId, station);
			request.getSession().setAttribute("planStations", planStations);
			String script = "<script> window.parent.location.href=window.parent.location.href; parent.close();</script>";
			response.getWriter().write(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转到修改基站页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward updateStationForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String stationid = request.getParameter("stationid");
		Map<String, TestPlanStation> planStations = (HashMap) request
				.getSession().getAttribute("planStations");
		if (planStations.containsKey(stationid)) {
			TestPlanStation station = planStations.get(stationid);
			WebApplicationContext ctx = getWebApplicationContext();
			TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
					"LOGIN_USER");
			List stations = planBO.getStation(userinfo);
			request.setAttribute("stations", stations);
			request.setAttribute("TestPlanBean", station);
			return mapping.findForward("editStationForm");
		}
		return null;
	}

	/**
	 * 修改基站
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateStation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String oldStastionId = request.getParameter("oldStastionId");
		TestPlanBean bean = (TestPlanBean) form;
		TestPlanStation station = new TestPlanStation();
		try {
			Map<String, TestPlanStation> planStations = (HashMap) request
					.getSession().getAttribute("planStations");
			planStations.remove(oldStastionId);
			String stationId = bean.getTestStationId();
			response.setCharacterEncoding("GBK");
			if (planStations.containsKey(stationId)) {
				String script = "<script >alert(\"基站不能重复!\");history.go(-1);</script>";
				response.getWriter().write(script);
				return null;
			}
			BeanUtil.objectCopy(bean, station);
			WebApplicationContext ctx = getWebApplicationContext();
			TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
			// UserInfo user = planBO.getUser(station.getTestMan());
			// ConPerson user = planBO.getConPerson(station.getTestMan());
			Point point = planBO.getPoint(station.getTestStationId());
			// station.setTestManName(user.getName());
			station.setStationName(point.getPointName());
			planStations.put(stationId, station);
			request.getSession().setAttribute("planStations", planStations);
			String script = "<script> window.parent.location.href=window.parent.location.href; parent.close();</script>";
			response.getWriter().write(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除基站
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public void deleteStation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String stationid = request.getParameter("stationid");
		Map<String, TestPlanStation> planStations = (HashMap) request
				.getSession().getAttribute("planStations");
		planStations.remove(stationid);
		request.getSession().setAttribute("planStations", planStations);
	}

	/**
	 * 保存或者修改测试计划以及测试数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveTestPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			WebApplicationContext ctx = getWebApplicationContext();
			TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
					"LOGIN_USER");
			TestPlanBean bean = (TestPlanBean) request.getSession()
					.getAttribute("testPlan");
			if (request.getParameter("isTempSaved") != null
					&& !"".equals(request.getParameter("isTempSaved"))) {
				bean.setIsTempSaved(request.getParameter("isTempSaved"));
			} else {
				bean.setIsTempSaved(MainTenanceConstant.PLAN_YES_SUBMITTED);
			}
			Map<String, TestPlanLine> planCables = (HashMap) request
					.getSession().getAttribute("planLines");
			Map<String, TestPlanStation> planStations = (HashMap) request
					.getSession().getAttribute("planStations");
			TestPlan plan = planBO.addTestPlan(bean, userinfo, planCables,
					planStations);
			String planId = plan.getId();
			if (planId != null && !"".equals(planId)) {
				if (MainTenanceConstant.PLAN_NO_SUBMITTED.equals(bean
						.getIsTempSaved())) {
					log(request, "暂存修改测试计划（计划名称为："
							+ planBO.getTestPlanById(planId).getTestPlanName()
							+ "）", "技术维护 ");
					return forwardInfoPage(mapping, request,
							"25editPlanTempSavedOK");
				}
				log(request, "修改测试计划（计划名称为："
						+ planBO.getTestPlanById(planId).getTestPlanName()
						+ "）", "技术维护 ");
				return forwardInfoPage(mapping, request, "25editPlanOK");
			}
			if (MainTenanceConstant.PLAN_NO_SUBMITTED.equals(bean
					.getIsTempSaved())) {
				log(request, "暂存测试计划（计划名称为："
						+ planBO.getTestPlanById(planId).getTestPlanName()
						+ "）", "技术维护 ");
				return forwardInfoPage(mapping, request, "25addPlanTempSavedOK");
			}
			log(request, "添加测试计划（计划名称为："
					+ planBO.getTestPlanById(planId).getTestPlanName() + "）",
					"技术维护 ");
			return forwardInfoPage(mapping, request, "25addPlanOK");
		} catch (Exception e) {
			logger.error("保存测试计划以及测试数据出现异常:", e);
			e.getStackTrace();
		}
		return forwardErrorPage(mapping, request, "error");
	}

	/**
	 * 转到修改测试计划的界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editTestPlanForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserInfo user = (UserInfo) request.getSession().getAttribute(
					"LOGIN_USER");
			request.getSession().setAttribute("testPlan", null);
			request.getSession().setAttribute("planStations", null);
			request.getSession().setAttribute("planLines", null);
			String planid = request.getParameter("planid");
			WebApplicationContext ctx = getWebApplicationContext();
			TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
			TestPlan plan = planBO.getTestPlanById(planid);
			request.setAttribute("testPlan", plan);
			String planType = plan.getTestPlanType();
			if (planType.equals(MainTenanceConstant.LINE_TEST)) {
				Map<String, TestPlanLine> planCables = planBO
						.getPlanLineByPlanId(planid);
				planBO.processPlanCables(user, plan.getTestBeginDate(),
						planCables);
				request.getSession().setAttribute("planLines", planCables);
			}
			if (planType.equals(MainTenanceConstant.STATION_TEST)) {
				Map<String, TestPlanStation> planStations = planBO
						.getPlanStationByPlanId(planid);
				request.getSession().setAttribute("planStations", planStations);
			}
			return mapping.findForward("editTestPlanForm");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forwardErrorPage(mapping, request, "error");
	}

	/**
	 * 审批时查看计划详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewPalnDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("dataApproves", null);
		request.getSession().setAttribute("data", null);
		String planId = request.getParameter("planId");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanApproveBO approveBO = (TestPlanApproveBO) ctx
				.getBean("testPlanApproveBO");
		TestPlan plan = planBO.getTestPlanById(planId);
		String planType = plan.getTestPlanType();
		TestData data = planBO.getDataByPlanId(planId);
		if (data != null) {
			String dataid = data.getId();
			List dataApproves = approveBO
					.getPlanApproveHistorys(dataid, "data");
			request.getSession().setAttribute("dataApproves", dataApproves);
			request.getSession().setAttribute("data", data);
		}
		if (planType.equals(MainTenanceConstant.LINE_TEST)) {
			List list = planBO.getPlanLinesByPlanId(planId);
			request.getSession().setAttribute("list", list);
			return mapping.findForward("viewPalnCableDetail");
		}
		if (planType.equals(MainTenanceConstant.STATION_TEST)) {
			List list = planBO.getPlanStationsByPlanId(planId);
			request.getSession().setAttribute("list", list);
			return mapping.findForward("viewPalnStationDetail");
		}
		return null;
	}

	/**
	 * 查看计划信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewPaln(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String planId = request.getParameter("planId");
		String query = request.getParameter("query");
		String isread = request.getParameter("isread");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		TestPlanApproveBO approveBO = (TestPlanApproveBO) ctx
				.getBean("testPlanApproveBO");
		TestPlanExamBO examBO = (TestPlanExamBO) ctx.getBean("testPlanExamBO");
		TestPlan plan = planBO.getTestPlanById(planId);
		String planid = plan.getId();
		String userid = plan.getCreatorId();
		String conid = plan.getContractorId();
		UserInfo user = planBO.getUser(userid);
		Contractor c = planBO.getContratroById(conid);
		List planApproves = approveBO.getPlanApproveHistorys(planid, "plan");
		request.setAttribute("plan", plan);
		request.setAttribute("user", user);
		request.setAttribute("c", c);
		request.setAttribute("planApproves", planApproves);
		String planstate = plan.getTestState();
		if (planstate.equals(MainTenanceConstant.TEST_PLAN_END)) {
			Evaluate eva = examBO.getEvaluate(planid,
					MainTenanceConstant.LP_EVALUATE_TEST_PLAN);
			request.setAttribute("evaluate", eva);
		}
		TestData data = planBO.getDataByPlanId(planid);
		String dataid = "";
		if (data != null) {
			dataid = data.getId();
		}
		request.setAttribute("dataid", dataid);
		request.setAttribute("query", query);
		request.setAttribute("isread", isread);
		return mapping.findForward("viewplan");
	}

	/**
	 *已阅读
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward readPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String planid = request.getParameter("planid");
		String dataid = request.getParameter("dataid");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO planBO = (TestPlanBO) ctx.getBean("testPlanBO");
		planBO.updateReader(user, planid, dataid);
		if (dataid != null && !"".equals(dataid)) {
			TestData data = planBO.getDataByPlanId(planid);
			String type = MainTenanceConstant.TEST_DATA_APPROVE_WAIT;
			if (data != null && data.getState().equals(type)) {
				return forwardInfoPage(mapping, request, "testPlanDataReaded");
			}
		}
		return forwardInfoPage(mapping, request, "testPlanReaded");
	}

	/**
	 * 查询已办工作列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getFinishHandledWork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		TestPlanBO bo = (TestPlanBO) ctx.getBean("testPlanBO");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String taskName = request.getParameter("task_name");
		String taskOutCome = request.getParameter("task_out_come");
		List list = bo.getHandeledWorks(user, taskName, taskOutCome);
		request.getSession().setAttribute("handeledPlans", list);
		super.setPageReset(request);
		return mapping.findForward("finishHandelPlans");
	}

	/**
	 * 执行取消技术维护任务表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelTestPlanForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String testPlanId = request.getParameter("test_plan_id");
		request.setAttribute("test_plan_id", testPlanId);
		return mapping.findForward("test_plan_cancel");
	}

	/**
	 * 执行取消技术维护任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancelTestPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		TestPlanBean bean = (TestPlanBean) form;
		// bean.setId(request.getParameter("cutId"));
		TestPlanBO bo = (TestPlanBO) ctx.getBean("testPlanBO");
		bo.cancelTestPlan(bean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href='" + url + "';");
		out.print("window.close();");
		out.print("</script>");
		return null;
		// return super.forwardInfoPageWithUrl(mapping, request,
		// "CANCEL_TEST_PLAN_SUCCESS", url);
	}

}