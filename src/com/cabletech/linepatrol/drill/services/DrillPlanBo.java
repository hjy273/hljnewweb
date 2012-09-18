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
	 * 查看演练任务
	 * 
	 * @param taskId：演练任务ID
	 * @return：演练任务实体
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
	 * 添加演练计划 添加演练计划前先判断是否已经制定了演练任务，若没有制定演练任务，先制定演练任务，后添加演练计划。若已制定，直接添加演练计划。
	 * 判断依据是任务ID是否存在
	 * 
	 * @param drillPlanBean：演练计划Bean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void addDrillPlan(DrillPlanBean drillPlanBean, UserInfo userInfo,
			List<FileItem> files) throws ServiceException {

		// 获取基本数据
		String taskId = drillPlanBean.getTaskId();
		String userId = userInfo.getUserID();
		String deptId = userInfo.getDeptID();
		String approvers = drillPlanBean.getApproveId();
		String reader = drillPlanBean.getReader();
		String mobiles = drillPlanBean.getMobile();
		String name = drillPlanBean.getName();
		String drillTaskConId = "";
		String[] readerPhones = drillPlanBean.getReaderPhones();

		// 根据taskId是否为空判断演练任务是否制定，若为空表示未制定。先制定演练任务
		DrillPlan drillPlanSave = new DrillPlan();
		if (taskId == null || "".equals(taskId)) {
			// 如果是代维人员自己建立演练方案，先建立演练计划
			// 保存演练任务
			DrillTask drillTask = new DrillTask();
			try {
				BeanUtil.objectCopy(drillPlanBean, drillTask);
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("转换异常：" + e1.getMessage());
				throw new ServiceException();
			}
			drillTask.setDrillType(DrillTask.DRILLTYPE_CON);
			drillTask.setBeginTime(DateUtil.parseDateTime(drillPlanBean.getRealBeginTime()));
			drillTask.setEndTime(DateUtil.parseDateTime(drillPlanBean.getRealEndTime()));
			drillTask.setLocale(drillPlanBean.getAddress());
			drillTask.setDemand(drillPlanBean.getScenario());
			DrillTask drillTaskSave = saveDrillTask(drillTask, userId);

			// 保存演练任务与代维关系表
			DrillTaskCon drillTaskCon = saveDrillTaskCon(deptId, drillTaskSave.getId(),
					DrillTaskCon.APPROVEPLAN);
			drillTaskConId = drillTaskCon.getId();

			// 保存演练计划
			drillPlanBean.setTaskId(drillTaskSave.getId());
			drillPlanSave = saveDrillPlan(drillPlanBean, userId, deptId);
		} else {
			// 演练计划已建立。建立演练方案
			drillPlanSave = saveDrillPlan(drillPlanBean, userId, deptId);

			drillTaskConDao.setStateByContractorIdAndTaskId(deptId, taskId,
					DrillTaskCon.APPROVEPLAN);
		}

		// 保存附件
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), drillPlanSave.getId(),
				DrillConstant.LP_DRILLPLAN, userInfo.getUserID());

		// 保存审核人信息
		String planId = drillPlanSave.getId();
		
		//保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, planId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLPLAN);
		
		//保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, planId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN);
		//代维自己制定计划
		if (taskId == null || "".equals(taskId)) {
			Map variables = new HashMap();
			variables.put("assignee",approvers+","+reader);
			variables.put("transition", "apporve");
			workflowBo.createProcessInstance("drill", drillTaskConId, variables);
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("制定演练方案");//添加流程处理动作说明
			processHistoryBean.setTaskOutCome("apporve");//添加工作流流向信息
			processHistoryBean.initial(null, userInfo, "drill."+drillTaskConId,ModuleCatalog.DRILL);
			processHistoryBean.setNextOperateUserId(approvers+","+reader);//添加下一步处理人的编号
			processHistoryBean.setObjectId(drillTaskConId);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}else{
			//代维认领线维分配任务并制定计划
			String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, deptId);
			Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),eid);
			if (task != null && DrillWorkflowBO.CREATE_DRILL_PROJ_TASK.equals(task.getName())) {
				System.out.println("演练计划添加中：" + task.getName());
				workflowBo.setVariables(task, "assignee", approvers+","+reader);
				workflowBo.completeTask(task.getId(), "approve");
				System.out.println("演练计划已经提交！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("制定演练方案");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("approve");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(approvers+","+reader);//添加下一步处理人的编号
				processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
		
		
		// 发送短信
		String content = "【演练】您有一个名称为\"" + name + "\"演练方案单等待您的审核。";
		sendMessage(content, mobiles);

		// 保存短信记录
		saveMessage(content, mobiles, planId,
				DrillConstant.LP_DRILLPLAN, ModuleCatalog.DRILL);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "【演练】您有一个名称为\"" + name + "\"演练方案单等待您的查阅。";
				sendMessage(content, readerPhones[i]);

				// 保存短信记录
				saveMessage(content, readerPhones[i], planId, DrillConstant.LP_DRILLPLAN,
						ModuleCatalog.DRILL);
			}
		}
		
		
	}
	
	public void tempSaveDrillPlan(DrillPlanBean drillPlanBean, UserInfo userInfo,
			List<FileItem> files) throws ServiceException {

		// 获取基本数据
		String userId = userInfo.getUserID();
		String deptId = userInfo.getDeptID();
		String approvers = drillPlanBean.getApproveId();
		String reader = drillPlanBean.getReader();

		DrillPlan drillPlanSave = new DrillPlan();
		drillPlanSave = saveDrillPlan(drillPlanBean, userId, deptId);

		// 保存附件
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), drillPlanSave.getId(),
				DrillConstant.LP_DRILLPLAN, userInfo.getUserID());
		
		// 保存审核人信息
		String planId = drillPlanSave.getId();
		
		replyApproverDAO.deleteList(planId, DrillConstant.LP_DRILLPLAN);
		//保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, planId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLPLAN);
		
		//保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, planId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN);

	}

	/**
	 * 通过计划ID活动执行任务和执行计划
	 * 
	 * @param planId：演练计划ID
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
	 * 编辑演练计划
	 * 
	 * @param drillPlanBean：演练计划实体Bean
	 * @param userInfo
	 * @param taskCreator：演练任务创建人
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
			logger.error("drillPlanBean转换为drillTask出错，出错信息：" + e.getMessage());
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
		// 保存附件
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), planId, DrillConstant.LP_DRILLPLAN, userInfo
				.getUserID());

		replyApproverDAO.deleteList(planId, DrillConstant.LP_DRILLPLAN);
		
		//保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, planId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLPLAN);
		
		//保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, planId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN);
		String eid = drillTaskConDao.getIdByConIdAndTaskId(drillPlanBean.getTaskId(), userInfo.getDeptID());
		// 工作流
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null && DrillWorkflowBO.CREATE_DRILL_PROJ_TASK.equals(task.getName())) {
			System.out.println("演练计划添加中：" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "approve");
			System.out.println("演练计划已经提交！");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("制定演练方案");//添加流程处理动作说明
			processHistoryBean.setTaskOutCome("approve");//添加工作流流向信息
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
			processHistoryBean.setNextOperateUserId(approvers+","+reader);//添加下一步处理人的编号
			processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		// 发送短信
		String content = "【演练】您有一个名称为\"" + drillTask.getName() + "\"演练方案单等待您的审核。";
		sendMessage(content, mobiles);

		// 保存短信记录
		saveMessage(content, mobiles, planId,
				DrillConstant.LP_DRILLPLAN, ModuleCatalog.DRILL);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "【演练】您有一个名称为\"" + drillTask.getName() + "\"演练方案单等待您的查阅。";
				sendMessage(content, readerPhones[i]);

				// 保存短信记录
				saveMessage(content, readerPhones[i], planId, DrillConstant.LP_DRILLPLAN,
						ModuleCatalog.DRILL);
			}
		}
		
		
	}

	/**
	 * 演练计划审核 当operator为approve是表示审核，为transfer表示转审
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
		
		//去除已阅和转审给抄送人和自己为抄送人的情况
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approves + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = drillPlanDao.getApproverIds(planId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLPLAN, condition);
		
		// 保存审核信息
		saveApproveInfo(planId, DrillConstant.LP_DRILLPLAN,
				userId, approveResult, approveRemark);

		if ("approve".equals(operator)) {
			phone = getPhoneByUserId(creator);
			if (DrillConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				// 改变状态
				drillTaskConDao.setStateByContractorIdAndTaskId(conId, taskId,
						DrillTaskCon.PLANUNAPPROVE);
				// 改变未通过次数
				drillPlanDao.setUnapproveNumberByPlanId(planId);

				// 发送短信内容
				content = "【演练】您的\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"演练方案单未通过审核。";
				drillPlanDao.addDeadline(planId, deadline);
			} else {

				// 改变状态
				drillTaskConDao.setStateByContractorIdAndTaskId(conId, taskId,
						DrillTaskCon.ADDSUMMARY);
				
				// 发送短信内容
				content = "【演练】您的\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"演练方案单已经通过审核。";
				//添加演练总结填写期限
				drillPlanDao.addDeadline(planId, deadline);
			}
			
		} else {
			// 保存转审人信息
			replyApproverDAO.saveApproverOrReader(approves, planId, DrillConstant.APPROVE_TRANSFER_MAN, DrillConstant.LP_DRILLPLAN);

			// 发送短信
			content = "【演练】您有一个名称为\""
					+ drillTaskDao.getTaskNameByTaskId(taskId)
					+ "\"演练方案单等待您的审核。";
			phone = drillPlanBean.getMobile();
		}
		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, conId);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null
				&& DrillWorkflowBO.APPROVE_DRILL_PROJ_TASK.equals(task.getName())) {
			System.out.println("演练方案待审核：" + task.getName());
			if (DrillConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", creator);
				workflowBo.setVariables(task, "transition", "passed");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("演练方案审核通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("演练方案审批通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("passed");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(creator);//添加下一步处理人的编号
				processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (DrillConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", creator);
				workflowBo.completeTask(task.getId(), "not_passed");
				System.out.println("演练方案审核不通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("演练方案审批不通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("not_passed");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(creator);//添加下一步处理人的编号
				processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (DrillConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", approves+","+approverIdsReader);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("演练方案已经转审！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("演练方案转审");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("transfer");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(approves);//添加下一步处理人的编号
				processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
		}
		
		// 发送短信
		sendMessage(content, phone);
		// 保存发送短信记录
		saveMessage(content, phone, planId,
				DrillConstant.LP_DRILLPLAN, ModuleCatalog.DRILL);
		
		
	}

	/**
	 * 保存演练任务
	 * 
	 * @param drillTask：演练任务实体
	 * @param creator：演练任务创建人
	 * @return
	 */
	private DrillTask saveDrillTask(DrillTask drillTask, String creator) {
		drillTask.setDeadline(null);
		drillTask.setCreator(creator);
		drillTask.setCreateTime(new Date());
		return drillTaskDao.addDrillTask(drillTask);
	}

	/**
	 * 保存演练任务与代维关系表
	 * 
	 * @param conId：代维ID
	 * @param taskId：演练任务ID
	 * @param state：状态
	 */
	private DrillTaskCon saveDrillTaskCon(String conId, String taskId, String state) {
		DrillTaskCon drillTaskCon = new DrillTaskCon();
		drillTaskCon.setContractorId(conId);
		drillTaskCon.setDrillId(taskId);
		drillTaskCon.setState(state);
		return drillTaskConDao.saveDrillTaskCon(drillTaskCon);
	}

	/**
	 * 保存演练计划
	 * 
	 * @param drillPlanBean：演练计划实体Bean
	 * @param creator：演练计划创建人
	 * @param conId：代维ID
	 * @return
	 */
	private DrillPlan saveDrillPlan(DrillPlanBean drillPlanBean,
			String creator, String conId) {
		DrillPlan drillPlan = new DrillPlan();
		try {
			BeanUtil.objectCopy(drillPlanBean, drillPlan);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("drillPlanBean转换为drillPlan出错：" + e.getMessage());
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
	 * 将字符串转换为List
	 * 
	 * @param str：需要转换的字符串
	 * @return：list
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
	 * 保存文件
	 * 
	 * @param files：上传的附件
	 * @param module：模块常量
	 * @param regionName：区域名称
	 * @param entityId：附件关联的实体ID
	 * @param entityType：附件关联的表名
	 * @param uploader：附件上传人
	 */
	public void saveFiles(List<FileItem> files,
			String module, String regionName, String entityId,
			String entityType, String uploader) {
		uploadFile.saveFiles(files, module, regionName , entityId, entityType, uploader);
	}

	/**
	 * 保存审核人信息
	 * 
	 * @param approvers：审核人
	 * @param approveType：审核类型
	 * @param entityId：实体ID
	 * @param objectType：实体关联的表名
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
	 * 删除审核信息
	 * 
	 * @param entityId：实体ID
	 * @param entityType：实体类型
	 */
	public void deleteApprove(String entityId, String entityType) {
		approverDAO.deleteList(entityId, entityType);
	}

	/**
	 * 保存审核信息
	 * 
	 * @param entityId：实体ID
	 * @param entityType：实体表名
	 * @param approverId：审核人ID
	 * @param approveResult：审核结果
	 * @param approveRemark：审核意见
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
	 * 发送短信
	 * 
	 * @param content：短信内容
	 * @param mobiles：接收短信手机号码
	 */
	public void sendMessage(String content, String mobiles) {
		if(mobiles != null && !"".equals(mobiles)){
			List<String> mobileList = StringUtils.string2List(mobiles, ",");
			super.sendMessage(content, mobileList);
		}
	}

	/**
	 * 保存短信记录
	 * 
	 * @param content：短信内容
	 * @param mobiles：接收短信手机号码
	 * @param entityId：实体ID
	 * @param entityType：实体类型
	 * @param entityModule：实体模型
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
	 * 通过用户ID查询用户的手机号码
	 * 
	 * @param userId：用户ID
	 * @return：返回用户手机号码
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
		//保存流程历史
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("演练方案查阅");//添加流程处理动作说明
		processHistoryBean.setTaskOutCome("");//添加工作流流向信息
		processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
		processHistoryBean.setNextOperateUserId("");//添加下一步处理人的编号
		processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}
