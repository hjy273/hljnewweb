package com.cabletech.linepatrol.drill.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
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
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.drill.beans.DrillPlanModifyBean;
import com.cabletech.linepatrol.drill.dao.DrillPlanDao;
import com.cabletech.linepatrol.drill.dao.DrillPlanModifyDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskConDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskDao;
import com.cabletech.linepatrol.drill.module.DrillConstant;
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillPlanModify;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.module.DrillTaskCon;
import com.cabletech.linepatrol.drill.workflow.DrillSubWorkflowBO;

@Service
@Transactional
public class DrillPlanModifyBo extends EntityManager<DrillPlanModify, String> {

	private static Logger logger = Logger
			.getLogger(DrillPlanModifyBo.class.getName());
	
	@Override
	protected HibernateDao<DrillPlanModify, String> getEntityDao() {
		return drillPlanModifyDao;
	}
	
	@Autowired
	private DrillSubWorkflowBO workflowBo;

	@Resource(name = "drillPlanModifyDao")
	private DrillPlanModifyDao drillPlanModifyDao;
	
	@Resource(name = "drillPlanDao")
	private DrillPlanDao drillPlanDao;
	
	@Resource(name = "drillTaskDao")
	private DrillTaskDao drillTaskDao;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	
	@Resource(name = "drillTaskConDao")
	private DrillTaskConDao drillTaskConDao;
	
	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;

