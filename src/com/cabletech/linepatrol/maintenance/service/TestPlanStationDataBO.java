package com.cabletech.linepatrol.maintenance.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.maintenance.beans.TestStationDataBean;
import com.cabletech.linepatrol.maintenance.dao.TestDataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanStationDAO;
import com.cabletech.linepatrol.maintenance.dao.TestStationDataDAO;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.module.TestPlanStation;
import com.cabletech.linepatrol.maintenance.module.TestStationData;
import com.cabletech.linepatrol.maintenance.workflow.MaintenanceWorkflowBO;

@Service
@Transactional
public class TestPlanStationDataBO extends EntityManager<TestStationData,String> {
	@Resource(name="testStationDataDAO")
	private TestStationDataDAO stationDataDAO;
	@Resource(name="testPlanDAO")
	private TestPlanDAO planDAO;
	@Resource(name="testDataDAO")
	private TestDataDAO dataDAO;
	@Resource(name="testPlanStationDAO")
	private TestPlanStationDAO stationDAO;
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;
	@Autowired
	private MaintenanceWorkflowBO workflowBo;



	public TestStationData getStationDataById(String id){
		return stationDataDAO.getStationDataById(id);
	}

	/**
	 * �������¼������
	 * @param bean
	 * @param dataID
	 */
	public TestStationData saveStationData(TestStationDataBean bean,UserInfo user,String tempstate){
		try{
			String planid = bean.getTestPlanId();
			TestPlan plan = planDAO.getTestPlanById(planid);
			String planstate = plan.getTestState();
			String dataID = "";
			if(planstate.equals(MainTenanceConstant.LOGGING_DATA_WAIT)){
				TestData data = new TestData();
				data.setTestPlanId(planid);
				data.setApproveTimes(0);
				data.setRecordTime(new Date());
				data.setCreateTime(new Date());
				data.setRecordManId(user.getUserID());
				dataDAO.save(data);
				dataID = data.getId();
				plan.setTestState(MainTenanceConstant.LOGGING_DATA_NO_OVER);
			}else{
				TestData data = dataDAO.getDataByPlanId(planid);
				dataID = data.getId();
			}
			String stationid = bean.getStationId();
			TestPlanStation station =  stationDAO.getTestPlanStationById(stationid);
			if(tempstate!=null && tempstate.equals("tempsave")){
				station.setState(MainTenanceConstant.ENTERING_TEMP);
			}else{
				station.setState(MainTenanceConstant.ENTERING_Y);
			}
			
			stationDAO.save(station);
			planDAO.save(plan);
			TestStationData stationData = new TestStationData();
			BeanUtil.objectCopy(bean,stationData);
			stationData.setTestDataId(dataID);
			//	stationData.setTestState(MainTenanceConstant.ENTERING_Y);
			stationData.setCreateTime(new Date());
			stationDataDAO.save(stationData);
			return stationData;
		}catch(Exception e){
			logger.info("����������ݳ����쳣:"+e.getMessage());
			e.getStackTrace();
		}
		return null;
	}

	/**
	 * �޸ĵ���¼������
	 * @param bean
	 * @param dataID
	 */
	public void updateStationData(TestStationDataBean bean,String tempstate,String stationId){
		try{
			TestStationData stationData = new TestStationData();
			BeanUtil.objectCopy(bean,stationData);
			stationData.setCreateTime(new Date());
			stationDataDAO.save(stationData);
			TestPlanStation station = stationDAO.get(stationId);
			if(tempstate!=null && tempstate.equals("tempsave")){
				station.setState(MainTenanceConstant.ENTERING_TEMP);
			}else{
				station.setState(MainTenanceConstant.ENTERING_Y);
			}
			stationDAO.save(station);
		}catch(Exception e){
			e.getStackTrace();
		}
	}

