package com.cabletech.linepatrol.maintenance.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.maintenance.beans.TestPlanApproveBean;
import com.cabletech.linepatrol.maintenance.dao.TestDataDAO;
import com.cabletech.linepatrol.maintenance.dao.TestPlanDAO;
import com.cabletech.linepatrol.maintenance.module.TestData;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.workflow.MaintenanceWorkflowBO;
import com.cabletech.linepatrol.trouble.services.TroubleConstant;
@Service
@Transactional
public class TestPlanApproveBO extends EntityManager<TestPlan,String> {


	@Resource(name="testPlanDAO")
	private TestPlanDAO planDAO;
	@Resource(name="testDataDAO")
	private TestDataDAO dataDAO;
	@Resource(name="approveDAO")
	private ApproveDAO approveDAO;
	@Resource(name="replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Autowired
	private MaintenanceWorkflowBO workflowBo;



	/**
	 * ������Լƻ������Ϣ
	 * @param user
	 * @param bean
	 * @param troubleId
	 * @param attachids
	 * @throws Exception
	 */
	public void addApproveTestPlan(UserInfo user,TestPlanApproveBean bean,
			String planid) throws ServiceException{
		ApproveInfo approve = new ApproveInfo();
		try {
			BeanUtil.objectCopy(bean, approve);

			TestPlan plan = planDAO.getTestPlanById(planid);
			String approveResult = approve.getApproveResult();
			approveDAO.saveApproveInfo(approve);
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_NO)){//��ͨ��
				plan.setTestState(MainTenanceConstant.PLAN_APPROVE_NO);
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_PASS)){//ͨ��
				plan.setTestState(MainTenanceConstant.LOGGING_DATA_WAIT);
			}
			String transfer = bean.getTransfer();
			String transfers = transfer;
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_TRANSFER)){//ת��
				Set<String> all = approverDAO.getApprover(planid,MainTenanceConstant.APPROVE_READ,MainTenanceConstant.LP_TEST_PLAN);
				all.add(transfer);
				transfers = StringUtils.join(all.iterator(), ",");
				//approverDAO.updateApprover(planid, MainTenanceConstant.LP_TEST_PLAN);
				//saveApprover(planid,transfer,MainTenanceConstant.LP_TEST_PLAN);
				approverDAO.saveApproverOrReader(transfer,planid,MainTenanceConstant.APPROVE_TRANSFER_MAN,MainTenanceConstant.LP_TEST_PLAN);
			}else{
				int approveNum = plan.getApproveTimes()+1;
				plan.setApproveTimes(approveNum);
			}
			planDAO.save(plan);
			String mobiles = bean.getMobiles();
			sendMsg(approve,plan,approveResult,mobiles);
			approvePlanWorkFlow(user,plan,approve,transfers,transfer);
		}catch(Exception e) {
			throw new ServiceException("��˲��Լƻ������쳣:"+e.getMessage());
		}
	}


	/**
	 * ��˲��Լƻ��������Ͷ���
	 */
	public void sendMsg(ApproveInfo approve,TestPlan plan,String approveResult,String mobiles){
		//���Ͷ���
		try{
			String mobile ="";
			String content="������ά��������һ������Ϊ";
			content+="\""+plan.getTestPlanName()+" \"�Ĳ��Լƻ�";
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_NO)){//��ͨ��
				content+="���δͨ����";
			}
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_PASS)){//ͨ��
				content+="�Ѿ�ͨ����ˣ�";
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_TRANSFER)){//ת��
				content+="�ȴ����ļ�ʱ����";
				mobile=mobiles;
			}else{
				UserInfoDAOImpl userdao = new UserInfoDAOImpl();
				UserInfo userinfo = userdao.findById(plan.getCreatorId());
				mobile = userinfo.getPhone();
			}

			//����֪ͨ  ���� �ƻ���д��Ա
			super.sendMessage(content, mobile);
			//			smSendProxy.simpleSend(mobile,content, null, null, true);
			logger.info("��������: "+mobile +":"+content);
			String id = approve.getId();
			saveSMHistory(mobile,content,id);
		}catch(Exception e){
			logger.error("��˲��Լƻ����Ͷ���ʧ��:"+e.getMessage());
			e.getStackTrace();
		}
	}

	public void saveSMHistory(String mobiles,String content,String id){
		SMHistory history = new SMHistory();
		history.setSimIds(mobiles);
		history.setSendContent(content);
		history.setSendTime(new Date());
		history.setSmType(MainTenanceConstant.LP_APPROVE_INFO);
		history.setObjectId(id);
		history.setBusinessModule(MainTenanceConstant.MAINTENANCE_MODULE);
		historyDAO.save(history);
	}

	/**
	 * ��˼ƻ���������
	 * @param user
	 * @param plan
	 * @throws Exception 
	 */
	private void approvePlanWorkFlow(UserInfo user,TestPlan plan,
			ApproveInfo approve,String transfers,String transfer) throws Exception{
		String planid = plan.getId();
		String creator = plan.getCreatorId();
		String approveResult = approve.getApproveResult();
		Task task = workflowBo.getHandleTaskForId(user.getUserID(), planid);
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		if (task != null
				&& MaintenanceWorkflowBO.APPROVE_PLAN_TASK.equals(task.getName())) {
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_NO)){//��ͨ��
				workflowBo.setVariables(task, "assignee",creator);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("���Լƻ�û��ͨ����ˣ�����޸ļƻ���");
				processHistoryBean.setProcessAction("���Լƻ���˲�ͨ��");
				processHistoryBean.setTaskOutCome("not_passed");
				processHistoryBean.setNextOperateUserId(creator);
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_PASS)){//ͨ��
				workflowBo.setVariables(task, "assignee",creator);
				workflowBo.setVariables(task, "transition", "record");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("�����Ѿ�ͨ����ˣ��ȴ�¼�����ݣ�");
				processHistoryBean.setProcessAction("���Լƻ����ͨ��");
				processHistoryBean.setTaskOutCome("record");
				processHistoryBean.setNextOperateUserId(creator);
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_TRANSFER)){//ת��
				workflowBo.setVariables(task, "assignee",transfers);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("���Լƻ��Ѿ�����ת��");
				processHistoryBean.setProcessAction("���Լƻ�ת��");
				processHistoryBean.setTaskOutCome("transfer");
				processHistoryBean.setNextOperateUserId(transfer);
			}
			processHistoryBean.initial(task, user, "",
					ModuleCatalog.MAINTENANCE);
			processHistoryBean.setObjectId(planid);
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	/**
	 * ����¼�����������Ϣ
	 * @param user
	 * @param bean
	 * @param troubleId
	 * @param attachids
	 * @throws Exception
	 */
	public void addApproveTestData(UserInfo user,TestPlanApproveBean bean) throws ServiceException{
		ApproveInfo approve = new ApproveInfo();
		try {
			BeanUtil.objectCopy(bean, approve);
			String dataid = bean.getObjectId();
			TestData data = dataDAO.get(dataid);
			String planid = data.getTestPlanId();
			TestPlan plan = planDAO.getTestPlanById(planid);
			String approveResult = approve.getApproveResult();
			approveDAO.saveApproveInfo(approve);
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_NO)){//��ͨ��
				plan.setTestState(MainTenanceConstant.LOGGING_APPROVE_NO_PASS);
				data.setState(MainTenanceConstant.TEST_DATA_APPROVE_NO_PASS);
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_PASS)){//ͨ��
				plan.setTestState(MainTenanceConstant.WAIT_EXAM);
				data.setState(MainTenanceConstant.TEST_DATA_APPROVE_PASS);
			}	
			String transfer = bean.getTransfer();
			String mans = transfer;
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_TRANSFER)){//ת��
				approverDAO.saveApproverOrReader(transfer,dataid,
						MainTenanceConstant.APPROVE_TRANSFER_MAN,MainTenanceConstant.LP_TEST_DATA);
				Set<String> all = approverDAO.getApprover(dataid,MainTenanceConstant.APPROVE_READ,
						MainTenanceConstant.LP_TEST_DATA);
				all.add(transfer);
				mans= StringUtils.join(all.iterator(), ",");
			}else{
				int approveNum = data.getApproveTimes()+1;
				data.setApproveTimes(approveNum);
			}
			dataDAO.save(data);
			planDAO.save(plan);
			String mobiles = bean.getMobiles();
			sendTestDataMsg(approve,plan,data,approveResult,mobiles);
			approveDataWorkFlow(user,plan,approve,mans,transfer);
		}catch(Exception e) {
			throw new ServiceException("��˲�������¼������쳣:"+e.getMessage());
		}
	}


	/**
	 * ���¼�����ݽ������Ͷ���
	 */
	public void sendTestDataMsg(ApproveInfo approve,TestPlan plan,TestData data,
			String approveResult,String mobiles){
		//���Ͷ���
		try{
			String mobile="";
			String content="������ά��������һ������Ϊ";
			content+="\""+plan.getTestPlanName()+" \" �ļƻ����Խ��";
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_NO)){//��ͨ��
				content+="δͨ����ˣ�";
			}
			if(approveResult.equals(TroubleConstant.APPROVE_RESULT_PASS)){//ͨ��
				content+="�Ѿ�ͨ����ˣ�";
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_TRANSFER)){//ת��
				content+="�ȴ����ļ�ʱ����";
				mobile=mobiles;
			}else{
				UserInfoDAOImpl userdao = new UserInfoDAOImpl();
				UserInfo userinfo = userdao.findById(data.getRecordManId());
				mobile = userinfo.getPhone();
			}
			//����֪ͨ  ¼�����ݼƻ���д��Ա
			super.sendMessage(content, mobile);
			//			smSendProxy.simpleSend(mobile,content, null, null, true);
			logger.info("��������: "+mobile +":"+content);
			String id = approve.getId();
			saveSMHistory(mobile,content,id);
		}catch(Exception e){
			logger.error("���¼�����ݷ��Ͷ���ʧ��:"+e.getMessage());
			e.getStackTrace();
		}
	}


	/**
	 * �������¼�봦������
	 * @param user
	 * @param plan
	 * @throws Exception 
	 */
	private void approveDataWorkFlow(UserInfo user,TestPlan plan,
			ApproveInfo approve,String mans,String transfer) throws Exception{
		String planid = plan.getId();
		String creator = plan.getCreatorId();
		String userid = user.getUserID();
		String approveResult = approve.getApproveResult();
		Task task = workflowBo.getHandleTaskForId(userid, planid);
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		if (task != null
				&& MaintenanceWorkflowBO.APPROVE_DATA_TASK.equals(task.getName())) {
			System.out.println("¼�����ݴ���ˣ�" + task.getName());
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_NO)){//��ͨ��
				workflowBo.setVariables(task, "assignee",creator);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("¼������û��ͨ����ˣ�����޸�¼�����ݣ�");
				processHistoryBean.setProcessAction("¼��������˲�ͨ��");
				processHistoryBean.setTaskOutCome("not_passed");
				processHistoryBean.setNextOperateUserId(creator);
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_PASS)){//ͨ��
				workflowBo.setVariables(task, "assignee",userid);//�������������ͬһ����
				workflowBo.setVariables(task, "transition", "evaluate");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("¼�������Ѿ�ͨ����ˣ��ȴ��������֣�");
				processHistoryBean.setProcessAction("¼���������ͨ��");
				processHistoryBean.setTaskOutCome("evaluate");
				processHistoryBean.setNextOperateUserId(userid);
			}
			if(approveResult.equals(MainTenanceConstant.APPROVE_RESULT_TRANSFER)){//ת��
				workflowBo.setVariables(task, "assignee",mans);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("¼�������Ѿ�����ת��");
				processHistoryBean.setProcessAction("¼������ת��");
				processHistoryBean.setTaskOutCome("transfer");
				processHistoryBean.setNextOperateUserId(transfer);
			}
			processHistoryBean.initial(task, user, "",
					ModuleCatalog.MAINTENANCE);
			processHistoryBean.setObjectId(planid);
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	/**
	 * ��ѯ�����ʷ
	 * @param id �ƻ�id������¼������id
	 * @param type �ж��ǲ��Լƻ��Ļ���¼�����ݵ�
	 * @return
	 */
	public List getPlanApproveHistorys(String id,String type){
		return planDAO.getApproveHistorys(id, type);
	}

	@Override
	protected HibernateDao<TestPlan, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}


}