	/**
	 * ��ô��칤���б�
	 * @param userInfo
	 * @param condition
	 * @param taskName
	 * @return
	 */
	public List getAgentList(UserInfo userInfo, String condition,
			String taskName) {
		String assignee = userInfo.getUserID();
		List list = workflowBo.queryForHandleListBeanDetail(assignee, condition,
				taskName);
		List list2 = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String modifyId = (String) bean.get("modify_id");
				boolean read = approverDAO.isReadOnly(modifyId,
						assignee, DrillConstant.LP_DRILLPLAN_MODIFY);
				bean.set("isread", "" + read);
				if(judgeFinishRead(bean, modifyId, DrillConstant.LP_DRILLPLAN_MODIFY, assignee)){
					list2.add(bean);
				}
			}
		}
		return list2;
	}
	
	/**
	 * �ж��Ƿ��Ѳ���
	 * @param bean
	 * @param objectId
	 * @param objectType
	 * @param userId
	 * @return
	 */
	private boolean judgeFinishRead(BasicDynaBean bean, String objectId,
			String objectType, String userId) {
		ReplyApprover approver;
		boolean flag = true;
		List<ReplyApprover> approverList = approverDAO.getApprovers(objectId,
				objectType);
		for (int i = 0; approverList != null && i < approverList.size(); i++) {
			approver = approverList.get(i);
			if (approver != null && userId.equals(approver.getApproverId())) {
				if (CommonConstant.FINISH_READED.equals(approver
						.getFinishReaded())) {
					flag = false;
				} else {
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * ��ò�ѯ�����б�
	 * @param condition
	 * @return
	 */
	public List getQueryList(String condition){
		return drillPlanModifyDao.getQueryList(condition);
	}

	/**
	 * ��������������ǰ��������
	 * @param planId
	 * @return
	 */
	public Map addDrillPlanModifyForm(String planId){
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		String taskId = drillPlan.getTaskId();
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		Map map = new HashMap();
		map.put("drillTask", drillTask);
		map.put("drillPlan", drillPlan);
		return map;
	}
	
	/**
	 * ����������ID��ѯ�������������Ϣ
	 * @param planId
	 * @return
	 */
	public List viewDrillPlanModifyByPlanId(String planId){
		List list = drillPlanModifyDao.findByProperty("planId", planId);
		return list;
	}
	
	/**
	 * ��������������
	 * @param drillPlanModifyBean�������������Bean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void addDrillPlanModify(DrillPlanModifyBean drillPlanModifyBean, UserInfo userInfo) throws ServiceException {
		String planId = drillPlanModifyBean.getPlanId();
		String approvers = drillPlanModifyBean.getApproveId();
		String reader = drillPlanModifyBean.getReader();
		String mobiles = drillPlanModifyBean.getMobiles();
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		String taskId = drillPlan.getTaskId();
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		String name = drillTask.getName();
		String conId = userInfo.getDeptID();
		String[] readerPhones = drillPlanModifyBean.getReaderPhones();
		String maineid = drillTaskConDao.getIdByConIdAndTaskId(taskId, conId);
		
		DrillPlanModify drillPlanModify = new DrillPlanModify();
		try {
			BeanUtil.objectCopy(drillPlanModifyBean, drillPlanModify);
		} catch (Exception e) {
			logger.error("DrillPlanModifyBeanת��ΪDrillPlanModify����������Ϣ��" + e.getMessage());
			e.printStackTrace();
			throw new ServiceException();
		}
		drillPlanModify.setModifyMan(userInfo.getUserID());
		drillPlanModify.setModifyDate(new Date());
		DrillPlanModify drillPlanModifySave = drillPlanModifyDao.saveDrillPlanModify(drillPlanModify);
		String drillPlanModifyId = drillPlanModifySave.getId();
		
		//�����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, drillPlanModifyId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLPLAN_MODIFY);
		
		//���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, drillPlanModifyId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN_MODIFY);
		String eid = drillPlanModifySave.getId();
		// JBPM
		Map variables = new HashMap();
		variables.put("assignee", userInfo.getUserID());
		//variables.put("transition", "start");
		workflowBo.createProcessInstance(DrillSubWorkflowBO.DRILL_SUB_WORKFLOW, eid, variables);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null
				&& DrillSubWorkflowBO.CHANGE_DRILL_PROJ_TASK.equals(task
						.getName())) {
			workflowBo.setVariables(task, "assignee", approvers + "," + reader);
			workflowBo.completeTask(task.getId(), "change");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("�����������");//������̴�����˵��
			processHistoryBean.setTaskOutCome("change");//��ӹ�����������Ϣ
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
			processHistoryBean.setNextOperateUserId(approvers + "," + reader);//�����һ�������˵ı��
			processHistoryBean.setObjectId(maineid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		
		// ���Ͷ���
		String content = "������������һ������Ϊ\"" + name + "\"��������������뵥�ȴ�������ˡ�";
		sendMessage(content, mobiles);

		// ������ż�¼
		saveMessage(content, mobiles, drillPlanModifyId,
				DrillConstant.LP_DRILLPLAN_MODIFY, ModuleCatalog.DRILL);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "������������һ������Ϊ\"" + name + "\"��������������뵥�ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], drillPlanModifyId, DrillConstant.LP_DRILLPLAN_MODIFY,
						ModuleCatalog.DRILL);
			}
		}
		
		
			
			/*drillTaskConDao.getIdByConIdAndTaskId(taskId, userInfo.getDeptID());
		//JBPM
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),eid);
		if (task != null && DrillWorkflowBO.CHANGE_DRILL_PROJ_TASK.equals(task.getName())) {
			System.out.println("������������У�" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "change");
			System.out.println("������������Ѿ��ύ��");
		}*/
	}
	
	/**
	 * ���������������ǰ��������
	 * @param planModifyId:�����������ID
	 * @return
	 */
	public Map approveDrillPlanModifyForm(String planModifyId) {
		DrillPlanModify drillPlanModify = drillPlanModifyDao.findByUnique("id", planModifyId);
		String planId = drillPlanModify.getPlanId();
		String taskId = drillPlanDao.getTaskIdByPlanId(planId);
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		List list = drillPlanModifyDao.findByProperty("planId", planId);
		String conId = drillTaskConDao.getConIdByPlanId(planId);
		Map map = new HashMap();
		map.put("drillPlanModify", drillPlanModify);
		map.put("list", list);
		map.put("drillTask", drillTask);
		map.put("drillPlan", drillPlan);
		map.put("conId", conId);
		return map;
	}
	
	/**
	 * ���������������
	 * @param drillPlanModifyBean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void approveDrillPlanModify(DrillPlanModifyBean drillPlanModifyBean, UserInfo userInfo) throws ServiceException {
		String planModifyId = drillPlanModifyBean.getId();
		String id = drillPlanModifyBean.getPlanId();
		String userId = userInfo.getUserID();
		String approveResult = drillPlanModifyBean.getApproveResult();
		String approveRemark = drillPlanModifyBean.getApproveRemark();
		String operator = drillPlanModifyBean.getOperator();
		String conId = drillPlanModifyBean.getConId();
		String taskId = drillPlanModifyBean.getTaskId();
		String creator = drillPlanModifyBean.getModifyMan();
		String phone ="";
		String approves = drillPlanModifyBean.getApprovers();
		String nextStartTime = drillPlanModifyBean.getNextStartTime();
		String nextEndTime = drillPlanModifyBean.getNextEndTime();
		String deadline = drillPlanModifyBean.getDeadline();
		String content = "";
		String maineid = drillTaskConDao.getIdByConIdAndTaskId(taskId, conId);
		//ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approves + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = drillPlanDao.getApproverIds(planModifyId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN_MODIFY, condition);
		// ���������Ϣ
		saveApproveInfo(planModifyId, DrillConstant.LP_DRILLPLAN_MODIFY,
				userId, approveResult, approveRemark);

		if ("approve".equals(operator)) {
			phone = getPhoneByUserId(creator);
			if (DrillConstant.APPROVE_RESULT_NO.equals(approveResult)) {

				// ���Ͷ�������
				content = "������������\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"��������������뵥δͨ����ˡ�";
				
				//drillPlanModifyDao.delete(planModifyId);
			} else {
				// �ı�״̬
				drillTaskConDao.setStateByContractorIdAndTaskId(conId, taskId,
						DrillTaskCon.ADDSUMMARY);

				// ���Ͷ�������
				content = "������������\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"��������������뵥�Ѿ�ͨ����ˡ�";
				
				drillPlanDao.updateDate(nextStartTime, nextEndTime, deadline, id);
			}
			
		} else {
			// ����ת������Ϣ
			replyApproverDAO.saveApproverOrReader(approves, planModifyId, DrillConstant.APPROVE_TRANSFER_MAN, DrillConstant.LP_DRILLPLAN_MODIFY);

			// ���Ͷ���
			content = "������������һ������Ϊ\""
					+ drillTaskDao.getTaskNameByTaskId(taskId)
					+ "\"��������������뵥�ȴ�������ˡ�";
			
			phone = drillPlanModifyBean.getMobiles();
		}
		// ���Ͷ���
		sendMessage(content, phone);
		// ���淢�Ͷ��ż�¼
		saveMessage(content, phone, planModifyId,
				DrillConstant.LP_DRILLPLAN_MODIFY, ModuleCatalog.DRILL);
		
		String eid = planModifyId;
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null
				&& DrillSubWorkflowBO.APPROVE_CHANGE_DRILL_PROJ_TASK.equals(task
						.getName())) {
			System.out.println("���Ϸ�����ֹ����ˣ�" + task.getName());
			if (DrillConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "transition", "end");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("���Ϸ�����ֹ���ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("���������������ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("end");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(creator);//�����һ�������˵ı��
				processHistoryBean.setObjectId(maineid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (DrillConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "transition", "end");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("���Ϸ�����ֹ��˲�ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�����������������ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("end");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(creator);//�����һ�������˵ı��
				processHistoryBean.setObjectId(maineid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (DrillConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", approves+","+approverIdsReader);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("���Ϸ�����ֹ�Ѿ�ת��");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�����������ת��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("transfer");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(approves);//�����һ�������˵ı��
				processHistoryBean.setObjectId(maineid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
	}
	
	/**
	 * ���������Ϣ
	 * 
	 * @param entityId��ʵ��ID
	 * @param entityType��ʵ�����
	 * @param approverId�������ID
	 * @param approveResult����˽��
	 * @param approveRemark��������
	 */
	public void saveApproveInfo(String entityId, String entityType,
			String approverId, String approveResult, String approveRemark) {
		ApproveInfo approveInfo = new ApproveInfo();
		approveInfo.setObjectId(entityId);
		approveInfo.setObjectType(entityType);
		approveInfo.setApproverId(approverId);
		approveInfo.setApproveTime(new Date());
		approveInfo.setApproveResult(approveResult);
		approveInfo.setApproveRemark(approveRemark);
		approveDao.save(approveInfo);
	}

	/**
	 * ���Ͷ���
	 * 
	 * @param content����������
	 * @param mobiles�����ն����ֻ�����
	 */
	public void sendMessage(String content, String mobiles) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}

	/**
	 * ������ż�¼
	 * 
	 * @param content����������
	 * @param mobiles�����ն����ֻ�����
	 * @param entityId��ʵ��ID
	 * @param entityType��ʵ������
	 * @param entityModule��ʵ��ģ��
	 */
	public void saveMessage(String content, String mobiles, String entityId,
			String entityType, String entityModule) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			for (String mobile : mobileList) {
				SMHistory history = new SMHistory();
				history.setSimIds(mobile);
				history.setSendContent(content);
				history.setSendTime(new Date());
				history.setSmType(entityType);
				history.setObjectId(entityId);
				history.setBusinessModule(entityModule);
				historyDAO.save(history);
			}
		}
	}

	/**
	 * ͨ���û�ID��ѯ�û����ֻ�����
	 * 
	 * @param userId���û�ID
	 * @return�������û��ֻ�����
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = drillPlanModifyDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	/**
	 * ���������������
	 * @param userInfo
	 * @param approverId
	 * @param modifyId
	 * @throws ServiceException
	 */
	public void readReply(UserInfo userInfo, String approverId, String modifyId) throws ServiceException{
		approverDAO.updateReader(approverId, modifyId, DrillConstant.LP_DRILLPLAN_MODIFY);
		
		DrillPlanModify drillPlanModify = drillPlanModifyDao.findByUnique("id", modifyId);
		String creator = drillPlanModify.getModifyMan();
		String eid = drillPlanModifyDao.getTaskConIdByModifyId(modifyId);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		//����������ʷ
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("���������������");//������̴�����˵��
		processHistoryBean.setTaskOutCome("");//��ӹ�����������Ϣ
		processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
		processHistoryBean.setNextOperateUserId("");//�����һ�������˵ı��
		processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}
