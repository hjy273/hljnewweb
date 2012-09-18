package com.cabletech.linepatrol.drill.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
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
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.drill.beans.DrillTaskBean;
import com.cabletech.linepatrol.drill.dao.DrillPlanModifyDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskConDao;
import com.cabletech.linepatrol.drill.dao.DrillTaskDao;
import com.cabletech.linepatrol.drill.module.DrillConstant;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.module.DrillTaskCon;
import com.cabletech.linepatrol.drill.workflow.DrillWorkflowBO;

@Service
@Transactional
public class DrillTaskBo extends EntityManager<DrillTask, String> {
	private static Logger logger = Logger
			.getLogger(DrillTaskBo.class.getName());

	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;
	@Autowired
	private DrillWorkflowBO workflowBo;

	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;

	@Override
	protected HibernateDao<DrillTask, String> getEntityDao() {
		return drillTaskDao;
	}

	@Resource(name = "drillTaskDao")
	private DrillTaskDao drillTaskDao;

	@Resource(name = "drillTaskConDao")
	private DrillTaskConDao drillTaskConDao;

	@Resource(name = "drillPlanModifyDao")
	private DrillPlanModifyDao drillPlanModifyDao;

	@Resource(name = "uploadFileService")
	private UploadFileService uploadFile;

	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;

	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;

	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;

