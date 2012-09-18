package com.cabletech.linepatrol.drill.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.cabletech.linepatrol.drill.beans.DrillSummaryBean;
import com.cabletech.linepatrol.drill.dao.DrillPlanDao;
import com.cabletech.linepatrol.drill.dao.DrillPlanModifyDao;
import com.cabletech.linepatrol.drill.dao.DrillSummaryDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskConDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskDao;
import com.cabletech.linepatrol.drill.module.DrillConstant;
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillSummary;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.module.DrillTaskCon;
import com.cabletech.linepatrol.drill.workflow.DrillWorkflowBO;
import com.cabletech.linepatrol.safeguard.module.SafeguardConstant;

@Service
@Transactional
public class DrillSummaryBo extends EntityManager<DrillSummary, String> {

	private static Logger logger = Logger.getLogger(DrillSummaryBo.class
			.getName());

	@Override
	protected HibernateDao<DrillSummary, String> getEntityDao() {
		return drillSummaryDao;
	}
	
	@Autowired
	private DrillWorkflowBO workflowBo;

	@Resource(name = "drillTaskDao")
	private DrillTaskDao drillTaskDao;

	@Resource(name = "drillTaskConDao")
	private DrillTaskConDao drillTaskConDao;

	@Resource(name = "drillPlanDao")
	private DrillPlanDao drillPlanDao;
	
	@Resource(name = "drillPlanModifyDao")
	private DrillPlanModifyDao drillPlanModifyDao;

	@Resource(name = "drillSummaryDao")
	private DrillSummaryDao drillSummaryDao;

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
	 * ��������ܽ�ǰ���������������������
	 * 
	 * @param planId����������ID
	 * @return
	 */
	public Map addDrillSummaryForm(String planId) {
		String taskId = drillPlanDao.getTaskIdByPlanId(planId);
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		List planEndList = drillPlanModifyDao.findByProperty("planId", planId);
		DrillSummary drillSummary = drillSummaryDao.findByUnique("planId", planId);
		Map map = new HashMap();
		map.put("drillTask", drillTask);
		map.put("drillPlan", drillPlan);
		map.put("planEndList", planEndList);
		map.put("drillSummary", drillSummary);
		return map;
	}

