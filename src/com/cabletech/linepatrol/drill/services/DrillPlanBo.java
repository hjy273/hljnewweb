package com.cabletech.linepatrol.drill.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
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
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.commons.util.StringUtils;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.cut.module.CutConstant;
import com.cabletech.linepatrol.drill.beans.DrillPlanBean;
import com.cabletech.linepatrol.drill.dao.DrillPlanDao;
import com.cabletech.linepatrol.drill.dao.DrillPlanModifyDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskConDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskDao;
import com.cabletech.linepatrol.drill.module.DrillConstant;
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.module.DrillTaskCon;
import com.cabletech.linepatrol.drill.workflow.DrillWorkflowBO;

@Service
@Transactional
public class DrillPlanBo extends EntityManager<DrillPlan, String> {

	private static Logger logger = Logger
			.getLogger(DrillPlanBo.class.getName());
	
	@Autowired
	private DrillWorkflowBO workflowBo;

	@Override
	protected HibernateDao<DrillPlan, String> getEntityDao() {
		return drillPlanDao;
	}

	@Resource(name = "drillTaskDao")
	private DrillTaskDao drillTaskDao;

	@Resource(name = "drillTaskConDao")
	private DrillTaskConDao drillTaskConDao;
	
	@Resource(name = "drillPlanModifyDao")
	private DrillPlanModifyDao drillPlanModifyDao;

	@Resource(name = "drillPlanDao")
	private DrillPlanDao drillPlanDao;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	
	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;

	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;

	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;

	/**
	 * �鿴��������
	 * 
	 * @param taskId����������ID
	 * @return����������ʵ��
	 */
	public Map getDrillTaskByTaskId(String taskId, String conId) {
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		DrillPlan drillPlan = drillPlanDao.getDrillPlanByTaskIdAndContractorId(taskId, conId);
		String[] approveInfo = drillTaskDao.getUserIdAndUserNameByUserId(drillTask.getCreator());
		Map map = new HashMap();
		map.put("drillTask", drillTask);
		map.put("approveInfo", approveInfo);
		map.put("drillPlan", drillPlan);
		return map;
	}

