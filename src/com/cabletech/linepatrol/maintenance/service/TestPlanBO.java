package com.cabletech.linepatrol.maintenance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.ContractorDAOImpl;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Point;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.maintenance.beans.TestPlanBean;
import com.cabletech.linepatrol.maintenance.beans.TestPlanQueryStatBean;
import com.cabletech.linepatrol.maintenance.dao.TestChipDataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestConnectorWasteDAO;
import com.cabletech.linepatrol.maintenance.dao.TestCoreLengthDAO;
import com.cabletech.linepatrol.maintenance.dao.TestCoredataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestDataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestDecayconstantDAO;
import com.cabletech.linepatrol.maintenance.dao.TestEndWasteDAO;
import com.cabletech.linepatrol.maintenance.dao.TestExceptionEventDAO;
import com.cabletech.linepatrol.maintenance.dao.TestLineDataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestOtherAnalyseDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanLineDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanStationDAO;
import com.cabletech.linepatrol.maintenance.dao.TestProblemDAO;
import com.cabletech.linepatrol.maintenance.dao.TestStationDataDAO;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestChipData;
import com.cabletech.linepatrol.maintenance.module.TestCoreData;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestPlanStation;
import com.cabletech.linepatrol.maintenance.module.TestProblem;
import com.cabletech.linepatrol.maintenance.workflow.MaintenanceWorkflowBO;
import com.cabletech.linepatrol.resource.dao.RepeaterSectionDao;
import com.cabletech.linepatrol.resource.model.RepeaterSection;
import com.cabletech.sysmanage.dao.ConPersonDAOImpl;
import com.cabletech.sysmanage.domainobjects.ConPerson;

@Service
@Transactional
public class TestPlanBO extends EntityManager<TestPlan, String> {
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;
	@Resource(name = "testPlanDAO")
	private TestPlanDAO dao;
	@Resource(name = "repeaterSectionDao")
	private RepeaterSectionDao resDao;
	@Resource(name = "testPlanLineDAO")
	private TestPlanLineDAO planLineDAO;
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	@Resource(name = "testPlanStationDAO")
	private TestPlanStationDAO planStationDAO;
	@Resource(name = "testStationDataDAO")
	private TestStationDataDAO stationDataDAO;
	@Resource(name = "testDataDAO")
	private TestDataDAO dataDAO;
	@Resource(name = "testLineDataDAO")
	private TestLineDataDAO lineDataDAO;
	@Resource(name = "testChipDataDAO")
	private TestChipDataDAO chipDAO;

	@Resource(name = "testCoredataDAO")
	private TestCoredataDAO coredataDAO;
	@Resource(name = "testConnectorWasteDAO")
	private TestConnectorWasteDAO connectorWasteDAO;
	@Resource(name = "testCoreLengthDAO")
	private TestCoreLengthDAO coreLengthDAO;
	@Resource(name = "testDecayconstantDAO")
	private TestDecayconstantDAO decayconstantDAO;
	@Resource(name = "testEndWasteDAO")
	private TestEndWasteDAO endWasteDAO;
	@Resource(name = "testExceptionEventDAO")
	private TestExceptionEventDAO exceptionEventDAO;
	@Resource(name = "testOtherAnalyseDAO")
	private TestOtherAnalyseDAO otherAnalyseDAO;

	@Resource(name = "testProblemDAO")
	private TestProblemDAO problemDAO;

	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	@Autowired
	private MaintenanceWorkflowBO workflowBo;
	@Resource(name = "conPersonDAOImpl")
	private ConPersonDAOImpl conPersonDAOImpl;

	/**
	 * 查询年计划
	 * 
	 * @param id
	 *            年计划系统id
	 * @return
	 */
	public TestPlan getTestPlanById(String id) {
		TestPlan plan = dao.getTestPlanById(id);
		String cancelUserName = userInfoDao.getUserName(plan.getCancelUserId());
		plan.setCancelUserName(cancelUserName);
		return plan;
	}

