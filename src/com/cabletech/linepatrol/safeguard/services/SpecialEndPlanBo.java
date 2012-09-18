package com.cabletech.linepatrol.safeguard.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cabletech.linepatrol.safeguard.beans.SpecialEndPlanBean;
import com.cabletech.linepatrol.safeguard.dao.SafeguardConDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardPlanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardSpplanDao;
import com.cabletech.linepatrol.safeguard.dao.SafeguardTaskDao;
import com.cabletech.linepatrol.safeguard.dao.SpecialEndPlanDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardConstant;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSpplan;
import com.cabletech.linepatrol.safeguard.module.SpecialEndPlan;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardReplanSubWorkflowBO;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardSubWorkflowBO;
import com.cabletech.linepatrol.specialplan.dao.SpecialPlanDao;

@Service
@Transactional
public class SpecialEndPlanBo extends EntityManager<SpecialEndPlan, String> {

	private static Logger logger = Logger.getLogger(SpecialEndPlanBo.class
			.getName());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Override
	protected HibernateDao<SpecialEndPlan, String> getEntityDao() {
		return specialEndPlanDao;
	}

	@Autowired
	private SafeguardSubWorkflowBO workflowBo;

	@Resource(name = "specialEndPlanDao")
	private SpecialEndPlanDao specialEndPlanDao;
	
	@Resource(name = "specialPlanDao")
	private SpecialPlanDao specialPlanDao;
	
	@Resource(name = "safeguardSpplanDao")
	private SafeguardSpplanDao safeguardSpplanDao;
	
	@Resource(name = "safeguardPlanDao")
	private SafeguardPlanDao safeguardPlanDao;
	
	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO replyApproverDAO;
	
	@Resource(name = "safeguardTaskDao")
	private SafeguardTaskDao safeguardTaskDao;
	
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	
	@Resource(name = "safeguardConDao")
	private SafeguardConDao safeguardConDao;
	
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	
	public SpecialEndPlan getSpecialEndPlan(String spEndId){
		return specialEndPlanDao.findByUnique("id", spEndId);
	}
	