	/**
	 * ��������ƻ� ��������ƻ�ǰ���ж��Ƿ��Ѿ��ƶ�������������û���ƶ������������ƶ��������񣬺���������ƻ��������ƶ���ֱ����������ƻ���
	 * �ж�����������ID�Ƿ����
	 * 
	 * @param drillPlanBean�������ƻ�Bean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void addDrillPlan(DrillPlanBean drillPlanBean, UserInfo userInfo,
			List<FileItem> files) throws ServiceException {

		// ��ȡ��������
		String taskId = drillPlanBean.getTaskId();
		String userId = userInfo.getUserID();
		String deptId = userInfo.getDeptID();
		String approvers = drillPlanBean.getApproveId();
		String reader = drillPlanBean.getReader();
		String mobiles = drillPlanBean.getMobile();
		String name = drillPlanBean.getName();
		String drillTaskConId = "";
		String[] readerPhones = drillPlanBean.getReaderPhones();

		// ����taskId�Ƿ�Ϊ���ж����������Ƿ��ƶ�����Ϊ�ձ�ʾδ�ƶ������ƶ���������
		DrillPlan drillPlanSave = new DrillPlan();
		if (taskId == null || "".equals(taskId)) {
			// ����Ǵ�ά��Ա�Լ����������������Ƚ��������ƻ�
			// ������������
			DrillTask drillTask = new DrillTask();
			try {
				BeanUtil.objectCopy(drillPlanBean, drillTask);
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("ת���쳣��" + e1.getMessage());
				throw new ServiceException();
			}
			drillTask.setDrillType(DrillTask.DRILLTYPE_CON);
			drillTask.setBeginTime(DateUtil.parseDateTime(drillPlanBean.getRealBeginTime()));
			drillTask.setEndTime(DateUtil.parseDateTime(drillPlanBean.getRealEndTime()));
			drillTask.setLocale(drillPlanBean.getAddress());
			drillTask.setDemand(drillPlanBean.getScenario());
			DrillTask drillTaskSave = saveDrillTask(drillTask, userId);

			// ���������������ά��ϵ��
			DrillTaskCon drillTaskCon = saveDrillTaskCon(deptId, drillTaskSave.getId(),
					DrillTaskCon.APPROVEPLAN);
			drillTaskConId = drillTaskCon.getId();

			// ���������ƻ�
			drillPlanBean.setTaskId(drillTaskSave.getId());
			drillPlanSave = saveDrillPlan(drillPlanBean, userId, deptId);
		} else {
			// �����ƻ��ѽ�����������������
			drillPlanSave = saveDrillPlan(drillPlanBean, userId, deptId);

			drillTaskConDao.setStateByContractorIdAndTaskId(deptId, taskId,
					DrillTaskCon.APPROVEPLAN);
		}

		// ���渽��
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), drillPlanSave.getId(),
				DrillConstant.LP_DRILLPLAN, userInfo.getUserID());

		// �����������Ϣ
		String planId = drillPlanSave.getId();
		
		//�����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, planId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLPLAN);
		
		//���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, planId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN);
		//��ά�Լ��ƶ��ƻ�
		if (taskId == null || "".equals(taskId)) {
			Map variables = new HashMap();
			variables.put("assignee",approvers+","+reader);
			variables.put("transition", "apporve");
			workflowBo.createProcessInstance("drill", drillTaskConId, variables);
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("�ƶ���������");//������̴�����˵��
			processHistoryBean.setTaskOutCome("apporve");//��ӹ�����������Ϣ
			processHistoryBean.initial(null, userInfo, "drill."+drillTaskConId,ModuleCatalog.DRILL);
			processHistoryBean.setNextOperateUserId(approvers+","+reader);//�����һ�������˵ı��
			processHistoryBean.setObjectId(drillTaskConId);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}else{
			//��ά������ά���������ƶ��ƻ�
			String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, deptId);
			Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),eid);
			if (task != null && DrillWorkflowBO.CREATE_DRILL_PROJ_TASK.equals(task.getName())) {
				System.out.println("�����ƻ�����У�" + task.getName());
				workflowBo.setVariables(task, "assignee", approvers+","+reader);
				workflowBo.completeTask(task.getId(), "approve");
				System.out.println("�����ƻ��Ѿ��ύ��");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�ƶ���������");//������̴�����˵��
				processHistoryBean.setTaskOutCome("approve");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(approvers+","+reader);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
		
		
		// ���Ͷ���
		String content = "������������һ������Ϊ\"" + name + "\"�����������ȴ�������ˡ�";
		sendMessage(content, mobiles);

		// ������ż�¼
		saveMessage(content, mobiles, planId,
				DrillConstant.LP_DRILLPLAN, ModuleCatalog.DRILL);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "������������һ������Ϊ\"" + name + "\"�����������ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], planId, DrillConstant.LP_DRILLPLAN,
						ModuleCatalog.DRILL);
			}
		}
		
		
	}
	
	public void tempSaveDrillPlan(DrillPlanBean drillPlanBean, UserInfo userInfo,
			List<FileItem> files) throws ServiceException {

		// ��ȡ��������
		String userId = userInfo.getUserID();
		String deptId = userInfo.getDeptID();
		String approvers = drillPlanBean.getApproveId();
		String reader = drillPlanBean.getReader();

		DrillPlan drillPlanSave = new DrillPlan();
		drillPlanSave = saveDrillPlan(drillPlanBean, userId, deptId);

		// ���渽��
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), drillPlanSave.getId(),
				DrillConstant.LP_DRILLPLAN, userInfo.getUserID());
		
		// �����������Ϣ
		String planId = drillPlanSave.getId();
		
		replyApproverDAO.deleteList(planId, DrillConstant.LP_DRILLPLAN);
		//�����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, planId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLPLAN);
		
		//���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, planId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN);

	}

	/**
	 * ͨ���ƻ�ID�ִ�������ִ�мƻ�
	 * 
	 * @param planId�������ƻ�ID
	 * @return
	 */
	public Map getTaskAndPlan(String planId) {
		String taskId = drillPlanDao.getTaskIdByPlanId(planId);
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		List planEndList = drillPlanModifyDao.findByProperty("planId", planId);
		String conId = drillTaskConDao.getConIdByPlanId(planId);
		//ApproveInfo approveInfo = this.getLastApproveInfo(planId, DrillConstant.LP_DRILLPLAN);
		Map map = new HashMap();
		map.put("drillTask", drillTask);
		map.put("drillPlan", drillPlan);
		map.put("planEndList", planEndList);
		map.put("conId", conId);
		//map.put("approveInfo", approveInfo);
		return map;
	}
	
