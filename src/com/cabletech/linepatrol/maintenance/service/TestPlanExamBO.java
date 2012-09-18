package com.cabletech.linepatrol.maintenance.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.dao.UserInfoDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailySpecialBO;
import com.cabletech.linepatrol.commons.dao.EvaluateDao;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.Evaluate;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.maintenance.dao.TestPlanDAO;
import com.cabletech.linepatrol.maintenance.module.TestPlan;
import com.cabletech.linepatrol.maintenance.workflow.MaintenanceWorkflowBO;
@Service
@Transactional
public class TestPlanExamBO extends EntityManager<Evaluate,String> {
	
	@Resource(name="evaluateDao")
	private EvaluateDao dao;

	@Resource(name="testPlanDAO")
	private TestPlanDAO planDAO;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name="smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	@Autowired
	private MaintenanceWorkflowBO workflowBo;

	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;
	
	/**
	 * ��ѯ�����˵ļƻ��б�
	 * @return
	 */
	public List getWaitExams(String regionid){
		return planDAO.getWaitExams(regionid);
	}
	
	public void saveEvaluate(UserInfo user,String planid,AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans){
		/*Evaluate evaluate = new Evaluate();
		evaluate.setEntityScore(score);
		evaluate.setEntityRemark(remark);
		evaluate.setEvaluator(user.getUserID());
		evaluate.setEntityType(MainTenanceConstant.LP_EVALUATE_TEST_PLAN);
		evaluate.setEntityId(planid);
		dao.savaEvaluate(evaluate);*/
		
		//�����ճ�������Ϣ
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//����ר���ճ�������Ϣ
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		TestPlan plan = planDAO.get(planid);
		plan.setTestState(MainTenanceConstant.TEST_PLAN_END);
		planDAO.save(plan);
		sendMsg(plan,planid);
		evaluate(user,planid);
	}
	
	
	/**
	 * ���Ͷ���(���ŷ��͸���д���Ϸ���������)
	 */
	public void sendMsg(TestPlan plan,String planId){
		try{
			String eid = planId;
			UserInfoDAOImpl userdao = new UserInfoDAOImpl();
			String userid = plan.getCreatorId();
			UserInfo user = userdao.findById(userid);
			if(user!=null){
				String phone = user.getPhone();
				if(phone!=null && !"".equals(phone)){
					//���Ͷ���
					String content = "������ά��������һ������Ϊ\""+plan.getTestPlanName()+" \" �Ĳ��Լƻ�";
					content+="�Ѿ����˽�����";
					logger.info("��������: "+phone +":"+content);
					super.sendMessage(content, phone);
//					smSendProxy.simpleSend(phone,content, null, null, true);
					//������ż�¼
					SMHistory history = new SMHistory();
					history.setSimIds(phone);
					history.setSendContent(content);
					history.setSendTime(new Date());
					history.setSmType(MainTenanceConstant.LP_EVALUATE);
					history.setObjectId(eid);
					history.setBusinessModule(MainTenanceConstant.MAINTENANCE_MODULE);
					historyDAO.save(history);
				}
			}
		}catch(Exception e){
			e.getStackTrace();
		}
	}

	
	/**
	 * ���˽��������̽���
	 * @param user
	 */
	private void evaluate(UserInfo user,String planid) {
		Task task = workflowBo.getHandleTaskForId(user.getUserID(),planid);
		if (task != null
				&& MaintenanceWorkflowBO.EVALUATE_TASK.equals(task.getName())) {
			workflowBo.completeTask(task.getId(), "end");
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(task, user, "",
					ModuleCatalog.MAINTENANCE);
			processHistoryBean.setNextOperateUserId("");
			processHistoryBean.setObjectId(planid);
			processHistoryBean.setProcessAction("���Լƻ�����");
			processHistoryBean.setTaskOutCome("end");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
		System.out.println("���Լƻ��Ѿ����ˣ�");
	}
	
	public Evaluate getEvaluate(String id, String type){
		return dao.getEvaluate(id, type);
	}
	
	
	@Override
	protected HibernateDao<Evaluate, String> getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}


}
