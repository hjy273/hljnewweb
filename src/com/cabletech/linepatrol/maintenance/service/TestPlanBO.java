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
	 * ��ѯ��ƻ�
	 * 
	 * @param id
	 *            ��ƻ�ϵͳid
	 * @return
	 */
	public TestPlan getTestPlanById(String id) {
		TestPlan plan = dao.getTestPlanById(id);
		String cancelUserName = userInfoDao.getUserName(plan.getCancelUserId());
		plan.setCancelUserName(cancelUserName);
		return plan;
	}

	/**
	 * ������Լƻ�
	 * 
	 * @param bean
	 * @param userinfo
	 * @param planLines
	 *            �м̶���Ϣ
	 * @param planStations
	 *            ��վ��Ϣ
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
		if (planId == null || "".equals(planId)) {// ������Լƻ�����������
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
	 * ���Ͷ��� ���ƻ������
	 */
	public void sendMsg(TestPlan plan, String mobile, String rmobiles) {
		String planid = plan.getId();
		String content = "������ά��������һ������Ϊ\"" + plan.getTestPlanName()
				+ "\"�Ĳ��Լƻ��ȴ����ļ�ʱ��ˡ�";
		// ���Լƻ�����ˡ�
		logger.info("��������: " + mobile + ":" + content);
		super.sendMessage(content, mobile);
		// smSendProxy.simpleSend(mobile, content, null, null, true);
		// ������
		if (rmobiles != null && !"".equals(rmobiles)) {
			String msg = "������ά��������һ������Ϊ\"" + plan.getTestPlanName()
					+ "\"�Ĳ��Լƻ��ȴ������ա�";
			List<String> mobileList = StringUtils.string2List(rmobiles, ",");
			logger.info("��������: " + mobile + ":" + rmobiles);
			super.sendMessage(msg, mobileList);
			// for (String rmobile : mobileList) {
			// smSendProxy.simpleSend(rmobile, msg, null, null, true);
			// }
		}

		// ������ż�¼
		SMHistory history = new SMHistory();
		history.setSimIds(mobile + "," + rmobiles);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(MainTenanceConstant.LP_TEST_PLAN);
		history.setObjectId(planid);
		history.setBusinessModule(MainTenanceConstant.MAINTENANCE_MODULE);
		historyDAO.save(history);
	}

	// ִ�б�����Լƻ��Ĺ������������
	public void planWorkFlow(TestPlan plan, UserInfo user, String approvers) {
		String planid = plan.getId();
		String userid = user.getUserID();
		Task task = workflowBo.getHandleTaskForId(userid, planid);
		if (task != null
				&& MaintenanceWorkflowBO.CREATE_PLAN_TASK
						.equals(task.getName())) {
			workflowBo.setVariables(task, "assignee", approvers);
			workflowBo.completeTask(task.getId(), "apply_approve");
			System.out.println("���Լƻ�����ɹ���");
		}
	}

	/**
	 * ����������ʷ
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
			processHistoryBean.setProcessAction("�ƶ����Լƻ�");
			processHistoryBean.setTaskOutCome("apply_approve");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		} catch (Exception e) {
			logger.error("�ƶ����Լƻ����Ӵ�������ʧ��:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * ��ѯ���칤��
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
				if (planstate.equals(MainTenanceConstant.PLAN_WAIT_APPROVE)) {// �ж��û��ǲ��ǳ�����
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
	 * ������ƻ�id��ѯ�ƶ����м̶�
	 * 
	 * @param planId
	 * @return
	 */
	public List getPlanLinesByPlanId(String planId) {
		return dao.getPlanLinesByPlanId(planId);
	}

	/**
	 * ��ѯ���������Ƿ�¼����
	 * 
	 * @param planId
	 * @return
	 */
	public boolean judgeAllEnteringLine(String planId) {
		return dao.judgeAllEnteringLine(planId);
	}

	/**
	 * ������ƻ�id��ѯ�ƶ��Ļ�վ
	 * 
	 * @param planId
	 * @return
	 */
	public List getPlanStationsByPlanId(String planId) {
		return dao.getPlanStationsByPlanId(planId);
	}

	/**
	 * ��ѯ��վ�����Ƿ�¼����
	 * 
	 * @param planId
	 * @return
	 */
	public boolean judgeAllEnteringStation(String planId) {
		return dao.judgeAllEnteringStation(planId);
	}

	/**
	 * ������ƻ�id��ѯ�ƶ����м̶�
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
					.getFinishtime(), "yyyy��MM��"));
			planCables.put(lineId, planLine);
		}
		return planCables;
	}

	/**
	 * ������ƻ�id��ѯ�ƶ��Ļ�վ
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
	 * ��ѯ�ϴβ����м̶ϵĸ���
	 * 
	 * @param troubleid
	 * @return
	 */
	public Integer getTestCableNum(String conid) {
		return dao.getTestCableNum(conid);
	}

	/**
	 * �ô�ά��λ������м̶ε�������
	 * 
	 * @return
	 */
	public Integer getCableNumber(UserInfo userinfo, String begindate) {
		return dao.getCableNumber(userinfo, begindate);
	}

	/**
	 * ��ѯ������Ϣ
	 * 
	 * @return
	 */
	public List getCable(UserInfo user, String level, String cableName,
			String begindate) {
		return dao.getCable(user, level, cableName, begindate);
	}

	/**
	 * ��ѯ������ƻ����Դ���
	 * 
	 * @param trunkid
	 * @return
	 */
	public int getTestNumYearPlan(UserInfo user, String trunkid,
			String testBeginDate) {
		return dao.getTestNumYearPlan(user, trunkid, testBeginDate);
	}

	/**
	 * �����Ѿ������ٴ�
	 * 
	 * @param trunkid
	 * @param time
	 * @return
	 */
	public int getFinishedNum(UserInfo user, String trunkid, String time) {
		return dao.getFinishedNum(user, trunkid, time);
	}

	/**
	 * ��ѯ�ϴβ��Ի�վ�ĸ���
	 * 
	 * @param troubleid
	 * @return
	 */
	public Integer getTestStationNum(String conid) {
		return dao.getTestStationNum(conid);
	}

	/**
	 * ��ѯ��վ��Ϣ
	 * 
	 * @return
	 */
	public List getStation(UserInfo user) {
		return dao.getStation(user);
	}

	/**
	 * �õ��ƻ�������Ա
	 * 
	 * @param user
	 * @return
	 */
	public List getUsers(UserInfo user, String UserName) {
		return dao.getUsers(user, UserName);
	}

	/**
	 * �����û�id��ѯ�û�
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
	 * ��ѯ��ά�û�
	 * 
	 * @param id
	 *            ��άid
	 * @return
	 */
	public ConPerson getConPerson(String id) {
		ConPerson cp = conPersonDAOImpl.get(id);
		return cp;
	}

	/**
	 * ���ݴ�άid��ѯ��ά��˾
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
	 * �õ���վ��Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public Point getPoint(String id) {
		return dao.getPoint(id);
	}

	/**
	 * ���ݼƻ��õ�¼������
	 * 
	 * @param planid
	 * @return
	 */
	public TestData getDataByPlanId(String planid) {
		return dataDAO.getDataByPlanId(planid);
	}

	/**
	 * �����ַ���Ϊlist
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
	 * ����¼��������Ϣ
	 * 
	 * @param data
	 */
	public void saveTestData(TestData data) {
		dataDAO.save(data);
	}

	/**
	 * ���ݲ��Լƻ���ѯ����¼����Ϣ
	 * 
	 * @param planid
	 * @return
	 */
	public TestData getTestDataByPlanID(String planid) {
		return dataDAO.getDataByPlanId(planid);
	}

	/**
	 * ��ѯһ���ƻ���ϸ��Ϣ
	 * 
	 * @return
	 */
	public BasicDynaBean getPlanInfo(String planid) {
		return dao.getPlanInfo(planid);
	}

	/**
	 * ��ѯ��ά
	 * 
	 * @param user
	 * @return
	 */
	public List<Contractor> getContractors(UserInfo user) {
		return dao.getContractors(user);
	}

	/**
	 * ����������ѯ��ƻ�
	 * 
	 * @return
	 */
	public List getTestPlans(TestPlanQueryStatBean bean, UserInfo user,
			String act) {
		return dao.getTestPlans(bean, user, act);
	}

	/**
	 * ��ȡ���������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public TestProblem getTestProblem(String id) {
		return problemDAO.get(id);
	}

	/**
	 * �����������
	 * 
	 * @param problem
	 */
	public void saveTestProblem(TestProblem problem) {
		problemDAO.save(problem);
	}

	/**
	 * ��ѯδ����Ĺ�����Ϣ
	 * 
	 * @param u
	 * @return
	 */
	public List getProblems(UserInfo u, String state) {
		return problemDAO.getProblems(u, state);
	}

	/**
	 * ��ѯ�������������
	 * 
	 * @param planid
	 *            ���Լƻ�id
	 * @return
	 */
	public Integer getProblemNumByPlanId(String planid) {
		return problemDAO.getProblemNumByPlanId(planid);
	}

	/**
	 * ��ѯ�Ѿ�����Ĺ������������
	 * 
	 * @param planid
	 * @return
	 */
	public Integer getSolveProblemNum(String planid) {
		return problemDAO.getSolveProblemNum(planid);
	}

	/**
	 * ��ѯ�Ѱ�
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
	 * ɾ�����Լƻ�
	 * 
	 * @param testPlanid
	 */
	public void deleteTestPlan(String testPlanid) {
		TestPlan plan = dao.getTestPlanById(testPlanid);
		dao.delete(testPlanid);// ɾ���ƻ�
		dataDAO.deleteDataByPlanId(testPlanid);// ¼��������Ϣ��
		String plantype = plan.getTestPlanType();
		if (plantype.equals(MainTenanceConstant.LINE_TEST)) {// ���˲���
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
					TestCoreData coreData = coredataDAO.getCoreData(chipDataId);// ���ݷ���
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
				chipDAO.deleteChipByCableDataId(cableDataId);// ɾ��������Ϣ
				problemDAO.deleteProblemsByPlanIdAndLineId(testPlanid, lineid);// ɾ�������м̶�
			}
			lineDataDAO.deleteLineDataByPlanId(testPlanid);// ����¼������
		}
		if (plantype.equals(MainTenanceConstant.STATION_TEST)) {// �ӵص������
			// List<TestPlanStation> planStations =
			// planStationDAO.getTestPlanStationByPlanId(testPlanid);
			planStationDAO.deleteStation(testPlanid);// �ƻ��еĵ�����Ϣ
			stationDataDAO.deleteStationDateByPlanId(testPlanid);// �ӵص���¼������
		}
	}

	/**
	 * ���³����˵��Ѿ����ı��
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
				processHistoryBean.setProcessAction("¼����������");
			}
		} else {
			Task task = workflowBo.getHandleTaskForId(user.getUserID(), planid);
			if (task != null
					&& MaintenanceWorkflowBO.APPROVE_PLAN_TASK.equals(task
							.getName())) {
				processHistoryBean.initial(task, user, "",
						ModuleCatalog.MAINTENANCE);
				processHistoryBean.setObjectId(planid);
				processHistoryBean.setProcessAction("���Լƻ�����");
			}
		}
		processHistoryBO.saveProcessHistory(processHistoryBean);
	}

	/**
	 * ��ѯ�Ѿ�¼��Ĺ�����Ϣ
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
	 * ִ�в�ѯ���Ĳ��Թ����ٴδ�����ȡ���µĲ��Դ�����
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
	 * ִ��ȡ������ά������
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
		processHistoryBean.setProcessAction("����ά������ȡ��");
		processHistoryBean.setTaskOutCome("cancel");
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
			workflowBo.endProcessInstance(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