	/**
	 * ��������¼��������Ϣ
	 * @param id ¼������ϵͳ���
	 * @param approvers �����
	 * @param mobiles ����˺���
	 * @param reads ������
	 * @param mobiles �����˺���
	 * @throws Exception 
	 */
	public void saveApprove(UserInfo user,String id,String planid,String approvers,
			String mobiles,String reads,String rmobiles) throws Exception{
	//	TestData data = dataDAO.get(id);
		TestData data = new TestData();
		if(id!=null && !id.equals("")){
			data = dataDAO.getDataById(id);
		}else{
			data.setTestPlanId(planid);
			data.setApproveTimes(0);
			data.setRecordTime(new Date());
			data.setCreateTime(new Date());
			data.setRecordManId(user.getUserID());
			dataDAO.save(data);
			id = data.getId();
		}
		data.setState(MainTenanceConstant.TEST_DATA_APPROVE_WAIT);
		dataDAO.save(data);
		approverDAO.deleteList(id,MainTenanceConstant.LP_TEST_DATA);
		approverDAO.saveApproverOrReader(approvers,id,MainTenanceConstant.APPROVE_MAN,MainTenanceConstant.LP_TEST_DATA);
		approverDAO.saveApproverOrReader(reads,id,MainTenanceConstant.APPROVE_READ,MainTenanceConstant.LP_TEST_DATA);
		TestPlan plan = planDAO.getTestPlanById(planid);
		plan.setTestState(MainTenanceConstant.LOGGING_WAIT_APPROVE);
		planDAO.save(plan);
		String mans = approvers+","+reads;
		sendMsg(plan,id,mobiles,rmobiles);
		dataWorkFlow(plan,user,mans);
	}
	
	/**
	 * ���Ͷ��� ��¼�����������
	 */
	public void sendMsg(TestPlan plan,String dataid,String mobile,String rmobiles){
		String content = "������ά��������һ������Ϊ\""+plan.getTestPlanName()+"\"�Ĳ��Լƻ�����ȴ����ļ�ʱ��ˡ�";
		logger.info("��������: "+mobile +":"+content);
		//���Լƻ�����ˡ�
		super.sendMessage(content, mobile);
//		smSendProxy.simpleSend(mobile,content, null, null, true);
		//�������ֻ�����
		if(rmobiles!=null && rmobiles.length()>0){
			String msg = "������ά��������һ������Ϊ\""+plan.getTestPlanName()+"\"�Ĳ��Լƻ�����ȴ������ա�";
			List<String> mobileList = StringUtils.string2List(rmobiles, ",");
			super.sendMessage(msg, mobileList);
//			for(String rmobile:mobileList){
//				System.out.println("������:"+rmobile);
//				smSendProxy.simpleSend(rmobile,msg, null, null, true);
//			}
		}
		//������ż�¼
		SMHistory history = new SMHistory();
		history.setSimIds(mobile+","+rmobiles);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(MainTenanceConstant.LP_TEST_DATA);
		history.setObjectId(dataid);
		history.setBusinessModule(MainTenanceConstant.MAINTENANCE_MODULE);
		historyDAO.save(history);
	}


	// ִ�б���¼�����ݵĹ������������
	public void dataWorkFlow(TestPlan plan,UserInfo user,String approvers) throws Exception{
		String planid = plan.getId();
		String userid = user.getUserID();
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(userid, planid);
		if (task != null && MaintenanceWorkflowBO.RECORD_TASK.equals(task.getName())) {
			workflowBo.setVariables(task, "assignee", approvers);
			workflowBo.setVariables(task, "transition", "submited");
			workflowBo.completeTask(task.getId(), "record");
			System.out.println("¼�����ݱ���ɹ���");	
			processHistoryBean.setProcessAction("�ύ����¼��");
			processHistoryBean.setTaskOutCome("submited");
			processHistoryBean.initial(task, user, "",
					ModuleCatalog.MAINTENANCE);
			processHistoryBean.setNextOperateUserId(approvers);
			processHistoryBean.setObjectId(planid);
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}


	/**
	 * ���ݼƻ��õ�¼������
	 * @param planid
	 * @return
	 */
	public TestData getDataByPlanId(String planid){
		return dataDAO.getDataByPlanId(planid);
	}

	public TestStationData getStationDataByPlanIdAndStationId(String planid,String stationId){
		return stationDataDAO.getStationDataByPlanIdAndStationId(planid, stationId);
	}



	@Override
	protected HibernateDao<TestStationData, String> getEntityDao() {
		return null;
	}
}