	/**
	 * 保存测试计划
	 * 
	 * @param bean
	 * @param userinfo
	 * @param planLines
	 *            中继段信息
	 * @param planStations
	 *            基站信息
	 * @return
	 * @throws Exception
	 */
	public TestPlan addTestPlan(TestPlanBean bean, UserInfo userinfo,
			Map<String, TestPlanLine> planLines,
			Map<String, TestPlanStation> planStations) throws Exception {
		String userid = userinfo.getUserID();
		TestPlan plan = new TestPlan();
		BeanUtil.objectCopy(bean, plan);
		plan.setCreateTime(new Date());
		plan.setContractorId(userinfo.getDeptID());
		plan.setCreatorId(userid);
		plan.setTestState(MainTenanceConstant.PLAN_WAIT_APPROVE);
		if (MainTenanceConstant.PLAN_NO_SUBMITTED.equals(bean.getIsTempSaved())) {
			plan.setTestState(MainTenanceConstant.PLAN_WAIT_SUBMIT);
		}
		plan.setRegionid(userinfo.getRegionid());
		dao.save(plan);
		String planid = plan.getId();
		String planType = plan.getTestPlanType();
		if (planType.equals(MainTenanceConstant.LINE_TEST)) {
			planLineDAO.savePlanLine(planLines, planid);
		}
		if (planType.equals(MainTenanceConstant.STATION_TEST)) {
			planStationDAO.savePlanStations(planStations, planid);
		}
		String approvers = bean.getApprovers();
		String reads = bean.getReads();
		approverDAO.deleteList(planid, MainTenanceConstant.LP_TEST_PLAN);
		approverDAO.saveApproverOrReader(approvers, planid,
				MainTenanceConstant.APPROVE_MAN,
				MainTenanceConstant.LP_TEST_PLAN);
		approverDAO.saveApproverOrReader(reads, planid,
				MainTenanceConstant.APPROVE_READ,
				MainTenanceConstant.LP_TEST_PLAN);
		String planId = bean.getId();
		String processInstanceId = "";
		if (planId == null || "".equals(planId)) {// 保存测试计划启动工作流
			Map variables = new HashMap();
			variables.put("assignee", userid);
			processInstanceId = workflowBo.createProcessInstance("maintence",
					planid, variables);
		}
		String mobiles = bean.getMobiles();
		String rmobiles = bean.getRmobiles();
		sendMsg(plan, mobiles, rmobiles);
		String mans = approvers + "," + reads;
		if (MainTenanceConstant.PLAN_YES_SUBMITTED
				.equals(bean.getIsTempSaved())) {
			planWorkFlow(plan, userinfo, mans);
		}
		addWorkFlowHistory(userinfo, processInstanceId, approvers, planid);
		return plan;
	}

	/**
	 * 发送短信 给计划审核人
	 */
	public void sendMsg(TestPlan plan, String mobile, String rmobiles) {
		String planid = plan.getId();
		String content = "【技术维护】您有一个名称为\"" + plan.getTestPlanName()
				+ "\"的测试计划等待您的及时审核。";
		// 测试计划审核人。
		logger.info("短信内容: " + mobile + ":" + content);
		super.sendMessage(content, mobile);
		// smSendProxy.simpleSend(mobile, content, null, null, true);
		// 抄送人
		if (rmobiles != null && !"".equals(rmobiles)) {
			String msg = "【技术维护】您有一个名称为\"" + plan.getTestPlanName()
					+ "\"的测试计划等待您查收。";
			List<String> mobileList = StringUtils.string2List(rmobiles, ",");
			logger.info("短信内容: " + mobile + ":" + rmobiles);
			super.sendMessage(msg, mobileList);
			// for (String rmobile : mobileList) {
			// smSendProxy.simpleSend(rmobile, msg, null, null, true);
			// }
		}

		// 保存短信记录
		SMHistory history = new SMHistory();
		history.setSimIds(mobile + "," + rmobiles);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(MainTenanceConstant.LP_TEST_PLAN);
		history.setObjectId(planid);
		history.setBusinessModule(MainTenanceConstant.MAINTENANCE_MODULE);
		historyDAO.save(history);
	}