	public ApproveInfo getLastApproveInfo(String objectId, String objectType){
		String hql = "from ApproveInfo approve where approve.objectId=? and objectType=?";
		List list = approveDao.find(hql, objectId, objectType);
		if(list != null && list.size() > 0){
			String id = ((ApproveInfo)list.get(0)).getId();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ApproveInfo approveInfo = (ApproveInfo) iterator.next();
				if(Integer.parseInt(id) < Integer.parseInt(approveInfo.getId())){
					id = approveInfo.getId();
				}
			}
			return approveDao.findByUnique("id", id);
		}
		return null;
	}

	/**
	 * �༭�����ƻ�
	 * 
	 * @param drillPlanBean�������ƻ�ʵ��Bean
	 * @param userInfo
	 * @param taskCreator���������񴴽���
	 * @throws ServiceException
	 */
	public void editDrillPlan(DrillPlanBean drillPlanBean, UserInfo userInfo,
			String taskCreator, List<FileItem> files) throws ServiceException {
		String approvers = drillPlanBean.getApproveId();
		String reader = drillPlanBean.getReader();
		String mobiles = drillPlanBean.getMobile();
		String[] readerPhones = drillPlanBean.getReaderPhones();

		DrillTask drillTask = new DrillTask();
		try {
			BeanUtil.objectCopy(drillPlanBean, drillTask);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("drillPlanBeanת��ΪdrillTask����������Ϣ��" + e.getMessage());
			throw new ServiceException();
		}
		if (taskCreator.equals(userInfo.getUserID())) {
			drillTask.setId(drillPlanBean.getTaskId());
			saveDrillTask(drillTask, userInfo.getUserID());
		}
		saveDrillPlan(drillPlanBean, userInfo.getUserID(), userInfo.getDeptID());
		drillTaskConDao.setStateByContractorIdAndTaskId(userInfo.getDeptID(),
				drillPlanBean.getTaskId(), DrillTaskCon.APPROVEPLAN);

		String planId = drillPlanBean.getId();
		// ���渽��
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), planId, DrillConstant.LP_DRILLPLAN, userInfo
				.getUserID());

		replyApproverDAO.deleteList(planId, DrillConstant.LP_DRILLPLAN);
		
		//�����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, planId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLPLAN);
		
		//���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, planId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN);
		String eid = drillTaskConDao.getIdByConIdAndTaskId(drillPlanBean.getTaskId(), userInfo.getDeptID());
		// ������
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null && DrillWorkflowBO.CREATE_DRILL_PROJ_TASK.equals(task.getName())) {
			System.out.println("�����ƻ�����У�" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "approve");
			System.out.println("�����ƻ��Ѿ��ύ��");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("�ƶ���������");//������̴�����˵��
			processHistoryBean.setTaskOutCome("approve");//��ӹ�����������Ϣ
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
			processHistoryBean.setNextOperateUserId(approvers+","+reader);//�����һ�������˵ı��
			processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		// ���Ͷ���
		String content = "������������һ������Ϊ\"" + drillTask.getName() + "\"�����������ȴ�������ˡ�";
		sendMessage(content, mobiles);

		// ������ż�¼
		saveMessage(content, mobiles, planId,
				DrillConstant.LP_DRILLPLAN, ModuleCatalog.DRILL);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "������������һ������Ϊ\"" + drillTask.getName() + "\"�����������ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], planId, DrillConstant.LP_DRILLPLAN,
						ModuleCatalog.DRILL);
			}
		}
		
		
	}

	/**
	 * �����ƻ���� ��operatorΪapprove�Ǳ�ʾ��ˣ�Ϊtransfer��ʾת��
	 * 
	 * @param drillPlanBean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void approveDrillPlan(DrillPlanBean drillPlanBean, UserInfo userInfo)
			throws ServiceException {
		String planId = drillPlanBean.getId();
		String approveResult = drillPlanBean.getApproveResult();
		String approveRemark = drillPlanBean.getApproveRemark();
		String operator = drillPlanBean.getOperator();
		String conId = drillPlanBean.getContractorId();
		String taskId = drillPlanBean.getTaskId();
		String approves = drillPlanBean.getApprovers();
		String userId = userInfo.getUserID();
		String creator = drillPlanBean.getCreator();
		String phone = "";
		String deadline = drillPlanBean.getDeadline();
		String content = "";
		//approverDAO.deleteList(planId, DrillConstant.LP_DRILLPLAN);
		
		//ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approves + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = drillPlanDao.getApproverIds(planId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN, condition);
		
		// ���������Ϣ
		saveApproveInfo(planId, DrillConstant.LP_DRILLPLAN,
				userId, approveResult, approveRemark);

		if ("approve".equals(operator)) {
			phone = getPhoneByUserId(creator);
			if (DrillConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				// �ı�״̬
				drillTaskConDao.setStateByContractorIdAndTaskId(conId, taskId,
						DrillTaskCon.PLANUNAPPROVE);
				// �ı�δͨ������
				drillPlanDao.setUnapproveNumberByPlanId(planId);

				// ���Ͷ�������
				content = "������������\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"����������δͨ����ˡ�";
				drillPlanDao.addDeadline(planId, deadline);
			} else {

				// �ı�״̬
				drillTaskConDao.setStateByContractorIdAndTaskId(conId, taskId,
						DrillTaskCon.ADDSUMMARY);
				
				// ���Ͷ�������
				content = "������������\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"�����������Ѿ�ͨ����ˡ�";
				//��������ܽ���д����
				drillPlanDao.addDeadline(planId, deadline);
			}
			
		} else {
			// ����ת������Ϣ
			replyApproverDAO.saveApproverOrReader(approves, planId, DrillConstant.APPROVE_TRANSFER_MAN, DrillConstant.LP_DRILLPLAN);

			// ���Ͷ���
			content = "������������һ������Ϊ\""
					+ drillTaskDao.getTaskNameByTaskId(taskId)
					+ "\"�����������ȴ�������ˡ�";
			phone = drillPlanBean.getMobile();
		}
		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, conId);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null
				&& DrillWorkflowBO.APPROVE_DRILL_PROJ_TASK.equals(task.getName())) {
			System.out.println("������������ˣ�" + task.getName());
			if (DrillConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", creator);
				workflowBo.setVariables(task, "transition", "passed");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("�����������ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("������������ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("passed");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(creator);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (DrillConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", creator);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("����������˲�ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��������������ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("not_passed");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(creator);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
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
				System.out.println("���������Ѿ�ת��");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("��������ת��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("transfer");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(approves);//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
		
		// ���Ͷ���
		sendMessage(content, phone);
		// ���淢�Ͷ��ż�¼
		saveMessage(content, phone, planId,
				DrillConstant.LP_DRILLPLAN, ModuleCatalog.DRILL);
		
		
	}

	/**
	 * ������������
	 * 
	 * @param drillTask����������ʵ��
	 * @param creator���������񴴽���
	 * @return
	 */
	private DrillTask saveDrillTask(DrillTask drillTask, String creator) {
		drillTask.setDeadline(null);
		drillTask.setCreator(creator);
		drillTask.setCreateTime(new Date());
		return drillTaskDao.addDrillTask(drillTask);
	}

	/**
	 * ���������������ά��ϵ��
	 * 
	 * @param conId����άID
	 * @param taskId����������ID
	 * @param state��״̬
	 */
	private DrillTaskCon saveDrillTaskCon(String conId, String taskId, String state) {
		DrillTaskCon drillTaskCon = new DrillTaskCon();
		drillTaskCon.setContractorId(conId);
		drillTaskCon.setDrillId(taskId);
		drillTaskCon.setState(state);
		return drillTaskConDao.saveDrillTaskCon(drillTaskCon);
	}

	/**
	 * ���������ƻ�
	 * 
	 * @param drillPlanBean�������ƻ�ʵ��Bean
	 * @param creator�������ƻ�������
	 * @param conId����άID
	 * @return
	 */
	private DrillPlan saveDrillPlan(DrillPlanBean drillPlanBean,
			String creator, String conId) {
		DrillPlan drillPlan = new DrillPlan();
		try {
			BeanUtil.objectCopy(drillPlanBean, drillPlan);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("drillPlanBeanת��ΪdrillPlan����" + e.getMessage());
			throw new ServiceException();
		}
		drillPlan.setCreator(creator);
		drillPlan.setCreateTime(new Date());
		drillPlan.setContractorId(conId);
		return drillPlanDao.addDrillPlan(drillPlan);
	}

	public DrillPlanDao getDrillPlanDao() {
		return drillPlanDao;
	}

	public void setDrillPlanDao(DrillPlanDao drillPlanDao) {
		this.drillPlanDao = drillPlanDao;
	}
	
	/**
	 * ���ַ���ת��ΪList
	 * 
	 * @param str����Ҫת�����ַ���
	 * @return��list
	 */
	public List<String> stringToList(String str) {
		if (str == null || str.equals("")) {
			return null;
		}
		String[] strArray = str.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; strArray != null && i < strArray.length; i++) {
			list.add(strArray[i]);
		}
		return list;
	}

	/**
	 * �����ļ�
	 * 
	 * @param files���ϴ��ĸ���
	 * @param module��ģ�鳣��
	 * @param regionName����������
	 * @param entityId������������ʵ��ID
	 * @param entityType�����������ı���
	 * @param uploader�������ϴ���
	 */
	public void saveFiles(List<FileItem> files,
			String module, String regionName, String entityId,
			String entityType, String uploader) {
		uploadFile.saveFiles(files, module, regionName , entityId, entityType, uploader);
	}

	/**
	 * �����������Ϣ
	 * 
	 * @param approvers�������
	 * @param approveType���������
	 * @param entityId��ʵ��ID
	 * @param objectType��ʵ������ı���
	 */
	public void saveApprove(String approvers, String approveType,
			String entityId, String entityType) {
		List<String> approverList = this.stringToList(approvers);
		if (approverList != null && approverList.size() > 0) {
			for (String str : approverList) {
				ReplyApprover approver = new ReplyApprover();
				approver.setObjectId(entityId);
				approver.setApproverId(str);
				approver.setObjectType(entityType);
				approver.setApproverType(approveType);
				approverDAO.save(approver);
			}
		}
	}
	
	/**
	 * ɾ�������Ϣ
	 * 
	 * @param entityId��ʵ��ID
	 * @param entityType��ʵ������
	 */
	public void deleteApprove(String entityId, String entityType) {
		approverDAO.deleteList(entityId, entityType);
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
		List list = drillPlanDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	public void readReply(UserInfo userInfo, String approverId, String planId) throws ServiceException{
		approverDAO.updateReader(approverId, planId, DrillConstant.LP_DRILLPLAN);
		
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		String taskId = drillPlan.getTaskId();
		String conId = drillPlan.getContractorId();
		String creator = drillPlan.getCreator();
		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, conId);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		//����������ʷ
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("������������");//������̴�����˵��
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