	/**
	 * 由特巡计划ID获得终止计划ID
	 * @param spid
	 * @return
	 */
	public String getEndPlanIdBySpId(String spid){
		String endPlanId = null;
		List list = specialEndPlanDao.findByProperty("planId", spid);
		if(list != null && list.size() > 0){
			endPlanId = ((SpecialEndPlan)list.get(0)).getId();
			Date createDate = ((SpecialEndPlan)list.get(0)).getCreateTime();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				SpecialEndPlan specialEndPlan = (SpecialEndPlan) iterator.next();
				if(createDate.before(specialEndPlan.getCreateTime())){
					createDate = specialEndPlan.getCreateTime();
					endPlanId = specialEndPlan.getId();
				}
			}
		}
		return endPlanId;
	}
	
	/**
	 * 获得代办工作
	 * @param userInfo
	 * @param condition
	 * @param taskName
	 * @return
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
				String spplanId = (String) bean.get("sp_end_id");
				boolean read = approverDAO.isReadOnly(spplanId,
						assignee, SafeguardConstant.LP_SPECIAL_ENDPLAN);
				bean.set("isread", "" + read);
				if(judgeFinishRead(bean, spplanId, SafeguardConstant.LP_SPECIAL_ENDPLAN, assignee)){
					list2.add(bean);
				}
			}
		}
		return list2;
	}
	
	/**
	 * 判断是否已被查阅
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
		//logger.info("*******flag:::" + flag + " objectId::" + objectId + " objectType::" + objectType);
		return flag;
	}

	/**
	 * 添加特许计划终止
	 * @param specialEndPlanBean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void addSpecialEndPlan(SpecialEndPlanBean specialEndPlanBean, UserInfo userInfo) throws ServiceException {
		String approver = specialEndPlanBean.getApproveId();
		String reader = specialEndPlanBean.getReader();
		//String spEndId = specialEndPlanBean.getId();
		String spId = specialEndPlanBean.getPlanId();
		String conId = userInfo.getDeptID();
		
		SafeguardSpplan safeguardSpplan = safeguardSpplanDao.findByUnique("spplanId", spId);
		String planId = safeguardSpplan.getPlanId();
		SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
		String taskId = safeguardPlan.getSafeguardId();
		String[] readerPhones = specialEndPlanBean.getReaderPhones();
		String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);
		
		SpecialEndPlan specialEndPlan = new SpecialEndPlan();
		try {
			BeanUtil.objectCopy(specialEndPlanBean, specialEndPlan);
		} catch (Exception e) {
			logger.error("SpecialEndPlanBean转换为SpecialEndPlan出错，出错信息：" + e.getMessage());
			e.printStackTrace();
			throw new ServiceException();
		}
		//specialEndPlan.setEndType("1");
		specialEndPlan.setCreater(userInfo.getUserID());
		specialEndPlan.setCreateTime(new Date());
		
		SpecialEndPlan specialEndPlanSave = specialEndPlanDao.saveSpecialEndPlan(specialEndPlan);
		String spEndId = specialEndPlanSave.getId();
		// 保存审核人
		replyApproverDAO.saveApproverOrReader(approver, spEndId,
				SafeguardConstant.APPROVE_MAN,
				SafeguardConstant.LP_SPECIAL_ENDPLAN);
		// 保存抄送人
		replyApproverDAO.saveApproverOrReader(reader, spEndId,
				SafeguardConstant.APPROVE_READ,
				SafeguardConstant.LP_SPECIAL_ENDPLAN);
		// 代维认领线维分配任务并制定计划
		
		// JBPM
		Map variables = new HashMap();
		variables.put("assignee", userInfo.getUserID());
		//variables.put("transition", "start");
		workflowBo.createProcessInstance("safeguard_sub", spEndId, variables);
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_sub."+spEndId);
		if (task != null
				&& SafeguardSubWorkflowBO.CHANGE_GUARD_PLAN_TASK.equals(task
						.getName())) {
			workflowBo.setVariables(task, "assignee", approver + "," + reader);
			workflowBo.completeTask(task.getId(), "approve");
			
			//保存流程历史
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			String nextOperateUserId = "";
			processHistoryBean.setProcessAction("特巡计划终止");//添加流程处理动作说明
			processHistoryBean.setTaskOutCome("approve");//添加工作流流向信息
			processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
			processHistoryBean.setNextOperateUserId(approver + "," + reader);//添加下一步处理人的编号
			processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
			try {
				processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		
		String name = safeguardTaskDao.getTaskNameByTaskId(taskId);
		String mobiles = safeguardTaskDao.getMobiles(approver);
		// 发送短信
		String content = "【保障】您有一个名称为\"" + name + "\"保障方案终止单等待您的审核。";
		sendMessage(content, mobiles);
		// 保存短信记录
		saveMessage(content, mobiles, spEndId,
				SafeguardConstant.LP_SPECIAL_ENDPLAN,
				ModuleCatalog.SAFEGUARD);
		
		if(readerPhones != null && readerPhones.length > 0){
			for (int i = 0; i < readerPhones.length; i++) {
				content = "【保障】您有一个名称为\"" + name + "\"保障方案终止单等待您的查阅。";
				sendMessage(content, readerPhones[i]);

				// 保存短信记录
				saveMessage(content, readerPhones[i], spEndId, SafeguardConstant.LP_SPECIAL_ENDPLAN,
						ModuleCatalog.SAFEGUARD);
			}
		}
	}
	
	/**
	 * 审批特巡计划终止
	 * @param specialEndPlanBean
	 * @param userInfo
	 * @throws ServiceException
	 */
	public void approveSpecialEndPlan(SpecialEndPlanBean specialEndPlanBean, UserInfo userInfo) throws ServiceException {
		String id = specialEndPlanBean.getId();
		String approveResult = specialEndPlanBean.getApproveResult();
		String approveRemark = specialEndPlanBean.getApproveRemark();
		String operator = specialEndPlanBean.getOperator();
		String spId = specialEndPlanBean.getPlanId();
		String approvers = specialEndPlanBean.getApprovers();
		SpecialEndPlan specialEndPlan = specialEndPlanDao.findByUnique("id", id);
		Date endDate = specialEndPlan.getEndDate();
		String endType = specialEndPlan.getEndType();
		
		String creator = specialEndPlanBean.getCreater();
		String phone = "";
		
		SafeguardSpplan safeguardSpplan = safeguardSpplanDao.findByUnique("spplanId", spId);
		String planId = safeguardSpplan.getPlanId();
		SafeguardPlan safeguardPlan = safeguardPlanDao.findByUnique("id", planId);
		String conId = safeguardPlan.getContractorId();
		String taskId = safeguardPlan.getSafeguardId();
		String content = "";
		
		//处理该特巡终止ID的其他ID
		String otherEndPlanId = specialEndPlanDao.getOtherEndId(spId, id);
		//去除已阅和转审给抄送人和自己为抄送人的情况
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approvers + "','" + userInfo.getUserID() + "')";
		String approverIdsReader1 = safeguardPlanDao.getApproverIds(id, SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SPECIAL_ENDPLAN, condition);
		String approverIdsReader2 = safeguardPlanDao.getApproverIds(otherEndPlanId, SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SPECIAL_ENDPLAN, condition);
		String approverIdsReader = approverIdsReader1.equals("")?approverIdsReader2:approverIdsReader1;
		// 保存审核信息
		saveApproveInfo(id, SafeguardConstant.LP_SPECIAL_ENDPLAN, userInfo
				.getUserID(), approveResult, approveRemark);
		String eid = safeguardConDao.getIdByConIdAndTaskId(taskId, conId);

		if ("approve".equals(operator)) {
			phone = getPhoneByUserId(creator);
			if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {

				// 发送短信内容
				content = "【保障】您的\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
						+ "\"保障方案终止单未通过审核。";
			} else {
				// 发送短信内容
				content = "【保障】您的\"" + safeguardTaskDao.getTaskNameByTaskId(taskId)
						+ "\"保障方案终止单已经通过审核。";
				specialPlanDao.updateEndDateBySpId(spId, endDate);
			}
			
		} else {
			// 保存审核人信息
			replyApproverDAO.saveApproverOrReader(approvers, id,
					SafeguardConstant.APPROVE_TRANSFER_MAN,
					SafeguardConstant.LP_SPECIAL_ENDPLAN);

			// 发送短信
			content = "【保障】您有一个名称为\""
					+ safeguardTaskDao.getTaskNameByTaskId(taskId)
					+ "\"保障方案终止单等待您的审核。";
			phone = specialEndPlanBean.getMobiles();
			
		}
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_sub."+id);
		if (task != null
				&& SafeguardSubWorkflowBO.CHANGE_GUARD_PLAN_APPROVE_TASK.equals(task
						.getName())) {
			System.out.println("保障方案终止待审核：" + task.getName());
			if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				if(SpecialEndPlan.ENDPLAN.equals(endType)){
					//workflowBo.completeTask(id, "approve");
					workflowBo.setVariables(task, "transition", "end");
					workflowBo.completeTask(task.getId(), "passed");
					System.out.println("保障方案终止审核通过！");
				}else{
					workflowBo.setVariables(task, "assignee", creator);
					workflowBo.setVariables(task, "transition", "re_planed");
					workflowBo.completeTask(task.getId(), "passed");
					System.out.println("等待重新制定特巡计划！");
				}
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("特巡计划终止审批通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("passed");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
				processHistoryBean.setNextOperateUserId(creator);//添加下一步处理人的编号
				processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
				try {
					processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException();
				}
			}
			if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {
				workflowBo.setVariables(task, "transition", "end");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("保障方案终止审核不通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("特巡计划终止审批不通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("passed");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
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
				System.out.println("保障方案终止已经转审！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("特巡计划终止转审");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("transfer");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
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
		saveMessage(content, phone, id,
				SafeguardConstant.LP_SPECIAL_ENDPLAN,
				ModuleCatalog.SAFEGUARD);
		
	}
	
	//审批特巡计划
	public void approveSpecialPlan(SpecialEndPlanBean specialEndPlanBean, UserInfo userInfo, String spplan, String creator) throws ServiceException {
		String approveResult = specialEndPlanBean.getApproveResult();
		String approveRemark = specialEndPlanBean.getApproveRemark();
		String approvers = specialEndPlanBean.getApprovers();
		String operator = specialEndPlanBean.getOperator();
		
		//去除已阅和转审给抄送人和自己为抄送人的情况
		String condition = "and t.finish_readed<>'1' and t.approver_id not in ('" + approvers + "','" + userInfo.getUserID() + "')";
		String approverIdsReader = safeguardPlanDao.getApproverIds(spplan, SafeguardConstant.APPROVE_READ, SafeguardConstant.LP_SPECIAL_PLAN, condition);
		
		String phone = "";
		String content = "";
		// 保存审核信息
		saveApproveInfo(spplan, SafeguardConstant.LP_SPECIAL_PLAN, userInfo
				.getUserID(), approveResult, approveRemark);
		String eid = safeguardConDao.getConIdBySpecialPlanId(spplan);
		String name = specialEndPlanDao.getSpecialNameBySpecialId(spplan);
		
		if ("approve".equals(operator)) {
			phone = getPhoneByUserId(creator);
			if (SafeguardConstant.APPROVE_RESULT_NO.equals(approveResult)) {

				// 发送短信内容
				content = "【保障】您的\"" + name	+ "\"特巡计划单未通过审核。";
			} else {
				// 发送短信内容
				content = "【保障】您的\"" + name + "\"特巡计划单已经通过审核。";
			}
			
		} else {
			// 保存审核人信息
			replyApproverDAO.saveApproverOrReader(approvers, spplan,
					SafeguardConstant.APPROVE_TRANSFER_MAN,
					SafeguardConstant.LP_SPECIAL_PLAN);

			// 发送短信
			content = "【保障】您有一个名称为\"" + name + "\"特巡计划单等待您的审核。";
			phone = specialEndPlanBean.getMobiles();
			
		}
		
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_replan_sub."+spplan);
		if (task != null
				&& SafeguardReplanSubWorkflowBO.REMAKE_GUARD_PLAN_APPROVE_TASK.equals(task
						.getName())) {
			System.out.println("特巡计划待审核：" + task.getName());
			if (SafeguardConstant.APPROVE_RESULT_PASS.equals(approveResult)) {
				workflowBo.setVariables(task, "transition", "end");
				workflowBo.completeTask(task.getId(), "passed");
				System.out.println("特巡计划审核通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("特巡计划审批通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("passed");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
				processHistoryBean.setNextOperateUserId(creator);//添加下一步处理人的编号
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
				System.out.println("特巡计划审核不通过！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("特巡计划审批不通过");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("not_passed");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
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
				System.out.println("特巡计划已经转审！");
				
				//保存流程历史
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				String nextOperateUserId = "";
				processHistoryBean.setProcessAction("特巡计划转审");//添加流程处理动作说明
				processHistoryBean.setTaskOutCome("transfer");//添加工作流流向信息
				processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
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
		saveMessage(content, phone, spplan,
				SafeguardConstant.LP_SPECIAL_PLAN,
				ModuleCatalog.SAFEGUARD);
		
	}
	
	/**
	 * 获得特巡计划终止列表
	 * @param userInfo
	 * @return
	 * @throws ServiceException
	 */
	public List getSpecialEndPlanList(UserInfo userInfo) throws ServiceException {
		String condition = "";
		logger.info("获得用户类型：" + userInfo.getDeptype());
		if("2".equals(userInfo.getDeptype())){
			condition += " and userInfo.userid='" + userInfo.getUserID() + "'";
		}
		List list = specialEndPlanDao.getSpecialEndPlanList(condition);
		return list;
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
	 * 通过用户ID查询用户的手机号码
	 * 
	 * @param userId：用户ID
	 * @return：返回用户手机号码
	 */
	public String getPhoneByUserId(String userId) {
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = safeguardPlanDao.getHibernateTemplate().find(hql, userId);
		if (list != null && !list.equals("")) {
			UserInfo userInfo = (UserInfo) list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	/**
	 * 查阅特巡计划终止
	 * @param userInfo
	 * @param approverId
	 * @param endId
	 * @throws ServiceException
	 */
	public void readReply(UserInfo userInfo, String approverId, String endId) throws ServiceException{
		approverDAO.updateReader(approverId, endId, SafeguardConstant.LP_SPECIAL_ENDPLAN);
		
		SpecialEndPlan specialEndPlan = specialEndPlanDao.findByUnique("id", endId);
		String creator = specialEndPlan.getCreater();
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_sub."+endId);
		String eid = specialEndPlanDao.getTaskConIdByEndId(endId);
		//保存流程历史
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("特巡计划终止查阅");//添加流程处理动作说明
		processHistoryBean.setTaskOutCome("");//添加工作流流向信息
		processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
		processHistoryBean.setNextOperateUserId("");//添加下一步处理人的编号
		processHistoryBean.setObjectId(eid);//添加流程实例的Key信息。建议：一派多时使用每个流程的Key信息；存在子流程时使用父流程的Key信息
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);//保存流程历史信息
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	/**
	 * 查阅特巡计划
	 * @param userInfo
	 * @param approverId
	 * @param spId
	 * @throws ServiceException
	 */
	public void readSpecialReply(UserInfo userInfo, String approverId, String spId) throws ServiceException{
		approverDAO.updateReader(approverId, spId, SafeguardConstant.LP_SPECIAL_PLAN);
		
		/*SpecialEndPlan specialEndPlan = specialEndPlanDao.findByUnique("id", spId);
		String creator = specialEndPlan.getCreater();*/
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), "safeguard_replan_sub."+spId);
		String eid = safeguardConDao.getConIdBySpecialPlanId(spId);
		//保存流程历史
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.setProcessAction("特巡计划查阅");//添加流程处理动作说明
		processHistoryBean.setTaskOutCome("");//添加工作流流向信息
		processHistoryBean.initial(task, userInfo, "",ModuleCatalog.SAFEGUARD);
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