	/**
	 * ��������ܽ�
	 * 
	 * @param drillSummaryBean�������ܽ�ʵ��Bean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void addDrillSummary(DrillSummaryBean drillSummaryBean,
			UserInfo userInfo, List<FileItem> files) throws ServiceException {
		String taskId = drillSummaryBean.getTaskId();
		String approvers = drillSummaryBean.getApproveId();
		String reader = drillSummaryBean.getReader();
		String name = drillSummaryBean.getName();
		String mobiles = drillSummaryBean.getMobiles();
		String[] readerPhones = drillSummaryBean.getReaderPhones();

		// ���������ܽ�
		DrillSummary drillSummarySave = saveDrillSummary(drillSummaryBean,
				userInfo);
		String summaryId = drillSummarySave.getId();

		drillTaskConDao.setStateByContractorIdAndTaskId(userInfo.getDeptID(),
				taskId, DrillTaskCon.APPROVESUMMARY);

		// ���渽��
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), summaryId, DrillConstant.LP_DRILLSUMMARY,
				userInfo.getUserID());

		//�����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, summaryId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLSUMMARY);
		
		//���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, summaryId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLSUMMARY);
		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, userInfo.getDeptID());
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),eid);
		if (task != null && DrillWorkflowBO.CREATE_DRILL_SUMMARY_TASK.equals(task.getName())) {
			System.out.println("�����ܽ�����У�" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "approve");
			System.out.println("�����ܽ��Ѿ��ύ��");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("�ƶ������ܽ�");//������̴�����˵��
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
		String content = "������������һ������Ϊ\"" + name + "\"�����ܽᵥ�ȴ�������ˡ�";
		sendMessage(content, mobiles);

		// ������ż�¼
		saveMessage(content, mobiles, summaryId,
				DrillConstant.LP_DRILLSUMMARY, ModuleCatalog.DRILL);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "������������һ������Ϊ\"" + name + "\"�����ܽᵥ�ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], summaryId, DrillConstant.LP_DRILLSUMMARY,
						ModuleCatalog.DRILL);
			}
		}
	}
	
	/**
	 * �����ܽ���ʱ����
	 * @param drillSummaryBean
	 * @param userInfo
	 * @param files
	 * @throws ServiceException
	 */
	public void tempSaveDrillSummary(DrillSummaryBean drillSummaryBean,
			UserInfo userInfo, List<FileItem> files) throws ServiceException {
		String approvers = drillSummaryBean.getApproveId();
		String reader = drillSummaryBean.getReader();

		// ���������ܽ�
		DrillSummary drillSummarySave = saveDrillSummary(drillSummaryBean,
				userInfo);
		String summaryId = drillSummarySave.getId();

		// ���渽��
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), summaryId, DrillConstant.LP_DRILLSUMMARY,
				userInfo.getUserID());

		replyApproverDAO.deleteList(summaryId, DrillConstant.LP_DRILLSUMMARY);
		//�����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, summaryId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLSUMMARY);
		
		//���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, summaryId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLSUMMARY);
	}

	/**
	 * ���������ܽ��
	 * 
	 * @param drillSummaryBean�������ܽ�ʵ��Bean
	 * @param userInfo
	 * @return
	 */
	private DrillSummary saveDrillSummary(DrillSummaryBean drillSummaryBean,
			UserInfo userInfo) {
		DrillSummary drillSummary = new DrillSummary();
		try {
			BeanUtil.objectCopy(drillSummaryBean, drillSummary);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("DrillSummaryBeanת��ΪDrillSummary����������Ϣ��"
					+ e.getMessage());
			throw new ServiceException();
		}
		drillSummary.setCreator(userInfo.getUserID());
		drillSummary.setCreateTime(new Date());
		return drillSummaryDao.addDrillSummary(drillSummary);
	}

	/**
	 * ͨ�������ܽ�ID��������������������������ܽ�
	 * 
	 * @param summaryId�������ܽ�ID
	 * @return
	 */
	public Map getTaskPlanSummary(String summaryId) {
		String planId = drillSummaryDao.getPlanIdBySummaryId(summaryId);
		String taskId = drillPlanDao.getTaskIdByPlanId(planId);
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		DrillSummary drillSummary = drillSummaryDao.findByUnique("id",
				summaryId);
		List planEndList = drillPlanModifyDao.findByProperty("planId", planId);
		String conId = drillTaskConDao.getConIdBySummaryId(summaryId);
		String haveApproveInfo = drillSummaryDao.haveApproveInfo(summaryId);
		Map map = new HashMap();
		map.put("drillTask", drillTask);
		map.put("drillPlan", drillPlan);
		map.put("drillSummary", drillSummary);
		map.put("planEndList", planEndList);
		map.put("conId", conId);
		map.put("haveApproveInfo", haveApproveInfo);
		return map;
	}

	/**
	 * �༭�����ܽ�
	 * 
	 * @param drillSummaryBean�������ܽ�ʵ��Bean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void editDrillSummary(DrillSummaryBean drillSummaryBean,
			UserInfo userInfo, List<FileItem> files) throws ServiceException {
		String taskId = drillSummaryBean.getTaskId();
		String name = drillSummaryBean.getName();
		String approvers = drillSummaryBean.getApproveId();
		String reader = drillSummaryBean.getReader();
		String mobiles = drillSummaryBean.getMobiles();
		String[] readerPhones = drillSummaryBean.getReaderPhones();

		// ���������ܽ�
		saveDrillSummary(drillSummaryBean, userInfo);

		String summaryId = drillSummaryBean.getId();
		drillTaskConDao.setStateByContractorIdAndTaskId(userInfo.getDeptID(),
				taskId, DrillTaskCon.APPROVESUMMARY);

		// ���渽��
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), summaryId, DrillConstant.LP_DRILLSUMMARY,
				userInfo.getUserID());

		replyApproverDAO.deleteList(summaryId, DrillConstant.LP_DRILLSUMMARY);
		//�����������Ϣ
		replyApproverDAO.saveApproverOrReader(approvers, summaryId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLSUMMARY);
		
		//���泭������Ϣ
		replyApproverDAO.saveApproverOrReader(reader, summaryId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLSUMMARY);
		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, userInfo.getDeptID());
		// ������
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null && DrillWorkflowBO.CREATE_DRILL_SUMMARY_TASK.equals(task.getName())) {
			System.out.println("������������У�" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "approve");
			System.out.println("���������Ѿ��ύ��");
			
			//����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("�ƶ������ܽ�");//������̴�����˵��
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
		String content = "������������һ������Ϊ\"" + name + "\"�����ܽᵥ�ȴ�������ˡ�";
		sendMessage(content, mobiles);

		// ������ż�¼
		saveMessage(content, mobiles, summaryId,
				DrillConstant.LP_DRILLSUMMARY, ModuleCatalog.DRILL);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "������������һ������Ϊ\"" + name + "\"�����ܽᵥ�ȴ����Ĳ��ġ�";
				sendMessage(content, readerPhones[i]);

				// ������ż�¼
				saveMessage(content, readerPhones[i], summaryId, DrillConstant.LP_DRILLSUMMARY,
						ModuleCatalog.DRILL);
			}
		}
		
		
	}

	/**
	 * ��������ܽ�
	 * 
	 * @param drillSummaryBean�������ܽ�ʵ��Bean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void approveDrillSummary(DrillSummaryBean drillSummaryBean,
			UserInfo userInfo) throws ServiceException {
		String summaryId = drillSummaryBean.getId();
		String approveResult = drillSummaryBean.getApproveResult();
		String approveRemark = drillSummaryBean.getApproveRemark();
		String operator = drillSummaryBean.getOperator();
		String planId = drillSummaryDao.getPlanIdBySummaryId(summaryId);
		DrillPlan drillPlan = drillPlanDao.findByUnique("id", planId);
		String conId = drillPlan.getContractorId();
		String taskId = drillPlan.getTaskId();
		String creator = drillSummaryBean.getCreator();
		String phone = "";

		String approvers = drillSummaryBean.getApprovers();
		String content = "";
		
		//ȥ�����ĺ�ת��������˺��Լ�Ϊ�����˵����
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approvers + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = drillPlanDao.getApproverIds(summaryId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLSUMMARY, condition);
		// ���������Ϣ
		saveApproveInfo(summaryId,
				DrillConstant.LP_DRILLSUMMARY, userInfo.getUserID(),
				approveResult, approveRemark);

		if ("approve".equals(operator)) {
			phone = getPhoneByUserId(creator);
			if (DrillConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				drillTaskConDao.setStateByContractorIdAndTaskId(conId, taskId,
						DrillTaskCon.SUMMARYUNAPPROVE);
				drillSummaryDao.setUnapproveNumberBySummaryId(summaryId);

				// ���Ͷ�������
				content = "������������\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"�����ܽᵥδͨ����ˡ�";
			} else {
				drillTaskConDao.setStateByContractorIdAndTaskId(conId, taskId,
						DrillTaskCon.DRILLREMARK);

				// ���Ͷ�������
				content = "������������\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"�����ܽᵥ�Ѿ�ͨ����ˡ�";

			}
			
		} else {
			// ����ת������Ϣ
			replyApproverDAO.saveApproverOrReader(approvers, summaryId, DrillConstant.APPROVE_TRANSFER_MAN, DrillConstant.LP_DRILLSUMMARY);

			// ���Ͷ���
			content = "������������һ������Ϊ\""
					+ drillTaskDao.getTaskNameByTaskId(taskId)
					+ "\"�����ܽᵥ�ȴ�������ˡ�";
			phone = drillSummaryBean.getMobiles();
		}

		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, conId);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null
				&& DrillWorkflowBO.APPROVE_DRILL_SUMMARY_TASK.equals(task.getName())) {
			System.out.println("�����ܽ����ˣ�" + task.getName());
			if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", userInfo.getUserID());
				workflowBo.setVariables(task, "transition", "evaluate");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("�����ܽ����ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�����ܽ�����ͨ��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("evaluate");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(userInfo.getUserID());//�����һ�������˵ı��
				processHistoryBean.setObjectId(eid);//�������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//����������ʷ��Ϣ
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", creator);
				workflowBo.completeTask(task.getId(), "not_passed");
				//workflowBo.signal(processInstanceId, "guard_plan_execute_state", "summarize");
				System.out.println("�����ܽ���˲�ͨ����");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�����ܽ�������ͨ��");//������̴�����˵��
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
			if (SafeguardConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", approvers+","+approverIdsReader);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("�����ܽ��Ѿ�ת��");
				
				//����������ʷ
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("�����ܽ�ת��");//������̴�����˵��
				processHistoryBean.setTaskOutCome("transfer");//��ӹ�����������Ϣ
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(approvers);//�����һ�������˵ı��
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
		// ������ż�¼
		saveMessage(content, phone, summaryId,
				DrillConstant.LP_DRILLSUMMARY, ModuleCatalog.DRILL);
		
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
		List list = drillSummaryDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	public void readReply(UserInfo userInfo, String approverId, String summaryId) throws ServiceException{
		approverDAO.updateReader(approverId, summaryId, DrillConstant.LP_DRILLSUMMARY);
		
		DrillSummary drillSummary = drillSummaryDao.findByUnique("id", summaryId);
		String creator = drillSummary.getCreator();
		String eid = drillSummaryDao.getTaskConIdBySummaryId(summaryId);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		//����������ʷ
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		String nextOperateUserId = "";
		processHistoryBean.setProcessAction("�����ܽ����");//������̴�����˵��
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