	/**
	 * ������������
	 * 
	 * @param drillTaskBean
	 *            ����������Bean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void addDrillTask(DrillTaskBean drillTaskBean, UserInfo userInfo,
			List<FileItem> files) throws ServiceException {
		String contractorId = drillTaskBean.getContractorId();
		// String userId = drillTaskBean.getUserId();
		String mobileId = drillTaskBean.getMobileId();
		String conUser = drillTaskBean.getConUser();

		// ������������
		DrillTask drillTaskSave = saveDrillTask(drillTaskBean, userInfo, "save");

		// ���������������ά��ϵ
		String taskId = drillTaskSave.getId();
		saveDrillTaskCon(taskId, contractorId, DrillTaskCon.ADDPLAN);

		// �ϴ��ļ�
		saveFiles(files, ModuleCatalog.DRILL, userInfo.getRegionName(),
				drillTaskSave.getId(), DrillConstant.LP_DRILLTASK, userInfo
						.getUserID());
		// ��ô�ά��λ�û�ID
		String[] cons = contractorId.split(",");
		for (int i = 0; i < cons.length; i++) {
			String conId = cons[i].split(";")[0];
			String user = null;
			if (conUser == null || "".equals(conUser)) {
				user = drillTaskConDao.getUserIdByConId(conId);
			} else {
				user = drillTaskConDao.getUserStrByConId(conId, conUser);
			}
			String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, conId);
			// JBPM
			Map variables = new HashMap();
			variables.put("assignee", user);
			variables.put("transition", "create_drill_proj");
			workflowBo.createProcessInstance("drill", eid, variables);

			// ����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("���������ɷ�");// ������̴�����˵��
			processHistoryBean.setTaskOutCome("create_drill_proj");// ��ӹ�����������Ϣ
			processHistoryBean.initial(null, userInfo, "drill." + eid,
					ModuleCatalog.DRILL);
			processHistoryBean.setNextOperateUserId(user);// �����һ�������˵ı��
			processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		// ��ô�ά��λID
		String[] mobileIds = mobileId.split(",");
		for (int i = 0; i < mobileIds.length; i++) {
			String content = "������������һ������Ϊ\"" + drillTaskSave.getName()
					+ "\"�������񵥵ȴ����Ĵ�������";
			sendMessage(content, mobileIds[i]);
			saveMessage(content, mobileIds[i], taskId,
					DrillConstant.LP_DRILLPLAN, ModuleCatalog.DRILL);
		}

	}

	/**
	 * ������������
	 * 
	 * @param drillTaskBean
	 * @param userInfo
	 * @param files
	 * @throws ServiceException
	 */
	public void perfectDrillTask(DrillTaskBean drillTaskBean,
			UserInfo userInfo, List<FileItem> files) throws ServiceException {
		String contractorId = drillTaskBean.getContractorId();
		String mobileId = drillTaskBean.getMobileId();
		String conUser = drillTaskBean.getConUser();

		// ������������
		DrillTask drillTaskSave = saveDrillTask(drillTaskBean, userInfo, "save");

		// ���������������ά��ϵ
		String taskId = drillTaskSave.getId();
		saveDrillTaskCon(taskId, contractorId, DrillTaskCon.ADDPLAN);

		// �ϴ��ļ�
		saveFiles(files, ModuleCatalog.DRILL, userInfo.getRegionName(),
				drillTaskSave.getId(), DrillConstant.LP_DRILLTASK, userInfo
						.getUserID());
		// ��ô�ά��λ�û�ID
		String[] cons = contractorId.split(",");
		for (int i = 0; i < cons.length; i++) {
			String conId = cons[i].split(";")[0];
			String user = drillTaskConDao.getUserStrByConId(conId, conUser);
			String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, conId);
			// JBPM
			Map variables = new HashMap();
			variables.put("assignee", user);
			variables.put("transition", "create_drill_proj");
			workflowBo.createProcessInstance("drill", eid, variables);

			// ����������ʷ
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("���������ɷ�");// ������̴�����˵��
			processHistoryBean.setTaskOutCome("create_drill_proj");// ��ӹ�����������Ϣ
			processHistoryBean.initial(null, userInfo, "drill." + eid,
					ModuleCatalog.DRILL);
			processHistoryBean.setNextOperateUserId(user);// �����һ�������˵ı��
			processHistoryBean.setObjectId(eid);// �������ʵ����Key��Ϣ�����飺һ�ɶ�ʱʹ��ÿ�����̵�Key��Ϣ������������ʱʹ�ø����̵�Key��Ϣ
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);// ����������ʷ��Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		// ��ô�ά��λID
		String[] mobileIds = mobileId.split(",");
		for (int i = 0; i < mobileIds.length; i++) {
			String content = "������������һ������Ϊ\"" + drillTaskSave.getName()
					+ "\"�������񵥣�������ʱ����";
			sendMessage(content, mobileIds[i]);
			saveMessage(content, mobileIds[i], taskId,
					DrillConstant.LP_DRILLPLAN, ModuleCatalog.DRILL);
		}
	}

	/**
	 * ɾ����ʱ��������
	 * 
	 * @param taskId
	 * @throws ServiceException
	 */
	public void deleteTempTask(String taskId) throws ServiceException {
		// ɾ��������Ϣ
		List affixIds = drillTaskDao.getAffixIdByTaskId(taskId);
		for (Iterator iterator = affixIds.iterator(); iterator.hasNext();) {
			String affixId = (String) iterator.next();
			uploadFile.delById(affixId);
		}
		drillTaskConDao.deleteTaskConByTaskId(taskId);
		drillTaskDao.delete(taskId);
	}

	/**
	 * ��ʱ������������
	 * 
	 * @param drillTaskBean
	 * @param userInfo
	 * @param files
	 * @throws ServiceException
	 */
	public void tempSaveDrillTask(DrillTaskBean drillTaskBean,
			UserInfo userInfo, List<FileItem> files) throws ServiceException {
		String contractorId = drillTaskBean.getContractorId();

		// ������������
		DrillTask drillTaskSave = saveDrillTask(drillTaskBean, userInfo,
				"tempSave");

		// ���������������ά��ϵ
		String taskId = drillTaskSave.getId();
		saveDrillTaskCon(taskId, contractorId, "0");

		// �ϴ��ļ�
		saveFiles(files, ModuleCatalog.DRILL, userInfo.getRegionName(),
				drillTaskSave.getId(), DrillConstant.LP_DRILLTASK, userInfo
						.getUserID());
	}

	/**
	 * ��ô��������������б�
	 * 
	 * @param creator
	 * @return
	 */
	public List perfectDrillTaskList(String creator) {
		return drillTaskDao.perfectDrillTaskList(creator);
	}

	/**
	 * ������������ǰ��������
	 * 
	 * @param taskId
	 * @return
	 */
	public Map perfectDrillTaskForm(String taskId) {
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		String[] userIds = drillTaskConDao.getConUserIdsByTaskId(taskId);
		Map map = new HashMap();
		map.put("drillTask", drillTask);
		map.put("userIds", userIds);
		return map;
	}

	/**
	 * �鿴��������
	 * 
	 * @param taskId
	 *            ����������ID
	 * @return����������ʵ��
	 */
	public Map viewDrillTask(String taskId, String contractorId) {
		DrillTask drillTask = drillTaskDao.findByUnique("id", taskId);
		String cancelUserName = userInfoDao.getUserName(drillTask
				.getCancelUserId());
		drillTask.setCancelUserName(cancelUserName);
		String conId = drillTaskConDao.getConIdByTaskIdAndConId(taskId,
				contractorId);
		Map map = new HashMap();
		map.put("drillTask", drillTask);
		map.put("conId", conId);
		return map;
	}

	/**
	 * ������������
	 * 
	 * @param drillTaskBean
	 * @param userInfo
	 * @return
	 */
	private DrillTask saveDrillTask(DrillTaskBean drillTaskBean,
			UserInfo userInfo, String saveFlag) {
		DrillTask drillTask = new DrillTask();
		try {
			BeanUtil.objectCopy(drillTaskBean, drillTask);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("DrillTaskBeanת��ΪDrillTask����������Ϣ��" + e.getMessage());
			throw new ServiceException();
		}
		if ("save".equals(saveFlag)) {
			drillTask.setSaveFlag("0");
		} else {
			drillTask.setSaveFlag("1");
		}
		drillTask.setDrillType(DrillTask.DRILLTYPE_MOBILE);
		drillTask.setCreator(userInfo.getUserID());
		drillTask.setCreateTime(new Date());
		return drillTaskDao.addDrillTask(drillTask);
	}

	/**
	 * ���������������ά��ϵ
	 * 
	 * @param taskId
	 *            ����������ID
	 * @param contractorId
	 *            ����άID
	 * @param state
	 *            ��״̬
	 */
	private void saveDrillTaskCon(String taskId, String contractorId,
			String state) {

		if (contractorId != null && !"".equals(contractorId)) {
			drillTaskConDao.deleteTaskConByTaskId(taskId);
			String[] conId = contractorId.split(",");
			for (int i = 0; i < conId.length; i++) {
				// drillTaskConDao.delteTaskCon(taskId, conId[i]);
				DrillTaskCon drillTaskCon = new DrillTaskCon();
				drillTaskCon.setDrillId(taskId);
				drillTaskCon.setContractorId(conId[i]);
				drillTaskCon.setState(state);
				drillTaskConDao.save(drillTaskCon);
			}
		}
	}

	/**
	 * ��ô�������
	 * 
	 * @param taskName
	 * @param userInfo
	 *            ���û�ID
	 * @param condition
	 *            ����ѯ����
	 * @return�����������б�
	 */
	public List getAgentList(UserInfo userInfo, String condition,
			String taskName) {
		String assignee = userInfo.getUserID();
		List list = workflowBo.queryForHandleListBean(assignee, condition,
				taskName);
		List list2 = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				Object planId = bean.get("plan_id");
				Object summaryId = bean.get("summary_id");
				String conState = (String) bean.get("con_state");
				String modifyId = (String) bean.get("modify_id");
				String workflow = (String) bean.get("flow_state");
				logger.info("----------------workflow:" + workflow);
				if ("".equals(modifyId) || modifyId == null) {
					if (summaryId != null
							&& !"create_drill_summary_task".equals(workflow)) {

						// if()
						/*
						 * if (planId != null) { boolean read =
						 * approverDAO.isReadOnly((String) planId, assignee,
						 * "LP_DRILLPLAN"); bean.set("isread", "" + read);
						 * if(read){ if(judgeFinishRead((String)planId,
						 * DrillConstant.LP_DRILLPLAN, assignee)){
						 * list2.add(bean); } }else{ list2.add(bean); }
						 * continue; }
						 */
						/*
						 * bean.set("isread", "false"); list2.add(bean);
						 */

						boolean read = approverDAO
								.isReadOnly((String) summaryId, assignee,
										"LP_DRILLSUMMARY");
						bean.set("isread", "" + read);
						if (read) {
							if (judgeFinishRead((String) summaryId,
									DrillConstant.LP_DRILLSUMMARY, assignee)) {
								list2.add(bean);
							}
						} else {
							list2.add(bean);
						}
						continue;
					}
					if (planId != null) {
						boolean read = approverDAO.isReadOnly((String) planId,
								assignee, "LP_DRILLPLAN");
						bean.set("isread", "" + read);
						if (read) {
							if (judgeFinishRead((String) planId,
									DrillConstant.LP_DRILLPLAN, assignee)) {
								list2.add(bean);
							}
						} else {
							list2.add(bean);
						}
						continue;
					}
					bean.set("isread", "false");
					list2.add(bean);
				} else {
					boolean read = approverDAO.isReadOnly(modifyId, assignee,
							DrillConstant.LP_DRILLPLAN_MODIFY);
					bean.set("isread", "" + read);
					if (judgeFinishRead(modifyId,
							DrillConstant.LP_DRILLPLAN_MODIFY, assignee)) {
						if (userInfo.getDeptype().equals("1")) {
							bean.set("con_state", "����������");
							list2.add(bean);
						} else {
							String flag = drillPlanModifyDao
									.whetherCanSummary((String) planId);
							bean.set("page_flag", flag);
							if ("yes".equals(flag)) {
								bean.set("con_state", "������������");
							} else {
								bean.set("con_state", "�ƶ��ܽ�");
								list2.add(bean);
							}
						}
					}
				}
			}
		}
		return list2;
	}

	/**
	 * �ж��Ƿ��ѱ�����
	 * 
	 * @param objectId
	 * @param objectType
	 * @param userId
	 * @return
	 */
	private boolean judgeFinishRead(String objectId, String objectType,
			String userId) {
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
		// logger.info("*******flag:::" + flag + " objectId::" + objectId +
		// " objectType::" + objectType);
		return flag;
	}

	/**
	 * ���ַ���ת��ΪList
	 * 
	 * @param str
	 *            ����Ҫת�����ַ���
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
	 * @param files
	 *            ���ϴ��ĸ���
	 * @param module
	 *            ��ģ�鳣��
	 * @param regionName
	 *            ����������
	 * @param entityId
	 *            ������������ʵ��ID
	 * @param entityType
	 *            �����������ı���
	 * @param uploader
	 *            �������ϴ���
	 */
	public void saveFiles(List<FileItem> files, String module,
			String regionName, String entityId, String entityType,
			String uploader) {
		uploadFile.saveFiles(files, module, regionName, entityId, entityType,
				uploader);
	}

	/**
	 * �����������Ϣ
	 * 
	 * @param approvers
	 *            �������
	 * @param approveType
	 *            ���������
	 * @param entityId
	 *            ��ʵ��ID
	 * @param objectType
	 *            ��ʵ������ı���
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
	 * @param entityId
	 *            ��ʵ��ID
	 * @param entityType
	 *            ��ʵ������
	 */
	public void deleteApprove(String entityId, String entityType) {
		approverDAO.deleteList(entityId, entityType);
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param entityId
	 *            ��ʵ��ID
	 * @param entityType
	 *            ��ʵ�����
	 * @param approverId
	 *            �������ID
	 * @param approveResult
	 *            ����˽��
	 * @param approveRemark
	 *            ��������
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
	 * @param content
	 *            ����������
	 * @param mobiles
	 *            �����ն����ֻ�����
	 */
	public void sendMessage(String content, String mobiles) {
		if (mobiles != null && !"".equals(mobiles)) {
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}

	/**
	 * ������ż�¼
	 * 
	 * @param content
	 *            ����������
	 * @param mobiles
	 *            �����ն����ֻ�����
	 * @param entityId
	 *            ��ʵ��ID
	 * @param entityType
	 *            ��ʵ������
	 * @param entityModule
	 *            ��ʵ��ģ��
	 */
	public void saveMessage(String content, String mobiles, String entityId,
			String entityType, String entityModule) {
		if (mobiles != null && !"".equals(mobiles)) {
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
	 * @param userId
	 *            ���û�ID
	 * @return�������û��ֻ�����
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = drillTaskDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}

	public List queryForHandleDrillTaskNum(String condition, UserInfo userInfo) {
		String assignee = "";
		String userId = userInfo.getUserID();
		assignee = userId;
		List list = new ArrayList();
		List handleTaskList = workflowBo.queryForHandleListBean(assignee,
				condition, "");
		DynaBean bean;
		int waitCreateDrillTaskNum = 0;
		int waitApproveDrillTaskNum = 0;
		int waitChangeDrillTaskNum = 0;
		int waitApproveChangeDrillTaskNum = 0;
		int waitCreateDrillSummaryTaskNum = 0;
		int waitApproveDrillSummaryTaskNum = 0;
		int waitEvaluateTaskNum = 0;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			bean = (DynaBean) handleTaskList.get(i);
			if (bean != null) {
				if (bean.get("flow_state") != null) {
					if (DrillWorkflowBO.CREATE_DRILL_PROJ_TASK.equals(bean
							.get("flow_state"))) {
						waitCreateDrillTaskNum++;
					}
					if (DrillWorkflowBO.APPROVE_DRILL_PROJ_TASK.equals(bean
							.get("flow_state"))) {
						boolean read = approverDAO.isReadOnly((String) bean
								.get("plan_id"), assignee, "LP_DRILLPLAN");
						if (read) {
							if (judgeFinishRead((String) bean.get("plan_id"),
									DrillConstant.LP_DRILLPLAN, userId)) {
								waitApproveDrillTaskNum++;
							}
						} else {
							waitApproveDrillTaskNum++;
						}
					}
					if (DrillWorkflowBO.CHANGE_DRILL_PROJ_TASK.equals(bean
							.get("flow_state"))) {
						waitChangeDrillTaskNum++;
					}
					if (DrillWorkflowBO.APPROVE_CHANGE_DRILL_PROJ_TASK
							.equals(bean.get("flow_state"))) {
						boolean read = approverDAO.isReadOnly((String) bean
								.get("modify_id"), assignee,
								DrillConstant.LP_DRILLPLAN_MODIFY);
						if (read) {
							if (judgeFinishRead((String) bean.get("modify_id"),
									DrillConstant.LP_DRILLPLAN_MODIFY, userId)) {
								waitApproveChangeDrillTaskNum++;
							}
						} else {
							waitApproveChangeDrillTaskNum++;
						}
					}
					if (DrillWorkflowBO.CREATE_DRILL_SUMMARY_TASK.equals(bean
							.get("flow_state"))) {
						if ("2".equals(userInfo.getDeptype())) {
							String flag = drillPlanModifyDao
									.whetherCanSummary((String) bean
											.get("plan_id"));
							if ("yes".equals(flag)) {
								continue;
							} else {
								waitCreateDrillSummaryTaskNum++;
							}
						} else {
							waitCreateDrillSummaryTaskNum++;
						}
					}
					if (DrillWorkflowBO.APPROVE_DRILL_SUMMARY_TASK.equals(bean
							.get("flow_state"))) {
						boolean read = approverDAO
								.isReadOnly((String) bean.get("summary_id"),
										assignee, "LP_DRILLSUMMARY");
						if (read) {
							if (judgeFinishRead(
									(String) bean.get("summary_id"),
									DrillConstant.LP_DRILLSUMMARY, userId)) {
								waitApproveDrillSummaryTaskNum++;
							}
						} else {
							waitApproveDrillSummaryTaskNum++;
						}
					}
					if (DrillWorkflowBO.EVALUATE_TASK.equals(bean
							.get("flow_state"))) {
						waitEvaluateTaskNum++;
					}
				}
			}
		}
		list.add(waitCreateDrillTaskNum);
		list.add(waitApproveDrillTaskNum);
		list.add(waitChangeDrillTaskNum);
		list.add(waitApproveChangeDrillTaskNum);
		list.add(waitCreateDrillSummaryTaskNum);
		list.add(waitApproveDrillSummaryTaskNum);
		list.add(waitEvaluateTaskNum);
		return list;
	}

	/**
	 * �Ѱ칤���б�
	 * 
	 * @param userInfo
	 * @param taskName
	 * @param taskOutCome
	 * @return
	 */
	public List queryFinishHandledDrillList(UserInfo userInfo, String taskName,
			String taskOutCome) {
		String assignee = userInfo.getUserID();
		String condition = "";
		// ConditionGenerate.getUserQueryCondition(userInfo);
		condition += " and process.operate_user_id='" + assignee + "' ";
		if ("2".equals(userInfo.getDeptype())) {
			condition += " and taskcon.contractor_id='" + userInfo.getDeptID()
					+ "' ";
		}
		List list = new ArrayList();
		List prevList = new ArrayList();
		List handledTaskList = drillTaskDao
				.queryFinishHandledDrillList(condition);
		DynaBean taskBean;
		DynaBean tmpBean;
		boolean flag = true;
		for (int i = 0; handledTaskList != null && i < handledTaskList.size(); i++) {
			flag = true;
			taskBean = (DynaBean) handledTaskList.get(i);
			if (taskBean != null) {
				for (int j = 0; prevList != null && j < prevList.size(); j++) {
					tmpBean = (DynaBean) prevList.get(j);
					if (tmpBean != null
							&& tmpBean.get("con_id").equals(
									taskBean.get("con_id"))) {
						flag = false;
						break;
					}
				}
				if (flag) {
					prevList.add(taskBean);
				}
			}
		}
		if (taskName != null && !"".equals(taskName)) {
			for (int i = 0; prevList != null && i < prevList.size(); i++) {
				tmpBean = (DynaBean) prevList.get(i);
				if (judgeInStr((String) tmpBean.get("handled_task_name"),
						taskName)) {
					if (taskOutCome != null && !"".equals(taskOutCome)) {
						if (judgeInStr((String) tmpBean.get("task_out_come"),
								taskOutCome)) {
							list.add(tmpBean);
						}
					} else {
						list.add(tmpBean);
					}
				}
			}
		} else {
			list = prevList;
		}
		return list;
	}

	private boolean judgeInStr(String value, String compareStr) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (compareStr != null) {
			if (compareStr.indexOf(",") != -1) {
				String[] str = compareStr.split(",");
				for (int i = 0; str != null && i < str.length; i++) {
					if (str[i] != null && str[i].equals(value)) {
						flag = true;
						break;
					}
				}
			} else {
				if (compareStr.equals(value)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * ���������ɵ�״̬�������������
	 * 
	 * @param condition
	 * @param userInfo
	 * @return
	 */
	public List queryForHandledDrillNumList(String condition, UserInfo userInfo) {
		String assignee = userInfo.getUserID();
		List list = new ArrayList();
		List handleTaskList = queryFinishHandledDrillList(userInfo, "", "");
		DynaBean bean;
		int createDrillProjNum = 0;
		int approveDrillProjNum = 0;
		int changeDrillProjNum = 0;
		int approveChangeDrillProjNum = 0;
		int createDrillSummaryNum = 0;
		int approveDrillSummaryNum = 0;
		int evaluateTaskNum = 0;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			bean = (DynaBean) handleTaskList.get(i);
			if (bean != null) {
				if (bean.get("handled_task_name") != null) {
					// ���������ɵ�״̬�������������
					if (DrillWorkflowBO.CREATE_DRILL_PROJ_TASK.equals(bean
							.get("handled_task_name"))) {
						createDrillProjNum++;
					}
					if (DrillWorkflowBO.APPROVE_DRILL_PROJ_TASK.equals(bean
							.get("handled_task_name"))) {
						approveDrillProjNum++;
					}
					if (DrillWorkflowBO.CHANGE_DRILL_PROJ_TASK.equals(bean
							.get("handled_task_name"))) {
						changeDrillProjNum++;
					}
					if (DrillWorkflowBO.APPROVE_CHANGE_DRILL_PROJ_TASK
							.equals(bean.get("handled_task_name"))) {
						approveChangeDrillProjNum++;
					}
					if (DrillWorkflowBO.CREATE_DRILL_SUMMARY_TASK.equals(bean
							.get("handled_task_name"))) {
						createDrillSummaryNum++;
					}
					if (DrillWorkflowBO.APPROVE_DRILL_SUMMARY_TASK.equals(bean
							.get("handled_task_name"))) {
						approveDrillSummaryNum++;
					}
					if (DrillWorkflowBO.EVALUATE_TASK.equals(bean
							.get("handled_task_name"))) {
						evaluateTaskNum++;
					}
				}
			}
		}
		list.add(createDrillProjNum);
		list.add(approveDrillProjNum);
		list.add(changeDrillProjNum);
		list.add(approveChangeDrillProjNum);
		list.add(createDrillSummaryNum);
		list.add(approveDrillSummaryNum);
		list.add(evaluateTaskNum);
		return list;
	}

	public void cancelDrillTask(DrillTaskBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		DrillTask drillTask = drillTaskDao.findByUnique("id", bean.getId());
		drillTask.setCancelReason(bean.getCancelReason());
		drillTask.setCancelTime(new Date());
		drillTask.setCancelUserId(userInfo.getUserID());
		drillTask.setDrillState(DrillTask.CANCELED_STATE);
		drillTaskDao.save(drillTask);
		List drillConList = drillTaskConDao.getIdsByConIdAndTaskId(drillTask
				.getId());
		DrillTaskCon drillTaskCon = null;
		String processInstanceId = "";
		ProcessHistoryBean processHistoryBean;
		for (int i = 0; drillConList != null && i < drillConList.size(); i++) {
			drillTaskCon = (DrillTaskCon) drillConList.get(i);
			processInstanceId = DrillWorkflowBO.DRILL_WORKFLOW + "."
					+ drillTaskCon.getId();
			processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(null, userInfo, processInstanceId,
					ModuleCatalog.DRILL);
			processHistoryBean.setHandledTaskName("any");
			processHistoryBean.setNextOperateUserId("");
			processHistoryBean.setObjectId(drillTaskCon.getId());
			processHistoryBean.setProcessAction("����ȡ��");
			processHistoryBean.setTaskOutCome("cancel");
			processHistoryBO.saveProcessHistory(processHistoryBean);
			workflowBo.endProcessInstance(processInstanceId);
		}
	}
}
