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
	 * 添加演练总结前加载演练任务和演练方案
	 * 
	 * @param planId：演练方案ID
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
	 * 添加演练总结
	 * 
	 * @param drillSummaryBean：演练总结实体Bean
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

		// 保存演练总结
		DrillSummary drillSummarySave = saveDrillSummary(drillSummaryBean,
				userInfo);
		String summaryId = drillSummarySave.getId();

		drillTaskConDao.setStateByContractorIdAndTaskId(userInfo.getDeptID(),
				taskId, DrillTaskCon.APPROVESUMMARY);

		// 保存附件
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), summaryId, DrillConstant.LP_DRILLSUMMARY,
				userInfo.getUserID());

		//保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, summaryId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLSUMMARY);
		
		//保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, summaryId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLSUMMARY);
		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, userInfo.getDeptID());
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),eid);
		if (task != null && DrillWorkflowBO.CREATE_DRILL_SUMMARY_TASK.equals(task.getName())) {
			System.out.println("演练总结添加中：" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "approve");
			System.out.println("演练总结已经提交！");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("制定演练总结");//添加流程处理动作说明
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
		String content = "【演练】您有一个名称为\"" + name + "\"演练总结单等待您的审核。";
		sendMessage(content, mobiles);

		// 保存短信记录
		saveMessage(content, mobiles, summaryId,
				DrillConstant.LP_DRILLSUMMARY, ModuleCatalog.DRILL);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "【演练】您有一个名称为\"" + name + "\"演练总结单等待您的查阅。";
				sendMessage(content, readerPhones[i]);

				// 保存短信记录
				saveMessage(content, readerPhones[i], summaryId, DrillConstant.LP_DRILLSUMMARY,
						ModuleCatalog.DRILL);
			}
		}
	}
	
	/**
	 * 演练总结临时保存
	 * @param drillSummaryBean
	 * @param userInfo
	 * @param files
	 * @throws ServiceException
	 */
	public void tempSaveDrillSummary(DrillSummaryBean drillSummaryBean,
			UserInfo userInfo, List<FileItem> files) throws ServiceException {
		String approvers = drillSummaryBean.getApproveId();
		String reader = drillSummaryBean.getReader();

		// 保存演练总结
		DrillSummary drillSummarySave = saveDrillSummary(drillSummaryBean,
				userInfo);
		String summaryId = drillSummarySave.getId();

		// 保存附件
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), summaryId, DrillConstant.LP_DRILLSUMMARY,
				userInfo.getUserID());

		replyApproverDAO.deleteList(summaryId, DrillConstant.LP_DRILLSUMMARY);
		//保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, summaryId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLSUMMARY);
		
		//保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, summaryId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLSUMMARY);
	}

	/**
	 * 保存演练总结表
	 * 
	 * @param drillSummaryBean：演练总结实体Bean
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
			logger.info("DrillSummaryBean转换为DrillSummary出错，出错信息："
					+ e.getMessage());
			throw new ServiceException();
		}
		drillSummary.setCreator(userInfo.getUserID());
		drillSummary.setCreateTime(new Date());
		return drillSummaryDao.addDrillSummary(drillSummary);
	}

	/**
	 * 通过演练总结ID获得演练任务、演练方案、演练总结
	 * 
	 * @param summaryId：演练总结ID
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
	 * 编辑演练总结
	 * 
	 * @param drillSummaryBean：演练总结实体Bean
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

		// 保存演练总结
		saveDrillSummary(drillSummaryBean, userInfo);

		String summaryId = drillSummaryBean.getId();
		drillTaskConDao.setStateByContractorIdAndTaskId(userInfo.getDeptID(),
				taskId, DrillTaskCon.APPROVESUMMARY);

		// 保存附件
		saveFiles(files, ModuleCatalog.DRILL, userInfo
				.getRegionName(), summaryId, DrillConstant.LP_DRILLSUMMARY,
				userInfo.getUserID());

		replyApproverDAO.deleteList(summaryId, DrillConstant.LP_DRILLSUMMARY);
		//保存审核人信息
		replyApproverDAO.saveApproverOrReader(approvers, summaryId, DrillConstant.APPROVE_MAN, DrillConstant.LP_DRILLSUMMARY);
		
		//保存抄送人信息
		replyApproverDAO.saveApproverOrReader(reader, summaryId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLSUMMARY);
		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, userInfo.getDeptID());
		// 工作流
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null && DrillWorkflowBO.CREATE_DRILL_SUMMARY_TASK.equals(task.getName())) {
			System.out.println("演练方案添加中：" + task.getName());
			workflowBo.setVariables(task, "assignee", approvers+","+reader);
			workflowBo.completeTask(task.getId(), "approve");
			System.out.println("演练方案已经提交！");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("制定演练总结");//添加流程处理动作说明
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
		String content = "【演练】您有一个名称为\"" + name + "\"演练总结单等待您的审核。";
		sendMessage(content, mobiles);

		// 保存短信记录
		saveMessage(content, mobiles, summaryId,
				DrillConstant.LP_DRILLSUMMARY, ModuleCatalog.DRILL);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "【演练】您有一个名称为\"" + name + "\"演练总结单等待您的查阅。";
				sendMessage(content, readerPhones[i]);

				// 保存短信记录
				saveMessage(content, readerPhones[i], summaryId, DrillConstant.LP_DRILLSUMMARY,
						ModuleCatalog.DRILL);
			}
		}
		
		
	}

	/**
	 * 审核演练总结
	 * 
	 * @param drillSummaryBean：演练总结实体Bean
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
		
		//去除已阅和转审给抄送人和自己为抄送人的情况
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approvers + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = drillPlanDao.getApproverIds(summaryId, DrillConstant.APPROVE_READ, DrillConstant.LP_DRILLSUMMARY, condition);
		// 保存审核信息
		saveApproveInfo(summaryId,
				DrillConstant.LP_DRILLSUMMARY, userInfo.getUserID(),
				approveResult, approveRemark);

		if ("approve".equals(operator)) {
			phone = getPhoneByUserId(creator);
			if (DrillConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				drillTaskConDao.setStateByContractorIdAndTaskId(conId, taskId,
						DrillTaskCon.SUMMARYUNAPPROVE);
				drillSummaryDao.setUnapproveNumberBySummaryId(summaryId);

				// 发送短信内容
				content = "【演练】您的\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"演练总结单未通过审核。";
			} else {
				drillTaskConDao.setStateByContractorIdAndTaskId(conId, taskId,
						DrillTaskCon.DRILLREMARK);

				// 发送短信内容
				content = "【演练】您的\"" + drillTaskDao.getTaskNameByTaskId(taskId)
						+ "\"演练总结单已经通过审核。";

			}
			
		} else {
			// 保存转审人信息
			replyApproverDAO.saveApproverOrReader(approvers, summaryId, DrillConstant.APPROVE_TRANSFER_MAN, DrillConstant.LP_DRILLSUMMARY);

			// 发送短信
			content = "【演练】您有一个名称为\""
					+ drillTaskDao.getTaskNameByTaskId(taskId)
					+ "\"演练总结单等待您的审核。";
			phone = drillSummaryBean.getMobiles();
		}

		String eid = drillTaskConDao.getIdByConIdAndTaskId(taskId, conId);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), eid);
		if (task != null
				&& DrillWorkflowBO.APPROVE_DRILL_SUMMARY_TASK.equals(task.getName())) {
			System.out.println("演练总结待审核：" + task.getName());
			if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", userInfo.getUserID());
				workflowBo.setVariables(task, "transition", "evaluate");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("演练总结审核通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("演练总结审批通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("evaluate");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(userInfo.getUserID());//添加下一步处理人的编号
				processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", creator);
				workflowBo.completeTask(task.getId(), "not_passed");
				//workflowBo.signal(processInstanceId, "guard_plan_execute_state", "summarize");
				System.out.println("演练总结审核不通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("演练总结审批不通过");//添加流程处理动作说明
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
			if (SafeguardConstant.APPROVE_RESULT_TRANSFER.equals(approveResult)) {
				workflowBo.setVariables(task, "assignee", approvers+","+approverIdsReader);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("演练总结已经转审！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("演练总结转审");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("transfer");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.DRILL);
				processHistoryBean.setNextOperateUserId(approvers);//添加下一步处理人的编号
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
		// 保存短信记录
		saveMessage(content, phone, summaryId,
				DrillConstant.LP_DRILLSUMMARY, ModuleCatalog.DRILL);
		
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
		//保存流程历史
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		String nextOperateUserId = "";
		processHistoryBean.setProcessAction("演练总结查阅");//添加流程处理动作说明
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
