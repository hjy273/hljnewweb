package com.cabletech.linepatrol.material.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.ctf.workflow.AbstractProcessService;
import com.cabletech.linepatrol.commons.constant.CommonConstant;
import com.cabletech.linepatrol.commons.dao.ApproveDAO;
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.commons.dao.SmHistoryDAO;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.commons.module.ApproveInfo;
import com.cabletech.linepatrol.commons.module.ReplyApprover;
import com.cabletech.linepatrol.commons.module.SMHistory;
import com.cabletech.linepatrol.material.beans.MaterialApplyBean;
import com.cabletech.linepatrol.material.dao.MaterialApplyDao;
import com.cabletech.linepatrol.material.dao.MaterialApplyItemDao;
import com.cabletech.linepatrol.material.dao.MaterialPutStorageDao;
import com.cabletech.linepatrol.material.domain.MaterialApply;
import com.cabletech.linepatrol.material.domain.MaterialApplyItem;
import com.cabletech.linepatrol.material.domain.MaterialPutStorage;
import com.cabletech.linepatrol.material.workflow.MaterialPutStorageWorkflowBO;

@Service
@Transactional
public class MaterialApplyBo extends
		AbstractProcessService<MaterialApply, String> {
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;
	@Resource(name = "smHistoryDAO")
	private SmHistoryDAO historyDAO;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	@Resource(name = "materialApplyDao")
	private MaterialApplyDao dao;
	@Resource(name = "materialApplyItemDao")
	private MaterialApplyItemDao itemDao;
	@Resource(name = "materialPutStorageDao")
	private MaterialPutStorageDao putStorageDao;
	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDao;
	@Resource(name = "approveDAO")
	private ApproveDAO approveDao;
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userDao;
	@Resource(name = "materialPutStorageWorkflowBO")
	private MaterialPutStorageWorkflowBO workflowBo;

	/**
	 * 执行添加材料申请信息
	 * 
	 * @param bean
	 * @param userInfo
	 * @throws Exception
	 */
	public void addMaterialApply(MaterialApplyBean bean, UserInfo userInfo)
			throws Exception {
		bean.setCreator(userInfo.getUserID());
		bean.setContractorId(userInfo.getDeptID());
		MaterialApply apply = new MaterialApply();
		BeanUtil.objectCopy(bean, apply);
		dao.initObject(apply);
		apply.setCreateDate(new Date());
		if("0".equals(bean.getIsSubmited())){
			apply.setState(MaterialApply.LP_APPLY_SAVE);
		}else{
			apply.setState(MaterialApply.LP_APPLY_SUBMIT);
		}
		dao.save(apply);
		bean.setId(apply.getId());

		saveItems(bean);

		String approverId = bean.getApproverId() + "," + bean.getReaderId();
		approverDao.saveApproverOrReader(bean.getApproverId(), apply.getId(),
				CommonConstant.APPROVE_MAN, MaterialApply.LP_MT_NEW);
		approverDao.saveApproverOrReader(bean.getReaderId(), apply.getId(),
				CommonConstant.APPROVE_READ, MaterialApply.LP_MT_NEW);

		Map variables = new HashMap();
		variables.put("assignee", bean.getCreator());
		String processInstanceId = workflowBo.createProcessInstance(
				MaterialPutStorageWorkflowBO.MATERIAL_PUT_STORAGE_WORKFLOW,
				apply.getId(), variables);

		if (CommonConstant.SUBMITED.equals(bean.getIsSubmited())) {
			Task task = workflowBo.getHandleTaskForId(bean.getCreator(), apply
					.getId(), MaterialPutStorageWorkflowBO.APPLY_TASK);
			if (task != null) {
				workflowBo.setVariables(task, "assignee", approverId);
				workflowBo.completeTask(task.getId(), "approve");
				logger.info("材料申请提交审核.....................");
				// processHistoryBO.saveProcessHistory(processInstanceId, task,
				// approverId, userInfo.getUserID(), apply.getId(),
				// ModuleCatalog.MATERIAL);
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				processHistoryBean.initial(task, userInfo, "",
						ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId(approverId);
				processHistoryBean.setObjectId(apply.getId());
				processHistoryBean.setProcessAction("材料申请提交审核");
				processHistoryBean.setTaskOutCome("approve");
				processHistoryBO.saveProcessHistory(processHistoryBean);
			}
			sendMessage(bean.getTitle(), apply.getId(), bean.getApproverId(),
					"审核");
			sendMessage(bean.getTitle(), apply.getId(), bean.getReaderId(),
					"批阅");
		}
	}

	/**
	 * 执行修改材料申请信息
	 * 
	 * @param bean
	 * @param userInfo
	 * @throws Exception
	 */
	public void modMaterialApply(MaterialApplyBean bean, UserInfo userInfo)
			throws Exception {
		MaterialApply apply = dao.get(bean.getId());
		dao.initObject(apply);
		BeanUtil.objectCopy(bean, apply);
		dao.initObject(apply);
		if("0".equals(bean.getIsSubmited())){
			apply.setState(MaterialApply.LP_APPLY_SAVE);
		}else{
			apply.setState(MaterialApply.LP_APPLY_SUBMIT);
		}
		dao.save(apply);
		bean.setId(apply.getId());

		List itemList = itemDao.find(
				" from MaterialApplyItem t where applyId=? ", apply.getId());
		itemDao.getHibernateTemplate().deleteAll(itemList);
		saveItems(bean);

		approverDao.deleteList(apply.getId(), MaterialApply.LP_MT_NEW);
		String approverId = bean.getApproverId() + "," + bean.getReaderId();
		approverDao.saveApproverOrReader(bean.getApproverId(), apply.getId(),
				CommonConstant.APPROVE_MAN, MaterialApply.LP_MT_NEW);
		approverDao.saveApproverOrReader(bean.getReaderId(), apply.getId(),
				CommonConstant.APPROVE_READ, MaterialApply.LP_MT_NEW);

		if (CommonConstant.SUBMITED.equals(bean.getIsSubmited())) {
			Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(),
					apply.getId(), MaterialPutStorageWorkflowBO.APPLY_TASK);
			if (task != null) {
				workflowBo.setVariables(task, "assignee", approverId);
				workflowBo.completeTask(task.getId(), "approve");
				logger.info("材料申请提交审核.....................");
				// processHistoryBO.saveProcessHistory(task.getExecutionId(),
				// task, approverId, userInfo.getUserID(), apply.getId(),
				// ModuleCatalog.MATERIAL);
				ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
				processHistoryBean.initial(task, userInfo, "",
						ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId(approverId);
				processHistoryBean.setObjectId(apply.getId());
				processHistoryBean.setObjectType(ModuleCatalog.MATERIAL);
				processHistoryBean.setProcessAction("材料申请提交审核");
				processHistoryBean.setTaskOutCome("approve");
				processHistoryBO.saveProcessHistory(processHistoryBean);
			}
			sendMessage(bean.getTitle(), apply.getId(), bean.getApproverId(),
					"审核");
			sendMessage(bean.getTitle(), apply.getId(), bean.getReaderId(),
					"批阅");
		}
	}

	/**
	 * 执行删除材料申请信息
	 * 
	 * @param applyId
	 */
	public void delMaterialApply(String applyId) {
		MaterialApply apply = dao.get(applyId);
		dao.initObject(apply);
		dao.delete(apply);

		List itemList = itemDao.find(
				" from MaterialApplyItem t where applyId=? ", apply.getId());
		itemDao.getHibernateTemplate().deleteAll(itemList);

		approverDao.deleteList(apply.getId(), MaterialApply.LP_MT_NEW);
	}

	/**
	 * 执行查询材料申请列表
	 * 
	 * @param bean
	 * @param userInfo
	 * @return
	 */
	public List queryMaterialApplyList(MaterialApplyBean bean, UserInfo userInfo) {
		String condition = ConditionGenerate.getUserQueryCondition(userInfo);
		condition += ConditionGenerate.getCondition("apply.type", bean
				.getType(), "apply.type", "=");
		condition += ConditionGenerate.getDateCondition("apply.createdate",
				bean.getBeginTime(), "apply.createdate", ">=", "00:00:00");
		condition += ConditionGenerate.getDateCondition("apply.createdate",
				bean.getEndTime(), "apply.createdate", "<=", "23:59:59");
		List list = dao.queryList(condition);
		return list;
	}

	/**
	 * 执行查询待办材料申请列表
	 * 
	 * @param bean
	 * @param userInfo
	 * @param taskName
	 * @return
	 */
	public List queryWaitHandleMaterialApplyList(MaterialApplyBean bean,
			UserInfo userInfo, String taskName) {
		List list = new ArrayList();
		List handleTaskList = workflowBo.queryForHandleListBean(userInfo
				.getUserID(), "", taskName);
		DynaBean tmpBean;
		String applyId;
		String putStorageId;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			tmpBean = (DynaBean) handleTaskList.get(i);
			if (tmpBean != null) {
				applyId = (String) tmpBean.get("id");
				putStorageId = (String) tmpBean.get("storage_id");
				if (MaterialPutStorageWorkflowBO.APPLY_APPROVE_TASK
						.equals(tmpBean.get("flow_state"))) {
					if (judgeNotFinishRead(tmpBean, applyId,
							MaterialApply.LP_MT_NEW, userInfo.getUserID())) {
						list.add(tmpBean);
					}
				} else if (MaterialPutStorageWorkflowBO.PUT_STORAGE_CONFIRM_TASK
						.equals(tmpBean.get("flow_state"))) {
					if (judgeNotFinishRead(tmpBean, putStorageId,
							MaterialPutStorage.LP_MT_STORAGE, userInfo
									.getUserID())) {
						list.add(tmpBean);
					}
				} else {
					list.add(tmpBean);
				}
			}
		}
		return list;
	}

	/**
	 * 执行查询已办材料申请列表
	 * 
	 * @param userInfo
	 * @param taskOutCome
	 * @param taskName
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List queryFinishHandledMaterialApplyList(UserInfo userInfo,
			String taskName, String taskOutCome, String beginTime,
			String endTime) {
		String assignee = userInfo.getUserID();
		String condition = ConditionGenerate.getUserQueryCondition(userInfo);
		condition += " and process.operate_user_id='" + assignee + "' ";
		condition += ConditionGenerate.getDateCondition("apply.createdate",
				beginTime, "add_months(sysdate,-1)", ">=", "00:00:00");
		condition += ConditionGenerate.getDateCondition("apply.createdate",
				endTime, "sysdate", "<=", "23:59:59");
		// condition += ConditionGenerate.getConditionByInputValues(
		// "process.handled_task_name", taskName,
		// "process.handled_task_name");
		// condition += ConditionGenerate.getConditionByInputValues(
		// "process.task_out_come", taskOutCome, "process.task_out_come");
		List list = new ArrayList();
		List prevList = new ArrayList();
		List handledTaskList = dao.queryFinishHandledList(condition);
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
							&& tmpBean.get("id").equals(taskBean.get("id"))) {
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

	/**
	 * 执行查询待办材料申请数量信息
	 * 
	 * @param condition
	 * @param userInfo
	 * @return
	 */
	public List queryForHandleMaterialApplyTaskNum(String condition,
			UserInfo userInfo) {
		// TODO Auto-generated method stub
		String assignee = userInfo.getUserID();
		List list = new ArrayList();
		List handleTaskList = workflowBo.queryForHandleListBean(assignee,
				condition, "");
		DynaBean bean;
		String applyId = "";
		String putStorageId = "";
		int waitApplyTaskNum = 0;
		int waitApplyApproveTaskNum = 0;
		int waitPutStorageTaskNum = 0;
		int waitPutStorageConfirmTaskNum = 0;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			bean = (DynaBean) handleTaskList.get(i);
			if (bean != null) {
				applyId = (String) bean.get("id");
				putStorageId = (String) bean.get("storage_id");
				if (bean.get("flow_state") != null) {
					// 根据任务派单状态来计算待办数量
					if (MaterialPutStorageWorkflowBO.APPLY_TASK.equals(bean
							.get("flow_state"))) {
						waitApplyTaskNum++;
					}
					if (MaterialPutStorageWorkflowBO.APPLY_APPROVE_TASK
							.equals(bean.get("flow_state"))) {
						if (judgeNotFinishRead(bean, applyId,
								MaterialApply.LP_MT_NEW, assignee)) {
							waitApplyApproveTaskNum++;
						}
					}
					if (MaterialPutStorageWorkflowBO.PUT_STORAGE_TASK
							.equals(bean.get("flow_state"))) {
						waitPutStorageTaskNum++;
					}
					if (MaterialPutStorageWorkflowBO.PUT_STORAGE_CONFIRM_TASK
							.equals(bean.get("flow_state"))) {
						if (judgeNotFinishRead(bean, putStorageId,
								MaterialPutStorage.LP_MT_STORAGE, assignee)) {
							waitPutStorageConfirmTaskNum++;
						}
					}
				}
			}
		}
		list.add(waitApplyTaskNum);
		list.add(waitApplyApproveTaskNum);
		list.add(waitPutStorageTaskNum);
		list.add(waitPutStorageConfirmTaskNum);
		return list;
	}

	/**
	 * 执行查询已办材料申请数量信息
	 * 
	 * @param condition
	 * @param userInfo
	 * @return
	 */
	public List queryForHandledMaterialApplyTaskNum(String condition,
			UserInfo userInfo, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		String assignee = userInfo.getUserID();
		List list = new ArrayList();
		List handleTaskList = queryFinishHandledMaterialApplyList(userInfo, "",
				"", beginTime, endTime);
		DynaBean bean;
		int applyedTaskNum = 0;
		int applyApprovedTaskNum = 0;
		int puttedStorageTaskNum = 0;
		int putStorageConfirmedTaskNum = 0;
		for (int i = 0; handleTaskList != null && i < handleTaskList.size(); i++) {
			bean = (DynaBean) handleTaskList.get(i);
			if (bean != null) {
				if (bean.get("handled_task_name") != null) {
					// 根据任务派单状态来计算待办数量
					if (MaterialPutStorageWorkflowBO.APPLY_TASK.equals(bean
							.get("handled_task_name"))) {
						applyedTaskNum++;
					}
					if (MaterialPutStorageWorkflowBO.APPLY_APPROVE_TASK
							.equals(bean.get("handled_task_name"))) {
						applyApprovedTaskNum++;
					}
					if (MaterialPutStorageWorkflowBO.PUT_STORAGE_TASK
							.equals(bean.get("handled_task_name"))) {
						puttedStorageTaskNum++;
					}
					if (MaterialPutStorageWorkflowBO.PUT_STORAGE_CONFIRM_TASK
							.equals(bean.get("handled_task_name"))) {
						putStorageConfirmedTaskNum++;
					}
				}
			}
		}
		list.add(applyedTaskNum);
		list.add(applyApprovedTaskNum);
		list.add(puttedStorageTaskNum);
		list.add(putStorageConfirmedTaskNum);
		return list;
	}

	/**
	 * 执行查看材料申请详细信息
	 * 
	 * @param applyId
	 * @return
	 * @throws Exception
	 */
	public Map viewMaterialApply(String applyId) throws Exception {
		MaterialApply apply = dao.get(applyId);
		dao.initObject(apply);
		String cancelUserName = userInfoDao
				.getUserName(apply.getCancelUserId());
		apply.setCancelUserName(cancelUserName);
		MaterialApplyBean bean = new MaterialApplyBean();
		BeanUtil.objectCopy(apply, bean);
		bean.setCreatorName(userDao.getUserName(bean.getCreator()));

		List itemList = itemDao.queryList(" and t.material_new_id='" + applyId
				+ "' ");
		String[] materialTypeId = null;
		String[] materialModelId = null;
		String[] materialId = null;
		String[] materialAddressId = null;
		String[] materialTypeName = null;
		String[] materialModelName = null;
		String[] materialName = null;
		String[] materialAddressName = null;
		String[] materialUnit = null;
		String[] count = null;

		if (itemList != null && !itemList.isEmpty()) {
			materialTypeId = new String[itemList.size()];
			materialModelId = new String[itemList.size()];
			materialId = new String[itemList.size()];
			materialAddressId = new String[itemList.size()];
			materialTypeName = new String[itemList.size()];
			materialModelName = new String[itemList.size()];
			materialName = new String[itemList.size()];
			materialAddressName = new String[itemList.size()];
			materialUnit = new String[itemList.size()];
			count = new String[itemList.size()];
			DynaBean tmpBean;
			for (int i = 0; i < itemList.size(); i++) {
				tmpBean = (DynaBean) itemList.get(i);
				materialTypeId[i] = (String) tmpBean.get("typeid");
				materialModelId[i] = (String) tmpBean.get("modelid");
				materialId[i] = (String) tmpBean.get("materialid");
				materialAddressId[i] = (String) tmpBean.get("addressid");
				materialTypeName[i] = (String) tmpBean.get("typename");
				materialModelName[i] = (String) tmpBean.get("modelname");
				materialName[i] = (String) tmpBean.get("name");
				materialAddressName[i] = (String) tmpBean.get("address");
				materialUnit[i] = (String) tmpBean.get("unit");
				count[i] = (String) tmpBean.get("count");
			}
		}
		bean.setMaterialTypeId(materialTypeId);
		bean.setMaterialModelId(materialModelId);
		bean.setMaterialId(materialId);
		bean.setMaterialTypeName(materialTypeName);
		bean.setMaterialModelName(materialModelName);
		bean.setMaterialName(materialName);
		bean.setMaterialUnit(materialUnit);
		bean.setAddressId(materialAddressId);
		bean.setAddressName(materialAddressName);
		bean.setCount(count);

		Map map = new HashMap();
		List list = putStorageDao
				.queryList(" and t.applyid='" + applyId + "' ");
		if (list != null && !list.isEmpty()) {
			DynaBean tmpBean = (DynaBean) list.get(0);
			map.put("storage_id", tmpBean.get("id"));
		} else {
			map.put("storage_id", "-1");
		}
		map.put("apply_bean", bean);
		return map;
	}

	/**
	 * 执行审核材料申请信息
	 * 
	 * @param bean
	 * @param userInfo
	 * @throws Exception
	 */
	public void approveMaterialApply(MaterialApplyBean bean, UserInfo userInfo)
			throws Exception {
		ApproveInfo approve = new ApproveInfo();
		BeanUtil.objectCopy(bean, approve);
		approve.setApproverId(userInfo.getUserID());
		approve.setApproveTime(new Date());
		approve.setObjectId(bean.getApplyId());
		approve.setObjectType(MaterialApply.LP_MT_NEW);
		approveDao.initObject(approve);
		approveDao.save(approve);
		
		MaterialApply apply = dao.get(bean.getApplyId());
		dao.initObject(apply);
		if(CommonConstant.APPROVE_RESULT_TRANSFER.equals(approve.getApproveResult())){
			apply.setState(MaterialApply.LP_APPROVE_TRANS);
		}else{
			if(CommonConstant.APPROVE_RESULT_PASS.equals(approve.getApproveResult())){
				apply.setState(MaterialApply.LP_APPROVE_PASS);
			}else{
				apply.setState(MaterialApply.LP_APPROVE_NOT_PASS);
			}
		}
		dao.save(apply);
		String readers=dao.getReaderByCondition(bean.getApplyId(), userInfo.getUserID(), bean.getApplyId(),MaterialApply.LP_MT_NEW);
		if (CommonConstant.APPROVE_RESULT_TRANSFER.equals(approve
				.getApproveResult())) {
			approverDao.saveApproverOrReader(bean.getApproverId(), bean
					.getApplyId(), CommonConstant.APPROVE_TRANSFER_MAN,
					MaterialApply.LP_MT_NEW);
		}

		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean
				.getApplyId(), MaterialPutStorageWorkflowBO.APPLY_APPROVE_TASK);
		if (task != null) {
			ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
			processHistoryBean.initial(task, userInfo, "",
					ModuleCatalog.MATERIAL);
			processHistoryBean.setObjectId(apply.getId());
			if (CommonConstant.APPROVE_RESULT_NO.equals(approve
					.getApproveResult())) {
				workflowBo.setVariables(task, "assignee", apply.getCreator());
				workflowBo.completeTask(task.getId(), "not_passed");
				logger.info("材料申请提交审核不通过.....................");
				// processHistoryBO.saveProcessHistory(task.getExecutionId(),
				// task, apply.getCreator(), userInfo.getUserID(), bean
				// .getApplyId(), ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId(apply.getCreator());
				processHistoryBean.setProcessAction("材料申请审核");
				processHistoryBean.setTaskOutCome("not_passed");
			}
			if (CommonConstant.APPROVE_RESULT_TRANSFER.equals(approve
					.getApproveResult())) {
				workflowBo.setVariables(task, "assignee", bean.getApproverId()+","+readers);
				workflowBo.setVariables(task, "transition", "transfer");
				workflowBo.completeTask(task.getId(), "passed");
				logger.info("材料申请提交转审.....................");
				// processHistoryBO.saveProcessHistory(task.getExecutionId(),
				// task, bean.getApproverId(), userInfo.getUserID(), bean
				// .getApplyId(), ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId(bean.getApproverId());
				processHistoryBean.setProcessAction("材料申请转审");
				processHistoryBean.setTaskOutCome("transfer");
			}
			if (CommonConstant.APPROVE_RESULT_PASS.equals(approve
					.getApproveResult())) {
				workflowBo.setVariables(task, "assignee", bean.getCreator());
				workflowBo.setVariables(task, "transition", "put_storage");
				workflowBo.completeTask(task.getId(), "passed");
				logger.info("材料申请提交审核通过.....................");
				// processHistoryBO.saveProcessHistory(task.getExecutionId(),
				// task, bean.getCreator(), userInfo.getUserID(), bean
				// .getApplyId(), ModuleCatalog.MATERIAL);
				processHistoryBean.setNextOperateUserId(bean.getCreator());
				processHistoryBean.setProcessAction("材料申请审核");
				processHistoryBean.setTaskOutCome("put_storage");
			}
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
		if (CommonConstant.APPROVE_RESULT_TRANSFER.equals(approve
				.getApproveResult())) {
			sendMessage(bean.getTitle(), bean.getApplyId(), bean
					.getApproverId(), "审核");
		} else if (CommonConstant.APPROVE_RESULT_NO.equals(approve
				.getApproveResult())) {
			sendMessage(bean.getTitle(), bean.getApplyId(), apply.getCreator(),
					"重新申请");
		} else {
			sendMessage(bean.getTitle(), bean.getApplyId(), apply.getCreator(),
					"提交入库");
		}
	}

	/**
	 * 执行批阅材料申请信息
	 * 
	 * @param bean
	 * @param userInfo
	 * @throws Exception
	 */
	public void readApproveMaterialApply(MaterialApplyBean bean,
			UserInfo userInfo) throws Exception {
		String applyId = bean.getApplyId();
		approverDao.updateReader(userInfo.getUserID(), applyId,
				MaterialApply.LP_MT_NEW);
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		Task task = workflowBo.getHandleTaskForId(userInfo.getUserID(), bean
				.getApplyId(), MaterialPutStorageWorkflowBO.APPLY_APPROVE_TASK);
		if (task != null) {
			processHistoryBean.initial(task, userInfo, "",
					ModuleCatalog.MATERIAL);
			processHistoryBean.setObjectId(applyId);
			processHistoryBean.setNextOperateUserId("");
			processHistoryBean.setProcessAction("材料申请批阅");
			processHistoryBean.setTaskOutCome("read");
			processHistoryBO.saveProcessHistory(processHistoryBean);
		}
	}

	/**
	 * 执行查询材料申请审核信息列表
	 * 
	 * @param applyId
	 * @return
	 */
	public List queryMaterialApplyApproveInfoList(String applyId) {
		String condition = " and approve.object_id='" + applyId + "' ";
		condition += " and approve.object_type='LP_MT_NEW' ";
		List approveInfoList = approveDao.queryList(condition);
		return approveInfoList;
	}

	/**
	 * 执行发送材料申请短信
	 * 
	 * @param title
	 * @param applyId
	 * @param userIds
	 * @param actionString
	 */
	public void sendMessage(String title, String applyId, String userIds,
			String actionString) {
		String content = "【备料】您有一个名称为" + title + "的材料申请单等待您的" + actionString
				+ "！";
		if (StringUtils.isNotBlank(userIds)) {
			String[] userId;
			if (userIds.indexOf(",") != -1) {
				userId = userIds.split(",");
			} else {
				userId = new String[1];
				userId[0] = userIds;
			}
			String sim = "";
			String mobiles = "";
			for (int i = 0; i < userId.length; i++) {
				sim = dao.getSendPhone(userId[i]);
				mobiles = mobiles + sim + ",";
				// smSendProxy.simpleSend(sim, content, null, null, true);
			}
			logger.info("短信内容:" + content);
			logger.info("短信号码:" + mobiles);
			if (mobiles != null && !mobiles.equals("")) {
				super.sendMessage(content, mobiles);
			}
			// 保存短信记录
			SMHistory history = new SMHistory();
			history.setSimIds(mobiles);
			history.setSendContent(content);
			history.setSendTime(new Date());
			// history.setSendState(sendState);
			history.setSmType(MaterialPutStorage.LP_MT_STORAGE);
			history.setObjectId(applyId);
			history.setBusinessModule(MaterialApply.MATERIAL_MODULE);
			historyDAO.save(history);
		}
	}

	/**
	 * 执行保存材料申请中的材料申请项目信息
	 * 
	 * @param bean
	 */
	public void saveItems(MaterialApplyBean bean) {
		String[] materialIds = bean.getMaterialId();
		MaterialApplyItem item;
		int addressId = 0;
		int materialId = 0;
		double count = 0;
		for (int i = 0; materialIds != null && i < materialIds.length; i++) {
			addressId = Integer.parseInt(bean.getAddressId()[i]);
			materialId = Integer.parseInt(materialIds[i]);
			count = Double.parseDouble(bean.getCount()[i]);
			item = new MaterialApplyItem();
			item.setAddressId(addressId);
			item.setApplyId(bean.getId());
			item.setCount(count);
			item.setMaterialId(materialId);
			item.setState(bean.getType());
			itemDao.initObject(item);
			itemDao.save(item);
		}
	}

	@Override
	public void deleteAllData(String objectId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List getProcessInstanceIdList(String objectId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancelMaterialApply(MaterialApplyBean bean, UserInfo userInfo) {
		// TODO Auto-generated method stub
		MaterialApply apply = dao.get(bean.getId());
		dao.initObject(apply);
		apply.setCancelReason(bean.getCancelReason());
		apply.setCancelTime(new Date());
		apply.setCancelUserId(userInfo.getUserID());
		apply.setState(MaterialApply.CANCELED_STATE);
		dao.save(apply);
		String processInstanceId = "";
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processInstanceId = MaterialPutStorageWorkflowBO.MATERIAL_PUT_STORAGE_WORKFLOW
				+ "." + apply.getId();
		processHistoryBean.initial(null, userInfo, processInstanceId,
				ModuleCatalog.MATERIAL);
		processHistoryBean.setHandledTaskName("any");
		processHistoryBean.setNextOperateUserId("");
		processHistoryBean.setObjectId(apply.getId());
		processHistoryBean.setProcessAction("材料申请流程取消");
		processHistoryBean.setTaskOutCome("cancel");
		try {
			processHistoryBO.saveProcessHistory(processHistoryBean);// 保存流程历史信息
			workflowBo.endProcessInstance(processInstanceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
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
	 * 执行判断材料申请是否已被批阅
	 * 
	 * @param bean
	 * @param objectId
	 * @param objectType
	 * @param userId
	 * @return
	 */
	private boolean judgeNotFinishRead(DynaBean bean, String objectId,
			String objectType, String userId) {
		ReplyApprover approver;
		boolean flag = false;
		DynaBean tmpBean;
		List<ReplyApprover> approverList = approverDao.getApprovers(objectId,
				objectType);
		for (int i = 0; approverList != null && i < approverList.size(); i++) {
			approver = approverList.get(i);
			if (approver != null && userId.equals(approver.getApproverId())) {
				if (CommonConstant.APPROVE_READ.equals(approver
						.getApproverType())) {
					bean.set("is_reader", "1");
					if (CommonConstant.FINISH_READED.equals(approver
							.getFinishReaded())) {
						flag = false;
					} else {
						flag = true;
					}
				} else {
					flag = true;
				}
			}
		}
		for (int i = 0; approverList != null && i < approverList.size(); i++) {
			approver = approverList.get(i);
			if (approver != null && userId.equals(approver.getApproverId())) {
				if (CommonConstant.APPROVE_TRANSFER_MAN.equals(approver
						.getApproverType())) {
					bean.set("is_reader", "0");
					flag = true;
				} else if (CommonConstant.APPROVE_MAN.equals(approver
						.getApproverType())) {
					bean.set("is_reader", "0");
					flag = true;
				} else {
					flag = flag && true;
				}
			}
		}
		return flag;
	}

	@Override
	protected HibernateDao<MaterialApply, String> getEntityDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