	// 执行保存测试计划的工作流处理过程
	public void planWorkFlow(TestPlan plan, UserInfo user, String approvers) {
		String planid = plan.getId();
		String userid = user.getUserID();
		Task task = workflowBo.getHandleTaskForId(userid, planid);
		if (task != null
				&& MaintenanceWorkflowBO.CREATE_PLAN_TASK
						.equals(task.getName())) {
			workflowBo.setVariables(task, "assignee", approvers);
			workflowBo.completeTask(task.getId(), "apply_approve");
			System.out.println("测试计划保存成功！");
		}
	}

	/**
	 * 保存流程历史
	 * 
	 * @param user
	 * @param processInstanceId
	 */
	public void addWorkFlowHistory(UserInfo user, String processInstanceId,
			String approver, String planid) {
		try {
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(null, user, processInstanceId,
					ModuleCatalog.MAINTENANCE);
			processHistoryBean.setHandledTaskName("start");
			processHistoryBean.setNextOperateUserId(approver);
			processHistoryBean.setObjectId(planid);
			processHistoryBean.setProcessAction("制定测试计划");
			processHistoryBean.setTaskOutCome("apply_approve");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		} catch (Exception e) {
			logger.error("制定测试计划增加处理流程失败:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 查询待办工作
	 * 
	 * @param taskName
	 * @return
	 */
	public List getWaitWork(UserInfo user, String taskName) {
		String userid = user.getUserID();
		String planType = MainTenanceConstant.LP_TEST_PLAN;
		String dataType = MainTenanceConstant.LP_TEST_DATA;
		String condition = "";
		List list = workflowBo.queryForHandleListBean(userid, condition,
				taskName);
		List resultList = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String objectid = (String) bean.get("id");
				String planstate = (String) bean.get("planstate");
				if (planstate.equals(MainTenanceConstant.PLAN_WAIT_APPROVE)) {// 判断用户是不是抄送人
					boolean read = approverDAO.isReadOnly(objectid, userid,
							planType);
					bean.set("isread", read + "");
				}
				if (planstate.equals(MainTenanceConstant.LOGGING_WAIT_APPROVE)) {
					TestData data = dataDAO.getDataByPlanId(objectid);
					String dataid = data.getId();
					boolean read = approverDAO.isReadOnly(dataid, userid,
							dataType);
					bean.set("isread", read + "");
				}
				String isread = (String) bean.get("isread");
				List wlist = getWaitHandelPlan(bean, user, objectid, isread);
				if (wlist != null && wlist.size() > 0) {
					resultList.add(wlist.get(0));
				}
			}
		}
		// return list;
		return resultList;
	}

	public List getWaitHandelPlan(BasicDynaBean bean, UserInfo user,
			String planid, String isread) {
		String userid = user.getUserID();
		List resultList = new ArrayList();
		if (isread.equals("true")) {
			List plans = dao.getPlanByReads(user, planid);
			if (plans != null && plans.size() > 0) {
				resultList.add(bean);
			} else {
				TestData data = dataDAO.getDataByPlanId(planid);
				if (data != null) {
					String dataid = data.getId();
					List datas = dao.getDataByReads(user, dataid);
					if (datas != null && datas.size() > 0) {
						resultList.add(bean);
					}
				}
			}
		} else {
			resultList.add(bean);
		}
		return resultList;
	}

	/**
	 * 根据年计划id查询制定的中继段
	 * 
	 * @param planId
	 * @return
	 */
	public List getPlanLinesByPlanId(String planId) {
		return dao.getPlanLinesByPlanId(planId);
	}

	/**
	 * 查询备纤数据是否录入完
	 * 
	 * @param planId
	 * @return
	 */
	public boolean judgeAllEnteringLine(String planId) {
		return dao.judgeAllEnteringLine(planId);
	}

	/**
	 * 根据年计划id查询制定的基站
	 * 
	 * @param planId
	 * @return
	 */
	public List getPlanStationsByPlanId(String planId) {
		return dao.getPlanStationsByPlanId(planId);
	}

	/**
	 * 查询基站数据是否录入完
	 * 
	 * @param planId
	 * @return
	 */
	public boolean judgeAllEnteringStation(String planId) {
		return dao.judgeAllEnteringStation(planId);
	}

	/**
	 * 根据年计划id查询制定的中继段
	 * 
	 * @param planId
	 * @return
	 */
	public Map<String, TestPlanLine> getPlanLineByPlanId(String planId) {
		Map<String, TestPlanLine> planCables = new HashMap<String, TestPlanLine>();
		List<TestPlanLine> list = dao.getPlanLineByPlanId(planId);
		for (TestPlanLine planLine : list) {
			String lineId = planLine.getCablelineId();
			// UserInfo user = getUser(planLine.getTestMan());
			// ConPerson user = getConPerson(planLine.getTestMan());
			RepeaterSection res = getRepeaterSection(planLine.getCablelineId());
			// planLine.setTestManName(user.getName());
			planLine.setCablelineName(res.getSegmentname());
			planLine.setContractorTime(DateUtil.DateToString(res
					.getFinishtime(), "yyyy年MM月"));
			planCables.put(lineId, planLine);
		}
		return planCables;
	}

	/**
	 * 根据年计划id查询制定的基站
	 * 
	 * @param planId
	 * @return
	 */
	public Map<String, TestPlanStation> getPlanStationByPlanId(String planId) {
		Map<String, TestPlanStation> planStations = new HashMap<String, TestPlanStation>();
		List<TestPlanStation> list = dao.getPlanStationByPlanId(planId);
		for (TestPlanStation station : list) {
			String stationId = station.getTestStationId();
			// UserInfo user = getUser(station.getTestMan());
			// ConPerson user = getConPerson(station.getTestMan());
			Point point = getPoint(station.getTestStationId());
			// station.setTestManName(user.getName());
			station.setStationName(point.getPointName());
			planStations.put(stationId, station);
		}
		return planStations;
	}

	/**
	 * 查询上次测试中继断的个数
	 * 
	 * @param troubleid
	 * @return
	 */
	public Integer getTestCableNum(String conid) {
		return dao.getTestCableNum(conid);
	}

	/**
	 * 该代维单位负责的中继段的总数量
	 * 
	 * @return
	 */
	public Integer getCableNumber(UserInfo userinfo, String begindate) {
		return dao.getCableNumber(userinfo, begindate);
	}

	/**
	 * 查询光缆信息
	 * 
	 * @return
	 */
	public List getCable(UserInfo user, String level, String cableName,
			String begindate) {
		return dao.getCable(user, level, cableName, begindate);
	}

	/**
	 * 查询光缆年计划测试次数
	 * 
	 * @param trunkid
	 * @return
	 */
	public int getTestNumYearPlan(UserInfo user, String trunkid,
			String testBeginDate) {
		return dao.getTestNumYearPlan(user, trunkid, testBeginDate);
	}

	/**
	 * 今年已经做多少次
	 * 
	 * @param trunkid
	 * @param time
	 * @return
	 */
	public int getFinishedNum(UserInfo user, String trunkid, String time) {
		return dao.getFinishedNum(user, trunkid, time);
	}

	/**
	 * 查询上次测试基站的个数
	 * 
	 * @param troubleid
	 * @return
	 */
	public Integer getTestStationNum(String conid) {
		return dao.getTestStationNum(conid);
	}

	/**
	 * 查询基站信息
	 * 
	 * @return
	 */
	public List getStation(UserInfo user) {
		return dao.getStation(user);
	}

	/**
	 * 得到计划测试人员
	 * 
	 * @param user
	 * @return
	 */
	public List getUsers(UserInfo user, String UserName) {
		return dao.getUsers(user, UserName);
	}

	/**
	 * 根据用户id查询用户
	 * 
	 * @param userid
	 * @return
	 */
	public UserInfo getUser(String userid) {
		try {
			com.cabletech.baseinfo.dao.UserInfoDAOImpl userDAO = new com.cabletech.baseinfo.dao.UserInfoDAOImpl();
			return userDAO.findById(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询代维用户
	 * 
	 * @param id
	 *            代维id
	 * @return
	 */
	public ConPerson getConPerson(String id) {
		ConPerson cp = conPersonDAOImpl.get(id);
		return cp;
	}

	/**
	 * 根据代维id查询代维公司
	 * 
	 * @param userid
	 * @return
	 */
	public Contractor getContratroById(String conid) {
		try {
			ContractorDAOImpl cDAO = new ContractorDAOImpl();
			return cDAO.findById(conid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(readOnly = true)
	public RepeaterSection getRepeaterSection(String id) {
		return resDao.get(id);
	}

	/**
	 * 得到基站信息
	 * 
	 * @param id
	 * @return
	 */
	public Point getPoint(String id) {
		return dao.getPoint(id);
	}

	/**
	 * 根据计划得到录入数据
	 * 
	 * @param planid
	 * @return
	 */
	public TestData getDataByPlanId(String planid) {
		return dataDAO.getDataByPlanId(planid);
	}

	/**
	 * 解析字符串为list
	 * 
	 * @param str
	 * @return
	 */
	/*
	 * public List<String> stringToList(String str){ String[] strArray =
	 * str.split(","); List<String> list = new ArrayList<String>(); for(int
	 * i=0;strArray!=null && i<strArray.length;i++){ list.add(strArray[i]); }
	 * return list; }
	 */

	/**
	 * 保存录入数据信息
	 * 
	 * @param data
	 */
	public void saveTestData(TestData data) {
		dataDAO.save(data);
	}

	/**
	 * 根据测试计划查询数据录入信息
	 * 
	 * @param planid
	 * @return
	 */
	public TestData getTestDataByPlanID(String planid) {
		return dataDAO.getDataByPlanId(planid);
	}

	/**
	 * 查询一个计划详细信息
	 * 
	 * @return
	 */
	public BasicDynaBean getPlanInfo(String planid) {
		return dao.getPlanInfo(planid);
	}

	/**
	 * 查询代维
	 * 
	 * @param user
	 * @return
	 */
	public List<Contractor> getContractors(UserInfo user) {
		return dao.getContractors(user);
	}

	/**
	 * 根据条件查询年计划
	 * 
	 * @return
	 */
	public List getTestPlans(TestPlanQueryStatBean bean, UserInfo user,
			String act) {
		return dao.getTestPlans(bean, user, act);
	}

	/**
	 * 获取问题光缆信息
	 * 
	 * @param id
	 * @return
	 */
	public TestProblem getTestProblem(String id) {
		return problemDAO.get(id);
	}

	/**
	 * 保存问题光缆
	 * 
	 * @param problem
	 */
	public void saveTestProblem(TestProblem problem) {
		problemDAO.save(problem);
	}

	/**
	 * 查询未解决的光缆信息
	 * 
	 * @param u
	 * @return
	 */
	public List getProblems(UserInfo u, String state) {
		return problemDAO.getProblems(u, state);
	}

	/**
	 * 查询光缆问题的数量
	 * 
	 * @param planid
	 *            测试计划id
	 * @return
	 */
	public Integer getProblemNumByPlanId(String planid) {
		return problemDAO.getProblemNumByPlanId(planid);
	}

	/**
	 * 查询已经解决的光缆问题的数量
	 * 
	 * @param planid
	 * @return
	 */
	public Integer getSolveProblemNum(String planid) {
		return problemDAO.getSolveProblemNum(planid);
	}

	/**
	 * 查询已办
	 * 
	 * @param user
	 * @return
	 */
	public List getHandeledWorks(UserInfo user, String taskName,
			String taskOutCome) {
		String condition = "";
		if (taskName != null && !taskName.equals("")) {
			condition += " and handled_task_name='" + taskName + "' ";
		}
		return dao.getHandeledWorks(user, condition, "");
	}

	@Override
	protected HibernateDao<TestPlan, String> getEntityDao() {
		return null;
	}

	public List queryForHandleTestPlanNum(String condition, UserInfo userInfo) {
		// TODO Auto-generated method stub
		String assignee = userInfo.getUserID();
		List list = new ArrayList();
		// List handleTaskList =
		// workflowBo.queryForHandleListBean(assignee,condition, "");getWaitWork
		List handleTaskList = getWaitWork(userInfo, "");
		DynaBean bean;
		int waitCreatePlanTaskNum = 0;
		int waitApprovePlanTaskNum = 0;
		int waitRecordTaskNum = 0;
		int waitApproveRecordTaskNum = 0;
		int waitEvaluateTaskNum = 0;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			bean = (DynaBean) handleTaskList.get(i);
			if (bean != null) {
				if (bean.get("flow_state") != null) {
					if (MaintenanceWorkflowBO.CREATE_PLAN_TASK.equals(bean
							.get("flow_state"))) {
						waitCreatePlanTaskNum++;
					}
					if (MaintenanceWorkflowBO.APPROVE_PLAN_TASK.equals(bean
							.get("flow_state"))) {
						waitApprovePlanTaskNum++;
					}
					if (MaintenanceWorkflowBO.RECORD_TASK.equals(bean
							.get("flow_state"))) {
						waitRecordTaskNum++;
					}
					if (MaintenanceWorkflowBO.APPROVE_DATA_TASK.equals(bean
							.get("flow_state"))) {
						waitApproveRecordTaskNum++;
					}
					if (MaintenanceWorkflowBO.EVALUATE_TASK.equals(bean
							.get("flow_state"))) {
						waitEvaluateTaskNum++;
					}
				}
			}
		}
		list.add(waitCreatePlanTaskNum);
		list.add(waitApprovePlanTaskNum);
		list.add(waitRecordTaskNum);
		list.add(waitApproveRecordTaskNum);
		list.add(waitEvaluateTaskNum);
		return list;
	}

	/**
	 * 删除测试计划
	 * 
	 * @param testPlanid
	 */
	public void deleteTestPlan(String testPlanid) {
		TestPlan plan = dao.getTestPlanById(testPlanid);
		dao.delete(testPlanid);// 删除计划
		dataDAO.deleteDataByPlanId(testPlanid);// 录入数据信息表
		String plantype = plan.getTestPlanType();
		if (plantype.equals(MainTenanceConstant.LINE_TEST)) {// 备纤测试
			// List<TestPlanLine> planlines =
			// planLineDAO.getTestPlanLineByPlanId(testPlanid);
			planLineDAO.deleteLine(testPlanid);
			List<TestCableData> cabledatas = lineDataDAO
					.getCableDataByPlanId(testPlanid);
			for (int i = 0; cabledatas != null && cabledatas.size() > 0; i++) {
				TestCableData cabledata = cabledatas.get(i);
				String cableDataId = cabledata.getId();
				String lineid = cabledata.getTestCablelineId();
				List<TestChipData> chipdata = chipDAO
						.getCableChipsByCableDataId(cableDataId);
				for (int m = 0; chipdata != null && m < chipdata.size(); m++) {
					TestChipData data = chipdata.get(m);
					String chipDataId = data.getId();
					TestCoreData coreData = coredataDAO.getCoreData(chipDataId);// 数据分析
					if (coreData != null) {
						String anaylseId = coreData.getId();
						coredataDAO.delete(coreData);
						coreLengthDAO.deletCoreLengthByAnaylseId(anaylseId);
						decayconstantDAO
								.deletDecayconstantByAnaylseId(anaylseId);
						endWasteDAO.deletEndWasteByAnaylseId(anaylseId);
						otherAnalyseDAO.deletOtherByAnaylseId(anaylseId);
						connectorWasteDAO.deleteWasteByAnaylseId(anaylseId);
						exceptionEventDAO.deletEventByAnaylseId(anaylseId);
					}
				}
				chipDAO.deleteChipByCableDataId(cableDataId);// 删除纤序信息
				problemDAO.deleteProblemsByPlanIdAndLineId(testPlanid, lineid);// 删除问题中继段
			}
			lineDataDAO.deleteLineDataByPlanId(testPlanid);// 备纤录入数据
		}
		if (plantype.equals(MainTenanceConstant.STATION_TEST)) {// 接地电阻测试
			// List<TestPlanStation> planStations =
			// planStationDAO.getTestPlanStationByPlanId(testPlanid);
			planStationDAO.deleteStation(testPlanid);// 计划中的电阻信息
			stationDataDAO.deleteStationDateByPlanId(testPlanid);// 接地电阻录入数据
		}
	}

	/**
	 * 更新抄送人的已经批阅标记
	 * 
	 * @throws Exception
	 */
	public void updateReader(UserInfo user, String planid, String dataid)
			throws Exception {
		String approverId = user.getUserID();
		String objectType = MainTenanceConstant.LP_TEST_PLAN;
		approverDAO.updateReader(approverId, planid, objectType);
		if (dataid != null && !"".equals(dataid)) {
			TestData data = getDataByPlanId(planid);
			String type = MainTenanceConstant.TEST_DATA_APPROVE_WAIT;
			if (data != null && data.getState().equals(type)) {
				objectType = MainTenanceConstant.LP_TEST_DATA;
				approverDAO.updateReader(approverId, dataid, objectType);
			}
		}
		replyRead(user, planid, dataid);
	}

	private void replyRead(UserInfo user, String planid, String dataid)
			throws Exception {
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setNextOperateUserId("");
		processHistoryBean.setTaskOutCome("read");
		if (dataid != null && !"".equals(dataid)) {
			Task task = workflowBo.getHandleTaskForId(user.getUserID(), dataid);
			if (task != null
					&& MaintenanceWorkflowBO.APPROVE_DATA_TASK.equals(task
							.getName())) {
				processHistoryBean.initial(task, user, "",
						ModuleCatalog.MAINTENANCE);
				processHistoryBean.setObjectId(dataid);
				processHistoryBean.setProcessAction("录入数据批阅");
			}
		} else {
			Task task = workflowBo.getHandleTaskForId(user.getUserID(), planid);
			if (task != null
					&& MaintenanceWorkflowBO.APPROVE_PLAN_TASK.equals(task
							.getName())) {
				processHistoryBean.initial(task, user, "",
						ModuleCatalog.MAINTENANCE);
				processHistoryBean.setObjectId(planid);
				processHistoryBean.setProcessAction("测试计划批阅");
			}
		}
		processHistoryBO.saveProcessHistory(processHistoryBean);
	}

	/**
	 * 查询已经录入的光缆信息
	 * 
	 * @param user
	 * @return
	 */
	public List getCables(UserInfo user, TestPlanQueryStatBean bean) {
		return dao.getCables(user, bean);
	}

	public TestCoreData getCoreData(String chipDataId) {
		return coredataDAO.getCoreData(chipDataId);
	}

	/**
	 * 执行查询到的测试光缆再次处理（获取光缆的测试次数）
	 * 
	 * @param user
	 * @param testBeginDate
	 * @param planCables
	 */
	public void processPlanCables(UserInfo user, Date testBeginDate,
			Map<String, TestPlanLine> planCables) {
		// TODO Auto-generated method stub
		String dateStr = DateUtil.DateToString(testBeginDate, "yyyy/MM/dd");
		if (planCables != null) {
			Set keySet = planCables.keySet();
			if (keySet != null) {
				Iterator it = keySet.iterator();
				String cableId = "";
				TestPlanLine line = null;
				int testNum = 0;
				int finishTestNum = 0;
				while (it != null && it.hasNext()) {
					testNum = 0;
					finishTestNum = 0;
					cableId = (String) it.next();
					line = planCables.get(cableId);
					testNum = this.getTestNumYearPlan(user, cableId, dateStr);
					if (testNum > 0) {
						finishTestNum = this.getFinishedNum(user, cableId,
								dateStr);
					}
					line.setCablePlanTestNum(testNum);
					line.setCablePlanedTestNum(finishTestNum);
					planCables.put(cableId, line);
				}
			}
		}
	}

	/**
	 * 执行取消技术维护流程
	 * 
	 * @param bean
	 * @param userInfo
	 */
	public void cancelTestPlan(TestPlanBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		TestPlan plan = dao.getTestPlanById(bean.getId());
		plan.setCancelReason(bean.getCancelReason());
		plan.setCancelTime(new Date());
		plan.setCancelUserId(userInfo.getUserID());
		plan.setTestState(TestPlan.CANCELED_STATE);
		dao.save(plan);
		String processInstanceId = "";
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processInstanceId = MaintenanceWorkflowBO.MAINTENANCE_WORKFLOW + "."
				+ plan.getId();
		processHistoryBean.initial(null, userInfo, processInstanceId,
				ModuleCatalog.MAINTENANCE);
		processHistoryBean.setHandledTaskName("any");
		processHistoryBean.setNextOperateUserId("");
		processHistoryBean.setObjectId(plan.getId());
		processHistoryBean.setProcessAction("技术维护流程取消");
		processHistoryBean.setTaskOutCome("cancel");
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
			workflowBo.endProcessInstance(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
